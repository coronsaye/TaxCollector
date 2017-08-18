package com.vatebra.eirsagentpoc.business.domain.usecase;

import android.support.annotation.NonNull;

import com.vatebra.eirsagentpoc.UseCase;
import com.vatebra.eirsagentpoc.business.domain.entity.Business;
import com.vatebra.eirsagentpoc.business.domain.entity.BusinessDataSource;
import com.vatebra.eirsagentpoc.business.domain.entity.BusinessRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by David Eti on 17/08/2017.
 */

public class GetBusiness extends UseCase<GetBusiness.RequestValues, GetBusiness.ResponseValue> {

    private final BusinessRepository mBusinessRepository;

    public GetBusiness(@NonNull BusinessRepository businessRepository) {
        mBusinessRepository = checkNotNull(businessRepository, "businessRepository cannot be null");
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        mBusinessRepository.getBusiness(requestValues.getBusinessRin(), new BusinessDataSource.GetBusinessCallback() {
            @Override
            public void onBusinessLoaded(Business business) {
                if (business != null) {
                    ResponseValue responseValue = new ResponseValue(business);
                    getUseCaseCallback().onSuccess(responseValue);
                } else {
                    getUseCaseCallback().onError();
                }
            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError();
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {

        private final String mBusinessRin;

        public RequestValues(@NonNull String businessRin) {
            mBusinessRin = checkNotNull(businessRin, "businessRin cannot be null!");
        }

        public String getBusinessRin() {
            return mBusinessRin;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private Business mBusiness;

        public ResponseValue(@NonNull Business business) {
            mBusiness = checkNotNull(business, "business cannot be null!");
        }

        public Business getBusiness() {
            return mBusiness;
        }
    }
}
