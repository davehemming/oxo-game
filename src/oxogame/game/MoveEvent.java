package oxogame.game;

import oxogame.board.Square;
import oxogame.token.Token;

/**
 * A class that represents a MoveEvent.  Essentially it is a Java Bean.
 *
 * @author David Hemming
 */
public class MoveEvent {
    
    private final Square square;
    private final Token token;
    
    /**
     *
     * @param square    a Square containing an x y location to place the Token
     * @param token     the players Token to be placed in the desired Board
     *                  location
     */
    public MoveEvent(Square square, Token token) {
        this.square = square;
        this.token = token;
    }

    /**
     * @return the token
     */
    public Token getToken() {
        return token;
    }

    /**
     * @return the square
     */
    public Square getSquare() {
        return square;
    }
    
    
}
