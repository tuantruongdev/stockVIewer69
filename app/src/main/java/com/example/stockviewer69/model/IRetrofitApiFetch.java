package com.example.stockviewer69.model;

import com.example.stockviewer69.model.entity.MarketChartModel;
import com.example.stockviewer69.model.entity.StockMarketData;
import com.example.stockviewer69.utils.Const;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IRetrofitApiFetch
{

    Gson gson= new GsonBuilder().setDateFormat("dd-MM-yyy").create();
    IRetrofitApiFetch iRetrofitApiFetch=new Retrofit.Builder().baseUrl(Const.COINGECKO_API_URL)

            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(IRetrofitApiFetch.class);

    @GET("coins/{id}/market_chart/range")
    Call<MarketChartModel> getCharts (@Path("id")String id, @Query("vs_currency")String vsCurrency, @Query("from")String from, @Query("to")String to);

    @GET("coins/{id}?localization=false&tickers=false&market_data=true&community_data=false&developer_data=false&sparkline=false")
    Call<StockMarketData> getStockMarketData(@Path("id")String id);
//https://newsapi.org/v2/everything?q=Ethereum&from=2022-07-07&to=2022-07-07&sortBy=popularity&pageSize=10&page=1&apiKey=eb65e13f15f046a5a98882c6179f4642

}
