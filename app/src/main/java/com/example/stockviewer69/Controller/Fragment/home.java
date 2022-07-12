package com.example.stockviewer69.Controller.Fragment;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.stockviewer69.Adapter.NewsAdapter;
import com.example.stockviewer69.Model.ApiFetch;
import com.example.stockviewer69.Model.NewsModel;
import com.example.stockviewer69.R;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class home extends Fragment {
    ArrayList<NewsModel.Article> news=new ArrayList<>();
    NewsAdapter newsAdapter;
    RecyclerView newsRecyclerView;
    private final int TOP_10_STOCK=1001;
    private final int TOP_GAIN_STOCK=1002;
    private final int TOP_LOSE_STOCK=1003;

    SwipeRefreshLayout swipeRefreshLayout;
    public home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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


        newsRecyclerView=getView().findViewById(R.id.newsRecycleViewMain);
        if(newsRecyclerView == null){
            Log.d(TAG, "onViewCreated: is null");
        }
        newsAdapter=new NewsAdapter(news, getContext(),home.this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        newsRecyclerView.setAdapter(newsAdapter);
        newsRecyclerView.setLayoutManager(linearLayoutManager);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                featuresFragment2.reloadList();
                featuresFragment.reloadList();
            }
        });
        init();
    }

    public void updateListNews(ArrayList<NewsModel.Article> article){
        for (int i = 0; i < article.size()-1; i++) {
            news.add(article.get(i));
            Log.d(TAG, "updateListNews: "+article.get(i).title);
        }
        newsAdapter.notifyDataSetChanged();

    }
    private void init(){
        try {
            ApiFetch apiFetch  = new ApiFetch();
            apiFetch.getMainArticle(this);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

}