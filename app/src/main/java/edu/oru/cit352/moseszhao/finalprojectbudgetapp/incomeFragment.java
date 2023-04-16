package edu.oru.cit352.moseszhao.finalprojectbudgetapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link incomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class incomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public incomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IncomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static incomeFragment newInstance(String param1, String param2) {
        incomeFragment fragment = new incomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_income, container, false);

        // Call the function to initialize the button
        initIncomeButtons(view);

        // Return the inflated view
        return view;
    }


    private void initIncomeButtons(View view){
        //*******Pay Button*****************************************************
        // Get a reference to Main activity
        MainActivity activity = (MainActivity) getActivity();

        //Find view with ID
        Button btnPay = view.findViewById(R.id.buttonPay);
        TextView category = activity.findViewById(R.id.textViewCategoryActual);

        //Set category when clicked
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category.setText("PAY");
            }
        });

        //*******GIFT Button*****************************************************
        //Find view with ID
        Button btnGift = view.findViewById(R.id.buttonGift);

        //Set category when clicked
        btnGift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category.setText("TOY");

            }
        });

        //*******SAVING Button*****************************************************
        //Find view with ID
        Button btnSaving = view.findViewById(R.id.buttonSaving);

        //Set category when clicked
        btnSaving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category.setText("SAVING");
            }
        });

        //*******Red-Bag Button*****************************************************
        //Find view with ID
        Button btnRedBag = view.findViewById(R.id.buttonRedBag);

        //Set category when clicked
        btnRedBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category.setText("REDBAG");
            }
        });

        //*******Transaction Button*****************************************************
        //Find view with ID
        Button btnTransaction = view.findViewById(R.id.buttonTransactionPay);

        //Set category when clicked
        btnTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category.setText("TRANSACTION");
            }
        });

        //*******Stock Button*****************************************************
        //Find view with ID
        Button btnStock = view.findViewById(R.id.buttonStock);

        //Set category when clicked
        btnStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category.setText("STOCK");
            }
        });

        //*******OTHER Button*****************************************************
        //Find view with ID
        Button btnOther = view.findViewById(R.id.buttonOtherIncome);

        //Set category when clicked
        btnOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category.setText("OTHER");
            }
        });

    }


}