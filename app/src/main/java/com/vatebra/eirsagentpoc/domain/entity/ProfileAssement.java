package com.vatebra.eirsagentpoc.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Collins Oronsaye on 26/08/2017.
 */

public class ProfileAssement implements Serializable {

    private static final long serialVersionUID = -6825923536311382684L;
    @SerializedName("ProfileID")
    private int profileID;
    @SerializedName("ProfileRef")
    private String profileRef;
    @SerializedName("PaymentName")
    private String paymentName;
    @SerializedName("TaxYear")
    private String taxYear;
    @SerializedName("Frequency")
    private String frequency;
    @SerializedName("AsssessmentAmount")
    private String assessmentAmount;

    public int getProfileID() {
        return profileID;
    }

    public void setProfileID(int profileID) {
        this.profileID = profileID;
    }

    public String getProfileRef() {
        return profileRef;
    }

    public void setProfileRef(String profileRef) {
        this.profileRef = profileRef;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public String getTaxYear() {
        return taxYear;
    }

    public void setTaxYear(String taxYear) {
        this.taxYear = taxYear;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getAssessmentAmount() {
        return assessmentAmount;
    }

    public void setAssessmentAmount(String assessmentAmount) {
        this.assessmentAmount = assessmentAmount;
    }
}
