package com.vatebra.eirsagentpoc.util;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by David Eti on 05/06/2017.
 */

public class VatEventSharedHelper {
    private static VatEventSharedHelper vatEventSharedHelper;
    SharedPreferenceUtil sharedPreferenceUtil;
    /**
     * Attendee Preferences
     */
    public static final String PREF_ACCOUNT = "attendee_regid";
    Context context;

    public VatEventSharedHelper(Context context) {
        this.context = context;
        sharedPreferenceUtil = new SharedPreferenceUtil();
    }


    public static VatEventSharedHelper getInstance(Context context) {
        if (vatEventSharedHelper == null) {
            vatEventSharedHelper = new VatEventSharedHelper(context);
        }
        return vatEventSharedHelper;
    }

    public double saveAmount(double amount) {
        String value = String.valueOf(amount);
        sharedPreferenceUtil.save(context, value, PREF_ACCOUNT);
        return getAmount();
    }

    public double getAmount() {
        try {
            return Double.parseDouble(sharedPreferenceUtil.getValue(context, PREF_ACCOUNT));
        } catch (Exception ex) {
            return 0;
        }
    }

    public double removeAmount(double amount) {
        double dbAmount = Double.parseDouble(sharedPreferenceUtil.getValue(context, PREF_ACCOUNT));
        double newBalance = dbAmount - amount;
        saveAmount(newBalance);
        return newBalance;
    }


}
