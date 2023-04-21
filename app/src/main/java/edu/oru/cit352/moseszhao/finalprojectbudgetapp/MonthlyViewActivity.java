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
import android.widget.Toast;

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

}