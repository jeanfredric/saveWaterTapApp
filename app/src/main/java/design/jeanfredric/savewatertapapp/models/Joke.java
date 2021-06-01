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

    private String title;
    private String joke;
    private String img;

    /**
     * Constructor.
     * @param jokeText The string of the new joke.
     * @param jokeImage Image related to the joke.
     */
    public Joke(String jokeTitle, String jokeText, String jokeImage) {
        title = jokeTitle;
        joke = jokeText;
        img = jokeImage;
    }

    /**
     * Gets the title of the joke.
     * @return The title of the joke.
     */
    public String getJokeTitle() {
        return title;
    }

    /**
     * Gets the body text of the joke.
     * @return The string of the joke.
     */
    public String getJokeText() {
        return joke;
    }

    /**
     * Gets the image source path.
     * @return The image related to the joke.
     */
    public String getJokeImage() {
        return img;
    }

}
