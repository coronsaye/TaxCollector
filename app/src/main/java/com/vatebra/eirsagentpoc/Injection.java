package com.vatebra.eirsagentpoc;

import android.content.Context;
import android.support.annotation.NonNull;

import com.vatebra.eirsagentpoc.business.domain.entity.BusinessRepository;
import com.vatebra.eirsagentpoc.business.domain.entity.FakeBusinessRemoteDataSource;
import com.vatebra.eirsagentpoc.business.domain.entity.LocalBusinessDataSource;
import com.vatebra.eirsagentpoc.business.domain.usecase.GetBusiness;
import com.vatebra.eirsagentpoc.business.domain.usecase.GetBusinesses;
import com.vatebra.eirsagentpoc.business.domain.usecase.SaveBusiness;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by David Eti on 17/08/2017.
 */

/**
 * Enables injection of mock implementations for
 * {@link com.vatebra.eirsagentpoc.business.domain.entity.BusinessDataSource} at compile time. This is useful for testing, since it allows us to use
 * a fake instance of the class to isolate the dependencies and run a test hermetically.
 */
public class Injection {


    private static BusinessRepository providesBusinessRepository(@NonNull Context context) {
        checkNotNull(context);

        return BusinessRepository.getInstance(LocalBusinessDataSource.getInstance(),
                FakeBusinessRemoteDataSource.getInstance());

    }

    public static UseCaseHandler provideUseCaseHandler() {
        return UseCaseHandler.getInstance();
    }

    public static GetBusinesses provideGetBusinesses(@NonNull Context context) {
        return new GetBusinesses(Injection.providesBusinessRepository(context));
    }

    public static GetBusiness provideGetBusiness(@NonNull Context context) {
        return new GetBusiness(Injection.providesBusinessRepository(context));
    }

    public static SaveBusiness provideSaveBusiness(@NonNull Context context) {
        return new SaveBusiness(Injection.providesBusinessRepository(context));
    }
}
