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

import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class WaterTap {

    private int litersConsumed;
    private boolean isOn;
    Timer timer;
    TimerTask timerTask;

    public WaterTap() {
        litersConsumed = 0;
        isOn = false;
        timer = new Timer();
    }

    public void start() {
        isOn = true;
        //För varje sekund
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


    }

    private void incrementConsumption() {
        litersConsumed++;
    }

    public boolean isOn() {
        return isOn;
    }

    public int getConsumption() {
        return litersConsumed;
    }
}
