package com.example.mp3player.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class LocalPlaylistViewPaggerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mArrayFragment = new ArrayList<>();
    public LocalPlaylistViewPaggerAdapter(FragmentManager fm) {
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

    public void addFragment(Fragment fragment){
        mArrayFragment.add(fragment);
    }
}
