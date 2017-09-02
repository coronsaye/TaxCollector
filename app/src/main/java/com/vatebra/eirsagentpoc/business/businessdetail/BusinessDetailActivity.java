package com.vatebra.eirsagentpoc.business.businessdetail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.vatebra.eirsagentpoc.Injection;
import com.vatebra.eirsagentpoc.R;
import com.vatebra.eirsagentpoc.business.addeditbusinesses.AddEditBusinessFragment;
import com.vatebra.eirsagentpoc.util.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BusinessDetailActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    public static final String EXTRA_BUSINESS_RIN = "BUSINESS_RIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Manage Business");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        }


        String businessRin = getIntent().getStringExtra(EXTRA_BUSINESS_RIN);

        BusinessDetailFragment businessDetailFragment = (BusinessDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (businessDetailFragment == null) {
            businessDetailFragment = BusinessDetailFragment.newInstance(businessRin);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    businessDetailFragment, R.id.contentFrame);
        }


        new BusinessDetailPresenter(Injection.provideUseCaseHandler(), businessDetailFragment, Injection.provideGetBusiness(this), businessRin);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
