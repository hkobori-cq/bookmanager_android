package com.caraquri.bookmanager_android.util;

import com.caraquri.bookmanager_android.model.BookDataModel;

import java.util.ArrayList;
import java.util.List;

public class DummyDataGenerator {
    public static List<BookDataModel> generateStringListData() {
        ArrayList<BookDataModel> list = new ArrayList<>();
        list.add(new BookDataModel("■アジア由来の牛"));
        list.add(new BookDataModel("黄牛"));
        return list;
    }

}
