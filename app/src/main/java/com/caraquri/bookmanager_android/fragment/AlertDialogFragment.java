package com.caraquri.bookmanager_android.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.caraquri.bookmanager_android.R;

public class AlertDialogFragment extends DialogFragment {
    public static final String ALERT_DIALOG_MESSAGE_KEY = "message";
    public static final String ALERT_DIALOG_SHOW_KEY = "show_alert_dialog";
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String message = getArguments().getString(ALERT_DIALOG_MESSAGE_KEY);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message);
        builder.setPositiveButton(getString(R.string.alertOkMessage), null);
        return builder.create();
    }
}
