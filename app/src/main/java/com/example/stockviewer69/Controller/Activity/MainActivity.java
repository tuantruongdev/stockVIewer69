package com.example.stockviewer69.Controller.Activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.example.stockviewer69.Controller.Fragment.details;
import com.example.stockviewer69.Controller.Fragment.home;
import com.example.stockviewer69.Controller.Fragment.others;
import com.example.stockviewer69.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    private final int TOP_10_STOCK = 1001;
    private final int TOP_GAIN_STOCK = 1002;
    private final int TOP_LOSE_STOCK = 1003;

    SwipeRefreshLayout swipeRefreshLayout;
    Fragment homeFragment;
    Fragment detailsFragment;
    Fragment othersFragment;
    Fragment activeFragment;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //bottom nav
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnItemSelectedListener(mOnNavigationItemSelectedListener);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.primaryDark));
        }

        //init fragment
        homeFragment = new home();
        detailsFragment = new details();
        othersFragment = new others();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.frame_container, homeFragment, "Homez")
                .add(R.id.frame_container, detailsFragment, "Detailsz")
                .add(R.id.frame_container, othersFragment, "Othersz")
                .commit();
        fragmentManager.beginTransaction().hide(homeFragment).hide(detailsFragment).hide(othersFragment).commit();

        activeFragment = homeFragment;

        loadFragment(fragmentManager, homeFragment);
        // mainStockRecycleView.addItemDecoration(new DividerItemDecoration(MainActivity.this,));
    }

    private BottomNavigationView.OnItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:

                    loadFragment(fragmentManager, homeFragment);
                    return true;
                case R.id.navigation_details:

                    loadFragment(fragmentManager, detailsFragment);
                    //Toast.makeText(MainActivity.this, "details", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_others:
                    loadFragment(fragmentManager, othersFragment);
                    return true;

            }
            return false;
        }
    };

    private void loadFragment(FragmentManager fm, Fragment targetFragment) {
        Log.d(TAG, "loadFragment: switch fragment");

        fm.beginTransaction().hide(activeFragment).show(targetFragment).commit();
        activeFragment = targetFragment;
    }

}