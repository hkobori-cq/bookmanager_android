package com.caraquri.bookmanager_android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.caraquri.bookmanager_android.R;
import com.caraquri.bookmanager_android.databinding.FragmentUserViewBinding;

public class UserSettingsFragment extends Fragment{
    protected FragmentUserViewBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_view, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding = FragmentUserViewBinding.bind(getView());
    }
}
