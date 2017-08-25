package com.vatebra.eirsagentpoc.services;

import com.vatebra.eirsagentpoc.domain.entity.ApiResponse;
import com.vatebra.eirsagentpoc.domain.entity.Business;
import com.vatebra.eirsagentpoc.domain.entity.BusinessCategory;
import com.vatebra.eirsagentpoc.domain.entity.BusinessResponse;
import com.vatebra.eirsagentpoc.domain.entity.BusinessSector;
import com.vatebra.eirsagentpoc.domain.entity.BusinessStruture;
import com.vatebra.eirsagentpoc.domain.entity.BusinessSubSector;
import com.vatebra.eirsagentpoc.domain.entity.Company;
import com.vatebra.eirsagentpoc.domain.entity.EconomicActivity;
import com.vatebra.eirsagentpoc.domain.entity.Individual;
import com.vatebra.eirsagentpoc.domain.entity.Lga;
import com.vatebra.eirsagentpoc.domain.entity.TaxOffice;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by David Eti on 22/08/2017.
 */

public interface RetrofitProxyService {
    @GET("bussiness/businesslist")
    Call<BusinessResponse> getBusinesses();

    @GET("business/businessStructures")
    Call<ApiResponse<BusinessStruture>> getBusinessStructures();

    @GET("global/lga")
    Call<ApiResponse<Lga>> getLgas();

    @GET("business/businesscategories")
    Call<ApiResponse<BusinessCategory>> getBusinessCategories();

    @GET("business/businessSectors")
    Call<ApiResponse<BusinessSector>> getBusinessSectors();

    @GET("business/businesssubsectors")
    Call<ApiResponse<BusinessSubSector>> getBusinessSubSectors();

    @GET("company/GetIndividuals")
    Call<ApiResponse<Individual>> getIndividuals();

    @GET("company/GetCompanies")
    Call<ApiResponse<Company>> getCompanies();

    @POST("bussiness/createbusiness")
    Call<ApiResponse<Business>> CreateBusiness(@Body Business business);

    @PUT("bussiness/createbusiness")
    Call<ApiResponse<Business>> EditBusiness(@Body Business business);

    @POST("company/AddIndividual")
    Call<ApiResponse<Individual>> CreateIndividual(@Body Individual individual);

    @POST("company/UpdateIndividual")
    Call<ApiResponse<Individual>> UpdateIndividual(@Body Individual individual);

    @GET("company/GetTaxOffice")
    Call<ApiResponse<TaxOffice>> getTaxOffice();

    @GET("company/GetEconomicActivity")
    Call<ApiResponse<EconomicActivity>> getEconomicActivities();

    @POST("company/AddCompany")
    Call<ApiResponse<Company>> CreateCompany(@Body Company company);

    @POST("company/UpdateCompany")
    Call<ApiResponse<Company>> UpdateCompany(@Body Company company);

}
