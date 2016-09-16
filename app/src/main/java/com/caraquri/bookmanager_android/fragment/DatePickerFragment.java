package com.caraquri.bookmanager_android.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.widget.DatePicker;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    protected int year;
    protected int month;
    protected int dayOfMonth;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, dayOfMonth);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        String yearStr = String.valueOf(year);
        String monthStr = String.valueOf(++month);
        String dayStr = String.valueOf(day);
        String date = yearStr + "-" + monthStr + "-" + dayStr;
        Fragment target = getTargetFragment();
        Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_TEXT, date);
        target.onActivityResult(1, 1, intent);
    }
}
