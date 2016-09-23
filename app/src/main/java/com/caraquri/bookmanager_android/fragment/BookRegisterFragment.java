package com.caraquri.bookmanager_android.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.caraquri.bookmanager_android.R;
import com.caraquri.bookmanager_android.activity.MainActivity;
import com.caraquri.bookmanager_android.api.DataClient;
import com.caraquri.bookmanager_android.databinding.FragmentAddViewBinding;

import java.io.FileDescriptor;
import java.io.IOException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class BookRegisterFragment extends Fragment {
    private static final int SET_DATE = 1;
    private static final int TAPPED_ADD_IMAGE_BUTTON = 2;
    private static final String IMAGE_STORE_URL = "image/*";
    protected FragmentAddViewBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_add_view, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding = FragmentAddViewBinding.bind(getView());
        if (getActivity().getIntent().hasExtra(getString(R.string.name))) {
            initFieldData();
        }
        tappedDateEditField();
        tappedAddImageButton();
        onTextFieldUnFocused();
        initToolbar();
    }

    private void initToolbar() {
        ActionBar bar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (bar != null) {
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setDisplayShowHomeEnabled(true);
            bar.setDisplayShowTitleEnabled(false);
            bar.setHomeButtonEnabled(true);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_book, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.register:
                registerBookData();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void registerBookData() {
        Call<Void> call;
        String bookIDText = getActivity().getIntent().getStringExtra(getString(R.string.id));
        String bookTitleFieldText = binding.bookTitleField.getText().toString();
        int bookPriceFieldText = Integer.parseInt(binding.bookPriceField.getText().toString());
        String bookDateFieldText = binding.bookDateField.getText().toString();

        Bundle args = new Bundle();
        AlertDialogFragment alertDialog = new AlertDialogFragment();
        if (binding.bookTitleField.getText().toString().isEmpty()) {
            args.putString(getString(R.string.message), getString(R.string.input_book_name));
            alertDialog.setArguments(args);
            alertDialog.show(getActivity().getSupportFragmentManager(), getString(R.string.dialog));
        } else if (binding.bookPriceField.getText().toString().isEmpty()) {
            args.putString(getString(R.string.message), getString(R.string.input_book_price));
            alertDialog.setArguments(args);
            alertDialog.show(getActivity().getSupportFragmentManager(), getString(R.string.dialog));
        } else if (binding.bookDateField.getText().toString().isEmpty()) {
            args.putString(getString(R.string.message), getString(R.string.select_purchase_date));
            alertDialog.setArguments(args);
            alertDialog.show(getActivity().getSupportFragmentManager(), getString(R.string.dialog));
        } else {
            DataClient client = new DataClient();
            if (getActivity().getIntent().hasExtra(getString(R.string.name))) {
                call = client.bookUpdateClient(
                        bookIDText,
                        getString(R.string.sample_image),
                        bookTitleFieldText,
                        bookPriceFieldText,
                        bookDateFieldText
                );
            } else {
                call = client.bookRegisterClient(
                        getString(R.string.sample_image),
                        bookTitleFieldText,
                        bookPriceFieldText,
                        bookDateFieldText
                );
            }
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Response<Void> response, Retrofit retrofit) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
        }
    }

    /**
     * EditTextでキーボードが出ている際、バックレイヤーを触るとキーボードが消えるようにするメソッド
     */
    private void onTextFieldUnFocused() {
        binding.bookAddFragment.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager inputMethodManager = (InputMethodManager) getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager
                        .hideSoftInputFromWindow(binding.bookTitleField.getWindowToken(), 0);
                inputMethodManager
                        .hideSoftInputFromWindow(binding.bookDateField.getWindowToken(), 0);
                inputMethodManager
                        .hideSoftInputFromWindow(binding.bookPriceField.getWindowToken(), 0);
                return true;
            }
        });
    }

    private void tappedAddImageButton() {
        binding.addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType(IMAGE_STORE_URL);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, TAPPED_ADD_IMAGE_BUTTON);
            }
        });

    }

    /**
     * getActivityがEditActivityだった場合は初期値を入れる
     */
    private void initFieldData() {
        Intent intent = getActivity().getIntent();
        binding.addPageBookImage.setImageResource(R.drawable.sample);
        binding.bookTitleField.setText(intent.getStringExtra(getString(R.string.name)));
        binding.bookPriceField.setText(intent.getStringExtra(getString(R.string.price))
                .replace(getString(R.string.japanese_yen), ""));
        binding.bookDateField.setText(intent.getStringExtra(getString(R.string.date)).replaceAll("/", "-"));
    }


    private void tappedDateEditField() {
        binding.bookDateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                DatePickerFragment datePicker = new DatePickerFragment();
                datePicker.setTargetFragment(BookRegisterFragment.this, 0);
                datePicker.show(manager, getString(R.string.date_picker));
            }
        });
    }

    /**
     * 送られてきたIntentをrequestCodeによって分類
     * case 1のときは購入日のフィールドにデータをセットする
     * case 10のときはイメージを添付する
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SET_DATE:
                String date = data.getStringExtra(Intent.EXTRA_TEXT);
                binding.bookDateField.setText(date);
                break;
            case TAPPED_ADD_IMAGE_BUTTON:
                Uri uri = data.getData();

                try {
                    Bitmap bmp = createBitmapFromUri(uri);
                    binding.addPageBookImage.setImageBitmap(bmp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }

    }

    /**
     * URIkからデータをビットマップで使える形に変換するメソッド
     *
     * @param uri 画像のURI
     * @return
     * @throws IOException
     */
    private Bitmap createBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = getActivity()
                .getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = null;
        if (parcelFileDescriptor != null) {
            fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        }
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        if (parcelFileDescriptor != null) {
            parcelFileDescriptor.close();
        }
        return image;
    }

}
