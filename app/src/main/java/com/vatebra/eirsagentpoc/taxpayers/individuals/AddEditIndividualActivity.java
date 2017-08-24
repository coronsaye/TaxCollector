package com.vatebra.eirsagentpoc.taxpayers.individuals;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.vatebra.eirsagentpoc.R;
import com.vatebra.eirsagentpoc.domain.entity.Business;
import com.vatebra.eirsagentpoc.domain.entity.BusinessDataSource;
import com.vatebra.eirsagentpoc.domain.entity.EconomicActivity;
import com.vatebra.eirsagentpoc.domain.entity.Individual;
import com.vatebra.eirsagentpoc.domain.entity.Lga;
import com.vatebra.eirsagentpoc.domain.entity.TaxOffice;
import com.vatebra.eirsagentpoc.repository.IndividualRepository;
import com.vatebra.eirsagentpoc.util.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.vatebra.eirsagentpoc.taxpayers.individuals.IndividualDetailActivity.EXTRA_INDIVIDUAL_RIN;

public class AddEditIndividualActivity extends AppCompatActivity {

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
    TextInputEditText add_dob;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_individual);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userRin = getIntent().getStringExtra(EXTRA_INDIVIDUAL_RIN);

        if (userRin != null) {
            individualRepository = IndividualRepository.getInstance();

            individual = individualRepository.getIndividual(userRin);
        }

        populateFields();

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
                individual.setDateOfBirth(add_dob.getText().toString());
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
                EconomicActivity economicActivity = (EconomicActivity) economic_spinner.getSelectedItem();
                if (economicActivity != null) {
                    individual.setEconomicActivity(economicActivity.getName());
                    individual.setEconomicActivityID(economicActivity.getTaxPayerTypeId());
                }


                SaveIndividual(individual);


            }
        });

    }

    private void SaveIndividual(Individual individual) {
        if (isNewIndividual()) {
            individualRepository.CreateIndividual(individual);
            //create individual
        } else {
            //update individual
            individualRepository.UpdateIndividual(individual);
        }
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

}
