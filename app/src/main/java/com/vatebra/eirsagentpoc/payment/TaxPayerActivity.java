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
import android.util.Log;
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
import com.vatebra.eirsagentpoc.util.Constants;
import com.vatebra.eirsagentpoc.util.VatEventSharedHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.vatebra.eirsagentpoc.util.Constants.formatStringToNaira;
import static com.vatebra.eirsagentpoc.util.Constants.nairaSymbol;

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

    List<String> paymentOptions;
    VatEventSharedHelper helper;
    int selectedBillPosition;
    double oldBillAmount;
    double amountToBePaid;

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
        accountText.setVisibility(View.INVISIBLE);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setTitle("Manage Bills");
            double amount = helper.getAmount();
//            getSupportActionBar().setTitle("My Account: " + nairaSymbol + amount);
            getSupportActionBar().setTitle(("My Account: " + formatStringToNaira(amount)));

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

    private void providePaymentOptions(final Bill bill, final boolean isFullPayment) {
        paymentOptions = new ArrayList<>();

        paymentOptions.add("Scratch Card");
        paymentOptions.add("ATM Debit Card");
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
                                ScratchCardDialog(bill);
                                break;

                            case 1:
                                AtmFragment atmFragment = AtmFragment.newInstance(bill, isFullPayment, amountToBePaid);
                                ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), atmFragment, R.id.contentFrame);
                                break;
                            case 2:
                                break;
                            case 3:
                                payWithAgentBank(bill, isFullPayment);
                                break;
                        }
                        return true;
                    }
                })
                .positiveText("Continue")
                .show();
    }

    private void paymentTypeSelection(final Bill bill) {
        new MaterialDialog.Builder(this)
                .title("Partial/Full Payment")
                .content("To proceed with partial payment, input amount to be paid below and press Continue, else choose " +
                        "the full payment option.")
                .inputType(InputType.TYPE_NUMBER_FLAG_DECIMAL)
                .input("AMOUNT", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        if (TextUtils.isEmpty(input.toString())) {
                            Toast.makeText(TaxPayerActivity.this, "Ensure you provide an amount", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Double partialAmount = Double.parseDouble(input.toString());
                        oldBillAmount = bill.getAsssessmentAmount();
                        amountToBePaid = partialAmount;
//                        bill.setAsssessmentAmount(partialAmount);
                        providePaymentOptions(bill, false);
                    }
                }).positiveText("Continue").negativeText("Full Payment").onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                providePaymentOptions(bill, true);
            }
        }).show();
    }

    private void populateFields() {

//        accountText.setText("Taxpayer Account: " + nairaSymbol + taxPayer.getAccountBalance());
        accountText.setText("Taxpayer Account: " + formatStringToNaira(taxPayer.getAccountBalance()));
        taxpayerTextView.setText(taxPayer.getTaxPayerName());
        tinTextView.setText(taxPayer.getTIN());
        if (taxPayer.getBills() != null) {
            taxPayerAdapter = new TaxPayerAdapter(taxPayer.getBills(), new TaxPayerAdapter.OnBillClickListener() {
                @Override
                public void onBillClicked(final Bill bill, int position) {
                    //pay bill
                    selectedBillPosition = position;
                    paymentTypeSelection(bill);

                }
            });
        }


        listView.setAdapter(taxPayerAdapter);
    }

    private void payWithAgentBank(final Bill bill, final boolean isFullPayment) {
        int toBePaid = (int) amountToBePaid;
        globalRepository.deductFromAgentAccount(Constants.userId, toBePaid, new BusinessRepository.OnApiReceived<String>() {
            @Override
            public void OnSuccess(String data) {
                helper.removeAmount(amountToBePaid);
                payBillWithAtm(bill, true, isFullPayment);

                try {
                    Double amount = Double.parseDouble(data);
                    helper.saveAmount(amount);
                } catch (Exception e) {
                    Log.e("getAccountBalance", "OnSuccess: ", e);
                }
            }

            @Override
            public void OnFailed(String message) {
                Snackbar.make(toolbar, message, Snackbar.LENGTH_LONG).show();
            }
        });
    }

//    private void showAgreementDialog(final Bill bill) {
//        new MaterialDialog.Builder(TaxPayerActivity.this)
//                .title("Pay Bill?")
//                .content("You are about to pay the bill for " + bill.getAssetName() + "'s " + bill.getRuleName() + " would you like to proceed")
//                .positiveText("Agree")
//                .showListener(new DialogInterface.OnShowListener() {
//                    @Override
//                    public void onShow(DialogInterface dialog) {
//
//
//                    }
//                })
//                .cancelListener(new DialogInterface.OnCancelListener() {
//                    @Override
//                    public void onCancel(DialogInterface dialog) {
//                    }
//                })
//                .dismissListener(new DialogInterface.OnDismissListener() {
//                    @Override
//                    public void onDismiss(DialogInterface dialog) {
//                    }
//                }).onPositive(new MaterialDialog.SingleButtonCallback() {
//            @Override
//            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                payBill(bill);
//            }
//        })
//                .show();
//    }

