package com.caraquri.bookmanager_android.widget;

import android.view.View;

public interface OnRecyclerItemClickListener {
    void onRecyclerItemClick(View v, String id, String name, String price, String date);
}
