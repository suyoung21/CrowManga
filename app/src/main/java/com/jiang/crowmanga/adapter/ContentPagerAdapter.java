package com.jiang.crowmanga.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class ContentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private List<String> nameList;

    public ContentPagerAdapter(List<Fragment> fragments, List<String> nameList, FragmentManager fm) {
        super(fm);
        this.fragments = fragments;
        this.nameList = nameList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return nameList.get(position);
    }
}