//    private void payBill(final Bill bill) {
//        if (dialogLoad != null) {
//            dialogLoad.show();
//        }
//
//        globalRepository.payBill(bill.getAssessmentID(), bill.getAsssessmentAmount(), taxPayer.getTIN(), new BusinessRepository.OnApiReceived<String>() {
//            @Override
//            public void OnSuccess(String data) {
//                if (dialogLoad != null & dialogLoad.isShowing()) {
//                    dialogLoad.hide();
//                }
//
//                Snackbar.make(toolbar, "Successfully paid bill", Snackbar.LENGTH_LONG).show();
//                accountText.setText("Taxpayer Account: " + nairaSymbol + data);
//                taxPayerAdapter.removeBill(bill);
//            }
//
//            @Override
//            public void OnFailed(String message) {
//                if (dialogLoad != null & dialogLoad.isShowing()) {
//                    dialogLoad.hide();
//                }
//                Snackbar.make(toolbar, message, Snackbar.LENGTH_LONG).show();
//            }
//        });
//    }

    private void payBillWithAtm(final Bill bill, final boolean isAgentWallet, final boolean isFullPayment) {
        if (dialogLoad != null) {
            dialogLoad.show();
        }

        if (isFullPayment) {
            globalRepository.payBillWithAtm(bill.getAssessmentID(), amountToBePaid, taxPayer.getTIN(), new BusinessRepository.OnApiReceived<String>() {
                @Override
                public void OnSuccess(String data) {
                    if (dialogLoad != null & dialogLoad.isShowing()) {
                        dialogLoad.hide();
                    }

                    Snackbar.make(toolbar, "Successfully paid bill", Snackbar.LENGTH_LONG).show();
                    taxPayerAdapter.removeBill(bill);
                    if (isAgentWallet) {
                        double amount = helper.getAmount();
                        if (getSupportActionBar() != null) {
                            getSupportActionBar().setTitle(("My Account: " + formatStringToNaira(amount)));

                        }
//                            getSupportActionBar().setTitle("My Account: " + nairaSymbol + amount);

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
        } else {
            globalRepository.partialPayment(bill.getAssessmentID(), bill.getAsssessmentAmount(), taxPayer.getTIN(), amountToBePaid, new BusinessRepository.OnApiReceived<String>() {
                @Override
                public void OnSuccess(String data) {
                    if (dialogLoad != null & dialogLoad.isShowing()) {
                        dialogLoad.hide();
                    }

                    Snackbar.make(toolbar, "Successfully paid bill", Snackbar.LENGTH_LONG).show();
                    if (bill.getAmountLeft() == 0.0) {
                        taxPayerAdapter.removeBill(bill);

                    } else {
                        bill.setAmountPaid(bill.getAmountPaid() + amountToBePaid);
                        bill.setAsssessmentAmount(oldBillAmount);
                        taxPayerAdapter.UpdateBill(bill, selectedBillPosition);
                    }

                    if (isAgentWallet) {
                        double amount = helper.getAmount();
                        if (getSupportActionBar() != null)
//                            getSupportActionBar().setTitle("My Account: " + nairaSymbol + amount);
                            getSupportActionBar().setTitle(("My Account: " + formatStringToNaira(amount)));

                    }
                }

                @Override
                public void OnFailed(String message) {
                    if (dialogLoad != null & dialogLoad.isShowing()) {
                        dialogLoad.hide();
                    }
                    bill.setAsssessmentAmount(oldBillAmount);
                    Snackbar.make(toolbar, message, Snackbar.LENGTH_LONG).show();
                }
            });
        }

    }

    private void ScratchCardDialog(final Bill bill) {
        new MaterialDialog.Builder(TaxPayerActivity.this)
                .title("Scratch Card Payment")
                .content("Provide the scratch card pin for payment")
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
                        globalRepository.ScratchCard(input.toString(), taxPayer.getTIN(), bill.getAssessmentID(), new BusinessRepository.OnApiReceived<String>() {
                            @Override
                            public void OnSuccess(String data) {
                                if (dialogLoad != null & dialogLoad.isShowing()) {
                                    dialogLoad.hide();
                                }
                                double amountPaid = Double.parseDouble(data);
                                if (bill.getAsssessmentAmount() > amountPaid) {
                                    bill.setAmountPaid(amountPaid);
//                                    bill.setAsssessmentAmount(oldBillAmount);
                                    taxPayerAdapter.UpdateBill(bill, selectedBillPosition);
                                } else {
                                    taxPayerAdapter.removeBill(bill);
                                }
                                Snackbar.make(toolbar, "paid " + data + " successfully", Snackbar.LENGTH_LONG).show();

                            }

                            @Override
                            public void OnFailed(String message) {
                                if (dialogLoad != null & dialogLoad.isShowing()) {
                                    dialogLoad.hide();
                                }
                                Snackbar.make(toolbar, message, Snackbar.LENGTH_LONG).show();
                            }
                        });

//                        globalRepository.topUp(input.toString(), taxPayer.getTIN(), new BusinessRepository.OnApiReceived<String>() {
//                            @Override
//                            public void OnSuccess(String data) {
//                                if (dialogLoad != null & dialogLoad.isShowing()) {
//                                    dialogLoad.hide();
//                                }
//
//                                Snackbar.make(toolbar, "New Balance: " + data, Snackbar.LENGTH_LONG).show();
//                                accountText.setText("Taxpayer Account: " + data);
//
//                            }
//
//                            @Override
//                            public void OnFailed(String message) {
//                                if (dialogLoad != null & dialogLoad.isShowing()) {
//                                    dialogLoad.hide();
//                                }
//                                Snackbar.make(toolbar, message, Snackbar.LENGTH_LONG).show();
//
//                            }
//                        });
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
    public void OnSuccessTransaction(Bill bill, boolean isFullPayment) {

//        Snackbar.make(toolbar, "You have successfully payed for: " + bill.getRuleName(), Snackbar.LENGTH_LONG).show();
        payBillWithAtm(bill, false, isFullPayment);
    }

    @Override
    public void OnFailedTransaction(String message) {
        Snackbar.make(toolbar, message, Snackbar.LENGTH_LONG).show();

    }
}

