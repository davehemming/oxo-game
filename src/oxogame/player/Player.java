package oxogame.player;

import java.util.ArrayList;
import java.util.List;
import oxogame.board.Board;
import oxogame.game.MoveEvent;
import oxogame.token.Token;

/**
 * A class that represents a Player that takes turns to place Tokens on the 
 * Board.
 *
 * @author David Hemming
 */
public abstract class Player {

    // A sequence that automatically increments with each new Player 
    // registration and is used to generate Player IDs
    private static int playerSequence = 1;
    // A list of Player names. Used to prevent two Players from sharing the 
    // same name
    private static List<String> playerNames = new ArrayList<>();

    protected int playerID;
    protected String name;
    protected Token token;
    
    // Player statistics
    private int wins;
    private int losses;
    private int drawn;

    /**
     *
     * @param name          the name of the Player
     * @param token         the Players chosen Token
     * @throws Exception
     */
    public Player(String name, Token token) throws Exception {

        if (!isNameValid(name)) {
            throw new Exception("Player Name is invalid");
        }
        
        if (!isNameUnique(name)) {
            throw new Exception("Player Name already exists");
        }

        this.name = name;
        this.token = token;

        setToken(token);
        playerNames.add(name);
        this.playerID = playerSequence++;
        
        wins = 0;
        losses = 0;
        drawn = 0;

//        this.token.setPlayer(this);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the token
     */
    public Token getToken() {
        return token;
    }

    /**
     * @param token the token to set
     * @throws java.lang.Exception
     */
    public void setToken(Token token) throws Exception {

        token.setPlayer(this);
        this.token = token;

    }

    /**
     * @return the playerID
     */
    public int getPlayerID() {
        return playerID;
    }

    /**
     * @return the playerSequence
     */
    public static int getNextPlayerSequence() {
        return playerSequence;
    }
    
    /**
     * Checks to see if a Player name is valid.
     *
     * @param name  the proposed player name
     * @return      true if the Player name is valid
     *              false otherwise
     */
    public static boolean isNameValid(String name) {
        // Note: this would be a better method if Regex were implemented
        return name != null && !name.equals("") && !name.equals(" ");
    }
    
    /**
     * Checks to see if the Player name has already been registered.
     *
     * @param name  the name to check for uniqueness
     * @return      true if the name is unique
     *              false otherwise
     */
    public static boolean isNameUnique(String name) {
        return !playerNames.contains(name);
    }
    
    /**
     * Gets a Players desired move.
     *
     * @param board the board object, sent to the Player object so that Player
     *              can choose a suitable Square in which to place their Token
     * @return      a MoveEvent containing all the information of the Players
     *              desired Move
     */
    public abstract MoveEvent getPlayerMove(Board board);

    public void incrementWins() {
        this.wins++;
    }

    public void incrementLosses() {
        this.losses++;
    }

    public void incrementDrawn() {
        this.drawn++;
    }
    
    public void setWins(int wins) {
		this.wins = wins;
	}

	public int getWins() {
		return wins;
	}

	public int getLosses() {
		return losses;
	}

	public int getDrawn() {
		return drawn;
	}

	public void setLosses(int losses) {
		this.losses = losses;
	}

	public void setDrawn(int drawn) {
		this.drawn = drawn;
	}

	/**
     * Constructs a String containing the Players information and statistics.
     *
     * @return  a String with Player name and statistics.
     */
    @Override
    public String toString() {
        return token.toString() + ": " + name + "..." + wins + " Wins, " + losses + " Losses, " +
                drawn + " Drawn";
    }

}
