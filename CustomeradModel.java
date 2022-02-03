package com.example.praful.Models;

public class CustomeradModel {
    String customerAddress;


    public CustomeradModel ( String customerAddress) {

        this.customerAddress = customerAddress;
    }


    public String getCustomerAddress ( ) {
        return customerAddress;
    }

    public void setCustomerAddress (String customerAddress) {
        this.customerAddress = customerAddress;
    }


}
