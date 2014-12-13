package oxogame.game;

import java.io.IOException;
import java.util.*;

import oxogame.board.Board;
import oxogame.board.BoardManager;
import oxogame.board.BoardListener;
import oxogame.dataaccess.DataAccess;
import oxogame.player.HumanPlayer;
import oxogame.player.Player;
import oxogame.player.PlayerManager;
import oxogame.player.SystemPlayer;
import oxogame.token.Token;
import oxogame.token.TokenManager;
import oxogame.userinterface.OXOGameUIListener;
import oxogame.userinterface.TextInterface;

/**
 * A controller class that coordinates a Game of Tic Tac Toe.
 * 
 * @author David Hemming
 */
public class Game implements OXOGameUIListener, BoardListener {

	private List<Player> testPlayers;
	private final int PLAYERS_REQUIRED = 2;
	private BoardManager boardManager;
	private TokenManager tokenManager;
	private PlayerManager playerManager;
	private TextInterface view;

	/**
	 * 
	 * @param view
	 *            the view layer in which this controller class communicates
	 *            through
	 */
	public Game(TextInterface view) {
		this.view = view;
		view.setOXOGameUIListener(this);
		boardManager = new BoardManager(this);
		tokenManager = new TokenManager();
		playerManager = new PlayerManager();
		tokenManager.addToken(new Token('X'));
		tokenManager.addToken(new Token('O'));
	}

	/**
	 * Initiates a game of Tic Tac Toe by checking that it has enough Players.
	 * If it does not have enough Players it sends a request to the view layer,
	 * otherwise it starts the game.
	 * 
	 * @author dave
	 */
	public void init() {

		ArrayList<String> playerData = new ArrayList<String>();

		if (playerManager.size() < PLAYERS_REQUIRED) {

			try {
				playerData = DataAccess.readData();

				if (playerData == null || playerData.isEmpty()
						|| playerData.get(0).isEmpty()
						|| playerData.get(0).charAt(0) == ' '
						|| !Character.isLetter(playerData.get(0).charAt(0))) {
					view.requestPlayer(tokenManager.getAvailableTokenList());
				} else {
					view.requestPlayer(playerData,
							tokenManager.getAvailableTokenList());
				}
			} catch (Exception e) {
				try {
					view.requestPlayer(tokenManager.getAvailableTokenList());
				} catch (Exception e1) {
					view.renderErrorMessage(e1.getMessage());
				}
			}
		}

		if (playerManager.size() == PLAYERS_REQUIRED) {
			if (view.promptUserForNewGame()) {
				run();
			} else {
				quit();
			}
		}
	}

	/**
	 * The main process which runs each game.
	 * 
	 * @author dave
	 */
	public void run() {

		while (true) {

			try {
				Board board = boardManager.getBoard();
				Player player = playerManager.getNextPlayer();
				MoveEvent playerMove;

				// Renders information about the current state of the Board to
				// to the View layer if the Player whos turn it is is a Human
				// (user)
				if (player instanceof HumanPlayer) {
					view.renderHeader("YOUR MOVE "
							+ player.getName().toUpperCase() + " ("
							+ player.getToken().toString() + ")");
					view.renderBoard(boardManager.getBoard());
				}

				// Gets the Players move, loops until the move is valid
				do {
					playerMove = player.getPlayerMove(board);
				} while (!board.canPlaceToken(playerMove.getSquare()));

				boardManager.playerMove(playerMove);

			} catch (Exception ex) {
			}

		}
	}

	/**
	 * Actions a Game Won event.
	 * 
	 * @param winningPlayer
	 */
	@SuppressWarnings("unchecked")
	private void gameWon(Player winningPlayer) {
		if (winningPlayer != null) {

			playerManager.updatePlayerStats(winningPlayer);

			view.renderHeader("GAME OVER");
			view.renderBoard(boardManager.getBoard());
			// inform the user about who the winning Player is
			view.renderMessage(winningPlayer.getName() + " ("
					+ winningPlayer.getToken() + ") Wins The Game!");
			try {
				view.renderPlayerStats(playerManager.getPlayersList());
			} catch (IOException e) {
				view.renderErrorMessage("Unable to Open/Create playerData! Stats Not Saved!");
			}
			resetGame();

			// Ask the user if they want to play another game
			if (view.promptUserForNewGame()) {
				run();
			} else {
				quit();
			}
		}
	}

	/**
	 * Actions a Game Drawn Event
	 * 
	 * @author dave
	 */
	private void gameDrawn() {
		playerManager.updatePlayerStats();

		view.renderHeader("GAME OVER");
		view.renderBoard(boardManager.getBoard());
		view.renderMessage("Game is a Stalemate!");
		try {
			view.renderPlayerStats(playerManager.getPlayersList());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resetGame();

		if (view.promptUserForNewGame()) {
			run();
		} else {
			quit();
		}
	}

	/**
	 * Resets the board ready for a new Game
	 */
	private void resetGame() {
		boardManager.resetBoard();
	}

	/**
	 * Adds a new Player to the PlayerManager. This method is called by the view
	 * layer when the user has created a new Player and wants to add them to the
	 * Game.
	 * 
	 * @author dave
	 * @param player
	 *            the new Player to add to the Game
	 */
	@Override
	public void newPlayer(Player player) {
		if (playerManager.addPlayer(player)) {
			// inform the user that the Player was successfully added
			view.renderMessage(player.getName()
					+ " joins the game using token '"
					+ player.getToken().toString() + "'");
			init();
		}
	}

	/**
	 * This event is called by the BoardManager to inform the Game that a row on
	 * the Board contains three Tokens of the same type.
	 * 
	 * @author dave
	 * @param t
	 *            the Token that has achieved Three-In-A-Row
	 */
	@Override
	public void threeInARowAchieved(Token t) {
		gameWon(t.getPlayer());
	}

	/**
	 * This event is called by the BoardManager to inform the Game that the
	 * Board is full and cannot take any more Tokens.
	 * 
	 * @author dave
	 * 
	 */
	@Override
	public void boardIsFull() {
		gameDrawn();
	}

	/**
	 * This event is called by both this class and the View class to quit the
	 * Game and Application.
	 * 
	 * @author dave
	 * 
	 */
	@Override
	public void quit() {
		view.renderQuitMessage();
		System.exit(0);
	}

}
