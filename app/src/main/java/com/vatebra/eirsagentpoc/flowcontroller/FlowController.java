package com.vatebra.eirsagentpoc.flowcontroller;

import android.content.Context;
import android.content.Intent;

import com.vatebra.eirsagentpoc.business.AddEditBusinessActivity;
import com.vatebra.eirsagentpoc.business.BusinessActivity;

/**
 * Created by David Eti on 17/08/2017.
 */

public class FlowController {

    public static void launchBusinessActivity(Context context) {
        Intent intent = new Intent(context, BusinessActivity.class);
        context.startActivity(intent);
    }

    public static void launchAddBusinessActivity(Context context) {
        Intent intent = new Intent(context, AddEditBusinessActivity.class);
        context.startActivity(intent);
    }
}
