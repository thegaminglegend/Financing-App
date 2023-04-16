package edu.oru.cit352.moseszhao.finalprojectbudgetapp;

//Imports
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import androidx.fragment.app.DialogFragment;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/*
Name: Mengen Zhao
Professor: Dr. Osborne
Program: Financing App
Date: 4/15/2023
Description: An app that records financial spending and gain
The DatePickerDialog class that shows a dialog for the user to pick time.
*/

public class DatePickerDialog extends DialogFragment {

    //Declare Calendar instance variables
    Calendar selectedDate;

    //Define interface
    //Requires any class using DataPickerDialog to implement the didFinishDatePickerDialog() method
    public interface SaveDateListener {
        void didFinishDatePickerDialog(Calendar selectedTime);
    }

    //Empty constructor
    public DatePickerDialog() {
    }

    //Method to set up the dialog
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for the dialog
        final View view = inflater.inflate(R.layout.date_selection, container);
        //Set the dialog title
        getDialog().setTitle("Select Date");
        //Initialize selectedDate to the current date
        selectedDate = Calendar.getInstance();

        // Set up the CalendarView and set the selectedDate to the chosen date
        //Find view with ID
        final CalendarView cv = view.findViewById(R.id.calendarView);
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {
                selectedDate.set(year, month, day);
            }
        });

        //Set up the save button to save the selected date
        //Find view with ID
        Button saveButton = view.findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            //Method to call saveItem to save the date when clicked
            @Override
            public void onClick(View view) {
                saveItem(selectedDate);
            }
        });

        //Set up the cancel button to dismiss the dialog
        //Find view with ID
        Button cancelButton = view.findViewById(R.id.buttonCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            //Method to dismiss the dialog when clicked
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return view;
    }

    //Method to save the selected date and set the Birthday text to the date
    private void saveItem(Calendar selectedTime) {
        SaveDateListener activity = (SaveDateListener) getActivity();
        //call function to set birthday date to selected time
        activity.didFinishDatePickerDialog(selectedTime);
        getDialog().dismiss();
    }

}