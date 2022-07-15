package com.example.stockviewer69.utils;

public interface Const {
    public final String COINGECKO_API_URL="https://api.coingecko.com/api/v3/";
    public final String NEWS_API_URL="https://newsapi.org/v2/";
    public final String ICON_API_URL="https://cryptoicons.org/api/icon/";
    public interface Key{
        public final String STOCK_SHORT_NAME="stockShortName";
        public final String STOCK_FULL_NAME="stockFullName";
        public final String STOCK_PRICE="stockPrice";
        public final String STOCK_GAIN="stockGain";
        public final String STOCK_ID="stockId";
        public final String STOCK_MARKET_CHART="stockMarketChart";
        public final String URL="url";
        public final String SOURCE="source";
    }
    public interface Type{
        public final int TOP_10_STOCK = 1001;
        public final int TOP_GAIN_STOCK = 1002;
        public final int TOP_LOSE_STOCK = 1003;
    }

    public interface ImageType {
        public final int IMG_TYPE_ICON = 1;
        public final int IMG_TYPE_IMAGE= 2;
    }
}
