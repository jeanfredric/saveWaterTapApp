/**
 * An android mobile application that imitates the sound of a water tap in order to make people not
 * consume water just to hide sounds. The application calculates how much water is saved and places
 * it in relation to other water consuming facts. The application also display jokes to make the
 * toilet visit more pleasant. Created at Umeå university course "5DV209 Utveckling av mobila
 * applikationer" ST2021.
 *
 * MainActivity is a controller clas that is responsible to hold the bottom navigation bar and
 * display the user's navigation choice.
 *
 * @tested_on   Pixel 3a    / 1080 x 2220 res   / API 30
 *              Nexus 4     / 768 x 1280 res    / API 23
 *
 * @author  Fredric Birgersson
 * @version 1.0
 * @since   2021-06-01
 */

package design.jeanfredric.savewatertapapp.controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import org.jetbrains.annotations.NotNull;
import design.jeanfredric.savewatertapapp.R;

public class MainActivity extends AppCompatActivity {

    private static final String ACTIVE_FRAGMENT_KEY = "MainActivity.activeFragment";
    private static final String WATERTAP_FRAGMENT = "MainActivity.waterTapFragment";
    private static final String JOKES_FRAGMENT = "MainActivity.jokesFragment";
    private static final String ABOUT_FRAGMENT = "MainActivity.aboutFragment";

    private FragmentManager fragmentManager;
    private Fragment waterTapFragment;
    private Fragment jokesFragment;
    private Fragment aboutFragment;
    private Fragment activeFragment;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        //Retain fragments if possible
        waterTapFragment = fragmentManager.findFragmentByTag(WATERTAP_FRAGMENT);
        jokesFragment = fragmentManager.findFragmentByTag(JOKES_FRAGMENT);
        aboutFragment = fragmentManager.findFragmentByTag(ABOUT_FRAGMENT);

        //Otherwise create new fragments
        if (waterTapFragment == null) {
            waterTapFragment = new WaterTapFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, waterTapFragment, WATERTAP_FRAGMENT)
                    .hide(waterTapFragment)
                    .commit();
        }
        if (jokesFragment == null) {
            jokesFragment = new JokesFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, jokesFragment, JOKES_FRAGMENT)
                    .hide(jokesFragment)
                    .commit();
        }
        if (aboutFragment == null) {
            aboutFragment = new AboutFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, aboutFragment, ABOUT_FRAGMENT)
                    .hide(aboutFragment)
                    .commit();
        }

        //Restore activeFragment if activity has been destroyed
        if(savedInstanceState != null) {
            switch (savedInstanceState.getString(ACTIVE_FRAGMENT_KEY)) {
                case WATERTAP_FRAGMENT:
                    activeFragment = waterTapFragment;
                    break;
                case JOKES_FRAGMENT:
                    activeFragment = jokesFragment;
                    break;
                case  ABOUT_FRAGMENT:
                    activeFragment = aboutFragment;
                    break;
            }
        }
        else {
            activeFragment = waterTapFragment;
        }

        //Show active fragment
        fragmentManager.beginTransaction().show(activeFragment).commit();

        //Initialize bottom navigation menu
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigatonItemSelectedListener);
    }

    /**
     * Listens to actual bottom navigation menu-choice and decides what fragment to show.
     */
    private BottomNavigationView.OnNavigationItemSelectedListener navigatonItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.water_saver:
                            setCurrentFragment(waterTapFragment);
                            break;
                        case R.id.jokes:
                            setCurrentFragment(jokesFragment);
                            break;
                        case R.id.about:
                            setCurrentFragment(aboutFragment);
                            break;
                    }
                    return true;
                }
            };

    /**
     * Changes which Fragment is visible in the view.
     * @param newFragment Fragment to make visible in the view.
     */
    private void setCurrentFragment (Fragment newFragment) {
        fragmentManager.beginTransaction()
                .hide(activeFragment)
                .show(newFragment)
                .commit();

        activeFragment = newFragment;
    }

    /**
     * Called when activity gets destroyed and make it possible to retain data.
     * @param outState data needed to be able to recover previous data.
     */
    @Override
    protected void onSaveInstanceState(@NonNull @NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ACTIVE_FRAGMENT_KEY, activeFragment.getTag());
    }

    /**
     * Called when activity is no longer visible.
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * Called when user presses the android native back button.
     */
    @Override
    public void onBackPressed() {
        if (activeFragment != waterTapFragment) {
            setCurrentFragment(waterTapFragment);
            bottomNavigationView.setSelectedItemId(R.id.water_saver);
        } else {
            super.onBackPressed();
        }
    }
}