package com.vatebra.eirsagentpoc.business.businesses;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.vatebra.eirsagentpoc.Injection;
import com.vatebra.eirsagentpoc.R;
import com.vatebra.eirsagentpoc.util.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BusinessActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Businesses");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        BusinessFragment businessFragment = (BusinessFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (businessFragment == null) {
            businessFragment = BusinessFragment.newInstance();
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
