package com.caraquri.bookmanager_android.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.caraquri.bookmanager_android.R;
import com.caraquri.bookmanager_android.databinding.FragmentAddViewBinding;

import java.io.FileDescriptor;
import java.io.IOException;


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
        if (!(getActivity().getIntent().getStringExtra("name") == null)) {
            initFieldData();
        }
        tappedDateButton();
        tappedAddImageButton();
    }


    private void tappedAddImageButton() {
        binding.addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 10);
            }
        });

    }

    private void initFieldData() {
        Intent intent = getActivity().getIntent();
        binding.addPageBookImage.setImageResource(R.drawable.sample);
        binding.bookTitleField.setText(intent.getStringExtra("name"));
        binding.bookPriceField.setText(intent.getStringExtra("price").replace("円", ""));
        binding.bookDateField.setText(intent.getStringExtra("date").replaceAll("/", "-"));
    }


    private void tappedDateButton() {

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
        switch (requestCode) {
            case 1:
                String date = data.getStringExtra(Intent.EXTRA_TEXT);
                binding.bookDateField.setText(date);
                break;
            case 10:
                Uri uri = data.getData();

                try {
                    Bitmap bmp = getBitmapFromUri(uri);
                    binding.addPageBookImage.setImageBitmap(bmp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }

    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = getActivity().getContentResolver().openFileDescriptor(uri, "r");
        assert parcelFileDescriptor != null;
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

}
