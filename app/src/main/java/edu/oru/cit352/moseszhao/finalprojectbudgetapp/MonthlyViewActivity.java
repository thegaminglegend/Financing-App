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

public class MonthlyViewActivity extends AppCompatActivity {

    //Instance Variables
    ArrayList<Finance> finances;
    FinanceAdapter financeAdapter;
    RecyclerView financeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_view);

        //Initialize buttons
        initAddButton();

        //Initialize a
        initFinance();

        initNet();

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

    //Function to initialize
    private void initFinance(){

        //To get the data from database and display it
        //Instance variable
        FinanceDataSource fs = new FinanceDataSource(this);

        try {
            //Open DB get contacts and close DB
            fs.open();
            finances = fs.getFiances();
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

    private void initNet(){
        TextView net = findViewById(R.id.textViewNetActual);
        TextView monthIncome = findViewById(R.id.textViewMonthIncome);
        TextView monthPay = findViewById(R.id.textViewMonthPay);

        //To get the data from database and display it
        //Instance variable
        FinanceDataSource fs = new FinanceDataSource(this);

        try {
            //Open DB get contacts and close DB
            fs.open();
            float x = fs.getSumPay();
            float y = fs.getSumIncome();
            float z = y-x;
            fs.close();

            // Create a DecimalFormat object with desired format pattern
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00"); // example format pattern

            // Format the float value as a string
            String formattedStringPay = decimalFormat.format(x);
            String formattedStringIncome = decimalFormat.format(y);
            String formattedStringNet = decimalFormat.format(z);


            monthPay.setText(formattedStringPay);
            monthIncome.setText(formattedStringIncome);
            net.setText(formattedStringNet);


            //If something wrong show error text
        } catch (Exception e) {
            Toast.makeText(this, "Error retrieving sum of pay", Toast.LENGTH_LONG).show();
        }




    }

}