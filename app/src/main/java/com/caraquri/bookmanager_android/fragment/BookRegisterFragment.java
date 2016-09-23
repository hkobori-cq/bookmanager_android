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
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.caraquri.bookmanager_android.R;
import com.caraquri.bookmanager_android.databinding.FragmentAddViewBinding;

import java.io.FileDescriptor;
import java.io.IOException;


public class BookRegisterFragment extends Fragment {
    public static final int SET_DATE = 1;
    public static final int TAPPED_ADD_IMAGE_BUTTON = 2;
    protected FragmentAddViewBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_view, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding = FragmentAddViewBinding.bind(getView());
        if (getActivity().getIntent().hasExtra(getString(R.string.name))) {
            initFieldData();
        }
        tappedDateButton();
        tappedAddImageButton();
        onTextFieldUnFocused();
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
                intent.setType("image/*");
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
        binding.bookPriceField.setText(intent.getStringExtra(getString(R.string.price)).replace("円", ""));
        binding.bookDateField.setText(intent.getStringExtra(getString(R.string.date)).replaceAll("/", "-"));
    }


    private void tappedDateButton() {
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
