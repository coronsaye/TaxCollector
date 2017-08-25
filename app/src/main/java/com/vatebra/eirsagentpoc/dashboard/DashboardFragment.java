package com.vatebra.eirsagentpoc.dashboard;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;
import com.vatebra.eirsagentpoc.R;
import com.vatebra.eirsagentpoc.flowcontroller.FlowController;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

    @BindView(R.id.expanding_list_main)
    ExpandingList expandingList;


    public DashboardFragment() {
        // Required empty public constructor
    }

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this, view);
        createDashboard();
        return view;
    }

    private void createDashboard() {
        populateUi(getString(R.string.assets_title), new String[]{getString(R.string.business_title), getString(R.string.building_title)}, R.color.pink, R.drawable.ic_assets);
        populateUi(getString(R.string.tax_payer_title), new String[]{getString(R.string.individuals_title), getString(R.string.companies_title)}, R.color.green, R.drawable.ic_tax_payers);
        populateUi(getString(R.string.payment_title), null, R.color.purple, R.drawable.ic_payment);
    }

    private void populateUi(String menuTitle, String[] submenuItems, int colorRes, int iconRes) {
        final ExpandingItem item = expandingList.createNewItem(R.layout.dashboard_expanding_layout);
        if (item != null) {
            item.setIndicatorColorRes(colorRes);
            item.setIndicatorIconRes(iconRes);
            //Set the Main Menu Title
            ((TextView) item.findViewById(R.id.mainTextView)).setText(menuTitle);

            if (submenuItems != null) {
                item.createSubItems(submenuItems.length);

                for (int i = 0; i < item.getSubItemsCount(); i++) {
                    //Let's get the created sub item by its index
                    final View view = item.getSubItemView(i);

                    //Let's set some values in
                    configureSubItem(item, view, submenuItems[i]);
                }
            } else {
                if (!isAdded()) {
                    return;
                }
                //do something when item is clicked

                item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

            }
        }
    }

    private void configureSubItem(final ExpandingItem item, final View view, String subTitle) {
        final TextView subItemTextView = (TextView) view.findViewById(R.id.subItemTextView);
        subItemTextView.setText(subTitle);


        subItemTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (subItemTextView.getText().equals(getString(R.string.business_title)))
                    FlowController.launchBusinessActivity(getContext());
                else if (subItemTextView.getText().equals(getString(R.string.individuals_title)))
                    FlowController.launchIndividualActivity(getContext());
                else if (subItemTextView.getText().equals(getString(R.string.companies_title)))
                    FlowController.launchCompanyActivity(getContext());
                else if (subItemTextView.getText().equals(getString(R.string.building_title)))
                    FlowController.launchBuildingActivity(getContext());


                //go to respective View
            }
        });
        view.findViewById(R.id.add_sub_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //addItem
                if (subItemTextView.getText().equals(getString(R.string.business_title)))
                    FlowController.launchAddEditBusinessActivity(getContext());
                else if (subItemTextView.getText().equals(getString(R.string.individuals_title)))
                    FlowController.launchAddEditIndividualActivity(getContext());
                else if (subItemTextView.getText().equals(getString(R.string.companies_title)))
                    FlowController.launchAddEditCompanyActivity(getContext());
                else if (subItemTextView.getText().equals(getString(R.string.building_title)))
                    FlowController.launchAddEditBuildingActivity(getContext());

            }
        });
    }

}
