package com.example.stockviewer69.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.stockviewer69.R;
import com.example.stockviewer69.adapter.FeatureFragmentController;
import com.example.stockviewer69.adapter.MainStockAdapter;
import com.example.stockviewer69.model.ICallBackUpdateFeature;
import com.example.stockviewer69.model.entity.OverViewStockModel;
import com.example.stockviewer69.utils.Const;

import java.util.ArrayList;


public class FeatureFragment extends Fragment implements MainStockAdapter.ICallBackMain, MainStockAdapter.ICallBackSetColor, ICallBackUpdateFeature {
    private static final String ARG_PARAM1 = "feature_name";
    private static final String ARG_PARAM2 = "feature_type";
    TextView tvFeatureName;
    RecyclerView mainStockRecycleView;
    MainStockAdapter mainStockAdapter;
    FeatureFragment featuresFragment;
    FeatureFragmentController featureFragmentController;
    private String featureName;
    private int featureType;

    public FeatureFragment() {
        // Required empty public constructor
    }

    public static FeatureFragment newInstance(String param1, String param2) {
        FeatureFragment fragment = new FeatureFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        featureFragmentController = new FeatureFragmentController(this);
        featureFragmentController.init();
        featureFragmentController.getStockList();
        tvFeatureName = getView().findViewById(R.id.tvFeatures);
        tvFeatureName.setText(featureName);
        mainStockRecycleView = getView().findViewById(R.id.stockRecycleView);
        mainStockAdapter = new MainStockAdapter(new ArrayList<>(), this, this);
        mainStockRecycleView.setAdapter(mainStockAdapter);
        RecyclerView.LayoutManager lm = new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false);
        mainStockRecycleView.setLayoutManager(lm);
    }

    public void reloadList() {
        Toast.makeText(getContext(), "reloaded", Toast.LENGTH_SHORT).show();
        mainStockAdapter.clearStock();
        featureFragmentController.getStockList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feature, container, false);
    }

    //?????????
    @Override
    public void setImageWithGlide(String url, int type, ImageView stockIcon) {
        String fullUrl;
        if (type == Const.ImageType.IMG_TYPE_ICON) {
            fullUrl = String.format(Const.ICON_API_URL + url.toLowerCase() + "/200");
        } else {
            fullUrl = url;
        }
        Glide.with(getContext()).load(fullUrl)
                .placeholder(R.mipmap.default_coin)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(stockIcon);
    }

    @Override
    public int setLineColor(int drawable) {
        return ContextCompat.getColor(getContext(), drawable);
    }

    @Override
    public void updateStockMarketData(OverViewStockModel o) {
        Log.d("1336", "updateList: ");
        mainStockAdapter.addStock(o);
        mainStockAdapter.notifyDataSetChanged();
    }
}