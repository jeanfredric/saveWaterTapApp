/**
 * An android mobile application that imitates the sound of a water tap in order to make people not
 * consume water just to hide sounds. The application calculates how much water is saved and places
 * it in relation to other water consuming facts. The application also display jokes to make the
 * toilet visit more pleasant. Created at Umeå university course "5DV209 Utveckling av mobila
 * applikationer" ST2021.
 *
 * JokesFragment is responsible for deciding which joke to show depending on interactions with the
 * user.
 *
 * @author  Fredric Birgersson
 * @version 1.0
 * @since   2021-06-01
 */

package design.jeanfredric.savewatertapapp.controllers;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import org.jetbrains.annotations.NotNull;
import design.jeanfredric.savewatertapapp.R;
import design.jeanfredric.savewatertapapp.models.Joke;
import design.jeanfredric.savewatertapapp.models.Jokes;

public class JokesFragment extends Fragment implements SensorEventListener {

    private static final String JOKES_KEY = "JokesFragment.jokes";

    private Jokes jokes;
    private TextView displayingTitle;
    private TextView displayingBody;
    private ImageView displayingImg;

    private SensorManager sensorManager;
    private Sensor accelSensor;
    private boolean isAccelSensorAvailable, isNotFirstTime = false;
    private float currentX, currentY, currentZ, lastX, lastY, lastZ, xDifference, yDifference, zDifference;
    private float shakeThreshold = 5f;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            jokes = savedInstanceState.getParcelable(JOKES_KEY);
        } else {
            jokes = new Jokes();
            jokes.add(new Joke(R.string.joke_title_src_1, R.string.joke_body_src_1, R.drawable.joke_1));
            jokes.add(new Joke(R.string.joke_title_src_2, R.string.joke_body_src_2, R.drawable.joke_2));
            jokes.add(new Joke(R.string.joke_title_src_3, R.string.joke_body_src_3, R.drawable.joke_3));
            jokes.add(new Joke(R.string.joke_title_src_4, R.string.joke_body_src_4, R.drawable.joke_4));
            jokes.add(new Joke(R.string.joke_title_src_5, R.string.joke_body_src_5, R.drawable.joke_5));
            jokes.setEmpty(new Joke(R.string.joke_title_src_empty, R.string.joke_body_src_empty, R.drawable.jokes_empty));

        }
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_jokes, container, false);

        displayingTitle = v.findViewById(R.id.joke_title);
        displayingBody = v.findViewById(R.id.joke_body);
        displayingImg = v.findViewById(R.id.joke_img);

        //If the fragment is created for the first time, initialize a joke
        if (savedInstanceState == null) {
            jokes.randomizeJoke();
            jokes.markAsViewed();
        }

        //Attach active joke on the view
        displayActiveJoke();

        //Initialize shake listener
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER ) != null) {
            accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            isAccelSensorAvailable = true;
        } else {
            Log.e("", "Accelerometer sensor is not available");
            isAccelSensorAvailable = false;
        }

        return v;
    }

    /**
     * Called when activity gets destroyed and make it possible to retain data.
     * @param outState data needed to be able to recover previous data.
     */
    @Override
    public void onSaveInstanceState(@NonNull @NotNull Bundle outState) {
      outState.putParcelable(JOKES_KEY, jokes);
      super.onSaveInstanceState(outState);
    }

    /**
     * Code that will run when shake is detected.
     * @param event User physical initialized shake.
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        currentX = event.values[0];
        currentY = event.values[1];
        currentZ = event.values[2];

        //If shakes is detected, display a new joke
        if(isNotFirstTime) {
            xDifference = Math.abs(lastX - currentX);
            yDifference = Math.abs(lastY - currentY);
            zDifference = Math.abs(lastZ - currentZ);

            if((xDifference > shakeThreshold && yDifference > shakeThreshold) ||
                    (xDifference > shakeThreshold && zDifference > shakeThreshold) ||
                    (yDifference > shakeThreshold && zDifference > shakeThreshold)) {

                if(jokes.jokesAvailable()) {
                    jokes.randomizeJoke();
                    jokes.markAsViewed();
                } else {
                    jokes.displayEmpty();
                    jokes.resetJokes();
                }
                displayActiveJoke();
            }
        }

        lastX = currentX;
        lastY = currentY;
        lastZ = currentZ;
        isNotFirstTime = true;
    }

    /**
     * Must be implemented when using Accelerometer.
     * @param sensor
     * @param accuracy
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * Activates the shake listener when parent activity is resumed and if this fragment is displayed.
     */
    @Override
    public void onResume() {
        super.onResume();

        if (isAccelSensorAvailable && !isHidden()) {
            sensorManager.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    /**
     * Deactivates the shake listener when parent activity activity pauses and this fragment is active.
     */
    @Override
    public void onPause() {
        super.onPause();

        if(isAccelSensorAvailable && !isHidden()) {
            sensorManager.unregisterListener(this);
        }
    }

    /**
     * Sets the view properties to corresponding properties of active Joke.
     */
    public void displayActiveJoke() {
        displayingTitle.setText(jokes.getActiveJoke().getJokeTitle());
        displayingBody.setText(jokes.getActiveJoke().getJokeBody());
        displayingImg.setImageResource(jokes.getActiveJoke().getJokeImage());
    }

    /**
     * Unregisters the shake listener if the fragment is hidden.
     * @param hidden weather the fragment is hidden or not.
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            if(isAccelSensorAvailable) {
                sensorManager.unregisterListener(this);
            }
        }
        else {
            if(isAccelSensorAvailable) {
                sensorManager.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
    }
}
