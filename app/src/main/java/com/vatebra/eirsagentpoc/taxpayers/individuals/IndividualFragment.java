package com.vatebra.eirsagentpoc.taxpayers.individuals;


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
import com.vatebra.eirsagentpoc.domain.entity.Individual;
import com.vatebra.eirsagentpoc.flowcontroller.FlowController;
import com.vatebra.eirsagentpoc.repository.IndividualRepository;
import com.vatebra.eirsagentpoc.util.ScrollChildSwipeRefreshLayout;

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

    public IndividualFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListAdapter = new IndividualAdapter(new ArrayList<Individual>(0), individualItemListener);
    }

    public static IndividualFragment newInstance() {
        return new IndividualFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_individual, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
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
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show add individuals page
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
