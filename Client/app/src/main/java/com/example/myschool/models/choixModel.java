package com.example.myschool.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class choixModel implements Serializable {

    @SerializedName("Choix")
    String choix;


    public String getChoix() {
        return choix;
    }

    public void setChoix(String choix) {
        this.choix = choix;
    }
}
