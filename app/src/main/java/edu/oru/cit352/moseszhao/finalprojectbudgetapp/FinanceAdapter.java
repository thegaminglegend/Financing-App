package edu.oru.cit352.moseszhao.finalprojectbudgetapp;

//Imports
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


/*
Name: Mengen Zhao
Professor: Dr. Osborne
Program: Contact List app
Date: 3、2/2023
Description: A contact list App that stores user's information.
Class that extends RecyclerView.Adapter to manage the data
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

    //Get the data give it to viewHolder
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        FinanceViewHolder cvh = (FinanceViewHolder) holder;
        //Get the information in the relevant position and set it to viewHolder
        cvh.getMoneyTextView().setText(String.valueOf(financeData.get(position).getAmount()));
        cvh.getDateTextView().setText(DateFormat.format("MM/dd/yyyy",financeData.get(position).getDate().getTimeInMillis()));
        cvh.getCategoryTextView().setText(financeData.get(position).getCategory());

        //Check if is pay or not and set the text color
        if(financeData.get(position).getPayOrIncome().equals("true")){
            cvh.getMoneyTextView().setTextColor(Color.parseColor("#e3513e"));
        } else {
            cvh.getMoneyTextView().setTextColor(Color.parseColor("#1a9991"));
        }


        //Check if is in delete mode
//        if(isDeleting){
//            //Make delete button to be visible
//            cvh.getDeleteButton().setVisibility(View.VISIBLE);
//            //Set listener to call method to delete the contact
//            cvh.getDeleteButton().setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    deleteItem(position);
//                }
//            });
//        } else {
//            cvh.getDeleteButton().setVisibility(View.INVISIBLE);
//        }
    }

    //Method to get the number of Items
    @Override
    public int getItemCount() {
        return financeData.size();
    }

    //Method to delete Item
//    private void deleteItem(int position) {
//        //Get contact to delete with position
//        Contact contact = contactData.get(position);
//        //Instance of ContactDataSource
//        ContactDataSource ds = new ContactDataSource(parentContext);
//        try {
//            //Open DB call method to delete the contact record and closeDB
//            ds.open();
//            boolean didDelete = ds.deleteContact(contact.getContactID());
//            ds.close();
//            //If successfully deleted remove the contact from the ArrayLIst and reload the display
//            if (didDelete) {
//                contactData.remove(position);
//                notifyDataSetChanged();
//            }
//            //If not show fail message
//            else {
//                Toast.makeText(parentContext, "Delete Failed!", Toast.LENGTH_LONG).show();
//            }
//        }
//        catch (Exception e) {
//            //Do nothing if fails
//        }
//    }

    //Method to set isDeleting to true or false
    //public void setDelete(boolean b) {
     //   isDeleting = b;
    //}


}