package com.example.myschool.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class chapitre implements Serializable {

    @SerializedName("text")
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
