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

    private Fragment waterTapFragment;
    private Fragment jokesFragment;
    private Fragment aboutFragment;
    private FragmentManager fragmentManager;
    private Fragment activeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize all the fragments
        waterTapFragment = new WaterTapFragment();
        jokesFragment = new JokesFragment();
        aboutFragment = new AboutFragment();
        fragmentManager = getSupportFragmentManager();
        activeFragment = waterTapFragment;

        //Show the current fragment
        fragmentManager.beginTransaction().add(R.id.fragment_container, waterTapFragment).hide(activeFragment).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_container, jokesFragment).hide(jokesFragment).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_container, aboutFragment).hide(aboutFragment).commit();
        fragmentManager.beginTransaction().show(activeFragment).commit();



        //Initialize bottom navigation menu
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigatonItemSelectedListener);

    }

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

}

