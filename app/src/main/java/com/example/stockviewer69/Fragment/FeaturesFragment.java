package com.example.stockviewer69.Fragment;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stockviewer69.Adapter.MainStockAdapter;
import com.example.stockviewer69.Model.ApiFetch;
import com.example.stockviewer69.Model.DetailStockModel;
import com.example.stockviewer69.Model.OverViewStockModel;
import com.example.stockviewer69.R;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;


public class FeaturesFragment extends Fragment {

    ArrayList<OverViewStockModel> overViewStockList;
    TextView tvFeatureName;
    RecyclerView mainStockRecycleView;
    MainStockAdapter mainStockAdapter;

    private static final String ARG_PARAM1 = "feature_name";
    private static final String ARG_PARAM2 = "feature_type";


    private String featureName;
    private int featureType;

    public FeaturesFragment() {
        // Required empty public constructor
    }


    public static FeaturesFragment newInstance(String param1, String param2) {
        FeaturesFragment fragment = new FeaturesFragment();
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
            featureType  = getArguments().getInt(ARG_PARAM2);
        }

    }
    private void sendRequests(){
        try {
            ApiFetch test=new ApiFetch();
            String to=String.valueOf(System.currentTimeMillis()/1000 - 500 );
            String from=String.valueOf(System.currentTimeMillis()/1000 - 1000 -86400);;


//            test.sendRequest("klines?symbol=BTCUSDT&interval=1h&limit=24","POST",this,"Bitcon","BTC","bitcoin");
//            test.sendRequest("klines?symbol=ETHUSDT&interval=1h&limit=24","POST",this,"Etherium","ETH","ethereum");
//            test.sendRequest("klines?symbol=BNBUSDT&interval=1h&limit=24","POST",this,"Binance Coin","BNB","binancecoin");
//            test.sendRequest("klines?symbol=SOLUSDT&interval=1h&limit=24","POST",this,"Solana","SOL","solana");
//            test.sendRequest("klines?symbol=ADAUSDT&interval=1h&limit=24","POST",this,"ADA","ADA","cardano");
//            test.sendRequest("klines?symbol=BCHUSDT&interval=1h&limit=24","POST",this,"Bitcoin Hash","BCH","bitcoin-cash");
//            test.sendRequest("klines?symbol=XRPUSDT&interval=1h&limit=24","POST",this,"XRP","XRP","ripple");
//            test.sendRequest("klines?symbol=AAVEUSDT&interval=1h&limit=24","POST",this,"AAVE","AAVE","aave");

            test.sendRequest("coins/{id}/market_chart/range?vs_currency=usd&from="+from+"&to="+to,"POST",this,"Bitcon","BTC","bitcoin");
            test.sendRequest("coins/{id}/market_chart/range?vs_currency=usd&from="+from+"&to="+to,"POST",this,"Etherium","ETH","ethereum");
            test.sendRequest("coins/{id}/market_chart/range?vs_currency=usd&from="+from+"&to="+to,"POST",this,"Binance Coin","BNB","binancecoin");
            test.sendRequest("coins/{id}/market_chart/range?vs_currency=usd&from="+from+"&to="+to,"POST",this,"Solana","SOL","solana");
            test.sendRequest("coins/{id}/market_chart/range?vs_currency=usd&from="+from+"&to="+to,"POST",this,"ADA","ADA","cardano");
            test.sendRequest("coins/{id}/market_chart/range?vs_currency=usd&from="+from+"&to="+to,"POST",this,"Bitcoin Hash","BCH","bitcoin-cash");
            test.sendRequest("coins/{id}/market_chart/range?vs_currency=usd&from="+from+"&to="+to,"POST",this,"XRP","XRP","ripple");
            test.sendRequest("coins/{id}/market_chart/range?vs_currency=usd&from="+from+"&to="+to,"POST",this,"AAVE","AAVE","aave");

       } catch (Exception e) {
            e.printStackTrace();
        }}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        overViewStockList=new ArrayList<OverViewStockModel>();

        sendRequests();

        tvFeatureName=getView().findViewById(R.id.tvFeatures);
        tvFeatureName.setText(featureName);
        mainStockRecycleView=(RecyclerView) getView().findViewById(R.id.stockRecycleView);
        mainStockAdapter = new MainStockAdapter(overViewStockList,getContext(),this.getActivity());
        mainStockRecycleView.setAdapter(mainStockAdapter);
        RecyclerView.LayoutManager lm=new GridLayoutManager(getContext(),1, RecyclerView.HORIZONTAL,false);
        mainStockRecycleView.setLayoutManager(lm);



    }
    public void updateList(OverViewStockModel overViewStockItem){
        Log.d("1336", "updateList: ");
            overViewStockList.add(overViewStockItem);
            mainStockAdapter.notifyDataSetChanged();

    }
    public void reloadList(){
        Toast.makeText(getContext(), "reloaded", Toast.LENGTH_SHORT).show();
        overViewStockList.clear();
       // mainStockAdapter.notifyDataSetChanged();
        sendRequests();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_features, container, false);
    }
}