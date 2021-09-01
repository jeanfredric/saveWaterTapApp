/**
 * An android mobile application that imitates the sound of a water tap in order to make people not
 * consume water just to hide sounds. The application calculates how much water is saved and places
 * it in relation to other water consuming facts. The application also display jokes to make the
 * toilet visit more pleasant. Created at Umeå university course "5DV209 Utveckling av mobila
 * applikationer" ST2021.
 *
 * WaterTapFragment is responsible for starting a water tap sound when the users pushes a button,
 * display how many liters of waters that is saved and display what those liters is equivalent to.
 *
 * @author  Fredric Birgersson
 * @version 1.0
 * @since   2021-06-01
 */

package design.jeanfredric.savewatertapapp.controllers;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.databinding.ObservableField;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import java.util.Timer;
import java.util.TimerTask;

import design.jeanfredric.savewatertapapp.R;
import design.jeanfredric.savewatertapapp.databinding.FragmentWatertapBinding;
import design.jeanfredric.savewatertapapp.models.ConsumptionFacts;
import design.jeanfredric.savewatertapapp.models.FactTimer;
import design.jeanfredric.savewatertapapp.models.WaterTap;

public class WaterTapFragment extends Fragment {

    private static final String WATERTAP_KEY = "WaterTapFragment.waterTap";
    private static final String CONSUMPTIONFACTS_KEY = "WaterTapFragment.consumptionFacts";
    private static final String FACTTIMER_KEY = "WaterTapFragment.factTimer";
    private static final String BTN_OFF = "SAVE WATER NOW";
    private static final String BTN_ON = "TURN TAP OFF";

    private WaterTap waterTap;
    private ConsumptionFacts consumptionFacts;
    private FactTimer factTimer;

    public String btnText = BTN_OFF;
    public final ObservableField<String> btnTextObservable = new ObservableField<>();

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            waterTap = savedInstanceState.getParcelable(WATERTAP_KEY);
            consumptionFacts = savedInstanceState.getParcelable(CONSUMPTIONFACTS_KEY);
            factTimer = savedInstanceState.getParcelable(FACTTIMER_KEY);

            if(waterTap.isOn()) {
                waterTap.playTapSound(getContext());
                btnText = BTN_ON;
            }
        }
        else {
            waterTap = new WaterTap();
            consumptionFacts = new ConsumptionFacts();
            consumptionFacts.add(0, "How much fresh water 10% of the world population has access to.");
            consumptionFacts.add(2, "How much water a human should drink per day.");
            consumptionFacts.add(4, "How much clean water an average Sub-Sahara African household consumes in a day.*");
            consumptionFacts.add(7, "Five big Coca-cola bottles.");
            consumptionFacts.add(11, "Many people in the world exist on 11 litres of water per day or less. We can use that amount in one flush of the toilet.");
            consumptionFacts.add(15, "How much water that on average are wasted when brushing your teeth.");
            consumptionFacts.add(35, "The production of one slice of bread.");
            consumptionFacts.add(75, "The production of a can of beer.");
            consumptionFacts.add(180, "The production of a soft drink.");
            consumptionFacts.add(900, "How much water that is required to produce a smartphone.");
            factTimer = new FactTimer();
        }
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        FragmentWatertapBinding fragmentWatertapBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_watertap, null, false);
        View bindingView = fragmentWatertapBinding.getRoot();
        fragmentWatertapBinding.setWatertapFragment(this);
        fragmentWatertapBinding.setWaterTap(waterTap);
        fragmentWatertapBinding.setConsumptionFacts(consumptionFacts);

        btnTextObservable.set(btnText);

        return bindingView;
    }

    /**
     * Toggles the water tap status, gets called on button click.
     * @param view View that calls the method on button click.
     */
    public void toggleWaterTap(View view) {
        if (!waterTap.isOn()) {
            waterTap.start(getContext());
            factTimer.start(consumptionFacts, waterTap);
            btnText = BTN_ON;
        } else {
            waterTap.stop();
            factTimer.stop();
            btnText = BTN_OFF;
        }
        btnTextObservable.set(btnText);
    }

    @Override
    public void onSaveInstanceState(@NonNull @NotNull Bundle outState) {
        outState.putParcelable(WATERTAP_KEY, waterTap);
        outState.putParcelable(CONSUMPTIONFACTS_KEY, consumptionFacts);
        outState.putParcelable(FACTTIMER_KEY, factTimer);
    }

    /**
     * Stops watertap sound when exiting the app.
     */
    @Override
    public void onStop() {
        super.onStop();
        waterTap.stopTapSound();
    }
}
