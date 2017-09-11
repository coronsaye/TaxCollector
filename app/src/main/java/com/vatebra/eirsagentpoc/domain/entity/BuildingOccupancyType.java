package com.vatebra.eirsagentpoc.domain.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by David Eti on 25/08/2017.
 */

public class BuildingOccupancyType extends RealmObject {

    @PrimaryKey
    private int ID;

    private String BuildingOccupancy;
    private String Name;

    public BuildingOccupancyType() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getBuildingOccupancy() {
        return BuildingOccupancy;
    }

    public void setBuildingOccupancy(String buildingOccupancy) {
        BuildingOccupancy = buildingOccupancy;
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
