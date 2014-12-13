package oxogame.board;

import java.util.ArrayList;
import java.util.List;
import oxogame.game.MoveEvent;
import oxogame.token.Token;

/**
 * Class that manages a Board object.
 *
 * @author David Hemming
 */
public class BoardManager {
    
    private Board board;
    private int moveCount;
    private final List<List<Square>> linearSquares;
    private BoardListener boardListener;
    
    /**
     *
     * @param boardListener an object that wants to be alerted about certain
     *                      events that have occurred. 
     */
    public BoardManager(BoardListener boardListener) {
        this.boardListener = boardListener;
        board = new Board();
        linearSquares = getLinearSquareList();
        moveCount = 0;
    }
    
    /**
     * Tells the Board to reset which clears all the Squares of Tokens ready
     * for a new Game.
     *
     */
    public void resetBoard() {
        board.resetBoard();
        moveCount = 0;
    }
    
    public Board getBoard() {
        return board;
    }
    
    /**
     * Handles a MoveEvent by placing the Players Token in the desired Square 
     * the Board, incrementing the moveCount, and then checking the Board
     * status.
     *
     * @author  dave
     * @param   playerMove
     * @return              true if the Token was successfully placed
     *                      false otherwise
     */
    public boolean playerMove(MoveEvent playerMove) {
        if (board.placeToken(playerMove.getSquare(), playerMove.getToken())) {
           moveCount++;
           checkBoardStatus();
           return true;
        }
        
        return false;
        
    }
    
    /**
     * Creates a List of Linear Squares, be they in the form of a row, column,
     * or diagonal. This makes it easy to check for three squares in a row that
     * contains the same Token, thus indicating a win by the Player that
     * controls that Token.
     * 
     * @author  dave
     * @return  a List of Lists containing Squares that form a row, col, or
     *          diagonal
     */
    private List<List<Square>> getLinearSquareList() {
        List<List<Square>> linearSquareList = new ArrayList<>();
        
        for (int i = 0; i < board.getRows(); i++) {
            linearSquareList.add(board.getRow(i));
        }
        
        for (int i = 0; i < board.getCols(); i++) {
            linearSquareList.add(board.getCol(i));
        }
        
        linearSquareList.add(board.getDiagonal(0));
        linearSquareList.add(board.getDiagonal(board.getCols() - 1));
        
        return linearSquareList;
    }
    
    /**
     * Check the Boards status.  The status can either be a Player has achieved
     * three Tokens in a row anywhere on the Board, or the Board is full and no
     * more Tokens can placed anywhere on the Board.
     * 
     * @author dave
     */
    private void checkBoardStatus() {
        if (moveCount >= 5) {
            for (List<Square> linearRow : linearSquares) {
                if (hasThreeInARow(linearRow)) {
                    fireFirstToThreeInARowAchievedEvent(linearRow.get(0).getToken());
                    break;
                }
            }
        }
        
        if (moveCount == 9) {
            fireBoardIsFullEvent();
        }
    }
    
    /**
     * Checks to see if a List (row/col/diagonal) of Squares has three of the
     * same Tokens.
     * 
     * @param squares   a row of Squares to test for three of the same Token
     * @return          true if the row of Squares has three Tokens in a row
     *                  false otherwise
     */
    private boolean hasThreeInARow(List<Square> squares) {
        
        Token token = null;
        
        for (Square s : squares) {
            if (token == null) {
                token = s.getToken();
            } else {
                if (token != s.getToken())
                    return false;
            }
        }
        
        return true;
    }
    
    /**
     * Alert the listener that a row contains three Tokens of the same type.
     * 
     * @param t the Token that has successfully occupied three Squares in a row
     */
    private void fireFirstToThreeInARowAchievedEvent(Token t) {
        boardListener.threeInARowAchieved(t);
    }
    
    /**
     * Alerts the listener that the Board is full and it is not possible to 
     * place any more Tokens.
     */
    private void fireBoardIsFullEvent() {
        boardListener.boardIsFull();
    }
    
    
    
}
