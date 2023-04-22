package edu.oru.cit352.moseszhao.finalprojectbudgetapp;
//Imports
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MonthlyViewActivity extends AppCompatActivity {

    //Instance Variables
    ArrayList<Finance> finances;
    FinanceAdapter financeAdapter;
    RecyclerView financeList;

    // Get the current month and year
    Calendar calendar = Calendar.getInstance();
    int currentMonth = calendar.get(Calendar.MONTH) + 1; // Add 1 to match database month format
    int currentYear = calendar.get(Calendar.YEAR);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_view);

        //Initialize buttons
        initAddButton();

        //Initialize Views
        initFinance(currentMonth,currentYear);
        initNet(currentMonth, currentYear);
        initMonth();
        initChangeMonthButtons();

    }

    //Function to initialize button to add a financial instance
    private void initAddButton(){
        //Find View With ID
        ImageView add = findViewById(R.id.imageViewAdd);
        //Go to Main activity when clicked
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MonthlyViewActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    //Function to initialize each finance instance
    private void initFinance(int currentMonth, int currentYear){

        //To get the data from database and display it
        //Instance variable
        FinanceDataSource fs = new FinanceDataSource(this);

        try {

            //Open DB get contacts and close DB
            fs.open();
            finances = fs.getFiances(currentMonth,currentYear);
            fs.close();
            //Find view with ID
            financeList = findViewById(R.id.rvFinance);
            //Instantiate layoutManager and set the layout manager
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager((this));
            financeList.setLayoutManager(layoutManager);
            //Instantiate contactAdapter and set the financeAdapter
            financeAdapter = new FinanceAdapter(finances, this);
            financeList.setAdapter(financeAdapter);

            //If something wrong show error text
        } catch (Exception e) {
            Toast.makeText(this, "Error retrieving financial instance", Toast.LENGTH_LONG).show();
        }
    }

    //Function to set the net and sum of income and pay
    private void initNet(int currentMonth, int currentYear){
        TextView net = findViewById(R.id.textViewNetActual);
        TextView monthIncome = findViewById(R.id.textViewMonthIncome);
        TextView monthPay = findViewById(R.id.textViewMonthPay);

        //To get the data from database and display it
        //Instance variable
        FinanceDataSource fs = new FinanceDataSource(this);

        try {
            //Open DB get contacts and close DB
            fs.open();
            //Get teh sum and net of pay and income
            float x = fs.getSumPay(currentMonth, currentYear);
            float y = fs.getSumIncome(currentMonth, currentYear);
            float z = y-x;
            fs.close();

            // Create a DecimalFormat object with desired format pattern
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");

            // Format the float value as a string
            String formattedStringPay = decimalFormat.format(x);
            String formattedStringIncome = decimalFormat.format(y);
            String formattedStringNet = decimalFormat.format(z);

            //Set the textViews
            monthPay.setText(formattedStringPay);
            monthIncome.setText(formattedStringIncome);
            net.setText(formattedStringNet);


            //If something wrong show error text
        } catch (Exception e) {
            Toast.makeText(this, "Error retrieving sum and net of payment", Toast.LENGTH_LONG).show();
        }
    }

    //Function to initialize the Month
    private void initMonth(){

        //Find View with ID
        TextView monthDate = findViewById(R.id.textViewMonthDate);


        // Create an array of month names in English
        String[] monthNames = {"January", "February", "March",
                "April", "May", "June",
                "July", "August", "September",
                "October", "November", "December"};

        // Set the month and year as text in the TextView
        monthDate.setText(monthNames[currentMonth-1] + " " + currentYear);


    }

    //Function to initialize button to change the financial instance and month
    private void initChangeMonthButtons(){

        //Find View with ID
        ImageView left = findViewById(R.id.imageViewLeft);
        ImageView right = findViewById(R.id.imageViewRight);

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Decrement current month
                currentMonth--;
                //Check if past january if so reset currentmonth to December and decrement Year
                if(currentMonth==0){
                    currentMonth=12;
                    currentYear--;
                }
                //Reset the month and financial instance for the month and year
                initFinance(currentMonth,currentYear);
                initMonth();
                initNet(currentMonth,currentYear);
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Increment current month
                currentMonth++;
                //Check if past December if so reset currentmonth to January and increment Year
                if(currentMonth==13){
                    currentMonth=1;
                    currentYear++;
                }
                //Reset the financial instance for the month and year
                initFinance(currentMonth,currentYear);
                initMonth();
                initNet(currentMonth,currentYear);
            }
        });
    }

}