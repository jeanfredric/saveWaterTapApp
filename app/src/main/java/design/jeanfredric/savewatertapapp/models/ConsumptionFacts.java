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

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import java.util.ArrayList;

public class ConsumptionFacts {

    private ArrayList<ConsumptionFact> facts;
    private String activeFact;
    public final ObservableField<String> activeFactObservable;

    public ConsumptionFacts() {
        facts = new ArrayList<>();
        activeFactObservable = new ObservableField<>();
        activeFact = "Nothing... Hit that button to change that!";
        activeFactObservable.set(activeFact);
    }

    public void add(int litersConsumed, String relatingFact) {
        facts.add(new ConsumptionFact(litersConsumed, relatingFact));
    }

    public void setFact(int litersConsumed) {
        String newFact = getFact(litersConsumed);
        if (newFact != null) {
            activeFact = newFact;
            activeFactObservable.set(activeFact);
        }
    }

    private String getFact(int litersConsumed) {

        for (ConsumptionFact cFact : facts) {
            if (cFact.getLiters() == litersConsumed) {
                return cFact.getFact();
            }
        }
        return null;
    }
}
