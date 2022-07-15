package com.example.stockviewer69.model;

import com.example.stockviewer69.model.entity.NewsModel;
import com.example.stockviewer69.model.entity.StockMarketData;

import java.util.ArrayList;

public interface ICallBackUpdate {
    public void updateStockMarketData(StockMarketData stockMarketData);
    public void updateNews(ArrayList<NewsModel.Article> news);
}
