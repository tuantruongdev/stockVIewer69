//package com.example.stockviewer69.Model;
//
//import static android.content.ContentValues.TAG;
//
//import android.os.Handler;
//import android.os.Looper;
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//
//import com.example.stockviewer69.Controller.Fragment.FeaturesFragment;
//import com.example.stockviewer69.Controller.Activity.StockViewActivity;
//import com.google.gson.Gson;
//
//import java.io.IOException;
//import java.net.MalformedURLException;
//
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//
//public class ApiFetchnews {
//
//    private final String stringApiEndPoint = "https://api.binance.com/api/v3/";
//    final String stringEndPoint="https://api.coingecko.com/api/v3/";
//    final OkHttpClient client = new OkHttpClient();
//
//    public ApiFetchnews() throws MalformedURLException {
//
//    }
//
//
//    public void sendRequest(String path, String method, FeaturesFragment featuresFragment, String symbol, String shortSymbol ,String stockId) throws IOException {
//        Request rq=new Request.Builder().url(stringApiEndPoint+path)
//              .build();
//
//        client.newCall(rq).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                Log.d(TAG, "1337onFailure: failed");
//            }
//
//            @Override
//            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                if (response.isSuccessful()){
//                //Log.d(TAG, "onResponse: successfully"+response.body().string());
//                  String body= "{\"master\":"+ response.body().string()+"}";
//                  Gson gson= new Gson();
//                   RawOverViewStockModel rq=gson.fromJson(body,RawOverViewStockModel.class);
//                   Log.d(TAG, "1337: "+body);
//
//                      Log.d(TAG, "onResponse: "+ rq.master.get(0).get(1));
//                   // ArrayList<OverViewStockModel> raw=new ArrayList<>();
//                    //convert raw object to detalStockModel
//                   // raw.add();
//                    new Handler(Looper.getMainLooper()).post(new Runnable() {
//                        @Override
//                        public void run() {
//                            featuresFragment.updateList( new OverViewStockModel(shortSymbol,symbol,stockId,20,396001,  RawOverViewStockModel.convertToDetailStockModel(rq)));
//                        }
//                    });
//
//                    Log.d(TAG, "1337: running");
//                }
//            }
//        });
//             Log.d(TAG, "1337: end");
//    }
//
//    public void getMarketChartData (String id,String currency,StockViewActivity stockViewActivity){
//
//        String to=String.valueOf(System.currentTimeMillis()/1000 - 500 );
//        String from=String.valueOf(System.currentTimeMillis()/1000 - 1000 -86400);;
//        Log.d(TAG, "getMarketChartData: frm "+from);
//        Log.d(TAG, "getMarketChartData: to "+to);
//        IRetrofitApiFetch.iRetrofitApiFetch.getCharts(id,currency,from,to).enqueue(new retrofit2.Callback<MarketChartModel>() {
//            @Override
//            public void onResponse(retrofit2.Call<MarketChartModel> call, retrofit2.Response<MarketChartModel> response) {
//                stockViewActivity.updateMarketChart(response.body());
//            }
//
//            @Override
//            public void onFailure(retrofit2.Call<MarketChartModel> call, Throwable t) {
//
//            }
//        });
//
//
////1657443887
//        //1657357487
//
//    }
//    public void getMarketData(String id, StockViewActivity stockViewActivity){
//        IRetrofitApiFetch.iRetrofitApiFetch.getStockMarketData(id).enqueue(new retrofit2.Callback<StockMarketData>() {
//            @Override
//            public void onResponse(retrofit2.Call<StockMarketData> call, retrofit2.Response<StockMarketData> response) {
//                stockViewActivity.updateMarketData(response.body());
//            }
//
//            @Override
//            public void onFailure(retrofit2.Call<StockMarketData> call, Throwable t) {
//                Log.d(TAG, "onFailureMarketData: "+t.getMessage());
//            }
//        });
//
//
//    }
//
//
//
//}
