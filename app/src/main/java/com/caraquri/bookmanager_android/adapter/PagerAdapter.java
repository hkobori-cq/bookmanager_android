package com.caraquri.bookmanager_android.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.caraquri.bookmanager_android.fragment.RecyclerLayoutFragment;
import com.caraquri.bookmanager_android.fragment.UserSettingsFragment;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentPagerAdapter {

    private static final int USER_SETTINGS_FRAGMENT_FLAG = 1;
    private static final int TAB_COUNT = 2;
    private final List<String> categoryNames = new ArrayList<>();

    public void addCategory(String categoryName) {
        categoryNames.add(categoryName);
    }

    public PagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            default:
                return new RecyclerLayoutFragment();
            case USER_SETTINGS_FRAGMENT_FLAG:
                return new UserSettingsFragment();
        }
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return categoryNames.get(position);
    }
}
