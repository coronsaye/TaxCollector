package com.vatebra.eirsagentpoc.business.addeditbusinesses;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.vatebra.eirsagentpoc.Injection;
import com.vatebra.eirsagentpoc.R;
import com.vatebra.eirsagentpoc.building.domain.entity.Building;
import com.vatebra.eirsagentpoc.taxpayers.ProfilingActivity;
import com.vatebra.eirsagentpoc.util.ActivityUtils;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddEditBusinessActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_BUSINESS = 1;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
//    Building attachedBuilding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_business);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        }

        AddEditBusinessFragment addEditBusinessFragment = (AddEditBusinessFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);


        String businessRin = getIntent().getStringExtra(AddEditBusinessFragment.ARGUMENT_EDIT_BUSINESS_ID);

        if (addEditBusinessFragment == null) {
            addEditBusinessFragment = AddEditBusinessFragment.newInstance();
            Bundle extras = getIntent().getExtras();
            if (extras != null && extras.containsKey(ProfilingActivity.EXTRA_OBJECT_BUILDING_KEY)) {
                if (getSupportActionBar() != null)
                    getSupportActionBar().setTitle("Add new business to building");
                Bundle bundle = new Bundle();
                if (extras.containsKey(ProfilingActivity.EXTRA_OBJECT_BUSINESS_KEY)) {
                    bundle.putParcelable(AddEditBusinessFragment.ARGUMENT_ATTACHED_BUSINESS, getIntent().getExtras().getParcelable(ProfilingActivity.EXTRA_OBJECT_BUSINESS_KEY));
                }
                bundle.putParcelable(AddEditBusinessFragment.ARGUMENT_ATTACHED_BUILDING, getIntent().getExtras().getParcelable(ProfilingActivity.EXTRA_OBJECT_BUILDING_KEY));
                addEditBusinessFragment.setArguments(bundle);

//                attachedBuilding = Parcels.unwrap(getIntent().getExtras().getParcelable(ProfilingActivity.EXTRA_OBJECT_BUILDING_KEY));

            } else if (getIntent().hasExtra(AddEditBusinessFragment.ARGUMENT_EDIT_BUSINESS_ID)) {
                if (getSupportActionBar() != null)
                    getSupportActionBar().setTitle(R.string.edit_business_title);
                Bundle bundle = new Bundle();
                bundle.putString(AddEditBusinessFragment.ARGUMENT_EDIT_BUSINESS_ID, businessRin);
                addEditBusinessFragment.setArguments(bundle);
            } else {
                if (getSupportActionBar() != null)
                    getSupportActionBar().setTitle(R.string.add_business_title);
            }
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), addEditBusinessFragment, R.id.contentFrame);

            new AddEditBusinessPresenter(Injection.provideUseCaseHandler(), addEditBusinessFragment, Injection.provideSaveBusiness(this), Injection.provideGetBusiness(this), Injection.provideUpdateBusiness(this), businessRin);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
