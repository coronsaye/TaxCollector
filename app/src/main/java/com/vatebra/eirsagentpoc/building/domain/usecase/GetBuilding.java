package com.vatebra.eirsagentpoc.building.domain.usecase;

import android.support.annotation.NonNull;

import com.vatebra.eirsagentpoc.UseCase;
import com.vatebra.eirsagentpoc.building.domain.entity.Building;
import com.vatebra.eirsagentpoc.building.domain.entity.BuildingDataSource;
import com.vatebra.eirsagentpoc.building.domain.entity.BuildingRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by David Eti on 21/08/2017.
 */

public class GetBuilding extends UseCase<GetBuilding.RequestValues, GetBuilding.ResponseValue> {

    private final BuildingRepository buildingRepository;


    public GetBuilding(@NonNull BuildingRepository buildingRepository) {
        this.buildingRepository = checkNotNull(buildingRepository, "buildingRepository cannot be null");

    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        buildingRepository.getBuilding(requestValues.getBuildingRin(), new BuildingDataSource.GetBuildingCallback() {
            @Override
            public void onBuildingLoaded(Building building) {

                if (building != null) {
                    ResponseValue responseValue = new ResponseValue(building);
                    getUseCaseCallback().onSuccess(responseValue);
                } else {
                    getUseCaseCallback().onError();
                }

            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError();

            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private final String buildingRin;

        public RequestValues(@NonNull String buildingRin) {
            this.buildingRin = checkNotNull(buildingRin, "buildingRin cannot be null!");
        }

        public String getBuildingRin() {
            return buildingRin;
        }
    }


    public static final class ResponseValue implements UseCase.ResponseValue {
        private Building building;

        public ResponseValue(@NonNull Building building) {
            this.building = checkNotNull(building, "building cannot be null!");
        }


        public Building getBuilding() {
            return building;
        }
    }
}
