package com.example.stockviewer69.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stockviewer69.R;
import com.example.stockviewer69.model.entity.OverViewStockModel;
import com.example.stockviewer69.utils.Const;
import com.example.stockviewer69.view.activity.StockViewActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.math.BigDecimal;
import java.util.ArrayList;

public class MainStockAdapter extends RecyclerView.Adapter<MainStockAdapter.ViewHolder> {
    private final ICallBackMain mListener;
    private final ICallBackSetColor cbSetColor;
    private final ArrayList<OverViewStockModel> stockData;
    private LayoutInflater layoutInflater;

    public MainStockAdapter(ArrayList<OverViewStockModel> stockData, ICallBackMain mListener, ICallBackSetColor cbSetColor) {
        this.stockData = stockData;
        this.mListener = mListener;
        this.cbSetColor = cbSetColor;
    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = this.layoutInflater.inflate(R.layout.activity_line_chart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return stockData.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int lineColor;
        OverViewStockModel o = getItem(position);

        holder.tvStockShortName.setText(o.getStockFullName());
        holder.tvStockGain.setText((o.getStockGain() + "%"));
        holder.tvStockPrice.setText("$" + BigDecimal.valueOf(o.getStockPrice()));
        holder.setItemClickListener((view, position1, isLongClick) -> {
            new StockViewActivity().starter(view.getContext(), o);
        });
        mListener.setImageWithGlide(o.getStockShortName(), Const.ImageType.IMG_TYPE_ICON, holder.ivStockIcon);

        LineDataSet dataSet = new LineDataSet(o.getEntryStockPriceHistory(), "dataset");
        dataSet.setDrawValues(false);
        if (o.getStockGain() > 0) {
            lineColor = cbSetColor.setLineColor(R.color.greenUp);
        } else {
            holder.ivArrowIcon.setImageDrawable(holder.res);
            lineColor = cbSetColor.setLineColor(R.color.redDown);
        }
        holder.tvStockGain.setTextColor(lineColor);
        dataSet.setColor(lineColor);
        dataSet.setDrawCircles(false);
        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(dataSet);
        LineData lineData = new LineData(iLineDataSets);
        holder.lineChart.setData(lineData);
        holder.lineChart.invalidate();
        modifyLineChart(holder.lineChart);
    }

    OverViewStockModel getItem(int i) {
        return stockData.get(i);
    }

    public void clearStock() {
        this.stockData.clear();
    }

    public void addStock(OverViewStockModel o) {
        this.stockData.add(o);
    }

    void modifyLineChart(LineChart lineChart) {
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

    public interface ItemClickListener {
        void onItemClick(View view, int position, boolean isLongClick);
    }

    public interface ICallBackMain {
        void setImageWithGlide(String url, int type, ImageView stockIcon);
    }

    public interface ICallBackSetColor {
        int setLineColor(int drawable);
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvStockShortName = itemView.findViewById(R.id.stockShortName);
            tvStockPrice = itemView.findViewById(R.id.stockPrice);
            tvStockGain = itemView.findViewById(R.id.stockGain);
            ivStockIcon = itemView.findViewById(R.id.stockImage);
            lineChart = itemView.findViewById(R.id.lineChart);
            ivArrowIcon = itemView.findViewById(R.id.arrow);
            res = itemView.getResources().getDrawable(R.drawable.ic_baseline_arrow_drop_down_24);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(v, getAdapterPosition(), false);
        }
    }

}
