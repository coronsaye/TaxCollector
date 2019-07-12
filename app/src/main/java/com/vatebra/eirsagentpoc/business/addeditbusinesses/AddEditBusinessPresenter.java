package com.vatebra.eirsagentpoc.business.addeditbusinesses;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.vatebra.eirsagentpoc.UseCase;
import com.vatebra.eirsagentpoc.UseCaseHandler;
import com.vatebra.eirsagentpoc.business.businesses.usecase.UpdateBusiness;
import com.vatebra.eirsagentpoc.domain.entity.Business;
import com.vatebra.eirsagentpoc.business.businesses.usecase.GetBusiness;
import com.vatebra.eirsagentpoc.business.businesses.usecase.SaveBusiness;

import java.util.Random;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Collins Oronsaye on 17/08/2017.
 */

public class AddEditBusinessPresenter implements AddBusinessContract.Presenter {

    private final AddBusinessContract.View mAddBusinessView;
    private final SaveBusiness mSaveBusiness;
    private final GetBusiness mGetBusiness;
    private final UseCaseHandler mUseCaseHandler;
    private final UpdateBusiness mUpdateBusiness;
    @Nullable
    private String mBusinessRin;

    public AddEditBusinessPresenter(@NonNull UseCaseHandler useCaseHandler,
                                    @NonNull AddBusinessContract.View addBusinessView,
                                    @NonNull SaveBusiness saveBusiness,
                                    @NonNull GetBusiness getBusiness,
                                    @NonNull UpdateBusiness updateBusiness,
                                    @Nullable String businessRin) {

        mUseCaseHandler = checkNotNull(useCaseHandler, "useCaseHandler cannot be null!");
        mBusinessRin = businessRin;
        mAddBusinessView = checkNotNull(addBusinessView, "addBusinessView cannot be null!");
        mSaveBusiness = checkNotNull(saveBusiness, "saveBusiness cannot be null!");
        mGetBusiness = checkNotNull(getBusiness, "getBusiness cannot be null!");
        mUpdateBusiness = checkNotNull(updateBusiness, "updateBusiness cannot be null");
        mAddBusinessView.setPresenter(this);
    }

    private void showBusiness(Business business) {
        if (mAddBusinessView.isActive()) {

            mAddBusinessView.setBusiness(business);
        }
    }


    @Override
    public void saveBusiness(Business business) {
        checkNotNull(business, "business cannot be null");
        if (isNewBusiness()) {
            createBusiness(business);
        } else {
            updateBusiness(business);
        }

//        if (isNewBusiness()) {
//            Random random = new Random();
//            int rin = random.nextInt();
//            String stringRin = String.valueOf(rin);
//            Business business = new Business(name, stringRin, lga);
//            createBusiness(business);
//        } else {
//            Business business = new Business(name, mBusinessRin, lga);
//            updateBusiness(business);
//        }
    }

    @Override
    public void populateBusiness() {
        if (mBusinessRin == null) {
            throw new RuntimeException("populateBusiness() was called but Business is new.");
        }

        mUseCaseHandler.execute(mGetBusiness, new GetBusiness.RequestValues(mBusinessRin), new UseCase.UseCaseCallback<GetBusiness.ResponseValue>() {
            @Override
            public void onSuccess(GetBusiness.ResponseValue response) {
                showBusiness(response.getBusiness());
            }

            @Override
            public void onError() {
                showCannotGetBusinessError();
            }
        });
    }

    private void showCannotGetBusinessError() {
        if (mAddBusinessView.isActive()) {
            mAddBusinessView.showCannotGetBusinessError();
        }
    }

    @Override
    public void populateLga() {

    }

    @Override
    public void populateCategories() {

    }

    @Override
    public void populateSector() {

    }

    @Override
    public void populateSubSector(int sectorId) {

    }

    @Override
    public void populateOperations() {

    }

    @Override
    public void populateStructures() {

    }


    private void createBusiness(Business business) {
        mUseCaseHandler.execute(mSaveBusiness, new SaveBusiness.RequestValues(business), new UseCase.UseCaseCallback<SaveBusiness.ResponseValue>() {
            @Override
            public void onSuccess(SaveBusiness.ResponseValue response) {

                if (!mAddBusinessView.isActive()) {
                    return;
                }
                mAddBusinessView.showAddSuccessMessage(response.getMessage());

                mAddBusinessView.showBusinessList();
            }

            @Override
            public void onError() {
                if (!mAddBusinessView.isActive()) {
                    return;
                }
                mAddBusinessView.showEditBusinessError();
            }
        });
    }

    private void updateBusiness(Business business) {
        if (mBusinessRin == null) {
            throw new RuntimeException("updateBusiness() was called but Business is new.");
        }

        mUseCaseHandler.execute(mUpdateBusiness, new UpdateBusiness.RequestValues(business), new UseCase.UseCaseCallback<UpdateBusiness.ResponseValue>() {
            @Override
            public void onSuccess(UpdateBusiness.ResponseValue response) {
                if (!mAddBusinessView.isActive())
                    return;

                mAddBusinessView.showEditBusinessMessageSuccess(response.getMessage());
                mAddBusinessView.showBusinessList();
            }

            @Override
            public void onError() {
                mAddBusinessView.showEditBusinessError();

            }
        });

    }

    private boolean isNewBusiness() {
        return mBusinessRin == null;
    }

    @Override
    public void start() {
        if (mBusinessRin != null) {
            populateBusiness();
        }
    }
}
