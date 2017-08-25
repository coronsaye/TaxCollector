package com.vatebra.eirsagentpoc.repository;

import android.util.Log;
import android.widget.Toast;

import com.vatebra.eirsagentpoc.App;
import com.vatebra.eirsagentpoc.building.domain.entity.Building;
import com.vatebra.eirsagentpoc.building.domain.entity.BuildingDataSource;
import com.vatebra.eirsagentpoc.domain.entity.ApiResponse;
import com.vatebra.eirsagentpoc.domain.entity.BuildingCompletion;
import com.vatebra.eirsagentpoc.domain.entity.BuildingFunction;
import com.vatebra.eirsagentpoc.domain.entity.BuildingOccupancies;
import com.vatebra.eirsagentpoc.domain.entity.BuildingOccupancyType;
import com.vatebra.eirsagentpoc.domain.entity.BuildingOwnerShip;
import com.vatebra.eirsagentpoc.domain.entity.BuildingPurpose;
import com.vatebra.eirsagentpoc.domain.entity.BuildingType;
import com.vatebra.eirsagentpoc.domain.entity.BusinessDataSource;
import com.vatebra.eirsagentpoc.domain.entity.Company;
import com.vatebra.eirsagentpoc.domain.entity.EconomicActivity;
import com.vatebra.eirsagentpoc.domain.entity.Town;
import com.vatebra.eirsagentpoc.domain.entity.Ward;
import com.vatebra.eirsagentpoc.entity.BuildingDao;
import com.vatebra.eirsagentpoc.services.RetrofitProxyService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.vatebra.eirsagentpoc.services.RetrofitClient.getRetrofitInstance;

/**
 * Created by David Eti on 25/08/2017.
 */

public class NewBuildingRepository {

    private static NewBuildingRepository INSTANCE = null;
    private static final String TAG = NewBuildingRepository.class.getSimpleName();
    private RetrofitProxyService retrofitProxyService;
    private BuildingDao buildingDao;

    private NewBuildingRepository() {
        buildingDao = BuildingDao.getInstance();
        retrofitProxyService = getRetrofitInstance();

    }

