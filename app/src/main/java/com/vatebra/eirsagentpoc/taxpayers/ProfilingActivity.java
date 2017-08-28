package com.vatebra.eirsagentpoc.taxpayers;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.vatebra.eirsagentpoc.R;
import com.vatebra.eirsagentpoc.domain.entity.AssetProfile;
import com.vatebra.eirsagentpoc.domain.entity.Business;
import com.vatebra.eirsagentpoc.flowcontroller.FlowController;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfilingActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.profile_textview)
    TextView profileName;


    @BindView(R.id.assesment_list)
    ListView listView;


    @BindView(R.id.fab_continue)
    FloatingActionButton floatingActionButton;

    AssetProfile assetProfile;
    public static final String EXTRA_PROFILE_KEY = "PROFILE_OBJECT";
    public static final String EXTRA_OBJECT_BUSINESS_KEY = "PROFILE_BUSINESS";

    List<String> options;
    Business business;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiling);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        assetProfile = (AssetProfile) getIntent().getSerializableExtra(EXTRA_PROFILE_KEY);
        if (getIntent().getExtras().getParcelable(EXTRA_OBJECT_BUSINESS_KEY) != null) {
            business = Parcels.unwrap(getIntent().getExtras().getParcelable(EXTRA_OBJECT_BUSINESS_KEY));
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Asset Profile");
        }

        options = new ArrayList<>();
        options.add("New Individual");
        options.add("Existing Individual");
        options.add("New Company");
        options.add("Existing Company");

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(ProfilingActivity.this)
                        .title("Kindly Choose the Taxpayer Responsible")
                        .items(options)
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                /**
                                 * If you use alwaysCallSingleChoiceCallback(), which is discussed below,
                                 * returning false here won't allow the newly selected radio button to actually be selected.
                                 **/

                                switch (which) {
                                    case 0:
                                        FlowController.launchAddEditIndividualActivity(ProfilingActivity.this, business);
                                        break;
                                    case 1:
                                        FlowController.launchIndividualActivity(ProfilingActivity.this, true, business);
                                        break;
                                    case 2:
                                        FlowController.launchAddEditCompanyActivity(ProfilingActivity.this, business);
                                        break;
                                    case 3:
                                        FlowController.launchCompanyActivity(ProfilingActivity.this, true,business);
                                        break;
                                }
                                return true;
                            }
                        })
                        .positiveText("Continue")
                        .show();
            }
        });

        populateFields();
    }


    private void populateFields() {
        profileName.setText(assetProfile.getProfileName());
        ProfileAdapter profileAdapter = new ProfileAdapter(assetProfile.getProfileAssements());
        listView.setAdapter(profileAdapter);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
