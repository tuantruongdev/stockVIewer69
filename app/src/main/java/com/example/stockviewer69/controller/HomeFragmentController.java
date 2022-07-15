package com.example.stockviewer69.controller;

import com.example.stockviewer69.model.ApiFetch;
import com.example.stockviewer69.model.ICallBackUpdate;
import com.example.stockviewer69.model.entity.NewsModel;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class HomeFragmentController {
    ApiFetch apiFetch;
    ArrayList<NewsModel.Article> news = new ArrayList<>(); //in case store data in controller instead of adapter
    ICallBackUpdate iCallBackUpdate;

    public HomeFragmentController(ICallBackUpdate iCallBackUpdate) {
        this.iCallBackUpdate = iCallBackUpdate;
    }

    public void init() {
        try {
            apiFetch = new ApiFetch();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void getNews(String stockFullName) {
        //  apiFetch.getMarketChartData(intent.getStringExtra("stockId"),"usd",this);
        apiFetch.getMainArticle(stockFullName, iCallBackUpdate);
    }
}
