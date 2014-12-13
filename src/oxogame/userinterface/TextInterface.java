package oxogame.userinterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import oxogame.board.Board;
import oxogame.board.Square;
import oxogame.dataaccess.DataAccess;
import oxogame.game.MoveEvent;
import oxogame.player.HumanPlayer;
import oxogame.player.Player;
import oxogame.player.PlayerType;
import oxogame.player.SystemPlayer;
import oxogame.token.Token;
import oxogame.token.TokenManager;
import util.Strings;
import util.TextUI;

/**
 * This class provides a User Interface for the OXO application.
 * 
 * @author David Hemming
 */
public class TextInterface extends TextUI implements
		HumanPlayerControlInterface {

	/**
	 * Prints a banner and intro to the program to the console
	 * 
	 */
	public void renderBanner() {
		printHeader("TIC TAC TOE", TEXT_ALIGN.CENTER);
		println();
		println("To exit type 'quit' at any time");
	}

	/**
	 * Displays the statistics of the Players
	 * 
	 * @author dave
	 * @param players
	 *            the players who's statistics are to be printed to the console
	 * @throws IOException 
	 */
	public void renderPlayerStats(List<Player> players) throws IOException {
		
		String[] playerData;

		if (players != null) {
			println();
			printHeader("PLAYER STATS");
			println();
			for (Player p : players) {
				DataAccess.searchAndWritePlayer(p);
			}
			for (String line : DataAccess.readData()) {
				playerData = line.split(";");
				println(playerData[0]+"..."+playerData[1]+" Wins, "+playerData[2]+" Losses, "+playerData[3]+" Drawn");
			}
		}

	}

	/**
	 * Displays a message upon the user quitting the program
	 * 
	 */
	public void renderQuitMessage() {
		println();
		println("Goodbye");
	}

	/**
	 * Displays a specially formatted message
	 * 
	 * @author dave
	 * @param msg
	 *            the message to format and display
	 */
	public void renderMessage(String msg) {

		println();
		printMessage("New Message:", msg, TEXT_ALIGN.CENTER);
	}

	/**
	 * Displays an error message
	 * 
	 * @author dave
	 * @param error
	 *            the text of the error that is to be displayed
	 */
	public void renderErrorMessage(String error) {
		println();
		printError(error);
	}

	/**
	 * Displays the OXO Board in the console
	 * 
	 * @author dave
	 * @param board
	 *            the board to render to the console
	 */
	public void renderBoard(Board board) {
		String b = "";

		b += "\n";
		for (int i = 0; i < board.getCols(); i++) {
			b += getRenderedRow(board, i);
		}

		b = appendPreStringToNewLines(b);
		println(b);

	}

	/**
	 * Displays a specially formatted header in the console
	 * 
	 * @author dave
	 * @param headerText
	 *            the text that is to be formatted and dispalyed in the header
	 */
	public void renderHeader(String headerText) {
		println();
		printHeader(headerText);
	}

	/**
	 * Selects a row from the Board as directed by the x-axis parameter, and
	 * formats it to be correctly displayed in a Rendered board.
	 * 
	 * @author dave
	 * @param board
	 *            the board that contains the desired row
	 * @param x
	 *            the x-axis in which the row lies
	 * @return the row which has been properly rendered for display in the
	 *         console
	 */
	private String getRenderedRow(Board board, int x) {
		String r = "";
		List<Square> row = board.getRow(x);

		// if first row in Board include the top border of the row
		if (x == 0) {

			r += "   Y   ";

			// print the Y coordinates
			for (int i = 0; i < board.getCols(); i++) {
				r += i + "       ";
			}

			r += "\n";
			r += "X  ";

			for (int i = 0; i < board.getCols(); i++) {
				r += " _______";
			}
			r += "\n";
		}

		// top part of the row
		r += "   |";
		for (Square s : row) {
			r += "       |";

		}

		// middle part of the row
		r += "\n";
		r += x;
		r += "  |";
		for (Square s : row) {
			// render the Tokens symbol if the current Square has a Token
			if (s.hasToken()) {
				r += "   " + s.getToken().toString() + "   |";
			} else {
				r += "       |";
			}

		}

		// the bottom part of the row
		r += "\n";
		r += "   |";
		for (Square s : row) {
			r += "_______|";
		}

		r += "\n";

		return r;
	}

	/**
	 * Used by the Controller to request that the user create and submit another
	 * Player to the Game.
	 * 
	 * @author dave
	 * @param availableTokens
	 */
	public void requestPlayer(List<Token> availableTokens) {
		Player player;

		player = createPlayer(availableTokens);
		// when Player is created let the Controller (as a registered Listener
		// to this class) know
		fireNewPlayerEvent(player);
	}

	/**
	 * Used by the Controller to request that the user create and submit another
	 * Player to the Game.
	 * 
	 * @author leo
	 * @param availableTokens
	 * @param playersList
	 */
	public void requestPlayer(ArrayList<String> playersList,
			List<Token> availableTokens) {

		Player player;
		String[] splitLine;
		TreeMap<Integer, String> players = new TreeMap<>();

		println();
		printHeader("PLAYER SELECT", TEXT_ALIGN.LEFT);
		println();

		for (int i = 0; i < playersList.size(); i++) {
			splitLine = playersList.get(i).split(";");

			players.put(i + 1, splitLine[0]);
		}
		players.put(playersList.size() + 1, "New Player");

		println();
		displayMenu(players);
		println();

		int selection = getMenuSelection("Select Player", players);

		if (selection <= playersList.size()) {
			String selectedPlayer = playersList.get(selection - 1);
			player = createPlayer(selectedPlayer, availableTokens);
		} else {
			player = createPlayer(availableTokens);
		}

		// when Player is created let the Controller (as a registered Listener
		// to this class) know
		fireNewPlayerEvent(player);
	}

	/**
	 * Registers a Listener object to this class. This is likely going to be the
	 * main Controller, which will be interested in certain events that occur in
	 * the user interface
	 * 
	 * @author dave
	 * @param oxoGameUIListener
	 *            the object that is interested in certain events occurring in
	 *            this class
	 */
	public void setOXOGameUIListener(OXOGameUIListener oxoGameUIListener) {
		this.uiListener = oxoGameUIListener;
	}

	/**
	 * Upon creating a new Player, let the Listener know and send them the newly
	 * created Player.
	 * 
	 * @author dave
	 * @param player
	 *            the newly created Player
	 */
	protected void fireNewPlayerEvent(Player player) {
		if (uiListener != null) {
			((OXOGameUIListener) uiListener).newPlayer(player);
		}
	}

	/**
	 * Walks the user through the process of creating a new Player for the game
	 * 
	 * @author dave
	 * @param availableTokens
	 *            the Tokens that are available for a new Player to use
	 * @return the newly created Player
	 */
	protected Player createPlayer(List<Token> availableTokens) {
		PlayerType playerType;
		String playerName;
		Token playerToken;
		Player player = null;

		println();
		printHeader("PLAYER " + Player.getNextPlayerSequence()
				+ " REGISTRATION", TEXT_ALIGN.LEFT);
		println();

		while (player == null) {
			try {
				playerType = promptUserForPlayerType();
				playerName = promptUserForPlayerName();
				playerToken = promptUserForPlayerToken(availableTokens);

				if (playerType == PlayerType.HUMAN_PLAYER) {
					player = new HumanPlayer(playerName, playerToken, this);
				} else {
					player = new SystemPlayer(playerName, playerToken);
				}

			} catch (Exception e) {
				printError(e.getMessage());
			}
		}

		return player;
	}

	/**
	 * Walks the user through the process of creating a new Player for the game
	 * 
	 * @author leo
	 * @param availableTokens
	 *            the Tokens that are available for a new Player to use
	 * @param selectedPlayer
	 * @return the newly created Player
	 */
	protected Player createPlayer(String selectedPlayer,
			List<Token> availableTokens) {

		String playerName;
		Token playerToken;
		Player player = null;
		String[] playerData = selectedPlayer.split(";");

		while (player == null) {
			try {
				playerName = playerData[0];
				playerToken = promptUserForPlayerToken(availableTokens);

				player = new HumanPlayer(playerName, playerToken, this);
				try {
					player.setWins(Integer.parseInt(playerData[1]));
				} catch (NumberFormatException e) {
					player.setWins(0);
				}
				try {
					player.setDrawn(Integer.parseInt(playerData[2]));
				} catch (NumberFormatException e) {
					player.setDrawn(0);
				}
				try {
					player.setLosses(Integer.parseInt(playerData[3]));
				} catch (NumberFormatException e) {
					player.setLosses(0);
				}
			} catch (Exception e) {
				printError(e.getMessage());
			}
		}

		return player;
	}

	/**
	 * Gets a decision from the user about whether they want to create either a
	 * new Human Player or System Player
	 * 
	 * @author dave
	 * @return the type of Player the user wishes to create
	 */
	public PlayerType promptUserForPlayerType() {

		TreeMap<Integer, String> playerTypes = new TreeMap<>();
		playerTypes.put(1, "Human Player");
		playerTypes.put(2, "System Player");

		println();
		displayMenu(playerTypes);
		println();
		int selection = getMenuSelection("Select Player Type", playerTypes);

		if (selection == 1) {
			return PlayerType.HUMAN_PLAYER;
		} else {
			return PlayerType.SYSTEM_PLAYER;
		}
	}

	/**
	 * Gets the user to enter a valid name for the new Player.
	 * 
	 * @author dave
	 * @return the valid name that is to be used for a new Player
	 */
	protected String promptUserForPlayerName() {
		boolean valid = false;
		String name = null;

		while (!valid) {
			println();
			name = getStringInput("Name");

			if (!Player.isNameValid(name)) {
				printError("Player Name is Not Valid, try again...");
			} else if (!Player.isNameUnique(name)) {
				printError("Player Name already exists, try again...");
			} else {
				valid = true;
			}
		}

		return name;

	}

	/**
	 * Displays a list of available Tokens and gets the user to choose one that
	 * will be used by the new Player
	 * 
	 * @author dave
	 * @param availableTokens
	 *            the list of available Tokens
	 * @return the users chosen Token
	 */
	protected Token promptUserForPlayerToken(List<Token> availableTokens) {

		Token playerToken = null;

		if (availableTokens.size() > 1) {
			TreeMap<Integer, String> menuItemsMap = new TreeMap<>();
			for (int i = 0; i < availableTokens.size(); i++) {
				menuItemsMap.put(i + 1, availableTokens.get(i).toString());
			}

			println();
			displayMenu(menuItemsMap);
			println();
			int selection = getMenuSelection("Token", menuItemsMap);
			playerToken = availableTokens.get(selection - 1);

		} else if (availableTokens.size() == 1) {
			playerToken = availableTokens.get(0);
		}

		return playerToken;

	}

	/**
	 * This method is used by the HumanPlayer class to get the users next move.
	 * It asks for a players move in the form of x y coordinates, checks the
	 * move is valid, and then returns the players desired move in the form of a
	 * MoveEvent.
	 * 
	 * @author dave
	 * @param player
	 *            the Player who's move it is
	 * @param board
	 *            the Board which is used by the method to check that the
	 *            Players move is valid
	 * @return the MoveEvent which contains the user desired move information
	 */
	@Override
	public MoveEvent promptPlayerForNextMove(Player player, Board board) {
		boolean valid = false;
		String coordinates;
		int x = -1;
		int y = -1;

		while (!valid) {
			coordinates = getStringInput("x,y");

			// coordinates can be in the form of xy
			if (coordinates.length() == 2) {
				// check supplied coordinates are valid
				if (canParseInt(coordinates.substring(0, 1))
						&& canParseInt(coordinates.substring(1, 2))) {
					x = parseInt(coordinates.substring(0, 1));
					y = parseInt(coordinates.substring(1, 2));
					valid = true;
				}

				// or coordinates can be in the form of x,y
			} else if (coordinates.length() == 3) {
				if (canParseInt(coordinates.substring(0, 1))
						&& canParseInt(coordinates.substring(2, 3))) {
					x = parseInt(coordinates.substring(0, 1));
					y = parseInt(coordinates.substring(2, 3));
					valid = true;
				}
			}

			if (valid && board.isRowValid(x) && board.isColValid(y)) {

				if (!board.canPlaceToken(x, y)) {
					printError("Selected location already contains a token, try again...");
					valid = false;
				}
			} else {
				printError(Strings.Error.INVALID_INPUT.toString());
				valid = false;
			}

		}

		return new MoveEvent(board.getSquare(x, y), player.getToken());

	}

	/**
	 * Ask the user if they want to start a new game.
	 * 
	 * @author dave
	 * @return true if the user does want to start a new game false otherwise
	 */
	public boolean promptUserForNewGame() {

		println();
		return getBooleanInput("Start New Game (y/n)");
	}
}
