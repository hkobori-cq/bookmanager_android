package com.caraquri.bookmanager_android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.caraquri.bookmanager_android.R;
import com.caraquri.bookmanager_android.activity.MainActivity;
import com.caraquri.bookmanager_android.api.DataClient;
import com.caraquri.bookmanager_android.databinding.FragmentUserLoginBinding;
import com.caraquri.bookmanager_android.util.KeyboardUtil;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class UserLoginFragment extends Fragment {
    protected FragmentUserLoginBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_user_login, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding = FragmentUserLoginBinding.bind(getView());
        onTextFieldUnFocused();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_user_settings, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_user_settings:
                registerUserData();
                break;
        }
        return super.onOptionsItemSelected(item);
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

    private void registerUserData() {
        String emailStr = binding.userLoginEmailField.getText().toString();
        String passwordStr = binding.userLoginPasswordField.getText().toString();

        Bundle args = new Bundle();
        AlertDialogFragment alertDialog = new AlertDialogFragment();

        if (emailStr.isEmpty()) {
            args.putString(AlertDialogFragment.ALERT_DIALOG_MESSAGE_KEY, getString(R.string.input_mail_address));
            alertDialog.setArguments(args);
            alertDialog.show(getActivity().getSupportFragmentManager(), AlertDialogFragment.ALERT_DIALOG_SHOW_KEY);
        } else if (passwordStr.isEmpty()) {
            args.putString(AlertDialogFragment.ALERT_DIALOG_MESSAGE_KEY, getString(R.string.input_password));
            alertDialog.setArguments(args);
            alertDialog.show(getActivity().getSupportFragmentManager(), AlertDialogFragment.ALERT_DIALOG_SHOW_KEY);
        } else {
            DataClient client = new DataClient();
            Call<Integer> call = client.userLoginClient(emailStr, passwordStr);
            call.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Response<Integer> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        Bundle args = new Bundle();
                        AlertDialogFragment alertDialog = new AlertDialogFragment();
                        args.putString(AlertDialogFragment.ALERT_DIALOG_MESSAGE_KEY, getString(R.string.failed_login_message));
                        alertDialog.setArguments(args);
                        alertDialog.show(getActivity().getSupportFragmentManager(), AlertDialogFragment.ALERT_DIALOG_SHOW_KEY);
                    }
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
        }
    }


}
