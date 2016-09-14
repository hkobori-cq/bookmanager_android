package com.caraquri.bookmanager_android.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.caraquri.bookmanager_android.R;
import com.caraquri.bookmanager_android.adapter.BookTitleAdapter;
import com.caraquri.bookmanager_android.api.BookDataClient;
import com.caraquri.bookmanager_android.databinding.FragmentAddViewBinding;
import com.caraquri.bookmanager_android.databinding.FragmentListViewBinding;
import com.caraquri.bookmanager_android.model.BookDataEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

public class RecycleLayoutFragment extends Fragment {
    FragmentListViewBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_view, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecyclerView();
    }

    public void initRecyclerView(){
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://app.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        BookDataClient bookDataClient = retrofit.create(BookDataClient.class);
        Call<BookDataEntity> call = bookDataClient.getBookData("0-10");
        call.enqueue(new Callback<BookDataEntity>() {
            @Override
            public void onResponse(Response<BookDataEntity> response, Retrofit retrofit) {

            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }
}
