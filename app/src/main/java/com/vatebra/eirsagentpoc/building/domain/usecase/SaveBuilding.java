package com.vatebra.eirsagentpoc.building.domain.usecase;

import android.support.annotation.NonNull;

import com.vatebra.eirsagentpoc.UseCase;
import com.vatebra.eirsagentpoc.building.domain.entity.Building;
import com.vatebra.eirsagentpoc.building.domain.entity.BuildingRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Collins Oronsaye on 21/08/2017.
 */

public class SaveBuilding extends UseCase<SaveBuilding.RequestValues, SaveBuilding.ResponseValue> {

    private final BuildingRepository buildingRepository;

    public SaveBuilding(@NonNull BuildingRepository buildingRepository) {
        this.buildingRepository = checkNotNull(buildingRepository, "BuildingRepository cannot be null");
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        Building building = requestValues.getBuilding();
        buildingRepository.addBuilding(building);
        getUseCaseCallback().onSuccess(new ResponseValue((building)));
    }

    public static final class RequestValues implements UseCase.RequestValues {

        private final Building mBuilding;

        public RequestValues(@NonNull Building building) {
            mBuilding = checkNotNull(building, "building cannot be null!");
        }

        public Building getBuilding() {
            return mBuilding;
        }

    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final Building mBuilding;

        public ResponseValue(@NonNull Building building) {
            mBuilding = checkNotNull(building, "building cannot be null!");
        }

        public Building getBuilding() {
            return mBuilding;
        }

    }
}
