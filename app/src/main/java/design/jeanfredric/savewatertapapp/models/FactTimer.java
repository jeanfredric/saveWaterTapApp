/**
 * An android mobile application that imitates the sound of a water tap in order to make people not
 * consume water just to hide sounds. The application calculates how much water is saved and places
 * it in relation to other water consuming facts. The application also display jokes to make the
 * toilet visit more pleasant. Created at Umeå university course "5DV209 Utveckling av mobila
 * applikationer" ST2021.
 *
 * FactTimer is a model class that makes it easier for the WaterTapFragment to retrieve a fact
 * based on a WaterTap object's water consumption. It makes it easier to save Timer instances on
 * orientation change.
 *
 * @author  Fredric Birgersson
 * @version 1.0
 * @since   2021-06-01
 */

package design.jeanfredric.savewatertapapp.models;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Timer;
import java.util.TimerTask;

public class FactTimer implements Parcelable {

    private static final int FACTTIMERSPEED_MILLS = 1000;

    private Timer timer;
    private TimerTask timerTask;

    /**
     * Constructor.
     */
    public FactTimer() {
        timer = new Timer();
    }

    /**
     * Parcelable method.
     * @param in
     */
    protected FactTimer(Parcel in) {
    }

    /**
     * Parcelable method.
     */
    public static final Creator<FactTimer> CREATOR = new Creator<FactTimer>() {
        @Override
        public FactTimer createFromParcel(Parcel in) {
            return new FactTimer(in);
        }

        @Override
        public FactTimer[] newArray(int size) {
            return new FactTimer[size];
        }
    };

    /**
     * Starts a timer and updates the actual consumption fact based on a waterTap obejcts consumption.
     * @param consumptionFacts A bunch of facts that are related to certain levels of water consumption.
     * @param waterTap A WaterTap object that consumes water.
     */
    public void start(ConsumptionFacts consumptionFacts, WaterTap waterTap) {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                consumptionFacts.setFact(waterTap.getConsumption());
            }
        };
        timer.schedule(timerTask,0, FACTTIMERSPEED_MILLS);
    }

    /**
     * Stops the timer.
     */
    public void stop() {
        if (timerTask != null) {
            timerTask.cancel();
        }
    }

    /**
     * Parcelable method.
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Parcelable method.
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
