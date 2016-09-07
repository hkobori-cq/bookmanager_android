package com.caraquri.bookmanager_android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookDataModel {
    public BookDataModel(String bookName){
        this.bookName = bookName;
    }

    @Expose
    @SerializedName("book_name")
    public String bookName;
}
