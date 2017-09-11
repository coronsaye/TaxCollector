package com.vatebra.eirsagentpoc.taxpayers.companies;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.vatebra.eirsagentpoc.App;
import com.vatebra.eirsagentpoc.Injection;
import com.vatebra.eirsagentpoc.R;
import com.vatebra.eirsagentpoc.UseCase;
import com.vatebra.eirsagentpoc.UseCaseHandler;
import com.vatebra.eirsagentpoc.building.domain.entity.Building;
import com.vatebra.eirsagentpoc.business.businesses.usecase.SaveBusiness;
import com.vatebra.eirsagentpoc.domain.entity.Business;
import com.vatebra.eirsagentpoc.domain.entity.BusinessDataSource;
import com.vatebra.eirsagentpoc.domain.entity.Company;
import com.vatebra.eirsagentpoc.domain.entity.EconomicActivity;
import com.vatebra.eirsagentpoc.domain.entity.TaxOffice;
import com.vatebra.eirsagentpoc.flowcontroller.FlowController;
import com.vatebra.eirsagentpoc.repository.BusinessRepository;
import com.vatebra.eirsagentpoc.repository.CompanyRepository;
import com.vatebra.eirsagentpoc.repository.IndividualRepository;
import com.vatebra.eirsagentpoc.repository.NewBuildingRepository;
import com.vatebra.eirsagentpoc.taxpayers.ProfilingActivity;
import com.vatebra.eirsagentpoc.taxpayers.buildings.AddEditBuidingActivity;
import com.vatebra.eirsagentpoc.taxpayers.individuals.AddEditIndividualActivity;
import com.vatebra.eirsagentpoc.util.Constants;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddEditCompanyActivity extends AppCompatActivity implements CompanyRepository.OnResponse, LocationListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.add_companyname)
    TextInputEditText add_companyname;

    @BindView(R.id.add_mobile)
    TextInputEditText add_mobile;

    @BindView(R.id.add_mobile_two)
    TextInputEditText add_mobile_two;

    @BindView(R.id.add_email)
    TextInputEditText add_email;

    @BindView(R.id.email_two)
    TextInputEditText email_two;

    @BindView(R.id.tax_office_spinner)
    Spinner tax_office_spinner;

    @BindView(R.id.economic_spinner)
    Spinner economic_spinner;

    @BindView(R.id.add_longitude)
    TextInputEditText add_longitude;

    @BindView(R.id.add_latitude)
    TextInputEditText add_latitude;

    @BindView(R.id.fab_edit_done)
    FloatingActionButton fabDone;

    @BindView(R.id.notification_spinner)
    Spinner notification_spinner;

    String companyRin;

    Company company;

    CompanyRepository companyRepository;

    IndividualRepository individualRepository;
    Business attachedBusiness;
    UseCaseHandler mUseCaseHandler;

    SaveBusiness saveBusiness;
    MaterialDialog dialogLoad;
    Building attachedBuilding;
    NewBuildingRepository newBuildingRepository;
    LocationManager locationManager;
    double longitudeNetwork;
    double latitudeNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_company);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);


        companyRin = getIntent().getStringExtra(CompanyDetailActivity.EXTRA_COMPANY_RIN);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey(ProfilingActivity.EXTRA_OBJECT_BUSINESS_KEY)) {
                attachedBusiness = Parcels.unwrap(getIntent().getExtras().getParcelable(ProfilingActivity.EXTRA_OBJECT_BUSINESS_KEY));
            } else if (extras.containsKey(ProfilingActivity.EXTRA_OBJECT_BUILDING_KEY)) {
                attachedBuilding = Parcels.unwrap(getIntent().getExtras().getParcelable(ProfilingActivity.EXTRA_OBJECT_BUILDING_KEY));

            }
        }

        newBuildingRepository = NewBuildingRepository.getInstance();
        mUseCaseHandler = Injection.provideUseCaseHandler();
        saveBusiness = Injection.provideSaveBusiness(AddEditCompanyActivity.this);
        individualRepository = IndividualRepository.getInstance();
        companyRepository = CompanyRepository.getInstance();
        dialogLoad = new MaterialDialog.Builder(this)
                .title("Loading")
                .content("Please Wait...")
                .progress(true, 0)
                .progressIndeterminateStyle(true).build();

        if (companyRin != null) {

            company = companyRepository.getCompany(companyRin);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (isNewCompany()) {
                getSupportActionBar().setTitle("Add New Company");
            } else {
                getSupportActionBar().setTitle("Edit Company");
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
                String phonePretext = "234";
                if (isNewCompany()) {
                    company = new Company();
                }

                if (!add_mobile.getText().toString().startsWith(phonePretext)) {
                    add_mobile.setError("Ensure Phone Number Starts with 234");
                    return;
                }
                company.setName(add_companyname.getText().toString());
                company.setMobileNo(add_mobile.getText().toString());
                company.setPhoneNo(add_mobile_two.getText().toString());
                company.setEmailAddress(add_email.getText().toString());
                company.setEmailAddressTwo(email_two.getText().toString());


                TaxOffice taxOffice = (TaxOffice) tax_office_spinner.getSelectedItem();
                if (taxOffice != null) {
                    company.setTaxOffice(String.valueOf(taxOffice.getId()));

                    company.setTaxOfficeID(taxOffice.getId());
                }

                EconomicActivity economicActivity = (EconomicActivity) economic_spinner.getSelectedItem();
                if (economicActivity != null) {
                    company.setEconomicActivity(economicActivity.getName());
                    company.setEconomicActivityID(economicActivity.getId());
                }
                try {
                    double longitude = Double.parseDouble(add_longitude.getText().toString());
                    double latitude = Double.parseDouble(add_latitude.getText().toString());
                    company.setLongitude(longitude);
                    company.setLatitude(latitude);
                } catch (Exception ex) {

                }


                SaveCompany(company);
            }
        });
    }

    private boolean isNewCompany() {
        return companyRin == null;
    }

    private void populateFields() {
        if (!isNewCompany()) {
            add_companyname.setText(company.getName());
            add_mobile.setText(company.getMobileNo());
            add_mobile_two.setText(company.getPhoneNo());
            add_email.setText(company.getEmailAddress());
            email_two.setText(company.getEmailAddressTwo());
            String longitude = String.valueOf(company.getLongitude());
            String latitude = String.valueOf(company.getLatitude());
            add_longitude.setText(longitude);
            add_latitude.setText(latitude);
        } else {
            //is a new company
            if (attachedBusiness != null) {
                add_companyname.setText(attachedBusiness.getName());
            }
        }

        ArrayAdapter<Constants.NotificationMethod> notificationMethodArrayAdapter = new ArrayAdapter<>(AddEditCompanyActivity.this, android.R.layout.simple_spinner_item, Constants.NotificationMethod.values());
        notificationMethodArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        notification_spinner.setAdapter(notificationMethodArrayAdapter);

        individualRepository.getTaxOffice(new BusinessDataSource.GetObjectCallback<TaxOffice>() {
            @Override
            public void onObjectsLoaded(List<TaxOffice> objects) {
                ArrayAdapter<TaxOffice> dataAdapter = new ArrayAdapter<>(AddEditCompanyActivity.this, android.R.layout.simple_spinner_item, objects);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                tax_office_spinner.setAdapter(dataAdapter);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });

        individualRepository.getEconomicActivities(new BusinessDataSource.GetObjectCallback<EconomicActivity>() {
            @Override
            public void onObjectsLoaded(List<EconomicActivity> objects) {
                ArrayAdapter<EconomicActivity> dataAdapter = new ArrayAdapter<>(AddEditCompanyActivity.this, android.R.layout.simple_spinner_item, objects);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                economic_spinner.setAdapter(dataAdapter);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    private void SaveCompany(Company company) {

        if (isNewCompany()) {
            if (dialogLoad != null && !dialogLoad.isShowing()) {
                dialogLoad.show();
            }

            companyRepository.CreateCompany(company, new BusinessRepository.OnApiReceived<Company>() {
                @Override
                public void OnSuccess(Company data) {
                    if (attachedBusiness != null) {
                        attachBusinessToCompany(data.getId());
                    } else if (attachedBuilding != null) {

                        attachBuildingToCompany(data.getId());

                    } else {
                        if (dialogLoad != null && dialogLoad.isShowing())
                            dialogLoad.dismiss();

                        Snackbar.make(fabDone, "Individual Created Successfully", Snackbar.LENGTH_LONG).show();

                    }
                }

                @Override
                public void OnFailed(String message) {
                    if (dialogLoad != null && dialogLoad.isShowing()) {
                        dialogLoad.dismiss();
                    }
                    Snackbar.make(fabDone, message, Snackbar.LENGTH_LONG).show();
                }
            });
            //create company
        } else {
            companyRepository.UpdateCompany(company, this);
            //update company
        }
    }

    private void attachBuildingToCompany(int companyId) {
        if (attachedBuilding == null)
            return;

        attachedBuilding.setCompanyID(companyId);
        if (dialogLoad != null && dialogLoad.isShowing()) {
            dialogLoad.hide();
        }
        newBuildingRepository.CreateBuilding(attachedBuilding, new BusinessRepository.OnApiReceived<Building>() {
            @Override
            public void OnSuccess(Building data) {
                Snackbar.make(fabDone, "Building Profiling Complete", Snackbar.LENGTH_INDEFINITE).setAction("Complete", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                        FlowController.launchDashboardctivity(AddEditCompanyActivity.this);
                    }
                }).show();
            }

            @Override
            public void OnFailed(String message) {
                Snackbar.make(fabDone, message, Snackbar.LENGTH_LONG).show();
            }
        });

    }

    private void attachBusinessToCompany(int companyId) {
        if (attachedBusiness == null)
            return;

        attachedBusiness.setCompanyID(companyId);
        mUseCaseHandler.execute(saveBusiness, new SaveBusiness.RequestValues(attachedBusiness), new UseCase.UseCaseCallback<SaveBusiness.ResponseValue>() {
            @Override
            public void onSuccess(SaveBusiness.ResponseValue response) {

                if (dialogLoad != null && dialogLoad.isShowing()) {
                    dialogLoad.hide();
                }

                Snackbar.make(fabDone, "Business Profiling Complete", Snackbar.LENGTH_INDEFINITE).setAction("Complete", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                        FlowController.launchDashboardctivity(AddEditCompanyActivity.this);
                    }
                }).show();
            }

            @Override
            public void onError() {
                if (dialogLoad != null && dialogLoad.isShowing()) {
                    dialogLoad.hide();
                }
                Snackbar.make(fabDone, "Business Profiling Failed", Snackbar.LENGTH_LONG).show();

            }
        });

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
                ContextCompat.checkSelfPermission(AddEditCompanyActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(AddEditCompanyActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void OnSuccessMessage(String message) {
        Toast.makeText(App.getInstance(), message, Toast.LENGTH_LONG).show();
        this.finish();
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