    public synchronized static NewBuildingRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NewBuildingRepository();
        }
        return INSTANCE;
    }

    public List<Building> getBuildings() {
        refresh();
        return buildingDao.getBuildings();
    }

    public void UpdateBuilding(Building building, final OnMessageResponse callback) {
        retrofitProxyService.Editbuilding(building).enqueue(new Callback<ApiResponse<Building>>() {
            @Override
            public void onResponse(Call<ApiResponse<Building>> call, Response<ApiResponse<Building>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    ApiResponse<Building> apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getStatus().equals("00")) {
                        callback.OnSuccessMessage(apiResponse.getMessage());
                        getBuildings();
                    } else {
                        Toast.makeText(App.getInstance(), "Could not Update ", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.e(TAG, "onResponse: " + response.raw());
                    Toast.makeText(App.getInstance(), "Could not Update Building", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Building>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                Toast.makeText(App.getInstance(), "Could not Update, ensure you have an active connection ", Toast.LENGTH_LONG).show();

            }
        });

    }

    public void CreateBuilding(Building building, final OnMessageResponse callback) {
        retrofitProxyService.CreatBuilding(building).enqueue(new Callback<ApiResponse<Building>>() {
            @Override
            public void onResponse(Call<ApiResponse<Building>> call, Response<ApiResponse<Building>> response) {
                ApiResponse<Building> apiResponse = response.body();
                if (apiResponse != null && apiResponse.getStatus().equals("00")) {
                    callback.OnSuccessMessage(apiResponse.getMessage());
                    getBuildings();
                } else {
                    Toast.makeText(App.getInstance(), "Could not Create Company", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Building>> call, Throwable t) {

            }
        });
    }

    public void getBuildingTypes(final BusinessDataSource.GetObjectCallback<BuildingType> callback) {
        if (buildingDao.getBuildingTypes() != null) {
            callback.onObjectsLoaded(buildingDao.getBuildingTypes());
        }
        retrofitProxyService.getBuildingTypes().enqueue(new Callback<ApiResponse<BuildingType>>() {
            @Override
            public void onResponse(Call<ApiResponse<BuildingType>> call, Response<ApiResponse<BuildingType>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    ApiResponse<BuildingType> apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getStatus().equals("00")) {
                        buildingDao.SaveBuildingTypes(apiResponse.getData());
                        callback.onObjectsLoaded(buildingDao.getBuildingTypes());
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<ApiResponse<BuildingType>> call, Throwable t) {

            }
        });
    }

    public void getTowns(final BusinessDataSource.GetObjectCallback<Town> callback) {
        if (buildingDao.getTowns() != null) {
            callback.onObjectsLoaded(buildingDao.getTowns());
        }
        retrofitProxyService.getTowns().enqueue(new Callback<ApiResponse<Town>>() {
            @Override
            public void onResponse(Call<ApiResponse<Town>> call, Response<ApiResponse<Town>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    ApiResponse<Town> apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getStatus().equals("00")) {
                        buildingDao.SaveTowns(apiResponse.getData());
                        callback.onObjectsLoaded(buildingDao.getTowns());
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Town>> call, Throwable t) {

            }
        });
    }

    public void getWards(final BusinessDataSource.GetObjectCallback<Ward> callback, final int lgaId) {
        if (buildingDao.getWards(lgaId) != null) {
            callback.onObjectsLoaded(buildingDao.getWards(lgaId));
        }

        retrofitProxyService.getWards(lgaId).enqueue(new Callback<ApiResponse<Ward>>() {
            @Override
            public void onResponse(Call<ApiResponse<Ward>> call, Response<ApiResponse<Ward>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    ApiResponse<Ward> apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getStatus().equals("00")) {
                        buildingDao.SaveWards(apiResponse.getData());
                        callback.onObjectsLoaded(buildingDao.getWards(lgaId));
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Ward>> call, Throwable t) {

            }
        });
    }

    public void getBuildingCompletion(final BusinessDataSource.GetObjectCallback<BuildingCompletion> callback) {
        if (buildingDao.getBuildingCompletion() != null) {
            callback.onObjectsLoaded(buildingDao.getBuildingCompletion());
        }

        retrofitProxyService.getBuildingCompletions().enqueue(new Callback<ApiResponse<BuildingCompletion>>() {
            @Override
            public void onResponse(Call<ApiResponse<BuildingCompletion>> call, Response<ApiResponse<BuildingCompletion>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    ApiResponse<BuildingCompletion> apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getStatus().equals("00")) {
                        buildingDao.SaveBuildingCompletions(apiResponse.getData());
                        callback.onObjectsLoaded(buildingDao.getBuildingCompletion());
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<ApiResponse<BuildingCompletion>> call, Throwable t) {

            }
        });
    }

    public void getBuildingPurpose(final BusinessDataSource.GetObjectCallback<BuildingPurpose> callback) {
        if (buildingDao.getBuildingPurposes() != null) {
            callback.onObjectsLoaded(buildingDao.getBuildingPurposes());
        }
        retrofitProxyService.getBuildingPurposes().enqueue(new Callback<ApiResponse<BuildingPurpose>>() {
            @Override
            public void onResponse(Call<ApiResponse<BuildingPurpose>> call, Response<ApiResponse<BuildingPurpose>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    ApiResponse<BuildingPurpose> apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getStatus().equals("00")) {
                        buildingDao.SaveBuildingPurposes(apiResponse.getData());
                        callback.onObjectsLoaded(buildingDao.getBuildingPurposes());
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<BuildingPurpose>> call, Throwable t) {

            }
        });
    }

    public void getBuildingOwnership(final BusinessDataSource.GetObjectCallback<BuildingOwnerShip> callback) {
        if (buildingDao.getBuildingOwnerships() != null) {
            callback.onObjectsLoaded(buildingDao.getBuildingOwnerships());
        }
        retrofitProxyService.getBuildingOwnerships().enqueue(new Callback<ApiResponse<BuildingOwnerShip>>() {
            @Override
            public void onResponse(Call<ApiResponse<BuildingOwnerShip>> call, Response<ApiResponse<BuildingOwnerShip>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    ApiResponse<BuildingOwnerShip> apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getStatus().equals("00")) {
                        buildingDao.SaveBuildingOwnerShips(apiResponse.getData());
                        callback.onObjectsLoaded(buildingDao.getBuildingOwnerships());
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<BuildingOwnerShip>> call, Throwable t) {

            }
        });
    }


    public void getBuildingOccupancy(final BusinessDataSource.GetObjectCallback<BuildingOccupancies> callback) {
        if (buildingDao.getBuildingOccupancies() != null) {
            callback.onObjectsLoaded(buildingDao.getBuildingOccupancies());
        }
        retrofitProxyService.getBuildingOccupancies().enqueue(new Callback<ApiResponse<BuildingOccupancies>>() {
            @Override
            public void onResponse(Call<ApiResponse<BuildingOccupancies>> call, Response<ApiResponse<BuildingOccupancies>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    ApiResponse<BuildingOccupancies> apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getStatus().equals("00")) {
                        buildingDao.SaveBuildingOccupancies(apiResponse.getData());
                        callback.onObjectsLoaded(buildingDao.getBuildingOccupancies());
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<BuildingOccupancies>> call, Throwable t) {

            }
        });
    }

    public void getBuildingOccupancyTypes(final BusinessDataSource.GetObjectCallback<BuildingOccupancyType> callback) {
        if (buildingDao.getBuildingOccupancyType() != null) {
            callback.onObjectsLoaded(buildingDao.getBuildingOccupancyType());
        }
        retrofitProxyService.getBuildingOccupancyTypes().enqueue(new Callback<ApiResponse<BuildingOccupancyType>>() {
            @Override
            public void onResponse(Call<ApiResponse<BuildingOccupancyType>> call, Response<ApiResponse<BuildingOccupancyType>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    ApiResponse<BuildingOccupancyType> apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getStatus().equals("00")) {
                        buildingDao.SaveBuildingOccupanciesTypes(apiResponse.getData());
                        callback.onObjectsLoaded(buildingDao.getBuildingOccupancyType());
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<BuildingOccupancyType>> call, Throwable t) {

            }
        });
    }

    public void getBuildingFunctions(final BusinessDataSource.GetObjectCallback<BuildingFunction> callback) {
        if (buildingDao.getBuildingFunctions() != null) {
            callback.onObjectsLoaded(buildingDao.getBuildingFunctions());
        }
        retrofitProxyService.getBuildingFunctions().enqueue(new Callback<ApiResponse<BuildingFunction>>() {
            @Override
            public void onResponse(Call<ApiResponse<BuildingFunction>> call, Response<ApiResponse<BuildingFunction>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    ApiResponse<BuildingFunction> apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getStatus().equals("00")) {
                        buildingDao.SaveBuildingFunctions(apiResponse.getData());
                        callback.onObjectsLoaded(buildingDao.getBuildingFunctions());
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<BuildingFunction>> call, Throwable t) {

            }
        });
    }

    public Building getBuilding(String rin) {
        return buildingDao.getBuilding(rin);

    }

    private void refresh() {
        retrofitProxyService.getBuildings().enqueue(new Callback<ApiResponse<Building>>() {
            @Override
            public void onResponse(Call<ApiResponse<Building>> call, Response<ApiResponse<Building>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    ApiResponse<Building> apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getStatus().equals("00")) {
                        SaveBuildings(apiResponse.getData());
                    }
                } else {
                    //could not get buildings

                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Building>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    public void SaveBuildings(List<Building> buildings) {
        buildingDao.SaveBuildings(buildings);
    }

    public interface OnMessageResponse {
        void OnSuccessMessage(String message);

    }
}
