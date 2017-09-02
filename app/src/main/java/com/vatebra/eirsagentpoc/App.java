package com.vatebra.eirsagentpoc;

import android.app.Application;

import com.vatebra.eirsagentpoc.di.AppComponent;
import com.vatebra.eirsagentpoc.util.VatEventSharedHelper;


import co.paystack.android.PaystackSdk;
import io.realm.Realm;

/**
 * Created by David Eti on 16/08/2017.
 */

public class App extends Application {
    private AppComponent appComponent;
    private static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        initApplication();
        PaystackSdk.initialize(getApplicationContext());
        VatEventSharedHelper helper = VatEventSharedHelper.getInstance(getApplicationContext());
        if (helper.getAmount() == 0) {
            helper.saveAmount(500000);
        }

//        appComponent = DaggerAppComponent.builder()
//                .appModule(new AppModule(this))
//                .networkModule(new NetworkModule("https://api.github.com"))
//                .build();
    }

    private void initApplication() {
        instance = this;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }


}
