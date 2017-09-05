package com.vatebra.eirsagentpoc.payment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatEditText;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.vatebra.eirsagentpoc.R;
import com.vatebra.eirsagentpoc.domain.entity.Bill;

import org.json.JSONException;

import co.paystack.android.Paystack;
import co.paystack.android.PaystackSdk;
import co.paystack.android.Transaction;
import co.paystack.android.model.Card;
import co.paystack.android.model.Charge;

import static android.text.TextUtils.isEmpty;

/**
 * Created by David Eti on 30/08/2017.
 */

public class AtmFragment extends Fragment implements View.OnClickListener {

    Button mButtonPerformTransaction;
    AppCompatEditText mEditCardNum;
    TextInputEditText mEditCVC;
    TextInputEditText mEditExpiryMonth;
    TextInputEditText mEditExpiryYear;
    Card card;
    private Charge charge;
    private Transaction transaction;
    String agentEmail = "david3ti@gmail.com";
    Bill bill;
    private static String ARG_BILL = "argbill";
    private static String ARG_ISFULLPAYMENT = "argisfullpayment";

    private OnTransactionListener mListener;
    MaterialDialog dialogLoad;
    Boolean isFullPayment;

    public AtmFragment() {

    }

    public static AtmFragment newInstance(Bill bill, boolean isFullPayment) {
        AtmFragment fragment = new AtmFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_BILL, bill);
        args.putBoolean(ARG_ISFULLPAYMENT, isFullPayment);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bill = (Bill) getArguments().getSerializable(ARG_BILL);
            isFullPayment = getArguments().getBoolean(ARG_ISFULLPAYMENT);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.payment_layout, container, false);
        mEditCardNum = (AppCompatEditText) view.findViewById(R.id.edit_card_number);
        mEditCVC = (TextInputEditText) view.findViewById(R.id.edit_cvc);
        mEditExpiryMonth = (TextInputEditText) view.findViewById(R.id.edit_expiry_month);
        mEditExpiryYear = (TextInputEditText) view.findViewById(R.id.edit_expiry_year);
        mButtonPerformTransaction = (Button) view.findViewById(R.id.button_perform_transaction);
        dialogLoad = new MaterialDialog.Builder(getContext())
                .title("Loading")
                .content("Please Wait...")
                .progress(true, 0)
                .progressIndeterminateStyle(true).build();
        mButtonPerformTransaction.setOnClickListener(this);
        return view;
    }

    private void validateTransactionForm(Bill bill) {
        validateCardForm();
        charge = new Charge();
        charge.setCard(card);

        try {
            charge.putCustomField("Paid Via", "Android SDK");
            charge.putMetadata("Cart ID", Integer.toString(299390));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //validate fields
        String email = agentEmail;

        if (isEmpty(email)) {
            Toast.makeText(getContext(), "No valid email provided", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getContext(), "No valid email provided", Toast.LENGTH_SHORT).show();
            return;
        }

        charge.setEmail(email);

        Double amountInDouble = bill.getAsssessmentAmount();

        int amount = -1;
        try {
            amount = amountInDouble.intValue() * 100;//convert to kobo
        } catch (Exception ignored) {
        }

        if (amount < 1) {
            mEditExpiryMonth.setError("Invalid amount");
            return;
        }

        charge.setAmount(amount);
        // Remember to use a unique reference from your server each time.
        // You may decide not to set a reference, we will provide a value
        // in that case
        //  charge.setReference("7073397683");

        // OUR SDK is Split Payments Aware
        // You may also set a subaccount, transaction_charge and bearer
        // Remember that only when a subaccount is provided will the rest be used


        // OUR SDK is Plans Aware, and MultiCurrency Aware
        // You may also set a currency and plan
        // charge.setPlan("PLN_waiagu1thcyiebp");
        //        .addParameter("invoice_limit",7)
        //        .setCurrency("USD");

        // You can add additional parameters to the transaction
        // Our documentation will give details on those we accept.
        // charge.addParameter("someBetaParam","Its value");
    }

    /**
     * Method to validate the form, and set errors on the edittexts.
     */
    private void validateCardForm() {
        //validate fields
        String cardNum = mEditCardNum.getText().toString().trim();

        if (isEmpty(cardNum)) {
            mEditCardNum.setError("Empty card number");
            return;
        }

        //build card object with ONLY the number, update the other fields later
        card = new Card.Builder(cardNum, 0, 0, "").build();
        if (!card.validNumber()) {
            mEditCardNum.setError("Invalid card number");
            return;
        }

        //validate cvc
        String cvc = mEditCVC.getText().toString().trim();
        if (isEmpty(cvc)) {
            mEditCVC.setError("Empty cvc");
            return;
        }
        //update the cvc field of the card
        card.setCvc(cvc);

        //check that it's valid
        if (!card.validCVC()) {
            mEditCVC.setError("Invalid cvc");
            return;
        }

        //validate expiry month;
        String sMonth = mEditExpiryMonth.getText().toString().trim();
        int month = -1;
        try {
            month = Integer.parseInt(sMonth);
        } catch (Exception ignored) {
        }

        if (month < 1) {
            mEditExpiryMonth.setError("Invalid month");
            return;
        }

        card.setExpiryMonth(month);

        String sYear = mEditExpiryYear.getText().toString().trim();
        int year = -1;
        try {
            year = Integer.parseInt(sYear);
        } catch (Exception ignored) {
        }

        if (year < 1) {
            mEditExpiryYear.setError("invalid year");
            return;
        }

        card.setExpiryYear(year);

        //validate expiry
        if (!card.validExpiryDate()) {
            mEditExpiryMonth.setError("Invalid expiry");
            mEditExpiryYear.setError("Invalid expiry");
        }
    }

    @Override
    public void onClick(View view) {
        validateTransactionForm(bill);
        chargeCard(bill);
    }


    private void chargeCard(final Bill bill) {
        if (dialogLoad != null) {
            dialogLoad.show();
        }

        transaction = null;
        PaystackSdk.chargeCard(getActivity(), charge, new Paystack.TransactionCallback() {
            @Override
            public void onSuccess(Transaction transaction) {

                if (!isAdded())
                    return;
                // This is called only after transaction is successful
                if (dialogLoad != null & dialogLoad.isShowing()) {
                    dialogLoad.hide();
                }

                AtmFragment.this.transaction = transaction;
                if (mListener != null && bill != null) {
                    mListener.OnSuccessTransaction(bill, isFullPayment);
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    FragmentTransaction trans = manager.beginTransaction();
                    trans.remove(AtmFragment.this);
                    trans.commit();
                    manager.popBackStack();
                } else {
                    Toast.makeText(getContext(), "Bill Payment Failed", Toast.LENGTH_LONG).show();
//                    getActivity().onBackPressed();

                }
                //send transaction ref to server
            }

            @Override
            public void beforeValidate(Transaction transaction) {
                // This is called only before requesting OTP
                // Save reference so you may send to server if
                // error occurs with OTP
                // No need to dismiss dialog
                if (dialogLoad != null & dialogLoad.isShowing()) {
                    dialogLoad.hide();
                }

                AtmFragment.this.transaction = transaction;
                Toast.makeText(getContext(), transaction.getReference(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onError(Throwable error, Transaction transaction) {

                if (!isAdded())
                    return;

                if (dialogLoad != null & dialogLoad.isShowing()) {
                    dialogLoad.hide();
                }

                if (transaction.getReference() != null) {
                    if (mListener != null)
                        mListener.OnFailedTransaction(transaction.getReference() + " concluded with error: " + error.getMessage());

                } else {
                    if (mListener != null)
                        mListener.OnFailedTransaction(error.getMessage());

                }
                //send failed transaction to api

            }

        });
    }

    public interface OnTransactionListener {

        void OnSuccessTransaction(Bill bill, boolean isFullPayment);

        void OnFailedTransaction(String message);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTransactionListener) {
            mListener = (OnTransactionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnTransactionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
