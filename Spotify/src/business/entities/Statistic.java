package business.entities;

import business.exceptions.BusinessException;

/**
 * Entity storing the information needed to plot the bar chart.
 *
 * @author Group 6
 * @version 1.0
 */
public class Statistic {
    /**
     * String storing the name of a genre.
     */
    private final String genre;
    /**
     * Integer storing the numbers of songs that belong to a genre.
     */
    private int numSongs;

    /**
     * Constructor method of Statistic.
     * Initializes genre and numSongs to the given values.
     *
     * @param genre string instance to obtain name of the genre.
     * @param value integer instance to obtain the number of songs.
     */
    public Statistic(String genre, int value) {
        this.genre = genre;
        this.numSongs = value;
    }

    /**
     * Getter method that returns the genre.
     *
     * @return name of the genre stored.
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Getter method that returns the number of songs.
     *
     * @return number of songs stored.
     */

    public int getNumSongs() {
        return numSongs;
    }

    /**
     *  Method that adds one to the number of songs.
     */
    public void addOne() {
        this.numSongs++;
    }
}
