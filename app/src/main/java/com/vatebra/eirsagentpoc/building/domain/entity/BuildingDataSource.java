package com.vatebra.eirsagentpoc.building.domain.entity;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by Collins Oronsaye on 21/08/2017.
 */

public interface BuildingDataSource {

    interface LoadBuildingsCallback {
        void onBuildingsLoaded(List<Building> buildings);
        void onDataNotAvailable();
    }
    interface GetBuildingCallback {
        void onBuildingLoaded(Building building);
        void onDataNotAvailable();

    }

    void getBuildings(@NonNull LoadBuildingsCallback callback);

    void getBuilding(@NonNull String buildingRin, @NonNull GetBuildingCallback callback );

    void saveBuildings(@NonNull List<Building> buildings);

    void addBuilding(@NonNull Building building);

}
