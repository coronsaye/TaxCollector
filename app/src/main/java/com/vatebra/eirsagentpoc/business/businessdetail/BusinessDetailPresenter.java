package com.vatebra.eirsagentpoc.business.businessdetail;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Strings;
import com.vatebra.eirsagentpoc.UseCase;
import com.vatebra.eirsagentpoc.UseCaseHandler;
import com.vatebra.eirsagentpoc.domain.entity.Business;
import com.vatebra.eirsagentpoc.business.businesses.usecase.GetBusiness;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Collins Oronsaye on 18/08/2017.
 */

public class BusinessDetailPresenter implements BusinessDetailContract.Presenter {


    private final BusinessDetailContract.View mBusinessDetailView;
    private final GetBusiness mGetBusiness;
    private final UseCaseHandler mUseCaseHandler;

    @Nullable
    private String mBusinessRin;


    public BusinessDetailPresenter(@NonNull UseCaseHandler useCaseHandler,
                                   @NonNull BusinessDetailContract.View businessDetailView,
                                   @NonNull GetBusiness getBusiness,
                                   @Nullable String businessRin) {
        mUseCaseHandler = checkNotNull(useCaseHandler, "useCaseHandler cannot be null");
        mBusinessDetailView = checkNotNull(businessDetailView, "businessDetailView cannot be null");
        mGetBusiness = checkNotNull(getBusiness, "getBusiness cannot be null");
        mBusinessRin = businessRin;
        mBusinessDetailView.setPresenter(this);

    }

    @Override
    public void editBusiness() {
        if (Strings.isNullOrEmpty(mBusinessRin)) {
            mBusinessDetailView.showCannotEditBusinessError();
            return;
        }
        mBusinessDetailView.showEditBusiness(mBusinessRin);
    }

    @Override
    public void start() {
        openTask();
    }


    private void openTask() {
        if (Strings.isNullOrEmpty(mBusinessRin)) {
            mBusinessDetailView.showCannotEditBusinessError();
            return;
        }
        mBusinessDetailView.setLoadingIndicator(true);
        mUseCaseHandler.execute(mGetBusiness, new GetBusiness.RequestValues(mBusinessRin), new UseCase.UseCaseCallback<GetBusiness.ResponseValue>() {
            @Override
            public void onSuccess(GetBusiness.ResponseValue response) {
                checkNotNull(response);
                Business business = response.getBusiness();
                if (!mBusinessDetailView.isActive()) {
                    return;
                }
                mBusinessDetailView.setLoadingIndicator(false);
                showBusiness(business);
            }

            @Override
            public void onError() {
                // The view may not be able to handle UI updates anymore
                if (!mBusinessDetailView.isActive()) {
                    return;
                }
                mBusinessDetailView.showCannotGetBusinessError();
            }
        });
    }


    private void showBusiness(@NonNull Business business) {
        checkNotNull(business, "business cannot be null");
        // TODO: 18/08/2017 add business operations
        // TODO: 18/08/2017 add business Status
        if (Strings.isNullOrEmpty(business.getName())) {
            mBusinessDetailView.showBusinessName("none");
        } else {
            mBusinessDetailView.showBusinessName(business.getName());
        }

        if (Strings.isNullOrEmpty(business.getBusinessCategory())) {
            mBusinessDetailView.showBusinessCategory("none");
        } else {
            mBusinessDetailView.showBusinessCategory(business.getBusinessCategory());
        }

        if (Strings.isNullOrEmpty(business.getLga())) {
            mBusinessDetailView.showBusinessLga("none");
        } else {
            mBusinessDetailView.showBusinessLga(business.getLga());
        }
        if (Strings.isNullOrEmpty(business.getBusinessSector())) {
            mBusinessDetailView.showBusinessSector("none");
        } else {
            mBusinessDetailView.showBusinessSector(business.getBusinessSector());
        }
        if (Strings.isNullOrEmpty(business.getBusinessSubSector())) {
            mBusinessDetailView.showBusinessSubSector("none");
        } else {
            mBusinessDetailView.showBusinessSubSector(business.getBusinessSubSector());
        }
        if (Strings.isNullOrEmpty(business.getProfile())) {
            mBusinessDetailView.showBusinessProfile("none");
        } else {
            mBusinessDetailView.showBusinessProfile(business.getProfile());
        }
        if (Strings.isNullOrEmpty(business.getBusinessStructure())) {
            mBusinessDetailView.showBusinessStructure("none");
        } else {
            mBusinessDetailView.showBusinessStructure(business.getBusinessStructure());
        }
    }
}
