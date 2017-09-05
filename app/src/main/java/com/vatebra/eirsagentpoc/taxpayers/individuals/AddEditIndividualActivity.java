package com.vatebra.eirsagentpoc.taxpayers.individuals;

import android.app.DatePickerDialog;
import android.location.LocationManager;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.vatebra.eirsagentpoc.domain.entity.EconomicActivity;
import com.vatebra.eirsagentpoc.domain.entity.Individual;
import com.vatebra.eirsagentpoc.domain.entity.Lga;
import com.vatebra.eirsagentpoc.domain.entity.TaxOffice;
import com.vatebra.eirsagentpoc.flowcontroller.FlowController;
import com.vatebra.eirsagentpoc.repository.BusinessRepository;
import com.vatebra.eirsagentpoc.repository.IndividualRepository;
import com.vatebra.eirsagentpoc.repository.NewBuildingRepository;
import com.vatebra.eirsagentpoc.taxpayers.ProfilingActivity;
import com.vatebra.eirsagentpoc.util.Constants;

import org.parceler.Parcels;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.vatebra.eirsagentpoc.taxpayers.individuals.IndividualDetailActivity.EXTRA_INDIVIDUAL_RIN;

public class AddEditIndividualActivity extends AppCompatActivity implements IndividualRepository.OnApiResponse, DatePickerDialog.OnDateSetListener {

    public static final int REQUEST_ADD_INDIVIDUAL = 2;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.add_firstname)
    TextInputEditText add_firstname;

    @BindView(R.id.add_middlename)
    TextInputEditText add_middlename;

    @BindView(R.id.add_lastname)
    TextInputEditText add_lastname;

    @BindView(R.id.add_dob)
    TextView add_dob;

    @BindView(R.id.add_mobile)
    TextInputEditText add_mobile;

    @BindView(R.id.add_mobile_two)
    TextInputEditText add_mobile_two;

    @BindView(R.id.add_email)
    TextInputEditText add_email;

    @BindView(R.id.email_two)
    TextInputEditText email_two;

    @BindView(R.id.bio_details)
    TextInputEditText bio_details;

    @BindView(R.id.gender_spinner)
    Spinner gender_spinner;

    @BindView(R.id.tax_office_spinner)
    Spinner tax_office_spinner;

    @BindView(R.id.marital_spinner)
    Spinner marital_spinner;

    @BindView(R.id.economic_spinner)
    Spinner economic_spinner;

    @BindView(R.id.fab_edit_done)
    FloatingActionButton fabDone;

    String userRin;

    Individual individual;

    IndividualRepository individualRepository;

    Business attachedBusiness;

    UseCaseHandler mUseCaseHandler;

    SaveBusiness saveBusiness;
    MaterialDialog dialogLoad;

    Building attachedBuilding;
    NewBuildingRepository newBuildingRepository;
    DatePickerDialog datePickerDialog;
    private int year, month, day;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_individual);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        userRin = getIntent().getStringExtra(EXTRA_INDIVIDUAL_RIN);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey(ProfilingActivity.EXTRA_OBJECT_BUSINESS_KEY)) {
                attachedBusiness = Parcels.unwrap(getIntent().getExtras().getParcelable(ProfilingActivity.EXTRA_OBJECT_BUSINESS_KEY));
            } else if (extras.containsKey(ProfilingActivity.EXTRA_OBJECT_BUILDING_KEY)) {
                attachedBuilding = Parcels.unwrap(getIntent().getExtras().getParcelable(ProfilingActivity.EXTRA_OBJECT_BUILDING_KEY));

            }
        }

        newBuildingRepository = NewBuildingRepository.getInstance();
        individualRepository = IndividualRepository.getInstance();
        mUseCaseHandler = Injection.provideUseCaseHandler();
        saveBusiness = Injection.provideSaveBusiness(AddEditIndividualActivity.this);
        if (userRin != null) {

            individual = individualRepository.getIndividual(userRin);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (isNewIndividual()) {
                getSupportActionBar().setTitle("Add New Individual");
            } else {
                getSupportActionBar().setTitle("Edit Individual");
            }
            getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        }

        dialogLoad = new MaterialDialog.Builder(this)
                .title("Loading")
                .content("Please Wait...")
                .progress(true, 0)
                .progressIndeterminateStyle(true).build();

        populateFields();
//        datePickerDialog = new DatePickerDialog(
//                context, AddEditIndividualActivity.this, startYear, starthMonth, startDay);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, this, year, month, day);

        add_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });
        fabDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phonePretext = "234";
                if (isNewIndividual()) {
                    individual = new Individual();
                }

                if (!add_mobile.getText().toString().startsWith(phonePretext)) {
                    add_mobile.setError("Ensure Phone Number Starts with 234");
                    return;
                } else if (TextUtils.isEmpty(add_firstname.getText().toString())) {
                    add_firstname.setError("Ensure First Name is filled");
                    return;
                } else if (TextUtils.isEmpty(add_lastname.getText().toString())) {
                    add_lastname.setError("Ensure Last Name is filled");
                    return;
                } else if (TextUtils.isEmpty(add_email.getText().toString())) {
                    add_email.setError("Ensure Email is filled");
                    return;
                }
                individual.setFirstName(add_firstname.getText().toString());
                individual.setMiddleName(add_middlename.getText().toString());
                individual.setLastName(add_lastname.getText().toString());
