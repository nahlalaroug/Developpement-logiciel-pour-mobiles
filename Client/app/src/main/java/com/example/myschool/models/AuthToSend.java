package com.example.myschool.models;

import java.util.ArrayList;
import java.util.HashMap;

public class AuthToSend {

    private int type;
    private String firstname;
    private String lastname;
    private String login;
    private String pass;
    private String email;
    private String grade;
    private String formule;
    private ArrayList<String> students =  new ArrayList<String>();
    private ArrayList<HashMap<String, String>> formula1 = new ArrayList<HashMap<String, String>>();

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getGrade() {
        return grade;
    }

    public int getType() {
        return type;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    public String getEmail() {
        return email;
    }

    public String getFormule() {
        return formule;
    }

    public ArrayList<String> getStudents() {
        return students;
    }

    public ArrayList<HashMap<String, String>> getFormula1() {
        return formula1;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFormule(String formule) {
        this.formule = formule;
    }

    public void setStudents(ArrayList<String> students) {
        this.students = students;
    }

    public void setFormula1(ArrayList<HashMap<String, String>> formula1) {
        this.formula1 = formula1;
    }

    public void debug(){
        System.out.println("Type : "+ type);
        System.out.println("FN : " + firstname);
        System.out.println("LN : " + lastname);
        System.out.println("PASS : " + pass);
        System.out.println("EMAIL : " + email);
        System.out.println("GRADE : " + grade);
        System.out.println("FOR : " + formule);
        System.out.println("FOR1 : " + formula1);
        System.out.println("CHILDS : " + getStudents().size());
        System.out.println("FORMULA 1 : " + getFormula1().size());
    }
}
