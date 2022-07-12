package com.example.stockviewer69.Model;

import static android.content.ContentValues.TAG;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.stockviewer69.Fragment.FeaturesFragment;
import com.example.stockviewer69.StockViewActivity;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class ApiFetch {

    private final String stringApiEndPoint = "https://api.binance.com/api/v3/";
    final String stringEndPoint="https://api.coingecko.com/api/v3/";
    final OkHttpClient client = new OkHttpClient();

    public ApiFetch() throws MalformedURLException {

    }

    public void sendRequest(String path, String method, FeaturesFragment featuresFragment, String symbol, String shortSymbol ,String stockId) throws IOException {
        path=path.replace("{id}",stockId);

        Request rq=new Request.Builder().url(stringEndPoint+path)
                .build();

        client.newCall(rq).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d(TAG, "1337onFailure: failed");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    //Log.d(TAG, "onResponse: successfully"+response.body().string());
                    String body= response.body().string();
                    Gson gson= new Gson();
                    MarketChartModel rq=gson.fromJson(body,MarketChartModel.class);
                    Log.d(TAG, "1337: "+body);

                    Log.d(TAG, "onResponse: "+String.valueOf( rq.getPrices().get(0).get(0)));
                    // ArrayList<OverViewStockModel> raw=new ArrayList<>();
                    //convert raw object to detalStockModel
                    // raw.add();
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            featuresFragment.updateList( new OverViewStockModel(shortSymbol,symbol,stockId,20,396001,  RawOverViewStockModel.convertToDetailStockModel(rq)));
                        }
                    });

                    Log.d(TAG, "1337: running");
                }
            }
        });
        Log.d(TAG, "1337: end");
    }

    public void sendRequestByTime(String path, String method, FeaturesFragment featuresFragment, String symbol, String shortSymbol ,String stockId,String from) throws IOException {

        path=path.replace("{id}",stockId);

        Request rq=new Request.Builder().url(stringEndPoint+path)
              .build();

        client.newCall(rq).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d(TAG, "1337onFailure: failed");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                //Log.d(TAG, "onResponse: successfully"+response.body().string());
                  String body= response.body().string();
                  Gson gson= new Gson();
                   MarketChartModel rq=gson.fromJson(body,MarketChartModel.class);
                   Log.d(TAG, "1337: "+body);

                      Log.d(TAG, "onResponse: "+String.valueOf( rq.getPrices().get(0).get(0)));
                   // ArrayList<OverViewStockModel> raw=new ArrayList<>();
                    //convert raw object to detalStockModel
                   // raw.add();
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            featuresFragment.updateList( new OverViewStockModel(shortSymbol,symbol,stockId,20,396001,  RawOverViewStockModel.convertToDetailStockModel(rq)));
                        }
                    });

                    Log.d(TAG, "1337: running");
                }
            }
        });
             Log.d(TAG, "1337: end");
    }

    public void getMarketChartData (String id,String currency,String from,StockViewActivity stockViewActivity){

        String to=String.valueOf(System.currentTimeMillis()/1000 - 500 );
      //  String from=String.valueOf(System.currentTimeMillis()/1000 - 1000 -86400);;
        Log.d(TAG, "getMarketChartData: frm "+from);
        Log.d(TAG, "getMarketChartData: to "+to);
        IRetrofitApiFetch.iRetrofitApiFetch.getCharts(id,currency,from,to).enqueue(new retrofit2.Callback<MarketChartModel>() {
            @Override
            public void onResponse(retrofit2.Call<MarketChartModel> call, retrofit2.Response<MarketChartModel> response) {
                //disabled due unused
                stockViewActivity.updateMarketChartByTime(response.body());
            }

            @Override
            public void onFailure(retrofit2.Call<MarketChartModel> call, Throwable t) {

            }
        });


//1657443887
        //1657357487

    }
    public void getMarketData(String id, StockViewActivity stockViewActivity){
        IRetrofitApiFetch.iRetrofitApiFetch.getStockMarketData(id).enqueue(new retrofit2.Callback<StockMarketData>() {
            @Override
            public void onResponse(retrofit2.Call<StockMarketData> call, retrofit2.Response<StockMarketData> response) {
                stockViewActivity.updateMarketData(response.body());
            }

            @Override
            public void onFailure(retrofit2.Call<StockMarketData> call, Throwable t) {
                Log.d(TAG, "onFailureMarketData: "+t.getMessage());
            }
        });

    }
    public void getArticle(String q,StockViewActivity stockViewActivity){
        IRetrofitApiFetchTest.iRetrofitApiFetch.getArticles("crypto","2022-07-07","2022-07-07","popularity","10","1").enqueue(new retrofit2.Callback<NewsModel>() {

            @Override
            public void onResponse(retrofit2.Call<NewsModel> call, retrofit2.Response<NewsModel> response) {
                stockViewActivity.updateListNews(response.body().getArticles());
            }

            @Override
            public void onFailure(retrofit2.Call<NewsModel> call, Throwable t) {

            }
        });


    };




}
