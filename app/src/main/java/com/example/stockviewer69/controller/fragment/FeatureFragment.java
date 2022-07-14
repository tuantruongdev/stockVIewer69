package com.example.stockviewer69.controller.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.stockviewer69.adapter.MainStockAdapter;
import com.example.stockviewer69.model.ApiFetch;
import com.example.stockviewer69.model.entity.OverViewStockModel;
import com.example.stockviewer69.R;
import com.example.stockviewer69.utils.Const;

import java.net.MalformedURLException;
import java.util.ArrayList;


public class FeatureFragment extends Fragment implements MainStockAdapter.ICallBackMain, MainStockAdapter.ICallBackSetColor{

    ArrayList<OverViewStockModel> overViewStockList;
    TextView tvFeatureName;
    RecyclerView mainStockRecycleView;
    MainStockAdapter mainStockAdapter;
    FeatureFragment featuresFragment;


    private static final String ARG_PARAM1 = "feature_name";
    private static final String ARG_PARAM2 = "feature_type";


    private String featureName;
    private int featureType;

    public FeatureFragment() {
        // Required empty public constructor
    }


    public static FeatureFragment newInstance(String param1, String param2) {
        FeatureFragment fragment = new FeatureFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            featureName = getArguments().getString(ARG_PARAM1);
            featureType = getArguments().getInt(ARG_PARAM2);
        }
        featuresFragment = this;
    }

    private void sendRequests() throws MalformedURLException {

        ApiFetch test = new ApiFetch();
        String to = String.valueOf(System.currentTimeMillis() / 1000 - 500);
        String from = String.valueOf(System.currentTimeMillis() / 1000 - 1000 - 86400);
        new Thread(new Runnable() {
            public void run() {
                try {
                    test.sendRequest("coins/{id}/market_chart/range?vs_currency=usd&from=" + from + "&to=" + to, "POST", featuresFragment, "Bitcoin", "BTC", "bitcoin");
                    test.sendRequest("coins/{id}/market_chart/range?vs_currency=usd&from=" + from + "&to=" + to, "POST", featuresFragment, "Etherium", "ETH", "ethereum");
                    test.sendRequest("coins/{id}/market_chart/range?vs_currency=usd&from=" + from + "&to=" + to, "POST", featuresFragment, "Binance Coin", "BNB", "binancecoin");
                    test.sendRequest("coins/{id}/market_chart/range?vs_currency=usd&from=" + from + "&to=" + to, "POST", featuresFragment, "Solana", "SOL", "solana");
                    test.sendRequest("coins/{id}/market_chart/range?vs_currency=usd&from=" + from + "&to=" + to, "POST", featuresFragment, "ADA", "ADA", "cardano");
                    test.sendRequest("coins/{id}/market_chart/range?vs_currency=usd&from=" + from + "&to=" + to, "POST", featuresFragment, "Bitcoin Hash", "BCH", "bitcoin-cash");
                    test.sendRequest("coins/{id}/market_chart/range?vs_currency=usd&from=" + from + "&to=" + to, "POST", featuresFragment, "XRP", "XRP", "ripple");
                    test.sendRequest("coins/{id}/market_chart/range?vs_currency=usd&from=" + from + "&to=" + to, "POST", featuresFragment, "AAVE", "AAVE", "aave");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        overViewStockList = new ArrayList<OverViewStockModel>();
        try {
            sendRequests();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        tvFeatureName = getView().findViewById(R.id.tvFeatures);
        tvFeatureName.setText(featureName);
        mainStockRecycleView = getView().findViewById(R.id.stockRecycleView);
        mainStockAdapter = new MainStockAdapter(overViewStockList,this, this);
        mainStockRecycleView.setAdapter(mainStockAdapter);
        RecyclerView.LayoutManager lm = new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false);
        mainStockRecycleView.setLayoutManager(lm);
    }


    public void updateList(OverViewStockModel overViewStockItem) {
        Log.d("1336", "updateList: ");
        overViewStockList.add(overViewStockItem);
        mainStockAdapter.notifyDataSetChanged();

    }

    public void reloadList() {
        Toast.makeText(getContext(), "reloaded", Toast.LENGTH_SHORT).show();
        overViewStockList.clear();
        // mainStockAdapter.notifyDataSetChanged();
        try {
            sendRequests();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feature, container, false);
    }
    //?????????
    @Override
    public void setImageWithGlide(String url,int type, ImageView stockIcon) {
        String fullUrl;
        if (type==Const.ImageType.IMG_TYPE_ICON){
            fullUrl=String.format(Const.ICON_API_URL + url.toLowerCase()+"/200");
        }else {
            fullUrl=url;
        }
        Glide.with(getContext()).load(fullUrl)
                .placeholder(R.mipmap.default_coin)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(stockIcon);
    }

    @Override
    public int setLineColor(int drawable) {
        return ContextCompat.getColor(getContext(),drawable);
    }
}