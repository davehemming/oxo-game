
package oxogame.board;

import java.util.ArrayList;
import java.util.List;
import oxogame.token.Token;

/**
 * Class that represents a 3 x 3 board (grid).  The board is composed of 
 * squares (locations), in which Tokens can be placed by Players.
 * 
 * @author David Hemming
 * @date 8 October 2013
 * 
 */
public class Board {
        
    private final int ROWS = 3;
    private final int COLS = 3;
    private final List<List<Square>> squareGrid;
    
    public Board() {
        squareGrid = new ArrayList<>();
        initBoard();
    }
    
    /**
     * Populates the board with a grid of Squares.
     * 
     * @author  David Hemming
     * 
     */
    private void initBoard() {
        
        for (int x = 0; x < COLS; x++) {
            List<Square> row = new ArrayList<>();
            for (int y = 0; y < ROWS; y++) {
                row.add(new Square(x, y));
            }
            squareGrid.add(row);
        }
    }
    
    /**
     * Resets the Board Squares by setting each Squares Token parameter to null.
     * 
     * @author  David Hemming
     * 
     */
    void resetBoard() {
        
        for (List<Square> l : squareGrid) {
            for (Square s : l) {
                s.setToken(null);
            }
        }
    }
    
    /**
     * Checks to see if a Square on the Board, as identified by an x y location
     * is both valid and void of any other Tokens.
     *
     * @author  David Hemming
     * @param x the x-coordinate of the Square to interrogate
     * @param y the y-coordinate of the Square to interrogate
     * @return  true if it is a valid Square and that Square is empty
     *          otherwise it returns false.
     */
    public boolean canPlaceToken(int x, int y) {
        
        return isRowValid(x) && isColValid(y) && isSquareVacant(x, y);
       
    }
    
    /**
     * Checks to see if a supplied Square exists on the board and is vacant.
     *
     * @author  David Hemming
     * @param   s the Square to interrogate
     * @return  true if the Square is valid AND the Square is vacant
     *          false otherwise
     */
    public boolean canPlaceToken(Square s) {
        
        return isSquareValid(s) && isSquareVacant(s);
    }
    
    /**
     * Checks to see if a row of Squares contains at least one vacant Square.
     * 
     * @author  David Hemming
     * @param   x the x-axis of the row of Squares to interrogate
     * @return  true if at least one of the Squares in the row is vacant
     *          false otherwise
     */
    public boolean canPlaceTokenInRow(int x) {
        
        for (Square s : getRow(x)) {
            if (isSquareVacant(s))
                return true;
        }
        
        return false;
    }
    
    /**
     * Checks to see if a column of Squares contains at least one vacant Square.
     *
     * @author  David Hemming
     * @param y the y-axis of the column of Squares to interrogate
     * @return  true if at least one of Squares in the column is vacant
     *          false otherwise
     */
    public boolean canPlaceTokenInCol(int y) {
        
        for (Square s : getCol(y)) {
            if (isSquareVacant(s))
                return true;
        }
        
        return false;
    }
    
    /**
     * Checks to see if a supplied x-axis is a valid row on the Board.
     *
     * @author  David Hemming
     * @param   x the x-axis of the row to check
     * @return  true if a row is matched by the supplied x-axis
     *          false otherwise
     */
    public boolean isRowValid(int x) {
        
        return x >= 0 && x <= ROWS;
    }
    
    /**
     * Checks to see if x-axis is a valid column on the Board.
     *
     * @author  David Hemming
     * @param y the y-axis of the column to check
     * @return  true if a column is matched by the supplied y-axis
     *          false otherwise
     */
    public boolean isColValid(int y) {
        
        return y >= 0 && y <= COLS;
    }
        
