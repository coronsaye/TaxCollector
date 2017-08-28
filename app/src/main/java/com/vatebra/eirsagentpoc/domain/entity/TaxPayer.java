package com.vatebra.eirsagentpoc.domain.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by David Eti on 27/08/2017.
 */

public class TaxPayer implements Serializable {

    private static final long serialVersionUID = 7655272539410885375L;
    private int TaxPayerID;
    private String TaxPayerName;
    private String TIN;
    private String TaxPayerEmail;
    private double AccountBalance;
    private List<Bill> bills;


    public int getTaxPayerID() {
        return TaxPayerID;
    }

    public void setTaxPayerID(int taxPayerID) {
        TaxPayerID = taxPayerID;
    }

    public String getTaxPayerName() {
        return TaxPayerName;
    }

    public void setTaxPayerName(String taxPayerName) {
        TaxPayerName = taxPayerName;
    }

    public String getTIN() {
        return TIN;
    }

    public void setTIN(String TIN) {
        this.TIN = TIN;
    }

    public String getTaxPayerEmail() {
        return TaxPayerEmail;
    }

    public void setTaxPayerEmail(String taxPayerEmail) {
        TaxPayerEmail = taxPayerEmail;
    }

    public double getAccountBalance() {
        return AccountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        AccountBalance = accountBalance;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }
}
