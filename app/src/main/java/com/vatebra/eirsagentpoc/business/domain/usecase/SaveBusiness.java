package com.vatebra.eirsagentpoc.business.domain.usecase;

import android.support.annotation.NonNull;

import com.vatebra.eirsagentpoc.UseCase;
import com.vatebra.eirsagentpoc.business.domain.entity.Business;
import com.vatebra.eirsagentpoc.business.domain.entity.BusinessRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by David Eti on 17/08/2017.
 */

public class SaveBusiness extends UseCase<SaveBusiness.RequestValues, SaveBusiness.ResponseValue> {
    private final BusinessRepository mBusinessRepository;

    public SaveBusiness(@NonNull BusinessRepository businessRepository) {
        mBusinessRepository = checkNotNull(businessRepository, "BusinessRepository cannot be null");
    }

    @Override
    protected void executeUseCase(SaveBusiness.RequestValues requestValues) {
        Business business = requestValues.getBusiness();
        mBusinessRepository.addBusiness(business);
        getUseCaseCallback().onSuccess(new ResponseValue(business));
    }

    public static final class RequestValues implements UseCase.RequestValues {

        private final Business mBusiness;

        public RequestValues(@NonNull Business business) {
            mBusiness = checkNotNull(business, "business cannot be null!");
        }

        public Business getBusiness() {
            return mBusiness;
        }


    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final Business mBusiness;

        public ResponseValue(@NonNull Business business) {
            mBusiness = checkNotNull(business, "business cannot be null!");
        }

        public Business getBusiness() {
            return mBusiness;
        }
    }
}
