package com.example.stockviewer69.Model;

import java.util.ArrayList;
import java.util.Date;

public class NewsModel {
    private String status;
    private int totalResults;
    private ArrayList<Article> articles;

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

    public NewsModel(String status, int totalResults, ArrayList<Article> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public static class Article{
        public Source source;

        public Article(Source source, String author, String title, String description, String url, String urlToImage, Date publishedAt, String content) {
            this.source = source;
            this.author = author;
            this.title = title;
            this.description = description;
            this.url = url;
            this.urlToImage = urlToImage;
            this.publishedAt = publishedAt;
            this.content = content;
        }

        public String author;
        public String title;
        public String description;
        public String url;
        public String urlToImage;
        public Date publishedAt;
        public String content;
    }





    public static class Source{
        public Source(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String id;
        public String name;
    }
}
