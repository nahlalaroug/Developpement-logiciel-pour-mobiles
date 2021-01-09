package com.example.myschool.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class adviceModel implements Serializable {

    @SerializedName("text")
    String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
