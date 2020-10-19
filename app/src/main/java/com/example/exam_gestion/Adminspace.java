package com.example.exam_gestion;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Adminspace extends AppCompatActivity {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminspace_activity);
        ButterKnife.bind(this);
        bottomNavigation.setOnNavigationItemSelectedListener(navListener);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Admin_home_fragment()).commit();
        }

    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
    new  BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
          Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new Admin_home_fragment();
                    break;
                case R.id.nav_profile:
                    selectedFragment = new Admin_profile_fragment();
                    break;

                case R.id.consultation:
                    selectedFragment = new Admin_consultation_fragment();
                    break;

                case R.id.nav_addaccount:
                    selectedFragment = new admin_addaccount_fragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    selectedFragment).addToBackStack("yes").commit();
            return true;








        }
    };
}
