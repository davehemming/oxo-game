package oxogame.player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A class that manages Players of the game.
 *
 * @author David Hemming
 */
public class PlayerManager {
    
    private static Iterator<Player> iterator;
    private List<Player> playerList;
    
    public PlayerManager() {
        playerList = new ArrayList<>();
    }
    
    /**
     * Adds a Player to the Manager.
     *
     * @param p the Player to add
     * @return  true if the Player was successfully added
     *          false otherwise
     */
    public boolean addPlayer(Player p) {
        if (p != null) {
            return playerList.add(p);
        }
       
        return false;
    }
    
    public List getPlayersList() {
        return playerList;
    }
    
    public int size() {
        return playerList.size();
    }
    
    /**
     * Updates the Player statistics after a Game in which one of the Players
     * was declared a winner.
     *
     * @param winningPlayer the Player that won the game
     * @throws IOException 
     */
    public void updatePlayerStats(Player winningPlayer){
        
        for (Player p : playerList) {
            if (p == winningPlayer)
            {
                p.incrementWins();
            }
            else
            {
                p.incrementLosses();
            }
        }
    }
    
    /**
     * Updates the Player statistics after a Game that was declared a draw
     *
     */
    public void updatePlayerStats() {
        
        for (Player p : playerList) {
            p.incrementDrawn();
        }
    }
        
    /**
     * Returns a Player who's turn it is.
     *
     * @return              the Player who's turn it is to make a Move
     * @throws Exception
     */
    public Player getNextPlayer() throws Exception {
        
        if (playerList.isEmpty())
            throw new Exception("Players list is empty");
        
        if (iterator == null || !iterator.hasNext()) {
            iterator = playerList.iterator();
        }
        
        return iterator.next();
    }
    
}
