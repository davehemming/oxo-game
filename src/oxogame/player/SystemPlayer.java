package oxogame.player;

import java.util.List;
import java.util.Random;
import oxogame.board.Board;
import oxogame.board.Square;
import oxogame.game.MoveEvent;
import oxogame.token.Token;

/**
 * A class that represents a System Player.
 *
 * @author Dave
 */
public class SystemPlayer extends Player {
    
    /**
     *
     * @param name          the Players name
     * @param token         the Players chosen Token
     * @throws Exception
     */
    public SystemPlayer(String name, Token token) throws Exception {
        super(name, token);
    }
    
    /**
     * Gets the SystemPlayer's move. At the moment it just gets a List of 
     * vacant squares and then randomly selects one.
     *
     * @param board
     * @return
     */
    @Override
    public MoveEvent getPlayerMove(Board board) {
//        String random = list.get(randomizer.nextInt(list.size()));
        Square square;
        List<Square> vacantSquareList = board.getVacantSquareList();
        
        square = vacantSquareList.get(new Random().nextInt(vacantSquareList.size()));
        
        return new MoveEvent(square, token);
        
//        return new MoveEvent(new Square(1,1), token);
    }
    
    
    
}
