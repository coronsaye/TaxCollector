package com.vatebra.eirsagentpoc.domain.entity;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by David Eti on 25/08/2017.
 */

public class Ward extends RealmObject {

    @PrimaryKey
    private int ID;
    private String LGA;
    private String Name;

    @SerializedName("LGAID")
    private int LgaId;

    public Ward() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getLGA() {
        return LGA;
    }

    public void setLGA(String LGA) {
        this.LGA = LGA;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getLgaId() {
        return LgaId;
    }

    public void setLgaId(int lgaId) {
        LgaId = lgaId;
    }

    @Override
    public String toString() {
        return getName();
    }
}
