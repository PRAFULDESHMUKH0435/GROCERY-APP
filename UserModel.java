package com.example.praful.Models;

public class UserModel {

    String userName;
    String userNumber;
    String userEmail;
    String userPassword;
    String userAddress;
    String prafulad;

    public UserModel (String prafulad) {
        this.prafulad = prafulad;
    }



    public UserModel (String s, String userNumber, String userEmail, String userPassword, String toUpperCase, String userImage) {
    }

    public UserModel (String userName,String userNumber, String userEmail, String userPassword, String userAddress) {
        this.userName = userName;
        this.userNumber = userNumber;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userAddress = userAddress;

    }


    public String getUserName ( ) {
        return userName;
    }

    public void setUserName (String userName) {
        this.userName = userName;
    }






    public String getUserAddress ( ) {
        return userAddress;
    }

    public void setUserAddress (String userAddress) {
        this.userAddress = userAddress;
    }



     public String getUserNumber ( ) {
        return userNumber;
    }

    public void setUserNumber (String userNumber) {
        this.userNumber = userNumber;
    }



    public String getUserEmail ( ) {
        return userEmail;
    }

    public void setUserEmail (String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword ( ) {
        return userPassword;
    }

    public void setUserPassword (String userPassword) {
        this.userPassword = userPassword;
    }


}
