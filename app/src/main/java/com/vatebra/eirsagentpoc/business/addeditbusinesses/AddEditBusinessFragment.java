package com.vatebra.eirsagentpoc.business.addeditbusinesses;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.vatebra.eirsagentpoc.R;
import com.vatebra.eirsagentpoc.business.domain.entity.Business;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddEditBusinessFragment extends Fragment implements AddBusinessContract.View {

    public static final String ARGUMENT_EDIT_BUSINESS_ID = "EDIT_BUSINESS_ID";

    @BindView(R.id.add_business_name)
    public TextView businessNameTextView;

    public FloatingActionButton fabDone;


    private AddBusinessContract.Presenter mPresenter;

    public AddEditBusinessFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    public static AddEditBusinessFragment newInstance() {
        return new AddEditBusinessFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fabDone = (FloatingActionButton) getActivity().findViewById(R.id.fab_edit_business_done);
        fabDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.saveBusiness(businessNameTextView.getText().toString(), "Lekki");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_business, container, false);
        ButterKnife.bind(this, root);
        setRetainInstance(true);
        return root;
    }

    @Override
    public void showBusinessList() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showAddBusinessError() {
        Snackbar.make(businessNameTextView, R.string.add_business_error, Snackbar.LENGTH_LONG).show();

    }

    @Override
    public void showAddSuccessMessage() {
        Toast.makeText(getContext(), R.string.business_success_message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showEditBusinessMessageSuccess() {
        Toast.makeText(getContext(), R.string.edit_business_success, Toast.LENGTH_LONG).show();

    }

    @Override
    public void showEditBusinessError() {

    }

    @Override
    public void showCannotGetBusinessError() {

    }

    @Override
    public void setBusiness(Business business) {
        // TODO: 17/08/2017 Populate fields on edit
        businessNameTextView.setText(business.getName());
    }

    @Override
    public void setPresenter(AddBusinessContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);

    }
}
