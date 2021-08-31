/**
 * An android mobile application that imitates the sound of a water tap in order to make people not
 * consume water just to hide sounds. The application calculates how much water is saved and places
 * it in relation to other water consuming facts. The application also display jokes to make the
 * toilet visit more pleasant. Created at Umeå university course "5DV209 Utveckling av mobila
 * applikationer" ST2021.
 *
 * WaterTap is a model class representation of a water tap. Simulates a water  tap sound and
 * counts water consumed.
 *
 * @author  Fredric Birgersson
 * @version 1.0
 * @since   2021-06-01
 */

package design.jeanfredric.savewatertapapp.models;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import androidx.databinding.ObservableInt;

import java.util.Timer;
import java.util.TimerTask;

import design.jeanfredric.savewatertapapp.R;

public class WaterTap {
    //TODO Funkare om man lägger denna private?
    public final ObservableInt litersConsumedObservable;
    private int litersConsumed;

    private boolean isOn;
    Timer timer;
    TimerTask timerTask;

    MediaPlayer player;

    public WaterTap() {
        litersConsumed = 0;
        litersConsumedObservable = new ObservableInt();
        litersConsumedObservable.set(litersConsumed);
        isOn = false;
        timer = new Timer();
    }

    public void start(Context context) {
        isOn = true;
        playTapSound(context);

        timerTask = new TimerTask() {
            @Override
            public void run() {
                incrementConsumption();
                Log.d("LITERS_CONSUMED",Integer.toString(litersConsumed));
            }
        };
        timer.schedule(timerTask,0, 1000);
    }

    public void stop() {
        isOn = false;
        //Stoppa timern i start
        timerTask.cancel();
        stopTapSound();


    }

    private void incrementConsumption() {
        litersConsumed++;
        litersConsumedObservable.set(litersConsumed);
    }

    public boolean isOn() {
        return isOn;
    }

    public int getConsumption() {
        return litersConsumed;
    }

    private void playTapSound(Context context) {
        if (player == null) {
            player = MediaPlayer.create(context, R.raw.watertapsound);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    playTapSound(context);
                }
            });
        }
        player.start();
    }

    public void stopTapSound() {
        if (player != null) {
            player.release();
            player = null;
        }
    }
}
