package com.example.stockviewer69.Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface IRetrofitApiFetch
{
    final String stringEndPoint="https://api.coingecko.com/api/v3/";
    final String stringArticleEndPoint="https://newsapi.org/v2/";
    Gson gson= new GsonBuilder().setDateFormat("dd-MM-yyy").create();
    IRetrofitApiFetch iRetrofitApiFetch=new Retrofit.Builder().baseUrl(stringEndPoint)

            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(IRetrofitApiFetch.class);

    @GET("coins/{id}/market_chart/range")
    Call<MarketChartModel> getCharts (@Path("id")String id, @Query("vs_currency")String vsCurrency, @Query("from")String from, @Query("to")String to);

    @GET("coins/{id}?localization=false&tickers=false&market_data=true&community_data=false&developer_data=false&sparkline=false")
    Call<StockMarketData> getStockMarketData(@Path("id")String id);
//https://newsapi.org/v2/everything?q=Ethereum&from=2022-07-07&to=2022-07-07&sortBy=popularity&pageSize=10&page=1&apiKey=eb65e13f15f046a5a98882c6179f4642
    @GET("everything?apiKey=eb65e13f15f046a5a98882c6179f4642")
    Call<NewsModel> getArticles( @Query("q") String qkey, @Query("from")String from, @Query("to")String to, @Query("sortBy")String sortBy, @Query("pageSize")String pageSzie, @Query("page")String page);


}
