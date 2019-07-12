package com.vatebra.eirsagentpoc.building.domain.usecase;

import android.support.annotation.NonNull;

import com.vatebra.eirsagentpoc.UseCase;
import com.vatebra.eirsagentpoc.building.domain.entity.Building;
import com.vatebra.eirsagentpoc.building.domain.entity.BuildingDataSource;
import com.vatebra.eirsagentpoc.building.domain.entity.BuildingRepository;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Collins Oronsaye on 21/08/2017.
 */

public class GetBuildings extends UseCase<GetBuildings.RequestValues, GetBuildings.ResponseValue> {

    private final BuildingRepository buildingRepository;


    public GetBuildings(@NonNull BuildingRepository buildingRepository) {
        this.buildingRepository = checkNotNull(buildingRepository, "business repository cannot be null");
    }


    @Override
    protected void executeUseCase(RequestValues requestValues) {
        if (requestValues.isForceUpdate()) {
            //refresh repository
        }

        buildingRepository.getBuildings(new BuildingDataSource.LoadBuildingsCallback() {
            @Override
            public void onBuildingsLoaded(List<Building> buildings) {
                ResponseValue responseValue = new ResponseValue(buildings);
                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError();
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {

        private final boolean mForceUpdate;

        public RequestValues(boolean forceUpdate) {
            mForceUpdate = forceUpdate;
        }

        public boolean isForceUpdate() {
            return mForceUpdate;
        }

    }

    public static final class ResponseValue implements UseCase.ResponseValue {
        private final List<Building> mBuildings;

        public ResponseValue(@NonNull List<Building> buildings) {
            mBuildings = checkNotNull(buildings, "buildings cannot be null!");
        }

        public List<Building> getBusinesses() {
            return mBuildings;
        }
    }
}
