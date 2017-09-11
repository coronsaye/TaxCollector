package com.vatebra.eirsagentpoc.business.businesses;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
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
import com.vatebra.eirsagentpoc.building.domain.entity.Building;
import com.vatebra.eirsagentpoc.domain.entity.AssetProfile;
import com.vatebra.eirsagentpoc.repository.BusinessRepository;
import com.vatebra.eirsagentpoc.util.ScrollChildSwipeRefreshLayout;
import com.vatebra.eirsagentpoc.domain.entity.Business;
import com.vatebra.eirsagentpoc.flowcontroller.FlowController;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class BusinessFragment extends Fragment implements BusinessContract.View, SearchView.OnQueryTextListener {

    private BusinessContract.Presenter presenter;
    private BusinessAdapter mListAdapter;

    @BindView(R.id.noBusinesses)
    View mNoBusinessView;

    @BindView(R.id.noBusinessIcon)
    ImageView mNoBusinessIcon;

    @BindView(R.id.noBusinessMain)
    TextView mNoBusinessMainView;

    @BindView(R.id.noBusinessAdd)
    TextView mNoBusinessAddView;

    @BindView(R.id.businessLL)
    LinearLayout mBusinessView;

    @BindView(R.id.business_list)
    ListView listView;

    @BindView(R.id.refresh_layout)
    ScrollChildSwipeRefreshLayout swipeRefreshLayout;

    FloatingActionButton floatingActionButton;

    Boolean isBusinessChooser = false;

    Building attachedBuilding;
    BusinessRepository businessRepository;
    private static final String ATTACHED_BUILDING_PARAMS = "attachedBuilding";
    private static final String IS_CHOOSER_PARAMS = "isBusinessChooser";

    public BusinessFragment() {
        // Required empty public constructor
    }

    public static BusinessFragment newInstance() {
        return new BusinessFragment();
    }

    public static BusinessFragment newInstance(boolean isBusinessChooser, Building building) {
        BusinessFragment businessFragment = new BusinessFragment();
        Bundle args = new Bundle();
        args.putBoolean(IS_CHOOSER_PARAMS, isBusinessChooser);
        args.putParcelable(ATTACHED_BUILDING_PARAMS, Parcels.wrap(building));
        businessFragment.setArguments(args);
        return businessFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isBusinessChooser = getArguments().getBoolean(IS_CHOOSER_PARAMS);
            attachedBuilding = Parcels.unwrap(getArguments().getParcelable(ATTACHED_BUILDING_PARAMS));
        }
        mListAdapter = new BusinessAdapter(new ArrayList<Business>(0), businessItemListener, isBusinessChooser);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void setPresenter(BusinessContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_business, container, false);
        ButterKnife.bind(this, root);
        setHasOptionsMenu(true);
        listView.setAdapter(mListAdapter);
        businessRepository = Injection.providesBusinessRepository(getContext());

        mNoBusinessAddView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddBusiness();
            }
        });
        floatingActionButton = (FloatingActionButton) getActivity().findViewById(R.id.fab_add_business);
        if (isBusinessChooser) {
            floatingActionButton.setImageResource(R.drawable.ic_right_cheron);
        } else {
            floatingActionButton.setVisibility(View.INVISIBLE);
        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBusinessChooser) {
                    attachBusinessToBuilding();
                } else {
                    presenter.addNewBusiness();
                }
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
                presenter.loadBusinesses();
            }
        });

        return root;

    }


    private void attachBusinessToBuilding() {
        if (!isAdded() || mListAdapter == null) {
            return;
        }
        if (mListAdapter.selectedBusiness == null) {
            Toast.makeText(getContext(), "Please select a business to attach to the building", Toast.LENGTH_LONG).show();
            return;
        }
        if (attachedBuilding != null) {
            businessRepository.GetBusinessProfile(mListAdapter.selectedBusiness, new BusinessRepository.OnApiReceived<AssetProfile>() {
                @Override
                public void OnSuccess(AssetProfile data) {

                }

                @Override
                public void OnFailed(String message) {
                    if (!isAdded())
                        return;
                    Snackbar.make(floatingActionButton, message, Snackbar.LENGTH_LONG).show();
                }
            });
            //launch profile
        }

    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        if (getView() == null) {
            return;
        }
        final SwipeRefreshLayout srl =
                (SwipeRefreshLayout) getView().findViewById(R.id.refresh_layout);

        // Make sure setRefreshing() is called after the layout is done with everything else.
        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(active);
            }
        });
    }

    @Override
    public void showBusinesses(List<Business> businesses) {
        mListAdapter.replaceData(businesses);
        mBusinessView.setVisibility(View.VISIBLE);
        mNoBusinessView.setVisibility(View.GONE);

    }

    @Override
    public void showAddBusiness() {
        FlowController.launchAddEditBusinessActivity(getContext());
    }

    @Override
    public void showBusinessDetailUi(String businessRin) {

        FlowController.launchBusinessDetailsActivity(getContext(), businessRin);
    }

    @Override
    public void showFormCompleteError(String formPoint) {
        String message = "Ensure that " + formPoint + " is filled";
        showMessage(message);
    }

    @Override
    public void showNoBusinesses() {
        showNoBusinessViews("There are currently no businesses", R.drawable.ic_not_interested_black_24dp, false);
    }

    @Override
    public void showSuccessfullySavedMessages() {
        showMessage(getString(R.string.add_business_success_message));
    }

    @Override
    public void showLoadingBusinessesError() {
        showMessage(getString(R.string.load_business_errors));
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    private void showMessage(String message) {
        if (getView() == null)
            return;
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = new SearchView(((AppCompatActivity) getContext()).getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(searchMenuItem, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(searchMenuItem, searchView);
        searchView.setOnQueryTextListener(this);

    }

    private void showNoBusinessViews(String mainText, int iconRes, boolean showAddView) {
        mBusinessView.setVisibility(View.GONE);
        mNoBusinessView.setVisibility(View.VISIBLE);

        mNoBusinessMainView.setText(mainText);
        mNoBusinessIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), iconRes, null));
        mNoBusinessAddView.setVisibility(showAddView ? View.VISIBLE : View.GONE);
    }

    BusinessAdapter.BusinessItemListener businessItemListener = new BusinessAdapter.BusinessItemListener() {
        @Override
        public void onBusinessClick(Business clickedBusiness) {
            presenter.openBusinessDetail(clickedBusiness);
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
        if (mListAdapter != null)
            mListAdapter.filter(newText);
        return true;
    }
}
