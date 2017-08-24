package com.vatebra.eirsagentpoc.domain.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by David Eti on 22/08/2017.
 */

public class BusinessCategory extends RealmObject {

    @PrimaryKey
    private int ID;
    private String Name;
    private String BusinessType;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getBusinessType() {
        return BusinessType;
    }

    public void setBusinessType(String businessType) {
        BusinessType = businessType;
    }

    public BusinessCategory() {
    }

    @Override
    public String toString() {
        return getName();
    }
}
