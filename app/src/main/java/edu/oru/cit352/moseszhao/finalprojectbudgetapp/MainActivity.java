package edu.oru.cit352.moseszhao.finalprojectbudgetapp;

//Imports

import static com.google.android.material.internal.ViewUtils.hideKeyboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import android.text.format.DateFormat;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

import java.time.Month;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements DatePickerDialog.SaveDateListener {

    private Finance currentFinance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize the buttons to control fragments
        initFragmentButtons();

        //Initialize buttons
        initChangeDateButton();
        initMonthViewButton();
        initConfirmButton();

        //Initialize text views
        initDate();

        //Instantiate the finance object
        currentFinance = new Finance();

        //Initialize Functions
        initTextChangedEvents();


    }

    //Function to set the amount when it is changed
    private void initTextChangedEvents() {
        //Find View by ID
        final EditText etAmount = findViewById(R.id.editTextTextAmount);
        etAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //set the amount when it is changed
                currentFinance.setAmount(Float.parseFloat(etAmount.getText().toString()));
            }
        });

        //Find View With ID
        final TextView tvCategory = findViewById(R.id.textViewCategoryActual);
        tvCategory.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //set the Category when it is changed
                currentFinance.setCategory(tvCategory.getText().toString());
            }
        });


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
                //Set the instance to be pay or income
                currentFinance.setPayOrIncome(false);
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
                //Set the instance to be pay or income
                currentFinance.setPayOrIncome(true);
            }
        });
    }


    //FUnction to initialize the date to the current date
    public void initDate(){
        TextView date = findViewById(R.id.textViewDate);
        date.setText(DateFormat.format("MM/dd/yyyy", Calendar.getInstance()));
    }


    //Function to set the date to the selected formatted date
    @Override
    public void didFinishDatePickerDialog(Calendar selectedTime) {
        //Find view with ID
        TextView date = findViewById(R.id.textViewDate);
        date.setText(DateFormat.format("MM/dd/yyyy", selectedTime));
        currentFinance.setDate(selectedTime);
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

    //Function to initialize monthlyView button
    private void initMonthViewButton(){
        //Find View With ID
        ImageView month = findViewById(R.id.imageViewMonth);
        //Go to monthView activity when clicked
        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MonthlyViewActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    //Initialize Confirm Button
    private void initConfirmButton() {
        //Find the view by ID
        Button saveButton = findViewById(R.id.buttonConfirm);
        saveButton.setOnClickListener(new View.OnClickListener() {
            //Method to save the contact information into DB when save clicked
            @Override
            public void onClick(View view) {
                //Declare variable
                boolean wasSuccessful;
                //instantiate class to manage database
                FinanceDataSource fs = new FinanceDataSource(MainActivity.this);
                try {
                    //Open DB
                    fs.open();
                    //Check if the finance instance is new if true insert into DB
                    if (currentFinance.getFinanceID() == -1) {
                        wasSuccessful = fs.insertFinance(currentFinance);
                        //If inserted update the financeID
                        if(wasSuccessful){
                            int newID = fs.getLastContactID();
                            currentFinance.setFinanceID(newID);
                        }
                    }
                    //If false update in DB
                    else {
                        wasSuccessful = fs.updateFinance(currentFinance);
                    }

                    //close DB
                    fs.close();
                }
                //If unable to insert or update set wasSuccessful to false
                catch (Exception e) {
                    wasSuccessful = false;
                }
                //If inserted or updated open monthly view
                if (wasSuccessful) {
                    Intent intent = new Intent(MainActivity.this, MonthlyViewActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }
            }
        });
    }


}
