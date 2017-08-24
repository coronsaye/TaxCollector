package com.vatebra.eirsagentpoc.di;

import com.vatebra.eirsagentpoc.repository.BusinessRepository;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by David Eti on 16/08/2017.
 */

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {

    void inject(BusinessRepository businessRepository);
}
