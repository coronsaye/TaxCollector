package com.vatebra.eirsagentpoc.business.domain.entity;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by David Eti on 16/08/2017.
 */

public class BusinessRepository implements BusinessDataSource {

    private static BusinessRepository INSTANCE = null;
    private final BusinessDataSource mBusinessLocalDataSource;
    private final BusinessDataSource mBusinessRemoteDataSource;
    private List<Business> cachedBusinesses;

    private BusinessRepository(@NonNull BusinessDataSource businessLocalDataSource,
                               @NonNull BusinessDataSource businessRemoteDataSource) {
        mBusinessLocalDataSource = checkNotNull(businessLocalDataSource);
        mBusinessRemoteDataSource = checkNotNull(businessRemoteDataSource);
    }


    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param businessRemoteDataSource the backend data source
     * @param businessLocalDataSource  the device storage data source
     * @return the {@link BusinessRepository} instance
     */
    public static BusinessRepository getInstance(BusinessDataSource businessLocalDataSource, BusinessDataSource businessRemoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new BusinessRepository(businessLocalDataSource, businessRemoteDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }


    @Override
    public void getBusinesses(@NonNull final LoadBusinessesCallback callback) {
        checkNotNull(callback);

        //go to server to get new items then save in background
        getBusinessesFromRemoteDataSource(callback);

        //return items from localdatabase
        mBusinessLocalDataSource.getBusinesses(new LoadBusinessesCallback() {
            @Override
            public void onBusinessesLoaded(List<Business> businesses) {
                refreshBusinesses(businesses);
                callback.onBusinessesLoaded(new ArrayList<>(cachedBusinesses));
            }

            @Override
            public void onDataNotAvailable() {
                getBusinessesFromRemoteDataSource(callback);
            }
        });
    }


    private void refreshBusinesses(@NonNull List<Business> businesses) {
        cachedBusinesses = checkNotNull(businesses);
    }

    @Override
    public void getBusiness(@NonNull String mBusinessRin, @NonNull final GetBusinessCallback callback) {
        checkNotNull(mBusinessRin);
        checkNotNull(callback);
        // Is the task in the local data source? If not, query the network.
        mBusinessLocalDataSource.getBusiness(mBusinessRin, new GetBusinessCallback() {
            @Override
            public void onBusinessLoaded(Business business) {
                callback.onBusinessLoaded(business);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void saveBusinesses(@NonNull List<Business> businesses) {
        //save businesses to database
        checkNotNull(businesses);
        mBusinessLocalDataSource.saveBusinesses(businesses);
        if (cachedBusinesses == null) {
            cachedBusinesses = new ArrayList<>();
        }
        cachedBusinesses = businesses;
    }

    @Override
    public void addBusiness(@NonNull Business business) {
        // TODO: 17/08/2017 Add business to remote or (Add business to database, then sync to remote) ?? sync adapter
        mBusinessRemoteDataSource.addBusiness(business);
        mBusinessLocalDataSource.addBusiness(business);
        if (cachedBusinesses == null) {
            cachedBusinesses = new ArrayList<>();
        }
        cachedBusinesses.add(business);
    }

    private void getBusinessesFromRemoteDataSource(@NonNull final LoadBusinessesCallback callback) {
        mBusinessRemoteDataSource.getBusinesses(new LoadBusinessesCallback() {
            @Override
            public void onBusinessesLoaded(List<Business> businesses) {
                refreshLocalDataSource(businesses);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void refreshLocalDataSource(List<Business> businesses) {
        mBusinessLocalDataSource.saveBusinesses(businesses);
    }
}