//                individual.setDateOfBirth(add_dob.getText().toString());
                individual.setDateOfBirth(calendar.getTime().toString());
                individual.setMobileNumberOne(add_mobile.getText().toString());
                individual.setMobileNumberTwo(add_mobile_two.getText().toString());
                individual.setEmailAddressOne(add_email.getText().toString());
                individual.setEmailAddresTwo(email_two.getText().toString());
                individual.setBiometricDetails(bio_details.getText().toString());


                TaxOffice taxOffice = (TaxOffice) tax_office_spinner.getSelectedItem();
                if (taxOffice != null) {
                    individual.setTaxOffice(taxOffice.getName());
                    individual.setTaxOfficeID(taxOffice.getId());
                }
                Constants.MaritalStatus status = (Constants.MaritalStatus) marital_spinner.getSelectedItem();
                if (status != null) {
                    individual.setMaritalStatus(status.name());
                    // TODO: 24/08/2017 SET MARITAL STATUS ID
                }

                Constants.EnumGender gender = (Constants.EnumGender) gender_spinner.getSelectedItem();
                if (gender != null) {
                    individual.setGender(gender.name());
                }
                EconomicActivity economicActivity = (EconomicActivity) economic_spinner.getSelectedItem();
                if (economicActivity != null) {
                    individual.setEconomicActivity(economicActivity.getName());
                    individual.setEconomicActivityID(economicActivity.getId());
                }


                SaveIndividual(individual);


            }
        });

    }

    private void SaveIndividual(Individual individual) {


        if (isNewIndividual()) {
            if (dialogLoad != null && !dialogLoad.isShowing()) {
                dialogLoad.show();
            }
            individualRepository.CreateIndividual(individual, new BusinessRepository.OnApiReceived<Individual>() {
                @Override
                public void OnSuccess(Individual data) {

                    if (attachedBusiness != null) {
                        attachBusinessToIndividual(data.getId());
                    } else if (attachedBuilding != null) {
                        attachBuildingToIndividual(data.getId());
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
            //create individual
        } else {
            //update individual
            individualRepository.UpdateIndividual(individual, this);
        }
    }

    private void attachBuildingToIndividual(int individualId) {
        if (attachedBuilding == null)
            return;

        attachedBuilding.setIndividualID(individualId);
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
                        FlowController.launchDashboardctivity(AddEditIndividualActivity.this);
                    }
                }).show();
            }

            @Override
            public void OnFailed(String message) {
                Snackbar.make(fabDone, message, Snackbar.LENGTH_LONG).show();
            }
        });

    }

    private void attachBusinessToIndividual(int individualId) {
        if (attachedBusiness == null)
            return;

        attachedBusiness.setIndividualID(individualId);
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
                        FlowController.launchDashboardctivity(AddEditIndividualActivity.this);
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


    private boolean isNewIndividual() {
        return userRin == null;
    }

    private void populateFields() {

        if (!isNewIndividual()) {
            add_firstname.setText(individual.getFirstName());
            add_middlename.setText(individual.getMiddleName());
            add_lastname.setText(individual.getLastName());
            add_dob.setText(individual.getDateOfBirth());
            add_mobile.setText(individual.getMobileNumberOne());
            add_mobile_two.setText(individual.getMobileNumberTwo());
            add_email.setText(individual.getEmailAddressOne());
            email_two.setText(individual.getEmailAddresTwo());
            bio_details.setText(individual.getBiometricDetails());
        }

        individualRepository.getTaxOffice(new BusinessDataSource.GetObjectCallback<TaxOffice>() {
            @Override
            public void onObjectsLoaded(List<TaxOffice> objects) {
                ArrayAdapter<TaxOffice> dataAdapter = new ArrayAdapter<>(AddEditIndividualActivity.this, android.R.layout.simple_spinner_item, objects);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                tax_office_spinner.setAdapter(dataAdapter);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });

        ArrayAdapter<Constants.MaritalStatus> dataAdapter = new ArrayAdapter<>(AddEditIndividualActivity.this, android.R.layout.simple_spinner_item, Constants.MaritalStatus.values());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        marital_spinner.setAdapter(dataAdapter);

        ArrayAdapter<Constants.EnumGender> genderAdapter = new ArrayAdapter<>(AddEditIndividualActivity.this, android.R.layout.simple_spinner_item, Constants.EnumGender.values());
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender_spinner.setAdapter(genderAdapter);


        individualRepository.getEconomicActivities(new BusinessDataSource.GetObjectCallback<EconomicActivity>() {
            @Override
            public void onObjectsLoaded(List<EconomicActivity> objects) {
                ArrayAdapter<EconomicActivity> dataAdapter = new ArrayAdapter<>(AddEditIndividualActivity.this, android.R.layout.simple_spinner_item, objects);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                economic_spinner.setAdapter(dataAdapter);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
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
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateLabel();
    }

    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        add_dob.setText(sdf.format(calendar.getTime()));
    }
}
