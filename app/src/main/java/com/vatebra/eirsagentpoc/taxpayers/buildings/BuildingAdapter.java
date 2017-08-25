package com.vatebra.eirsagentpoc.taxpayers.buildings;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vatebra.eirsagentpoc.R;
import com.vatebra.eirsagentpoc.building.domain.entity.Building;
import com.vatebra.eirsagentpoc.domain.entity.Company;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by David Eti on 25/08/2017.
 */

public class BuildingAdapter extends BaseAdapter{

    List<Building> buildings;
    private List<Building> searchBuildings = new ArrayList<>();
    private BuildingItemListener buildingItemListener;

    @Override
    public int getCount() {
        return searchBuildings.size();
    }

    @Override
    public Building getItem(int i) {
        return searchBuildings.get(i);
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

        final Building building = getItem(i);
        TextView titleTextView = (TextView) rowView.findViewById(R.id.title);
        TextView taxOfficeTextView = (TextView) rowView.findViewById(R.id.subTitle);
        TextView phoneTextView = (TextView) rowView.findViewById(R.id.bodyTitle);
        TextView rinTextView = (TextView) rowView.findViewById(R.id.rinTextView);

        titleTextView.setText(building.getName());
        taxOfficeTextView.setText(building.getOffStreetName());
        phoneTextView.setText(building.getTagNumber());
        rinTextView.setText("#" + building.getRin());
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildingItemListener.OnBuildingClick(building);
            }
        });
        return rowView;
    }

    public interface  BuildingItemListener{
        void OnBuildingClick(Building building);
    }

    public BuildingAdapter(List<Building> buildings, BuildingItemListener buildingItemListener) {
        setList(buildings);
        this.buildingItemListener = buildingItemListener;
    }


    private void setList(List<Building> buildings) {
        this.buildings = checkNotNull(buildings);
        searchBuildings.clear();
        searchBuildings.addAll(this.buildings);
    }


    public void replaceData(List<Building> buildings) {
        setList(buildings);
        notifyDataSetChanged();
    }
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        searchBuildings.clear();
        if (charText.length() == 0) {
            searchBuildings.addAll(buildings);
        } else {
            for (Building building : buildings) {
                if (building.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    searchBuildings.add(building);
                }
            }
        }
        notifyDataSetChanged();
    }
}
