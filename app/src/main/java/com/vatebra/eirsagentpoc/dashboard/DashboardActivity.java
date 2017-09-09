package com.vatebra.eirsagentpoc.dashboard;

import android.support.annotation.WorkerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.widget.FrameLayout;

import com.vatebra.eirsagentpoc.R;
import com.vatebra.eirsagentpoc.domain.entity.BusinessCategory;
import com.vatebra.eirsagentpoc.domain.entity.BusinessDataSource;
import com.vatebra.eirsagentpoc.domain.entity.BusinessSector;
import com.vatebra.eirsagentpoc.domain.entity.BusinessStruture;
import com.vatebra.eirsagentpoc.domain.entity.BusinessSubSector;
import com.vatebra.eirsagentpoc.domain.entity.Lga;
import com.vatebra.eirsagentpoc.domain.entity.LocalBusinessDataSource;
import com.vatebra.eirsagentpoc.domain.entity.RemoteBusinessDataSource;
import com.vatebra.eirsagentpoc.repository.BusinessRepository;
import com.vatebra.eirsagentpoc.repository.GlobalRepository;
import com.vatebra.eirsagentpoc.util.ActivityUtils;
import com.vatebra.eirsagentpoc.util.VatEventSharedHelper;

import java.util.List;

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
        downloadBusinessObjects();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.dashboard_title));
            getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        }
        DashboardFragment dashboardFragment =
                (DashboardFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (dashboardFragment == null) {
            // Create the fragment
            dashboardFragment = DashboardFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), dashboardFragment, R.id.contentFrame);
        }


    }

    private void downloadBusinessObjects() {
        RemoteBusinessDataSource apiDataSource = RemoteBusinessDataSource.getInstance();

        apiDataSource.getLgas(new BusinessDataSource.GetObjectCallback<Lga>() {
            @Override
            public void onObjectsLoaded(List<Lga> objects) {
                LocalBusinessDataSource.saveLgas(objects);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
        apiDataSource.getStructures(new BusinessDataSource.GetObjectCallback<BusinessStruture>() {
            @Override
            public void onObjectsLoaded(List<BusinessStruture> objects) {
                LocalBusinessDataSource.saveStructures(objects);

            }

            @Override
            public void onDataNotAvailable() {

            }
        });
        apiDataSource.getSectors(new BusinessDataSource.GetObjectCallback<BusinessSector>() {
            @Override
            public void onObjectsLoaded(List<BusinessSector> objects) {
                LocalBusinessDataSource.saveSectors(objects);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
        apiDataSource.getSubSectors(new BusinessDataSource.GetObjectCallback<BusinessSubSector>() {
            @Override
            public void onObjectsLoaded(List<BusinessSubSector> objects) {
                LocalBusinessDataSource.saveSubSectors(objects);

            }

            @Override
            public void onDataNotAvailable() {

            }
        });
        apiDataSource.getCategories(new BusinessDataSource.GetObjectCallback<BusinessCategory>() {
            @Override
            public void onObjectsLoaded(List<BusinessCategory> objects) {
                LocalBusinessDataSource.saveCategories(objects);

            }

            @Override
            public void onDataNotAvailable() {

            }
        });


    }




}
