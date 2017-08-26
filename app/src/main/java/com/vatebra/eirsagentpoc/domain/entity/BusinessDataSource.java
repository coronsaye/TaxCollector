package com.vatebra.eirsagentpoc.domain.entity;

import android.support.annotation.NonNull;

import com.vatebra.eirsagentpoc.repository.BusinessRepository;

import java.util.List;

import io.realm.RealmObject;

/**
 * Created by David Eti on 16/08/2017.
 */

public interface BusinessDataSource {


    interface LoadBusinessesCallback {

        void onBusinessesLoaded(List<Business> businesses);

        void onDataNotAvailable();
    }

    interface GetBusinessCallback {

        void onBusinessLoaded(Business business);

        void onDataNotAvailable();
    }

    interface GetObjectCallback<T> {

        void onObjectsLoaded(List<T> objects);

        void onDataNotAvailable();

    }

    interface UpdateBusinessCallback {

        void onUpdateSuccessful(String message);

        void onUpdateFailed();
    }


    void getBusinesses(@NonNull LoadBusinessesCallback callback);

    void getBusiness(@NonNull String mBusinessRin, @NonNull GetBusinessCallback callback);

    void saveBusinesses(@NonNull List<Business> businesses);

    void addBusiness(@NonNull Business business, @NonNull UpdateBusinessCallback callback);

    void updateBusiness(@NonNull Business business, @NonNull UpdateBusinessCallback callback);

    void getLgas(@NonNull GetObjectCallback<Lga> callback);

    void getCategories(@NonNull GetObjectCallback<BusinessCategory> callback);

    void getSectors(@NonNull GetObjectCallback<BusinessSector> callback);

    void getSubSectors(@NonNull GetObjectCallback<BusinessSubSector> callback);

    void getStructures(@NonNull GetObjectCallback<BusinessStruture> callback);

    void GetBusinessProfile(@NonNull Business business, final BusinessRepository.OnApiReceived<AssetProfile> callback);

}
