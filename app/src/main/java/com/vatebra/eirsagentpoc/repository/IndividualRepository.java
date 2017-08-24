package com.vatebra.eirsagentpoc.repository;

import android.util.Log;
import android.widget.Toast;

import com.vatebra.eirsagentpoc.App;
import com.vatebra.eirsagentpoc.domain.entity.ApiResponse;
import com.vatebra.eirsagentpoc.domain.entity.BusinessDataSource;
import com.vatebra.eirsagentpoc.domain.entity.EconomicActivity;
import com.vatebra.eirsagentpoc.domain.entity.Individual;
import com.vatebra.eirsagentpoc.domain.entity.TaxOffice;
import com.vatebra.eirsagentpoc.entity.IndividualDao;
import com.vatebra.eirsagentpoc.services.RetrofitProxyService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.vatebra.eirsagentpoc.services.RetrofitClient.getRetrofitInstance;

/**
 * Created by David Eti on 23/08/2017.
 */

public class IndividualRepository {

    private static IndividualRepository INSTANCE = null;
    private static final String TAG = IndividualRepository.class.getSimpleName();
    private RetrofitProxyService retrofitProxyService;
    private IndividualDao individualDao;

    private IndividualRepository() {
        individualDao = IndividualDao.getInstance();
        retrofitProxyService = getRetrofitInstance();
    }

    public synchronized static IndividualRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new IndividualRepository();
        }
        return INSTANCE;
    }

    public List<Individual> getIndividuals() {
        refresh();
        return individualDao.getIndividuals();
    }

    public Individual getIndividual(String rin) {
        return individualDao.getIndividual(rin);
    }

    public void SaveIndividuals(List<Individual> individuals) {
        individualDao.SaveIndividuals(individuals);
    }


    public void UpdateIndividual(final Individual individual) {
        retrofitProxyService.UpdateIndividual(individual).enqueue(new Callback<ApiResponse<Individual>>() {
            @Override
            public void onResponse(Call<ApiResponse<Individual>> call, Response<ApiResponse<Individual>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    ApiResponse<Individual> apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getStatus().equals("00")) {
                        Toast.makeText(App.getInstance(), "Successfully Update", Toast.LENGTH_LONG).show();
                        individualDao.SaveIndividual(individual);
                    }
                } else {
                    //could not get individuals
                    Toast.makeText(App.getInstance(), "Could not Updated", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Individual>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                Toast.makeText(App.getInstance(), "Could not Update Individual, ensure you have an active connection ", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void CreateIndividual(final Individual individual) {
        retrofitProxyService.CreateIndividual(individual).enqueue(new Callback<ApiResponse<Individual>>() {
            @Override
            public void onResponse(Call<ApiResponse<Individual>> call, Response<ApiResponse<Individual>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    ApiResponse<Individual> apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getStatus().equals("00")) {
                        Toast.makeText(App.getInstance(), "Successfully Created Individual", Toast.LENGTH_LONG).show();
                        individualDao.SaveIndividual(individual);
                    }
                } else {
                    //could not get individuals
                    Toast.makeText(App.getInstance(), "Could not Update Company", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Individual>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                Toast.makeText(App.getInstance(), "Could not Update, ensure you have an active connection ", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void refresh() {
        retrofitProxyService.getIndividuals().enqueue(new Callback<ApiResponse<Individual>>() {
            @Override
            public void onResponse(Call<ApiResponse<Individual>> call, Response<ApiResponse<Individual>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    ApiResponse<Individual> apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getStatus().equals("00")) {
                        SaveIndividuals(apiResponse.getData());
                    }
                } else {
                    //could not get individuals
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Individual>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);

            }
        });
    }

    public void getEconomicActivities(final BusinessDataSource.GetObjectCallback<EconomicActivity> callback) {
        if (individualDao.getEconomicActivity() != null) {
            callback.onObjectsLoaded(individualDao.getEconomicActivity());
        }
        retrofitProxyService.getEconomicActivities().enqueue(new Callback<ApiResponse<EconomicActivity>>() {
            @Override
            public void onResponse(Call<ApiResponse<EconomicActivity>> call, Response<ApiResponse<EconomicActivity>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    ApiResponse<EconomicActivity> apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getStatus().equals("00")) {
                        individualDao.SaveEconomicActivities(apiResponse.getData());
                        callback.onObjectsLoaded(individualDao.getEconomicActivity());
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<EconomicActivity>> call, Throwable t) {

            }
        });
    }

    public void getTaxOffice(final BusinessDataSource.GetObjectCallback<TaxOffice> callback) {
        if (individualDao.getTaxOffices() != null) {
            callback.onObjectsLoaded(individualDao.getTaxOffices());
        }
        retrofitProxyService.getTaxOffice().enqueue(new Callback<ApiResponse<TaxOffice>>() {
            @Override
            public void onResponse(Call<ApiResponse<TaxOffice>> call, Response<ApiResponse<TaxOffice>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    ApiResponse<TaxOffice> taxOfficeApiResponse = response.body();
                    if (taxOfficeApiResponse != null && taxOfficeApiResponse.getStatus().equals("00")) {
                        individualDao.SaveTaxOffices(taxOfficeApiResponse.getData());
                        callback.onObjectsLoaded(individualDao.getTaxOffices());
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<TaxOffice>> call, Throwable t) {

            }
        });

    }
}
