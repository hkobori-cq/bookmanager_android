package com.caraquri.bookmanager_android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookDataEntity {
    @Expose
    @SerializedName("result")
    private List<BookDataModel> bookDataModels;

    public List<BookDataModel> getBookData(){
        return bookDataModels;
    }

    public void setBookData(List<BookDataModel> bookDataModels){
        this.bookDataModels = bookDataModels;
    }
}
