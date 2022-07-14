package com.example.stockviewer69.model.entity;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class RawOverViewStockModel {


    @NonNull
    public static ArrayList<DetailStockModel> convertToDetailStockModel(MarketChartModel raw) {
        ArrayList<DetailStockModel> list = new ArrayList<>();

        //  Log.d(TAG, "convertToDetailStockModel: "+ raw.master.size());
        for (int i = 0; i < raw.getPrices().size(); i++) {

            list.add(new DetailStockModel(raw.getPrices().get(i).get(0), raw.getPrices().get(i).get(1)));


            //  list.add(new DetailStockModel( (Double) raw.master.get(i).get(0), Double.parseDouble((String)  raw.master.get(i).get(1)),1,1,1,1,1,1,1,1,1,1));

        }

        return list;
    }

}


