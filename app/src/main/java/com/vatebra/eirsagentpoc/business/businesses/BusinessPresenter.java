package com.vatebra.eirsagentpoc.business.businesses;

import android.support.annotation.NonNull;

import com.vatebra.eirsagentpoc.UseCase;
import com.vatebra.eirsagentpoc.UseCaseHandler;
import com.vatebra.eirsagentpoc.domain.entity.Business;
import com.vatebra.eirsagentpoc.business.businesses.usecase.GetBusinesses;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by David Eti on 17/08/2017.
 */

public class BusinessPresenter implements BusinessContract.Presenter {

    private final BusinessContract.View mBusinessView;
    private boolean mFirstLoad = true;
    private final UseCaseHandler mUseCaseHandler;
    private final GetBusinesses mGetBusinesses;

    public BusinessPresenter(@NonNull UseCaseHandler useCaseHandler
            , @NonNull BusinessContract.View businessView,
                             @NonNull GetBusinesses getBusinesses) {
        mUseCaseHandler = checkNotNull(useCaseHandler, "useCaseHandler cannot be null");
        mBusinessView = checkNotNull(businessView, "businessView cannot be null");
        mGetBusinesses = checkNotNull(getBusinesses, "getBusinesses cannot be null");

        mBusinessView.setPresenter(this);
    }

    @Override
    public void loadBusinesses() {
        loadBusinesses(mFirstLoad);
        mFirstLoad = false;
    }

    @Override
    public void addNewBusiness() {
        mBusinessView.showAddBusiness();
    }

    @Override
    public void openBusinessDetail(@NonNull Business business) {
        mBusinessView.showBusinessDetailUi(business.getRin());
    }

    @Override
    public void start() {
        loadBusinesses(false);
    }

    private void loadBusinesses(final boolean showLoadingUI) {
        if (showLoadingUI) {
            mBusinessView.setLoadingIndicator(true);
        }

        GetBusinesses.RequestValues requestValues = new GetBusinesses.RequestValues(true);
        mUseCaseHandler.execute(mGetBusinesses, requestValues, new UseCase.UseCaseCallback<GetBusinesses.ResponseValue>() {
            @Override
            public void onSuccess(GetBusinesses.ResponseValue response) {
                List<Business> businesses = response.getBusinesses();
                // The view may not be able to handle UI updates anymore
                if (!mBusinessView.isActive()) {
                    return;
                }
                if (showLoadingUI) {
                    mBusinessView.setLoadingIndicator(false);
                }
                processBusinesses(businesses);
            }

            @Override
            public void onError() {
                if (!mBusinessView.isActive()) {
                    return;
                }

                mBusinessView.showLoadingBusinessesError();
            }
        });
    }


    private void processBusinesses(List<Business> businesses) {
        if (!businesses.isEmpty()) {
            mBusinessView.showBusinesses(businesses);
        }
    }
}
