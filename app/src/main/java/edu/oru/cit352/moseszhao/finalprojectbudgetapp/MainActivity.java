package edu.oru.cit352.moseszhao.finalprojectbudgetapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    FragmentContainerView fragmentContainerView;
    Button btnIncome;
    Button btnPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentContainerView = findViewById(R.id.fragmentContainerView);
        btnIncome = findViewById(R.id.buttonIncome);
        btnPayment = findViewById(R.id.buttonPayment);

        btnIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean incomeIsClicked = true;
                openFragment(incomeIsClicked);
            }
        });

        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean incomeIsClicked = false;
                openFragment(incomeIsClicked);
            }
        });
    }
