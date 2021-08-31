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



    private WaterTap waterTap;
    private ConsumptionFacts consumptionFacts;

    public String btnText = "SAVE WATER NOW";
    public final ObservableField<String> btnTextObservable = new ObservableField<>();

    FactTimer factTimer;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            waterTap = savedInstanceState.getParcelable(WATERTAP_KEY);
            consumptionFacts = savedInstanceState.getParcelable(CONSUMPTIONFACTS_KEY);
            factTimer = savedInstanceState.getParcelable(FACTTIMER_KEY);

            if(waterTap.isOn()) {
                //waterTap.start(getContext());
                waterTap.playTapSound(getContext());
                btnText = "TURN TAP OFF";
            }
        }
        else {
            waterTap = new WaterTap();
            consumptionFacts = new ConsumptionFacts();
            consumptionFacts.add(10, "How much clean water a Sub-Saharan African household consumes in a day.");
            consumptionFacts.add(50, "Femtio är mycket de.");
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

    public void toggleWaterTap(View view) {
        if (!waterTap.isOn()) {
            waterTap.start(getContext());
            factTimer.start(consumptionFacts, waterTap);
            btnText = "TURN TAP OFF";
        } else {
            waterTap.stop();
            factTimer.stop();
            btnText = "SAVE WATER NOW";
        }
        btnTextObservable.set(btnText);
    }

    @Override
    public void onSaveInstanceState(@NonNull @NotNull Bundle outState) {
        outState.putParcelable(WATERTAP_KEY, waterTap);
        outState.putParcelable(CONSUMPTIONFACTS_KEY, consumptionFacts);
        outState.putParcelable(FACTTIMER_KEY, factTimer);

    }

    @Override
    public void onStop() {
        super.onStop();
        waterTap.stopTapSound();
    }
}
