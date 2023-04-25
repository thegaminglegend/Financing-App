package edu.oru.cit352.moseszhao.finalprojectbudgetapp;

/*
Name: Mengen Zhao
Professor: Dr. Osborne
Program: Financing App
Date: 4/25/2023
Description: An app that records financial spending and gain and displays it each month
The Finance class stores the information for each financial instance and has getters and setters to set the variables
*/

//Import
import java.util.Calendar;

public class Finance {
    //Variables for the properties of a financial instance
    private int financeID;
    private String category;
    private float amount;
    private Calendar date;
    private String payOrIncome;

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

    public String getPayOrIncome() {return payOrIncome;}

    public void setPayOrIncome(String payOrIncome){this.payOrIncome = payOrIncome;}
}