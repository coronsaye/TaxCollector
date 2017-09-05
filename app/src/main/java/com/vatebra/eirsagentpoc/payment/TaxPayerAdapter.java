package com.vatebra.eirsagentpoc.payment;

import android.text.Html;
import android.text.Spanned;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vatebra.eirsagentpoc.R;
import com.vatebra.eirsagentpoc.domain.entity.Bill;
import com.vatebra.eirsagentpoc.domain.entity.TaxPayer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by David Eti on 27/08/2017.
 */

public class TaxPayerAdapter extends BaseAdapter {

    List<Bill> bills;
    OnBillClickListener onBillClickListener;
    Spanned nairaSymbol = Html.fromHtml("&#8358");

    public TaxPayerAdapter(List<Bill> bills, OnBillClickListener onBillClickListener) {
        this.bills = bills;
        this.onBillClickListener = onBillClickListener;
    }

    @Override
    public int getCount() {
        return (bills.size());
    }

    @Override
    public Bill getItem(int i) {
        return bills.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void removeBill(Bill bill) {
        bills.remove(bill);
        notifyDataSetChanged();
    }

    public void UpdateBill(Bill bill, int position) {
        Bill oldBill = getItem(position);
        // TODO: 05/09/2017 SUBTRACT THE BILL AMOUNT FROM THE OLD BILL IN THE CASE OF PARTIAL
        bills.remove(position);
        notifyDataSetChanged();
        bills.add(position, bill);
        notifyDataSetChanged();

    }


    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
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

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());

        try {
            String value = bill.getSettlementDueDate();
            Date date = format.parse(value);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            String dateString = String.valueOf(bill.getTaxYear() + "/" + calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault())) + "/" + String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
            dueDateTextView.setText("Due On - " + dateString);

        } catch (Exception ex) {
            dueDateTextView.setText("Due On - " + bill.getSettlementDueDate());

        }

        TextView assessmentRefTextView = (TextView) rowView.findViewById(R.id.assessmentRefTextView);
        assessmentRefTextView.setText("#" + bill.getAssessmentRef());
        TextView paymentAmount = (TextView) rowView.findViewById(R.id.paymentAmount);
        paymentAmount.setText("PAY: " + nairaSymbol + bill.getAsssessmentAmount());


        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBillClickListener.onBillClicked(bill, i);
            }
        });

        return rowView;
    }


    public interface OnBillClickListener {

        void onBillClicked(Bill bill, int position);
    }
}
