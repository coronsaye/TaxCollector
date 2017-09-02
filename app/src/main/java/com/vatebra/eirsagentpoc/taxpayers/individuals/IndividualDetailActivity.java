package com.vatebra.eirsagentpoc.taxpayers.individuals;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.vatebra.eirsagentpoc.R;
import com.vatebra.eirsagentpoc.domain.entity.Individual;
import com.vatebra.eirsagentpoc.flowcontroller.FlowController;
import com.vatebra.eirsagentpoc.repository.IndividualRepository;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IndividualDetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.details_Individual_name)
    TextView individualNameTextView;
    @BindView(R.id.details_Individual_rin)
    TextView individualRinTextView;
    @BindView(R.id.Individual_phone)
    TextView individualPhoneTextView;

    @BindView(R.id.details_individual_email)
    TextView details_individual_email;
    @BindView(R.id.details_individual_gender)
    TextView details_individual_gender;
    @BindView(R.id.details_individual_dob)
    TextView details_individual_dob;
    @BindView(R.id.details_individual_taxoffice)
    TextView details_individual_taxoffice;

    @BindView(R.id.details_individual_tin)
    TextView details_individual_tin;
    @BindView(R.id.details_individual_status)
    TextView details_individual_status;
    @BindView(R.id.details_individual_tax_notify)
    TextView details_individual_tax_notify;
    @BindView(R.id.details_individual_marital)
    TextView details_individual_marital;
    @BindView(R.id.details_individual_nationality)
    TextView details_individual_nationality;

    @BindView(R.id.fab_edit)
    FloatingActionButton fabDisplayEdit;

    public static final String EXTRA_INDIVIDUAL_RIN = "INDIVIDUAL_RIN";
    IndividualRepository individualRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Manage Individual");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        }
        final String individualRin = getIntent().getStringExtra(EXTRA_INDIVIDUAL_RIN);

        if (individualRin != null) {
            individualRepository = IndividualRepository.getInstance();
            populateFields(individualRin);
            fabDisplayEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //display edit view
                    FlowController.launchAddEditIndividualActivity(IndividualDetailActivity.this, individualRin);
                }
            });
        }

    }

    private void populateFields(String rin) {
        //populate fields
        Individual individual = individualRepository.getIndividual(rin);
        individualNameTextView.setText(individual.getFullName());
        individualRinTextView.setText("#" + individual.getUserRin());
        if(individual.getMobileNumberOne().equals("none")){
            individualPhoneTextView.setText(individual.getMobileNumberTwo());
        }
        else {
            individualPhoneTextView.setText(individual.getMobileNumberOne());
        }

        details_individual_email.setText(individual.getEmailAddressOne());
        details_individual_gender.setText(individual.getGender());
        details_individual_dob.setText(individual.getDateOfBirth());
        details_individual_taxoffice.setText(individual.getTaxOffice());
        details_individual_tin.setText(individual.getTin());
        details_individual_status.setText(individual.getTaxPayerStatus());
        details_individual_tax_notify.setText(individual.getPreferredNotificationMethod());
        details_individual_marital.setText(individual.getMaritalStatus());
        details_individual_nationality.setText(individual.getNationality());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
