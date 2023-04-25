package edu.oru.cit352.moseszhao.finalprojectbudgetapp;

//Imports
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

/*
Name: Mengen Zhao
Professor: Dr. Osborne
Program: Financing App
Date: 4/25/2023
Description: An app that records financial spending and gain and displays it each month.
FinanceDataSource class uses FinanceDBHelper to perform database insert, update, delete, calculations
*/

public class FinanceDataSource {
    //Create instance variables
    private SQLiteDatabase database;
    private FinanceDBHelper dbHelper;

    //Constructor
    public FinanceDataSource(Context context) {
        dbHelper = new FinanceDBHelper(context);
    }

    //Method to open the database
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    //Method to close database
    public void close() {
        dbHelper.close();
    }

    //Method to insert a Finance Instance
    public boolean insertFinance(Finance f) {
        //declare variables
        boolean didSucceed = false;
        try {
            //Create contentValues object instance
            ContentValues initialValues = new ContentValues();

            //Key-Value store data in initialValues
            initialValues.put("category", f.getCategory());
            initialValues.put("amount", f.getAmount());
            initialValues.put("date", String.valueOf(f.getDate().getTimeInMillis()));
            initialValues.put("payorincome", f.getPayOrIncome());

            //Insert everything into the database and get true if successfully inserted
            didSucceed = database.insert("finance", null, initialValues) > 0;
        }
        catch (Exception e){
            //Do nothing if there is an exception false would be returned
        }
        return didSucceed;
    }

    //Method to update the Finance Instance
    public boolean updateFinance(Finance f) {
        //declare variables
        boolean didSucceed = false;
        try {
            //Get contact ID to know which row to update
            Long rowId = (long)f.getFinanceID();
            //Create contentValues object instance
            ContentValues updateValues = new ContentValues();

            //Key-Value store data in updateValues
            updateValues.put("category", f.getCategory());
            updateValues.put("amount", f.getAmount());
            updateValues.put("date", String.valueOf(f.getDate().getTimeInMillis()));
            updateValues.put("payorincome", f.getPayOrIncome());

            //Update everything into the database and get true if successfully updated
            didSucceed = database.update("contact", updateValues, "_id=" + rowId, null) > 0;
        }
        catch (Exception e){
            //Do nothing if there is an exception false would be returned
        }
        return didSucceed;
    }

    //Method to get the Id of the last inserted finance instance in the database
    public int getLastFinanceID(){
        //declare variables
        int lastId = -1;

        try {
            //Get the last Finance id
            String query = "Select MAX(_id) from finance";
            //Pointer to points to the last row of the database
            Cursor cursor = database.rawQuery(query, null);
            //Point cursor to the first row after the query
            cursor.moveToFirst();
            //Get the first column to get Id
            lastId = cursor.getInt(0);
            cursor.close();
        }
        //If exception set lastId as -1
        catch (Exception e){
            lastId = -1;
        }
        return lastId;
    }

    //Function to get the sum of all pay of a month in a year
    public float getSumPay(int currentMonth, int currentYear){

        //Instance variable
        float x = 0;

        try {

            // Convert currentMonth and currentYear to String with leading zero if necessary
            String currentMonthString = String.format("%02d", currentMonth);
            String currentYearString = String.valueOf(currentYear);

            // Query to retrieve sum of all pay for the current month
            String query = "SELECT SUM(amount) FROM finance WHERE payorincome = 'true' AND strftime('%m', date/1000, 'unixepoch') = ? AND strftime('%Y', date/1000, 'unixepoch') = ?";
            String[] selectionArgs = {currentMonthString, currentYearString};
            //Pointer to points to the last row of the database
            Cursor cursor = database.rawQuery(query, selectionArgs);

            //Point cursor to the first row after the query
            cursor.moveToFirst();
            //Get the first column to get Id
            x = cursor.getFloat(0);
            cursor.close();
        }
        //If exception set x as 0
        catch (Exception e){
            x = 0;
        }

        return x;
    }

    //Function to get the sum of all income of a month of a year
    public float getSumIncome(int currentMonth, int currentYear){

        //Instance variable
        float x = 0;

        try {

            // Convert currentMonth and currentYear to String with leading zero if necessary
            String currentMonthString = String.format("%02d", currentMonth);
            String currentYearString = String.valueOf(currentYear);

            // Query to retrieve sum of all income for the current month
            String query = "SELECT SUM(amount) FROM finance WHERE payorincome = 'false' AND strftime('%m', date/1000, 'unixepoch') = ? AND strftime('%Y', date/1000, 'unixepoch') = ?";
            String[] selectionArgs = {currentMonthString, currentYearString};
            //Pointer to points to the last row of the database
            Cursor cursor = database.rawQuery(query, selectionArgs);

            //Point cursor to the first row after the query
            cursor.moveToFirst();
            //Get the first column to get Id
            x = cursor.getFloat(0);
            cursor.close();
        }
        //If exception set x as -1
        catch (Exception e){
            x = 0;
        }

        return x;
    }

    //Method to retrieve data from DB
    public ArrayList<Finance> getFiances(int currentMonth, int currentYear) {
        //Instance Variable contains arraylist of Finance object
        ArrayList<Finance> finances = new ArrayList<Finance>();

        try {

            // Convert currentMonth and currentYear to String with leading zero if necessary
            String currentMonthString = String.format("%02d", currentMonth);
            String currentYearString = String.valueOf(currentYear);

            // Query to retrieve data for the current month
            String query = "SELECT * FROM finance WHERE strftime('%m', date/1000, 'unixepoch') = ? AND strftime('%Y', date/1000, 'unixepoch') = ?";
            String[] selectionArgs = {currentMonthString, currentYearString};
            Cursor cursor = database.rawQuery(query, selectionArgs);

            //Instance Variable
            Finance newFinance;
            cursor.moveToFirst();
            //Loop through DB until get every data from every entry
            while (!cursor.isAfterLast()) {
                //Get the data from each relevant column of the entry cursor pointing to
                newFinance = new Finance();
                newFinance.setFinanceID(cursor.getInt(0));
                newFinance.setAmount(cursor.getFloat(1));
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(Long.valueOf(cursor.getString(2)));
                newFinance.setDate(calendar);
                newFinance.setCategory(cursor.getString(3));
                newFinance.setPayOrIncome(cursor.getString(4));
                finances.add(newFinance);
                //Increment Cursor to go through all entry
                cursor.moveToNext();
            }
            //Close cursor
            cursor.close();
            //If get nothing give contact a Null arraylist
        } catch (Exception e) {
            finances = new ArrayList<Finance>();
        }
        return finances;
    }

    //Method for deleting Contact
    public boolean deleteFinance(int financeID) {
        //Declare variable
        boolean didDelete = false;
        try{
            //Delete the contact from DB
            didDelete = database.delete("finance", "_id=" + financeID, null) > 0;
        } catch (Exception e) {
            //Do nothing if fail
        }
        return didDelete;
    }

}
