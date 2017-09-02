package com.vatebra.eirsagentpoc.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by David Eti on 20/05/2017.
 */

public class SharedPreferenceUtil {

    public static final String PREFS_NAME = "KUNIBOOKS_PREFS";

    public SharedPreferenceUtil() {
        super();
    }

    public void save(Context context, String text, String PREFS_KEY) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2

        editor.putString(PREFS_KEY, text); //3

        editor.apply(); //4
    }

    public String getValue(Context context, String PREFS_KEY) {
        SharedPreferences settings;
        String text;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        text = settings.getString(PREFS_KEY, null);
        return text;
    }

    public void clearSharedPreference(Context context) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.clear();
        editor.apply();
    }

    public void removeValue(Context context, String PREFS_KEY) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.remove(PREFS_KEY);
        editor.apply();
    }
}
