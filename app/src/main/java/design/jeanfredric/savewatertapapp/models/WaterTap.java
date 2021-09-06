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
import android.os.Parcel;
import android.os.Parcelable;
import androidx.databinding.ObservableInt;
import java.util.Timer;
import java.util.TimerTask;
import design.jeanfredric.savewatertapapp.R;

public class WaterTap implements Parcelable {

    private static final int WATERTAPSPEED_MILLS = 15000;

    public final ObservableInt litersConsumedObservable;
    private int litersConsumed;

    private boolean isOn;
    Timer timer;
    TimerTask timerTask;
    MediaPlayer player;

    /**
     * Constructor.
     */
    public WaterTap() {
        litersConsumed = 0;
        litersConsumedObservable = new ObservableInt();
        litersConsumedObservable.set(litersConsumed);
        isOn = false;
        timer = new Timer();
    }

    /**
     * Parcelable method.
     * @param in
     */
    protected WaterTap(Parcel in) {
        litersConsumedObservable = in.readParcelable(ObservableInt.class.getClassLoader());
        litersConsumed = in.readInt();
        isOn = in.readByte() != 0;
    }

    public static final Creator<WaterTap> CREATOR = new Creator<WaterTap>() {
        @Override
        public WaterTap createFromParcel(Parcel in) {
            return new WaterTap(in);
        }

        @Override
        public WaterTap[] newArray(int size) {
            return new WaterTap[size];
        }
    };

    /**
     * Starts the water tap.
     * @param context The context from where the function is called.
     */
    public void start(Context context) {
        isOn = true;
        playTapSound(context);

        timerTask = new TimerTask() {
            @Override
            public void run() {
                incrementConsumption();
            }
        };
        timer.schedule(timerTask,WATERTAPSPEED_MILLS, WATERTAPSPEED_MILLS);
    }

    /**
     * Stops the water tap.
     */
    public void stop() {
        isOn = false;
        if(timerTask != null) {
            timerTask.cancel();
        }
        stopTapSound();
    }

    /**
     * Pauses the water tap.
     */
    public void pause() {
        if(timerTask != null) {
            timerTask.cancel();
        }
        stopTapSound();
    }

    /**
     * Increments the amount of liters consumed.
     */
    private void incrementConsumption() {
        litersConsumed++;
        litersConsumedObservable.set(litersConsumed);
    }

    /**
     * @return True if the water tap is turned on.
     */
    public boolean isOn() {
        return isOn;
    }

    /**
     * @return The total amount of litres consumed.
     */
    public int getConsumption() {
        return litersConsumed;
    }

    /**
     * Plays a sound of a running water tap.
     * @param context The context from where the function was called.
     */
    public void playTapSound(Context context) {
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

    /**
     * Stops the sound of a running water tap.
     */
    public void stopTapSound() {
        if (player != null) {
            player.release();
            player = null;
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
        dest.writeParcelable(litersConsumedObservable, flags);
        dest.writeInt(litersConsumed);
        dest.writeByte((byte) (isOn ? 1 : 0));
    }
}
