package com.vatebra.eirsagentpoc.domain.entity;

import java.io.Serializable;

/**
 * Created by Collins Oronsaye on 27/08/2017.
 */

public class Bill implements Serializable {

    private static final long serialVersionUID = 7799692457157661869L;
    private int AssessmentID;
    private String AssessmentRef;
    private String SettlementDueDate;
    private String RuleName;
    private int TaxYear;
    private int TaxPayerID;
    private String TIN;
    private String TaxPayerName;
    private String AssetName;
    private int SettlementStatusID;
    private String SettlementName;
    private String TaxPayerEmail;
    private double AsssessmentAmount;
    private double AmountPaid;

    public int getAssessmentID() {
        return AssessmentID;
    }

    public void setAssessmentID(int assessmentID) {
        AssessmentID = assessmentID;
    }

    public String getAssessmentRef() {
        return AssessmentRef;
    }

    public void setAssessmentRef(String assessmentRef) {
        AssessmentRef = assessmentRef;
    }

    public String getSettlementDueDate() {
        return SettlementDueDate;
    }

    public void setSettlementDueDate(String settlementDueDate) {
        SettlementDueDate = settlementDueDate;
    }

    public String getRuleName() {
        return RuleName;
    }

    public void setRuleName(String ruleName) {
        RuleName = ruleName;
    }

    public int getTaxYear() {
        return TaxYear;
    }

    public void setTaxYear(int taxYear) {
        TaxYear = taxYear;
    }

    public int getTaxPayerID() {
        return TaxPayerID;
    }

    public void setTaxPayerID(int taxPayerID) {
        TaxPayerID = taxPayerID;
    }

    public String getTIN() {
        return TIN;
    }

    public void setTIN(String TIN) {
        this.TIN = TIN;
    }

    public String getTaxPayerName() {
        return TaxPayerName;
    }

    public void setTaxPayerName(String taxPayerName) {
        TaxPayerName = taxPayerName;
    }

    public String getAssetName() {
        return AssetName;
    }

    public void setAssetName(String assetName) {
        AssetName = assetName;
    }

    public int getSettlementStatusID() {
        return SettlementStatusID;
    }

    public void setSettlementStatusID(int settlementStatusID) {
        SettlementStatusID = settlementStatusID;
    }

    public String getSettlementName() {
        return SettlementName;
    }

    public void setSettlementName(String settlementName) {
        SettlementName = settlementName;
    }

    public String getTaxPayerEmail() {
        return TaxPayerEmail;
    }

    public void setTaxPayerEmail(String taxPayerEmail) {
        TaxPayerEmail = taxPayerEmail;
    }

    public double getAsssessmentAmount() {
        return AsssessmentAmount;
    }

    public void setAsssessmentAmount(double asssessmentAmount) {
        AsssessmentAmount = asssessmentAmount;
    }

    public double getAmountPaid() {
        return AmountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        AmountPaid = amountPaid;
    }

    public double getAmountLeft() {
        return AsssessmentAmount - AmountPaid;
    }
}
