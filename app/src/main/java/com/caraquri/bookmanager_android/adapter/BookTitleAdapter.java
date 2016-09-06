package com.caraquri.bookmanager_android.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.caraquri.bookmanager_android.BR;
import com.caraquri.bookmanager_android.databinding.ItemBookListRowBinding;
import com.caraquri.bookmanager_android.model.BookDataModel;

import java.util.List;

public class BookTitleAdapter extends RecyclerView.Adapter<BookTitleAdapter.ViewHolder> {
    protected List<BookDataModel> dataset;
    public class ViewHolder extends RecyclerView.ViewHolder {
        final ItemBookListRowBinding binding;

        public ViewHolder(ItemBookListRowBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public BookTitleAdapter(List<BookDataModel> myDataset){
        dataset = myDataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        ItemBookListRowBinding binding = ItemBookListRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        BookDataModel bookDataModel = dataset.get(position);
        holder.binding.setVariable(BR.bookData,bookDataModel);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount(){
        return dataset.size();
    }
}