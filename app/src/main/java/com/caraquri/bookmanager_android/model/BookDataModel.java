package com.caraquri.bookmanager_android.model;

import android.databinding.BaseObservable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookDataModel extends BaseObservable {

    @Expose
    @SerializedName("id")
    public String id;
    @Expose
    @SerializedName("image_url")
    public String imageUrl;
    @Expose
    @SerializedName("name")
    public String bookName;
    @Expose
    @SerializedName("price")
    public String bookPrice;
    @Expose
    @SerializedName("purchase_date")
    public String purchaseDate;

    public BookDataModel(String bookName, String bookPrice, String purchaseDate) {
        this.bookName = bookName;
        this.bookPrice = bookPrice;
        this.purchaseDate = purchaseDate;
    }

}
