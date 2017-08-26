package com.vatebra.eirsagentpoc.taxpayers.buildings;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.vatebra.eirsagentpoc.App;
import com.vatebra.eirsagentpoc.Injection;
import com.vatebra.eirsagentpoc.R;
import com.vatebra.eirsagentpoc.building.domain.entity.Building;
import com.vatebra.eirsagentpoc.building.domain.entity.BuildingRepository;
import com.vatebra.eirsagentpoc.domain.entity.AssetProfile;
import com.vatebra.eirsagentpoc.domain.entity.BuildingCompletion;
import com.vatebra.eirsagentpoc.domain.entity.BuildingOccupancies;
import com.vatebra.eirsagentpoc.domain.entity.BuildingOccupancyType;
import com.vatebra.eirsagentpoc.domain.entity.BuildingOwnerShip;
import com.vatebra.eirsagentpoc.domain.entity.BuildingPurpose;
import com.vatebra.eirsagentpoc.domain.entity.BuildingType;
import com.vatebra.eirsagentpoc.domain.entity.BusinessDataSource;
import com.vatebra.eirsagentpoc.domain.entity.Lga;
import com.vatebra.eirsagentpoc.domain.entity.Town;
import com.vatebra.eirsagentpoc.domain.entity.Ward;
import com.vatebra.eirsagentpoc.flowcontroller.FlowController;
import com.vatebra.eirsagentpoc.repository.BusinessRepository;
import com.vatebra.eirsagentpoc.repository.NewBuildingRepository;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddEditBuidingActivity extends AppCompatActivity implements NewBuildingRepository.OnMessageResponse {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.add_buildingname)
    TextInputEditText add_buildingname;

    @BindView(R.id.add_tagname)
    TextInputEditText add_tagname;

    @BindView(R.id.add_streetname)
    TextInputEditText add_streetname;


    @BindView(R.id.add_town_spinner)
    Spinner add_town_spinner;

    @BindView(R.id.add_lga_spinner)
    Spinner add_lga_spinner;

    @BindView(R.id.ward_spinner)
    Spinner ward_spinner;


    @BindView(R.id.buildingtype_spinner)
    Spinner buildingtype_spinner;

    @BindView(R.id.buildingcompletion_spinner)
    Spinner buildingcompletion_spinner;

    @BindView(R.id.buildingpurpose_spinner)
    Spinner buildingpurpose_spinner;

    @BindView(R.id.buildingownership_spinner)
    Spinner buildingownership_spinner;

    @BindView(R.id.buildingoccupancy_spinner)
    Spinner buildingoccupancy_spinner;

    @BindView(R.id.buildingoccupancy_type_spinner)
    Spinner buildingoccupancy_type_spinner;

    @BindView(R.id.add_longitude)
    TextInputEditText add_longitude;

    @BindView(R.id.add_latitude)
    TextInputEditText add_latitude;


    @BindView(R.id.fab_edit_done)
    FloatingActionButton fabDone;

    String buildingRin;
    Building building;
    NewBuildingRepository buildingRepository;
    BusinessRepository businessRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_buiding);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        buildingRin = getIntent().getStringExtra(BuildingDetailsActivity.EXTRA_BUILDING_RIN);
        buildingRepository = NewBuildingRepository.getInstance();
        businessRepository = Injection.providesBusinessRepository(this);
        if (buildingRin != null) {
            building = buildingRepository.getBuilding(buildingRin);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (isNewBuilding()) {
                getSupportActionBar().setTitle("Add New Building");
            } else {
                getSupportActionBar().setTitle("Edit Building");
            }
        }
        populateFields();
        fabDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNewBuilding()) {
                    building = new Building();
                } else {
                    building.setRin(buildingRin);
                }
                building.setName(add_buildingname.getText().toString());
                building.setTagNumber(add_tagname.getText().toString());
                building.setStreetName(add_streetname.getText().toString());
                building.setBuildingNumber(add_tagname.getText().toString());
                Town town = (Town) add_town_spinner.getSelectedItem();
                if (town != null) {
                    building.setTownID(town.getID());
                }
                Lga lga = (Lga) add_lga_spinner.getSelectedItem();
                if (lga != null) {
                    building.setLGAID(lga.getID());
                }
                Ward ward = (Ward) ward_spinner.getSelectedItem();
                if (ward != null) {
                    building.setWardID(ward.getID());
                }
                BuildingType buildingType = (BuildingType) buildingtype_spinner.getSelectedItem();
                if (buildingType != null) {
                    building.setBuildingTypeID(buildingType.getID());
                }

                BuildingOwnerShip buildingOwnerShip = (BuildingOwnerShip) buildingownership_spinner.getSelectedItem();
                if (buildingOwnerShip != null) {
                    building.setBuildingOwnershipID(buildingOwnerShip.getID());
                }
                BuildingCompletion buildingCompletion = (BuildingCompletion) buildingcompletion_spinner.getSelectedItem();

                if (buildingCompletion != null) {
                    building.setBuildingCompletionID(buildingCompletion.getID());
                }

                BuildingPurpose buildingPurpose = (BuildingPurpose) buildingpurpose_spinner.getSelectedItem();
                if (buildingPurpose != null) {
                    building.setBuildingPurposeID(buildingPurpose.getID());
                }

                BuildingOccupancies buildingOccupancies = (BuildingOccupancies) buildingoccupancy_spinner.getSelectedItem();
                if (buildingOccupancies != null) {
                    building.setBuildingOccupancyID(buildingOccupancies.getID());
                }

                BuildingOccupancyType buildingOccupancyType = (BuildingOccupancyType) buildingoccupancy_type_spinner.getSelectedItem();
                if (buildingOccupancyType != null) {
                    building.setBuildingOccupancyTypeID(buildingOccupancyType.getID());
                }

                building.setLatitude(add_latitude.getText().toString());
                building.setLongitude(add_longitude.getText().toString());
                //SET BUILDING FIELDS
                SaveBuilding(building);
            }
        });
    }

    private void SaveBuilding(Building building) {
        if (isNewBuilding()) {
            buildingRepository.GetBuildingProfile(building, new BusinessRepository.OnApiReceived<AssetProfile>() {
                @Override
                public void OnSuccess(AssetProfile data) {
                    FlowController.launchProfilingActivity(AddEditBuidingActivity.this, data);
                }

                @Override
                public void OnFailed(String message) {
                    Snackbar.make(fabDone, message, Snackbar.LENGTH_LONG).show();
                }
            });
//            buildingRepository.CreateBuilding(building, this);
        } else {
            buildingRepository.UpdateBuilding(building, this);

        }
    }

    private void populateFields() {
        if (!isNewBuilding()) {
            add_buildingname.setText(building.getName());
            add_tagname.setText(building.getBuildingNumber());
            add_streetname.setText(building.getStreetName());
            add_latitude.setText(building.getLatitude());
            add_longitude.setText(building.getLongitude());
        }
        buildingRepository.getTowns(new BusinessDataSource.GetObjectCallback<Town>() {
            @Override
            public void onObjectsLoaded(List<Town> objects) {
                ArrayAdapter<Town> dataAdapter = new ArrayAdapter<>(AddEditBuidingActivity.this, android.R.layout.simple_spinner_item, objects);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                add_town_spinner.setAdapter(dataAdapter);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });

        businessRepository.getLgas(new BusinessDataSource.GetObjectCallback<Lga>() {
            @Override
            public void onObjectsLoaded(final List<Lga> objects) {
                ArrayAdapter<Lga> dataAdapter = new ArrayAdapter<>(AddEditBuidingActivity.this, android.R.layout.simple_spinner_item, objects);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                add_lga_spinner.setAdapter(dataAdapter);
                add_lga_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        buildingRepository.getWards(new BusinessDataSource.GetObjectCallback<Ward>() {
                            @Override
                            public void onObjectsLoaded(List<Ward> objects) {
                                ArrayAdapter<Ward> dataAdapter = new ArrayAdapter<>(AddEditBuidingActivity.this, android.R.layout.simple_spinner_item, objects);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                ward_spinner.setAdapter(dataAdapter);
                            }

                            @Override
                            public void onDataNotAvailable() {

                            }
                        }, objects.get(i).getID());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
//
            }

            @Override
            public void onDataNotAvailable() {

            }
        });

        buildingRepository.getBuildingTypes(new BusinessDataSource.GetObjectCallback<BuildingType>() {
            @Override
            public void onObjectsLoaded(List<BuildingType> objects) {
                ArrayAdapter<BuildingType> dataAdapter = new ArrayAdapter<>(AddEditBuidingActivity.this, android.R.layout.simple_spinner_item, objects);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                buildingtype_spinner.setAdapter(dataAdapter);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });

        buildingRepository.getBuildingCompletion(new BusinessDataSource.GetObjectCallback<BuildingCompletion>() {
            @Override
            public void onObjectsLoaded(List<BuildingCompletion> objects) {
                ArrayAdapter<BuildingCompletion> dataAdapter = new ArrayAdapter<>(AddEditBuidingActivity.this, android.R.layout.simple_spinner_item, objects);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                buildingcompletion_spinner.setAdapter(dataAdapter);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });

        buildingRepository.getBuildingPurpose(new BusinessDataSource.GetObjectCallback<BuildingPurpose>() {
            @Override
            public void onObjectsLoaded(List<BuildingPurpose> objects) {
                ArrayAdapter<BuildingPurpose> dataAdapter = new ArrayAdapter<>(AddEditBuidingActivity.this, android.R.layout.simple_spinner_item, objects);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                buildingpurpose_spinner.setAdapter(dataAdapter);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });

        buildingRepository.getBuildingOwnership(new BusinessDataSource.GetObjectCallback<BuildingOwnerShip>() {
            @Override
            public void onObjectsLoaded(List<BuildingOwnerShip> objects) {
                ArrayAdapter<BuildingOwnerShip> dataAdapter = new ArrayAdapter<>(AddEditBuidingActivity.this, android.R.layout.simple_spinner_item, objects);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                buildingownership_spinner.setAdapter(dataAdapter);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });

        buildingRepository.getBuildingOccupancy(new BusinessDataSource.GetObjectCallback<BuildingOccupancies>() {
            @Override
            public void onObjectsLoaded(List<BuildingOccupancies> objects) {
                ArrayAdapter<BuildingOccupancies> dataAdapter = new ArrayAdapter<>(AddEditBuidingActivity.this, android.R.layout.simple_spinner_item, objects);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                buildingoccupancy_spinner.setAdapter(dataAdapter);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });

        buildingRepository.getBuildingOccupancyTypes(new BusinessDataSource.GetObjectCallback<BuildingOccupancyType>() {
            @Override
            public void onObjectsLoaded(List<BuildingOccupancyType> objects) {
                ArrayAdapter<BuildingOccupancyType> dataAdapter = new ArrayAdapter<>(AddEditBuidingActivity.this, android.R.layout.simple_spinner_item, objects);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                buildingoccupancy_type_spinner.setAdapter(dataAdapter);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    private boolean isNewBuilding() {
        return buildingRin == null;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void OnSuccessMessage(String message) {
        Toast.makeText(App.getInstance(), message, Toast.LENGTH_LONG).show();
        this.finish();
    }
}
