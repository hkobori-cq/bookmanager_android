package com.caraquri.bookmanager_android.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.caraquri.bookmanager_android.R;
import com.caraquri.bookmanager_android.databinding.FragmentUserRegisterBinding;

public class UserSettingsFragment extends Fragment {
    protected FragmentUserRegisterBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_register, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding = FragmentUserRegisterBinding.bind(getView());
        tappedBackLayout();
    }

    /**
     * @see UserLoginFragment
     */
    private void tappedBackLayout() {
        binding.userRegisterFragment.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager inputMethodManager = (InputMethodManager) getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(binding.userEmailField
                        .getWindowToken(), 0);
                inputMethodManager.hideSoftInputFromWindow(binding.userPasswordField
                        .getWindowToken(), 0);
                inputMethodManager.hideSoftInputFromWindow(binding.userPasswordConfirmField
                        .getWindowToken(), 0);
                return true;
            }
        });
    }
}
