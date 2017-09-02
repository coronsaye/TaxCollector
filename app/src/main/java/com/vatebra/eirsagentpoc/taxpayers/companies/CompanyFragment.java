package com.vatebra.eirsagentpoc.taxpayers.companies;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import com.vatebra.eirsagentpoc.Injection;
import com.vatebra.eirsagentpoc.R;
import com.vatebra.eirsagentpoc.UseCase;
import com.vatebra.eirsagentpoc.UseCaseHandler;
import com.vatebra.eirsagentpoc.building.domain.entity.Building;
import com.vatebra.eirsagentpoc.business.businesses.usecase.SaveBusiness;
import com.vatebra.eirsagentpoc.domain.entity.Business;
import com.vatebra.eirsagentpoc.domain.entity.Company;
import com.vatebra.eirsagentpoc.flowcontroller.FlowController;
import com.vatebra.eirsagentpoc.repository.CompanyRepository;
import com.vatebra.eirsagentpoc.repository.NewBuildingRepository;
import com.vatebra.eirsagentpoc.taxpayers.individuals.IndividualFragment;
import com.vatebra.eirsagentpoc.util.ScrollChildSwipeRefreshLayout;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompanyFragment extends Fragment implements android.support.v7.widget.SearchView.OnQueryTextListener {

    private CompanyAdapter companyAdapter;

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

    List<Company> companies;

    CompanyRepository companyRepository;

    Boolean isChooseTaxPayer = false;
    private UseCaseHandler mUseCaseHandler;
    private SaveBusiness saveBusiness;
    private static final String COMPANY_PARAMS = "isChooseTaxPayer";
    private static final String BUSINESS_PARAMS = "businesspayer";
    private static final String BUILDING_PARAMS = "buildingpayer";

    Business business;
    Building building;
    NewBuildingRepository newBuildingRepository;

    public CompanyFragment() {
        // Required empty public constructor
    }

    public static CompanyFragment newInstance(boolean isChooseTaxPayer) {

        CompanyFragment companyFragment = new CompanyFragment();
        Bundle args = new Bundle();
        args.putBoolean(COMPANY_PARAMS, isChooseTaxPayer);

        companyFragment.setArguments(args);
        return companyFragment;
    }

    public static CompanyFragment newInstance(boolean isChooseTaxPayer, Business business) {
        CompanyFragment companyFragment = new CompanyFragment();
        Bundle args = new Bundle();
        args.putBoolean(COMPANY_PARAMS, isChooseTaxPayer);
        args.putParcelable(BUSINESS_PARAMS, Parcels.wrap(business));
        companyFragment.setArguments(args);
        return companyFragment;
    }

    public static CompanyFragment newInstance(boolean isChooseTaxPayer, Building building) {
        CompanyFragment companyFragment = new CompanyFragment();
        Bundle args = new Bundle();
        args.putBoolean(COMPANY_PARAMS, isChooseTaxPayer);
        args.putParcelable(BUILDING_PARAMS, Parcels.wrap(building));
        companyFragment.setArguments(args);
        return companyFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isChooseTaxPayer = getArguments().getBoolean(COMPANY_PARAMS);
            business = Parcels.unwrap(getArguments().getParcelable(BUSINESS_PARAMS));
            building = Parcels.unwrap(getArguments().getParcelable(BUILDING_PARAMS));
        }
        companyAdapter = new CompanyAdapter(new ArrayList<Company>(0), companyItemListener, isChooseTaxPayer);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_company, container, false);
        saveBusiness = Injection.provideSaveBusiness(getContext());
        mUseCaseHandler = Injection.provideUseCaseHandler();
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        newBuildingRepository = NewBuildingRepository.getInstance();
        companyRepository = CompanyRepository.getInstance();
        listView.setAdapter(companyAdapter);

        noCompanyAddView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show add company page
                FlowController.launchAddEditCompanyActivity(getContext());

            }
        });

        floatingActionButton = (FloatingActionButton) getActivity().findViewById(R.id.fab_add);
        if (isChooseTaxPayer)
            floatingActionButton.setImageResource(R.drawable.ic_right_cheron);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show add company page
                if (isChooseTaxPayer)
                    createAsset();
                else
                    FlowController.launchAddEditCompanyActivity(getContext());
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
                companies = companyRepository.getCompanies();
                if (companies != null) {
                    companyAdapter.replaceData(companies);
                    noComapanyView.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        companies = companyRepository.getCompanies();
        if (companies != null) {
            companyAdapter.replaceData(companies);
            noComapanyView.setVisibility(View.GONE);

        }
    }

    private void createAsset() {
        if (!isAdded() || companyAdapter == null) {
            return;
        }
        if (business != null) {
            business.setCompanyID(companyAdapter.selectedCompany.getId());
            mUseCaseHandler.execute(saveBusiness, new SaveBusiness.RequestValues(business), new UseCase.UseCaseCallback<SaveBusiness.ResponseValue>() {
                @Override
                public void onSuccess(SaveBusiness.ResponseValue response) {

                    if (!isAdded()) {
                        return;
                    }
                    Snackbar.make(floatingActionButton, "Business Profiling Complete", Snackbar.LENGTH_INDEFINITE).setAction("Complete", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getActivity().finish();
                            FlowController.launchDashboardctivity(getContext());
                        }
                    }).show();

                }

                @Override
                public void onError() {
                    Toast.makeText(getContext(), "Business Profiling Failed", Toast.LENGTH_LONG).show();

                }
            });

        } else {
            if (building != null) {
                building.setCompanyID(companyAdapter.selectedCompany.getId());
                newBuildingRepository.CreateBuilding(building, new NewBuildingRepository.OnMessageResponse() {
                    @Override
                    public void OnSuccessMessage(String message) {
                        Snackbar.make(listView, "Building Profiling Complete", Snackbar.LENGTH_INDEFINITE).setAction("Complete", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getActivity().finish();
                                FlowController.launchDashboardctivity(getContext());
                            }
                        }).show();
                    }
                });
            }
        }

//        String message = companyAdapter.selectedCompany.getName();
//        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
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


    CompanyAdapter.CompanyItemListener companyItemListener = new CompanyAdapter.CompanyItemListener() {
        @Override
        public void onCompanyClick(Company company) {
            FlowController.launchCompanyDetailsActivity(getContext(), company.getRin());
        }
    };

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

    /**
     * Called when the query text is changed by the user.
     *
     * @param newText the new content of the query text field.
     * @return false if the SearchView should perform the default action of showing any
     * suggestions if available, true if the action was handled by the listener.
     */
    @Override
    public boolean onQueryTextChange(String newText) {
        if (companyAdapter != null)
            companyAdapter.filter(newText);
        return true;
    }
}
