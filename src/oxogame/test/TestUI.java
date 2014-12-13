package oxogame.test;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import oxogame.player.HumanPlayer;
import oxogame.player.Player;
import oxogame.player.SystemPlayer;
import oxogame.token.Token;
import oxogame.userinterface.TextInterface;

/**
 * This class provides a User Interface for a person doing Testing on the 
 * application.
 *
 * @author David Hemming
 */
public class TestUI extends TextInterface {
    
    // helps determine which Test Player to create
    private int playerRequests;
    
    public TestUI() {
        playerRequests = 0;
    }

    /**
     * Speeds up testing of the application by automatically creating Players 
     * for the Game when the application starts.
     *
     * @author dave
     * @param availableTokens   the Tokens that are available for Player use
     */
    @Override
    public void requestPlayer(List<Token> availableTokens) {
        
        Player player = null;
        
        if (playerRequests == 0) {
            try {
                player = new HumanPlayer("Dave", availableTokens.get(0), this);
            } catch (Exception ex) {
                Logger.getLogger(TestUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } else if (playerRequests == 1) {
            try {
                player = new SystemPlayer("HAL", availableTokens.get(0));
            } catch (Exception ex) {
                Logger.getLogger(TestUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        if (player != null) {
            playerRequests++;
            fireNewPlayerEvent(player);
        }
    }
    
    /**
     * Automatically starts a new Game when queried by the Controller.
     * 
     * @return true
     */
    @Override
    public boolean promptUserForNewGame() {
        return true;
    }
    
}
