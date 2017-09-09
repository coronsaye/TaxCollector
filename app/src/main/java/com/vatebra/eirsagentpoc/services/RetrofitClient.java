package com.vatebra.eirsagentpoc.services;

import android.content.Context;
import android.util.Log;

import com.vatebra.eirsagentpoc.BuildConfig;

import java.util.concurrent.TimeUnit;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.vatebra.eirsagentpoc.App.getInstance;
import static okhttp3.logging.HttpLoggingInterceptor.Level.HEADERS;
import static okhttp3.logging.HttpLoggingInterceptor.Level.NONE;

/**
 * Created by COronsaye on 3/15/2017.
 */

public class RetrofitClient {

    private static Retrofit retrofit = null;
    private static RetrofitProxyService retrofitProxyService;
    private static String TAG = RetrofitClient.class.getSimpleName();

    private static Retrofit getClient() {
        String baseUri = "http://192.168.1.101/eirsapi/";
        if (retrofit == null) {

            try {

                OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(provideHttpLoggingInterceptor()).connectTimeout(100, TimeUnit.SECONDS)
                        .readTimeout(100, TimeUnit.SECONDS).retryOnConnectionFailure(true)
                        .build();

                retrofit = new Retrofit.Builder()
                        .client(okHttpClient)
                        .baseUrl(baseUri)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            } catch (Exception ex) {
                Log.e(TAG, "getClient: ", ex);
            }

        }
        return retrofit;
    }

    private static HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor =
                new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Log.d(TAG, message);
                    }
                });
        httpLoggingInterceptor.setLevel(BuildConfig.DEBUG ? HEADERS : NONE);
        return httpLoggingInterceptor;
    }

    public synchronized static RetrofitProxyService getRetrofitInstance() {
        if (retrofitProxyService == null)
            retrofitProxyService = getClient().create(RetrofitProxyService.class);

        return retrofitProxyService;
    }
}
