package com.example.stockviewer69.controller.fragment;

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
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.stockviewer69.adapter.MainStockAdapter;
import com.example.stockviewer69.adapter.NewsAdapter;
import com.example.stockviewer69.model.ApiFetch;
import com.example.stockviewer69.model.entity.NewsModel;
import com.example.stockviewer69.R;
import com.example.stockviewer69.utils.Const;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class HomeFragment extends Fragment implements MainStockAdapter.ICallBackMain {
    ArrayList<NewsModel.Article> news=new ArrayList<>();
    NewsAdapter newsAdapter;
    RecyclerView newsRecyclerView;
    private final int TOP_10_STOCK=1001;
    private final int TOP_GAIN_STOCK=1002;
    private final int TOP_LOSE_STOCK=1003;

    SwipeRefreshLayout swipeRefreshLayout;
    public HomeFragment() {
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
        FeatureFragment featuresFragment=new FeatureFragment();
        featuresFragment.setArguments(bundle);
        ft.add(R.id.fragmentContainer, featuresFragment);
        //  ft.add(R.id.fragment_container_gainer_loser,featuresFragment);
        ft.commit();
        FragmentManager fm2=getActivity().getSupportFragmentManager();
        FragmentTransaction ft2=fm2.beginTransaction();

        //create bundle for carrying data
        Bundle bundle2 = new Bundle();
        bundle2.putString("feature_name","Gainers & Losers:");
        bundle2.putInt("feature_type",TOP_10_STOCK);
        FeatureFragment featuresFragment2=new FeatureFragment();
        featuresFragment2.setArguments(bundle2);
        ft.add(R.id.fragmentContainerGainerLoser,featuresFragment2);
        ft2.commit();


        newsRecyclerView=getView().findViewById(R.id.newsRecycleViewMain);
        if(newsRecyclerView == null){
            Log.d(TAG, "onViewCreated: is null");
        }
        newsAdapter=new NewsAdapter(news,this );
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
    //????????????
    @Override
    public void setImageWithGlide(String url, int type, ImageView stockIcon) {
        String fullUrl;
        if (type== Const.ImageType.IMG_TYPE_ICON){
            fullUrl=String.format(Const.ICON_API_URL + url.toLowerCase()+"/200");
        }else {
            fullUrl=url;
        }
        Glide.with(this).load(fullUrl)
                .placeholder(R.mipmap.default_coin)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(stockIcon);
    }

}