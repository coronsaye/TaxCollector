package com.vatebra.eirsagentpoc.taxpayers;

import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vatebra.eirsagentpoc.R;
import com.vatebra.eirsagentpoc.building.domain.entity.Building;
import com.vatebra.eirsagentpoc.domain.entity.AssetProfile;
import com.vatebra.eirsagentpoc.domain.entity.ProfileAssement;
import com.vatebra.eirsagentpoc.taxpayers.buildings.BuildingAdapter;

import java.util.List;

import static com.vatebra.eirsagentpoc.util.Constants.formatStringToNaira;

/**
 * Created by David Eti on 26/08/2017.
 */

public class ProfileAdapter extends BaseAdapter {

    List<ProfileAssement> assements;


    public ProfileAdapter(List<ProfileAssement> assements) {
        this.assements = assements;
    }

    @Override
    public int getCount() {
        return assements.size();
    }

    @Override
    public ProfileAssement getItem(int i) {
        return assements.get(i);
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
            rowView = inflater.inflate(R.layout.item_payments, viewGroup, false);
        }
        final ProfileAssement assement = getItem(i);

        TextView profileTextView = (TextView) rowView.findViewById(R.id.ruleTitle);
        profileTextView.setText(assement.getPaymentName());

        TextView frequencyTextView = (TextView) rowView.findViewById(R.id.frequencyTextView);
        frequencyTextView.setText(assement.getFrequency());

        TextView yearTextView = (TextView) rowView.findViewById(R.id.bodyTitle);
        yearTextView.setText(assement.getTaxYear());

        TextView ruleCodeTextView = (TextView) rowView.findViewById(R.id.ruleCodeTextView);
        ruleCodeTextView.setText(assement.getProfileRef());


        TextView amountTextView = (TextView) rowView.findViewById(R.id.amountTextView);
//        amountTextView.setText(nairaSymbol + assement.getAssessmentAmount());
        double assessmentAmount = Double.parseDouble(assement.getAssessmentAmount());
        amountTextView.setText(formatStringToNaira(assessmentAmount));
        return rowView;
    }
}
