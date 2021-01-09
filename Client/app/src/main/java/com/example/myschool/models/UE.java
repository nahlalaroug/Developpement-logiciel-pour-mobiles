package com.example.myschool.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class UE implements Serializable {


    @SerializedName("label")
    private String label;

    @SerializedName("Chapitres")
    private ArrayList<chapitre> chapitres = new ArrayList<chapitre>();

    @SerializedName("Quizzs")
    private ArrayList<quizzModel> quizzs = new ArrayList<quizzModel>();

    public String getLabel() {
        return label;
    }

    public ArrayList<chapitre> getChapitres() {
        return chapitres;
    }

    public void setChapitres(ArrayList<chapitre> chapitres) {
        this.chapitres = chapitres;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ArrayList<quizzModel> getQuizzs() {
        return quizzs;
    }

    public void setQuizzs(ArrayList<quizzModel> quizzs) {
        this.quizzs = quizzs;
    }
}
