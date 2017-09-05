package com.vatebra.eirsagentpoc.repository;

import android.util.Log;
import android.widget.Toast;

import com.vatebra.eirsagentpoc.App;
import com.vatebra.eirsagentpoc.building.domain.entity.Building;
import com.vatebra.eirsagentpoc.domain.entity.ApiResponse;
import com.vatebra.eirsagentpoc.domain.entity.ApiSingleResponse;
import com.vatebra.eirsagentpoc.domain.entity.TaxPayer;
import com.vatebra.eirsagentpoc.entity.BuildingDao;
import com.vatebra.eirsagentpoc.services.RetrofitProxyService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.vatebra.eirsagentpoc.services.RetrofitClient.getRetrofitInstance;

/**
 * Created by David Eti on 27/08/2017.
 */

public class GlobalRepository {

    private static GlobalRepository INSTANCE = null;
    private static final String TAG = GlobalRepository.class.getSimpleName();
    private RetrofitProxyService retrofitProxyService;

    private GlobalRepository() {
        retrofitProxyService = getRetrofitInstance();

    }

    public synchronized static GlobalRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GlobalRepository();
        }
        return INSTANCE;
    }


    public void getTaxPayerByTin(String tin, final BusinessRepository.OnApiReceived<TaxPayer> callback) {
        retrofitProxyService.getTaxPayerDetails(tin).enqueue(new Callback<ApiSingleResponse<TaxPayer>>() {
            @Override
            public void onResponse(Call<ApiSingleResponse<TaxPayer>> call, Response<ApiSingleResponse<TaxPayer>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    ApiSingleResponse<TaxPayer> apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getStatus().equals("00")) {
                        callback.OnSuccess(apiResponse.getData());
                    } else {
                        callback.OnFailed("Could not retrieve Tax Payer, Ensure the TIN is correct");
                    }
                } else {
                    Log.e(TAG, "onResponse: " + response.raw());
                    callback.OnFailed("Could not retrieve Tax Payer, Ensure the TIN is correct");

                }
            }

            @Override
            public void onFailure(Call<ApiSingleResponse<TaxPayer>> call, Throwable t) {
                callback.OnFailed("Failed, Ensure you have an active connection");
            }
        });
    }


    public void topUp(String pin, String tin, final BusinessRepository.OnApiReceived<String> callback) {
        retrofitProxyService.topUpAccount(pin, tin).enqueue(new Callback<ApiSingleResponse<String>>() {
            @Override
            public void onResponse(Call<ApiSingleResponse<String>> call, Response<ApiSingleResponse<String>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    ApiSingleResponse<String> apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getStatus().equals("00")) {
                        callback.OnSuccess(apiResponse.getData());
                    } else if (response.code() == 404) {
                        callback.OnFailed("Top Up Failed, Card has been used before, or doesn't exist");
                    } else {
                        callback.OnFailed("Card has been used before, or doesn't exist");

                    }
                } else {
                    Log.e(TAG, "onResponse: " + response.raw());
                    callback.OnFailed("Could not top up Ensure the Pin is correct");

                }
            }

            @Override
            public void onFailure(Call<ApiSingleResponse<String>> call, Throwable t) {
                callback.OnFailed("Failed, Ensure you have an active connection");

            }
        });
    }

    public void payBill(int AssessmentId, double SettlementAmount, String tin, final BusinessRepository.OnApiReceived<String> callback) {
        retrofitProxyService.payBill(AssessmentId, SettlementAmount, tin).enqueue(new Callback<ApiSingleResponse<String>>() {
            @Override
            public void onResponse(Call<ApiSingleResponse<String>> call, Response<ApiSingleResponse<String>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    ApiSingleResponse<String> apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getStatus().equals("00")) {
                        callback.OnSuccess(apiResponse.getData());
                    } else if (response.code() == 400) {
                        callback.OnFailed("Bill payment failed, Account balance needs top up");
                    } else {
                        callback.OnFailed("Bill payment failed, try again later");

                    }
                } else {
                    Log.e(TAG, "onResponse: " + response.raw());
                    callback.OnFailed("Could not top up Ensure the Pin is correct");

                }
            }

            @Override
            public void onFailure(Call<ApiSingleResponse<String>> call, Throwable t) {
                callback.OnFailed("Failed, Ensure you have an active connection");

            }
        });
    }

    public void payBillWithAtm(int AssessmentId, double SettlementAmount, String tin, final BusinessRepository.OnApiReceived<String> callback) {
        retrofitProxyService.payBillWithAtm(AssessmentId, SettlementAmount, tin).enqueue(new Callback<ApiSingleResponse<String>>() {
            @Override
            public void onResponse(Call<ApiSingleResponse<String>> call, Response<ApiSingleResponse<String>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    ApiSingleResponse<String> apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getStatus().equals("00")) {
                        callback.OnSuccess(apiResponse.getData());
                    } else if (response.code() == 400) {
                        callback.OnFailed("Bill payment failed, try again later");
                    } else {
                        callback.OnFailed("Bill payment failed, try again later");

                    }
                } else {
                    Log.e(TAG, "onResponse: " + response.raw());
                    callback.OnFailed("Bill payment failed, try again later");

                }
            }

            @Override
            public void onFailure(Call<ApiSingleResponse<String>> call, Throwable t) {
                callback.OnFailed("Failed, Ensure you have an active connection");

            }
        });
    }

    public void partialPayment(int AssessmentId, double SettlementAmount, String tin, final BusinessRepository.OnApiReceived<String> callback) {
        retrofitProxyService.partialPayment(AssessmentId, SettlementAmount, tin).enqueue(new Callback<ApiSingleResponse<String>>() {
            @Override
            public void onResponse(Call<ApiSingleResponse<String>> call, Response<ApiSingleResponse<String>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    ApiSingleResponse<String> apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getStatus().equals("00")) {
                        callback.OnSuccess(apiResponse.getData());
                    } else if (response.code() == 400) {
                        callback.OnFailed("Bill payment failed, try again later");
                    } else {
                        callback.OnFailed("Bill payment failed, try again later");

                    }
                } else {
                    Log.e(TAG, "onResponse: " + response.raw());
                    callback.OnFailed("Bill payment failed, try again later");

                }
            }

            @Override
            public void onFailure(Call<ApiSingleResponse<String>> call, Throwable t) {
                callback.OnFailed("Failed, Ensure you have an active connection");

            }
        });
    }
}
