package com.example.stockviewer69.view.fragment;

import static android.content.ContentValues.TAG;
import static com.example.stockviewer69.utils.Const.Type.TOP_10_STOCK;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.stockviewer69.R;
import com.example.stockviewer69.adapter.MainStockAdapter;
import com.example.stockviewer69.adapter.NewsAdapter;
import com.example.stockviewer69.controller.HomeFragmentController;
import com.example.stockviewer69.model.ICallBackUpdate;
import com.example.stockviewer69.model.entity.NewsModel;
import com.example.stockviewer69.model.entity.StockMarketData;
import com.example.stockviewer69.utils.Const;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements MainStockAdapter.ICallBackMain, ICallBackUpdate {

    NewsAdapter newsAdapter;
    RecyclerView newsRecyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    HomeFragmentController homeFragmentController;

    public HomeFragment() {
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
        swipeRefreshLayout = getView().findViewById(R.id.swipeRefreshLayout);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        //create bundle for carrying data
        Bundle bundle = new Bundle();
        bundle.putString("feature_name", "Top 10 market cap stock:");
        bundle.putInt("feature_type", TOP_10_STOCK);
        FeatureFragment featuresFragment = new FeatureFragment();
        featuresFragment.setArguments(bundle);
        ft.add(R.id.fragmentContainer, featuresFragment);
        //  ft.add(R.id.fragment_container_gainer_loser,featuresFragment);
        ft.commit();
        FragmentManager fm2 = getActivity().getSupportFragmentManager();
        FragmentTransaction ft2 = fm2.beginTransaction();

        // create bundle for carrying data
        Bundle bundle2 = new Bundle();
        bundle2.putString("feature_name", "Gainers & Losers:");
        bundle2.putInt("feature_type", TOP_10_STOCK);
        FeatureFragment featuresFragment2 = new FeatureFragment();
        //FeatureFragment featuresFragment2=FeatureFragment.newInstance("Gainers & Losers:",String.valueOf(TOP_10_STOCK));

        featuresFragment2.setArguments(bundle2);
        ft.add(R.id.fragmentContainerGainerLoser, featuresFragment2);
        ft2.commit();

        homeFragmentController = new HomeFragmentController(this);
        homeFragmentController.init();

        newsRecyclerView = getView().findViewById(R.id.newsRecycleViewMain);
        newsAdapter = new NewsAdapter(new ArrayList<>(), this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        newsRecyclerView.setAdapter(newsAdapter);
        newsRecyclerView.setLayoutManager(linearLayoutManager);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
            featuresFragment2.reloadList();
            featuresFragment.reloadList();
        });
        init();
    }

    private void init() {
        homeFragmentController.getNews("crypto");
    }

    //????????????
    @Override
    public void setImageWithGlide(String url, int type, ImageView stockIcon) {
        String fullUrl;
        if (type == Const.ImageType.IMG_TYPE_ICON) {
            fullUrl = String.format(Const.ICON_API_URL + url.toLowerCase() + "/200");
        } else {
            fullUrl = url;
        }
        Glide.with(this).load(fullUrl)
                .placeholder(R.mipmap.default_coin)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(stockIcon);
    }

    @Override
    public void updateStockMarketData(StockMarketData stockMarketData) {

    }

    @Override
    public void updateNews(ArrayList<NewsModel.Article> article) {
        for (int i = 0; i < article.size() - 1; i++) {
            newsAdapter.addNews(article.get(i));
            Log.d(TAG, "updateListNews: " + article.get(i).title);
        }
        newsAdapter.notifyDataSetChanged();
    }
}