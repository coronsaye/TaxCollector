package com.vatebra.eirsagentpoc.domain.entity;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by David Eti on 24/08/2017.
 */

public class EconomicActivity extends RealmObject {

    @SerializedName("TaxPayerTypeID")
    private int TaxPayerTypeId;

    @PrimaryKey
    private int Id;

    private String Name;


    public EconomicActivity() {
    }

    public int getTaxPayerTypeId() {
        return TaxPayerTypeId;
    }

    public void setTaxPayerTypeId(int taxPayerTypeId) {
        TaxPayerTypeId = taxPayerTypeId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    @Override
    public String toString() {
        return getName();
    }
}
