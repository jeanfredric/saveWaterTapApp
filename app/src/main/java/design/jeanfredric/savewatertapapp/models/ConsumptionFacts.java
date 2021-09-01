/**
 * An android mobile application that imitates the sound of a water tap in order to make people not
 * consume water just to hide sounds. The application calculates how much water is saved and places
 * it in relation to other water consuming facts. The application also display jokes to make the
 * toilet visit more pleasant. Created at Umeå university course "5DV209 Utveckling av mobila
 * applikationer" ST2021.
 *
 * ConsumptionFacts is a model class that holds a bunch of ConsumptionFact-objects and is used to
 * navigate between them.
 *
 * @author  Fredric Birgersson
 * @version 1.0
 * @since   2021-06-01
 */

package design.jeanfredric.savewatertapapp.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.databinding.ObservableField;
import java.util.ArrayList;

public class ConsumptionFacts implements Parcelable {

    private ArrayList<ConsumptionFact> facts;
    private String activeFact;
    public final ObservableField<String> activeFactObservable = new ObservableField<>();

    /**
     * Constructor.
     */
    public ConsumptionFacts() {
        facts = new ArrayList<>();
        activeFact = "Nothing... Hit that button to change that!";
        activeFactObservable.set(activeFact);
    }

    /**
     * Parcelable method.
     * @param in
     */
    protected ConsumptionFacts(Parcel in) {
        activeFact = in.readString();
    }

    /**
     * Parcelable method.
     */
    public static final Creator<ConsumptionFacts> CREATOR = new Creator<ConsumptionFacts>() {
        @Override
        public ConsumptionFacts createFromParcel(Parcel in) {
            return new ConsumptionFacts(in);
        }

        @Override
        public ConsumptionFacts[] newArray(int size) {
            return new ConsumptionFacts[size];
        }
    };

    /**
     * Adds one new consumption fact to the array of facts.
     * @param litersConsumed How many liters of water the fact is related to.
     * @param relatingFact The fact that relates to number of liters of water.
     */
    public void add(int litersConsumed, String relatingFact) {
        facts.add(new ConsumptionFact(litersConsumed, relatingFact));
    }

    /**
     * Sets which facts that is going to be the at one moment relevant fact.
     * @param litersConsumed How many liters of water the fact is related to.
     */
    public void setFact(int litersConsumed) {
        String newFact = getFact(litersConsumed);
        if (newFact != null) {
            activeFact = newFact;
            activeFactObservable.set(activeFact);
        }
    }

    /**
     * @param litersConsumed How many liters of water the fact is related to.
     * @return The fact that relates to number of liters of water. Null if there is no related fact.
     */
    private String getFact(int litersConsumed) {

        for (ConsumptionFact cFact : facts) {
            if (cFact.getLiters() == litersConsumed) {
                return cFact.getFact();
            }
        }
        return null;
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
        dest.writeString(activeFact);
    }
}
