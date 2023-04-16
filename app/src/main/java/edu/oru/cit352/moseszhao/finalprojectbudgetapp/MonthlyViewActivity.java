package edu.oru.cit352.moseszhao.finalprojectbudgetapp;
//Imports
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MonthlyViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_view);

        //Initialize buttons
        initAddButton();

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


}