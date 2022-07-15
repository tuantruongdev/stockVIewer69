package com.example.stockviewer69.adapter;

import com.example.stockviewer69.model.ApiFetch;
import com.example.stockviewer69.model.ICallBackUpdateFeature;
import com.example.stockviewer69.model.entity.OverViewStockModel;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class FeatureFragmentController {
    ArrayList<OverViewStockModel> overViewStockList;
    ApiFetch apiFetch;
    ICallBackUpdateFeature iCallBackUpdateFeature;

    public FeatureFragmentController(ICallBackUpdateFeature iCallBackUpdateFeature) {
        this.iCallBackUpdateFeature = iCallBackUpdateFeature;
    }

    public void init() {
        try {
            apiFetch = new ApiFetch();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void getStockList() {
        String to = String.valueOf(System.currentTimeMillis() / 1000 - 500);
        String from = String.valueOf(System.currentTimeMillis() / 1000 - 1000 - 86400);

        new Thread(() -> {
            try {
                apiFetch.sendRequest("coins/{id}/market_chart/range?vs_currency=usd&from=" + from + "&to=" + to, "POST", "Bitcoin", "BTC", "bitcoin", iCallBackUpdateFeature);
                apiFetch.sendRequest("coins/{id}/market_chart/range?vs_currency=usd&from=" + from + "&to=" + to, "POST", "Etherium", "ETH", "ethereum", iCallBackUpdateFeature);
                apiFetch.sendRequest("coins/{id}/market_chart/range?vs_currency=usd&from=" + from + "&to=" + to, "POST", "Binance Coin", "BNB", "binancecoin", iCallBackUpdateFeature);
                apiFetch.sendRequest("coins/{id}/market_chart/range?vs_currency=usd&from=" + from + "&to=" + to, "POST", "Solana", "SOL", "solana", iCallBackUpdateFeature);
                apiFetch.sendRequest("coins/{id}/market_chart/range?vs_currency=usd&from=" + from + "&to=" + to, "POST", "ADA", "ADA", "cardano", iCallBackUpdateFeature);
                apiFetch.sendRequest("coins/{id}/market_chart/range?vs_currency=usd&from=" + from + "&to=" + to, "POST", "Bitcoin Hash", "BCH", "bitcoin-cash", iCallBackUpdateFeature);
                apiFetch.sendRequest("coins/{id}/market_chart/range?vs_currency=usd&from=" + from + "&to=" + to, "POST", "XRP", "XRP", "ripple", iCallBackUpdateFeature);
                apiFetch.sendRequest("coins/{id}/market_chart/range?vs_currency=usd&from=" + from + "&to=" + to, "POST", "AAVE", "AAVE", "aave", iCallBackUpdateFeature);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
