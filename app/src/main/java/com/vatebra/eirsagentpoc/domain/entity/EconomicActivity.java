package com.vatebra.eirsagentpoc.domain.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by David Eti on 24/08/2017.
 */

public class EconomicActivity extends RealmObject {

    @PrimaryKey
    private int TaxPayerTypeId;

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
}
