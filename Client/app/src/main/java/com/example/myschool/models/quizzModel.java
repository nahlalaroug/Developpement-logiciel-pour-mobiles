package com.example.myschool.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class quizzModel implements Serializable {

    @SerializedName("Question")
    private String q;

    @SerializedName("Choixs")
    private ArrayList<choixModel> c;

    @SerializedName("Reponse")
    private String r;


    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public ArrayList<choixModel> getC() {
        return c;
    }

    public void setC(ArrayList<choixModel> c) {
        this.c = c;
    }
}
