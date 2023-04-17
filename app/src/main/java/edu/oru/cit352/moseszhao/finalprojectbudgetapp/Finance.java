package edu.oru.cit352.moseszhao.finalprojectbudgetapp;

/*
Name: Mengen Zhao
Professor: Dr. Osborne
Program: Contact List app
Date: 2/28/2023
Description: A contact list App that stores user's information. The class that stores properties for a contact
*/

//Import
import java.util.Calendar;

public class Finance {
    //Variables for the properties of a contact
    private int financeID;
    private String category;
    private float amount;
    private Calendar date;

    //Constructor
    public Finance() {
        //New contact would be -1
        financeID = -1;
        date = Calendar.getInstance();
    }

    //Getters and setters
    public int getFinanceID() {
        return financeID;
    }

    public void setFinanceID(int financeID) {
        this.financeID = financeID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}