package com.example.stockviewer69.Model;

public class DetailStockModel {
    private double timeStamp;
    private double close;

    public double getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(double timeStamp) {
        this.timeStamp = timeStamp;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public DetailStockModel(double timeStamp, double close) {
        this.timeStamp = timeStamp;
        this.close = close;
    }
}
