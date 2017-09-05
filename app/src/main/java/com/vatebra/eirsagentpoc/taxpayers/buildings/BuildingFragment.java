package com.vatebra.eirsagentpoc.taxpayers.buildings;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vatebra.eirsagentpoc.R;
import com.vatebra.eirsagentpoc.building.domain.entity.Building;
import com.vatebra.eirsagentpoc.building.domain.entity.BuildingRepository;
import com.vatebra.eirsagentpoc.domain.entity.Business;
import com.vatebra.eirsagentpoc.flowcontroller.FlowController;
import com.vatebra.eirsagentpoc.repository.CompanyRepository;
import com.vatebra.eirsagentpoc.repository.NewBuildingRepository;
import com.vatebra.eirsagentpoc.taxpayers.companies.CompanyFragment;
import com.vatebra.eirsagentpoc.util.ScrollChildSwipeRefreshLayout;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuildingFragment extends Fragment implements android.support.v7.widget.SearchView.OnQueryTextListener {
    private BuildingAdapter buildingAdapter;

    @BindView(R.id.nocompanies)
    View noComapanyView;

    @BindView(R.id.emptyIcon)
    ImageView noCompanyIcon;

    @BindView(R.id.noCompanyMain)
    TextView noCompanyMainView;

    @BindView(R.id.noCompanyAdd)
    TextView noCompanyAddView;

    @BindView(R.id.companyLL)
    LinearLayout companyView;

    @BindView(R.id.company_list)
    ListView listView;

    @BindView(R.id.refresh_layout)
    ScrollChildSwipeRefreshLayout swipeRefreshLayout;

    FloatingActionButton floatingActionButton;

    List<Building> buildings;

    NewBuildingRepository buildingRepository;
    private static final String BUILDING_PARAMS = "isChooseTaxPayer";
    private static final String BUSINESS_PARAMS = "business_params";

    Boolean isBuildingChooser = false;
    Business attachedBusiness;

    public BuildingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isBuildingChooser = getArguments().getBoolean(BUILDING_PARAMS);
            attachedBusiness = Parcels.unwrap(getArguments().getParcelable(BUSINESS_PARAMS));
        }
        buildingAdapter = new BuildingAdapter(new ArrayList<Building>(0), buildingItemListener, isBuildingChooser);

    }

    @Override
    public void onResume() {
        super.onResume();
        buildings = buildingRepository.getBuildings();
        if (buildings != null) {
            noComapanyView.setVisibility(View.GONE);
            buildingAdapter.replaceData(buildings);
            if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }

    }

    public static BuildingFragment newInstance() {
        return new BuildingFragment();
    }

    public static BuildingFragment newInstance(boolean isBuildingChoosen) {
        BuildingFragment companyFragment = new BuildingFragment();
        Bundle args = new Bundle();
        args.putBoolean(BUILDING_PARAMS, isBuildingChoosen);
        companyFragment.setArguments(args);
        return companyFragment;
    }

    public static BuildingFragment newInstance(boolean isChooseTaxPayer, Business business) {
        BuildingFragment buildingFragment = new BuildingFragment();
        Bundle args = new Bundle();
        args.putBoolean(BUILDING_PARAMS, isChooseTaxPayer);
        args.putParcelable(BUSINESS_PARAMS, Parcels.wrap(business));
        buildingFragment.setArguments(args);
        return buildingFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_building, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        buildingRepository = NewBuildingRepository.getInstance();
        listView.setAdapter(buildingAdapter);

        noCompanyAddView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show add company page
                FlowController.launchAddEditBuildingActivity(getContext());
            }
        });

        floatingActionButton = (FloatingActionButton) getActivity().findViewById(R.id.fab_add);
        if (isBuildingChooser)
            floatingActionButton.setImageResource(R.drawable.ic_right_cheron);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show add company page
                FlowController.launchAddEditBuildingActivity(getContext());
            }
        });


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show add company page
                if (isBuildingChooser)
                    AttachBuildingToBusiness();
                else //show add company page
                    FlowController.launchAddEditBuildingActivity(getContext());
            }
        });

        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );
        // Set the scrolling view in the custom SwipeRefreshLayout.
        swipeRefreshLayout.setScrollUpChild(listView);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //load individuals
                buildings = buildingRepository.getBuildings();
                if (buildings != null) {
                    buildingAdapter.replaceData(buildings);
                    noComapanyView.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
        return view;
    }

    private void AttachBuildingToBusiness() {
        if (!isAdded() || buildingAdapter == null)
            return;
        if (buildingAdapter.selectedBuilding == null) {
            Toast.makeText(getContext(), "Please select a building to attach business", Toast.LENGTH_LONG).show();
            return;
        }
        if (attachedBusiness != null) {
            FlowController.launchAddEditBusinessActivity(getContext(), buildingAdapter.selectedBuilding, attachedBusiness);
        } else {
            FlowController.launchAddEditBusinessActivity(getContext(), buildingAdapter.selectedBuilding);

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        android.support.v7.widget.SearchView searchView = new android.support.v7.widget.SearchView(((AppCompatActivity) getContext()).getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(searchMenuItem, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(searchMenuItem, searchView);
        searchView.setOnQueryTextListener(this);

    }

    /**
     * Called when the user submits the query. This could be due to a key press on the
     * keyboard or due to pressing a submit button.
     * The listener can override the standard behavior by returning true
     * to indicate that it has handled the submit request. Otherwise return false to
     * let the SearchView handle the submission by launching any associated intent.
     *
     * @param query the query text that is to be submitted
     * @return true if the query has been handled by the listener, false to let the
     * SearchView perform the default action.
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    BuildingAdapter.BuildingItemListener buildingItemListener = new BuildingAdapter.BuildingItemListener() {
        @Override
        public void OnBuildingClick(Building building) {
            FlowController.launchBuildingDetailsActivity(getContext(), building.getRin());
        }
    };

    /**
     * Called when the query text is changed by the user.
     *
     * @param newText the new content of the query text field.
     * @return false if the SearchView should perform the default action of showing any
     * suggestions if available, true if the action was handled by the listener.
     */
    @Override
    public boolean onQueryTextChange(String newText) {
        if (buildingAdapter != null)
            buildingAdapter.filter(newText);
        return true;
    }
}
