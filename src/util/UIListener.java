package util;


/**
 * This interface is implemented by a Controller class that is interested in 
 * knowing when a quit event has been triggered in the UI
 *
 * @author David Hemming
 */
public abstract interface UIListener {

    public void quit();
}
