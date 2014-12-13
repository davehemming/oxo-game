package oxogame.userinterface;

import oxogame.player.Player;
import util.UIListener;

/**
 * This interface extends the UIListener interface by adding the ability for 
 * the UI to let a Controller know that a new Player has been created, and 
 * sending it the newly created Player
 *
 * @author David Hemming
 */
public interface OXOGameUIListener extends UIListener {

    public void newPlayer(Player player);
}
