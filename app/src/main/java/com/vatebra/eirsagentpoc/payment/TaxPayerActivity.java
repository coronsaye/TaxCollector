package com.vatebra.eirsagentpoc.payment;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.vatebra.eirsagentpoc.R;
import com.vatebra.eirsagentpoc.domain.entity.Bill;
import com.vatebra.eirsagentpoc.domain.entity.TaxPayer;
import com.vatebra.eirsagentpoc.flowcontroller.FlowController;
import com.vatebra.eirsagentpoc.repository.BusinessRepository;
import com.vatebra.eirsagentpoc.repository.GlobalRepository;
import com.vatebra.eirsagentpoc.util.ActivityUtils;
import com.vatebra.eirsagentpoc.util.VatEventSharedHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaxPayerActivity extends AppCompatActivity implements AtmFragment.OnTransactionListener {


    public static final String EXTRA_TAXPAYER = "TAX_PAYER";

    TaxPayer taxPayer;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.taxpayerTextView)
    TextView taxpayerTextView;


    @BindView(R.id.tinTextView)
    TextView tinTextView;

    @BindView(R.id.assesment_list)
    ListView listView;


    @BindView(R.id.accountText)
    TextView accountText;

    @BindView(R.id.button_topUp)
    FloatingActionButton button_topUp;

    MaterialDialog dialogLoad;

    private GlobalRepository globalRepository;
    TaxPayerAdapter taxPayerAdapter;

    Spanned nairaSymbol = Html.fromHtml("&#8358");
    List<String> paymentOptions;
    VatEventSharedHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tax_payer);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        globalRepository = GlobalRepository.getInstance();

        dialogLoad = new MaterialDialog.Builder(this)
                .title("Loading")
                .content("Please Wait...")
                .progress(true, 0)
                .progressIndeterminateStyle(true).build();
        helper = VatEventSharedHelper.getInstance(getApplicationContext());

        taxPayer = (TaxPayer) getIntent().getSerializableExtra(EXTRA_TAXPAYER);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setTitle("Manage Bills");
            double amount = helper.getAmount();
            getSupportActionBar().setTitle("My Account: " + nairaSymbol + amount);
            getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        }

