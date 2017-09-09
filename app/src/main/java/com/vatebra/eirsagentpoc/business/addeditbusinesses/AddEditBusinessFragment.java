package com.vatebra.eirsagentpoc.business.addeditbusinesses;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.vatebra.eirsagentpoc.Injection;
import com.vatebra.eirsagentpoc.R;
import com.vatebra.eirsagentpoc.building.domain.entity.Building;
import com.vatebra.eirsagentpoc.domain.entity.AssetProfile;
import com.vatebra.eirsagentpoc.domain.entity.Business;
import com.vatebra.eirsagentpoc.domain.entity.BusinessCategory;
import com.vatebra.eirsagentpoc.domain.entity.BusinessDataSource;
import com.vatebra.eirsagentpoc.domain.entity.BusinessSector;
import com.vatebra.eirsagentpoc.domain.entity.BusinessStruture;
import com.vatebra.eirsagentpoc.domain.entity.BusinessSubSector;
import com.vatebra.eirsagentpoc.domain.entity.Lga;
import com.vatebra.eirsagentpoc.flowcontroller.FlowController;
import com.vatebra.eirsagentpoc.repository.BusinessRepository;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddEditBusinessFragment extends Fragment implements AddBusinessContract.View {

    public static final String ARGUMENT_EDIT_BUSINESS_ID = "EDIT_BUSINESS_ID";
    public static final String ARGUMENT_ATTACHED_BUILDING = "ATTACHED_BUILDING";
    public static final String ARGUMENT_ATTACHED_BUSINESS = "ATTACHED_BUSINESS";

    @BindView(R.id.add_business_name)
    public TextView businessNameTextView;

    @BindView(R.id.lga_spinner)
    public Spinner lgaSpinner;

    @BindView(R.id.category_spinner)
    public Spinner categorySpinner;

    @BindView(R.id.sector_spinner)
    public Spinner sectorSpinner;

    @BindView(R.id.sub_sector_spinner)
    public Spinner subSectorSpinner;

    @BindView(R.id.structure_spinner)
    public Spinner structureSpinner;

    @BindView(R.id.lgaHint)
    TextView lgaHintTextView;

    public FloatingActionButton fabDone;
    BusinessRepository businessRepository;

    Business business;
    Building attachedBuilding;
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
                saveBusiness();
            }
        });
    }


    private void saveBusiness() {

        if (TextUtils.isEmpty(businessNameTextView.getText())) {
            showAddBusinessError();
            return;
        }

        if (this.business == null) {
            this.business = new Business();
        }
        Lga lga = (Lga) lgaSpinner.getSelectedItem();
        BusinessCategory category = (BusinessCategory) categorySpinner.getSelectedItem();
        BusinessSector sector = (BusinessSector) sectorSpinner.getSelectedItem();
        BusinessSubSector subSector = (BusinessSubSector) subSectorSpinner.getSelectedItem();
        BusinessStruture struture = (BusinessStruture) structureSpinner.getSelectedItem();
        business.setCreateName(businessNameTextView.getText().toString());
        business.setName(businessNameTextView.getText().toString());
        if (attachedBuilding != null) {
            business.setLGAID(attachedBuilding.getLGAID());
            business.setLga(attachedBuilding.getLga());

        } else {
            if (lga != null) {
                business.setLGAID(lga.getID());
                business.setLga(lga.getName());
            }

        }

        if (category != null) {
            business.setBusinessCategoryId(category.getID());
            business.setBusinessCategory(category.getName());
        }

        if (sector != null) {
            business.setBusinessSectorId(sector.getID());
            business.setBusinessSector(sector.getName());

        }

        if (subSector != null) {
            business.setBusinessSubSectorId(subSector.getID());
            business.setBusinessSubSector(subSector.getName());

        }

        if (struture != null) {
            business.setBusinessStructureId(struture.getID());
            business.setBusinessStructure(struture.getName());

        }
        if (attachedBuilding != null) {
            business.setBuildingID(attachedBuilding.getID());
            business.setBuildingRIN(attachedBuilding.getRin());
        } else {
            business.setBuildingRIN(null);
        }

        if (business.getRin() == null) {
            //is a new Business
            businessRepository.GetBusinessProfile(business, new BusinessRepository.OnApiReceived<AssetProfile>() {
                @Override
                public void OnSuccess(AssetProfile data) {
                    FlowController.launchProfilingActivity(getContext(), data, business);
                }

                @Override
                public void OnFailed(String message) {
                    if (!isAdded())
                        return;
                    Snackbar.make(businessNameTextView, message, Snackbar.LENGTH_LONG).show();
                }
            });
        } else if (business.getRin() != null && attachedBuilding != null) {
            businessRepository.GetBusinessProfile(business, new BusinessRepository.OnApiReceived<AssetProfile>() {
                @Override
                public void OnSuccess(AssetProfile data) {
                    FlowController.launchProfilingActivity(getContext(), data, business);
                }

                @Override
                public void OnFailed(String message) {
                    if (!isAdded())
                        return;
                    Snackbar.make(businessNameTextView, message, Snackbar.LENGTH_LONG).show();
                }
            });

        } else {
            mPresenter.saveBusiness(business);

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_business, container, false);
        ButterKnife.bind(this, root);
        businessRepository = Injection.providesBusinessRepository(getContext());
        if (attachedBuilding != null) {
            lgaHintTextView.setVisibility(View.GONE);
            lgaSpinner.setVisibility(View.GONE);
        }
        if (business != null) {
            businessNameTextView.setText(business.getName());
        }
        populateSpinners();
        setRetainInstance(true);
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            attachedBuilding = Parcels.unwrap(getArguments().getParcelable(ARGUMENT_ATTACHED_BUILDING));
            business = Parcels.unwrap(getArguments().getParcelable(ARGUMENT_ATTACHED_BUSINESS));
        }
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
    public void showAddSuccessMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showEditBusinessMessageSuccess(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();

    }

    @Override
    public void showEditBusinessError() {
        Toast.makeText(getContext(), "Failed Could not add Business", Toast.LENGTH_LONG).show();

    }

    @Override
    public void showCannotGetBusinessError() {
        Toast.makeText(getContext(), "Cannot Get Business", Toast.LENGTH_LONG).show();

    }

    @Override
    public void setBusiness(Business business) {
        this.business = business;
        // TODO: 17/08/2017 Populate fields on edit
        businessNameTextView.setText(business.getName());
    }

    @Override
    public void clearFields() {
        businessNameTextView.setText("");
    }

    @Override
    public void setLgas(List<Lga> lgas) {


    }

    private void populateSpinners() {


        businessRepository.getLgas(new BusinessDataSource.GetObjectCallback<Lga>() {
            @Override
            public void onObjectsLoaded(List<Lga> objects) {
                ArrayAdapter<Lga> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, objects);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                lgaSpinner.setAdapter(dataAdapter);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
        businessRepository.getCategories(new BusinessDataSource.GetObjectCallback<BusinessCategory>() {
            @Override
            public void onObjectsLoaded(List<BusinessCategory> objects) {
                ArrayAdapter<BusinessCategory> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, objects);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                categorySpinner.setAdapter(dataAdapter);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
        businessRepository.getSectors(new BusinessDataSource.GetObjectCallback<BusinessSector>() {
            @Override
            public void onObjectsLoaded(List<BusinessSector> objects) {
                ArrayAdapter<BusinessSector> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, objects);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                sectorSpinner.setAdapter(dataAdapter);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
        businessRepository.getSubSectors(new BusinessDataSource.GetObjectCallback<BusinessSubSector>() {
            @Override
            public void onObjectsLoaded(List<BusinessSubSector> objects) {
                ArrayAdapter<BusinessSubSector> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, objects);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                subSectorSpinner.setAdapter(dataAdapter);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
        businessRepository.getStructures(new BusinessDataSource.GetObjectCallback<BusinessStruture>() {
            @Override
            public void onObjectsLoaded(List<BusinessStruture> objects) {
                ArrayAdapter<BusinessStruture> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, objects);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                structureSpinner.setAdapter(dataAdapter);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void setCategoties(List<BusinessCategory> categories) {
        businessRepository.getCategories(new BusinessDataSource.GetObjectCallback<BusinessCategory>() {
            @Override
            public void onObjectsLoaded(List<BusinessCategory> objects) {

            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void setSectors(List<BusinessSector> sectors) {
        businessRepository.getSectors(new BusinessDataSource.GetObjectCallback<BusinessSector>() {
            @Override
            public void onObjectsLoaded(List<BusinessSector> objects) {

            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void setSubSectors(List<BusinessSubSector> subSectors) {
        businessRepository.getSubSectors(new BusinessDataSource.GetObjectCallback<BusinessSubSector>() {
            @Override
            public void onObjectsLoaded(List<BusinessSubSector> objects) {

            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void setStructures(List<BusinessStruture> structures) {
        businessRepository.getStructures(new BusinessDataSource.GetObjectCallback<BusinessStruture>() {
            @Override
            public void onObjectsLoaded(List<BusinessStruture> objects) {

            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void setPresenter(AddBusinessContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);

    }
}
