package com.vatebra.eirsagentpoc.services;

import com.vatebra.eirsagentpoc.building.domain.entity.Building;
import com.vatebra.eirsagentpoc.domain.entity.ApiResponse;
import com.vatebra.eirsagentpoc.domain.entity.BuildingCompletion;
import com.vatebra.eirsagentpoc.domain.entity.BuildingFunction;
import com.vatebra.eirsagentpoc.domain.entity.BuildingOccupancies;
import com.vatebra.eirsagentpoc.domain.entity.BuildingOccupancyType;
import com.vatebra.eirsagentpoc.domain.entity.BuildingOwnerShip;
import com.vatebra.eirsagentpoc.domain.entity.BuildingPurpose;
import com.vatebra.eirsagentpoc.domain.entity.BuildingType;
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
import com.vatebra.eirsagentpoc.domain.entity.Town;
import com.vatebra.eirsagentpoc.domain.entity.Ward;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

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

    @GET("building/buildingList")
    Call<ApiResponse<Building>> getBuildings();

    @GET("building/buildingtypes")
    Call<ApiResponse<BuildingType>> getBuildingTypes();

    @GET("building/buildingCompletions")
    Call<ApiResponse<BuildingCompletion>> getBuildingCompletions();

    @GET("building/buildingPurposes")
    Call<ApiResponse<BuildingPurpose>> getBuildingPurposes();

    @GET("building/buildingFunctions")
    Call<ApiResponse<BuildingFunction>> getBuildingFunctions();

    @GET("building/buildingOccupancies")
    Call<ApiResponse<BuildingOccupancies>> getBuildingOccupancies();

    @GET("building/buildingOccupancyTypes")
    Call<ApiResponse<BuildingOccupancyType>> getBuildingOccupancyTypes();

    @GET("building/buildingownerships")
    Call<ApiResponse<BuildingOwnerShip>> getBuildingOwnerships();

    @POST("company/createbuilding")
    Call<ApiResponse<Building>> CreatBuilding(@Body Building building);

    @POST("company/editbuilding")
    Call<ApiResponse<Building>> Editbuilding(@Body Building building);

    @GET("global/wards")
    Call<ApiResponse<Ward>> getWards(@Query("lgaID") int lgaId);

    @GET("global/towns")
    Call<ApiResponse<Town>> getTowns();

}
