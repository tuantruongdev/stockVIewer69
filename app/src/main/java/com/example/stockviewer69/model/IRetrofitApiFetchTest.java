package com.example.stockviewer69.model;

import com.example.stockviewer69.model.entity.NewsModel;
import com.example.stockviewer69.utils.Const;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IRetrofitApiFetchTest
{

    Gson gson= new GsonBuilder().setDateFormat("dd-MM-yyy").create();
    IRetrofitApiFetchTest iRetrofitApiFetch=new Retrofit.Builder().baseUrl(Const.NEWS_API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(IRetrofitApiFetchTest.class);


//https://newsapi.org/v2/everything?q=Ethereum&from=2022-07-07&to=2022-07-07&sortBy=popularity&pageSize=10&page=1&apiKey=eb65e13f15f046a5a98882c6179f4642
    @GET("everything?apiKey=eb65e13f15f046a5a98882c6179f4642")
    Call<NewsModel> getArticles(@Query("q") String qkey, @Query("from")String from, @Query("to")String to, @Query("sortBy")String sortBy, @Query("pageSize")String pageSzie, @Query("page")String page);


}
