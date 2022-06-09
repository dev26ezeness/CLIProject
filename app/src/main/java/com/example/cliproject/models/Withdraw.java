package com.example.cliproject.models;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.cliproject.MainActivity;
import com.example.cliproject.R;
import com.example.cliproject.adapter.accounts_adapter;
import com.example.cliproject.fragments.OtherAmountDialogFragment;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

 public  class Withdraw extends AppCompatActivity implements OtherAmountDialogFragment.OtherAmountListener,com.example.cliproject.adapter.accounts_adapter .SlotAdapterListener {
    Customer customer = Customer.getInstance();
    ATM atm = ATM.getInstance();
    Bank bank = Bank.getInstance();
    ProgressDialog progressDialog;
    TransactionDialogFragment transactionDialogFragment = new TransactionDialogFragment();
    static boolean transactionCancelled = false;
    RecyclerView accounts;
    com.example.cliproject.adapter.accounts_adapter accounts_adapter;
    Customer receavingCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        progressDialog = new ProgressDialog(this);
        accounts=(RecyclerView) findViewById(R.id.Withdraw_to_rv);


        ArrayList<Customer> customers = new ArrayList<>();
        customers=MainActivity.customers;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Double Ammount = extras.getDouble("amount");
            dedugpendingAmmount(Ammount);
            //The key argument here must match that used in the other activity
        }
        accounts_adapter = new accounts_adapter(customers, Withdraw.this,customer);
       accounts.setLayoutManager(new LinearLayoutManager(Withdraw.this, LinearLayoutManager.VERTICAL, false));


        accounts.setHasFixedSize(true);
       accounts.setAdapter(accounts_adapter);
        accounts_adapter.setCategoryAdapterListener(this);

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            ATM.passwordTries--;
            performTransaction();
        } else {
            transactionDialogFragment.dismiss();
        }
    }

    public void performTransaction() {
        transactionDialogFragment.show(getSupportFragmentManager(), "transaction dialog");


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!transactionCancelled) {
                    if (bank.checkPin()) {
                        // correct password
                        String message = atm.performTransaction(atm.WithdrawAmount,receavingCustomer);
                        Toast.makeText(getApplicationContext(), message + ".", Toast.LENGTH_LONG).show();

                        if (message.toLowerCase().contains("successful")) {
                            String datePattern = "dd MMMM yyyy";
                            SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
                            String date = dateFormat.format(new Date());

                            String timePattern = "hh:mm a";
                            SimpleDateFormat timeFormat = new SimpleDateFormat(timePattern);
                            String time = timeFormat.format(new Date());

                            String location = "Wuse Zone 4";
                            String transactionType = "Pay";
                            String account = "Savings";
                            String name=customer.getName();

                            String owning;
                            if(customer.getName().equals("Bob"))
                            {
                                owning=String.valueOf(GlobalData.AlliceOwning);
                            }else {
                                owning=String.valueOf(GlobalData.BobOwning);
                            }



                            String amount = String.valueOf(atm.WithdrawAmount);
                            String availableBalance = String.valueOf(customer.getAccountBalance());

                            Transaction transaction = new Transaction(customer,name, date, time, location,
                                    transactionType, account, amount, availableBalance,receavingCustomer,owning);
                            Intent receiptIntent = new Intent(Withdraw.this, Receipt.class);
                            receiptIntent.putExtra("transaction", (Serializable) transaction);
                            startActivity(receiptIntent);
                            finish();

                        } else {
                            final Handler handler = new Handler();
                            atm.WithdrawAmount = 0;
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    transactionDialogFragment.dismiss();
                                }
                            }, 1500);
                        }
                    } else {
                        atm.WithdrawAmount = 0;
                        final Toast invalidPinToast = Toast.makeText(getApplicationContext(), getString(R.string.incorrect_pin), Toast.LENGTH_LONG);

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                invalidPinToast.show();
                                transactionDialogFragment.dismiss();
                                Intent pfragment = new Intent(Withdraw.this, PasswordFragment.class);
                                if (ATM.passwordTries > 0) {
                                    startActivityForResult(pfragment, 0);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Incorrect Pin. Card would be retained. Contact your bank for assistance.", Toast.LENGTH_LONG).show();
                                    Intent loginIntent = new Intent(Withdraw.this, MainActivity.class);
                                    Customer.clearInstance();
                                    startActivity(loginIntent);
                                    finish();
                                }
                            }
                        }, 500);

                    }
                }
            }
        }, 2500);


    }

    @Override
    public void OnOtherAmountSubmit(Double amount) {
        atm.WithdrawAmount = amount;

    }

     @Override
     public void onCategoryClick(Customer category) {
        selectAmmountAlertDialog();
        receavingCustomer=category;

     }

     @Override
     public void onPointerCaptureChanged(boolean hasCapture) {

     }


     public static class TransactionDialogFragment extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater layoutInflater = requireActivity().getLayoutInflater();

            builder.setView(layoutInflater.inflate(R.layout.transaction_dialog, null))
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            transactionCancelled = true;
                        }
                    });
            return builder.create();
        }
    }


     private void selectAmmountAlertDialog() {
       /* AlertDialog.Builder builder =
                new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);*/
         android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Withdraw.this);
         // builder.setTitle(context.getString(R.string.confirm));

         TextView title = new TextView(Withdraw.this);
// You Can Customise your Title here
         title.setText(Withdraw.this.getResources().getString(R.string.select_acount));
         title.setPadding(10, 10, 10, 10);
         title.setTextColor(Withdraw.this.getResources().getColor(R.color.orange));
         title.setGravity(Gravity.CENTER);
         title.setTextSize(20);

         builder.setCustomTitle(title);

         LinearLayout diagLayout = new LinearLayout(Withdraw.this);
         diagLayout.setOrientation(LinearLayout.VERTICAL);
         final EditText text = new EditText(Withdraw.this);
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
             transactionCancelled = false;
             Double x=Double.parseDouble(text.getText().toString());
             atm.WithdrawAmount = Double.parseDouble(text.getText().toString());
             progressDialog.setMessage("Please Wait...");
             progressDialog.setCancelable(false);

             if (atm.WithdrawAmount != 0){
                 performTransaction();
             }

         });
         builder.setNegativeButton(Withdraw.this.getResources().getString(R.string.cancel), (dialog, which) -> dialog.dismiss());
         builder.show();
     }

     public  void dedugpendingAmmount(Double Ammount){
         transactionCancelled = false;

         atm.WithdrawAmount = Ammount;
         progressDialog.setMessage("Please Wait...");
         progressDialog.setCancelable(false);

         if (atm.WithdrawAmount != 0){
             performTransaction();
         }
     }

}
