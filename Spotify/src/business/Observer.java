package business;

import business.entities.Song;

/**
 * Interface that defines the methods that must be implemented by the observer objects.
 * Objects will follow the Observer pattern.
 *
 * @see Observable
 * @author Group 6
 * @version 1.0
 */
public interface Observer {

    void update(String message, Song song);
    void update();
}