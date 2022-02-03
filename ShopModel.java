package com.example.praful.Models;

import java.io.Serializable;

public class ShopModel implements Serializable {

    String shopimg;
    String shopname;
    String shopadd;

    public ShopModel ( ) {
    }

    public ShopModel (String shopimg, String shopname, String shopadd) {
        this.shopimg = shopimg;
        this.shopname = shopname;
        this.shopadd = shopadd;
    }

    public String getShopimg ( ) {
        return shopimg;
    }

    public void setShopimg (String shopimg) {
        this.shopimg = shopimg;
    }

    public String getShopname ( ) {
        return shopname;
    }

    public void setShopname (String shopname) {
        this.shopname = shopname;
    }

    public String getShopadd ( ) {
        return shopadd;
    }

    public void setShopadd (String shopadd) {
        this.shopadd = shopadd;
    }

}