//        button_topUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                topUp();
//                providePaymentOptions();
//            }
//        });

        populateFields();
    }

    private void providePaymentOptions(final Bill bill) {
        paymentOptions = new ArrayList<>();

        paymentOptions.add("Scratch Card");
        paymentOptions.add("ATM");
        paymentOptions.add("POS");
        paymentOptions.add("Agent Wallet");
        new MaterialDialog.Builder(this)
                .title("Kindly Choose A Payment Option")
                .items(paymentOptions)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        /**
                         * If you use alwaysCallSingleChoiceCallback(), which is discussed below,
                         * returning false here won't allow the newly selected radio button to actually be selected.
                         **/

                        switch (which) {
                            case 0:
                                topUp();
                                break;

                            case 1:
                                AtmFragment atmFragment = AtmFragment.newInstance(bill);
                                ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), atmFragment, R.id.contentFrame);
                                break;
                            case 2:
                                break;
                            case 3:
                                payWithAgentBank(bill);
                                break;
                        }
                        return true;
                    }
                })
                .positiveText("Continue")
                .show();
    }

    private void populateFields() {

        accountText.setText("Taxpayer Account: " + nairaSymbol + taxPayer.getAccountBalance());

        taxpayerTextView.setText(taxPayer.getTaxPayerName());
        tinTextView.setText(taxPayer.getTIN());
        if(taxPayer.getBills() != null){
            taxPayerAdapter = new TaxPayerAdapter(taxPayer.getBills(), new TaxPayerAdapter.OnBillClickListener() {
                @Override
                public void onBillClicked(final Bill bill) {
                    //pay bill
                    providePaymentOptions(bill);

                }
            });
        }


        listView.setAdapter(taxPayerAdapter);
    }

    private void payWithAgentBank(Bill bill) {
        helper.removeAmount(bill.getAsssessmentAmount());
        payBillWithAtm(bill, true);
    }

    private void showAgreementDialog(final Bill bill) {
        new MaterialDialog.Builder(TaxPayerActivity.this)
                .title("Pay Bill?")
                .content("You are about to pay the bill for " + bill.getAssetName() + "'s " + bill.getRuleName() + " would you like to proceed")
                .positiveText("Agree")
                .showListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {


                    }
                })
                .cancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                    }
                })
                .dismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                    }
                }).onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                payBill(bill);
            }
        })
                .show();
    }

    private void payBill(final Bill bill) {
        if (dialogLoad != null) {
            dialogLoad.show();
        }

        globalRepository.payBill(bill.getAssessmentID(), bill.getAsssessmentAmount(), taxPayer.getTIN(), new BusinessRepository.OnApiReceived<String>() {
            @Override
            public void OnSuccess(String data) {
                if (dialogLoad != null & dialogLoad.isShowing()) {
                    dialogLoad.hide();
                }

                Snackbar.make(toolbar, "Successfully paid bill", Snackbar.LENGTH_LONG).show();
                accountText.setText("Taxpayer Account: " + nairaSymbol + data);
                taxPayerAdapter.removeBill(bill);
            }

            @Override
            public void OnFailed(String message) {
                if (dialogLoad != null & dialogLoad.isShowing()) {
                    dialogLoad.hide();
                }
                Snackbar.make(toolbar, message, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void payBillWithAtm(final Bill bill, final boolean isAgentWallet) {
        if (dialogLoad != null) {
            dialogLoad.show();
        }

        globalRepository.payBillWithAtm(bill.getAssessmentID(), bill.getAsssessmentAmount(), taxPayer.getTIN(), new BusinessRepository.OnApiReceived<String>() {
            @Override
            public void OnSuccess(String data) {
                if (dialogLoad != null & dialogLoad.isShowing()) {
                    dialogLoad.hide();
                }

                Snackbar.make(toolbar, "Successfully paid bill", Snackbar.LENGTH_LONG).show();
                taxPayerAdapter.removeBill(bill);
                if (isAgentWallet) {
                    double amount = helper.getAmount();
                    if (getSupportActionBar() != null)
                        getSupportActionBar().setTitle("My Account: " + nairaSymbol + amount);
                }
            }

            @Override
            public void OnFailed(String message) {
                if (dialogLoad != null & dialogLoad.isShowing()) {
                    dialogLoad.hide();
                }
                Snackbar.make(toolbar, message, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void topUp() {
        new MaterialDialog.Builder(TaxPayerActivity.this)
                .title("Top Up")
                .content("Provide the scratch card pin to top up your Account")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("Scratch Card", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {

                        if (dialogLoad != null) {
                            dialogLoad.show();
                        }
                        if (TextUtils.isEmpty(input.toString())) {
                            Toast.makeText(TaxPayerActivity.this, "Ensure you provide PIN ", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        globalRepository.topUp(input.toString(), taxPayer.getTIN(), new BusinessRepository.OnApiReceived<String>() {
                            @Override
                            public void OnSuccess(String data) {
                                if (dialogLoad != null & dialogLoad.isShowing()) {
                                    dialogLoad.hide();
                                }

                                Snackbar.make(toolbar, "New Balance: " + data, Snackbar.LENGTH_LONG).show();
                                accountText.setText("Taxpayer Account: " + data);

                            }

                            @Override
                            public void OnFailed(String message) {
                                if (dialogLoad != null & dialogLoad.isShowing()) {
                                    dialogLoad.hide();
                                }
                                Snackbar.make(toolbar, message, Snackbar.LENGTH_LONG).show();

                            }
                        });
                    }
                }).show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnSuccessTransaction(Bill bill) {

//        Snackbar.make(toolbar, "You have successfully payed for: " + bill.getRuleName(), Snackbar.LENGTH_LONG).show();
        payBillWithAtm(bill, false);
    }

    @Override
    public void OnFailedTransaction(String message) {
        Snackbar.make(toolbar, message, Snackbar.LENGTH_LONG).show();

    }
}

