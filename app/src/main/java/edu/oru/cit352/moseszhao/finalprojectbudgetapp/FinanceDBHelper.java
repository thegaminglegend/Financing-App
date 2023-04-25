package edu.oru.cit352.moseszhao.finalprojectbudgetapp;

//imports
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/*
Name: Mengen Zhao
Professor: Dr. Osborne
Program: Financing App
Date: 4/25/2023
Description: An app that records financial spending and gain
Class to help to manage Database to create table and update DB when version change
*/

public class FinanceDBHelper extends SQLiteOpenHelper {

    //Declare Variables
    private static final String DATABASE_NAME = "myfinance.db";
    private static final int DATABASE_VERSION = 8;

    // Database creation sql statement
    private static final String CREATE_TABLE_FINANCE =
            "create table finance (_id integer primary key autoincrement, "
                    + "amount float not null,"
                    + "date text, category text not null, payorincome text not null);";

    //Constructor
    public FinanceDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Method to create the database
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FINANCE);
    }

    //Method to update the database when database version change
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(FinanceDBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS finance");
        onCreate(db);
    }

}