package com.vatebra.eirsagentpoc.taxpayers.individuals;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.vatebra.eirsagentpoc.R;
import com.vatebra.eirsagentpoc.building.domain.entity.Building;
import com.vatebra.eirsagentpoc.business.businesses.BusinessFragment;
import com.vatebra.eirsagentpoc.domain.entity.Business;
import com.vatebra.eirsagentpoc.taxpayers.ProfilingActivity;
import com.vatebra.eirsagentpoc.taxpayers.companies.CompanyFragment;
import com.vatebra.eirsagentpoc.util.ActivityUtils;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IndividualsActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    Business business;
    Building building;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individuals);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Individuals");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        }
        Boolean ischooserMenu = getIntent().getBooleanExtra(ProfilingActivity.EXTRA_PROFILE_KEY, false);
        if (ischooserMenu) {
            if (getIntent().getExtras().getParcelable(ProfilingActivity.EXTRA_OBJECT_BUSINESS_KEY) != null) {
                business = Parcels.unwrap(getIntent().getExtras().getParcelable(ProfilingActivity.EXTRA_OBJECT_BUSINESS_KEY));
            }
            if (getIntent().getExtras().getParcelable(ProfilingActivity.EXTRA_OBJECT_BUILDING_KEY) != null) {
                building = Parcels.unwrap(getIntent().getExtras().getParcelable(ProfilingActivity.EXTRA_OBJECT_BUILDING_KEY));
            }
        }

        IndividualFragment individualFragment = (IndividualFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (individualFragment == null) {
            if (ischooserMenu) {
                getSupportActionBar().setTitle("Select Tax Payer");
                if (business != null)
                    individualFragment = IndividualFragment.newInstance(true, business);
                else
                    individualFragment = IndividualFragment.newInstance(true, building);

            } else {
                individualFragment = IndividualFragment.newInstance(false);

            }
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), individualFragment, R.id.contentFrame);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
