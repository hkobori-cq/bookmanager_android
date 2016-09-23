package com.caraquri.bookmanager_android.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.caraquri.bookmanager_android.R;
import com.caraquri.bookmanager_android.adapter.RecyclerViewAdapter;
import com.caraquri.bookmanager_android.api.BookDataGetService;
import com.caraquri.bookmanager_android.api.DataClient;
import com.caraquri.bookmanager_android.databinding.FragmentListViewBinding;
import com.caraquri.bookmanager_android.model.BookDataEntity;
import com.caraquri.bookmanager_android.widget.EndlessScrollListener;
import com.caraquri.bookmanager_android.widget.OnRecyclerItemClickListener;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class RecyclerLayoutFragment extends Fragment {
    private Integer readData = 15;
    private FragmentListViewBinding binding;
    private OnRecyclerItemClickListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_view, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding = FragmentListViewBinding.bind(getView());
        initRecyclerView();
        isRecyclerViewScrolled();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = getActivity();
        listener = (OnRecyclerItemClickListener) activity;
    }


    private void initRecyclerView() {
        DataClient client = new DataClient();
        Call<BookDataEntity> call = client.bookDataLoadClient("0-" + readData.toString());
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

    private void isRecyclerViewScrolled(){
        binding.recyclerView.addOnScrollListener(new EndlessScrollListener((LinearLayoutManager) binding.recyclerView.getLayoutManager()) {
            @Override
            public void onLoadMore(int current_page) {
                readData = current_page * 20;
            }
        });
    }
}
