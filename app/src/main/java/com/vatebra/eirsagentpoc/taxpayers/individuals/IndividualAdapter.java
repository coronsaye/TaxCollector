package com.vatebra.eirsagentpoc.taxpayers.individuals;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.vatebra.eirsagentpoc.R;
import com.vatebra.eirsagentpoc.domain.entity.Business;
import com.vatebra.eirsagentpoc.domain.entity.Individual;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by Collins Oronsaye on 23/08/2017.
 */

public class IndividualAdapter extends BaseAdapter {

    private List<Individual> individuals;
    private List<Individual> searchIndividuals = new ArrayList<>();
    private IndividualItemListener individualItemListener;
    private boolean isChooseTaxPayer;

    Individual selectedIndividual;
    public IndividualAdapter(List<Individual> individuals, IndividualItemListener individualItemListener,boolean isChooseTaxPayer) {
        setList(individuals);
        this.individualItemListener = individualItemListener;
        this.isChooseTaxPayer = isChooseTaxPayer;
    }


    private void setList(List<Individual> individuals) {
        this.individuals = checkNotNull(individuals);
        searchIndividuals.clear();
        searchIndividuals.addAll(this.individuals);
    }

    public void replaceData(List<Individual> individuals) {
        setList(individuals);
        notifyDataSetChanged();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        searchIndividuals.clear();
        if (charText.length() == 0) {
            searchIndividuals.addAll(individuals);
        } else {
            for (Individual individual : individuals) {
                if (individual.getFullName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    searchIndividuals.add(individual);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return searchIndividuals.size();
    }

    @Override
    public Individual getItem(int i) {
        return searchIndividuals.get(i);
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
            rowView = inflater.inflate(R.layout.item_individuals, viewGroup, false);
        }

        final Individual individual = getItem(i);
        TextView titleTextView = (TextView) rowView.findViewById(R.id.title);
        TextView taxOfficeTextView = (TextView) rowView.findViewById(R.id.subTitle);
        TextView phoneTextView = (TextView) rowView.findViewById(R.id.bodyTitle);
        TextView rinTextView = (TextView) rowView.findViewById(R.id.rinTextView);
        CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.selectCheckbox);


        if (isChooseTaxPayer)
            checkBox.setVisibility(View.VISIBLE);


        titleTextView.setText(individual.getFullName());
        taxOfficeTextView.setText(individual.getTaxOffice());
        if(!individual.getMobileNumberOne().equals("none")){
            phoneTextView.setText(individual.getMobileNumberOne());
        }
        else {
            phoneTextView.setText(individual.getMobileNumberTwo());

        }
        rinTextView.setText("#" + individual.getUserRin());
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                individualItemListener.onIndividualClick(individual);
            }
        });

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedIndividual = individual;
            }
        });
        return rowView;
    }

    public interface IndividualItemListener {
        void onIndividualClick(Individual clickedIndividual);
    }

}
