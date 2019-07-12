package com.vatebra.eirsagentpoc.flowcontroller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.vatebra.eirsagentpoc.building.domain.entity.Building;
import com.vatebra.eirsagentpoc.business.addeditbusinesses.AddEditBusinessActivity;
import com.vatebra.eirsagentpoc.business.addeditbusinesses.AddEditBusinessFragment;
import com.vatebra.eirsagentpoc.business.businessdetail.BusinessDetailActivity;
import com.vatebra.eirsagentpoc.business.businesses.BusinessActivity;
import com.vatebra.eirsagentpoc.dashboard.DashboardActivity;
import com.vatebra.eirsagentpoc.domain.entity.AssetProfile;
import com.vatebra.eirsagentpoc.domain.entity.Business;
import com.vatebra.eirsagentpoc.domain.entity.TaxPayer;
import com.vatebra.eirsagentpoc.payment.TaxPayerActivity;
import com.vatebra.eirsagentpoc.taxpayers.ProfilingActivity;
import com.vatebra.eirsagentpoc.taxpayers.buildings.AddEditBuidingActivity;
import com.vatebra.eirsagentpoc.taxpayers.buildings.BuildingDetailsActivity;
import com.vatebra.eirsagentpoc.taxpayers.buildings.BuildingsActivity;
import com.vatebra.eirsagentpoc.taxpayers.companies.AddEditCompanyActivity;
import com.vatebra.eirsagentpoc.taxpayers.companies.CompaniesActivity;
import com.vatebra.eirsagentpoc.taxpayers.companies.CompanyDetailActivity;
import com.vatebra.eirsagentpoc.taxpayers.individuals.AddEditIndividualActivity;
import com.vatebra.eirsagentpoc.taxpayers.individuals.IndividualDetailActivity;
import com.vatebra.eirsagentpoc.taxpayers.individuals.IndividualsActivity;

import org.parceler.Parcels;

import static com.vatebra.eirsagentpoc.taxpayers.individuals.IndividualDetailActivity.EXTRA_INDIVIDUAL_RIN;


/**
 * Created by Collins Oronsaye on 17/08/2017.
 */

public class FlowController {
    public static void launchDashboardctivity(Context context) {
        Intent intent = new Intent(context, DashboardActivity.class);
        context.startActivity(intent);
    }

    public static void launchBusinessActivity(Context context) {
        Intent intent = new Intent(context, BusinessActivity.class);
        context.startActivity(intent);
    }

    public static void launchBusinessActivity(Context context, Building building) {
        Intent intent = new Intent(context, BusinessActivity.class);
        Bundle bund = new Bundle();
        bund.putParcelable(ProfilingActivity.EXTRA_OBJECT_BUILDING_KEY, Parcels.wrap(building));
        intent.putExtras(bund);
        context.startActivity(intent);
    }

    public static void launchAddEditBusinessActivity(Context context) {
        Intent intent = new Intent(context, AddEditBusinessActivity.class);
        context.startActivity(intent);
    }

    public static void launchBuildingActivity(Context context, boolean isChooser) {
        Intent intent = new Intent(context, BuildingsActivity.class);
        Bundle bund = new Bundle();
        bund.putBoolean(ProfilingActivity.EXTRA_PROFILE_KEY, isChooser);
        intent.putExtras(bund);
        context.startActivity(intent);
    }

    public static void launchBuildingActivity(Context context, boolean isChooser, Business business) {
        Intent intent = new Intent(context, BuildingsActivity.class);
        Bundle bund = new Bundle();
        bund.putBoolean(ProfilingActivity.EXTRA_PROFILE_KEY, isChooser);
        bund.putParcelable(ProfilingActivity.EXTRA_OBJECT_BUSINESS_KEY, Parcels.wrap(business));
        intent.putExtras(bund);
        context.startActivity(intent);
    }

    public static void launchAddEditBusinessActivity(Context context, String businessRin) {
        Intent intent = new Intent(context, AddEditBusinessActivity.class);
        intent.putExtra(AddEditBusinessFragment.ARGUMENT_EDIT_BUSINESS_ID, businessRin);
        context.startActivity(intent);
    }

    public static void launchAddEditBusinessActivity(Context context, Building building) {
        Intent intent = new Intent(context, AddEditBusinessActivity.class);
        Bundle bund = new Bundle();
        bund.putParcelable(ProfilingActivity.EXTRA_OBJECT_BUILDING_KEY, Parcels.wrap(building));
        intent.putExtras(bund);
        context.startActivity(intent);
    }

