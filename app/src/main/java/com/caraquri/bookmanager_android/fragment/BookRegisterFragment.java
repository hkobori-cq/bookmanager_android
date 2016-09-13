package com.caraquri.bookmanager_android.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.caraquri.bookmanager_android.R;
import com.caraquri.bookmanager_android.databinding.FragmentAddViewBinding;

public class BookRegisterFragment extends Fragment {
    protected FragmentAddViewBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_view, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding = FragmentAddViewBinding.bind(getView());
        initFieldData();
        TappedDateButton();
    }

    private void initFieldData(){
        Intent intent = getActivity().getIntent();
        binding.bookTitleField.setText(intent.getStringExtra("name"));
        binding.bookPriceField.setText(intent.getStringExtra("price"));
        binding.bookDateField.setText(intent.getStringExtra("date"));
    }

    private void TappedDateButton() {
        binding.bookDateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                DatePickerFragment datePicker = new DatePickerFragment();
                datePicker.setTargetFragment(BookRegisterFragment.this, 0);
                datePicker.show(manager, "datePicker");
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String date = data.getStringExtra(Intent.EXTRA_TEXT);
        binding.bookDateField.setText(date);
        super.onActivityResult(requestCode, resultCode, data);
    }

}
