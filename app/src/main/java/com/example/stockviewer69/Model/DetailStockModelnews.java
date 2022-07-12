package com.example.stockviewer69.Model;

public class DetailStockModelnews {
    private double openTime;
    private double openPrice;
    private double high;
    private double low;
    private double close;
    private double volume;
    private double closeTime;
    private double quoteAssetVolume;
    private double trades;
    private double tbbsav;
    private double  tbqsav;
    private double ignore;

    public DetailStockModelnews(double openTime, double openPrice, double high, double low, double close, double volume, double closeTime, double quoteAssetVolume, double trades, double tbbsav, double tbqsav, double ignore) {
        this.openTime = openTime;
        this.openPrice = openPrice;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.closeTime = closeTime;
        this.quoteAssetVolume = quoteAssetVolume;
        this.trades = trades;
        this.tbbsav = tbbsav;
        this.tbqsav = tbqsav;
        this.ignore = ignore;
    }

    public double getOpenTime() {
        return openTime;
    }

    public void setOpenTime(double openTime) {
        this.openTime = openTime;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(double closeTime) {
        this.closeTime = closeTime;
    }

    public double getQuoteAssetVolume() {
        return quoteAssetVolume;
    }

    public void setQuoteAssetVolume(double quoteAssetVolume) {
        this.quoteAssetVolume = quoteAssetVolume;
    }

    public double getTrades() {
        return trades;
    }

    public void setTrades(double trades) {
        this.trades = trades;
    }

    public double getTbbsav() {
        return tbbsav;
    }

    public void setTbbsav(double tbbsav) {
        this.tbbsav = tbbsav;
    }

    public double getTbqsav() {
        return tbqsav;
    }

    public void setTbqsav(double tbqsav) {
        this.tbqsav = tbqsav;
    }

    public double getIgnore() {
        return ignore;
    }

    public void setIgnore(double ignore) {
        this.ignore = ignore;
    }
}