    public static void launchAddEditBusinessActivity(Context context, Building building, Business business) {
        Intent intent = new Intent(context, AddEditBusinessActivity.class);
        Bundle bund = new Bundle();
        bund.putParcelable(ProfilingActivity.EXTRA_OBJECT_BUILDING_KEY, Parcels.wrap(building));
        bund.putParcelable(ProfilingActivity.EXTRA_OBJECT_BUSINESS_KEY, Parcels.wrap(business));
        intent.putExtras(bund);
        context.startActivity(intent);
    }


    public static void launchBusinessDetailsActivity(Context context, String businessRin) {
        Intent intent = new Intent(context, BusinessDetailActivity.class);
        intent.putExtra(BusinessDetailActivity.EXTRA_BUSINESS_RIN, businessRin);
        context.startActivity(intent);
    }

    public static void launchIndividualActivity(Context context) {
        Intent intent = new Intent(context, IndividualsActivity.class);
        context.startActivity(intent);
    }

    public static void launchIndividualDetailsActivity(Context context, String rin) {
        Intent intent = new Intent(context, IndividualDetailActivity.class);
        intent.putExtra(IndividualDetailActivity.EXTRA_INDIVIDUAL_RIN, rin);
        context.startActivity(intent);
    }

    public static void launchAddEditIndividualActivity(Context context) {
        Intent intent = new Intent(context, AddEditIndividualActivity.class);
        context.startActivity(intent);
    }

    public static void launchAddEditIndividualActivity(Context context, Business business) {
        Intent intent = new Intent(context, AddEditIndividualActivity.class);
        Bundle bund = new Bundle();
        bund.putParcelable(ProfilingActivity.EXTRA_OBJECT_BUSINESS_KEY, Parcels.wrap(business));
        intent.putExtras(bund);
        context.startActivity(intent);
    }

    public static void launchAddEditIndividualActivity(Context context, Building building) {
        Intent intent = new Intent(context, AddEditIndividualActivity.class);
        Bundle bund = new Bundle();
        bund.putParcelable(ProfilingActivity.EXTRA_OBJECT_BUILDING_KEY, Parcels.wrap(building));
        intent.putExtras(bund);
        context.startActivity(intent);
    }

    public static void launchIndividualActivity(Context context, boolean isChooser, Business business) {
        Intent intent = new Intent(context, IndividualsActivity.class);
        Bundle bund = new Bundle();
        bund.putBoolean(ProfilingActivity.EXTRA_PROFILE_KEY, isChooser);
        bund.putParcelable(ProfilingActivity.EXTRA_OBJECT_BUSINESS_KEY, Parcels.wrap(business));
        intent.putExtras(bund);
        context.startActivity(intent);
    }

    public static void launchIndividualActivity(Context context, boolean isChooser, Building building) {
        Intent intent = new Intent(context, IndividualsActivity.class);
        Bundle bund = new Bundle();
        bund.putBoolean(ProfilingActivity.EXTRA_PROFILE_KEY, isChooser);
        bund.putParcelable(ProfilingActivity.EXTRA_OBJECT_BUILDING_KEY, Parcels.wrap(building));
        intent.putExtras(bund);
        context.startActivity(intent);
    }

    public static void launchAddEditIndividualActivity(Context context, String userRin) {
        Intent intent = new Intent(context, AddEditIndividualActivity.class);
        intent.putExtra(IndividualDetailActivity.EXTRA_INDIVIDUAL_RIN, userRin);
        context.startActivity(intent);
    }

    public static void launchCompanyActivity(Context context) {
        Intent intent = new Intent(context, CompaniesActivity.class);
        context.startActivity(intent);
    }

    public static void launchCompanyActivity(Context context, boolean isChooser, Business business) {
        Intent intent = new Intent(context, CompaniesActivity.class);
        Bundle bund = new Bundle();
        bund.putBoolean(ProfilingActivity.EXTRA_PROFILE_KEY, isChooser);
        bund.putParcelable(ProfilingActivity.EXTRA_OBJECT_BUSINESS_KEY, Parcels.wrap(business));
        intent.putExtras(bund);
        context.startActivity(intent);
    }

