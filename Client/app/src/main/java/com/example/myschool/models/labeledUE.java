package com.example.myschool.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class labeledUE implements Serializable {

    @SerializedName("UE")
    private String ue;

    public String getUe() {
        return ue;
    }

    public void setUe(String ue) {
        this.ue = ue;
    }
}
