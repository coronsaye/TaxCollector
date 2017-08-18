package com.vatebra.eirsagentpoc.business.addeditbusinesses;

import com.vatebra.eirsagentpoc.BasePresenter;
import com.vatebra.eirsagentpoc.BaseView;
import com.vatebra.eirsagentpoc.business.domain.entity.Business;

/**
 * Created by David Eti on 17/08/2017.
 */

public class AddBusinessContract {

    interface View extends BaseView<Presenter> {

        void showBusinessList();

        boolean isActive();

        void showAddBusinessError();

        void showAddSuccessMessage();

        void showEditBusinessMessageSuccess();

        void showEditBusinessError();

        void showCannotGetBusinessError();

        void setBusiness(Business business);
    }

    interface Presenter extends BasePresenter {

        void saveBusiness(String name, String lga);

        void populateBusiness();

        void populateLga();

        void populateCategories();

        void populateSector();

        void populateSubSector(int sectorId);

        void populateOperations();
    }
}