    public static void launchCompanyActivity(Context context, boolean isChooser, Building building) {
        Intent intent = new Intent(context, CompaniesActivity.class);
        Bundle bund = new Bundle();
        bund.putBoolean(ProfilingActivity.EXTRA_PROFILE_KEY, isChooser);
        bund.putParcelable(ProfilingActivity.EXTRA_OBJECT_BUILDING_KEY, Parcels.wrap(building));
        intent.putExtras(bund);
        context.startActivity(intent);
    }

    public static void launchCompanyDetailsActivity(Context context, String rin) {
        Intent intent = new Intent(context, CompanyDetailActivity.class);
        intent.putExtra(CompanyDetailActivity.EXTRA_COMPANY_RIN, rin);
        context.startActivity(intent);
    }

    public static void launchAddEditCompanyActivity(Context context) {
        Intent intent = new Intent(context, AddEditCompanyActivity.class);
        context.startActivity(intent);
    }

    public static void launchAddEditCompanyActivity(Context context, Business business) {
        Intent intent = new Intent(context, AddEditCompanyActivity.class);
        Bundle bund = new Bundle();
        bund.putParcelable(ProfilingActivity.EXTRA_OBJECT_BUSINESS_KEY, Parcels.wrap(business));
        intent.putExtras(bund);
        context.startActivity(intent);
    }

    public static void launchAddEditCompanyActivity(Context context, Building building) {
        Intent intent = new Intent(context, AddEditCompanyActivity.class);
        Bundle bund = new Bundle();
        bund.putParcelable(ProfilingActivity.EXTRA_OBJECT_BUILDING_KEY, Parcels.wrap(building));
        intent.putExtras(bund);
        context.startActivity(intent);
    }

    public static void launchAddEditCompanyActivity(Context context, String companyRin) {
        Intent intent = new Intent(context, AddEditCompanyActivity.class);
        intent.putExtra(CompanyDetailActivity.EXTRA_COMPANY_RIN, companyRin);
        context.startActivity(intent);
    }

    public static void launchBuildingActivity(Context context) {
        Intent intent = new Intent(context, BuildingsActivity.class);
        context.startActivity(intent);
    }

    public static void launchBuildingDetailsActivity(Context context, String rin) {
        Intent intent = new Intent(context, BuildingDetailsActivity.class);
        intent.putExtra(BuildingDetailsActivity.EXTRA_BUILDING_RIN, rin);
        context.startActivity(intent);
    }


    public static void launchAddEditBuildingActivity(Context context) {
        Intent intent = new Intent(context, AddEditBuidingActivity.class);
        context.startActivity(intent);
    }

    public static void launchAddEditBuildingActivity(Context context, String buildingRin) {
        Intent intent = new Intent(context, AddEditBuidingActivity.class);
        intent.putExtra(BuildingDetailsActivity.EXTRA_BUILDING_RIN, buildingRin);
        context.startActivity(intent);
    }


    public static void launchProfilingActivity(Context context, AssetProfile profile) {
        Intent intent = new Intent(context, ProfilingActivity.class);
        intent.putExtra(ProfilingActivity.EXTRA_PROFILE_KEY, profile);
        context.startActivity(intent);
    }

    public static void launchProfilingActivity(Context context, AssetProfile profile, Building building) {
        Intent intent = new Intent(context, ProfilingActivity.class);
        Bundle bund = new Bundle();

        bund.putSerializable(ProfilingActivity.EXTRA_PROFILE_KEY, profile);
        bund.putParcelable(ProfilingActivity.EXTRA_OBJECT_BUILDING_KEY, Parcels.wrap(building));
        intent.putExtras(bund);

        context.startActivity(intent);
    }

    public static void launchProfilingActivity(Context context, AssetProfile profile, Business business) {
        Intent intent = new Intent(context, ProfilingActivity.class);
        Bundle bund = new Bundle();
        bund.putSerializable(ProfilingActivity.EXTRA_PROFILE_KEY, profile);
        bund.putParcelable(ProfilingActivity.EXTRA_OBJECT_BUSINESS_KEY, Parcels.wrap(business));
        intent.putExtras(bund);
        context.startActivity(intent);
    }

    public static void launchTaxPayerActivity(Context context, TaxPayer taxPayer) {
        Intent intent = new Intent(context, TaxPayerActivity.class);
        intent.putExtra(TaxPayerActivity.EXTRA_TAXPAYER, taxPayer);
        context.startActivity(intent);
    }


}
