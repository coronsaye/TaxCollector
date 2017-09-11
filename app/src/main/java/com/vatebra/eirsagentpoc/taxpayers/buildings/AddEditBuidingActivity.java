package com.vatebra.eirsagentpoc.taxpayers.buildings;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.common.base.Strings;
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
import com.vatebra.eirsagentpoc.taxpayers.ProfilingActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddEditBuidingActivity extends AppCompatActivity implements NewBuildingRepository.OnMessageResponse, LocationListener {

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

    @BindView(R.id.buildingNo_Layout)
    TextInputLayout buildingNo_Layout;

    @BindView(R.id.street_Layout)
    TextInputLayout street_Layout;

    String buildingRin;
    Building building;
    NewBuildingRepository buildingRepository;
    BusinessRepository businessRepository;
    LocationManager locationManager;
    double longitudeNetwork;
    double latitudeNetwork;
    //    List<String> options = Collections.singletonList("New Business");
    List<String> options = new ArrayList<>(Arrays.asList("New Business", "Existing Business"));

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
            getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        }
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!isLocationEnabled()) {
            showAlert();
        }


        populateFields();
        checkLocation();
        fabDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNewBuilding()) {
                    building = new Building();
                } else {
                    building.setRin(buildingRin);
                }
                if (Strings.isNullOrEmpty(add_tagname.getText().toString())) {
                    buildingNo_Layout.setError("Building No is required");
                    return;
                }
                if (Strings.isNullOrEmpty(add_streetname.getText().toString())) {
                    street_Layout.setError("Street Name is Required");
                    return;
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

    private void SaveBuilding(final Building building) {
        if (isNewBuilding()) {
            // TODO: 04/09/2017 PROFILING FOR BUILDINGS REMOVED
//            buildingRepository.GetBuildingProfile(building, new BusinessRepository.OnApiReceived<AssetProfile>() {
//                @Override
//                public void OnSuccess(AssetProfile data) {
//                    FlowController.launchProfilingActivity(AddEditBuidingActivity.this, data, building);
//                }
//
//                @Override
//                public void OnFailed(String message) {
//                    Snackbar.make(fabDone, message, Snackbar.LENGTH_LONG).show();
//                }
//            });
            buildingRepository.CreateBuilding(building, new BusinessRepository.OnApiReceived<Building>() {
                @Override
                public void OnSuccess(final Building data) {
                    new MaterialDialog.Builder(AddEditBuidingActivity.this)
                            .title("Would you like to attach a business to this building")
                            .items(options)
                            .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                                @Override
                                public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                    data.setID(data.Id);
                                    /**
                                     * If you use alwaysCallSingleChoiceCallback(), which is discussed below,
                                     * returning false here won't allow the newly selected radio button to actually be selected.
                                     **/
                                    switch (which) {
                                        case 0:
                                            FlowController.launchAddEditBusinessActivity(AddEditBuidingActivity.this, data);
                                            break;
                                        case 1:
                                            FlowController.launchAddEditBusinessActivity(AddEditBuidingActivity.this, data);
                                            break;
                                    }

                                    return true;
                                }
                            })
                            .positiveText("Continue")
                            .negativeText("No")
                            .show();
                }

                @Override
                public void OnFailed(String message) {
                    Snackbar.make(fabDone, message, Snackbar.LENGTH_LONG).show();
                }
            });
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
            add_town_spinner.setVisibility(View.GONE);
            add_lga_spinner.setVisibility(View.GONE);
            ward_spinner.setVisibility(View.GONE);
            buildingtype_spinner.setVisibility(View.GONE);
            buildingcompletion_spinner.setVisibility(View.GONE);
            buildingpurpose_spinner.setVisibility(View.GONE);
            buildingownership_spinner.setVisibility(View.GONE);
            buildingoccupancy_spinner.setVisibility(View.GONE);
            buildingoccupancy_type_spinner.setVisibility(View.GONE);
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
                                if (objects != null) {
                                    ArrayAdapter<Ward> dataAdapter = new ArrayAdapter<>(AddEditBuidingActivity.this, android.R.layout.simple_spinner_item, objects);
                                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    ward_spinner.setAdapter(dataAdapter);
                                }

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


    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

    private void checkLocation() {

        Location location = getLastKnownLocation();
        if (location != null) {
            longitudeNetwork = location.getLongitude();
            latitudeNetwork = location.getLatitude();
            add_longitude.setText(longitudeNetwork + "");
            add_latitude.setText(latitudeNetwork + "");
        }

    }

    private Location getLastKnownLocation() {
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(AddEditBuidingActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(AddEditBuidingActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Ensure permission to access location has been granted.", Toast.LENGTH_SHORT).show();
            return null;
        }
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = locationManager.getLastKnownLocation(provider);
            locationManager.requestLocationUpdates(provider, 2 * 60 * 1000, 10, this);

            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    @Override
    public void onLocationChanged(Location location) {
        longitudeNetwork = location.getLongitude();
        latitudeNetwork = location.getLatitude();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                add_longitude.setText(longitudeNetwork + "");
                add_latitude.setText(latitudeNetwork + "");
            }
        });
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


}
