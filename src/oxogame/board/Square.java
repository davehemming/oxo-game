
package oxogame.board;

import oxogame.token.Token;

/**
 * A class that represents a Square (location) on board which can either be 
 * vacant or occupied by a Players Token.
 *
 * @author David Hemming
 */
public class Square {
    
    private final int xcoord;
    private final int ycoord;
    private Token token;
    
    /**
     *
     * @param xcoord the x-coordinate of the Squares location on the board
     * @param ycoord the y-coordinate of the Squares location on the board
     */
    public Square(int xcoord, int ycoord) {
        this.xcoord = xcoord;
        this.ycoord = ycoord;
    }

    /**
     * @return the playerToken
     */
    public Token getToken() {
        return token;
    }

    /**
     * @param playerToken the playerToken to set
     */
    public void setToken(Token playerToken) {
        this.token = playerToken;
    }
    
    /**
     * Checks to see whether the Square is occupied by a Players Token
     *
     * @return true if the Square is occupied by a Token
     *         false otherwise
     */
    public boolean hasToken() {
        return token != null;
    }

    /**
     * @return the xcoord
     */
    public int getXcoord() {
        return xcoord;
    }

    /**
     * @return the ycoord
     */
    public int getYcoord() {
        return ycoord;
    }
    
}
