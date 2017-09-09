package com.vatebra.eirsagentpoc.dashboard;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;
import com.vatebra.eirsagentpoc.Injection;
import com.vatebra.eirsagentpoc.R;
import com.vatebra.eirsagentpoc.domain.entity.Business;
import com.vatebra.eirsagentpoc.domain.entity.TaxPayer;
import com.vatebra.eirsagentpoc.flowcontroller.FlowController;
import com.vatebra.eirsagentpoc.repository.BusinessRepository;
import com.vatebra.eirsagentpoc.repository.GlobalRepository;
import com.vatebra.eirsagentpoc.util.Constants;
import com.vatebra.eirsagentpoc.util.VatEventSharedHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.vatebra.eirsagentpoc.util.Constants.nairaSymbol;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

    @BindView(R.id.expanding_list_main)
    ExpandingList expandingList;
    GlobalRepository globalRepository;
    MaterialDialog dialogLoad;
    BusinessRepository businessRepository;

    @BindView(R.id.account_balance)
    TextView account_balance;
    VatEventSharedHelper helper;

    public DashboardFragment() {
        // Required empty public constructor
    }

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this, view);
        globalRepository = GlobalRepository.getInstance();
        businessRepository = Injection.providesBusinessRepository(getContext());
        helper = VatEventSharedHelper.getInstance(getContext());
        account_balance.setText("MY ACCOUNT: " + nairaSymbol + String.valueOf(helper.getAmount()));
        createDashboard();
        getAccountBalance();
        dialogLoad = new MaterialDialog.Builder(getContext())
                .title("Loading")
                .content("Please Wait...")
                .progress(true, 0)
                .progressIndeterminateStyle(true).build();
        return view;
    }

    private void createDashboard() {
        populateUi(getString(R.string.assets_title), new String[]{getString(R.string.business_title), getString(R.string.building_title)}, R.color.pink, R.drawable.ic_assets);
        populateUi(getString(R.string.tax_payer_title), new String[]{getString(R.string.individuals_title), getString(R.string.companies_title)}, R.color.green, R.drawable.ic_tax_payers);
        populateUi(getString(R.string.payment_title), null, R.color.purple, R.drawable.ic_payment);
    }

    private void populateUi(String menuTitle, String[] submenuItems, int colorRes, int iconRes) {
        final ExpandingItem item = expandingList.createNewItem(R.layout.dashboard_expanding_layout);
        if (item != null) {
            item.setIndicatorColorRes(colorRes);
            item.setIndicatorIconRes(iconRes);
            //Set the Main Menu Title
            ((TextView) item.findViewById(R.id.mainTextView)).setText(menuTitle);

            if (submenuItems != null) {
                item.createSubItems(submenuItems.length);

                for (int i = 0; i < item.getSubItemsCount(); i++) {
                    //Let's get the created sub item by its index
                    final View view = item.getSubItemView(i);

                    //Let's set some values in
                    configureSubItem(item, view, submenuItems[i]);
                }
            } else {
                if (!isAdded()) {
                    return;
                }
                //do something when item is clicked

                item.findViewById(R.id.mainTextView).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (((TextView) item.findViewById(R.id.mainTextView)).getText().equals(getString(R.string.payment_title))) {
                            new MaterialDialog.Builder(getContext())
                                    .title("View Tax Payer Taxes")
                                    .content("Kindly provide the TIN Number for the taxpayer")
                                    .inputType(InputType.TYPE_CLASS_TEXT)
                                    .input("Tin Number", "", new MaterialDialog.InputCallback() {
                                        @Override
                                        public void onInput(MaterialDialog dialog, CharSequence input) {
                                            if (!isAdded())
                                                return;

                                            if (TextUtils.isEmpty(input.toString())) {
                                                Toast.makeText(getContext(), "Ensure you provide TIN ", Toast.LENGTH_SHORT).show();
                                                return;
                                            }

                                            if (dialogLoad != null) {
                                                dialogLoad.show();
                                            }
                                            // Do something
                                            globalRepository.getTaxPayerByTin(input.toString(), new BusinessRepository.OnApiReceived<TaxPayer>() {
                                                @Override
                                                public void OnSuccess(TaxPayer data) {
                                                    if (!isAdded())
                                                        return;
                                                    if (dialogLoad != null & dialogLoad.isShowing()) {
                                                        dialogLoad.hide();
                                                    }
                                                    FlowController.launchTaxPayerActivity(getContext(), data);
                                                }

                                                @Override
                                                public void OnFailed(String message) {
                                                    if (!isAdded())
                                                        return;
                                                    if (dialogLoad != null & dialogLoad.isShowing()) {
                                                        dialogLoad.hide();
                                                    }
                                                    Snackbar.make(item, message, Snackbar.LENGTH_LONG).show();
                                                }
                                            });
                                        }
                                    }).show();
                        }
                    }
                });

            }
        }
    }

    private void configureSubItem(final ExpandingItem item, final View view, String subTitle) {
        final TextView subItemTextView = (TextView) view.findViewById(R.id.subItemTextView);
        subItemTextView.setText(subTitle);


        subItemTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (subItemTextView.getText().equals(getString(R.string.business_title)))
                    FlowController.launchBusinessActivity(getContext());
                else if (subItemTextView.getText().equals(getString(R.string.individuals_title)))
                    FlowController.launchIndividualActivity(getContext());
                else if (subItemTextView.getText().equals(getString(R.string.companies_title)))
                    FlowController.launchCompanyActivity(getContext());
                else if (subItemTextView.getText().equals(getString(R.string.building_title)))
                    FlowController.launchBuildingActivity(getContext());


                //go to respective View
            }
        });
        view.findViewById(R.id.add_sub_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //addItem
                if (subItemTextView.getText().equals(getString(R.string.business_title)))
                    LaunchAddBusiness();
                else if (subItemTextView.getText().equals(getString(R.string.individuals_title)))
                    FlowController.launchAddEditIndividualActivity(getContext());
                else if (subItemTextView.getText().equals(getString(R.string.companies_title)))
                    FlowController.launchAddEditCompanyActivity(getContext());
                else if (subItemTextView.getText().equals(getString(R.string.building_title)))
                    FlowController.launchAddEditBuildingActivity(getContext());

            }
        });
    }


    private void LaunchAddBusiness() {
        new MaterialDialog.Builder(getContext())
                .title("Add Business Using RIN")
                .content("Kindly provide  below the Business RIN Number for the asset if available")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("BUSINESS RIN", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        if (!isAdded())
                            return;
                        if (TextUtils.isEmpty(input.toString())) {
                            Toast.makeText(getContext(), "Ensure you provide a RIN ", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (dialogLoad != null) {
                            dialogLoad.show();
                        }

                        businessRepository.getBusinessByRin(input.toString(), new BusinessRepository.OnApiReceived<Business>() {
                            @Override
                            public void OnSuccess(Business data) {
                                if (!isAdded())
                                    return;
                                if (dialogLoad != null & dialogLoad.isShowing()) {
                                    dialogLoad.hide();
                                }

                                FlowController.launchBuildingActivity(getContext(), true, data);

                            }

                            @Override
                            public void OnFailed(String message) {
                                if (!isAdded())
                                    return;
                                if (dialogLoad != null & dialogLoad.isShowing()) {
                                    dialogLoad.hide();
                                }
//                                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                                Snackbar.make(expandingList, message, Snackbar.LENGTH_INDEFINITE).setAction("New Building", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        FlowController.launchBuildingActivity(getContext(), true);
                                    }
                                }).show();
                            }
                        });

                    }
                }).positiveText("Continue").negativeText("No Rin Provided").onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                //TIN NOT PROVIDED
//                FlowController.launchAddEditBusinessActivity(getContext());
                FlowController.launchBuildingActivity(getContext(), true);
            }
        }).show();
    }

    private void getAccountBalance() {
        globalRepository.getAgentAccountBalance(Constants.userId, new BusinessRepository.OnApiReceived<String>() {
            @Override
            public void OnSuccess(String data) {
                if (!isAdded())
                    return;
                try {
                    Double amount = Double.parseDouble(data);
                    helper.saveAmount(amount);
                    account_balance.setText("MY ACCOUNT: " + nairaSymbol + String.valueOf(helper.getAmount()));
                } catch (Exception e) {
                    Log.e("getAccountBalance", "OnSuccess: ", e);
                }

            }

            @Override
            public void OnFailed(String message) {

            }
        });
    }
}
