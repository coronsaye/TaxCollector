package com.vatebra.eirsagentpoc.domain.entity;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by David Eti on 23/08/2017.
 */

public class Individual extends RealmObject {


    @SerializedName("Id")
    private int Id;
    @PrimaryKey
    @SerializedName("UserRIN")
    private String userRin;
    @SerializedName("IndividualCodeNumber")
    private int IndividualCodeNumber = 0;
    @SerializedName("Gender")
    private String gender;
    @SerializedName("Title")
    private String title;
    @SerializedName("FirstName")
    private String firstName;
    @SerializedName("LastName")
    private String lastName;
    @SerializedName("MiddleName")
    private String middleName;
    @SerializedName("DOB")
    private String dateOfBirth;
    @SerializedName("TIN")
    private String tin;
    @Nullable
    @SerializedName("MobileNO")
    private String mobileNumberOne;
    @SerializedName("PhoneNO")
    private String mobileNumberTwo;
    @SerializedName("EmailAddress")
    private String emailAddressOne;
    @SerializedName("EmailAddress1")
    private String emailAddresTwo;
    @SerializedName("BioMetricDetails")
    private String biometricDetails;
    @SerializedName("TaxOfficeName")
    private String taxOffice;
    @Nullable
    @SerializedName("MaritalStatus")
    private String maritalStatus;
    @SerializedName("Nationality")
    private String nationality;
    @Nullable
    @SerializedName("TaxPayerType")
    private String taxPayerType;
    @SerializedName("EconomicActivity")
    private String economicActivity;
    @SerializedName("NotificationMethod")
    private String preferredNotificationMethod;
    @Nullable
    @SerializedName("TaxPayerStatuss")
    private String taxPayerStatus;
    @SerializedName("AccountBalance")
    private String accountBalance;


    private int TaxOfficeID;

    private int EconomicActivityID;

    public Individual() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUserRin() {
        return userRin;
    }

    public void setUserRin(String userRin) {
        this.userRin = userRin;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getMobileNumberOne() {
        return (mobileNumberOne != null) ? mobileNumberOne : "none";
    }

    public void setMobileNumberOne(String mobileNumberOne) {
        this.mobileNumberOne = mobileNumberOne;
    }

    public String getMobileNumberTwo() {
        return (mobileNumberTwo != null) ? mobileNumberTwo : "none";
    }

    public void setMobileNumberTwo(String mobileNumberTwo) {
        this.mobileNumberTwo = mobileNumberTwo;
    }

    public String getEmailAddressOne() {
        return emailAddressOne;
    }

    public void setEmailAddressOne(String emailAddressOne) {
        this.emailAddressOne = emailAddressOne;
    }

    public String getEmailAddresTwo() {
        return emailAddresTwo;
    }

    public void setEmailAddresTwo(String emailAddresTwo) {
        this.emailAddresTwo = emailAddresTwo;
    }

    public String getBiometricDetails() {
        return biometricDetails;
    }

    public void setBiometricDetails(String biometricDetails) {
        this.biometricDetails = biometricDetails;
    }

    public String getTaxOffice() {
        return taxOffice;
    }

    public void setTaxOffice(String taxOffice) {
        this.taxOffice = taxOffice;
    }

    public String getMaritalStatus() {
        return (maritalStatus != null) ? maritalStatus : "none";
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getTaxPayerType() {
        return (taxPayerType != null) ? taxPayerType : "none";
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

    public String getPreferredNotificationMethod() {
        return preferredNotificationMethod;
    }

    public void setPreferredNotificationMethod(String preferredNotificationMethod) {
        this.preferredNotificationMethod = preferredNotificationMethod;
    }

    public String getTaxPayerStatus() {
        return (taxPayerStatus != null) ? taxPayerStatus : "none";
    }

    public void setTaxPayerStatus(String taxPayerStatus) {
        this.taxPayerStatus = taxPayerStatus;
    }

    public String getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(String accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getFullName() {
        return getLastName() + " " + getMiddleName() + " " + getFirstName();
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
}
