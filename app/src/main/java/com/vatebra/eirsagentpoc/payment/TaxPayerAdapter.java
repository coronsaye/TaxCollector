package com.vatebra.eirsagentpoc.payment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vatebra.eirsagentpoc.R;
import com.vatebra.eirsagentpoc.domain.entity.Bill;
import com.vatebra.eirsagentpoc.domain.entity.TaxPayer;

import java.util.List;

/**
 * Created by David Eti on 27/08/2017.
 */

public class TaxPayerAdapter extends BaseAdapter {

    List<Bill> bills;
    OnBillClickListener onBillClickListener;

    public TaxPayerAdapter(List<Bill> bills, OnBillClickListener onBillClickListener) {
        this.bills = bills;
        this.onBillClickListener = onBillClickListener;
    }

    @Override
    public int getCount() {
        return bills.size();
    }

    @Override
    public Bill getItem(int i) {
        return bills.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void removeBill(Bill bill){
        bills.remove(bill);
        notifyDataSetChanged();
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rowView = view;
        if (rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            rowView = inflater.inflate(R.layout.item_bills, viewGroup, false);
        }

        final Bill bill = getItem(i);
        TextView assetTitleTextView = (TextView) rowView.findViewById(R.id.assetTitle);
        assetTitleTextView.setText(bill.getAssetName());
        TextView ruleTextView = (TextView) rowView.findViewById(R.id.subTitle);
        ruleTextView.setText(bill.getRuleName());
        TextView dueDateTextView = (TextView) rowView.findViewById(R.id.dueDateTitle);
        dueDateTextView.setText("Due On - " + bill.getSettlementDueDate());
        TextView assessmentRefTextView = (TextView) rowView.findViewById(R.id.assessmentRefTextView);
        assessmentRefTextView.setText("#" + bill.getAssessmentRef());
        TextView paymentAmount = (TextView) rowView.findViewById(R.id.paymentAmount);
        paymentAmount.setText("PAY: " + bill.getAsssessmentAmount());


        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBillClickListener.onBillClicked(bill);
            }
        });

        return rowView;
    }


    public interface OnBillClickListener {

        void onBillClicked(Bill bill);
    }
}
