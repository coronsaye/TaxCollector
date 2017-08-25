package com.vatebra.eirsagentpoc.taxpayers.companies;


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

import com.vatebra.eirsagentpoc.R;
import com.vatebra.eirsagentpoc.domain.entity.Company;
import com.vatebra.eirsagentpoc.flowcontroller.FlowController;
import com.vatebra.eirsagentpoc.repository.CompanyRepository;
import com.vatebra.eirsagentpoc.util.ScrollChildSwipeRefreshLayout;

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


    public CompanyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        companyAdapter = new CompanyAdapter(new ArrayList<Company>(0), companyItemListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_company, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        companyRepository = CompanyRepository.getInstance();
        listView.setAdapter(companyAdapter);

        noCompanyAddView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show add company page
            }
        });

        floatingActionButton = (FloatingActionButton) getActivity().findViewById(R.id.fab_add);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show add company page
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


    public static CompanyFragment newInstance() {
        return new CompanyFragment();
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
