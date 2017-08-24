package com.vatebra.eirsagentpoc.flowcontroller;

import android.content.Context;
import android.content.Intent;

import com.vatebra.eirsagentpoc.business.addeditbusinesses.AddEditBusinessActivity;
import com.vatebra.eirsagentpoc.business.addeditbusinesses.AddEditBusinessFragment;
import com.vatebra.eirsagentpoc.business.businessdetail.BusinessDetailActivity;
import com.vatebra.eirsagentpoc.business.businesses.BusinessActivity;
import com.vatebra.eirsagentpoc.taxpayers.companies.CompaniesActivity;
import com.vatebra.eirsagentpoc.taxpayers.companies.CompanyDetailActivity;
import com.vatebra.eirsagentpoc.taxpayers.individuals.AddEditIndividualActivity;
import com.vatebra.eirsagentpoc.taxpayers.individuals.IndividualDetailActivity;
import com.vatebra.eirsagentpoc.taxpayers.individuals.IndividualsActivity;

import static com.vatebra.eirsagentpoc.taxpayers.individuals.IndividualDetailActivity.EXTRA_INDIVIDUAL_RIN;


/**
 * Created by David Eti on 17/08/2017.
 */

public class FlowController {

    public static void launchBusinessActivity(Context context) {
        Intent intent = new Intent(context, BusinessActivity.class);
        context.startActivity(intent);
    }

    public static void launchAddEditBusinessActivity(Context context) {
        Intent intent = new Intent(context, AddEditBusinessActivity.class);
        context.startActivity(intent);
    }

    public static void launchAddEditBusinessActivity(Context context, String businessRin) {
        Intent intent = new Intent(context, AddEditBusinessActivity.class);
        intent.putExtra(AddEditBusinessFragment.ARGUMENT_EDIT_BUSINESS_ID, businessRin);
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

    public static void launchAddEditIndividualActivity(Context context){
        Intent intent = new Intent(context, AddEditIndividualActivity.class);
        context.startActivity(intent);
    }
    public static void launchAddEditIndividualActivity(Context context, String userRin){
        Intent intent = new Intent(context, AddEditIndividualActivity.class);
        intent.putExtra(IndividualDetailActivity.EXTRA_INDIVIDUAL_RIN,userRin);
        context.startActivity(intent);
    }
    public static void launchCompanyActivity(Context context) {
        Intent intent = new Intent(context, CompaniesActivity.class);
        context.startActivity(intent);
    }
    public static void launchCompanyDetailsActivity(Context context, String rin) {
        Intent intent = new Intent(context, CompanyDetailActivity.class);
        intent.putExtra(CompanyDetailActivity.EXTRA_COMPANY_RIN, rin);
        context.startActivity(intent);
    }
}
