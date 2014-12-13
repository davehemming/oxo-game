package oxogame.token;

import oxogame.player.Player;

/**
 * A class that represents a Token.
 *
 * @author David Hemming
 */
public class Token {

    private final char symbol;
    // The Player that controls this Token, prevents more than one Player from
    // having the same Token (one-to-one)
    private Player player;

    /**
     *
     * @param symbol
     */
    public Token(char symbol) {
        this.symbol = symbol;
        this.player = null;
    }

    /**
     * Sets the Player that controls this Token and throws an Exception if 
     * this Token already belongs to another Player.
     *
     * @param player        the Player that wishes to control this Token
     * @throws Exception
     */
    public void setPlayer(Player player) throws Exception {

        // Prevent token from being used by more than 1 player
        if (this.player != null) {
            throw new Exception("Token belongs to another player");
        }

        this.player = player;
    }

    /**
     *
     * @return
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Resets the Player that controls this Token, so that it may be used by
     * another Player.
     * 
     * @author dave
     * 
     */
    public void resetPlayer() {
        if (player != null) {
            try {
                player.setToken(null);
            } catch (Exception e) {
            }
        }
        player = null;
    }

    /**
     *
     * @return the symbol that represents this Token
     */
    @Override
    public String toString() {
        return "" + symbol;
    }

}
