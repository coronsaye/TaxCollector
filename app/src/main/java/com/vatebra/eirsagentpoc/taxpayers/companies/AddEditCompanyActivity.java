package com.vatebra.eirsagentpoc.taxpayers.companies;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import com.vatebra.eirsagentpoc.taxpayers.ProfilingActivity;
import com.vatebra.eirsagentpoc.taxpayers.individuals.AddEditIndividualActivity;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddEditCompanyActivity extends AppCompatActivity implements CompanyRepository.OnResponse {

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

    String companyRin;

    Company company;

    CompanyRepository companyRepository;

    IndividualRepository individualRepository;
    Business attachedBusiness;
    UseCaseHandler mUseCaseHandler;

    SaveBusiness saveBusiness;
    MaterialDialog dialogLoad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_company);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);


        companyRin = getIntent().getStringExtra(CompanyDetailActivity.EXTRA_COMPANY_RIN);
        if (getIntent().getExtras().getParcelable(ProfilingActivity.EXTRA_OBJECT_BUSINESS_KEY) != null) {
            attachedBusiness = Parcels.unwrap(getIntent().getExtras().getParcelable(ProfilingActivity.EXTRA_OBJECT_BUSINESS_KEY));
        }
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
        }

        populateFields();

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
                double longitude = Double.parseDouble(add_longitude.getText().toString());
                double latitude = Double.parseDouble(add_latitude.getText().toString());
                company.setLongitude(longitude);
                company.setLatitude(latitude);

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
        }
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
                    } else {
                        if (dialogLoad != null && dialogLoad.isShowing())
                            dialogLoad.hide();

                        Snackbar.make(fabDone, "Individual Created Successfully", Snackbar.LENGTH_LONG).show();

                    }
                }

                @Override
                public void OnFailed(String message) {
                    if (dialogLoad != null && dialogLoad.isShowing()) {
                        dialogLoad.hide();
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
