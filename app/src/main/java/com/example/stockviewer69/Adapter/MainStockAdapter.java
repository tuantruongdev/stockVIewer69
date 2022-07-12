package com.example.stockviewer69.Adapter;

import static com.example.stockviewer69.Model.IRetrofitApiFetch.gson;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.stockviewer69.Model.OverViewStockModel;
import com.example.stockviewer69.R;
import com.example.stockviewer69.Controller.Activity.StockViewActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Locale;

public class MainStockAdapter extends RecyclerView.Adapter<MainStockAdapter.ViewHolder>{

    Activity mActivity;
    private ArrayList<OverViewStockModel> stockData;
    private LayoutInflater layoutInflater;
    private ItemClickListener mClickListener;
    Context mContext;

    public MainStockAdapter(ArrayList<OverViewStockModel> stockData, Context context, Activity activity) {
        this.stockData = stockData;
        this.mContext=context;
        this.mActivity=activity;
    }



    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater=LayoutInflater.from(mContext);
        View view=  this.layoutInflater.inflate(R.layout.activity_line_chart,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        OverViewStockModel o= getItem(position);
        holder.tvStockShortName.setText(o.getStockFullName());
        holder.tvStockGain.setText(String.valueOf( o.getStockGain()+"%"));
        holder.tvStockPrice.setText(String.valueOf( "$"+ BigDecimal.valueOf( o.getStockPrice())));
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position, boolean isLongClick) {

                Intent intent=new Intent(mContext, StockViewActivity.class);
                intent.putExtra("stockShortName",o.getStockShortName());
                intent.putExtra("stockFullName",o.getStockFullName());
                intent.putExtra("stockPrice",BigDecimal.valueOf( o.getStockPrice())+" USD");
                intent.putExtra("stockGain",o.getStockGain());
                intent.putExtra("stockId",o.getStockId());
                intent.putExtra("stockMarketChart",gson.toJson(o));

                mContext.startActivity(intent);
                mActivity.overridePendingTransition(R.anim.slide_in,R.anim.slide_nothin);

           //     Toast.makeText(mContext, " "+stockData.get(position), Toast.LENGTH_SHORT).show();
            }



        });


        Glide.with(mContext).load("https://cryptoicons.org/api/icon/"+o.getStockShortName()
                .toLowerCase(Locale.ROOT)+"/200")
                .placeholder(R.mipmap.default_coin)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(holder.ivStockIcon);

        LineDataSet dataSet= new LineDataSet(o.getEntryStockPriceHistory(),"dataset");
        dataSet.setDrawValues(false);
        int lineColor;
        int cricleColor;
        if(o.getStockGain()>0){
             lineColor = ContextCompat.getColor(mContext, R.color.greenUp);
             cricleColor= ContextCompat.getColor(mContext, R.color.greenUp);


        }else{

            holder.ivArrowIcon.setImageDrawable(holder.res);
            lineColor = ContextCompat.getColor(mContext, R.color.redDown);
           cricleColor= ContextCompat.getColor(mContext, R.color.redDown);
        }
        holder.tvStockGain.setTextColor(lineColor);
        dataSet.setColor(lineColor);
        //dataSet.setCircleColor(cricleColor);
        dataSet.setDrawCircles(false);
        ArrayList<ILineDataSet> iLineDataSets= new ArrayList<>();
        iLineDataSets.add(dataSet);

        LineData lineData =new LineData(iLineDataSets);
        holder.lineChart.setData(lineData);
        holder.lineChart.invalidate();
        modifyLineChart(holder.lineChart);


      
    }
    void modifyLineChart(LineChart lineChart){
        lineChart.setNoDataText("No data available");

        lineChart.setTouchEnabled(true);
        lineChart.setClickable(false);
        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.setDoubleTapToZoomEnabled(false);

        lineChart.setDrawBorders(false);
        lineChart.setDrawGridBackground(false);

        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(false);

        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getAxisLeft().setDrawLabels(false);
        lineChart.getAxisLeft().setDrawAxisLine(false);

        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getXAxis().setDrawLabels(false);
        lineChart.getXAxis().setDrawAxisLine(false);

        lineChart.getAxisRight().setDrawGridLines(false);
        lineChart.getAxisRight().setDrawLabels(false);
        lineChart.getAxisRight().setDrawAxisLine(false);



    }




    @Override
    public int getItemCount() {
        return stockData.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvStockShortName;
        TextView tvStockPrice;
        TextView tvStockGain;
        LineChart lineChart;
        ImageView ivStockIcon;
        ImageView ivArrowIcon;
        Drawable res;
        private ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(v,getAdapterPosition(),false);
        }

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
           
            tvStockShortName=itemView.findViewById(R.id.tvStockShortName);
            tvStockPrice=itemView.findViewById(R.id.tvStockPrice);
            tvStockGain=itemView.findViewById(R.id.tvStockGain);
            ivStockIcon=itemView.findViewById(R.id.ivStockImage);
            lineChart=itemView.findViewById(R.id.lineChart);
            ivArrowIcon=itemView.findViewById(R.id.ivArrow);
            res=itemView.getResources().getDrawable(R.drawable.ic_baseline_arrow_drop_down_24);
            itemView.setOnClickListener(this);

        }


    }
    OverViewStockModel getItem(int i) {
        return stockData.get(i);
    }
    // allows clicks events to be caught


    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position,boolean isLongClick);
    }

}
