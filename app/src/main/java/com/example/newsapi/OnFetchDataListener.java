package com.example.newsapi;

import com.example.newsapi.models.NewsHeadlines;

import java.util.List;

public interface OnFetchDataListener<NewsAPIResponse> {
    void onFetchData(List<NewsHeadlines> list, String message);
    void onError(String message);

}
