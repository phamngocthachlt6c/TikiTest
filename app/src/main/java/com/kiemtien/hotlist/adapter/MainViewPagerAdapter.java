package com.kiemtien.hotlist.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.kiemtien.hotlist.fragment.BaseFragment;
import com.kiemtien.hotlist.fragment.PicturesFragment;
import com.kiemtien.hotlist.model.Category;

import java.util.ArrayList;
import java.util.List;

public class MainViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<BaseFragment> fragments;

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
        fragments.add(PicturesFragment.Companion.newInstance(new Category()));
        fragments.add(PicturesFragment.Companion.newInstance(new Category()));
        fragments.add(PicturesFragment.Companion.newInstance(new Category()));
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
