package com.caraquri.bookmanager_android.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.caraquri.bookmanager_android.BR;
import com.caraquri.bookmanager_android.databinding.ItemBookListRowBinding;
import com.caraquri.bookmanager_android.model.BookDataModel;
import com.caraquri.bookmanager_android.util.ChangeDateFormat;
import com.caraquri.bookmanager_android.widget.OnItemClickListener;

import java.util.List;


public class BookTitleAdapter extends RecyclerView.Adapter<BookTitleAdapter.ViewHolder> {
    protected List<BookDataModel> dataset;
    OnItemClickListener listener;

    public class ViewHolder extends RecyclerView.ViewHolder {
        final ItemBookListRowBinding binding;

        public ViewHolder(ItemBookListRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public BookTitleAdapter(List<BookDataModel> myDataset, OnItemClickListener listener) {
        dataset = myDataset;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemBookListRowBinding binding = ItemBookListRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final BookDataModel bookDataModel = dataset.get(position);
        bookDataModel.bookPrice = bookDataModel.bookPrice + "円";
        ChangeDateFormat format = new ChangeDateFormat();
        bookDataModel.purchaseDate = format.changeDateFormat(bookDataModel.purchaseDate);
        holder.binding.setVariable(BR.bookData, bookDataModel);
        holder.binding.executePendingBindings();
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(view, bookDataModel.id, bookDataModel.bookName, bookDataModel.bookPrice, bookDataModel.purchaseDate);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}