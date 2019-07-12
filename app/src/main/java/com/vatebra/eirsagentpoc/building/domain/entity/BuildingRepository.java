package com.vatebra.eirsagentpoc.building.domain.entity;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Collins Oronsaye on 21/08/2017.
 */

public class BuildingRepository implements BuildingDataSource {

    private static BuildingRepository INSTANCE = null;
    private final BuildingDataSource mBuildingLocalDataSource;
    private final BuildingDataSource mBuilldingRemoteDataSource;
    private List<Building> cachedBuildings;

    private BuildingRepository(@NonNull BuildingDataSource mBuildingLocalDataSource,
                               @NonNull BuildingDataSource mBuilldingRemoteDataSource) {
        this.mBuildingLocalDataSource = checkNotNull(mBuildingLocalDataSource);
        this.mBuilldingRemoteDataSource = checkNotNull(mBuilldingRemoteDataSource);
    }


    public synchronized static BuildingRepository getINSTANCE(BuildingDataSource mBuildingLocalDataSource, BuildingDataSource mBuilldingRemoteDataSource) {

        if (INSTANCE == null) {
            INSTANCE = new BuildingRepository(mBuildingLocalDataSource, mBuilldingRemoteDataSource);
        }
        return INSTANCE;
    }


    @Override
    public void getBuildings(@NonNull final LoadBuildingsCallback callback) {
        checkNotNull(callback);
        getBuildingsFromRemote(callback);

        mBuildingLocalDataSource.getBuildings(new LoadBuildingsCallback() {
            @Override
            public void onBuildingsLoaded(List<Building> buildings) {
                refreshBuildings(buildings);
                callback.onBuildingsLoaded(new ArrayList<>(cachedBuildings));
            }

            @Override
            public void onDataNotAvailable() {
                getBuildingsFromRemote(callback);

            }
        });
    }

    @Override
    public void getBuilding(@NonNull String buildingRin, @NonNull final GetBuildingCallback callback) {
        checkNotNull(buildingRin);
        checkNotNull(callback);
        mBuildingLocalDataSource.getBuilding(buildingRin, new GetBuildingCallback() {
            @Override
            public void onBuildingLoaded(Building building) {
                callback.onBuildingLoaded(building);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void saveBuildings(@NonNull List<Building> buildings) {
        checkNotNull(buildings);
        mBuildingLocalDataSource.saveBuildings(buildings);
        if (cachedBuildings == null) {
            cachedBuildings = new ArrayList<>();
        }
        cachedBuildings = buildings;
    }

    @Override
    public void addBuilding(@NonNull Building building) {
        checkNotNull(building);
        mBuilldingRemoteDataSource.addBuilding(building);
        mBuildingLocalDataSource.addBuilding(building);
        if (cachedBuildings == null) {
            cachedBuildings = new ArrayList<>();
        }
        cachedBuildings.add(building);
    }


    private void getBuildingsFromRemote(@NonNull final LoadBuildingsCallback callback) {
        mBuilldingRemoteDataSource.getBuildings(new LoadBuildingsCallback() {
            @Override
            public void onBuildingsLoaded(List<Building> buildings) {
                refreshBuildings(buildings);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void refreshBuildings(@NonNull List<Building> buildings) {
        cachedBuildings = checkNotNull(buildings);
    }
}
