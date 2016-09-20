package com.caraquri.bookmanager_android.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.caraquri.bookmanager_android.BR;
import com.caraquri.bookmanager_android.R;
import com.caraquri.bookmanager_android.databinding.ItemBookListRowBinding;
import com.caraquri.bookmanager_android.model.BookDataModel;
import com.caraquri.bookmanager_android.util.ChangeDateFormat;
import com.caraquri.bookmanager_android.widget.OnRecyclerItemClickListener;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<BookDataModel> bookData;
    private OnRecyclerItemClickListener listener;

    public class ViewHolder extends RecyclerView.ViewHolder {
        final ItemBookListRowBinding binding;

        public ViewHolder(ItemBookListRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    /**
     * 本のデータとリスナーをセットするメソッド
     *
     * @param mBookData
     * @param listener
     */
    public RecyclerViewAdapter(List<BookDataModel> mBookData, OnRecyclerItemClickListener listener) {
        bookData = mBookData;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemBookListRowBinding binding = ItemBookListRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final BookDataModel bookDataModel = bookData.get(position);
        bookDataModel.bookPrice = bookDataModel.bookPrice + R.string.japanese_yen;
        ChangeDateFormat format = new ChangeDateFormat();
        bookDataModel.purchaseDate = format.fromGMTFormatToDateFormat(bookDataModel.purchaseDate);
        holder.binding.bookImage.setImageResource(R.drawable.sample);
        holder.binding.setVariable(BR.bookData, bookDataModel);
        holder.binding.executePendingBindings();
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            /**
             * セルをクリックしたときのメソッド
             * MainActivityにデータを送るためにListenerにデータを送っている
             * @param view
             */
            @Override
            public void onClick(View view) {
                listener.onRecyclerItemClick(view, bookDataModel.id, bookDataModel.bookName, bookDataModel.bookPrice, bookDataModel.purchaseDate);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookData.size();
    }
}