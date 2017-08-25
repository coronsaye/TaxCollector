package com.vatebra.eirsagentpoc.taxpayers.buildings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.vatebra.eirsagentpoc.R;
import com.vatebra.eirsagentpoc.taxpayers.companies.CompanyFragment;
import com.vatebra.eirsagentpoc.util.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BuildingsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buildings);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Buildings");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        BuildingFragment buildingFragment = (BuildingFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (buildingFragment == null) {
            buildingFragment = BuildingFragment.newInstance();
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
