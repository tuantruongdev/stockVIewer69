package com.example.stockviewer69.activity;

import static android.content.ContentValues.TAG;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.stockviewer69.R;
import com.example.stockviewer69.fragment.DetailFragment;
import com.example.stockviewer69.fragment.HomeFragment;
import com.example.stockviewer69.fragment.OtherFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    Fragment homeFragment;
    Fragment detailFragment;
    Fragment otherFragment;
    Fragment activeFragment;
    FragmentManager fragmentManager;
    BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //bottom nav
       navigation  = findViewById(R.id.navigation);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.primaryDark));
        }
        //init fragment
        homeFragment = new HomeFragment();
        detailFragment = new DetailFragment();
        otherFragment = new OtherFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.frame_container, homeFragment, "Home")
                .add(R.id.frame_container, detailFragment, "Detail")
                .add(R.id.frame_container, otherFragment, "Other")
                .commit();
        fragmentManager.beginTransaction().hide(homeFragment).hide(detailFragment).hide(otherFragment).commit();
        activeFragment = homeFragment;
        loadFragment(fragmentManager, homeFragment);
        bind();
    }

    private void bind(){
       navigation.setOnItemSelectedListener(item -> {
           switch (item.getItemId()) {
               case R.id.navigation_home:
                   loadFragment(fragmentManager, homeFragment);
                   return true;
               case R.id.navigation_details:
                   loadFragment(fragmentManager, detailFragment);
                   return true;
               case R.id.navigation_others:
                   loadFragment(fragmentManager, otherFragment);
                   return true;
           }
           return false;
       });
    }

    private void loadFragment(FragmentManager fm, Fragment targetFragment) {
        Log.d(TAG, "loadFragment: switch fragment");
        fm.beginTransaction().hide(activeFragment).show(targetFragment).commit();
        activeFragment = targetFragment;
    }

}