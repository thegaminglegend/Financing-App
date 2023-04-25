package edu.oru.cit352.moseszhao.finalprojectbudgetapp;

//Imports
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


import android.content.Context;
import android.widget.Toast;

/*
Name: Mengen Zhao
Professor: Dr. Osborne
Program: Financing App
Date: 4/25/2023
Description: An app that records financial spending and gain and displays it each month
Finance Adapter Class that extends RecyclerView.Adapter to manage the data for the recycle view
to display a financial instance and delete it
*/

public class FinanceAdapter extends RecyclerView.Adapter {
    //Instance Variables
    private ArrayList<Finance> financeData;
    //private boolean isDeleting;
    private Context parentContext;

    //Embedded class for viewHolder that stores the data of the financial instance
    public class FinanceViewHolder extends RecyclerView.ViewHolder {
        //Reference Variables
        public TextView textMoney;
        public TextView textDate;
        public TextView textCategory;

        //Constructor
        public FinanceViewHolder(@NonNull View itemView) {
            super(itemView);
            //Find view with ID
            textMoney = itemView.findViewById(R.id.textMoney);
            textDate = itemView.findViewById(R.id.textDate);
            textCategory = itemView.findViewById(R.id.textCategory);
        }

        //Getters
        public TextView getMoneyTextView() {
            return textMoney;
        }
        public TextView getDateTextView() {
            return textDate;
        }
        public TextView getCategoryTextView() {
            return textCategory;
        }

    }

    //Constructor
    public FinanceAdapter(ArrayList<Finance> arrayList, Context context) {
        financeData = arrayList;
        parentContext = context;
    }

    @NonNull
    @Override
    //Initialize view holder
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.finance_instance, parent, false);
        return new FinanceViewHolder(v);
    }

    //Get the data give it to viewHolder that would be shown in recycle view
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        FinanceViewHolder cvh = (FinanceViewHolder) holder;
        //Get the information in the relevant position and set it to viewHolder
        cvh.getMoneyTextView().setText(String.valueOf(financeData.get(position).getAmount()));
        cvh.getDateTextView().setText(DateFormat.format("MM/dd/yyyy",financeData.get(position).getDate().getTimeInMillis()));
        cvh.getCategoryTextView().setText(financeData.get(position).getCategory());

        //Check if the financial instance is pay or not and set the text color
        if(financeData.get(position).getPayOrIncome().equals("true")){
            cvh.getMoneyTextView().setTextColor(Color.parseColor("#e3513e"));
        } else {
            cvh.getMoneyTextView().setTextColor(Color.parseColor("#1a9991"));
        }

        //delete a financial instance when long click an item
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                //Display a confirmation dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(parentContext);
                builder.setTitle("Delete Item")
                        .setMessage("Are you sure you want to delete this item?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Remove the financial instance
                                deleteItem(position);

                                //Notify the adapter that an item has been removed
                                notifyItemRemoved(position);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();

                // Return true
                return true;
            }
        });

    }

    //Method to get the number of Items
    @Override
    public int getItemCount() {
        return financeData.size();
    }

    //Function to delete Item
    private void deleteItem(int position) {
        //Get finance instance to delete with position
        Finance finance = financeData.get(position);
        //Instance of financedatasource
        FinanceDataSource fs = new FinanceDataSource(parentContext);
        try {
            //Open DB call method to delete the contact record and closeDB
            fs.open();
            boolean didDelete = fs.deleteFinance(finance.getFinanceID());
            fs.close();
            //If successfully deleted remove the contact from the ArrayLIst and reload the display
            if (didDelete) {
                financeData.remove(position);
                notifyDataSetChanged();
            }
            //If not show fail message
            else {
                Toast.makeText(parentContext, "Delete Failed!", Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e) {
            //Do nothing if fails
        }
    }

}