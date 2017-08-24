package com.vatebra.eirsagentpoc.business.addeditbusinesses;

import com.vatebra.eirsagentpoc.BasePresenter;
import com.vatebra.eirsagentpoc.BaseView;
import com.vatebra.eirsagentpoc.domain.entity.Business;
import com.vatebra.eirsagentpoc.domain.entity.BusinessCategory;
import com.vatebra.eirsagentpoc.domain.entity.BusinessSector;
import com.vatebra.eirsagentpoc.domain.entity.BusinessStruture;
import com.vatebra.eirsagentpoc.domain.entity.BusinessSubSector;
import com.vatebra.eirsagentpoc.domain.entity.Lga;

import java.util.List;

/**
 * Created by David Eti on 17/08/2017.
 */

public class AddBusinessContract {

    interface View extends BaseView<Presenter> {

        void showBusinessList();

        boolean isActive();

        void showAddBusinessError();

        void showAddSuccessMessage(String message);

        void showEditBusinessMessageSuccess(String message);

        void showEditBusinessError();

        void showCannotGetBusinessError();

        void setBusiness(Business business);
        void clearFields();

        void setLgas(List<Lga> lgas);

        void setCategoties(List<BusinessCategory> categories);

        void setSectors(List<BusinessSector> sectors);

        void setSubSectors(List<BusinessSubSector> subSectors);

        void setStructures(List<BusinessStruture> structures);
    }

    interface Presenter extends BasePresenter {

        void saveBusiness(Business business);

        void populateBusiness();

        void populateLga();

        void populateCategories();

        void populateSector();

        void populateSubSector(int sectorId);

        void populateOperations();

        void populateStructures();
    }
}
