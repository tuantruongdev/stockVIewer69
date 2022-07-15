package com.example.stockviewer69.model;

import static android.content.ContentValues.TAG;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.stockviewer69.activity.StockViewActivity;
import com.example.stockviewer69.controller.StockViewController;
import com.example.stockviewer69.fragment.FeatureFragment;
import com.example.stockviewer69.fragment.HomeFragment;
import com.example.stockviewer69.model.entity.MarketChartModel;
import com.example.stockviewer69.model.entity.NewsModel;
import com.example.stockviewer69.model.entity.OverViewStockModel;
import com.example.stockviewer69.model.entity.RawOverViewStockModel;
import com.example.stockviewer69.model.entity.StockMarketData;
import com.example.stockviewer69.utils.Const;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiFetch {
    final OkHttpClient client = new OkHttpClient();

    public ApiFetch() throws MalformedURLException {
    }

    public void sendRequest(String path, String method, FeatureFragment featuresFragment, String symbol, String shortSymbol, String stockId) throws IOException {
        path = path.replace("{id}", stockId);
        Request rq = new Request.Builder().url(Const.COINGECKO_API_URL + path)
                .build();

        client.newCall(rq).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d(TAG, "1337onFailure: failed");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String body = response.body().string();
                    Gson gson = new Gson();
                    MarketChartModel rq = gson.fromJson(body, MarketChartModel.class);
                    Log.d(TAG, "1337: " + body);
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            featuresFragment.updateList(new OverViewStockModel(shortSymbol, symbol, stockId, 20, 396001, RawOverViewStockModel.convertToDetailStockModel(rq)));
                        }
                    });
                }
            }
        });
    }

    public void getMarketChartData(String id, String currency, String from, StockViewActivity stockViewActivity) {
        String to = String.valueOf(System.currentTimeMillis() / 1000 - 500);
        IRetrofitApiFetch.iRetrofitApiFetch.getCharts(id, currency, from, to).enqueue(new retrofit2.Callback<MarketChartModel>() {
            @Override
            public void onResponse(retrofit2.Call<MarketChartModel> call, retrofit2.Response<MarketChartModel> response) {
                stockViewActivity.updateMarketChartByTime(response.body());
                //no longer disabled
            }

            @Override
            public void onFailure(retrofit2.Call<MarketChartModel> call, Throwable t) {
                Log.e(TAG, "onFailure: get chart data failed");
            }
        });
    }

    public void getMarketData(String id, ICallBackUpdate iCallBackUpdate) {
        IRetrofitApiFetch.iRetrofitApiFetch.getStockMarketData(id).enqueue(new retrofit2.Callback<StockMarketData>() {
            @Override
            public void onResponse(retrofit2.Call<StockMarketData> call, retrofit2.Response<StockMarketData> response) {
          //      stockViewActivity.updateMarketData(response.body());
                iCallBackUpdate.updateStockMarketData(response.body());
            }

            @Override
            public void onFailure(retrofit2.Call<StockMarketData> call, Throwable t) {
                Log.d(TAG, "onFailureMarketData: " + t.getMessage());
            }
        });
    }

    public void getArticle(String q, ICallBackUpdate iCallBackUpdate) {
        Log.d(TAG, "getArticle: " + q.toLowerCase());
        IRetrofitApiFetchTest.iRetrofitApiFetch.getArticles(q.toLowerCase(), "2022-06-15", "2022-07-07", "popularity", "10", "1").enqueue(new retrofit2.Callback<NewsModel>() {

            @Override
            public void onResponse(retrofit2.Call<NewsModel> call, retrofit2.Response<NewsModel> response) {
                Log.d(TAG, "onResponse:" + response.body());
                iCallBackUpdate.updateNews(response.body().getArticles());
            }

            @Override
            public void onFailure(retrofit2.Call<NewsModel> call, Throwable t) {
            }
        });
    }

    public void getMainArticle(HomeFragment featureFragment) {
        String currentDate=getDateAgoByTimeStamp(0);
        String targetDate=getDateAgoByTimeStamp(30);
        IRetrofitApiFetchTest.iRetrofitApiFetch.getArticles("crypto", targetDate, currentDate, "publishedAt", "15", "1").enqueue(new retrofit2.Callback<NewsModel>() {
            @Override
            public void onResponse(retrofit2.Call<NewsModel> call, retrofit2.Response<NewsModel> response) {
                featureFragment.updateListNews(response.body().getArticles());
            }

            @Override
            public void onFailure(retrofit2.Call<NewsModel> call, Throwable t) {

            }
        });
    }

    private String getDateAgoByTimeStamp(int day){
        Date curDate = new Date();
        Date newDate = new Date(curDate.getTime() - (day * 24 * 3600 * 1000));
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String currentDateString=  sdf.format(newDate);
        return currentDateString;
    }
}
