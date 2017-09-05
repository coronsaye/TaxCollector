package com.vatebra.eirsagentpoc.domain.entity;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.vatebra.eirsagentpoc.App;
import com.vatebra.eirsagentpoc.repository.BusinessRepository;
import com.vatebra.eirsagentpoc.services.RetrofitProxyService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.vatebra.eirsagentpoc.services.RetrofitClient.getRetrofitInstance;

/**
 * Created by David Eti on 22/08/2017.
 */

public class RemoteBusinessDataSource implements BusinessDataSource {

    private static RemoteBusinessDataSource INSTANCE;

    private RetrofitProxyService retrofitProxyService;

    private static List<Business> TASK_SERVICE_DATA = new ArrayList<>();
    private static String TAG = RemoteBusinessDataSource.class.getSimpleName();

    private RemoteBusinessDataSource() {
        retrofitProxyService = getRetrofitInstance();
    }

    public static RemoteBusinessDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteBusinessDataSource();
        }
        return INSTANCE;
    }


    @Override
    public void getBusinesses(@NonNull final LoadBusinessesCallback callback) {
        retrofitProxyService.getBusinesses().enqueue(new Callback<BusinessResponse>() {
            @Override
            public void onResponse(@NonNull Call<BusinessResponse> call, @NonNull Response<BusinessResponse> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    BusinessResponse businessResponse = response.body();
                    if (businessResponse != null && businessResponse.getStatus().equals("00")) {
                        callback.onBusinessesLoaded(businessResponse.getData());
                    }
                } else {
                    callback.onDataNotAvailable();
                }
            }

            @Override
            public void onFailure(@NonNull Call<BusinessResponse> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getBusiness(@NonNull String mBusinessRin, @NonNull GetBusinessCallback callback) {
    }

    @Override
    public void saveBusinesses(@NonNull List<Business> businesses) {

    }

    @Override
    public void GetBusinessProfile(@NonNull Business business, final BusinessRepository.OnApiReceived<AssetProfile> callback) {
        retrofitProxyService.getAssetProfile(business).enqueue(new Callback<ApiSingleResponse<AssetProfile>>() {
            @Override
            public void onResponse(Call<ApiSingleResponse<AssetProfile>> call, Response<ApiSingleResponse<AssetProfile>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    ApiSingleResponse<AssetProfile> apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getStatus().equals("00")) {
                        callback.OnSuccess(apiResponse.getData());

                    } else if (apiResponse != null && apiResponse.getStatus().equals("01")) {
                        callback.OnFailed(apiResponse.getMessage()); //Add Message
                    } else {
                        callback.OnFailed("Failed Could not get Profile");
                    }
                } else {
                    callback.OnFailed("Failed Could not get Profile");
                }
            }

            @Override
            public void onFailure(Call<ApiSingleResponse<AssetProfile>> call, Throwable t) {
                callback.OnFailed("Failed, Ensure you have an active connection");
            }
        });

    }

    @Override
    public void getBusinessByRin(@NonNull String rin, final BusinessRepository.OnApiReceived<Business> callback) {
        retrofitProxyService.getBusinessByRin(rin).enqueue(new Callback<ApiSingleResponse<Business>>() {
            @Override
            public void onResponse(Call<ApiSingleResponse<Business>> call, Response<ApiSingleResponse<Business>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    ApiSingleResponse<Business> apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getStatus().equals("00")) {
                        callback.OnSuccess(apiResponse.getData());
                    } else {
                        callback.OnFailed("Business Not Found, Ensure you have the correct Rin");
                    }
                } else {
                    callback.OnFailed("Business Not Found, Ensure you have the correct Rin");
                }
            }

            @Override
            public void onFailure(Call<ApiSingleResponse<Business>> call, Throwable t) {
                callback.OnFailed("Failed, Ensure you have an active connection");
            }
        });
    }

    @Override
    public void addBusiness(@NonNull Business business, @NonNull final UpdateBusinessCallback callback) {
        retrofitProxyService.CreateBusiness(business).enqueue(new Callback<ApiResponse<Business>>() {
            @Override
            public void onResponse(Call<ApiResponse<Business>> call, Response<ApiResponse<Business>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    ApiResponse<Business> businessApiResponse = response.body();
                    if (businessApiResponse != null && businessApiResponse.getStatus().equals("00")) {
                        callback.onUpdateSuccessful(businessApiResponse.getMessage());
                    } else if (businessApiResponse != null && businessApiResponse.getStatus().equals("01")) {
                        callback.onUpdateFailed(); //Add Message
                    } else {
                        callback.onUpdateFailed();
                    }
                } else {
                    callback.onUpdateFailed();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Business>> call, Throwable t) {
                Toast.makeText(App.getInstance(), "Failed, could not add business, Ensure you have an active connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void updateBusiness(@NonNull Business business, @NonNull final UpdateBusinessCallback callback) {
        retrofitProxyService.EditBusiness(business).enqueue(new Callback<ApiResponse<Business>>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<Business>> call, Response<ApiResponse<Business>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    ApiResponse<Business> businessApiResponse = response.body();
                    if (businessApiResponse != null && businessApiResponse.getStatus().equals("00")) {
                        callback.onUpdateSuccessful(businessApiResponse.getMessage());
                    } else if (businessApiResponse != null && businessApiResponse.getStatus().equals("01")) {
                        callback.onUpdateFailed();//Add Message
                    } else {
                        callback.onUpdateFailed();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse<Business>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                callback.onUpdateFailed();
            }
        });
    }

    @Override
    public void getLgas(@NonNull final GetObjectCallback<Lga> callback) {
        retrofitProxyService.getLgas().enqueue(new Callback<ApiResponse<Lga>>() {
            @Override
            public void onResponse(Call<ApiResponse<Lga>> call, Response<ApiResponse<Lga>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    ApiResponse<Lga> lgaApiResponse = response.body();
                    if (lgaApiResponse != null && lgaApiResponse.getStatus().equals("00")) {
                        callback.onObjectsLoaded(lgaApiResponse.getData());
                    }
                } else {
                    callback.onDataNotAvailable();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Lga>> call, Throwable t) {
                callback.onDataNotAvailable();
                Log.e(TAG, "onFailure: ", t);

            }
        });
    }

    @Override
    public void getCategories(@NonNull final GetObjectCallback<BusinessCategory> callback) {
        retrofitProxyService.getBusinessCategories().enqueue(new Callback<ApiResponse<BusinessCategory>>() {
            @Override
            public void onResponse(Call<ApiResponse<BusinessCategory>> call, Response<ApiResponse<BusinessCategory>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    ApiResponse<BusinessCategory> apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getStatus().equals("00")) {
                        callback.onObjectsLoaded(apiResponse.getData());
                    }
                } else {
                    callback.onDataNotAvailable();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<BusinessCategory>> call, Throwable t) {
                callback.onDataNotAvailable();
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    @Override
    public void getSectors(@NonNull final GetObjectCallback<BusinessSector> callback) {
        retrofitProxyService.getBusinessSectors().enqueue(new Callback<ApiResponse<BusinessSector>>() {
            @Override
            public void onResponse(Call<ApiResponse<BusinessSector>> call, Response<ApiResponse<BusinessSector>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    ApiResponse<BusinessSector> apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getStatus().equals("00")) {
                        callback.onObjectsLoaded(apiResponse.getData());
                    }
                } else {
                    callback.onDataNotAvailable();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<BusinessSector>> call, Throwable t) {
                callback.onDataNotAvailable();
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    @Override
    public void getSubSectors(@NonNull final GetObjectCallback<BusinessSubSector> callback) {
        retrofitProxyService.getBusinessSubSectors().enqueue(new Callback<ApiResponse<BusinessSubSector>>() {
            @Override
            public void onResponse(Call<ApiResponse<BusinessSubSector>> call, Response<ApiResponse<BusinessSubSector>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    ApiResponse<BusinessSubSector> apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getStatus().equals("00")) {
                        callback.onObjectsLoaded(apiResponse.getData());
                    }
                } else {
                    callback.onDataNotAvailable();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<BusinessSubSector>> call, Throwable t) {
                callback.onDataNotAvailable();
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    @Override
    public void getStructures(@NonNull final GetObjectCallback<BusinessStruture> callback) {
        retrofitProxyService.getBusinessStructures().enqueue(new Callback<ApiResponse<BusinessStruture>>() {
            @Override
            public void onResponse(Call<ApiResponse<BusinessStruture>> call, Response<ApiResponse<BusinessStruture>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    ApiResponse<BusinessStruture> apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getStatus().equals("00")) {
                        callback.onObjectsLoaded(apiResponse.getData());
                    }
                } else {
                    callback.onDataNotAvailable();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<BusinessStruture>> call, Throwable t) {
                callback.onDataNotAvailable();
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }


}
