package com.vatebra.eirsagentpoc.building.domain.entity;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by David Eti on 21/08/2017.
 */

public class Building extends RealmObject {

    @PrimaryKey
    @SerializedName("RIN")
    private String rin;
    private String tagNumber;
    private String buildingNumber;
    private String name;
    private String streetName;
    private String offStreetName;
    private String town;
    private String lga;
    private String ward;
    private String assetType;
    private String buildingType;
    private String buildingCompletion;
    private String buildingPurpose;
    private String buildingOwnership;
    private String buildingOccupancy;
    private String buildingOccupancyType;
    private String latitude;
    private String longitude;
    private String status;//active or inactive
    private String profile;


    private int TownID;
    private int LGAID;
    private int WardID;
    private int AssetTypeID;
    private int BuildingTypeID;
    private int BuildingCompletionID;
    private int BuildingPurposeID;
    private int BuildingOwnershipID;
    private int BuildingOccupancyID;
    private int BuildingOccupancyType;


    public Building() {

    }

    public String getRin() {
        return rin;
    }

    public void setRin(String rin) {
        this.rin = rin;
    }

    public String getTagNumber() {
        return tagNumber;
    }

    public void setTagNumber(String tagNumber) {
        this.tagNumber = tagNumber;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getOffStreetName() {
        return offStreetName;
    }

    public void setOffStreetName(String offStreetName) {
        this.offStreetName = offStreetName;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getLga() {
        return lga;
    }

    public void setLga(String lga) {
        this.lga = lga;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(String buildingType) {
        this.buildingType = buildingType;
    }

    public String getBuildingCompletion() {
        return buildingCompletion;
    }

    public void setBuildingCompletion(String buildingCompletion) {
        this.buildingCompletion = buildingCompletion;
    }

    public String getBuildingPurpose() {
        return buildingPurpose;
    }

    public void setBuildingPurpose(String buildingPurpose) {
        this.buildingPurpose = buildingPurpose;
    }

    public String getBuildingOwnership() {
        return buildingOwnership;
    }

    public void setBuildingOwnership(String buildingOwnership) {
        this.buildingOwnership = buildingOwnership;
    }

    public String getBuildingOccupancy() {
        return buildingOccupancy;
    }

    public void setBuildingOccupancy(String buildingOccupancy) {
        this.buildingOccupancy = buildingOccupancy;
    }

    public String getBuildingOccupancyType() {
        return buildingOccupancyType;
    }

    public void setBuildingOccupancyType(String buildingOccupancyType) {
        this.buildingOccupancyType = buildingOccupancyType;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public int getTownID() {
        return TownID;
    }

    public void setTownID(int townID) {
        TownID = townID;
    }

    public int getLGAID() {
        return LGAID;
    }

    public void setLGAID(int LGAID) {
        this.LGAID = LGAID;
    }

    public int getWardID() {
        return WardID;
    }

    public void setWardID(int wardID) {
        WardID = wardID;
    }

    public int getAssetTypeID() {
        return AssetTypeID;
    }

    public void setAssetTypeID(int assetTypeID) {
        AssetTypeID = assetTypeID;
    }

    public int getBuildingTypeID() {
        return BuildingTypeID;
    }

    public void setBuildingTypeID(int buildingTypeID) {
        BuildingTypeID = buildingTypeID;
    }

    public int getBuildingCompletionID() {
        return BuildingCompletionID;
    }

    public void setBuildingCompletionID(int buildingCompletionID) {
        BuildingCompletionID = buildingCompletionID;
    }

    public int getBuildingPurposeID() {
        return BuildingPurposeID;
    }

    public void setBuildingPurposeID(int buildingPurposeID) {
        BuildingPurposeID = buildingPurposeID;
    }

    public int getBuildingOwnershipID() {
        return BuildingOwnershipID;
    }

    public void setBuildingOwnershipID(int buildingOwnershipID) {
        BuildingOwnershipID = buildingOwnershipID;
    }

    public void setBuildingOccupancyType(int buildingOccupancyType) {
        BuildingOccupancyType = buildingOccupancyType;
    }

    public int getBuildingOccupancyID() {
        return BuildingOccupancyID;
    }

    public void setBuildingOccupancyID(int buildingOccupancyID) {
        BuildingOccupancyID = buildingOccupancyID;
    }
}
