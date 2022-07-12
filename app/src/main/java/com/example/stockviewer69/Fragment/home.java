package com.example.stockviewer69.Fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.stockviewer69.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class home extends Fragment {


    private final int TOP_10_STOCK=1001;
    private final int TOP_GAIN_STOCK=1002;
    private final int TOP_LOSE_STOCK=1003;

    SwipeRefreshLayout swipeRefreshLayout;
    public home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home.
     */
    // TODO: Rename and change types and number of parameters
    public static home newInstance(String param1, String param2) {
        home fragment = new home();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout=getView().findViewById(R.id.swipeRefreshLayout);

        FragmentManager fm=getActivity().getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();



        //create bundle for carrying data
        Bundle bundle = new Bundle();
        bundle.putString("feature_name","Top 10 market cap stock:");
        bundle.putInt("feature_type",TOP_10_STOCK);
        FeaturesFragment featuresFragment=new FeaturesFragment();
        featuresFragment.setArguments(bundle);
        ft.add(R.id.fragment_container, featuresFragment);
        //  ft.add(R.id.fragment_container_gainer_loser,featuresFragment);
        ft.commit();


        FragmentManager fm2=getActivity().getSupportFragmentManager();
        FragmentTransaction ft2=fm2.beginTransaction();

        //create bundle for carrying data
        Bundle bundle2 = new Bundle();
        bundle2.putString("feature_name","Gainers & Losers:");
        bundle2.putInt("feature_type",TOP_10_STOCK);
        FeaturesFragment featuresFragment2=new FeaturesFragment();
        featuresFragment2.setArguments(bundle2);
        ft.add(R.id.fragment_container_gainer_loser,featuresFragment2);
        ft2.commit();


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                featuresFragment2.reloadList();
                featuresFragment.reloadList();
            }
        });

    }
}