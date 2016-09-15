package com.caraquri.bookmanager_android.model;

import android.databinding.BaseObservable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDataModel extends BaseObservable{
    @Expose
    @SerializedName("mail_address")
    public String mailAddress;

    @Expose
    @SerializedName("password")
    public String password;

    public UserDataModel(String mailAddress,String password){
        this.mailAddress = mailAddress;
        this.password = password;
    }

}
