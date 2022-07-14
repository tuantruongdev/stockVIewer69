package com.example.stockviewer69.model.entity;

import java.util.ArrayList;

public class ResponseQuery {
    public int queryCount;
    public int resultsCount;
    public boolean adjusted;
    public ArrayList<Result> results;
    public String status;
    public String request_id;
    public int count;
    public ResponseQuery(int queryCount, int resultsCount, boolean adjusted, ArrayList<Result> results, String status, String request_id, int count) {
        this.queryCount = queryCount;
        this.resultsCount = resultsCount;
        this.adjusted = adjusted;
        this.results = results;
        this.status = status;
        this.request_id = request_id;
        this.count = count;
    }

    public int getQueryCount() {
        return queryCount;
    }

    public void setQueryCount(int queryCount) {
        this.queryCount = queryCount;
    }

    public int getResultsCount() {
        return resultsCount;
    }

    public void setResultsCount(int resultsCount) {
        this.resultsCount = resultsCount;
    }

    public boolean isAdjusted() {
        return adjusted;
    }

    public void setAdjusted(boolean adjusted) {
        this.adjusted = adjusted;
    }

    public ArrayList<Result> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public class Result {
        public String T;
        public double v;
        public double vw;
        public double o;
        public double c;
        public double h;
        public double l;
        public Object t;
        public int n;
    }
}
