package com.vatebra.eirsagentpoc.building.domain.entity;

import android.support.annotation.NonNull;

import com.vatebra.eirsagentpoc.business.domain.entity.FakeBusinessRemoteDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David Eti on 21/08/2017.
 */

public class RemoteBuildingDataSource implements BuildingDataSource {

    private static RemoteBuildingDataSource INSTANCE;

    private static List<Building> BUILDING_SERVICE_DATA = new ArrayList<>();

    private RemoteBuildingDataSource() {

    }

    public static RemoteBuildingDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteBuildingDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getBuildings(@NonNull LoadBuildingsCallback callback) {

    }

    @Override
    public void getBuilding(@NonNull String buildingRin, @NonNull GetBuildingCallback callback) {

    }

    @Override
    public void saveBuildings(@NonNull List<Building> buildings) {

    }

    @Override
    public void addBuilding(@NonNull Building building) {

    }
}
