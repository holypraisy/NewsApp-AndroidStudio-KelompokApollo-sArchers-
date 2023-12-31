package com.example.newsapi;

import android.content.Context;
import android.widget.Toast;

import com.example.newsapi.models.NewsAPIResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RequestManager {
    Context context;

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("https://newsapi.org/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    public void getNewsHeadlines(OnFetchDataListener listener, String category, String query){
        CallNewsApi callNewsApi = retrofit.create(CallNewsApi.class);
        Call<NewsAPIResponse> call = callNewsApi.callheadlines("us", category, query, context.getString(R.string.api_key));

        try {
            call.enqueue(new Callback<NewsAPIResponse>() {
                @Override
                public void onResponse(Call<NewsAPIResponse> call, Response<NewsAPIResponse> response) {
                    if (!response.isSuccessful()){
                        Toast.makeText(context,"ERROR!!", Toast.LENGTH_SHORT).show();
                    }

                    listener.onFetchData(response.body().getArticles(), response.message());

                }

                @Override
                public void onFailure(Call<NewsAPIResponse> call, Throwable t) {
                    listener.onError("Request Failed!");
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public RequestManager(Context context) {
        this.context = context;
    }

    public interface CallNewsApi {
        @GET("top-headlines")
        Call<NewsAPIResponse> callheadlines(
            @Query("country") String country,
            @Query("category") String category,
            @Query("q") String query,
            @Query("apiKey") String apiKey
        );


    }
}
