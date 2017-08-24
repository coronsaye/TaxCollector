package com.vatebra.eirsagentpoc.taxpayers.companies;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.vatebra.eirsagentpoc.R;
import com.vatebra.eirsagentpoc.domain.entity.Company;
import com.vatebra.eirsagentpoc.repository.CompanyRepository;
import com.vatebra.eirsagentpoc.repository.IndividualRepository;
import com.vatebra.eirsagentpoc.taxpayers.individuals.IndividualFragment;
import com.vatebra.eirsagentpoc.util.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompanyDetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab_edit)
    FloatingActionButton fabDisplayEdit;

    @BindView(R.id.details_company_name)
    TextView details_company_name;

    @BindView(R.id.details_company_rin)
    TextView details_company_rin;

    @BindView(R.id.company_phone)
    TextView company_phone;

    @BindView(R.id.details_company_phone_two)
    TextView details_company_phone_two;

    @BindView(R.id.details_company_email)
    TextView details_company_email;

    @BindView(R.id.details_company_email_two)
    TextView details_company_email_two;

    @BindView(R.id.details_company_taxoffice)
    TextView details_company_taxoffice;

    @BindView(R.id.details_company_tin)
    TextView details_company_tin;

    @BindView(R.id.details_company_status)
    TextView details_company_status;

    @BindView(R.id.details_company_tax_notify)
    TextView details_company_tax_notify;

    @BindView(R.id.details_company_economic)
    TextView details_company_economic;

    public static final String EXTRA_COMPANY_RIN = "COMPANY_RIN";
    CompanyRepository companyRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Manage Company");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        String companyRin = getIntent().getStringExtra(EXTRA_COMPANY_RIN);

        if (companyRin != null) {
            companyRepository = CompanyRepository.getInstance();
            populateFields(companyRin);
            fabDisplayEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //display edit view
                }
            });
        }
    }

    private void populateFields(String companyRin) {
        Company company = companyRepository.getCompany(companyRin);
        details_company_name.setText(company.getName());
        details_company_rin.setText("#" + company.getRin());
        company_phone.setText(company.getMobileNo());
        details_company_phone_two.setText(company.getPhoneNo());
        details_company_email.setText(company.getEmailAddress());
        details_company_email_two.setText(company.getEmailAddressTwo());
        details_company_taxoffice.setText(company.getTaxOffice());
        details_company_tin.setText(company.getTin());
        details_company_status.setText(company.getTaxPayerStatus());
        details_company_tax_notify.setText(company.getPreferredNotificationMethod());
        details_company_economic.setText(company.getEconomicActivity());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
