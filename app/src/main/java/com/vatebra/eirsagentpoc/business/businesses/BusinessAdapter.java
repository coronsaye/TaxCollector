package com.vatebra.eirsagentpoc.business.businesses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vatebra.eirsagentpoc.R;
import com.vatebra.eirsagentpoc.business.domain.entity.Business;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by David Eti on 17/08/2017.
 */

public class BusinessAdapter extends BaseAdapter {
    private List<Business> mBusinesses;

    private BusinessItemListener businessItemListener;

    public BusinessAdapter(List<Business> businesses, BusinessItemListener itemListener) {
        setList(businesses);
        businessItemListener = itemListener;
    }


    private void setList(List<Business> businesses) {
        mBusinesses = checkNotNull(businesses);
    }

    public void replaceData(List<Business> businesses) {
        setList(businesses);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mBusinesses.size();
    }

    @Override
    public Business getItem(int i) {
        return mBusinesses.get(i);
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

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                businessItemListener.onBusinessClick(business);
            }
        });

        return rowView;
    }

    public interface BusinessItemListener {
        void onBusinessClick(Business clickedBusiness);
    }

}
