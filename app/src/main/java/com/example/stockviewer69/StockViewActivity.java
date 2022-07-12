package com.example.stockviewer69;

import static android.content.ContentValues.TAG;

import static com.example.stockviewer69.Model.IRetrofitApiFetch.gson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.stockviewer69.Adapter.NewsAdapter;
import com.example.stockviewer69.Model.ApiFetch;
import com.example.stockviewer69.Model.MarketChartModel;
import com.example.stockviewer69.Model.NewsModel;
import com.example.stockviewer69.Model.OverViewStockModel;
import com.example.stockviewer69.Model.StockMarketData;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.JsonElement;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class StockViewActivity extends AppCompatActivity {
    ImageView ivBack,ivStockIcon;
    TextView tvStockShortName,tvStockFullName,tvStockPrice,tvStockGain,tvMarketCap,tvFullyDilCap,tv24hHigh,tv24hLow,tvTotalMaxSupply,tvMarketRank,tvAth,tvAtl,tvMarketRank2,
    tv1h,tv24h,tv7d,tv30d,tv90d,tv1y,tvAt;
    LineChart lineChart;
    LinearLayout gainLayout;
    double stockGain;
    OverViewStockModel overViewStockModel;
    StockViewActivity stockViewActivity;
    RecyclerView newsRecyclerView;
    NewsAdapter newsAdapter;
    ArrayList<NewsModel.Article> news=new ArrayList<>();
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
        ivBack=findViewById(R.id.ivBackBtn);
        tvStockShortName=findViewById(R.id.a_stockShortName);
        tvStockFullName=findViewById(R.id.a_tv_StockFullName);
        tvStockPrice=findViewById(R.id.a_stockPrice);
        lineChart=findViewById(R.id.a_lineChart);
        tvStockGain=findViewById(R.id.a_tvStockGain);
        gainLayout=findViewById(R.id.a_layoutGain);
        tvMarketCap=findViewById(R.id.tvMarketCap);
        tvFullyDilCap=findViewById(R.id.tv_fully_diluted_valuation);
        tv24hHigh=findViewById(R.id.tv24h_High);
        tv24hLow=findViewById(R.id.tv2h_Low);
        tvTotalMaxSupply=findViewById(R.id.tv_Max_Supply);
        tvMarketRank=findViewById(R.id.tv_Market_Cap_Rank);
        tvMarketRank2=findViewById(R.id.tv_Market_Cap_Rank2);
        tvAth=findViewById(R.id.tv_ath);
        tvAtl=findViewById(R.id.tv_atl);
        ivStockIcon=findViewById(R.id.ivStockIcon);
        tv1h=findViewById(R.id.tv1h);
        tv24h=findViewById(R.id.tv24h);
        tv7d=findViewById(R.id.tv7d);
        tv30d=findViewById(R.id.tv30d);
        tv90d=findViewById(R.id.tv90d);
        tv1y=findViewById(R.id.tv1y);
        tvAt=findViewById(R.id.tvAt);
        stockViewActivity=this;

        newsRecyclerView=findViewById(R.id.newsRecycleView);
        newsAdapter=new NewsAdapter(news,StockViewActivity.this,stockViewActivity);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(StockViewActivity.this);
        newsRecyclerView.setAdapter(newsAdapter);
        newsRecyclerView.setLayoutManager(linearLayoutManager);
        bind();
        initial();
      //  resetTimeBtn();

    }
    private void bind(){

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tv1h.setOnClickListener(onClickListener((long) (3600),tv1h));
        tv24h.setOnClickListener(onClickListener((long) (1*86400),tv24h));
        tv7d.setOnClickListener(onClickListener((long) (7*86400),tv7d));
        tv30d.setOnClickListener(onClickListener((long) (30*86400),tv30d));
        tv90d.setOnClickListener(onClickListener((long) (90*86400),tv90d));
        tv1y.setOnClickListener(onClickListener((long) (365*86400),tv1y));




    }
    private  View.OnClickListener onClickListener(Long time,TextView tvTime) {
        Intent intent= getIntent();

       return new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               try {
                   ApiFetch  apiFetch = new ApiFetch();
                   apiFetch.getMarketChartData(intent.getStringExtra("stockId"),"usd",String.valueOf(System.currentTimeMillis()/1000 - 1000 - time),stockViewActivity);

                   resetTimeBtn();
                   tvTime.setBackground(getDrawable(R.drawable.card_view_bg_pure));
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }
       };
    };
    private void resetTimeBtn(){
        tv1h.setBackground( getDrawable(R.color.darker_gray));
        tv24h.setBackground( getDrawable(R.color.darker_gray));
        tv7d.setBackground( getDrawable(R.color.darker_gray));
        tv30d.setBackground( getDrawable(R.color.darker_gray));
        tv90d.setBackground( getDrawable(R.color.darker_gray));
        tv1y.setBackground( getDrawable(R.color.darker_gray));
        tvAt.setBackground( getDrawable(R.color.darker_gray));
    }

    private void initial(){
        Intent intent=getIntent();
        tvStockShortName.setText(intent.getStringExtra("stockShortName"));
        tvStockFullName.setText(intent.getStringExtra("stockFullName"));
        tvStockPrice.setText(intent.getStringExtra("stockPrice"));
         stockGain=intent.getDoubleExtra("stockGain",0);
         String stockMarketChart=intent.getStringExtra("stockMarketChart");
        overViewStockModel= gson.fromJson(stockMarketChart, OverViewStockModel.class);
        //Log.d(TAG, "initial: "+overViewStockModel.getStockId());
        updateMarketChart(overViewStockModel);
        if(!(stockGain<0)){
            gainLayout.setBackground( getDrawable( R.drawable.card_view_bg_green));
        }
        tvStockGain.setText(stockGain+"%");
        try {
            ApiFetch apiFetch= new ApiFetch();
          //  apiFetch.getMarketChartData(intent.getStringExtra("stockId"),"usd",this);
            apiFetch.getMarketData(intent.getStringExtra("stockId"),this);
            apiFetch.getArticle(overViewStockModel.getStockFullName(),this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updateMarketData(StockMarketData stockMarketData){
    //    Log.d(TAG, "updateMarketData: "+stockMarketData.name);
        //tvMarketCap,tvFullyDilCap,tv24hHigh,tv24hLow,tvTotalMaxSupply,tvMarketRank,tvAth,tvAtl,tvMarketRank2;
        tvMarketCap.setText(truncateNumber(stockMarketData.market_data.market_cap.usd) +" $");

        tvFullyDilCap.setText(truncateNumber(stockMarketData.market_data.fully_diluted_valuation.usd)+" $");
        tvAth.setText(String.valueOf(stockMarketData.market_data.ath.usd)+" $");
        tvAtl.setText(String.valueOf(stockMarketData.market_data.atl.usd)+" $");
        tv24hHigh.setText(String.valueOf(stockMarketData.market_data.high_24h.usd)+" $");
        tv24hLow.setText(String.valueOf(stockMarketData.market_data.low_24h.usd)+" $");
        tvMarketRank.setText("#"+String.valueOf(stockMarketData.market_data.market_cap_rank));
        tvMarketRank2.setText("#"+String.valueOf(stockMarketData.market_data.market_cap_rank));
        tvTotalMaxSupply.setText(truncateNumber(stockMarketData.market_data.total_supply));
        Glide.with(StockViewActivity.this).load(stockMarketData.image.large
                        .toLowerCase(Locale.ROOT)+"/200")
                .placeholder(R.mipmap.default_coin)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(ivStockIcon);

    }


    public String truncateNumber(double floatNumber) {
        long million = 1000000L;
        long billion = 1000000000L;
        long trillion = 1000000000000L;

        long number = Math.round(floatNumber);
        if ((number >= million) && (number < billion)) {
            float fraction = calculateFraction(number, million);
            return Float.toString(fraction) + "M";
        } else if ((number >= billion) && (number < trillion)) {
            float fraction = calculateFraction(number, billion);
            return Float.toString(fraction) + "B";
        }else if(number>billion){
            float fraction = calculateFraction(number, trillion);
            return Float.toString(fraction) + "T";
        }
        return Long.toString(number);
    }

    public float calculateFraction(long number, long divisor) {
        long truncate = (number * 10L + (divisor / 2L)) / divisor;
        float fraction = (float) truncate * 0.10F;
        return fraction;
    }

    public void updateMarketChart(OverViewStockModel marketChartModel){
      //  Log.d(TAG, "updateMarketChart: "+marketChartModel.getPrices().get(0).get(1));
        ArrayList<Entry> entries=new ArrayList<>();
        entries=marketChartModel.getEntryStockPriceHistory();
        LineDataSet dataSet= new LineDataSet(entries,"dataset");
        int lineColor;
        if(stockGain<0){
            lineColor = ContextCompat.getColor(getApplicationContext(), R.color.redDown);
        }else {
            lineColor = ContextCompat.getColor(getApplicationContext(), R.color.greenUp);
        }

        tvStockPrice.setText(String.valueOf(entries.get(entries.size()-1).getY()) +" USD");
        dataSet.setColor(lineColor);
        dataSet.setDrawCircles(false);
        dataSet.setDrawValues(false);
        ArrayList<ILineDataSet> iLineDataSets= new ArrayList<>();
        iLineDataSets.add(dataSet);
        LineData lineData =new LineData(iLineDataSets);
        lineChart.setData(lineData);
        lineChart.invalidate();
        modifyLineChart(lineChart);

    }

    public void updateMarketChartByTime(MarketChartModel marketChartModel){
        //  Log.d(TAG, "updateMarketChart: "+marketChartModel.getPrices().get(0).get(1));
        ArrayList<Entry> entries=new ArrayList<>();

        try {
            for (int i = 0; i<marketChartModel.getPrices().size(); i++){
                entries.add(new Entry(i, Float.valueOf( marketChartModel.getPrices().get(i).get(1).toString())));
            }

        LineDataSet dataSet= new LineDataSet(entries,"dataset");
        int lineColor;
        if(stockGain<0){
            lineColor = ContextCompat.getColor(getApplicationContext(), R.color.redDown);
        }else {
            lineColor = ContextCompat.getColor(getApplicationContext(), R.color.greenUp);
        }

        tvStockPrice.setText(String.valueOf(entries.get(entries.size()-1).getY()) +" USD");
        dataSet.setColor(lineColor);
        dataSet.setDrawCircles(false);
        dataSet.setDrawValues(false);
        ArrayList<ILineDataSet> iLineDataSets= new ArrayList<>();
        iLineDataSets.add(dataSet);
        LineData lineData =new LineData(iLineDataSets);
        lineChart.setData(lineData);
        lineChart.invalidate();
        modifyLineChart(lineChart);
        }catch (Exception e){
            Log.d(TAG, "updateMarketChartByTime: error");
            Toast.makeText(getApplicationContext(),"Too many request, try again later",Toast.LENGTH_SHORT).show();
        }
    }
    public void updateListNews(ArrayList<NewsModel.Article> article){

       // news=article;
        for (int i = 0; i < article.size()-1; i++) {
            news.add(article.get(i));
            Log.d(TAG, "updateListNews: "+article.get(i).title);
        }
        newsAdapter.notifyDataSetChanged();

    }


    void modifyLineChart(LineChart lineChart){
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



        //overridePendingTransition(R.anim.slide_in,R.anim.slide_out);

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_nothin,R.anim.slide_out);
    }
}