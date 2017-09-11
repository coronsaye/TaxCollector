package com.vatebra.eirsagentpoc.taxpayers.individuals;


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
import com.vatebra.eirsagentpoc.domain.entity.BusinessDataSource;
import com.vatebra.eirsagentpoc.domain.entity.Individual;
import com.vatebra.eirsagentpoc.flowcontroller.FlowController;
import com.vatebra.eirsagentpoc.repository.BusinessRepository;
import com.vatebra.eirsagentpoc.repository.IndividualRepository;
import com.vatebra.eirsagentpoc.repository.NewBuildingRepository;
import com.vatebra.eirsagentpoc.util.ScrollChildSwipeRefreshLayout;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndividualFragment extends Fragment implements android.support.v7.widget.SearchView.OnQueryTextListener {

    private IndividualAdapter mListAdapter;
    @BindView(R.id.noIndividuals)
    View noIndividualView;

    @BindView(R.id.emptyIcon)
    ImageView noIndividualIcon;

    @BindView(R.id.noIndividualMain)
    TextView noIndividualMainView;
    @BindView(R.id.noIndividualAdd)
    TextView noIndividualAddView;
    @BindView(R.id.individualLL)
    LinearLayout individualView;
    @BindView(R.id.individual_list)
    ListView listView;

    @BindView(R.id.refresh_layout)
    ScrollChildSwipeRefreshLayout swipeRefreshLayout;

    FloatingActionButton floatingActionButton;

    List<Individual> individuals;

    IndividualRepository individualRepository;
    Boolean isChooseTaxPayer = false;
    Business business;
    Building building;
    NewBuildingRepository newBuildingRepository;
    BusinessRepository businessRepository;
    private UseCaseHandler mUseCaseHandler;
    private SaveBusiness saveBusiness;
    private static final String INDIVIDUAL_PARAMS = "isChooseTaxPayerInd";
    private static final String BUSINESS_PARAMS = "businesspayer";
    private static final String BUILDING_PARAMS = "buildingpayer";

    public IndividualFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isChooseTaxPayer = getArguments().getBoolean(INDIVIDUAL_PARAMS);
            business = Parcels.unwrap(getArguments().getParcelable(BUSINESS_PARAMS));
            building = Parcels.unwrap(getArguments().getParcelable(BUILDING_PARAMS));
        }
        mListAdapter = new IndividualAdapter(new ArrayList<Individual>(0), individualItemListener, isChooseTaxPayer);
    }

    public static IndividualFragment newInstance(boolean isChooseTaxPayer) {
        IndividualFragment companyFragment = new IndividualFragment();
        Bundle args = new Bundle();
        args.putBoolean(INDIVIDUAL_PARAMS, isChooseTaxPayer);

        companyFragment.setArguments(args);
        return companyFragment;
    }

    public static IndividualFragment newInstance(boolean isChooseTaxPayer, Business business) {
        IndividualFragment companyFragment = new IndividualFragment();
        Bundle args = new Bundle();
        args.putBoolean(INDIVIDUAL_PARAMS, isChooseTaxPayer);
        args.putParcelable(BUSINESS_PARAMS, Parcels.wrap(business));
        companyFragment.setArguments(args);
        return companyFragment;
    }

    public static IndividualFragment newInstance(boolean isChooseTaxPayer, Building building) {
        IndividualFragment companyFragment = new IndividualFragment();
        Bundle args = new Bundle();
        args.putBoolean(INDIVIDUAL_PARAMS, isChooseTaxPayer);
        args.putParcelable(BUILDING_PARAMS, Parcels.wrap(building));
        companyFragment.setArguments(args);
        return companyFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_individual, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        newBuildingRepository = NewBuildingRepository.getInstance();
        businessRepository = Injection.providesBusinessRepository(getContext());
        saveBusiness = Injection.provideSaveBusiness(getContext());
        mUseCaseHandler = Injection.provideUseCaseHandler();
        individualRepository = IndividualRepository.getInstance();
        listView.setAdapter(mListAdapter);
        noIndividualAddView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show add individuals page
                FlowController.launchAddEditIndividualActivity(getContext());
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
                    FlowController.launchAddEditIndividualActivity(getContext());
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
                individuals = individualRepository.getIndividuals();
                if (individuals != null) {
                    noIndividualView.setVisibility(View.GONE);
                    mListAdapter.replaceData(individuals);
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        individuals = individualRepository.getIndividuals();
        if (individuals != null) {
            noIndividualView.setVisibility(View.GONE);
            mListAdapter.replaceData(individuals);
        }

    }

    IndividualAdapter.IndividualItemListener individualItemListener = new IndividualAdapter.IndividualItemListener() {
        @Override
        public void onIndividualClick(Individual clickedIndividual) {
            //go to details
            FlowController.launchIndividualDetailsActivity(getContext(), clickedIndividual.getUserRin());
        }
    };

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

    private void createAsset() {
        if (!isAdded() || mListAdapter == null) {
            return;
        }
        if (business != null) {
            business.setIndividualID(mListAdapter.selectedIndividual.getId());
            businessRepository.addBusiness(business, new BusinessDataSource.UpdateBusinessCallback() {
                @Override
                public void onUpdateSuccessful(String message) {
                    Snackbar.make(listView, "Building Profiling Complete", Snackbar.LENGTH_INDEFINITE).setAction("Complete", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getActivity().finish();
                            FlowController.launchDashboardctivity(getContext());
                        }
                    }).show();
                }

                @Override
                public void onUpdateFailed() {
                    Snackbar.make(listView, "Creation Of Business Failed. ", Snackbar.LENGTH_LONG).show();

                }
            });
//            mUseCaseHandler.execute(saveBusiness, new SaveBusiness.RequestValues(business), new UseCase.UseCaseCallback<SaveBusiness.ResponseValue>() {
//                @Override
//                public void onSuccess(SaveBusiness.ResponseValue response) {
//
//                    if (!isAdded()) {
//                        return;
//                    }
//                    Toast.makeText(getContext(), "Business Profiling Complete", Toast.LENGTH_LONG).show();
//                    getActivity().finish();
//                    FlowController.launchDashboardctivity(getContext());
//                }
//
//                @Override
//                public void onError() {
//                    Toast.makeText(getContext(), "Business Profiling Failed", Toast.LENGTH_LONG).show();
//
//                }
//            });
//            String message = mListAdapter.selectedIndividual.getFullName() + "\nBusiness: " + business.getName();
//            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        } else {
            if (building != null) {
                building.setIndividualID(mListAdapter.selectedIndividual.getId());
                newBuildingRepository.CreateBuilding(building, new BusinessRepository.OnApiReceived<Building>() {
                    @Override
                    public void OnSuccess(Building data) {
                        Snackbar.make(listView, "Building Profiling Complete", Snackbar.LENGTH_INDEFINITE).setAction("Complete", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getActivity().finish();
                                FlowController.launchDashboardctivity(getContext());
                            }
                        }).show();
                    }

                    @Override
                    public void OnFailed(String message) {
                        Snackbar.make(listView, message, Snackbar.LENGTH_LONG).show();
                    }
                });

            }
        }

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (mListAdapter != null)
            mListAdapter.filter(s);
        return true;
    }
}
