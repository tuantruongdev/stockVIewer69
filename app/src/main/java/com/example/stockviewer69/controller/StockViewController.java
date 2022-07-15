package com.example.stockviewer69.controller;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stockviewer69.R;
import com.example.stockviewer69.activity.StockViewActivity;
import com.example.stockviewer69.adapter.NewsAdapter;
import com.example.stockviewer69.model.ApiFetch;
import com.example.stockviewer69.model.ICallBackUpdate;
import com.example.stockviewer69.model.entity.NewsModel;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class StockViewController {
    ArrayList<NewsModel.Article> news = new ArrayList<>();
    ICallBackUpdate iCallBackUpdate;
    ApiFetch apiFetch;
    public  StockViewController(ICallBackUpdate iCallBackUpdate){
        this.iCallBackUpdate=iCallBackUpdate;
        try {
            apiFetch=new ApiFetch();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


  public void getMarketData(String stockId){
            //  apiFetch.getMarketChartData(intent.getStringExtra("stockId"),"usd",this);
            apiFetch.getMarketData(stockId, iCallBackUpdate);

    }
   public void getNews(String stockFullName){
            //  apiFetch.getMarketChartData(intent.getStringExtra("stockId"),"usd",this);
            apiFetch.getArticle(stockFullName, iCallBackUpdate);

    }

}
