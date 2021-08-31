package design.jeanfredric.savewatertapapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Timer;
import java.util.TimerTask;

public class FactTimer implements Parcelable {

    private Timer timer;
    private TimerTask timerTask;


    public FactTimer() {
        timer = new Timer();
    }

    protected FactTimer(Parcel in) {
    }

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

    public void start(ConsumptionFacts consumptionFacts, WaterTap waterTap) {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                consumptionFacts.setFact(waterTap.getConsumption());
            }
        };
        timer.schedule(timerTask,0, 900);
    }

    public void stop() {
        timerTask.cancel();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
