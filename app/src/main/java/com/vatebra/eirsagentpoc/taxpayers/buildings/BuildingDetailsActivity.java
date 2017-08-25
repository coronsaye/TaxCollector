package com.vatebra.eirsagentpoc.taxpayers.buildings;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.vatebra.eirsagentpoc.R;
import com.vatebra.eirsagentpoc.building.domain.entity.Building;
import com.vatebra.eirsagentpoc.flowcontroller.FlowController;
import com.vatebra.eirsagentpoc.repository.NewBuildingRepository;
import com.vatebra.eirsagentpoc.taxpayers.companies.CompanyDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BuildingDetailsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.details_building_name)
    TextView details_building_name;

    @BindView(R.id.details_building_rin)
    TextView details_building_rin;

    @BindView(R.id.building_street)
    TextView building_street;

    @BindView(R.id.building_town)
    TextView building_town;

    @BindView(R.id.building_lga)
    TextView building_lga;

    @BindView(R.id.building_ward)
    TextView building_ward;

    @BindView(R.id.building_assettype)
    TextView building_assettype;

    @BindView(R.id.building_type)
    TextView building_type;

    @BindView(R.id.building_completion)
    TextView building_completion;

    @BindView(R.id.building_purpose)
    TextView building_purpose;

    @BindView(R.id.building_ownership)
    TextView building_ownership;

    @BindView(R.id.building_occupancy)
    TextView building_occupancy;

    @BindView(R.id.building_occupancy_type)
    TextView building_occupancy_type;

    @BindView(R.id.building_latitude)
    TextView building_latitude;

    @BindView(R.id.building_longitude)
    TextView building_longitude;
    @BindView(R.id.fab_edit)
    FloatingActionButton fabDisplayEdit;

    public static final String EXTRA_BUILDING_RIN = "BUILDING_RIN";
    NewBuildingRepository newBuildingRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Manage Building");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        final String buildingRin = getIntent().getStringExtra(EXTRA_BUILDING_RIN);
        if(buildingRin != null){
            newBuildingRepository = NewBuildingRepository.getInstance();
            populateFields(buildingRin);
            fabDisplayEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //display edit view
                    FlowController.launchAddEditBuildingActivity(BuildingDetailsActivity.this, buildingRin);

                }
            });
        }
    }


    private void populateFields(String buildingRin){
        Building building = newBuildingRepository.getBuilding(buildingRin);
        details_building_name.setText(building.getName());
        details_building_rin.setText("#" + building.getRin());
        building_street.setText(building.getStreetName());
        building_town.setText(building.getTown());
        building_lga.setText(building.getLga());
        building_ward.setText(building.getWard());
        building_assettype.setText(building.getAssetType());
        building_type.setText(building.getBuildingType());
        building_completion.setText(building.getBuildingCompletion());
        building_purpose.setText(building.getBuildingPurpose());
        building_ownership.setText(building.getBuildingOwnership());
        building_occupancy.setText(building.getBuildingOccupancy());
        building_occupancy_type.setText(building.getBuildingOccupancyType());
        building_latitude.setText(building.getLatitude());
        building_longitude.setText(building.getLongitude());


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
