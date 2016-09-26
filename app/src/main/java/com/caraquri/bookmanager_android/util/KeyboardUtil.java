package com.caraquri.bookmanager_android.util;


import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class KeyboardUtil {
    private static final int FLAG = 0;
    public void hideKeyboard(View view, Context context){
        InputMethodManager manager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(view.getWindowToken(),FLAG);
    }
}
