/**
 * An android mobile application that imitates the sound of a water tap in order to make people not
 * consume water just to hide sounds. The application calculates how much water is saved and places
 * it in relation to other water consuming facts. The application also display jokes to make the
 * toilet visit more pleasant. Created at Umeå university course "5DV209 Utveckling av mobila
 * applikationer" ST2021.
 *
 * Jokes is a model class representation of a pile of jokes.
 *
 * @author  Fredric Birgersson
 * @version 1.0
 * @since   2021-06-01
 */

package design.jeanfredric.savewatertapapp.models;

import java.util.ArrayList;

public class Jokes {

    private ArrayList<Joke> unViewedJokes;
    private ArrayList<Joke> viewedJokes;

    /**
     * Constructor.
     */
    public Jokes() {
        unViewedJokes = new ArrayList<Joke>();
        viewedJokes = new ArrayList<Joke>();
    }

    /**
     * Adds a joke to the collection of jokes.
     * @param joke The joke to be added.
     */
    public void add(Joke joke) {
        unViewedJokes.add(joke);
    }

    /**
     * Randomizes one joke that yet hasn't been viewed.
     * @return The joke that yet hasn't been viewed.
     */
    public Joke randomizeJoke() {
        int nrOfUnViewedJokes = unViewedJokes.size();
        int randomIndex = (int)(Math.random() * nrOfUnViewedJokes);
        Joke newJoke = unViewedJokes.get(randomIndex);
        return newJoke;
    }

    /**
     * Tells if there is more jokes that hasn't been seen yet.
     * @return True if there is unviewed jokes, otherwise false.
     */
    public boolean jokesAvailable() {
        return (unViewedJokes.size() > 0);
    }

    /**
     * Marks all jokes as unviewed.
     */
    public void resetJokes() {
        unViewedJokes.addAll(viewedJokes);
        viewedJokes.clear();
    }

    /**
     * Marks a previous unviewed joke as viewed.
     * @param newJoke Joke to be remarked.
     */
    public void markAsViewed(Joke newJoke) {
        unViewedJokes.remove(newJoke);
        viewedJokes.add(newJoke);
    }
}
