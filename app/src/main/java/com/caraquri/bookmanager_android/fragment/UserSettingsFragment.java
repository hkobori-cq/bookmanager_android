package com.caraquri.bookmanager_android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.caraquri.bookmanager_android.R;
import com.caraquri.bookmanager_android.api.DataClient;
import com.caraquri.bookmanager_android.databinding.FragmentUserRegisterBinding;
import com.caraquri.bookmanager_android.util.KeyboardUtil;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class UserSettingsFragment extends Fragment {
    protected FragmentUserRegisterBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_user_register, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding = FragmentUserRegisterBinding.bind(getView());
        onTextFieldUnFocused();
        initToolbar();
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

    private void initToolbar(){
        ActionBar bar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (bar != null) {
            bar.setDisplayShowHomeEnabled(true);
            bar.setHomeButtonEnabled(true);
        }
    }

    private void registerUserData(){
        String emailStr = binding.userEmailField.getText().toString();
        String passwordStr = binding.userPasswordField.getText().toString();
        String passwordConStr = binding.userPasswordConfirmField.getText().toString();
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
        } else if (passwordConStr.isEmpty()) {
            args.putString(AlertDialogFragment.ALERT_DIALOG_MESSAGE_KEY, getString(R.string.input_password_confirm));
            alertDialog.setArguments(args);
            alertDialog.show(getActivity().getSupportFragmentManager(), AlertDialogFragment.ALERT_DIALOG_SHOW_KEY);
        } else if (!(passwordStr.equals(passwordConStr))) {
            args.putString(AlertDialogFragment.ALERT_DIALOG_MESSAGE_KEY, getString(R.string.not_match_password));
            alertDialog.setArguments(args);
            alertDialog.show(getActivity().getSupportFragmentManager(), AlertDialogFragment.ALERT_DIALOG_SHOW_KEY);
        } else {
            DataClient client = new DataClient();
            Call<Void> call = client.userRegisterClient(emailStr, passwordStr);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Response<Void> response, Retrofit retrofit) {
                    Bundle args = new Bundle();
                    args.putString(AlertDialogFragment.ALERT_DIALOG_MESSAGE_KEY, getString(R.string.completed_register));
                    AlertDialogFragment alertDialog = new AlertDialogFragment();
                    alertDialog.setArguments(args);
                    alertDialog.show(getActivity().getSupportFragmentManager(), AlertDialogFragment.ALERT_DIALOG_SHOW_KEY);
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
        }
    }

    /**
     * @see UserLoginFragment
     */
    private void onTextFieldUnFocused() {
        final KeyboardUtil keyboardUtil = new KeyboardUtil();
        binding.userRegisterFragment.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                keyboardUtil.hideKeyboard(binding.userEmailField,getContext());
                keyboardUtil.hideKeyboard(binding.userPasswordField,getContext());
                keyboardUtil.hideKeyboard(binding.userPasswordConfirmField,getContext());
                return true;
            }
        });
    }
}
