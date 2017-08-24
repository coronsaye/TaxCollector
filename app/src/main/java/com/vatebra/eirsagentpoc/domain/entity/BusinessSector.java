package com.vatebra.eirsagentpoc.domain.entity;

import android.support.annotation.Nullable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by David Eti on 22/08/2017.
 */


public class BusinessSector extends RealmObject {

    @PrimaryKey
    private int ID;

    private String BusinessType;
    @Nullable
    private String BusinessCategory;
    private String Name;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getBusinessType() {
        return BusinessType;
    }

    public void setBusinessType(String businessType) {
        BusinessType = businessType;
    }

    @Nullable
    public String getBusinessCategory() {
        return BusinessCategory;
    }

    public void setBusinessCategory(@Nullable String businessCategory) {
        BusinessCategory = businessCategory;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public BusinessSector() {
    }
    @Override
    public String toString() {
        return getName();
    }
}
