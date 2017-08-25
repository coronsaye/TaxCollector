package com.vatebra.eirsagentpoc.domain.entity;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by David Eti on 23/08/2017.
 */

public class Company extends RealmObject {


    @SerializedName("Id")
    private int id;
    @PrimaryKey
    @SerializedName("RIN")
    private String rin;
    @SerializedName("Name")
    private String name;
    @SerializedName("TIN")
    private String tin;
    @SerializedName("MobileNo")
    private String mobileNo;
    @SerializedName("PhoneNo")
    private String phoneNo;
    @SerializedName("EmailAddress")
    private String emailAddress;
    @SerializedName("EmailAddress1")
    private String emailAddressTwo;
    @SerializedName("TaxOffice")
    private String taxOffice;
    @SerializedName("TaxOfficeName")
    private String TaxOfficeName;

    @SerializedName("TaxPayerType")
    private String taxPayerType;
    @SerializedName("EconomicActivity")
    private String economicActivity;
    @SerializedName("TaxPayerStatuss")
    private String taxPayerStatus;
    @SerializedName("Longitude")
    private double longitude;
    @SerializedName("Latitude")
    private double latitude;
    @SerializedName("NotificationMethod")
    private String preferredNotificationMethod;


    private int TaxOfficeID;

    private int EconomicActivityID;


    public Company() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRin() {
        return rin;
    }

    public void setRin(String rin) {
        this.rin = rin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddressTwo() {
        return emailAddressTwo;
    }

    public void setEmailAddressTwo(String emailAddressTwo) {
        this.emailAddressTwo = emailAddressTwo;
    }

    public String getTaxOffice() {
        return taxOffice;
    }

    public void setTaxOffice(String taxOffice) {
        this.taxOffice = taxOffice;
    }

    public String getTaxPayerType() {
        return taxPayerType;
    }

    public void setTaxPayerType(String taxPayerType) {
        this.taxPayerType = taxPayerType;
    }

    public String getEconomicActivity() {
        return economicActivity;
    }

    public void setEconomicActivity(String economicActivity) {
        this.economicActivity = economicActivity;
    }

    public String getTaxPayerStatus() {
        return taxPayerStatus;
    }

    public void setTaxPayerStatus(String taxPayerStatus) {
        this.taxPayerStatus = taxPayerStatus;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public String getPreferredNotificationMethod() {
        return preferredNotificationMethod;
    }

    public void setPreferredNotificationMethod(String preferredNotificationMethod) {
        this.preferredNotificationMethod = preferredNotificationMethod;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getTaxOfficeID() {
        return TaxOfficeID;
    }

    public void setTaxOfficeID(int taxOfficeID) {
        TaxOfficeID = taxOfficeID;
    }

    public int getEconomicActivityID() {
        return EconomicActivityID;
    }

    public void setEconomicActivityID(int economicActivityID) {
        EconomicActivityID = economicActivityID;
    }

    public String getTaxOfficeName() {
        return TaxOfficeName;
    }

    public void setTaxOfficeName(String taxOfficeName) {
        TaxOfficeName = taxOfficeName;
    }
}
