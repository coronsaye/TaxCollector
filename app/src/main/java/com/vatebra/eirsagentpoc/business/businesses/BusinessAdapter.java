package com.vatebra.eirsagentpoc.business.businesses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.vatebra.eirsagentpoc.R;
import com.vatebra.eirsagentpoc.domain.entity.Business;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by David Eti on 17/08/2017.
 */

public class BusinessAdapter extends BaseAdapter {
    private List<Business> mBusinesses;
    private List<Business> searchBusinessList = new ArrayList<>();
    private BusinessItemListener businessItemListener;
    private boolean isBusinessChooser;
    public Business selectedBusiness;

    public BusinessAdapter(List<Business> businesses, BusinessItemListener itemListener, boolean isBusinessChooser) {
        setList(businesses);
        businessItemListener = itemListener;
        this.isBusinessChooser = isBusinessChooser;
    }


    private void setList(List<Business> businesses) {
        mBusinesses = checkNotNull(businesses);
        searchBusinessList.clear();
        searchBusinessList.addAll(mBusinesses);
    }

    public void replaceData(List<Business> businesses) {
        setList(businesses);
        notifyDataSetChanged();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        searchBusinessList.clear();
        if (charText.length() == 0) {
            searchBusinessList.addAll(mBusinesses);
        } else {
            for (Business business : mBusinesses) {
                if (business.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    searchBusinessList.add(business);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return searchBusinessList.size();
    }

    @Override
    public Business getItem(int i) {
        return searchBusinessList.get(i);
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
            rowView = inflater.inflate(R.layout.item_business, viewGroup, false);
        }
        final Business business = getItem(i);
        TextView titleTextView = (TextView) rowView.findViewById(R.id.title);
        titleTextView.setText(business.getName());
        TextView subTitleTextView = (TextView) rowView.findViewById(R.id.subTitle);
        subTitleTextView.setText(business.getLga());

        CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.selectCheckbox);
        if (isBusinessChooser)
            checkBox.setVisibility(View.VISIBLE);

        TextView rinTextView = (TextView) rowView.findViewById(R.id.rinTextView);
        rinTextView.setText("#" + business.getRin());

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                businessItemListener.onBusinessClick(business);
            }
        });
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedBusiness = business;
            }
        });
        return rowView;
    }

    public interface BusinessItemListener {
        void onBusinessClick(Business clickedBusiness);
    }

}
