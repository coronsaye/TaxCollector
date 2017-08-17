package com.vatebra.eirsagentpoc.business;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.vatebra.eirsagentpoc.R;
import com.vatebra.eirsagentpoc.business.domain.entity.Business;
import com.vatebra.eirsagentpoc.flowcontroller.FlowController;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class BusinessFragment extends Fragment implements BusinessContract.View {

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

    public BusinessFragment() {
        // Required empty public constructor
    }

    public static BusinessFragment newInstance() {
        return new BusinessFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListAdapter = new BusinessAdapter(new ArrayList<Business>(0), businessItemListener);
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

        listView.setAdapter(mListAdapter);
        mNoBusinessAddView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddBusiness();
            }
        });
        floatingActionButton = (FloatingActionButton) getActivity().findViewById(R.id.fab_add_business);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.addNewBusiness();
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
        FlowController.launchAddBusinessActivity(getContext());
    }

    @Override
    public void showBusinessDetailUi() {
        // TODO: 17/08/2017 NAVIGATE TO DETAILS PAGE
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
            // TODO: 17/08/2017 OPEN BUSINESS DETAILS PAGE FROM PRESENTER
        }
    };
}
