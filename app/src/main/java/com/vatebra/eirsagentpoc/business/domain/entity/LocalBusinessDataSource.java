package com.vatebra.eirsagentpoc.business.domain.entity;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
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

    @SuppressWarnings("TryFinallyCanBeTryWithResources")
    @Override
    public void addBusiness(@NonNull final Business business) {
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
}
