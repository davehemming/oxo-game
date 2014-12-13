package util;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * A class that handles all the common functions necessary to receive input and
 * display messages and feedback to the user inside a console environment.
 * 
 * Note: Possible improvement would include using Reflection so that methods 
 * can accept validation methods as parameters.
 *
 * @author David Hemming
 */
public abstract class TextUI {

    /**
     * A Listener object which wants to be alerted when certain events occur in
     * the user interface.  Primarily used to alert a controller when the user 
     * has indicated their desire to quit the program.
     *
     */
    protected UIListener uiListener;

    private Scanner scan;
//    private Quit quit;
    // The keyword that the user will enter when they wish to quit the program.
    private String quitKeyword;
    // The String that is appended to all other Strings that are rendered to 
    // the console.  Used to create a more pleasing interface and maintain 
    // consistent formatting.
    private String preString;

    /**
     *
     */
    protected TextUI() {
        scan = new Scanner(System.in);
//        quit = new Quit();
        quitKeyword = "quit";
        preString = "\t";
    }

    /**
     * Actions a quit event by calling the Controllers quit method.
     *
     */
    private void fireQuitEvent() {
        if (uiListener != null) {
            uiListener.quit();
        }
//        uiListener.quit();
    }

//    protected void quit() {
////        quit.quit();
//        if (uiListener != null) {
//            uiListener.quit();
//        }
//    }

    /**
     * Applies basic validation to get a String input from the user.
     *
     * @author dave
     * @param prompt    the command prompt to display to the user 
     * @return          the validated String
     */
    protected String getStringInput(String prompt) {
        String input = "";

        do {
            print(prompt + ": ");
            input = scan.nextLine();

            if (input.equals("")) {
                printError(Strings.Error.INVALID_INPUT.toString());
            } else if (isQuitKeyword(input)) {
//                quit();
                fireQuitEvent();
            }

        } while (input.equals(""));

        return input;
    }

    /**
     * Applies basic validation to get a yes/no decision from the user.
     *
     * @author dave
     * @param prompt    the command prompt to display to the user
     * @return          true if yes or y
     *                  false if no or n
     */
    protected boolean getBooleanInput(String prompt) {
        String input;
        boolean valid;
        boolean booleanInput = true;

        ArrayList<String> positiveAffirmation = new ArrayList<>();
        positiveAffirmation.add("y");
        positiveAffirmation.add("yes");

        ArrayList<String> negativeAffirmation = new ArrayList<>();
        negativeAffirmation.add("n");
        negativeAffirmation.add("no");

        do {
            valid = true;
            input = getStringInput(prompt).toLowerCase();

            if (positiveAffirmation.contains(input)) {
                booleanInput = true;
            } else if (negativeAffirmation.contains(input)) {
                booleanInput = false;
            } else {
                printError(Strings.Error.INVALID_INPUT.toString());
                valid = false;
            }

        } while (!valid);

        return booleanInput;

    }

    /**
     * Checks if String is a valid integer.
     *
     * @author dave
     * @param s     the String to interrogate
     * @return      true if the supplied String is an integer
     *              false otherwise
     */
    protected boolean canParseInt(String s) {

        try {
            int i = Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }

    }

    /**
     * Attempts to convert the supplied String to an integer
     *
     * @param s the String to convert to an integer
     * @return  the converted integer contained within the String
     */
    protected int parseInt(String s) {
        int i = -1;

        try {
            i = Integer.parseInt(s);
        } catch (NumberFormatException ex) {
        }

        return i;
    }

    /**
     * Applies basic validation checking to get an integer input from the user.
     *
     * @author dave
     * @param prompt    the command prompt to display to the user.
     * @return          the valid integer value
     */
    protected int getIntInput(String prompt) {
        int intInput = -1;
        String input;
        boolean valid = false;

        do {
            print(prompt + ": ");
            input = scan.nextLine();

            try {
                intInput = Integer.parseInt(input);
                valid = true;
            } catch (NumberFormatException ex) {
                if (isQuitKeyword(input)) {
                    fireQuitEvent();
//                    quit();
                } else {
                    printError(Strings.Error.INVALID_INPUT.toString());
                }
            }
        } while (!valid);

        return intInput;
    }

    /**
     * Applies basic validation in order to get a menu selection from the user.
     *
     * @author dave
     * @param prompt        the command prompt to display to the user.
     * @param menuItemsMap  the menu items, used to validate that user input is
     *                      within the range
     * @return              an integer that represents an item within the menu
     */
    protected int getMenuSelection(String prompt, TreeMap<Integer, String> menuItemsMap) {
        int selection;
        boolean valid = false;

        do {
            selection = getIntInput(prompt);
            if (selection < menuItemsMap.firstEntry().getKey()
                    || selection > menuItemsMap.lastEntry().getKey()) {
                printError(Strings.Error.INVALID_SELECTION.toString());
            } else {
                valid = true;
            }

        } while (!valid);

        return selection;
    }

    /**
     * Displays a menu to the console.
     *
     * @author dave
     * @param menuItemsMap  the menu to display
     */
    protected void displayMenu(Map<Integer, String> menuItemsMap) {

        for (Integer i : menuItemsMap.keySet()) {
            println(i + ". " + menuItemsMap.get(i));
        }
    }

