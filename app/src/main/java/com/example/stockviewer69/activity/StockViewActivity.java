package com.example.stockviewer69.activity;

import static android.content.ContentValues.TAG;
import static com.example.stockviewer69.model.IRetrofitApiFetch.gson;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.stockviewer69.R;
import com.example.stockviewer69.adapter.MainStockAdapter;
import com.example.stockviewer69.adapter.NewsAdapter;
import com.example.stockviewer69.model.ApiFetch;
import com.example.stockviewer69.model.entity.MarketChartModel;
import com.example.stockviewer69.model.entity.NewsModel;
import com.example.stockviewer69.model.entity.OverViewStockModel;
import com.example.stockviewer69.model.entity.StockMarketData;
import com.example.stockviewer69.utils.Const;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Locale;

public class StockViewActivity extends AppCompatActivity implements MainStockAdapter.ICallBackMain,View.OnClickListener {
    private final int DAY = 1;
    private final int WEEK = 7;
    private final int MONTH = 30;
    private final int THREE_MONTH = 90;
    private final int YEAR = 365;
    private final long TIME_OF_DAY = 86400;
    private final long TIME_OF_HOUR = 3600;

    ArrayList<NewsModel.Article> news = new ArrayList<>();

    ImageView back, stockIcon;
    TextView stockShortName, stockFullName, stockPrice, stockGain, marketCap, fullyDilCap, s24hHigh, s24hLow, totalMaxSupply, marketRank, ath, atl, marketRank2,
            s1h, s24h, s7d, s30d, s90d, s1y, sAt;
    LineChart lineChart;
    LinearLayout gainLayout;
    double stockGainScore;
    OverViewStockModel overViewStockModel;
    RecyclerView newsRecyclerView;
    NewsAdapter newsAdapter;

