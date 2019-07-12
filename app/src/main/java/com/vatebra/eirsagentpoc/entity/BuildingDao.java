package com.vatebra.eirsagentpoc.entity;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.common.base.Strings;
import com.vatebra.eirsagentpoc.building.domain.entity.Building;
import com.vatebra.eirsagentpoc.domain.entity.BuildingCompletion;
import com.vatebra.eirsagentpoc.domain.entity.BuildingFunction;
import com.vatebra.eirsagentpoc.domain.entity.BuildingOccupancies;
import com.vatebra.eirsagentpoc.domain.entity.BuildingOccupancyType;
import com.vatebra.eirsagentpoc.domain.entity.BuildingOwnerShip;
import com.vatebra.eirsagentpoc.domain.entity.BuildingPurpose;
import com.vatebra.eirsagentpoc.domain.entity.BuildingType;
import com.vatebra.eirsagentpoc.domain.entity.Company;
import com.vatebra.eirsagentpoc.domain.entity.Town;
import com.vatebra.eirsagentpoc.domain.entity.Ward;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Collins Oronsaye on 25/08/2017.
 */

public class BuildingDao {

    private static BuildingDao INSTANCE;
    private static String TAG = BuildingDao.class.getSimpleName();

    private BuildingDao() {

    }

    public static BuildingDao getInstance() {

        if (INSTANCE == null) {
            INSTANCE = new BuildingDao();
        }
        return INSTANCE;
    }

    public List<Building> getBuildings() {
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

        if (buildings.isEmpty()) {
            // This will be called if the table is new or just empty.
            return null;
        } else {
            return buildings;
        }
    }

    public Building getBuilding(String buildingRin) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Building> results;
        Building building = new Building();
        try {
            results = realm.where(Building.class).findAll();
            Building building1 = results.where().equalTo("rin", buildingRin).findFirst();
            building = realm.copyFromRealm(building1);

        } catch (Exception ex) {
            Log.e(TAG, "getCompany: ", ex);
        } finally {
            realm.close();
        }

