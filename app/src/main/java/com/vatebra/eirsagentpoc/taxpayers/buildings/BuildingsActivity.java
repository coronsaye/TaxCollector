package com.vatebra.eirsagentpoc.taxpayers.buildings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.vatebra.eirsagentpoc.R;
import com.vatebra.eirsagentpoc.domain.entity.Business;
import com.vatebra.eirsagentpoc.taxpayers.ProfilingActivity;
import com.vatebra.eirsagentpoc.taxpayers.companies.CompanyFragment;
import com.vatebra.eirsagentpoc.util.ActivityUtils;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BuildingsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    Business attachedBusiness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buildings);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Buildings");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        }
        BuildingFragment buildingFragment = (BuildingFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        Boolean ischooserMenu = getIntent().getBooleanExtra(ProfilingActivity.EXTRA_PROFILE_KEY, false);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey(ProfilingActivity.EXTRA_OBJECT_BUSINESS_KEY)) {
                attachedBusiness = Parcels.unwrap(getIntent().getExtras().getParcelable(ProfilingActivity.EXTRA_OBJECT_BUSINESS_KEY));
            }
        }

        if (buildingFragment == null) {
            getSupportActionBar().setTitle("Select Building");

            if (ischooserMenu) {
                buildingFragment = BuildingFragment.newInstance(true);
                if (attachedBusiness != null) {
                    //
                    buildingFragment = BuildingFragment.newInstance(true, attachedBusiness);
                }

            } else {
                buildingFragment = BuildingFragment.newInstance(false);
            }
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), buildingFragment, R.id.contentFrame);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
