package oxogame;

import oxogame.game.Game;
import oxogame.test.TestUI;
import oxogame.userinterface.TextInterface;

/**
 * This class kicks off the application.
 * Note: Seems to be a couple of bugs during game play 11-10-2013 (Dave)
 *
 * @author David Hemming
 */
public class Application {

    public static void main(String[] args) {

        // Determines whether to run the application in Test Mode (using the 
        // TestUI) or normal mode (using the regular UI)
        final boolean isTestMode = false;
        TextInterface view;
        
        if (isTestMode) {
            view = new TestUI();
        } else {
            view = new TextInterface();
        }
        
        Game game = new Game(view);
        view.renderBanner();
        game.init();

    }

}
