package com.aadilmehraj.android.mvvmarch.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.aadilmehraj.android.mvvmarch.service.model.Category;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> mfragmentList = new ArrayList<>();
    private final List<Category> mfragmentTitleList = new ArrayList<>();


    public void addFragment(Fragment fragment, Category category){
        mfragmentList.add(fragment);
        mfragmentTitleList.add(category);
    }

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return mfragmentList.get(i);
    }

    @Override
    public int getCount() {
        return mfragmentList.size();
    }
}
