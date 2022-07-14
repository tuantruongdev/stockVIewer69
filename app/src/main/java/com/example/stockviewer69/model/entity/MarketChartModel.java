package com.example.stockviewer69.model.entity;

import java.util.ArrayList;

public class MarketChartModel {

    private ArrayList<ArrayList<Double>> prices;
    private ArrayList<ArrayList<Double>> market_caps;
    private ArrayList<ArrayList<Double>> total_volumes;

    public MarketChartModel(ArrayList<ArrayList<Double>> prices, ArrayList<ArrayList<Double>> market_caps, ArrayList<ArrayList<Double>> total_volumes) {
        this.prices = prices;
        this.market_caps = market_caps;
        this.total_volumes = total_volumes;
    }

    public ArrayList<ArrayList<Double>> getPrices() {
        return prices;
    }

    public void setPrices(ArrayList<ArrayList<Double>> prices) {
        this.prices = prices;
    }

    public ArrayList<ArrayList<Double>> getMarket_caps() {
        return market_caps;
    }

    public void setMarket_caps(ArrayList<ArrayList<Double>> market_caps) {
        this.market_caps = market_caps;
    }

    public ArrayList<ArrayList<Double>> getTotal_volumes() {
        return total_volumes;
    }

    public void setTotal_volumes(ArrayList<ArrayList<Double>> total_volumes) {
        this.total_volumes = total_volumes;
    }
}
