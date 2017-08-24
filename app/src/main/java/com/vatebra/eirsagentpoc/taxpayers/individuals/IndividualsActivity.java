package com.vatebra.eirsagentpoc.taxpayers.individuals;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.vatebra.eirsagentpoc.R;
import com.vatebra.eirsagentpoc.business.businesses.BusinessFragment;
import com.vatebra.eirsagentpoc.util.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IndividualsActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individuals);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Individuals");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        IndividualFragment individualFragment =  (IndividualFragment)getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (individualFragment == null) {
            individualFragment = IndividualFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), individualFragment, R.id.contentFrame);
        }

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
