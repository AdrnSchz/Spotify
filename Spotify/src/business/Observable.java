package business;

/**
 * Interface that defines the methods that must be implemented by the observable objects.
 * Objects will follow the Observer pattern.
 *
 * @see Observer
 * @author Group 6
 * @version 1.0
 */
public interface Observable {

    void attach(Observer o);

    void detach(Observer o);

    void notifyObservers(String message);
}