package com.example.myschool.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class userModel implements Serializable {

    private String name;

    @SerializedName("p_email")
    private String email;

    @SerializedName("p_acc_type")
    private int type;

    @SerializedName("p_firstname")
    private  String fname;

    @SerializedName("p_lastname")
    private  String lname;

    @SerializedName("p_grade")
    private  String grade;

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @SerializedName("p_formula1")
    private ArrayList<labeledUE> listUE;


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public ArrayList<labeledUE> getListUE() {
        return listUE;
    }

    public void setListUE(ArrayList<labeledUE> listUE) {
        this.listUE = listUE;
    }
}
