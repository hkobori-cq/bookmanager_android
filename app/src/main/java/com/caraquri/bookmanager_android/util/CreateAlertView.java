package com.caraquri.bookmanager_android.util;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.caraquri.bookmanager_android.R;

public class CreateAlertView {
    public void createAlertView(String title, Context context) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setNegativeButton(R.string.alertOkMessage, null)
                .show();
    }
}
