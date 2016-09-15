package com.caraquri.bookmanager_android.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.caraquri.bookmanager_android.R;
import com.caraquri.bookmanager_android.adapter.BookTitleAdapter;
import com.caraquri.bookmanager_android.api.BookDataClient;
import com.caraquri.bookmanager_android.databinding.FragmentAddViewBinding;
import com.caraquri.bookmanager_android.databinding.FragmentListViewBinding;
import com.caraquri.bookmanager_android.model.BookDataEntity;
import com.caraquri.bookmanager_android.widget.OnItemClickListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

public class RecyclerLayoutFragment extends Fragment {
    private FragmentListViewBinding binding;
    OnItemClickListener listener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_view, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding = FragmentListViewBinding.bind(getView());
        initRecyclerView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = getActivity();
        try {
            listener = (OnItemClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnItemSelectedListener");
        }
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
                BookTitleAdapter adapter = new BookTitleAdapter(response.body().getBookData(),listener);
                binding.recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }
}
