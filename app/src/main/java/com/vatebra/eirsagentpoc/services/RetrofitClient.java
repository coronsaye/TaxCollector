package com.vatebra.eirsagentpoc.services;

import android.content.Context;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.vatebra.eirsagentpoc.App.getInstance;

/**
 * Created by COronsaye on 3/15/2017.
 */

public class RetrofitClient {

    private static Retrofit retrofit = null;
    private static RetrofitProxyService retrofitProxyService;
    private static String TAG = RetrofitClient.class.getSimpleName();

    private static Retrofit getClient() {
        String baseUri = "http://192.168.1.84/edirsapi/";
        if (retrofit == null) {

            try {

                OkHttpClient okHttpClient = new OkHttpClient().newBuilder().retryOnConnectionFailure(true)
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

    public synchronized static RetrofitProxyService getRetrofitInstance() {
        if (retrofitProxyService == null)
            retrofitProxyService = getClient().create(RetrofitProxyService.class);

        return retrofitProxyService;
    }
}
