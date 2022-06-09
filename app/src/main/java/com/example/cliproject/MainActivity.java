package com.example.cliproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.cliproject.R;
import com.example.cliproject.models.ATM;
import com.example.cliproject.models.Customer;
import com.example.cliproject.models.Transaction;
import com.example.cliproject.models.Transactions;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
   public static ArrayList<Customer> customers = new ArrayList<>();
    static Customer customer;
    static double b=0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // creating sim customers
        if(null==customers||customers.size()==0) {
            customers.add(Customer.init("Bob", 0.0, "Bob", "9903"));
            customers.add(Customer.init("Alice", 0.0, "Alice", "3285"));
        }

        Button insertButton = findViewById(R.id.insert_card_button);
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogFragment fragment = new dialogFragment();
                fragment.show(getSupportFragmentManager(), "cardnumber");
            }
        });
    }

    static double generateAmount(){
        Random random = new Random();
        return Math.random() * random.nextInt(100000);
    }


    public static class dialogFragment extends DialogFragment{
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.select_account)
                    .setItems(getResources().getStringArray(R.array.cardnumbers), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String selectedNum = getResources().getStringArray(R.array.cardnumbers)[i];
                            Log.v("MainActivity", "Selected number " + selectedNum);
                            for (int a = 0; a < customers.size(); a++){
                                switch (selectedNum){
                                    case "Bob":
                                        if(customers.get(a).getName().equals("Bob"))
                                       b = customers.get(a).getAccountBalance();
                                        customer = Customer.init("Bob", b, "Bob", "9903");
                                            break;

                                    case "Alice":
                                        if(customers.get(a).getName().equals("Alice"))
                                       b = customers.get(a).getAccountBalance();
                                        customer = Customer.init("Alice",b, "Alice", "3285");
                                        break;


                                }
                            }
                            if (customer != null){
                                Log.v("MainActivity", "Customer name: " + customer.getName());
                                passwordDialogFragment passwordFragment = new passwordDialogFragment();
                                passwordFragment.show(getActivity().getSupportFragmentManager(), "password");
                            }
                        }
                    });
            return builder.create();
        }

        public static class passwordDialogFragment extends DialogFragment{

            @NonNull
            @Override
            public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                LayoutInflater layoutInflater = requireActivity().getLayoutInflater();

                builder.setView(layoutInflater.inflate(R.layout.password_dialog, null))
                        .setPositiveButton(R.string.enter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                final EditText password = getDialog().findViewById(R.id.password_edittext);
                                ATM atm = ATM.init();
                                atm.setPin(password.getText().toString());
                                final Intent transactionsIntent = new Intent(getActivity().getApplicationContext(), Transactions.class);
                                startActivity(transactionsIntent);
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                return builder.create();
            }
        }
    }
}



