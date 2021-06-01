package design.jeanfredric.savewatertapapp.controllers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import design.jeanfredric.savewatertapapp.R;
import design.jeanfredric.savewatertapapp.models.Joke;
import design.jeanfredric.savewatertapapp.models.Jokes;


public class JokesFragment extends Fragment {

    private Jokes jokes;
    private TextView displayingTitle;
    private TextView displayingBody;
    private ImageView displayingImg;


    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        jokes = new Jokes();
        //Fyll även denna med en massa data
        jokes.add(new Joke(R.string.joke_title_src_1, R.string.joke_body_src_1, R.drawable.tomato));
        jokes.add(new Joke(R.string.joke_title_src_2, R.string.joke_body_src_2, R.drawable.jokes_empty));

    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_jokes, container, false);

        displayingTitle = (TextView) v.findViewById(R.id.joke_title);
        displayingBody = (TextView) v.findViewById(R.id.joke_body);
        displayingImg = (ImageView) v.findViewById(R.id.joke_img);


        //Randomiza ett första skämt
        Joke joke = jokes.randomizeJoke();

        //Placera skämet på vyn
        displayingTitle.setText(joke.getJokeTitle());
        displayingBody.setText(joke.getJokeBody());
        displayingImg.setImageResource(joke.getJokeImage());

        //Shake listener

        //Kolla om det finns nytt skämt

            //Om ja, Randomiza nytt skämt

                //Markera nya skämtet som sett genom funktionen markAsViewed(joke)

            //Om nej, sätt text att du har sett alla skämt, men skaka igen för att se dem på nytt!


        return v;
    }
}