    /**
     * Checks the supplied String to see if it matches the quit keyword.
     * 
     * @author dave
     * @param word  the word suspected of being a quit keyword
     * @return      true if the String matches the quit keyword
     *              false otherwise
     */
    private boolean isQuitKeyword(String word) {
        return quitKeyword != null && word.equals(quitKeyword);
    }

    /**
     * Appends the pre-String to the new lines of a supplied String.
     *
     * @author dave
     * @param str   the String to append the pre-String characters to
     * @return      the String complete with the newly appended pre-Strings
     */
    protected String appendPreStringToNewLines(String str) {
        String nl = System.lineSeparator();

        str = str.replaceAll("[" + nl + "]", nl + preString);
        return str;
    }

    /**
     * Basic method to print a new line to the console. Useful as a shorter
     * version of System.out.println();
     *
     */
    protected void println() {
        System.out.println();
    }

    /**
     * Appends the pre-String to and prints a new line String to the console. 
     * Useful as a shorter version of System.out.println();
     *
     * @param line  the String to print to the console
     */
    protected void println(String line) {
        System.out.println(preString + line);
    }

    /**
     *
     */
    protected void print() {
        System.out.print(preString);
    }

    /**
     * Appends the pre-String and prints a String to the the console. Useful as 
     * a shorter version of System.out.print...
     *
     * @param line
     */
    protected void print(String line) {
        System.out.print(preString + line);
    }

    /**
     * Prints a divider to the console.
     *
     */
    protected void printDiv() {
        System.out.println(preString + "-----------------------------------------");
    }

    /**
     * Similar to print(String s), except it doesn't include the appending of 
     * the pre-String.
     *
     * @param line  the String to print to the console
     */
    protected static void printPlain(String line) {
        System.out.print(line);
    }

    /**
     * Appends the pre-String and prints an error message to the console. 
     * Useful as a shorter version of System.err.println...
     *
     * @param errorMessage
     */
    protected void printError(String errorMessage) {
        System.err.println(preString + errorMessage);
    }

    /**
     * Defines the possible alignment options available to some methods.
     *
     */
    protected enum TEXT_ALIGN {
        LEFT,
        CENTER
    }

    /**
     * Prints a left aligned header to the console.
     *
     * @param headerText    the text of the header
     */
    protected void printHeader(String headerText) {
        System.out.println(getHeader(headerText, TEXT_ALIGN.LEFT));
    }

    /**
     * Prints a header to the console
     *
     * @param headerText    the text of the header
     * @param textAlign     the alignment of the header text
     */
    protected void printHeader(String headerText, TEXT_ALIGN textAlign) {
        System.out.println(getHeader(headerText, textAlign));
    }

    /**
     * Constructs and returns a header item.
     * 
     * @author dave
     * @param headerText    the text of the header
     * @param textAlign     the alignment of the header text
     * @return              the newly constructed header item
     */
    private String getHeader(String headerText, TEXT_ALIGN textAlign) {
        final int HEADER_WIDTH = 50 - 2;
        final int TEXT_LENGTH = headerText.length();
        String s = "";

        s += preString + " " + getRepeatedCharString('_', HEADER_WIDTH) + " ";
        s += "\n";

        s += preString + "|" + getAlignedTextString(textAlign, HEADER_WIDTH, '_', headerText) + "|";

        return s;
    }

    /**
     * Constructs and displays a box contained message to the console.
     *
     * @author dave
     * @param messageHeader     the header text of the message
     * @param messageBody       the body text of the message
     * @param messageBodyAlign  the alignment of the message body text
     */
    protected void printMessage(String messageHeader, String messageBody, TEXT_ALIGN messageBodyAlign) {
        final int MESSAGE_WIDTH = 50 - 2;
        String s = "";

        s += getHeader(messageHeader, TEXT_ALIGN.LEFT);
        s += "\n";
        s += preString + "|" + getRepeatedCharString(' ', MESSAGE_WIDTH) + "|";
        s += "\n";
        s += preString + "|" + getAlignedTextString(messageBodyAlign, MESSAGE_WIDTH, ' ', messageBody) + "|";
        s += "\n";
        s += preString + "|" + getRepeatedCharString('_', MESSAGE_WIDTH) + "|";

        System.out.println(s);

    }

    /**
     * Constructs and returns a String that is aligned within a border.
     * 
     * @author dave
     * @param alignment     the desired alignment of the text
     * @param contentWidth  the width of the border around the text
     * @param padding       the left and right padding value
     * @param text          the text to align
     * @return              the newly aligned String
     */
    private String getAlignedTextString(TEXT_ALIGN alignment, int contentWidth, char padding, String text) {
        String s = "";

        switch (alignment) {
            case CENTER:
                final int PADDING_LENGTH = (contentWidth - text.length()) / 2;

                s += getRepeatedCharString(padding, PADDING_LENGTH);
                s += text;
                s += getRepeatedCharString(padding, (contentWidth - text.length() - PADDING_LENGTH));
                break;
            default:
                s += padding + text + getRepeatedCharString(padding, (contentWidth - 1 - text.length()));
                break;
        }

        return s;
    }

    /**
     * Iterates n times to produce a String containing the supplied character n
     * times.
     * 
     * @author dave
     * @param character the character to repeat n times
     * @param n         the number of times the character is to be added to the
     *                  String
     * @return          the newly constructed String containing the character
     *                  repeated n times
     */
    private String getRepeatedCharString(char character, int n) {
        String s = "";
        for (int i = 0; i < n; i++) {
            s += character;
        }

        return s;
    }

}
