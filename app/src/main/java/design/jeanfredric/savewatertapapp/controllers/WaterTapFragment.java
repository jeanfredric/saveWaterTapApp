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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import design.jeanfredric.savewatertapapp.R;

public class WaterTapFragment extends Fragment {

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_watertap, container, false);

        //Button listener

            //Spela upp ljud

            //Iterera liters consumed

            //Uppdatera equals consumptionFacts

        return v;
    }
}
