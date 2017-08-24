package com.vatebra.eirsagentpoc.domain.entity;

import android.support.annotation.Nullable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by David Eti on 22/08/2017.
 */

public class BusinessSubSector extends RealmObject {

    @PrimaryKey
    private int ID;
    @Nullable
    private String BusinessTypeID;
    private String BusinessCategory;
    private String BusinessSector;
    private String Name;

    public BusinessSubSector() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Nullable
    public String getBusinessTypeID() {
        return BusinessTypeID;
    }

    public void setBusinessTypeID(@Nullable String businessTypeID) {
        BusinessTypeID = businessTypeID;
    }

    public String getBusinessCategory() {
        return BusinessCategory;
    }

    public void setBusinessCategory(String businessCategory) {
        BusinessCategory = businessCategory;
    }

    public String getBusinessSector() {
        return BusinessSector;
    }

    public void setBusinessSector(String businessSector) {
        BusinessSector = businessSector;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
