package com.caraquri.bookmanager_android.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.caraquri.bookmanager_android.R;
import com.caraquri.bookmanager_android.activity.AddActivity;
import com.caraquri.bookmanager_android.adapter.RecyclerViewAdapter;
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
    private static final String LOAD_DATA_START = "0-";
    private int LOAD_DATA_END = 15;
    private FragmentListViewBinding binding;
    private OnRecyclerItemClickListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_list_view, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding = FragmentListViewBinding.bind(getView());
        initRecyclerView();
        isRecyclerViewScrolled();
        initToolbar();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = getActivity();
        listener = (OnRecyclerItemClickListener) activity;
    }

    private void initToolbar(){
        ActionBar bar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (bar != null) {
            bar.setDisplayShowHomeEnabled(true);
            bar.setHomeButtonEnabled(true);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(getActivity(), AddActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }




    private void initRecyclerView() {
        DataClient client = new DataClient();
        Call<BookDataEntity> call = client.bookDataLoadClient(LOAD_DATA_START + LOAD_DATA_END);
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

    private void isRecyclerViewScrolled() {
        binding.recyclerView.addOnScrollListener(new EndlessScrollListener((LinearLayoutManager) binding.recyclerView.getLayoutManager()) {
            @Override
            public void onLoadMore(int current_page) {
                LOAD_DATA_END = current_page * 20;
            }
        });
    }
}
