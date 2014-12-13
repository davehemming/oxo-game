package oxogame.player;

import oxogame.board.Board;
import oxogame.game.MoveEvent;
import oxogame.token.Token;
import oxogame.userinterface.HumanPlayerControlInterface;

/**
 * A class that defines a Human Player.
 *
 * @author David Hemming
 */
public class HumanPlayer extends Player {
    
    private HumanPlayerControlInterface userMoveInterface;

    /**
     *
     * @param name                  the name of the Player
     * @param token                 the Players chosen Token
     * @param userMoveInterface     the interface which the class uses to 
     *                              get the users move
     * @throws Exception
     */
    public HumanPlayer(String name, Token token, 
            HumanPlayerControlInterface userMoveInterface) throws Exception {
        super(name, token);
        this.userMoveInterface = userMoveInterface;
    }

    @Override
    public MoveEvent getPlayerMove(Board board) {
        
        // send a message to the interface to ask the user controlling this
        // HumanPlayer object for their desired move
        return userMoveInterface.promptPlayerForNextMove(this, board);
    }

}
