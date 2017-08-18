package com.vatebra.eirsagentpoc.flowcontroller;

import android.content.Context;
import android.content.Intent;

import com.vatebra.eirsagentpoc.business.addeditbusinesses.AddEditBusinessActivity;
import com.vatebra.eirsagentpoc.business.addeditbusinesses.AddEditBusinessFragment;
import com.vatebra.eirsagentpoc.business.businessdetail.BusinessDetailActivity;
import com.vatebra.eirsagentpoc.business.businesses.BusinessActivity;


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
}
