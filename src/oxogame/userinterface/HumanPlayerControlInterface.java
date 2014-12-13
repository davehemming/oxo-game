package oxogame.userinterface;

import oxogame.board.Board;
import oxogame.game.MoveEvent;
import oxogame.player.Player;

/**
 * This interface is used by the UI to render a request for a player move and 
 * return the players move to the HumanPlayer class
 *
 * @author David Hemming
 */
public interface HumanPlayerControlInterface {
    
    public MoveEvent promptPlayerForNextMove(Player player, Board board);
}
