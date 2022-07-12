//package com.example.stockviewer69.Model;
//
//import androidx.annotation.NonNull;
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//
//import java.util.ArrayList;
//
//public class RawOverViewStockModelnews {
//    @JsonProperty("master")
//    public ArrayList<ArrayList<Object>> getMaster() {
//        return this.master; }
//    public void setMaster(ArrayList<ArrayList<Object>> master) {
//        this.master = master; }
//    ArrayList<ArrayList<Object>> master;
//
//    @NonNull
//    public static ArrayList<DetailStockModel> convertToDetailStockModel(RawOverViewStockModelnews raw){
//        ArrayList<DetailStockModel> list=new ArrayList<>();
//
//      //  Log.d(TAG, "convertToDetailStockModel: "+ raw.master.size());
//        for (int i = 0; i < raw.master.size() ; i++) {
//
//            list.add(new DetailStockModel( (Double) raw.master.get(i).get(0), Double.parseDouble((String)   raw.master.get(i).get(1)), Double.parseDouble((String)  raw.master.get(i).get(2)), Double.parseDouble((String)  raw.master.get(i).get(3)), Double.parseDouble((String)  raw.master.get(i).get(4)), Double.parseDouble((String)  raw.master.get(i).get(5)), (Double) raw.master.get(i).get(6), Double.parseDouble((String)  raw.master.get(i).get(7)),(Double) raw.master.get(i).get(8),Double.parseDouble((String)  raw.master.get(i).get(9)),Double.parseDouble((String)  raw.master.get(i).get(10)),Double.parseDouble((String)  raw.master.get(i).get(11))));
//
//          //  list.add(new DetailStockModel( (Double) raw.master.get(i).get(0), Double.parseDouble((String)  raw.master.get(i).get(1)),1,1,1,1,1,1,1,1,1,1));
//
//        }
//
//        return list;
//    }
//
//}
//
//
