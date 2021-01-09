package com.example.myschool.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class gradesResult {

    @SerializedName("Grade")
    private String grade;

    @SerializedName("UE")
    private ArrayList<UE> ue;

    public String getGrade() {
        return grade;
    }

    public ArrayList<UE> getUe() {
        return ue;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setUe(ArrayList<UE> ue) {
        this.ue = ue;
    }

    public ArrayList<String> getStringifyUE(){
        ArrayList<String> res = new ArrayList<String>();

        for (int i = 0; i < this.getUe().size(); i++) {
            res.add(this.getUe().get(i).getLabel());
        }
        return res;
    }
    public void printGradeResult(){
        System.out.println("GRADE : " + this.getGrade());
        for (int i = 0; i < this.getUe().size(); i++) {
            System.out.println("UE : " + this.getUe().get(i).getLabel());
        }
    }
}

