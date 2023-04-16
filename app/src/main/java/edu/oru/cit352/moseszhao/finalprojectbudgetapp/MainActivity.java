package edu.oru.cit352.moseszhao.finalprojectbudgetapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.text.format.DateFormat;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements DatePickerDialog.SaveDateListener {

    public static String text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize the buttons to control fragments
        initFragmentButtons();
        initChangeDateButton();

    }

    //Function to initialize the buttons to control fragments
    private void initFragmentButtons(){

        //Find View by ID
        Button btnIncome = findViewById(R.id.buttonIncome);
        Button btnPayment = findViewById(R.id.buttonPayment);

        //Set Payment button fragment default
        btnPayment.setEnabled(false);
        btnPayment.setTextColor(Color.BLACK);

        //Change fragmentView to income fragment view when clicked
        btnIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //enable payment and disable income
                btnPayment.setEnabled(true);
                btnIncome.setEnabled(false);
                btnIncome.setTextColor(Color.BLACK);
                btnPayment.setTextColor(Color.WHITE);

                //Instance of fragment manager to manage the fragment
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, incomeFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();
            }
        });

        //Change fragmentView to payment fragment view when clicked
        btnPayment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //enable income and disable payment
                btnPayment.setEnabled(false);
                btnIncome.setEnabled(true);
                btnPayment.setTextColor(Color.BLACK);
                btnIncome.setTextColor(Color.WHITE);
                //Instance of fragment manager to manage the fragment
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, paymentFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();
            }
        });
    }


    //Function to set the date to the selected formatted date
    @Override
    public void didFinishDatePickerDialog(Calendar selectedTime) {
        //Find view with ID
        TextView date = findViewById(R.id.textViewDate);
        date.setText(DateFormat.format("MM/dd/yyyy", selectedTime));
    }
    
    //Function to initialize the date changing button
    private void initChangeDateButton(){
        //Find view with ID
        Button changeDate = findViewById(R.id.buttonDate);
        //Opens date picker dialog when clicked
        changeDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                DatePickerDialog datePickerDialog = new DatePickerDialog();
                datePickerDialog.show(fm, "DatePick");

            }
        });
    }
}
