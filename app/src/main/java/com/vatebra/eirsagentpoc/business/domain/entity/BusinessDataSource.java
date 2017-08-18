package com.vatebra.eirsagentpoc.business.domain.entity;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

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

    void getBusinesses(@NonNull LoadBusinessesCallback callback);

    void getBusiness(@NonNull String mBusinessRin, @NonNull GetBusinessCallback callback);

    void saveBusinesses(@NonNull List<Business> businesses);

    void addBusiness(@NonNull Business business);
}
