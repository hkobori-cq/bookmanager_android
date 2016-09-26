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
import com.caraquri.bookmanager_android.databinding.FragmentUserLoginBinding;
import com.caraquri.bookmanager_android.util.KeyboardUtil;

public class UserLoginFragment extends Fragment {
    protected FragmentUserLoginBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_login, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding = FragmentUserLoginBinding.bind(getView());
        onTextFieldUnFocused();
    }

    /**
     * EditText以外のViewがタップされたときのメソッド
     * タップされたときはkeyboardを隠す
     */
    private void onTextFieldUnFocused() {
        final KeyboardUtil keyboardUtil = new KeyboardUtil();
        binding.userLoginFragment.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                keyboardUtil.hideKeyboard(binding.userLoginEmailField,getContext());
                keyboardUtil.hideKeyboard(binding.userLoginPasswordField,getContext());
                return true;
            }
        });
    }


}
