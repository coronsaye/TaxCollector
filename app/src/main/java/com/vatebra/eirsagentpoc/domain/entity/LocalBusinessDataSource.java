package com.vatebra.eirsagentpoc.domain.entity;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.common.base.Strings;
import com.vatebra.eirsagentpoc.repository.BusinessRepository;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by David Eti on 16/08/2017.
 */

public class LocalBusinessDataSource implements BusinessDataSource {

    private static LocalBusinessDataSource INSTANCE;

    private static String TAG = LocalBusinessDataSource.class.getSimpleName();

    private LocalBusinessDataSource() {

    }

    public static LocalBusinessDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LocalBusinessDataSource();
        }
        return INSTANCE;
    }

    @SuppressWarnings("TryFinallyCanBeTryWithResources")
    @Override
    public void getBusinesses(@NonNull final LoadBusinessesCallback callback) {
        List<Business> businesses = new ArrayList<>();

        Realm realm = Realm.getDefaultInstance();
        try {
            RealmResults<Business> results = realm.where(Business.class).findAll();

            if (results != null)
                businesses = realm.copyFromRealm(results);

        } catch (Exception ex) {
            Log.e(TAG, "getBusinesses: ", ex);
        } finally {
            realm.close();
        }

        if (businesses.isEmpty()) {
            // This will be called if the table is new or just empty.
            callback.onDataNotAvailable();
        } else {
            callback.onBusinessesLoaded(businesses);
        }
    }

    @SuppressWarnings("TryFinallyCanBeTryWithResources")
    @Override
    public void getBusiness(@NonNull final String mBusinessRin, @NonNull final GetBusinessCallback callback) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Business> results;
        Business business = new Business();
        try {
            results = realm.where(Business.class).findAll();
            Business newBusiness = results.where().equalTo("rin", mBusinessRin).findFirst();
            //FIXES REALM ISSUE : This Realm instance has already been closed, making it unusable
            business = realm.copyFromRealm(newBusiness);

        } catch (Exception ex) {
            Log.e(TAG, "getBusiness: ", ex);
        } finally {
            realm.close();
        }

        if (!Strings.isNullOrEmpty(business.getRin())) {
            callback.onBusinessLoaded(business);
        } else {
            callback.onDataNotAvailable();
        }
    }


    @SuppressWarnings("TryFinallyCanBeTryWithResources")
    @Override
    public void saveBusinesses(@NonNull final List<Business> businesses) {
        checkNotNull(businesses);
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(businesses);
                }
            });
        } catch (Exception ex) {
            Log.e(TAG, "saveBusinesses: ", ex);
        } finally {
            realm.close();
        }
    }

    public  static void saveLgas(@NonNull final List<Lga> lgas) {
        checkNotNull(lgas);
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(lgas);
                }
            });
        } catch (Exception ex) {
            Log.e(TAG, "saveBusinesses: ", ex);
        } finally {
            realm.close();
        }
    }

    public static void saveSectors(@NonNull final List<BusinessSector> sectors) {
        checkNotNull(sectors);
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(sectors);
                }
            });
        } catch (Exception ex) {
            Log.e(TAG, "saveBusinesses: ", ex);
        } finally {
            realm.close();
        }
    }

    public static void saveSubSectors(@NonNull final List<BusinessSubSector> subSectors) {
        checkNotNull(subSectors);
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(subSectors);
                }
            });
        } catch (Exception ex) {
            Log.e(TAG, "saveBusinesses: ", ex);
        } finally {
            realm.close();
        }
    }

    public static void saveCategories(@NonNull final List<BusinessCategory> categories) {
        checkNotNull(categories);
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(categories);
                }
            });
        } catch (Exception ex) {
            Log.e(TAG, "saveBusinesses: ", ex);
        } finally {
            realm.close();
        }
    }

    public static void saveStructures(@NonNull final List<BusinessStruture> businessStrutures) {
        checkNotNull(businessStrutures);
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(businessStrutures);
                }
            });
        } catch (Exception ex) {
            Log.e(TAG, "saveBusinesses: ", ex);
        } finally {
            realm.close();
        }
    }

    @SuppressWarnings("TryFinallyCanBeTryWithResources")
    @Override
    public void addBusiness(@NonNull final Business business, @NonNull UpdateBusinessCallback callback) {
        checkNotNull(business);
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(business);
                }
            });
        } catch (Exception ex) {
            Log.e(TAG, "addBusiness: ", ex);
        } finally {
            realm.close();
        }
    }

    @Override
    public void updateBusiness(@NonNull final Business business, @NonNull UpdateBusinessCallback callback) {
        checkNotNull(business);
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(business);
                }
            });
        } catch (Exception ex) {
            Log.e(TAG, "updateBusiness: ", ex);
        } finally {
            realm.close();
        }
    }

    @Override
    public void getLgas(@NonNull GetObjectCallback<Lga> callback) {
        List<Lga> lgas = new ArrayList<>();

        Realm realm = Realm.getDefaultInstance();
        try {
            RealmResults<Lga> results = realm.where(Lga.class).findAll();

            if (results != null)
                lgas = realm.copyFromRealm(results);

        } catch (Exception ex) {
            Log.e(TAG, "getLgas: ", ex);
        } finally {
            realm.close();
        }

        if (lgas.isEmpty()) {
            // This will be called if the table is new or just empty.
            callback.onDataNotAvailable();
        } else {
            callback.onObjectsLoaded(lgas);
        }
    }

    @Override
    public void getCategories(@NonNull GetObjectCallback<BusinessCategory> callback) {
        List<BusinessCategory> data = new ArrayList<>();

        Realm realm = Realm.getDefaultInstance();
        try {
            RealmResults<BusinessCategory> results = realm.where(BusinessCategory.class).findAll();

            if (results != null)
                data = realm.copyFromRealm(results);

        } catch (Exception ex) {
            Log.e(TAG, "getCategories: ", ex);
        } finally {
            realm.close();
        }

        if (data.isEmpty()) {
            // This will be called if the table is new or just empty.
            callback.onDataNotAvailable();
        } else {
            callback.onObjectsLoaded(data);
        }
    }

    @Override
    public void getSectors(@NonNull GetObjectCallback<BusinessSector> callback) {
        List<BusinessSector> data = new ArrayList<>();

        Realm realm = Realm.getDefaultInstance();
        try {
            RealmResults<BusinessSector> results = realm.where(BusinessSector.class).findAll();

            if (results != null)
                data = realm.copyFromRealm(results);

        } catch (Exception ex) {
            Log.e(TAG, "getSectors: ", ex);
        } finally {
            realm.close();
        }

        if (data.isEmpty()) {
            // This will be called if the table is new or just empty.
            callback.onDataNotAvailable();
        } else {
            callback.onObjectsLoaded(data);
        }
    }

    @Override
    public void getSubSectors(@NonNull GetObjectCallback<BusinessSubSector> callback) {
        List<BusinessSubSector> data = new ArrayList<>();

        Realm realm = Realm.getDefaultInstance();
        try {
            RealmResults<BusinessSubSector> results = realm.where(BusinessSubSector.class).findAll();

            if (results != null)
                data = realm.copyFromRealm(results);

        } catch (Exception ex) {
            Log.e(TAG, "getSubSectors: ", ex);
        } finally {
            realm.close();
        }

        if (data.isEmpty()) {
            // This will be called if the table is new or just empty.
            callback.onDataNotAvailable();
        } else {
            callback.onObjectsLoaded(data);
        }
    }

    @Override
    public void getStructures(@NonNull GetObjectCallback<BusinessStruture> callback) {
        List<BusinessStruture> data = new ArrayList<>();

        Realm realm = Realm.getDefaultInstance();
        try {
            RealmResults<BusinessStruture> results = realm.where(BusinessStruture.class).findAll();

            if (results != null)
                data = realm.copyFromRealm(results);

        } catch (Exception ex) {
            Log.e(TAG, "getStructures: ", ex);
        } finally {
            realm.close();
        }

        if (data.isEmpty()) {
            // This will be called if the table is new or just empty.
            callback.onDataNotAvailable();
        } else {
            callback.onObjectsLoaded(data);
        }
    }

    @Override
    public void GetBusinessProfile(@NonNull Business business, BusinessRepository.OnApiReceived<AssetProfile> callback) {

    }

}
