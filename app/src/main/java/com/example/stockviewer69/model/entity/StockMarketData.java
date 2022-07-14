package com.example.stockviewer69.model.entity;

import java.util.ArrayList;
import java.util.Date;


public class StockMarketData {
    public String id;
    public String symbol;
    public String name;
    public Object asset_platform_id;
    public int block_time_in_minutes;
    public String hashing_algorithm;
    public ArrayList<String> categories;
    public Object public_notice;
    public ArrayList<Object> additional_notices;
    public Description description;
    public Links links;
    public Image image;
    public String country_origin;
    public String genesis_date;
    public double sentiment_votes_up_percentage;
    public double sentiment_votes_down_percentage;
    public int market_cap_rank;
    public int coingecko_rank;
    public double coingecko_score;
    public double developer_score;
    public double community_score;
    public double liquidity_score;
    public double public_interest_score;
    public MarketData market_data;
    public PublicInterestStats public_interest_stats;
    public ArrayList<Object> status_updates;
    public Date last_updated;
}

