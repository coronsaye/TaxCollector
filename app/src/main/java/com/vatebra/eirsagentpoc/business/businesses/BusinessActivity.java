package com.vatebra.eirsagentpoc.business.businesses;

import android.app.SearchManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.vatebra.eirsagentpoc.Injection;
import com.vatebra.eirsagentpoc.R;
import com.vatebra.eirsagentpoc.building.domain.entity.Building;
import com.vatebra.eirsagentpoc.taxpayers.ProfilingActivity;
import com.vatebra.eirsagentpoc.util.ActivityUtils;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BusinessActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    Building attachedBuilding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Businesses");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        }
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey(ProfilingActivity.EXTRA_OBJECT_BUSINESS_KEY)) {
                attachedBuilding = Parcels.unwrap(getIntent().getExtras().getParcelable(ProfilingActivity.EXTRA_OBJECT_BUILDING_KEY));
            }
        }
        BusinessFragment businessFragment = (BusinessFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (businessFragment == null) {
            if (attachedBuilding != null) {
                if (getSupportActionBar() != null)
                    getSupportActionBar().setTitle("Attach a business");

                businessFragment = BusinessFragment.newInstance(true, attachedBuilding);
            } else {
                businessFragment = BusinessFragment.newInstance();
            }
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), businessFragment, R.id.contentFrame);
        }

        //create the presenter
        new BusinessPresenter(Injection.provideUseCaseHandler(), businessFragment, Injection.provideGetBusinesses(this));

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
