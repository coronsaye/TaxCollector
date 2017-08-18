package com.vatebra.eirsagentpoc.business.businesses;

import android.support.annotation.NonNull;

import com.vatebra.eirsagentpoc.BasePresenter;
import com.vatebra.eirsagentpoc.BaseView;
import com.vatebra.eirsagentpoc.business.domain.entity.Business;

import java.util.List;

/**
 * Created by David Eti on 16/08/2017.
 */

public interface BusinessContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showBusinesses(List<Business> businesses);

        void showAddBusiness();

        void showBusinessDetailUi(String businessRin);

        void showNoBusinesses();

        void showSuccessfullySavedMessages();

        void showLoadingBusinessesError();

        boolean isActive();

    }


    interface Presenter extends BasePresenter {
        void loadBusinesses();

        void addNewBusiness();

        void openBusinessDetail(@NonNull Business business);
    }
}
