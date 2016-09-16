package com.caraquri.bookmanager_android.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.caraquri.bookmanager_android.R;
import com.caraquri.bookmanager_android.adapter.RecyclerViewAdapter;
import com.caraquri.bookmanager_android.api.BookDataGetClient;
import com.caraquri.bookmanager_android.databinding.FragmentListViewBinding;
import com.caraquri.bookmanager_android.model.BookDataEntity;
import com.caraquri.bookmanager_android.widget.EndlessScrollListener;
import com.caraquri.bookmanager_android.widget.OnRecyclerItemClickListener;
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
    private OnRecyclerItemClickListener listener;
    private Integer readData = 15;

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
            listener = (OnRecyclerItemClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnItemSelectedListener");
        }
    }


    public void initRecyclerView() {
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://app.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        BookDataGetClient bookDataGetClient = retrofit.create(BookDataGetClient.class);
        Call<BookDataEntity> call = bookDataGetClient.getBookData("0-"+readData.toString());
        binding.recyclerView.addOnScrollListener(new EndlessScrollListener((LinearLayoutManager)binding.recyclerView.getLayoutManager()) {
            @Override
            public void onLoadMore(int current_page) {
                readData = current_page * 20;
            }
        });
        call.enqueue(new Callback<BookDataEntity>() {
            /**
             * API通信が成功したときに呼ばれるメソッド
             * @param response
             * @param retrofit
             */
            @Override
            public void onResponse(Response<BookDataEntity> response, Retrofit retrofit) {
                RecyclerViewAdapter adapter = new RecyclerViewAdapter(response.body().getBookData(), listener);
                binding.recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }
}
