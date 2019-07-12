package com.vatebra.eirsagentpoc.business.businesses.usecase;

import android.support.annotation.NonNull;

import com.vatebra.eirsagentpoc.UseCase;
import com.vatebra.eirsagentpoc.domain.entity.Business;
import com.vatebra.eirsagentpoc.domain.entity.BusinessDataSource;
import com.vatebra.eirsagentpoc.repository.BusinessRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Collins Oronsaye on 24/08/2017.
 */
public class UpdateBusiness extends UseCase<UpdateBusiness.RequestValues, UpdateBusiness.ResponseValue> {
    private final BusinessRepository mBusinessRepository;

    public UpdateBusiness(@NonNull BusinessRepository businessRepository) {
        mBusinessRepository = checkNotNull(businessRepository, "BusinessRepository cannot be null");
    }

    @Override
    protected void executeUseCase(UpdateBusiness.RequestValues requestValues) {
        Business business = requestValues.getBusiness();
        mBusinessRepository.updateBusiness(business, new BusinessDataSource.UpdateBusinessCallback() {
            @Override
            public void onUpdateSuccessful(String message) {
                getUseCaseCallback().onSuccess(new ResponseValue(message));
            }

            @Override
            public void onUpdateFailed() {

            }
        });
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

        private String message;

        public ResponseValue(@NonNull String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
