package com.caraquri.bookmanager_android.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.caraquri.bookmanager_android.R;
import com.caraquri.bookmanager_android.databinding.FragmentAddViewBinding;

public class BookRegisterFragment extends Fragment{
    protected FragmentAddViewBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_add_view,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        binding = FragmentAddViewBinding.bind(getView());
    }



    public String getBookDataText(){
        return binding.bookDateField.getText().toString();
    }
}
