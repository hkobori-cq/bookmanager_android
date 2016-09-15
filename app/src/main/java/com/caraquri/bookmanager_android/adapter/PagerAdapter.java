package com.caraquri.bookmanager_android.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.caraquri.bookmanager_android.fragment.RecyclerLayoutFragment;
import com.caraquri.bookmanager_android.fragment.UserSettingsFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new RecyclerLayoutFragment();
            case 1:
                return  new UserSettingsFragment();
        }
        return null;
    }
    @Override
    public int getCount() {
        return 2;
    }
    @Override
    public CharSequence getPageTitle(int position){
        return "ページ"+(position+1);
    }
}
