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

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import design.jeanfredric.savewatertapapp.R;
import design.jeanfredric.savewatertapapp.databinding.FragmentWatertapBinding;
import design.jeanfredric.savewatertapapp.models.ConsumptionFacts;
import design.jeanfredric.savewatertapapp.models.WaterTap;

public class WaterTapFragment extends Fragment {

    private Button saveWaterButton;
    private TextView litersSaved;
    private WaterTap waterTap;
    private ConsumptionFacts consumptionFacts;


    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        waterTap = new WaterTap();
        consumptionFacts = new ConsumptionFacts();
        consumptionFacts.add(10, "How much clean water a Sub-Saharan African household consumes in a day.");
        consumptionFacts.add(50, "Femtio är mycket de.");
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_watertap, container, false);
        saveWaterButton = v.findViewById(R.id.save_water_btn);

        //TODO: Detta vill sig inte (DataBinderMapperImpl.java:9: error: cannot find symbol
        //TODO: import design.jeanfredric.savewatertapapp.databinding.FragmentWatertapBindingImpl;)
        FragmentWatertapBinding fragmentWatertapBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_watertap, null, false);
        View bindingView = fragmentWatertapBinding.getRoot();
        fragmentWatertapBinding.setWatertap(waterTap);

        //Button listener
        saveWaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!waterTap.isOn()) {
                    waterTap.start();
                    saveWaterButton.setText(R.string.save_water_btn_on);
                    saveWaterButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.peach));
                } else {
                    waterTap.stop();
                    saveWaterButton.setText(R.string.save_water_btn);
                    saveWaterButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.neon_green));

                }
            }
        });

        //TODO: Misstänker att jag måste köra all kommunikation via databinding
        return bindingView;
    }
}
