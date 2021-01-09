package com.example.myschool.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class loginResult implements Serializable {

    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("type")
    private int type;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getType(){
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setType(int type) {
        this.type = type;
    }
}
