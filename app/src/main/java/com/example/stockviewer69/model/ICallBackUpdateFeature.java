package com.example.stockviewer69.model;

import com.example.stockviewer69.model.entity.NewsModel;
import com.example.stockviewer69.model.entity.OverViewStockModel;
import com.example.stockviewer69.model.entity.StockMarketData;

import java.util.ArrayList;

public interface ICallBackUpdateFeature {
    public void updateStockMarketData(OverViewStockModel o);
}
