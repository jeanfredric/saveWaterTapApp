package design.jeanfredric.savewatertapapp.controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
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
            fragmentManager.beginTransaction().add(R.id.fragment_container, waterTapFragment,
                    WATERTAP_FRAGMENT).hide(waterTapFragment).commit();
        }
        if (jokesFragment == null) {
            jokesFragment = new JokesFragment();
            fragmentManager.beginTransaction().add(R.id.fragment_container, jokesFragment,
                    JOKES_FRAGMENT).hide(jokesFragment).commit();
        }
        if (aboutFragment == null) {
            aboutFragment = new AboutFragment();
            fragmentManager.beginTransaction().add(R.id.fragment_container, aboutFragment,
                    ABOUT_FRAGMENT).hide(aboutFragment).commit();
        }

        //Restore activeFragment if app has been destroyed
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
     * Listens to actual botton navigation menu-choice and decides what fragment to show.
     */
    private BottomNavigationView.OnNavigationItemSelectedListener navigatonItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.water_saver:
                    fragmentManager.beginTransaction().hide(activeFragment).show(waterTapFragment).commit();
                    activeFragment = waterTapFragment;
                    break;
                case R.id.jokes:
                    fragmentManager.beginTransaction().hide(activeFragment).show(jokesFragment).commit();
                    activeFragment = jokesFragment;
                    break;
                case R.id.about:
                    fragmentManager.beginTransaction().hide(activeFragment).show(aboutFragment).commit();
                    activeFragment = aboutFragment;
                    break;
            }
            return true;
        }
    };

    /**
     * Called when activity get destroyed and make it possible to retain data.
     * @param outState data needed to be able to recover previous data.
     */
    @Override
    protected void onSaveInstanceState(@NonNull @NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ACTIVE_FRAGMENT_KEY, activeFragment.getTag());
    }

    /**
     * Called when activity is no longer visable.
     */
    @Override
    protected void onPause() {
        super.onPause();
        fragmentManager.beginTransaction().hide(activeFragment).commit();
    }
}

