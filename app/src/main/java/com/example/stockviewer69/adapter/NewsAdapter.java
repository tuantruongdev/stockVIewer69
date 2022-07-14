package com.example.stockviewer69.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stockviewer69.R;
import com.example.stockviewer69.controller.activity.WebviewActivity;
import com.example.stockviewer69.model.entity.NewsModel;
import com.example.stockviewer69.utils.Const;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    MainStockAdapter.ICallBackMain mListener;
    private ArrayList<NewsModel.Article> listNews;
    private LayoutInflater layoutInflater;

    public NewsAdapter(ArrayList<NewsModel.Article> listNews, MainStockAdapter.ICallBackMain mListener) {
        this.mListener = mListener;
        this.listNews = listNews;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = this.layoutInflater.inflate(R.layout.news_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewsModel.Article news = getItem(position);
        holder.title.setText(news.title);
        holder.author.setText(news.source.name);
        holder.timeAgo.setText(news.toDuration());

        mListener.setImageWithGlide(news.urlToImage, Const.ImageType.IMG_TYPE_IMAGE, holder.newsThumb);
        holder.setItemClickListener((view, position1, isLongClick) -> new WebviewActivity().starter(view.getContext(), news));
    }

    @Override
    public int getItemCount() {
        return listNews.size();
    }

    NewsModel.Article getItem(int i) {
        return listNews.get(i);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, author, timeAgo;
        ImageView newsThumb;
        private MainStockAdapter.ItemClickListener itemClickListener;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.newsTitle);
            author = itemView.findViewById(R.id.newsSource);
            timeAgo = itemView.findViewById(R.id.newsETime);
            newsThumb = itemView.findViewById(R.id.newsImg);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(v, getAdapterPosition(), false);
        }

        public void setItemClickListener(MainStockAdapter.ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
    }
}


