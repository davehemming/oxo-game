
package oxogame.board;

import oxogame.token.Token;

/**
 * Interface that allows the BoardManager to alert the BoardListener about 
 * certain events.
 *
 * @author Dave
 */
public interface BoardListener {
    
    public void threeInARowAchieved(Token t);
    public void boardIsFull();
}
