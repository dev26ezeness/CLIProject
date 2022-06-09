package com.example.cliproject.models;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cliproject.MainActivity;
import com.example.cliproject.R;

public class Transactions  extends AppCompatActivity {


    Button btn_logut;
    ATM atm = ATM.getInstance();
    Bank bank = Bank.getInstance();
    ProgressDialog progressDialog;
    static boolean transactionCancelled = false;
    Withdraw.TransactionDialogFragment transactionDialogFragment = new Withdraw.TransactionDialogFragment();
    TextView balanceTextView;
    Customer receavingCustomer;
    TextView tv_owning;
    Boolean owningflag=false;
    Double owningAmount=0.0;

    final Customer customer = Customer.getInstance();
        @Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_transactions);
            progressDialog = new ProgressDialog(this);
            btn_logut=findViewById(R.id.btn_logut);

            tv_owning=findViewById(R.id.tv_owning);
            if(customer.getName().equals("Bob")){
                if(GlobalData.AlliceOwning==0.0){
                    tv_owning.setVisibility(View.GONE);
                    tv_owning.setText("Owing "+String.valueOf(GlobalData.AlliceOwning)+" to Alice");
                }else {
                    tv_owning.setText("Owing "+String.valueOf(GlobalData.AlliceOwning)+" to Alice");
                    tv_owning.setVisibility(View.VISIBLE);
                    receavingCustomer=MainActivity.customers.get(1);
                    owningflag=true;
                    owningAmount=GlobalData.AlliceOwning;
                }
            }else {
                if(GlobalData.BobOwning==0.0){
                    tv_owning.setVisibility(View.GONE);
                    tv_owning.setText("Owing "+String.valueOf(GlobalData.BobOwning)+" to Bob");
                }else {
                    tv_owning.setText("Owing "+String.valueOf(GlobalData.BobOwning)+" to Bob");
                    tv_owning.setVisibility(View.VISIBLE);
                    receavingCustomer=MainActivity.customers.get(0);
                    owningflag=true;
                    owningAmount=GlobalData.BobOwning;
                }

            }


    final ProgressDialog progressDialog = new ProgressDialog(this);
    progressDialog.setMessage("Please Wait...");
    progressDialog.show();

    final Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
        @Override
        public void run() {
//                progressDialog.show();
            progressDialog.dismiss();
        }
    }, 1200);



    ATM atm = ATM.getInstance();
    Bank bank = Bank.init();
    TextView welcomeTextView = findViewById(R.id.welcome_textview);
    welcomeTextView.setText("Welcome, " + customer.getName());

    Button  PayButton = findViewById(R.id.pay_button);
   // Button checkBalanceButton = findViewById(R.id.check_balance_button);
    Button TopUpButton = findViewById(R.id.top_up_button);


        balanceTextView = findViewById(R.id.balance_amount_textview);

        double amount = round(customer.getAccountBalance(),2);
            balanceTextView.setText((String.valueOf(amount)));




    PayButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent WithdrawIntent = new Intent(Transactions.this, Withdraw.class);
            startActivity(WithdrawIntent);
        }
    });

/*    checkBalanceButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent balanceIntent = new Intent(Transactions.this, Balance.class);
            startActivity(balanceIntent);
        }
    });*/

            TopUpButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            TopUpAlertDialog();

        }
    });
    btn_logut.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent loginIntent = new Intent(Transactions.this, MainActivity.class);
            startActivity(loginIntent);
            finish();
        }
    });
}

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }


    private void TopUpAlertDialog() {
       /* AlertDialog.Builder builder =
                new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);*/
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Transactions.this);
        // builder.setTitle(context.getString(R.string.confirm));

        TextView title = new TextView(Transactions.this);
// You Can Customise your Title here
        title.setText(Transactions.this.getResources().getString(R.string.select_acount_top));
        title.setPadding(10, 10, 10, 10);
        title.setTextColor(Transactions.this.getResources().getColor(R.color.orange));
        title.setGravity(Gravity.CENTER);
        title.setTextSize(20);

        builder.setCustomTitle(title);

        LinearLayout diagLayout = new LinearLayout(Transactions.this);
        diagLayout.setOrientation(LinearLayout.VERTICAL);
        final EditText text = new EditText(Transactions.this);
        //  text.setText(Home.this.getResources().getString(R.string.alert_log_out));
        text.setPadding(10, 10, 10, 10);
        text.setGravity(Gravity.CENTER);
        text.setTextSize(15);
        text.setInputType(InputType.TYPE_CLASS_NUMBER );

        diagLayout.addView(text);
        builder.setView(diagLayout);


        // builder.setMessage(context.getResources().getString(R.string.alert_log_out));
        builder.setPositiveButton("ok" ,(dialog, which) -> {
            dialog.dismiss();

            customer.AddAccountBalance(Double.parseDouble(text.getText().toString()));
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            double amount = round(customer.getAccountBalance(),2);
            balanceTextView.setText((String.valueOf(amount)));
for(int i=0;i<MainActivity.customers.size();i++)
{
    if(MainActivity.customers.get(i).getName().equals(customer.getName()))
    {
        MainActivity.customers.get(i).AddAccountBalance(amount);
    }
    if(owningflag)
    {
        Intent pay = new Intent(Transactions.this, Withdraw.class);
        pay.putExtra("amount",owningAmount);
        startActivity(pay);
    }

}



        });
        builder.setNegativeButton(Transactions.this.getResources().getString(R.string.cancel), (dialog, which) -> dialog.dismiss());
        builder.show();
    }











}