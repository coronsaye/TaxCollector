package com.vatebra.eirsagentpoc.domain.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Collins Oronsaye on 25/08/2017.
 */

public class BuildingCompletion extends RealmObject {
    @PrimaryKey
    private int ID;
    private String Name;

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

    public BuildingCompletion() {
    }


    @Override
    public String toString() {
        return getName();
    }
}
