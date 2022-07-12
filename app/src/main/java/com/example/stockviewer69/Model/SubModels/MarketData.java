package com.example.stockviewer69.Model.SubModels;


import java.util.Date;

public class MarketData{
    public CurrentPrice current_price;
    public Object total_value_locked;
    public Object mcap_to_tvl_ratio;
    public Object fdv_to_tvl_ratio;
    public Object roi;
    public Ath ath;
    public AthChangePercentage ath_change_percentage;
    public AthDate ath_date;
    public Atl atl;
    public AtlChangePercentage atl_change_percentage;
    public MarketCap market_cap;
    public int market_cap_rank;
    public FullyDilutedValuation fully_diluted_valuation;
    public TotalVolume total_volume;
    public High24h high_24h;
    public Low24h low_24h;
    public double price_change_24h;
    public double price_change_percentage_24h;
    public double price_change_percentage_7d;
    public double price_change_percentage_14d;
    public double price_change_percentage_30d;
    public double price_change_percentage_60d;
    public double price_change_percentage_200d;
    public double price_change_percentage_1y;
    public double market_cap_change_24h;
    public double market_cap_change_percentage_24h;
    public double total_supply;
    public double max_supply;
    public double circulating_supply;
    public Date last_updated;


    // import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
    // import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
    /* ObjectMapper om = new ObjectMapper();
    Root root = om.readValue(myJsonString, Root.class); */

}