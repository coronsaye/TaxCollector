package com.vatebra.eirsagentpoc.business.domain.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by David Eti on 16/08/2017.
 */

public class Business extends RealmObject {

    @PrimaryKey
    private String rin;
    private String assetType;
    private String name;
    private String lga;
    private String businessCategory;
    private String businessSector;
    private String businessSubSector;
    private String profile;
    private String dateCreated;


    public Business() {

    }

    public Business(String name, String rin, String lga) {
        this.name = name;
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
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
