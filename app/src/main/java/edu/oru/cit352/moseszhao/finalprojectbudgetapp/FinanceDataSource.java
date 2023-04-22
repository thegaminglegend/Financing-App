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
Program: Contact List app
Date: 2/28/2023
Description: A contact list App that stores user's information. Class to manage database
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
    public int getLastContactID(){
        //declare variables
        int lastId = -1;

        try {
            //Get the last contact id
            String query = "Select MAX(_id) from contact";
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

    //Function to get the sum of all pay
    public float getSumPay(){

        //Instance variable
        float x = 0;

        try {
            //Get the sum of all pay
            String query = "SELECT SUM(amount) FROM finance WHERE payorincome = 'true'";;
            //Pointer to points to the last row of the database
            Cursor cursor = database.rawQuery(query, null);
            //Point cursor to the first row after the query
            cursor.moveToFirst();
            //Get the first column to get Id
            x = cursor.getFloat(0);
            cursor.close();
        }
        //If exception set x as -1
        catch (Exception e){
            x = -1;
        }

        return x;
    }

    //Function to get the sum of all income
    public float getSumIncome(){

        //Instance variable
        float x = 0;

        try {
            //Get the sum of all income
            String query = "SELECT SUM(amount) FROM finance WHERE payorincome = 'false'";;
            //Pointer to points to the last row of the database
            Cursor cursor = database.rawQuery(query, null);
            //Point cursor to the first row after the query
            cursor.moveToFirst();
            //Get the first column to get Id
            x = cursor.getFloat(0);
            cursor.close();
        }
        //If exception set x as -1
        catch (Exception e){
            x = -1;
        }

        return x;
    }


//    //Method to get all the contact name from DB
//    public ArrayList<String> getContactName(){
//        //Reference Variable
//        ArrayList<String> contactNames = new ArrayList<>();
//        try{
//            //query to get the contactnname
//            String query = "Select contactname from contact";
//            Cursor cursor = database.rawQuery(query, null);
//            cursor.moveToFirst();
//
//            //Loop through the DB until the last entry and all on them into contactNames
//            while(!cursor.isAfterLast()){
//                contactNames.add(cursor.getString(0));
//                cursor.moveToNext();
//            }
//            //close cursor
//            cursor.close();
//        }
//        //If did not get anything set contactNames as a null ArrayList
//        catch (Exception e){
//            contactNames = new ArrayList<String>();
//        }
//        return contactNames;
//    }
//
    //Method to retrieve data from DB
    public ArrayList<Finance> getFiances() {
        //Instance Variable contains arraylist of Finance object
        ArrayList<Finance> finances = new ArrayList<Finance>();

        try {
            //Get all data from contact
            String query = "SELECT * FROM finance";
            //Initialize cursor
            Cursor cursor = database.rawQuery(query, null);
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



//
//    //Method to  get specific information with contactId
//    public Contact getSpecificContact(int contactId) {
//        //Instance Variable
//        Contact contact = new Contact();
//        //Get the information of that contact with contactId
//        String query = "SELECT * FROM contact WHERE _id =" + contactId;
//        //Cursor
//        Cursor cursor = database.rawQuery(query, null);
//        //Get all the information to store in the contact object
//        if (cursor.moveToFirst()) {
//            contact.setContactID(cursor.getInt(0));
//            contact.setContactName(cursor.getString(1));
//            contact.setStreetAddress(cursor.getString(2));
//            contact.setCity(cursor.getString(3));
//            contact.setState(cursor.getString(4));
//            contact.setZipCode(cursor.getString(5));
//            contact.setPhoneNumber(cursor.getString(6));
//            contact.setCellNumber(cursor.getString(7));
//            contact.seteMail(cursor.getString(8));
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTimeInMillis(Long.valueOf(cursor.getString(9)));
//            contact.setBirthday(calendar);
//            cursor.close();
//        }
//        return contact;
//    }

    //Method for deleting Contact
    public boolean deleteContact(int contactId) {
        //Declare variable
        boolean didDelete = false;
        try{
            //Delete the contact from DB
            didDelete = database.delete("contact", "_id=" + contactId, null) > 0;
        } catch (Exception e) {
            //Do nothing if fail
        }
        return didDelete;
    }

}
