package util;

import java.util.ResourceBundle;

/**
 * This class provides various Strings used throughout the program. Such Strings
 * may be used in either a command prompt, as error messages, or exceptions. It
 * provides a way to both separate Strings from the code, and also to ensure 
 * that commonly used Strings that might be used frequently in different parts 
 * of the application are only written once and as such are easy to change in 
 * the one location.
 *
 * @author David Hemming
 */
public class Strings {

    /**
     * Provides various strings that are used in a command prompt type setting
     *
     * @author dave
     */
    public enum Prompt {
        GET_PLAYER_NAME,
        SELECT_PLAYER_TOKEN,
        GET_PLAYER_MOVE_X_AXIS,
        GET_PLAYER_MOVE_Y_AXIS;
        
        private static final ResourceBundle rb
                = ResourceBundle.getBundle("util.prompts");
        
        /**
         *
         * @return  the desired String
         */
        @Override
        public String toString() {
            return rb.getString(name());
        }
        
    }
    
    /**
     * Provides various Strings that are used as error messages throughout the
     * program
     * 
     * @author dave
     *
     */
    public enum Error {
        INVALID_INPUT,
        INVALID_SELECTION,
        INVALID_PLAYER_NAME;
        
        private static final ResourceBundle rb
                = ResourceBundle.getBundle("util.Errors");
        
        /**
         *
         * @return  the desired String
         */
        @Override
        public String toString() {
            return rb.getString(name());
        }
    }
    
    /**
     * Provides various Strings that are used as exception messages throughout 
     * the program
     * 
     * @author dave
     * 
     */
    public enum Exception {
        INVALID_PLAYER_NAME,
        UNAVAILABLE_TOKEN,
        NULL_PLAYERS,
        DUPLICATE_PLAYERS,
        QUIT_KEYWORD_ENTERED;
        
        private static final ResourceBundle rb
                = ResourceBundle.getBundle("util.Exceptions");
        
        /**
         *
         * @return the desired String
         */
        @Override
        public String toString() {
            return rb.getString(name());
        }
    }
    
    
}
