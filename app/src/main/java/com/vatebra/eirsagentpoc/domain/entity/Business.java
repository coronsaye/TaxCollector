package com.vatebra.eirsagentpoc.domain.entity;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import io.realm.BusinessRealmProxy;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Collins Oronsaye on 16/08/2017.
 */

@Parcel(implementations = { BusinessRealmProxy.class },
        value = Parcel.Serialization.BEAN,
        analyze = { Business.class })
public class Business extends RealmObject {

    @PrimaryKey
    @SerializedName("RIN")
    private String rin;
    @SerializedName("AssetType")
    private String assetType;
    @SerializedName("Business")
    private String business;
    @SerializedName("LGA")
    private String lga;
    @SerializedName("BusinessCatgeory")
    private String businessCategory;
    @SerializedName("BusinessSector")
    private String businessSector;
    @SerializedName("BusinessSubSector")
    private String businessSubSector;

    private String profile;

    private String dateCreated;

    @SerializedName("BusinessStructure")
    private String BusinessStructure;


    private int AssetTypeID;
    private int BusinessCatgeoryID;
    private int BusinessStructureID;
    private int BusinessSectorID;
    private int BusinessSubSectorID;
    private int LGAID;


    private String Name; //as gotten from the api

    //USED FOR PROFILING
    private int CompanyID;
    private int IndividualID;


    //newly added for attaching building to business
    @SerializedName("BuildingID")
    private int BuildingID;
    @SerializedName("BuildingRIN")
    private String BuildingRIN;



    public Business() {

    }


    public Business(String name, String rin, String lga) {
        this.business = name;
        this.rin = rin;
        this.lga = lga;
    }


    public String getRin() {
        return rin;
    }

    public void setRin(String rin) {
        this.rin = rin;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getName() {
        return business;
    }

    public void setName(String name) {
        this.business = name;
    }

    public String getLga() {
        return lga;
    }

    public void setLga(String lga) {
        this.lga = lga;
    }

    public String getBusinessCategory() {
        return businessCategory;
    }

    public void setBusinessCategory(String businessCategory) {
        this.businessCategory = businessCategory;
    }

    public String getBusinessSector() {
        return businessSector;
    }

    public void setBusinessSector(String businessSector) {
        this.businessSector = businessSector;
    }

    public String getBusinessSubSector() {
        return businessSubSector;
    }

    public void setBusinessSubSector(String businessSubSector) {
        this.businessSubSector = businessSubSector;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getBusinessStructure() {
        return BusinessStructure;
    }

    public void setBusinessStructure(String businessStructure) {
        BusinessStructure = businessStructure;
    }


    public int getAssetTypeID() {
        return AssetTypeID;
    }

    public void setAssetTypeID(int assetTypeID) {
        AssetTypeID = assetTypeID;
    }

    public int getBusinessCategoryId() {
        return BusinessCatgeoryID;
    }

    public void setBusinessCategoryId(int businessCategoryId) {
        BusinessCatgeoryID = businessCategoryId;
    }

    public int getBusinessStructureId() {
        return BusinessStructureID;
    }

    public void setBusinessStructureId(int businessStructureId) {
        BusinessStructureID = businessStructureId;
    }

    public int getBusinessSectorId() {
        return BusinessSectorID;
    }

    public void setBusinessSectorId(int businessSectorId) {
        BusinessSectorID = businessSectorId;
    }

    public int getBusinessSubSectorId() {
        return BusinessSubSectorID;
    }

    public void setBusinessSubSectorId(int businessSubSectorId) {
        BusinessSubSectorID = businessSubSectorId;
    }

    public int getLGAID() {
        return LGAID;
    }

    public void setLGAID(int LGAID) {
        this.LGAID = LGAID;
    }

    public String getCreateName() {
        return Name;
    }

    public void setCreateName(String Name) {
        this.Name = Name;
    }


    public int getCompanyID() {
        return CompanyID;
    }

    public void setCompanyID(int companyID) {
        CompanyID = companyID;
    }

    public int getIndividualID() {
        return IndividualID;
    }

    public void setIndividualID(int individualID) {
        IndividualID = individualID;
    }


    public int getBuildingID() {
        return BuildingID;
    }

    public void setBuildingID(int buildingID) {
        BuildingID = buildingID;
    }

    public String getBuildingRIN() {
        return BuildingRIN;
    }

    public void setBuildingRIN(String buildingRIN) {
        BuildingRIN = buildingRIN;
    }
}
