package com.caraquri.bookmanager_android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookDataModel {

    @Expose
    @SerializedName("name")
    public String bookName;
    @Expose
    @SerializedName("price")
    public Integer bookPrice;
    @Expose
    @SerializedName("purchase_date")
    public String purchaseDate;

    public BookDataModel(String bookName,Integer bookPrice,String purchaseDate){
        this.bookName = bookName;
        this.bookPrice = bookPrice;
        this.purchaseDate = purchaseDate;
    }
}
