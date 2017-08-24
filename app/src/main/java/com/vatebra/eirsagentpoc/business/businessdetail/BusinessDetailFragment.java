package com.vatebra.eirsagentpoc.business.businessdetail;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vatebra.eirsagentpoc.R;
import com.vatebra.eirsagentpoc.business.addeditbusinesses.AddEditBusinessActivity;
import com.vatebra.eirsagentpoc.flowcontroller.FlowController;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class BusinessDetailFragment extends Fragment implements BusinessDetailContract.View {

    @BindView(R.id.details_business_name)
    TextView businessNameTextView;
    @BindView(R.id.details_business_rin)
    TextView businessRinTextView;
    @BindView(R.id.details_business_lga)
    TextView businessLgaTextView;
    @BindView(R.id.details_business_sector)
    TextView businessSectorTextView;
    @BindView(R.id.details_business_sub_sector)
    TextView businessSubSectorTextView;
    @BindView(R.id.details_business_operations)
    TextView businessOperationsTextView;
    @BindView(R.id.details_business_structure)
    TextView businessStructureTextView;
    @BindView(R.id.details_business_category)
    TextView businessCategoryTextView;

    FloatingActionButton fabDisplayEdit;
    private static final String ARGUMENT_BUSINESS_RIN = "BUSINESS_RIN";

    private BusinessDetailContract.Presenter mPresenter;

    public BusinessDetailFragment() {
        // Required empty public constructor
    }


    public static BusinessDetailFragment newInstance(@Nullable String businessRin) {
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_BUSINESS_RIN, businessRin);
        BusinessDetailFragment fragment = new BusinessDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void setPresenter(@NonNull BusinessDetailContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_business_detail, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fabDisplayEdit = (FloatingActionButton) getActivity().findViewById(R.id.fab_edit_business);
        fabDisplayEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.editBusiness();
            }
        });
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showEditBusiness(@NonNull String businessRin) {
        FlowController.launchAddEditBusinessActivity(getContext(), businessRin);
    }


    @Override
    public void showBusinessName(String name) {
        businessNameTextView.setText(name);
    }

    @Override
    public void showBusinessCategory(String category) {
        businessCategoryTextView.setText(category);
    }

    @Override
    public void showBusinessLga(String lga) {
        businessLgaTextView.setText(lga);
    }

    @Override
    public void showBusinessSector(String sector) {
        businessSectorTextView.setText(sector);
    }

    @Override
    public void showBusinessSubSector(String subSector) {
        businessSubSectorTextView.setText(subSector);
    }

    @Override
    public void showBusinessOperations(String operations) {
        businessOperationsTextView.setText(operations);
    }

    @Override
    public void showBusinessStructure(String structure) {
        businessStructureTextView.setText(structure);
    }

    @Override
    public void showBusinessProfile(String profile) {

    }

    @Override
    public void showBusinessStatus(String status) {

    }


    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showCannotGetBusinessError() {

    }

    @Override
    public void showCannotEditBusinessError() {
        if (getView() == null)
            return;
        Snackbar.make(getView(), getString(R.string.edit_business_error), Snackbar.LENGTH_LONG)
                .show();
    }



}
