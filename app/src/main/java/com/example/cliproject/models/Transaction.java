package com.example.cliproject.models;

import java.io.Serializable;

public class Transaction implements Serializable {
    private Customer customer;
    private String date;
    private String time;
    private String location;
    private String transactionType;
    private String account;
    private String amount;
    private String availableBalance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    String owning;

    public String getOwning() {
        return owning;
    }

    public void setOwning(String owning) {
        this.owning = owning;
    }

    private Customer receavingcustomer;

    public Customer getReceavingcustomer() {
        return receavingcustomer;
    }

    public void setReceavingcustomer(Customer receavingcustomer) {
        this.receavingcustomer = receavingcustomer;
    }

    public Transaction(Customer customer,
                       String name,
                       String date,
                       String time,
                       String location,
                       String transactionType,
                       String account,
                       String amount,
                       String availableBalance, Customer receavingcustomer,String owning) {
        this.customer = customer;
        this.date = date;
        this.time = time;
        this.location = location;
        this.transactionType = transactionType;
        this.account = account;
        this.amount = amount;
        this.availableBalance = availableBalance;
        this.receavingcustomer=receavingcustomer;
        this.name=name;
        this.owning=owning;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getDate(){
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getAccount() {
        return account;
    }

    public String getAmount() {
        return amount;
    }

    public String getAvailableBalance() {
        return availableBalance;
    }
}
