package com.vatebra.eirsagentpoc.dashboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.widget.FrameLayout;

import com.vatebra.eirsagentpoc.R;
import com.vatebra.eirsagentpoc.util.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.contentFrame)
    FrameLayout contentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(getString(R.string.dashboard_title));
        DashboardFragment dashboardFragment =
                (DashboardFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (dashboardFragment == null) {
            // Create the fragment
            dashboardFragment = DashboardFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), dashboardFragment, R.id.contentFrame);
        }
    }


}