    /**
     * Checks to see if a supplied Square contains a valid x and y coordinate.
     *
     * @author  David Hemming
     * @param   s the Square to check the x y axis of
     * @return  true if both the x and y axis are valid and exist on the board
     *          false otherwise
     */
    public boolean isSquareValid(Square s) {
        
        return isRowValid(s.getXcoord()) && isColValid(s.getYcoord());
    }
    
    
    /**
     * Attempts to place a supplied Token on Square contained within the Board.
     * 
     * @author  David Hemming
     * @param s a Square that contains a valid x and y coordinate
     * @param t a Token to place in the Square contained within the Board
     * @return  true if Token was successfully placed on the desired Square
     *          false otherwise
     */
    boolean placeToken(Square s, Token t) {
        
        if (canPlaceToken(s.getXcoord(), s.getYcoord())) {
            getSquare(s.getXcoord(), s.getYcoord()).setToken(t);
            
            return true;
        }
        
        return false;
    }
    
    /**
     * Returns a List containing a row of Squares that lie on a supplied x-axis.
     *
     * @author  David Hemming
     * @param x the x-axis of the row of Squares to retrieve
     * @return  a row of Squares from the Board
     */
    public List<Square> getRow(int x) {
        
        return squareGrid.get(x);
    }
    
    /**
     * Returns a List containing a column of Squares that lie on a supplied
     * y-axis.
     *
     * @author  David Hemming
     * @param y the y-axis of the column of Squares to retrieve
     * @return  a column of Squares from the Board
     */
    public List<Square> getCol(int y) {
        List<Square> col = new ArrayList<>();
        
        for (int i = 0; i < COLS; i++) {
            col.add(getSquare(i, y));
        }
        
        return col;
    }
    
    /**
     * Returns a List containing a diagonal sequence of Squares from the Board.
     *
     * @author  David Hemming
     * @param y the y-axis of the diagonal, either 0, or the last column
     * @return  either one of two possible diagonal sequences of Squares
     */
    public List<Square> getDiagonal(int y) {
        List<Square> diagonal = new ArrayList<>();
        int col = y;
        
        for (int row = 0; row < ROWS; row++) {
           
           diagonal.add(getSquare(row, col));
           if (y == 0)
                col++;
            else
                col--;
        }
        
        return diagonal;
    }
    
    /**
     * Loops through the Squares and creates a List of Squares that are vacant
     * before returning the newly compiled List.
     *
     * @author  David Hemming
     * @return  a List of Squares that are vacant
     */
    public List<Square> getVacantSquareList() {
        List<Square> vacantSquareList = new ArrayList<>();
        
        for (List<Square> row : squareGrid) {
            for (Square s : row) {
                if (isSquareVacant(s))
                    vacantSquareList.add(s);
            }
        }
        
        return vacantSquareList;
    }
    
    /**
     * Checks to see if a supplied x y location on the Board contains a Square
     * that is vacant.
     *
     * @author  David Hemming
     * @param x the x-axis of the Squares location to interrogate
     * @param y the y-axis of the Squares location to interrogate
     * @return  true if the Square is vacant
     *          false otherwise
     */
    public boolean isSquareVacant(int x, int y) {
        return getSquare(x,y).getToken() == null;
    }
    
    /**
     * Checks to see if a supplied Squares location matches a Squares location
     * on the board that is vacant.
     *
     * @author          David Hemming
     * @param square    the Square containing an x y axis in which to check for
     *                  a vacancy
     * @return          true if the Squares x y location on the Board is vacant
     *                  false otherwise
     */
    public boolean isSquareVacant(Square square) {
        return getSquare(square.getXcoord(), square.getYcoord()).getToken() == null;
    }
    
    /**
     * Returns a Square from the Board located at a supplied x y axis.
     *
     * @author  David Hemming
     * @param x the x-axis of the Square to retrieve
     * @param y the y-axis of the Square to retrieve
     * @return  a Square that is located at the coordinates x y
     */
    public Square getSquare(int x, int y) {
        return squareGrid.get(x).get(y);
    }
    
    /**
     *
     * @return  the number of rows on the Board
     */
    public int getRows() {
        return ROWS;
    }
    
    /**
     *
     * @return  the number of columns on the Board
     */
    public int getCols() {
        return COLS;
    }
    
}
