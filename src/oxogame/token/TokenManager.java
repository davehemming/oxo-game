package oxogame.token;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that manages the game Tokens.
 *
 * @author Dave
 */
public class TokenManager {
    
    private List<Token> tokenList;
    
    public TokenManager() {
        tokenList = new ArrayList<>();
    }

    /**
     *
     * @param t
     */
    public void addToken(Token t) {
        tokenList.add(t);
    }
    
    /**
     *
     * @return
     */
    public List<Token> getTokenList() {
        return tokenList;
    }
    
    /**
     * Creates a List of Tokens that do not belong to any Player.
     *
     * @return              a List of available Tokens
     * @throws Exception
     */
    public List<Token> getAvailableTokenList() throws Exception
    {
        List<Token> availableTokens = new ArrayList<>();
        
        for (Token t: tokenList) {
            if (t.getPlayer() == null) {
                availableTokens.add(t);
            }
        }
        
        if (availableTokens.isEmpty())
            throw new Exception ("There are no available tokens");
        
        return availableTokens;
    }
    
}
