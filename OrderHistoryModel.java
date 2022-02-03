package com.example.praful.Models;

public class OrderHistoryModel {

    String currentTime;
    String currentDate;
    String productName;
    String ProDesc;
    String productPrice;
    String totalQuantity;
    int totalPrice;
    String documentId;

    public OrderHistoryModel ( ) {
    }

    public OrderHistoryModel (String currentTime, String currentDate, String productName,String ProDesc, String productPrice, String totalQuantity, int totalPrice, String documentId) {
        this.currentTime = currentTime;
        this.currentDate = currentDate;
        this.productName = productName;
        this.ProDesc = ProDesc;
        this.productPrice = productPrice;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
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

     public String getProDesc ( ) {
        return ProDesc;
    }

    public void setProDesc (String proDesc) {
        this.ProDesc = proDesc;
    }

    public String getProductPrice ( ) {
        return productPrice;
    }

    public void setProductPrice (String productPrice) {
        this.productPrice = productPrice;
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

    public String getDocumentId ( ) {
        return documentId;
    }

    public void setDocumentId (String documentId) {
        this.documentId = documentId;
    }
}
