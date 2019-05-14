package com.example.mp3player.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class LocalViewPaggerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mArrayFragment = new ArrayList<>();
    private ArrayList<String> mArrayTitle = new ArrayList<>();

    public LocalViewPaggerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mArrayFragment.get(position);
    }

    @Override
    public int getCount() {
        return mArrayFragment.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mArrayTitle.get(position);
    }
}
