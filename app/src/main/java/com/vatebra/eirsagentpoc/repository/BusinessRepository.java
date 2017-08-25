package com.vatebra.eirsagentpoc.repository;

import android.support.annotation.NonNull;

import com.vatebra.eirsagentpoc.domain.entity.Business;
import com.vatebra.eirsagentpoc.domain.entity.BusinessCategory;
import com.vatebra.eirsagentpoc.domain.entity.BusinessDataSource;
import com.vatebra.eirsagentpoc.domain.entity.BusinessSector;
import com.vatebra.eirsagentpoc.domain.entity.BusinessStruture;
import com.vatebra.eirsagentpoc.domain.entity.BusinessSubSector;
import com.vatebra.eirsagentpoc.domain.entity.Lga;
import com.vatebra.eirsagentpoc.domain.entity.LocalBusinessDataSource;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;

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
    public void addBusiness(@NonNull final Business business, @NonNull final UpdateBusinessCallback callback) {
        // TODO: 17/08/2017 Add business to remote or (Add business to database, then sync to remote) ?? sync adapter
        mBusinessRemoteDataSource.addBusiness(business, new UpdateBusinessCallback() {
            @Override
            public void onUpdateSuccessful(String message) {
                callback.onUpdateSuccessful(message);
                if (cachedBusinesses == null) {
                    cachedBusinesses = new ArrayList<>();
                }

                getBusinesses(new LoadBusinessesCallback() {
                    @Override
                    public void onBusinessesLoaded(List<Business> businesses) {

                    }

                    @Override
                    public void onDataNotAvailable() {

                    }
                });
//                cachedBusinesses.add(business);
//                mBusinessLocalDataSource.addBusiness(business, callback);
            }

            @Override
            public void onUpdateFailed() {
                callback.onUpdateFailed();
            }
        });

    }

    @Override
    public void updateBusiness(@NonNull final Business business, @NonNull final UpdateBusinessCallback callback) {
        mBusinessRemoteDataSource.updateBusiness(business, new UpdateBusinessCallback() {
            @Override
            public void onUpdateSuccessful(String message) {
                callback.onUpdateSuccessful(message);
//                mBusinessLocalDataSource.updateBusiness(business, callback);

                getBusinesses(new LoadBusinessesCallback() {
                    @Override
                    public void onBusinessesLoaded(List<Business> businesses) {

                    }

                    @Override
                    public void onDataNotAvailable() {

                    }
                });
            }

            @Override
            public void onUpdateFailed() {
                callback.onUpdateFailed();
            }
        });
    }

    @Override
    public void getLgas(@NonNull final GetObjectCallback<Lga> callback) {
        checkNotNull(callback);

        getLgasFromRemote(callback);
        mBusinessLocalDataSource.getLgas(new GetObjectCallback<Lga>() {
            @Override
            public void onObjectsLoaded(List<Lga> objects) {
                callback.onObjectsLoaded(new ArrayList<>(objects));
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void getCategories(@NonNull final GetObjectCallback<BusinessCategory> callback) {
        checkNotNull(callback);

        mBusinessRemoteDataSource.getCategories(new GetObjectCallback<BusinessCategory>() {
            @Override
            public void onObjectsLoaded(List<BusinessCategory> objects) {
                LocalBusinessDataSource.saveCategories(objects);
            }

            @Override
            public void onDataNotAvailable() {
            }
        });
        mBusinessLocalDataSource.getCategories(new GetObjectCallback<BusinessCategory>() {
            @Override
            public void onObjectsLoaded(List<BusinessCategory> objects) {
                callback.onObjectsLoaded(new ArrayList<>(objects));
            }

            @Override
            public void onDataNotAvailable() {

            }
        });

    }

    @Override
    public void getSectors(@NonNull final GetObjectCallback<BusinessSector> callback) {
        checkNotNull(callback);
        mBusinessRemoteDataSource.getSectors(new GetObjectCallback<BusinessSector>() {
            @Override
            public void onObjectsLoaded(List<BusinessSector> objects) {
                LocalBusinessDataSource.saveSectors(objects);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
        mBusinessLocalDataSource.getSectors(new GetObjectCallback<BusinessSector>() {
            @Override
            public void onObjectsLoaded(List<BusinessSector> objects) {
                callback.onObjectsLoaded(new ArrayList<>(objects));
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void getSubSectors(@NonNull final GetObjectCallback<BusinessSubSector> callback) {
        checkNotNull(callback);
        mBusinessRemoteDataSource.getSubSectors(new GetObjectCallback<BusinessSubSector>() {
            @Override
            public void onObjectsLoaded(List<BusinessSubSector> objects) {
                LocalBusinessDataSource.saveSubSectors(objects);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
        mBusinessLocalDataSource.getSubSectors(new GetObjectCallback<BusinessSubSector>() {
            @Override
            public void onObjectsLoaded(List<BusinessSubSector> objects) {
                callback.onObjectsLoaded(new ArrayList<>(objects));
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void getStructures(@NonNull final GetObjectCallback<BusinessStruture> callback) {
        checkNotNull(callback);
        mBusinessRemoteDataSource.getStructures(new GetObjectCallback<BusinessStruture>() {
            @Override
            public void onObjectsLoaded(List<BusinessStruture> objects) {
                LocalBusinessDataSource.saveStructures(objects);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
        mBusinessLocalDataSource.getStructures(new GetObjectCallback<BusinessStruture>() {
            @Override
            public void onObjectsLoaded(List<BusinessStruture> objects) {
                callback.onObjectsLoaded(new ArrayList<>(objects));
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
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


    private void getLgasFromRemote(final GetObjectCallback<Lga> callback) {
        mBusinessRemoteDataSource.getLgas(new GetObjectCallback<Lga>() {
            @Override
            public void onObjectsLoaded(List<Lga> objects) {
                LocalBusinessDataSource.saveLgas(objects);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }


    private void getCategoriesFromRemote() {

    }

    private void getSectorsFromRemote() {

    }

    private void getSubSectorsFromRemote() {

    }

    private void getStructuresFromRemote() {

    }


}
