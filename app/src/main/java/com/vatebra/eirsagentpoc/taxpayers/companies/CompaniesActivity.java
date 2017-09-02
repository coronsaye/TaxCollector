package com.vatebra.eirsagentpoc.taxpayers.companies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.vatebra.eirsagentpoc.R;
import com.vatebra.eirsagentpoc.building.domain.entity.Building;
import com.vatebra.eirsagentpoc.domain.entity.Business;
import com.vatebra.eirsagentpoc.taxpayers.ProfilingActivity;
import com.vatebra.eirsagentpoc.util.ActivityUtils;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompaniesActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    Business business;
    Building building;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companies);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Companies");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        }
        CompanyFragment companyFragment = (CompanyFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        Boolean ischooserMenu = getIntent().getBooleanExtra(ProfilingActivity.EXTRA_PROFILE_KEY, false);
        if (companyFragment == null) {
            if (ischooserMenu) {
                getSupportActionBar().setTitle("Select Tax Paying Company ");
                if (getIntent().getExtras().getParcelable(ProfilingActivity.EXTRA_OBJECT_BUSINESS_KEY) != null) {
                    business = Parcels.unwrap(getIntent().getExtras().getParcelable(ProfilingActivity.EXTRA_OBJECT_BUSINESS_KEY));
                    companyFragment = CompanyFragment.newInstance(true, business);
                }
                if (getIntent().getExtras().getParcelable(ProfilingActivity.EXTRA_OBJECT_BUILDING_KEY) != null) {
                    building = Parcels.unwrap(getIntent().getExtras().getParcelable(ProfilingActivity.EXTRA_OBJECT_BUILDING_KEY));
                    companyFragment = CompanyFragment.newInstance(true, building);
                }
            } else {
                companyFragment = CompanyFragment.newInstance(false);
            }

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), companyFragment, R.id.contentFrame);
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
