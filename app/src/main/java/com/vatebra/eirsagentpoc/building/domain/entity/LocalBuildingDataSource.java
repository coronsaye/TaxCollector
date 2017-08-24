package com.vatebra.eirsagentpoc.building.domain.entity;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by David Eti on 21/08/2017.
 */

public class LocalBuildingDataSource implements BuildingDataSource {

    private static LocalBuildingDataSource INSTANCE;

    private static String TAG = LocalBuildingDataSource.class.getSimpleName();


    private LocalBuildingDataSource() {

    }

    public static LocalBuildingDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LocalBuildingDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getBuildings(@NonNull LoadBuildingsCallback callback) {
        List<Building> buildings = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        try {
            RealmResults<Building> results = realm.where(Building.class).findAll();
            if (results != null)
                buildings = realm.copyFromRealm(results);

        } catch (Exception ex) {
            Log.e(TAG, "getBuildings: ", ex);
        } finally {
            realm.close();
        }

        if (buildings.isEmpty())
            callback.onDataNotAvailable();
        else
            callback.onBuildingsLoaded(buildings);
    }

    @Override
    public void getBuilding(@NonNull String buildingRin, @NonNull GetBuildingCallback callback) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Building> results;
        Building building = new Building();
        try {
            results = realm.where(Building.class).findAll();
            Building dbBuilding = results.where().equalTo("rin", buildingRin).findFirst();
            building = realm.copyFromRealm(dbBuilding);
        } catch (Exception ex) {
            Log.e(TAG, "getBuilding: ", ex);
        } finally {
            realm.close();
        }
        if (!Strings.isNullOrEmpty(building.getRin()))
            callback.onBuildingLoaded(building);
        else
            callback.onDataNotAvailable();

    }

    @Override
    public void saveBuildings(@NonNull final List<Building> buildings) {
        checkNotNull(buildings);
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(buildings);
                }
            });
        } catch (Exception ex) {
            Log.e(TAG, "saveBuildings: ", ex);
        } finally {
            realm.close();
        }
    }

    @Override
    public void addBuilding(@NonNull final Building building) {
        checkNotNull(building);
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(building);
                }
            });
        } catch (Exception ex) {
            Log.e(TAG, "addBuilding: ", ex);
        } finally {
            realm.close();
        }
    }
}
