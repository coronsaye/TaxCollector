package com.vatebra.eirsagentpoc.business.businesses.usecase;

import android.support.annotation.NonNull;

import com.vatebra.eirsagentpoc.UseCase;
import com.vatebra.eirsagentpoc.domain.entity.Business;
import com.vatebra.eirsagentpoc.domain.entity.BusinessDataSource;
import com.vatebra.eirsagentpoc.repository.BusinessRepository;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Collins Oronsaye on 17/08/2017.
 */

public class GetBusinesses extends UseCase<GetBusinesses.RequestValues, GetBusinesses.ResponseValue> {

    private final BusinessRepository mBusinessRepository;


    public GetBusinesses(@NonNull BusinessRepository businessRepository) {
        mBusinessRepository = checkNotNull(businessRepository, "business repository cannot be null");
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        if (requestValues.isForceUpdate()) {
            //refresh repository
        }

        mBusinessRepository.getBusinesses(new BusinessDataSource.LoadBusinessesCallback() {
            @Override
            public void onBusinessesLoaded(List<Business> businesses) {

                ResponseValue responseValue = new ResponseValue(businesses);
                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError();
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {

        private final boolean mForceUpdate;

        public RequestValues(boolean forceUpdate) {
            mForceUpdate = forceUpdate;
        }

        public boolean isForceUpdate() {
            return mForceUpdate;
        }

    }

    public static final class ResponseValue implements UseCase.ResponseValue {
        private final List<Business> mBusinesses;

        public ResponseValue(@NonNull List<Business> businesses) {
            mBusinesses = checkNotNull(businesses, "businesses cannot be null!");
        }

        public List<Business> getBusinesses() {
            return mBusinesses;
        }
    }
}
