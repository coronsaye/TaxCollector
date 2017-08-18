package com.vatebra.eirsagentpoc.business.businessdetail;

import android.support.annotation.NonNull;

import com.vatebra.eirsagentpoc.BasePresenter;
import com.vatebra.eirsagentpoc.BaseView;

/**
 * Created by David Eti on 18/08/2017.
 */

public interface BusinessDetailContract {


    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showEditBusiness(@NonNull String businessRin);

        void showBusinessName(String name);

        void showBusinessCategory(String category);

        void showBusinessLga(String lga);

        void showBusinessSector(String sector);

        void showBusinessSubSector(String subsector);

        void showBusinessOperations(String operations);

        void showBusinessProfile(String profile);

        void showBusinessStatus(String status);

        boolean isActive();

        void showCannotGetBusinessError();

        void showCannotEditBusinessError();
    }

    interface Presenter extends BasePresenter {
        void editBusiness();
    }
}
