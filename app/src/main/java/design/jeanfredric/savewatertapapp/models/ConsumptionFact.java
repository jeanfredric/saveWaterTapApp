/**
 * An android mobile application that imitates the sound of a water tap in order to make people not
 * consume water just to hide sounds. The application calculates how much water is saved and places
 * it in relation to other water consuming facts. The application also display jokes to make the
 * toilet visit more pleasant. Created at Umeå university course "5DV209 Utveckling av mobila
 * applikationer" ST2021.
 *
 * ConsumptionFact is a model class that maps an amount of water to a fact about that amount of
 * water.
 *
 * @author  Fredric Birgersson
 * @version 1.0
 * @since   2021-06-01
 */

package design.jeanfredric.savewatertapapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ConsumptionFact implements Parcelable {

    private int liters;
    private String fact;

    public ConsumptionFact(int liters, String fact) {
        this.liters = liters;
        this.fact = fact;
    }

    protected ConsumptionFact(Parcel in) {
        liters = in.readInt();
        fact = in.readString();
    }

    public static final Creator<ConsumptionFact> CREATOR = new Creator<ConsumptionFact>() {
        @Override
        public ConsumptionFact createFromParcel(Parcel in) {
            return new ConsumptionFact(in);
        }

        @Override
        public ConsumptionFact[] newArray(int size) {
            return new ConsumptionFact[size];
        }
    };

    public ConsumptionFact get() {
        return this;
    }

    public int getLiters() {
        return liters;
    }

    public String getFact() {
        return fact;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(liters);
        dest.writeString(fact);
    }
}
