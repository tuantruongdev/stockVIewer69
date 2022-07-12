package com.example.stockviewer69;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import com.example.stockviewer69.Adapter.MainStockAdapter;
import com.example.stockviewer69.Fragment.FeaturesFragment;
import com.example.stockviewer69.Fragment.details;
import com.example.stockviewer69.Fragment.home;
import com.example.stockviewer69.Fragment.others;
import com.example.stockviewer69.Model.ApiFetch;
import com.example.stockviewer69.Model.OverViewStockModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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