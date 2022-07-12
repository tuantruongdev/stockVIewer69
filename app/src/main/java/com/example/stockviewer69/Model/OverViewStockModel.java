package com.example.stockviewer69.Model;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.widget.Switch;

import androidx.annotation.LongDef;

import com.github.mikephil.charting.data.Entry;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class OverViewStockModel {
    private String stockShortName;
    private String stockFullName;
    private String stockId;
    private double stockGain;
    private double stockPrice;
    ArrayList<DetailStockModel> stockPriceHistory;

    public OverViewStockModel(String stockShortName, String stockFullName, String stockId, double stockGain, double stockPrice, ArrayList<DetailStockModel> stockPriceHistory) {
        this.stockShortName = stockShortName;
        this.stockFullName = stockFullName;
        this.stockId = stockId;
        this.stockGain = checkGainPercentage(stockPriceHistory);
        this.stockPrice = getLastestPrice(stockPriceHistory);
        this.stockPriceHistory = stockPriceHistory;
    }

    public String getStockShortName() {
        return stockShortName;
    }

    public void setStockShortName(String stockShortName) {
        this.stockShortName = stockShortName;
    }

    public String getStockFullName() {
        return stockFullName;
    }

    public void setStockFullName(String stockFullName) {
        this.stockFullName = stockFullName;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public double getStockGain() {
        return stockGain;
    }

    public void setStockGain(double stockGain) {
        this.stockGain = stockGain;
    }

    public double getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(double stockPrice) {
        this.stockPrice = stockPrice;
    }

    public ArrayList<DetailStockModel> getStockPriceHistory() {
        return stockPriceHistory;
    }

    public void setStockPriceHistory(ArrayList<DetailStockModel> stockPriceHistory) {
        this.stockPriceHistory = stockPriceHistory;
    }

    private double checkGainPercentage(ArrayList<DetailStockModel> stockPriceHistory){
        int historyCount=stockPriceHistory.size();
        double open=stockPriceHistory.get(0).getClose();
        double close=stockPriceHistory.get(historyCount-1).getClose();
       DecimalFormat df = new DecimalFormat("#.00");

        Log.d(TAG, "checkGainPercentage: open "+open+"+ close:"+close);
        if(open>close){

            return Double.valueOf( df.format(((open/close)*100-100)*-1));
        }
        Log.d(TAG, "checkGainPercentage: open: "+open+" close: "+close);
        return Double.valueOf( df.format((close/open)*100-100));
    }
    private double getLastestPrice(ArrayList<DetailStockModel> stockPriceHistory){
        int historyCount=stockPriceHistory.size();
        double lastestPrice=stockPriceHistory.get(historyCount-1).getClose();

        if(BigDecimal.valueOf(lastestPrice).compareTo(BigDecimal.valueOf(1))<0){

            return lastestPrice;
        }
        DecimalFormat df = new DecimalFormat("#.00");


         return Double.parseDouble ( df.format(lastestPrice));

    }


    public ArrayList<Entry> getEntryStockPriceHistory(){
        ArrayList<Entry> myArr= new ArrayList<>();
        int pos=0;
        for (DetailStockModel entry:
            stockPriceHistory ) {
            myArr.add(new Entry(pos, Float.valueOf((float) entry.getClose())));
            pos++;
        }
        return  myArr;

    }

}
