package com.vatebra.eirsagentpoc.taxpayers.companies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.vatebra.eirsagentpoc.R;
import com.vatebra.eirsagentpoc.domain.entity.Company;
import com.vatebra.eirsagentpoc.domain.entity.Individual;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Collins Oronsaye on 23/08/2017.
 */

public class CompanyAdapter extends BaseAdapter {

    private List<Company> companies;
    private List<Company> searchCompanies = new ArrayList<>();
    public Company selectedCompany;
    private CompanyItemListener companyItemListener;
    private boolean isCompanyChooser;

    public CompanyAdapter(List<Company> companies, CompanyItemListener companyItemListener, boolean isCompanyChooser) {
        setList(companies);
        this.companyItemListener = companyItemListener;
        this.isCompanyChooser = isCompanyChooser;
    }

    private void setList(List<Company> companies) {
        this.companies = checkNotNull(companies);
        searchCompanies.clear();
        searchCompanies.addAll(this.companies);
    }

    public void replaceData(List<Company> companies) {
        setList(companies);
        notifyDataSetChanged();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        searchCompanies.clear();
        if (charText.length() == 0) {
            searchCompanies.addAll(companies);
        } else {
            for (Company company : companies) {
                if (company.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    searchCompanies.add(company);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return searchCompanies.size();
    }

    @Override
    public Company getItem(int i) {
        return searchCompanies.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rowView = view;
        if (rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            rowView = inflater.inflate(R.layout.item_company, viewGroup, false);
        }

        final Company company = getItem(i);
        TextView titleTextView = (TextView) rowView.findViewById(R.id.title);
        TextView taxOfficeTextView = (TextView) rowView.findViewById(R.id.subTitle);
        TextView phoneTextView = (TextView) rowView.findViewById(R.id.bodyTitle);
        TextView rinTextView = (TextView) rowView.findViewById(R.id.rinTextView);


        CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.selectCheckbox);
        if (isCompanyChooser)
            checkBox.setVisibility(View.VISIBLE);

        titleTextView.setText(company.getName());
        taxOfficeTextView.setText(company.getTaxOfficeName());
        phoneTextView.setText(company.getPhoneNo());
        rinTextView.setText("#" + company.getRin());
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                companyItemListener.onCompanyClick(company);
            }
        });
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedCompany = company;
            }
        });
        return rowView;
    }

    public interface CompanyItemListener {
        void onCompanyClick(Company company);
    }


}
