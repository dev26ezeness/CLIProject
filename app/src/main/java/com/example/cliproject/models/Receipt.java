package com.example.cliproject.models;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cliproject.R;

public class Receipt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        Intent thisIntent = getIntent();
        Transaction transaction = (Transaction) thisIntent.getSerializableExtra("transaction");

        TextView dateTextView = findViewById(R.id.date_textview);
        TextView timeTextView = findViewById(R.id.time_textview);
        TextView locationTextView = findViewById(R.id.location_textview);
        TextView transactionTypeTextView = findViewById(R.id.transaction_type_textview);
        TextView accountTextView = findViewById(R.id.account_textview);
        TextView amountTextView = findViewById(R.id.amount_textview);
        TextView balanceTextView = findViewById(R.id.available_balance_textview);


        TextView tv_owning = findViewById(R.id.tv_owning);

        TextView tv_owning_lebel = findViewById(R.id.tv_owning_lebel);










        TextView account_textview_r= findViewById(R.id.account_textview_r);
        TextView amount_textview_r = findViewById(R.id.amount_textview_r);
        TextView available_balance_textview_r = findViewById(R.id.available_balance_textview_r);





        dateTextView.setText(transaction.getDate());
        timeTextView.setText(transaction.getTime());
        locationTextView.setText(transaction.getLocation());
        transactionTypeTextView.setText(transaction.getTransactionType());
        accountTextView.setText(transaction.getName());

        balanceTextView.setText(transaction.getAvailableBalance());


        amount_textview_r.setText(transaction.getAmount());
        amountTextView.setText(transaction.getAmount());


Double paid=0.0,asked=0.0,finalpaid=0.0;
if(null!=transaction.getReceavingcustomer())

        tv_owning.setText("Owning "+transaction.owning+" to "+transaction.getReceavingcustomer().getName());
        if(transaction.owning.equals("0.0")) {
            tv_owning_lebel.setVisibility(View.GONE);
            tv_owning.setVisibility(View.GONE);
            amount_textview_r.setText(transaction.getAmount());
            amountTextView.setText(transaction.getAmount());
        }else {
            tv_owning_lebel.setVisibility(View.VISIBLE);
            tv_owning.setVisibility(View.VISIBLE);
            paid=Double.parseDouble(transaction.owning);
            asked=Double.parseDouble(transaction.getAmount());
            finalpaid=asked-paid;
            amountTextView.setText(String.valueOf(finalpaid));
            amount_textview_r.setText(String.valueOf(finalpaid));
        }
        if(transaction.getReceavingcustomer()!=null)
        account_textview_r.setText(transaction.getReceavingcustomer().getName());


        /*double receaver_available_balance=Double.parseDouble(transaction.getAmount())+Double.parseDouble(transaction.getAvailableBalance());
        available_balance_textview_r.setText(String.valueOf(receaver_available_balance));*/

        if(transaction.getReceavingcustomer()!=null)
        available_balance_textview_r.setText(String.valueOf(transaction.getReceavingcustomer().getAccountBalance()));
       /* else {
            if(transaction.getName().equals("Bob"))
            {    if(Double.parseDouble(transaction.getAmount())>GlobalData.AlliceOwning )
              GlobalData.AlliceOwning=0.0;
            else {   Double remaining =Double.parseDouble(transaction.getAccount());
                GlobalData.AlliceOwning=GlobalData.AlliceOwning-remaining;}
            }else {
                if(Double.parseDouble(transaction.getAmount())>GlobalData.BobOwning )
                    GlobalData.AlliceOwning=0.0;
                else {   Double remaining =Double.parseDouble(transaction.getAccount());
                    GlobalData.BobOwning=GlobalData.BobOwning-remaining;}
            }
        }*/

        Button homeButton = findViewById(R.id.home_button);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent transactionHome = new Intent(Receipt.this, Transactions.class);
                startActivity(transactionHome);
            }
        });

        ATM atm = ATM.getInstance();
        atm.WithdrawAmount = 0;

    }
}
