/**
 * An android mobile application that imitates the sound of a water tap in order to make people not
 * consume water just to hide sounds. The application calculates how much water is saved and places
 * it in relation to other water consuming facts. The application also display jokes to make the
 * toilet visit more pleasant. Created at Umeå university course "5DV209 Utveckling av mobila
 * applikationer" ST2021.
 *
 * Joke is a model class representation of one joke.
 *
 * @author  Fredric Birgersson
 * @version 1.0
 * @since   2021-06-01
 */

package design.jeanfredric.savewatertapapp.models;

public class Joke {

    private int title;
    private int joke;
    private int img;

    /**
     * Constructor.
     * @param jokeTitle Reference to the string of the new joke's title.
     * @param jokeBody Reference to the string of the new joke.
     * @param jokeImage Reference to the image related to the joke.
     */
    public Joke(int jokeTitle, int jokeBody, int jokeImage) {
        title = jokeTitle;
        joke = jokeBody;
        img = jokeImage;
    }

    /**
     * Gets the title of the joke.
     * @return A reference to the string of the joke title.
     */
    public int getJokeTitle() {
        return title;
    }

    /**
     * Gets the body text of the joke.
     * @return A reference to the string of the joke body.
     */
    public int getJokeBody() {
        return joke;
    }

    /**
     * Gets the image source path.
     * @return A reference to the image related to the joke.
     */
    public int getJokeImage() {
        return img;
    }

}
