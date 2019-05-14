package com.example.mp3player.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MainViewPaggerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mArrayFragment = new ArrayList<>();
    private ArrayList<String> mArrayTitle = new ArrayList<>();

    public MainViewPaggerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return mArrayFragment.get(i);
    }

    @Override
    public int getCount() {
        return mArrayFragment.size();
    }

    public void addFragment(Fragment fragment, String title){
        mArrayFragment.add(fragment);
        mArrayTitle.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mArrayTitle.get(position);
    }
}