    public void starter(Context context, OverViewStockModel o) {
        Intent intent = new Intent(context, StockViewActivity.class);
        intent.putExtra("stockShortName", o.getStockShortName());
        intent.putExtra("stockFullName", o.getStockFullName());
        intent.putExtra("stockPrice", BigDecimal.valueOf(o.getStockPrice()) + " USD");
        intent.putExtra("stockGain", o.getStockGain());
        intent.putExtra("stockId", o.getStockId());
        intent.putExtra("stockMarketChart", gson.toJson(o));

        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_view);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.primaryDark));
        }

        overridePendingTransition(R.anim.slide_in, R.anim.slide_nothin);

        back = findViewById(R.id.back);
        stockShortName = findViewById(R.id.stockShortName);
        stockFullName = findViewById(R.id.stockFullName);
        stockPrice = findViewById(R.id.stockPrice);
        lineChart = findViewById(R.id.lineChart);
        stockGain = findViewById(R.id.stockGain);
        gainLayout = findViewById(R.id.layoutGain);
        marketCap = findViewById(R.id.marketCap);
        fullyDilCap = findViewById(R.id.fullyDilutedValuation);
        s24hHigh = findViewById(R.id.stats24hHigh);
        s24hLow = findViewById(R.id.stats24hLow);
        totalMaxSupply = findViewById(R.id.maxSupply);
        marketRank = findViewById(R.id.marketCapRank);
        marketRank2 = findViewById(R.id.marketCapRank2);
        ath = findViewById(R.id.ath);
        atl = findViewById(R.id.atl);
        stockIcon = findViewById(R.id.stockIcon);
        s1h = findViewById(R.id.stats1h);
        s24h = findViewById(R.id.stats24h);
        s7d = findViewById(R.id.stats7d);
        s30d = findViewById(R.id.stats30d);
        s90d = findViewById(R.id.stats90d);
        s1y = findViewById(R.id.stats1y);
        sAt = findViewById(R.id.statsAt);

        newsRecyclerView = findViewById(R.id.newsRecycleView);
        newsAdapter = new NewsAdapter(news, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(StockViewActivity.this);
        newsRecyclerView.setAdapter(newsAdapter);
        newsRecyclerView.setLayoutManager(linearLayoutManager);

        bind();
        initial();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_nothin, R.anim.slide_out);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.stats1h:
                clickChangeTime(TIME_OF_HOUR, (TextView) v);
            case R.id.stats24h:
                clickChangeTime(DAY * TIME_OF_DAY, (TextView) v);
            case R.id.stats7d:
                clickChangeTime(WEEK * TIME_OF_DAY, (TextView) v);
            case R.id.stats30d:
                clickChangeTime(MONTH * TIME_OF_DAY, (TextView) v);
            case R.id.stats90d:
                clickChangeTime(THREE_MONTH * TIME_OF_DAY, (TextView) v);
            case R.id.stats1y:
                clickChangeTime(YEAR * TIME_OF_DAY, (TextView) v);


        }

    }
    ///????????
    @Override
    public void setImageWithGlide(String url, int type, ImageView stockIcon) {
        String fullUrl;
        if (type == Const.ImageType.IMG_TYPE_ICON) {
            fullUrl = String.format(Const.ICON_API_URL + url.toLowerCase() + "/200");
        } else {
            fullUrl = url;
        }
        Glide.with(this).load(fullUrl)
                .placeholder(R.mipmap.default_coin)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(stockIcon);
    }

    private void bind() {
        back.setOnClickListener(v -> onBackPressed());
        s1h.setOnClickListener(this);
        s24h.setOnClickListener(this);
        s7d.setOnClickListener(this);
        s30d.setOnClickListener(this);
        s90d.setOnClickListener(this);
        s1y.setOnClickListener(this);
    }

    private void clickChangeTime(Long time, TextView tvTime) {
        Intent intent = getIntent();

            try {
                ApiFetch apiFetch = new ApiFetch();
                apiFetch.getMarketChartData(intent.getStringExtra("stockId"), "usd", String.valueOf(System.currentTimeMillis() / 1000 - 1000 - time), this);

                resetTimeBtn();
                tvTime.setBackground(getDrawable(R.drawable.card_view_bg_pure));
            } catch (Exception e) {
                e.printStackTrace();
            }

    }

    private void resetTimeBtn() {
        s1h.setBackground(getDrawable(R.color.darker_gray));
        s24h.setBackground(getDrawable(R.color.darker_gray));
        s7d.setBackground(getDrawable(R.color.darker_gray));
        s30d.setBackground(getDrawable(R.color.darker_gray));
        s90d.setBackground(getDrawable(R.color.darker_gray));
        s1y.setBackground(getDrawable(R.color.darker_gray));
        sAt.setBackground(getDrawable(R.color.darker_gray));
    }

    private void initial() {
        Intent intent = getIntent();
        stockShortName.setText(intent.getStringExtra("stockShortName"));
        stockFullName.setText(intent.getStringExtra("stockFullName"));
        stockPrice.setText(intent.getStringExtra("stockPrice"));
        stockGainScore = intent.getDoubleExtra("stockGain", 0);
        String stockMarketChart = intent.getStringExtra("stockMarketChart");
        overViewStockModel = gson.fromJson(stockMarketChart, OverViewStockModel.class);
        updateMarketChart(overViewStockModel);

        if (!(stockGainScore < 0)) {
            gainLayout.setBackground(getDrawable(R.drawable.card_view_bg_green));
        }
        stockGain.setText(stockGainScore + "%");
        try {
            ApiFetch apiFetch = new ApiFetch();
            //  apiFetch.getMarketChartData(intent.getStringExtra("stockId"),"usd",this);
            apiFetch.getMarketData(intent.getStringExtra("stockId"), this);
            apiFetch.getArticle(overViewStockModel.getStockFullName(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateMarketData(StockMarketData stockMarketData) {
        marketCap.setText(truncateNumber(stockMarketData.market_data.market_cap.usd) + " $");
        fullyDilCap.setText(truncateNumber(stockMarketData.market_data.fully_diluted_valuation.usd) + " $");
        ath.setText(stockMarketData.market_data.ath.usd + " $");
        atl.setText(stockMarketData.market_data.atl.usd + " $");
        s24hHigh.setText(stockMarketData.market_data.high_24h.usd + " $");
        s24hLow.setText(stockMarketData.market_data.low_24h.usd + " $");
        marketRank.setText("#" + stockMarketData.market_data.market_cap_rank);
        marketRank2.setText("#" + stockMarketData.market_data.market_cap_rank);
        totalMaxSupply.setText(truncateNumber(stockMarketData.market_data.total_supply));
        Glide.with(StockViewActivity.this).load(stockMarketData.image.large
                        .toLowerCase(Locale.ROOT) + "/200")
                .placeholder(R.mipmap.default_coin)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(stockIcon);

    }

    public String truncateNumber(double floatNumber) {
        long million = 1000000L;
        long billion = 1000000000L;
        long trillion = 1000000000000L;

        long number = Math.round(floatNumber);
        if ((number >= million) && (number < billion)) {
            float fraction = calculateFraction(number, million);
            return (fraction) + "M";
        } else if ((number >= billion) && (number < trillion)) {
            float fraction = calculateFraction(number, billion);
            return (fraction) + "B";
        } else if (number > billion) {
            float fraction = calculateFraction(number, trillion);
            return (fraction) + "T";
        }
        return Long.toString(number);
    }

    public float calculateFraction(long number, long divisor) {
        long truncate = (number * 10L + (divisor / 2L)) / divisor;
        float fraction = (float) truncate * 0.10F;
        return fraction;
    }

    public void updateMarketChart(OverViewStockModel marketChartModel) {
        ArrayList<Entry> entries;
        entries = marketChartModel.getEntryStockPriceHistory();
        LineDataSet dataSet = new LineDataSet(entries, "dataset");
        int lineColor;
        if (stockGainScore < 0) {
            lineColor = ContextCompat.getColor(getApplicationContext(), R.color.redDown);
        } else {
            lineColor = ContextCompat.getColor(getApplicationContext(), R.color.greenUp);
        }

        stockPrice.setText(entries.get(entries.size() - 1).getY() + " USD");
        dataSet.setColor(lineColor);
        dataSet.setDrawCircles(false);
        dataSet.setDrawValues(false);
        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(dataSet);
        LineData lineData = new LineData(iLineDataSets);
        lineChart.setData(lineData);
        lineChart.invalidate();
        modifyLineChart(lineChart);

    }

    public void updateMarketChartByTime(MarketChartModel marketChartModel) {
        ArrayList<Entry> entries = new ArrayList<>();

        try {
            for (int i = 0; i < marketChartModel.getPrices().size(); i++) {
                entries.add(new Entry(i, Float.valueOf(marketChartModel.getPrices().get(i).get(1).toString())));
            }

            LineDataSet dataSet = new LineDataSet(entries, "dataset");
            int lineColor;
            if (stockGainScore < 0) {
                lineColor = ContextCompat.getColor(getApplicationContext(), R.color.redDown);
            } else {
                lineColor = ContextCompat.getColor(getApplicationContext(), R.color.greenUp);
            }

            stockPrice.setText(String.valueOf(entries.get(entries.size() - 1).getY()) + " USD");
            dataSet.setColor(lineColor);
            dataSet.setDrawCircles(false);
            dataSet.setDrawValues(false);
            ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
            iLineDataSets.add(dataSet);
            LineData lineData = new LineData(iLineDataSets);
            lineChart.setData(lineData);
            lineChart.invalidate();
            modifyLineChart(lineChart);
        } catch (Exception e) {
            Log.d(TAG, "updateMarketChartByTime: error");
            Toast.makeText(getApplicationContext(), "Too many request, try again later", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateListNews(ArrayList<NewsModel.Article> article) {
        // news=article;
        for (int i = 0; i < article.size() - 1; i++) {
            news.add(article.get(i));
            Log.d(TAG, "updateListNews: " + article.get(i).title);
        }
        newsAdapter.notifyDataSetChanged();
    }

    void modifyLineChart(LineChart lineChart) {
        lineChart.setNoDataText("No data available");

        lineChart.setTouchEnabled(true);
        lineChart.setClickable(true);
        //    lineChart.setDoubleTapToZoomEnabled(false);
        //  lineChart.setDoubleTapToZoomEnabled(false);

        // lineChart.setDrawBorders(true);
        //lineChart.setDrawGridBackground(true);

        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(false);

//        lineChart.getAxisLeft().setDrawGridLines(false);
        //  lineChart.getAxisLeft().setDrawLabels(false);
//        lineChart.getAxisLeft().setDrawAxisLine(false);

        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getXAxis().setDrawLabels(false);
        lineChart.getXAxis().setDrawAxisLine(false);

        //  lineChart.getAxisRight().setDrawGridLines(false);
        lineChart.getAxisRight().setDrawLabels(false);
        //lineChart.getAxisRight().setDrawAxisLine(false);
    }


}