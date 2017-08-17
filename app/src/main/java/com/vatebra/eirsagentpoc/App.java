package com.vatebra.eirsagentpoc;

import android.app.Application;

import com.vatebra.eirsagentpoc.di.AppComponent;
import com.vatebra.eirsagentpoc.di.AppModule;
import com.vatebra.eirsagentpoc.di.DaggerAppComponent;
import com.vatebra.eirsagentpoc.di.NetworkModule;

import io.realm.Realm;

/**
 * Created by David Eti on 16/08/2017.
 */

public class App extends Application {
    private AppComponent appComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
//        appComponent = DaggerAppComponent.builder()
//                .appModule(new AppModule(this))
//                .networkModule(new NetworkModule("https://api.github.com"))
//                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

}
