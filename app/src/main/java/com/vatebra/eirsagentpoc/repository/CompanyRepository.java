package com.vatebra.eirsagentpoc.repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.vatebra.eirsagentpoc.App;
import com.vatebra.eirsagentpoc.domain.entity.ApiResponse;
import com.vatebra.eirsagentpoc.domain.entity.Company;
import com.vatebra.eirsagentpoc.domain.entity.Individual;
import com.vatebra.eirsagentpoc.entity.CompanyDao;
import com.vatebra.eirsagentpoc.services.RetrofitProxyService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.vatebra.eirsagentpoc.services.RetrofitClient.getRetrofitInstance;

/**
 * Created by David Eti on 23/08/2017.
 */

public class CompanyRepository {
    private static CompanyRepository INSTANCE = null;
    private static final String TAG = CompanyRepository.class.getSimpleName();
    private RetrofitProxyService retrofitProxyService;
    private CompanyDao companyDao;

    private CompanyRepository() {
        companyDao = CompanyDao.getInstance();
        retrofitProxyService = getRetrofitInstance();
    }

    public synchronized static CompanyRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CompanyRepository();
        }
        return INSTANCE;
    }

    public List<Company> getCompanies() {
        refresh();
        return companyDao.getCompanies();
    }

    public void UpdateCompany(final Company company){
        retrofitProxyService.CreateCompany(company).enqueue(new Callback<ApiResponse<Company>>() {
            @Override
            public void onResponse(Call<ApiResponse<Company>> call, Response<ApiResponse<Company>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    ApiResponse<Company> apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getStatus().equals("00")) {
                        Toast.makeText(App.getInstance(), "Successfully Updated", Toast.LENGTH_LONG).show();
                        companyDao.SaveCompany(company);
                    }
                } else {
                    //could not get individuals
                    Toast.makeText(App.getInstance(), "Could not Update Company", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Company>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                Toast.makeText(App.getInstance(), "Could not Update, ensure you have an active connection ", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void CreateCompany(final Company company){

        retrofitProxyService.CreateCompany(company).enqueue(new Callback<ApiResponse<Company>>() {
            @Override
            public void onResponse(Call<ApiResponse<Company>> call, Response<ApiResponse<Company>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    ApiResponse<Company> apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getStatus().equals("00")) {
                        Toast.makeText(App.getInstance(), "Successfully Created Company", Toast.LENGTH_LONG).show();
                        companyDao.SaveCompany(company);
                    }
                } else {
                    //could not get individuals
                    Toast.makeText(App.getInstance(), "Could not Create Company", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Company>> call, Throwable t) {

            }
        });
    }
    public Company getCompany(String rin) {
        return companyDao.getCompany(rin);
    }

    public void SaveCompanies(List<Company> companies) {
        companyDao.SaveCompanies(companies);
    }

    private void refresh() {
        retrofitProxyService.getCompanies().enqueue(new Callback<ApiResponse<Company>>() {
            @Override
            public void onResponse(Call<ApiResponse<Company>> call, Response<ApiResponse<Company>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    ApiResponse<Company> apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getStatus().equals("00")) {
                        SaveCompanies(apiResponse.getData());
                    }
                } else {
                    //could not get individuals

                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Company>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }
}
