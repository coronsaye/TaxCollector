package com.vatebra.eirsagentpoc.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Collins Oronsaye on 26/08/2017.
 */

public class AssetProfile implements Serializable {

    private static final long serialVersionUID = -1381892018984702724L;

    @SerializedName("ProfileID")
    private int profileID;
    @SerializedName("ProfileName")
    private String profileName;
    @SerializedName("assessmentRules")
    private List<ProfileAssement> profileAssements;

    public int getProfileID() {
        return profileID;
    }

    public void setProfileID(int profileID) {
        this.profileID = profileID;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public List<ProfileAssement> getProfileAssements() {
        return profileAssements;
    }

    public void setProfileAssements(List<ProfileAssement> profileAssements) {
        this.profileAssements = profileAssements;
    }
}
