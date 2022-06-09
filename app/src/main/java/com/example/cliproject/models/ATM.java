package com.example.cliproject.models;


import com.example.cliproject.MainActivity;

import java.util.ArrayList;

public class ATM {
    static private String pin;
    private static ATM atm = null;
    private Customer customer = Customer.getInstance();
    static private Bank bank;
    static public int passwordTries;
    public double WithdrawAmount = 0;
    private Customer customerReceaver;
    private ATM(){

    };

    public static ATM init(){
        atm = new ATM();
        passwordTries = 3;
        return atm;
    }

    public static ATM getInstance(){
        bank = Bank.getInstance();
        return atm;
    }

    public String getPin(){
        // pin entered by the user
        return pin;
    }

    public void setPin(String pin){
        this.pin = pin;
    }

    public static void clearInstance(){
        atm = null;
        pin = null;
    }

    public String performTransaction(double WithdrawAmount,Customer receavingCustomer){
        if (bank.checkBalance(WithdrawAmount)){
            int approval = bank.getApproval();

                customer.changeAccountBalance(WithdrawAmount);
              int i=  MainActivity.customers.indexOf(receavingCustomer);

            int payer=  MainActivity.customers.indexOf(customer);
            if(payer!=-1)
            {
                MainActivity.customers.get(payer).changeAccountBalance(WithdrawAmount);
            }else {

                if(customer.getName().equals("Bob")){
                    MainActivity.customers.get(0).changeAccountBalance(WithdrawAmount);
                }else {
                    MainActivity.customers.get(1).changeAccountBalance(WithdrawAmount);
                }



            }

           //receavingCustomer.AddAccountBalance(WithdrawAmount);
            if(i!=-1)
           MainActivity.customers.get(i).AddAccountBalance(WithdrawAmount);
            else {
                if(customer.getName().equals("Bob")){
                    MainActivity.customers.get(0).AddAccountBalance(WithdrawAmount);
                    if(customer.getAccountBalance()>GlobalData.AlliceOwning )
                        GlobalData.AlliceOwning=0.0;
                    else {
                        GlobalData.AlliceOwning=GlobalData.AlliceOwning-customer.getAccountBalance();}
                }else {
                    MainActivity.customers.get(1).AddAccountBalance(WithdrawAmount);

                    if(customer.getAccountBalance()>GlobalData.BobOwning)
                        GlobalData.BobOwning=0.0;
                    else {
                        GlobalData.BobOwning=GlobalData.BobOwning-customer.getAccountBalance();}
                }
            }

            ArrayList<Customer> customerss = new ArrayList<>();
            customerss = MainActivity.customers;
                return "Transaction Successful";

        } else {

               Double payableAmmount=customer.getAccountBalance();
            customer.changeAccountBalance(payableAmmount);
            int i=  MainActivity.customers.indexOf(receavingCustomer);

            double currentBallace=receavingCustomer.getAccountBalance();
            currentBallace +=WithdrawAmount;
            //receavingCustomer.AddAccountBalance(WithdrawAmount);
            MainActivity.customers.get(i).AddAccountBalance(payableAmmount);

            ArrayList<Customer> customerss = new ArrayList<>();
            customerss = MainActivity.customers;
            Double remaining =WithdrawAmount-payableAmmount;
            if(customer.getName().equals("Bob"))
            {
                GlobalData.AlliceOwning=remaining;
            }else {
                GlobalData.BobOwning=remaining;
            }
            String remain=String.valueOf(remaining);
           // return "Owing"+remain+" to Alice";

            return "Transaction Successful";
        }

       // return "";
    }


}
