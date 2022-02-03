package com.example.praful.Models;

public class MyCartModel {

    String currentTime;
    String currentDate;
    String productName;
    String productPrice;
    String totalQuantity;
    String ProDesc;
    int totalPrice;
    String documentId;

    public MyCartModel ( ) {
    }

    public MyCartModel (String currentTime, String currentDate, String productName, String productPrice,String ProDesc, String totalQuantity, int totalPrice) {
        this.currentTime = currentTime;
        this.currentDate = currentDate;
        this.productName = productName;
        this.productPrice = productPrice;
        this.ProDesc = ProDesc;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
    }


    public String getDocumentId ( ) {
        return documentId;
    }

    public void setDocumentId (String documentId) {
        this.documentId = documentId;
    }

    public String getCurrentTime ( ) {
        return currentTime;
    }

    public void setCurrentTime (String currentTime) {
        this.currentTime = currentTime;
    }

    public String getCurrentDate ( ) {
        return currentDate;
    }

    public void setCurrentDate (String currentDate) {
        this.currentDate = currentDate;
    }

    public String getProductName ( ) {
        return productName;
    }

    public void setProductName (String productName) {
        this.productName = productName;
    }

    public String getProductPrice ( ) {
        return productPrice;
    }

    public String getProDesc ( ) {
        return ProDesc;
    }

    public void setProductPrice (String productPrice) {
        this.productPrice = productPrice;
    }

    public void setProDesc (String proDesc) {
        this.ProDesc = ProDesc;
    }

    public String getTotalQuantity ( ) {
        return totalQuantity;
    }

    public void setTotalQuantity (String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getTotalPrice ( ) {
        return totalPrice;
    }

    public void setTotalPrice (int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
