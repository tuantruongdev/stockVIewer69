package com.example.stockviewer69.model.entity;

import static android.content.ContentValues.TAG;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class NewsModel {
    private String status;
    private int totalResults;
    private ArrayList<Article> articles;

    public NewsModel(String status, int totalResults, ArrayList<Article> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }

    public static class Article {
        public Source source;
        public String author;
        public String title;
        public String description;
        public String url;
        public String urlToImage;
        public String publishedAt;
        public String content;

        public Article(Source source, String author, String title, String description, String url, String urlToImage, String publishedAt, String content) {
            this.source = source;
            this.author = author;
            this.title = title;
            this.description = description;
            this.url = url;
            this.urlToImage = urlToImage;
            this.publishedAt = publishedAt;
            this.content = content;
        }

        public Long getTimeStamp() {
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                Date past = format.parse(this.publishedAt);
                Date current = new Date();
                return current.getTime() / 1000 - past.getTime() / 1000;
            } catch (Exception j) {
                j.printStackTrace();
            }
            return Long.valueOf(0);
        }

        public String toDuration() {
            List<Long> times = new ArrayList<>();
            times.add(TimeUnit.DAYS.toMillis(365) / 1000);
            times.add(TimeUnit.DAYS.toMillis(30) / 1000);
            times.add(TimeUnit.DAYS.toMillis(1) / 1000);
            times.add(TimeUnit.HOURS.toMillis(1) / 1000);
            times.add(TimeUnit.MINUTES.toMillis(1) / 1000);
            times.add(TimeUnit.SECONDS.toMillis(1) / 1000);
            List<String> timesString = new ArrayList<>();

            timesString.add("year");
            timesString.add("month");
            timesString.add("day");
            timesString.add("hour");
            timesString.add("minute");
            timesString.add("second");


            long duration = getTimeStamp();

            StringBuffer res = new StringBuffer();
            for (int i = 0; i < times.size(); i++) {
                Long current = times.get(i);
                long temp = duration / current;
                if (temp > 0) {
                    res.append(temp).append(" ").append(timesString.get(i)).append(temp != 1 ? "s" : "").append(" ago");
                    break;
                }
            }
            if ("".equals(res.toString()))
                return "0 seconds ago";
            else
                Log.d(TAG, "toDuration: " + res);
            return res.toString();
        }
    }


    public static class Source {
        public String id;
        public String name;

        public Source(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }


}
