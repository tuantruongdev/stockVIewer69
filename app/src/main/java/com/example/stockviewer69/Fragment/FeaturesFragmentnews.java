//package com.example.stockviewer69.Fragment;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.stockviewer69.Adapter.MainStockAdapter;
//import com.example.stockviewer69.Model.ApiFetch;
//import com.example.stockviewer69.Model.OverViewStockModel;
//import com.example.stockviewer69.R;
//
//import java.util.ArrayList;
//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link FeaturesFragmentnews#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class FeaturesFragmentnews extends Fragment {
//
//    ArrayList<OverViewStockModel> overViewStockList;
//    TextView tvFeatureName;
//    RecyclerView mainStockRecycleView;
//    MainStockAdapter mainStockAdapter;
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "feature_name";
//    private static final String ARG_PARAM2 = "feature_type";
//
//    // TODO: Rename and change types of parameters
//    private String featureName;
//    private int featureType;
//
//    public FeaturesFragmentnews() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment FeaturesFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static FeaturesFragmentnews newInstance(String param1, String param2) {
//        FeaturesFragmentnews fragment = new FeaturesFragmentnews();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        //args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            featureName = getArguments().getString(ARG_PARAM1);
//            featureType  = getArguments().getInt(ARG_PARAM2);
//        }
//
//    }
//    private void sendRequests(){
//        try {
//            ApiFetch test=new ApiFetch();
//            test.sendRequest("klines?symbol=BTCUSDT&interval=1h&limit=24","POST",this,"Bitcon","BTC","bitcoin");
//            test.sendRequest("klines?symbol=ETHUSDT&interval=1h&limit=24","POST",this,"Etherium","ETH","ethereum");
//            test.sendRequest("klines?symbol=BNBUSDT&interval=1h&limit=24","POST",this,"Binance Coin","BNB","binancecoin");
//            test.sendRequest("klines?symbol=SOLUSDT&interval=1h&limit=24","POST",this,"Solana","SOL","solana");
//            test.sendRequest("klines?symbol=ADAUSDT&interval=1h&limit=24","POST",this,"ADA","ADA","cardano");
//            test.sendRequest("klines?symbol=BCHUSDT&interval=1h&limit=24","POST",this,"Bitcoin Hash","BCH","bitcoin-cash");
//            test.sendRequest("klines?symbol=XRPUSDT&interval=1h&limit=24","POST",this,"XRP","XRP","ripple");
//            test.sendRequest("klines?symbol=AAVEUSDT&interval=1h&limit=24","POST",this,"AAVE","AAVE","aave");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }}
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        overViewStockList=new ArrayList<OverViewStockModel>();
//
//        sendRequests();
//
//        tvFeatureName=getView().findViewById(R.id.tvFeatures);
//        tvFeatureName.setText(featureName);
//        mainStockRecycleView=(RecyclerView) getView().findViewById(R.id.stockRecycleView);
//        mainStockAdapter = new MainStockAdapter(overViewStockList,getContext(),this.getActivity());
//        mainStockRecycleView.setAdapter(mainStockAdapter);
//        RecyclerView.LayoutManager lm=new GridLayoutManager(getContext(),1, RecyclerView.HORIZONTAL,false);
//        mainStockRecycleView.setLayoutManager(lm);
//        // tempEntry1.add(new DetailStockModel(1.6571016E12,20202.76000000,20220.00000000,20045.00000000,20132.99000000,2826.07880000,1.657105199999E12,56903051.10171330,48043.0,1277.27956000,25716880.01921120,0) );
//         //tempEntry1.add(new DetailStockModel(1.6571016E12,20202.76000000,20220.00000000,20045.00000000,20132.99000000,2826.07880000,1.657105199999E12,56903051.10171330,48043.0,1277.27956000,25716880.01921120,0) );
////
////        tempEntry1.add(new Entry(1,10) );
////        tempEntry1.add(new Entry(2,12) );
////        tempEntry1.add(new Entry(3,8) );
////        tempEntry1.add(new Entry(4,10) );
////        tempEntry1.add(new Entry(5,15) );
////        tempEntry1.add(new Entry(6,20) );
////
////        ArrayList<Entry> tempEntry2=new ArrayList<Entry>();
////        tempEntry2.add(new Entry(0,15) );
////        tempEntry2.add(new Entry(1,13) );
////        tempEntry2.add(new Entry(2,17) );
////        tempEntry2.add(new Entry(3,9) );
////        tempEntry2.add(new Entry(4,11) );
////        tempEntry2.add(new Entry(5,7) );
////        tempEntry2.add(new Entry(6,5) );
////
////        tempList.add(new OverViewStockModel("APPL","APPLE",69.5,120,tempEntry1 ));
////        tempList.add(new OverViewStockModel("TESL","TESLA",-21.2,98,tempEntry2 ));
////        tempList.add(new OverViewStockModel("APPL","APPLE",69.5,120,tempEntry1 ));
////        tempList.add(new OverViewStockModel("TESL","TESLA",-21.2,98,tempEntry2 ));
////        tempList.add(new OverViewStockModel("APPL","APPLE",69.5,120,tempEntry1 ));
////        tempList.add(new OverViewStockModel("TESL","TESLA",-21.2,98,tempEntry2 ));
////        tempList.add(new OverViewStockModel("APPL","APPLE",69.5,120,tempEntry1 ));
////        tempList.add(new OverViewStockModel("TESL","TESLA",-21.2,98,tempEntry2 ));
////        tempList.add(new OverViewStockModel("APPL","APPLE",69.5,120,tempEntry1 ));
//
//
//
//    }
//    public void updateList(OverViewStockModel overViewStockItem){
//        Log.d("1336", "updateList: ");
//            overViewStockList.add(overViewStockItem);
//            mainStockAdapter.notifyDataSetChanged();
//
//    }
//    public void reloadList(){
//        Toast.makeText(getContext(), "reloaded", Toast.LENGTH_SHORT).show();
//        overViewStockList.clear();
//       // mainStockAdapter.notifyDataSetChanged();
//        sendRequests();
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_features, container, false);
//    }
//}