package com.vatebra.eirsagentpoc.business.domain.entity;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David Eti on 17/08/2017.
 */

public class FakeBusinessRemoteDataSource implements BusinessDataSource {

    private static FakeBusinessRemoteDataSource INSTANCE;


    private static List<Business> TASK_SERVICE_DATA = new ArrayList<>();

    private FakeBusinessRemoteDataSource() {

    }

    public static FakeBusinessRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FakeBusinessRemoteDataSource();
        }
        return INSTANCE;
    }


    @Override
    public void getBusinesses(@NonNull LoadBusinessesCallback callback) {
        if (TASK_SERVICE_DATA.isEmpty()) {
            TASK_SERVICE_DATA.add(new Business("Vatebra", 1, "Victoria Island"));
            TASK_SERVICE_DATA.add(new Business("Andela", 2, "Yaba"));
            TASK_SERVICE_DATA.add(new Business("UBA", 3, "Victoria Island"));
            TASK_SERVICE_DATA.add(new Business("First Bank", 4, "Victoria Island"));
            TASK_SERVICE_DATA.add(new Business("Dangote Cement", 5, "Egbeda"));
            TASK_SERVICE_DATA.add(new Business("Dangote Sugars", 6, "Yaba"));
            TASK_SERVICE_DATA.add(new Business("Iya Basira Canteen", 7, "Surulere"));
            TASK_SERVICE_DATA.add(new Business("Interswitch", 8, "Victoria Island"));
            TASK_SERVICE_DATA.add(new Business("Lacic Learning", 9, "Victoria Island"));
        }
        callback.onBusinessesLoaded(TASK_SERVICE_DATA);
    }

    @Override
    public void getBusiness(@NonNull LoadBusinessCallback callback) {

    }

    @Override
    public void saveBusinesses(@NonNull List<Business> businesses) {
        TASK_SERVICE_DATA = businesses;
    }

    @Override
    public void addBusiness(@NonNull Business business) {

    }
}
