package com.vatebra.eirsagentpoc.payment;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaxPayerActivity extends AppCompatActivity {


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
    Button button_topUp;

    MaterialDialog dialogLoad;

    private GlobalRepository globalRepository;
    TaxPayerAdapter taxPayerAdapter;

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

        taxPayer = (TaxPayer) getIntent().getSerializableExtra(EXTRA_TAXPAYER);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Manage Bills");
        }

        button_topUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topUp();
            }
        });

        populateFields();
    }

    private void populateFields() {
        accountText.setText("Account: " + taxPayer.getAccountBalance());

        taxpayerTextView.setText(taxPayer.getTaxPayerName());
        tinTextView.setText(taxPayer.getTIN());
        taxPayerAdapter = new TaxPayerAdapter(taxPayer.getBills(), new TaxPayerAdapter.OnBillClickListener() {
            @Override
            public void onBillClicked(final Bill bill) {
                //pay bill
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
        });

        listView.setAdapter(taxPayerAdapter);
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
                accountText.setText("Account: " + data);
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

    private void topUp() {
        new MaterialDialog.Builder(TaxPayerActivity.this)
                .title("Top Up")
                .content("Kindly provide the scratch card pin to top up your Account")
                .inputType(InputType.TYPE_TEXT_VARIATION_PASSWORD)
                .input("Scratch Card", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {

                        if (dialogLoad != null) {
                            dialogLoad.show();
                        }


                        globalRepository.topUp(input.toString(), taxPayer.getTIN(), new BusinessRepository.OnApiReceived<String>() {
                            @Override
                            public void OnSuccess(String data) {
                                if (dialogLoad != null & dialogLoad.isShowing()) {
                                    dialogLoad.hide();
                                }

                                Snackbar.make(toolbar, "New Balance: " + data, Snackbar.LENGTH_LONG).show();
                                accountText.setText("Account: " + data);

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
}

