package com.vatebra.eirsagentpoc.taxpayers.companies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.vatebra.eirsagentpoc.R;
import com.vatebra.eirsagentpoc.util.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompaniesActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companies);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Companies");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        CompanyFragment companyFragment = (CompanyFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (companyFragment == null) {
            companyFragment = CompanyFragment.newInstance();
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
