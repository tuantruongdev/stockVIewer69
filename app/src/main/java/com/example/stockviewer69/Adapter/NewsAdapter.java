package com.example.stockviewer69.Adapter;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.stockviewer69.Model.NewsModel;
import com.example.stockviewer69.R;
import com.example.stockviewer69.Controller.Activity.WebviewActivity;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private ArrayList<NewsModel.Article> listNews;
    Activity mActivity=null;
    Fragment mFragment=null;

    public NewsAdapter(ArrayList<NewsModel.Article> listNews, Context mContext,Fragment fragment) {
        this.listNews = listNews;
        this.mContext = mContext;
        this.mFragment= fragment;
    }
    public NewsAdapter(ArrayList<NewsModel.Article> listNews, Context mContext,Activity activity) {
        this.listNews = listNews;
        this.mContext = mContext;
        this.mActivity=activity;
    }

    private LayoutInflater layoutInflater;
    Context mContext;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       layoutInflater=LayoutInflater.from(mContext);
        View view=  this.layoutInflater.inflate(R.layout.news_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewsModel.Article news= getItem(position);
        holder.tvTitle.setText(news.title);
        holder.tvAuthor.setText(news.source.name);
        holder.tvTimeAgo.setText(news.toDuration());

        Glide.with(mContext).load(news.urlToImage)
                .placeholder(R.mipmap.default_coin)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(holder.ivNewsThumb);
        Log.d(TAG, "onBindViewHolder: "+news.urlToImage);
        
        holder.setItemClickListener(new MainStockAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position, boolean isLongClick) {
                Log.d(TAG, "onItemClick: ");
                Intent intent=new Intent(mContext, WebviewActivity.class);
                intent.putExtra("url",news.url);
                intent.putExtra("source",news.source.name);
                mContext.startActivity(intent);
                if (mActivity!=null) {
                    mActivity.overridePendingTransition(R.anim.slide_in, R.anim.slide_nothin);
                }
            }
        });
    }




    @Override
    public int getItemCount() {
        return listNews.size();
    }
    NewsModel.Article getItem(int i) {
        return listNews.get(i);
    }
    public class  ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvTitle,tvAuthor,tvTimeAgo;
        ImageView ivNewsThumb;
        private MainStockAdapter.ItemClickListener itemClickListener;
        public void setItemClickListener(MainStockAdapter.ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle=itemView.findViewById(R.id.tvNewsTitle);
            tvAuthor=itemView.findViewById(R.id.tvNewsSource);
            tvTimeAgo=itemView.findViewById(R.id.tvNewsETime);
            ivNewsThumb=itemView.findViewById(R.id.ivNewsImg);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(v,getAdapterPosition(),false);
        }
    }

        public interface ItemClickListener {
        void onItemClick(View view, int position,boolean isLongClick);
    }
}