        if (!Strings.isNullOrEmpty(building.getRin())) {
            return building;
        } else {
            return null;
        }

    }


    public void SaveBuildingTypes(@NonNull final List<BuildingType> buildingTypes) {
        checkNotNull(buildingTypes);
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(buildingTypes);
                }
            });
        } catch (Exception ex) {
            Log.e(TAG, "SaveTypes: ", ex);
        } finally {
            realm.close();
        }
    }

    public List<BuildingType> getBuildingTypes() {
        List<BuildingType> buildingTypes = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        try {
            RealmResults<BuildingType> results = realm.where(BuildingType.class).findAll();

            if (results != null)
                buildingTypes = realm.copyFromRealm(results);

        } catch (Exception ex) {
            Log.e(TAG, "getBuildings: ", ex);
        } finally {
            realm.close();
        }

        if (buildingTypes.isEmpty()) {
            // This will be called if the table is new or just empty.
            return null;
        } else {
            return buildingTypes;
        }
    }

    public void SaveBuildingCompletions(@NonNull final List<BuildingCompletion> buildingCompletions) {
        checkNotNull(buildingCompletions);
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(buildingCompletions);
                }
            });
        } catch (Exception ex) {
            Log.e(TAG, "SaveTypes: ", ex);
        } finally {
            realm.close();
        }
    }

    public List<BuildingCompletion> getBuildingCompletion() {
        List<BuildingCompletion> buildingCompletions = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        try {
            RealmResults<BuildingCompletion> results = realm.where(BuildingCompletion.class).findAll();

            if (results != null)
                buildingCompletions = realm.copyFromRealm(results);

        } catch (Exception ex) {
            Log.e(TAG, "getBuildingCompletion: ", ex);
        } finally {
            realm.close();
        }

        if (buildingCompletions.isEmpty()) {
            // This will be called if the table is new or just empty.
            return null;
        } else {
            return buildingCompletions;
        }
    }

    public void SaveBuildingPurposes(@NonNull final List<BuildingPurpose> buildingPurposes) {
        checkNotNull(buildingPurposes);
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(buildingPurposes);
                }
            });
        } catch (Exception ex) {
            Log.e(TAG, "SaveTypes: ", ex);
        } finally {
            realm.close();
        }
    }

    public List<BuildingPurpose> getBuildingPurposes() {
        List<BuildingPurpose> buildingPurposes = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        try {
            RealmResults<BuildingPurpose> results = realm.where(BuildingPurpose.class).findAll();

            if (results != null)
                buildingPurposes = realm.copyFromRealm(results);

        } catch (Exception ex) {
            Log.e(TAG, "getBuildingPurposes: ", ex);
        } finally {
            realm.close();
        }

        if (buildingPurposes.isEmpty()) {
            // This will be called if the table is new or just empty.
            return null;
        } else {
            return buildingPurposes;
        }
    }

    public void SaveBuildingFunctions(@NonNull final List<BuildingFunction> buildingFunctions) {
        checkNotNull(buildingFunctions);
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(buildingFunctions);
                }
            });
        } catch (Exception ex) {
            Log.e(TAG, "SaveTypes: ", ex);
        } finally {
            realm.close();
        }
    }

    public List<BuildingFunction> getBuildingFunctions() {
        List<BuildingFunction> buildingFunctions = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        try {
            RealmResults<BuildingFunction> results = realm.where(BuildingFunction.class).findAll();

            if (results != null)
                buildingFunctions = realm.copyFromRealm(results);

        } catch (Exception ex) {
            Log.e(TAG, "getBuildingFunctions: ", ex);
        } finally {
            realm.close();
        }

        if (buildingFunctions.isEmpty()) {
            // This will be called if the table is new or just empty.
            return null;
        } else {
            return buildingFunctions;
        }
    }

    public void SaveBuildingOwnerShips(@NonNull final List<BuildingOwnerShip> buildingOwnerShips) {
        checkNotNull(buildingOwnerShips);
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(buildingOwnerShips);
                }
            });
        } catch (Exception ex) {
            Log.e(TAG, "SaveTypes: ", ex);
        } finally {
            realm.close();
        }
    }

    public List<BuildingOwnerShip> getBuildingOwnerships() {
        List<BuildingOwnerShip> buildingOwnerShips = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        try {
            RealmResults<BuildingOwnerShip> results = realm.where(BuildingOwnerShip.class).findAll();

            if (results != null)
                buildingOwnerShips = realm.copyFromRealm(results);

        } catch (Exception ex) {
            Log.e(TAG, "getBuildingOwnerships: ", ex);
        } finally {
            realm.close();
        }

        if (buildingOwnerShips.isEmpty()) {
            // This will be called if the table is new or just empty.
            return null;
        } else {
            return buildingOwnerShips;
        }
    }

    public void SaveBuildingOccupancies(@NonNull final List<BuildingOccupancies> buildingOccupancies) {
        checkNotNull(buildingOccupancies);
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(buildingOccupancies);
                }
            });
        } catch (Exception ex) {
            Log.e(TAG, "SaveTypes: ", ex);
        } finally {
            realm.close();
        }
    }

    public List<BuildingOccupancies> getBuildingOccupancies() {
        List<BuildingOccupancies> buildingOccupancies = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        try {
            RealmResults<BuildingOccupancies> results = realm.where(BuildingOccupancies.class).findAll();

            if (results != null)
                buildingOccupancies = realm.copyFromRealm(results);

        } catch (Exception ex) {
            Log.e(TAG, "getBuildingOccupancies: ", ex);
        } finally {
            realm.close();
        }

        if (buildingOccupancies.isEmpty()) {
            // This will be called if the table is new or just empty.
            return null;
        } else {
            return buildingOccupancies;
        }
    }

    public void SaveBuildingOccupanciesTypes(@NonNull final List<BuildingOccupancyType> buildingOccupancyTypes) {
        checkNotNull(buildingOccupancyTypes);
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(buildingOccupancyTypes);
                }
            });
        } catch (Exception ex) {
            Log.e(TAG, "SaveTypes: ", ex);
        } finally {
            realm.close();
        }
    }

    public List<BuildingOccupancyType> getBuildingOccupancyType() {
        List<BuildingOccupancyType> buildingOccupancyTypes = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        try {
            RealmResults<BuildingOccupancyType> results = realm.where(BuildingOccupancyType.class).findAll();

            if (results != null)
                buildingOccupancyTypes = realm.copyFromRealm(results);

        } catch (Exception ex) {
            Log.e(TAG, "getBuildingOccupancyType: ", ex);
        } finally {
            realm.close();
        }

        if (buildingOccupancyTypes.isEmpty()) {
            // This will be called if the table is new or just empty.
            return null;
        } else {
            return buildingOccupancyTypes;
        }
    }

    public void SaveTowns(@NonNull final List<Town> towns) {
        checkNotNull(towns);
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(towns);
                }
            });
        } catch (Exception ex) {
            Log.e(TAG, "SaveTowns: ", ex);
        } finally {
            realm.close();
        }
    }

    public List<Town> getTowns() {
        List<Town> towns = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        try {
            RealmResults<Town> results = realm.where(Town.class).findAll();

            if (results != null)
                towns = realm.copyFromRealm(results);

        } catch (Exception ex) {
            Log.e(TAG, "getTowns: ", ex);
        } finally {
            realm.close();
        }

        if (towns.isEmpty()) {
            // This will be called if the table is new or just empty.
            return null;
        } else {
            return towns;
        }
    }

    public void SaveWards(@NonNull final List<Ward> wards) {
        checkNotNull(wards);
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(wards);
                }
            });
        } catch (Exception ex) {
            Log.e(TAG, "SaveWards: ", ex);
        } finally {
            realm.close();
        }
    }

    public List<Ward> getWards(int lgaId) {
        List<Ward> wards = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        try {
            RealmResults<Ward> results = realm.where(Ward.class).equalTo("LgaId", lgaId).findAll();

            if (results != null)
                wards = realm.copyFromRealm(results);

        } catch (Exception ex) {
            Log.e(TAG, "getWards: ", ex);
        } finally {
            realm.close();
        }

        if (wards.isEmpty()) {
            // This will be called if the table is new or just empty.
            return null;
        } else {
            return wards;
        }
    }


    public void SaveBuildings(@NonNull final List<Building> buildings) {
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
            Log.e(TAG, "SaveBuildings: ", ex);
        } finally {
            realm.close();
        }
    }


    public void SaveBuilding(@NonNull final Building building) {
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
            Log.e(TAG, "SaveBuilding: ", ex);
        } finally {
            realm.close();
        }
    }
}
