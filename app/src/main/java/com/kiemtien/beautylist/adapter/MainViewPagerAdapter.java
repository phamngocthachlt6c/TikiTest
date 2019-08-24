package com.kiemtien.beautylist.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.kiemtien.beautylist.callback.MainActivityCallback;
import com.kiemtien.beautylist.fragment.BaseFragment;
import com.kiemtien.beautylist.fragment.CategoriesFragment;
import com.kiemtien.beautylist.fragment.PicturesFragment;
import com.kiemtien.beautylist.model.Category;

import java.util.ArrayList;
import java.util.List;

public class MainViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<BaseFragment> fragments;

    public MainViewPagerAdapter(FragmentManager fm, MainActivityCallback onDirection) {
        super(fm);
        fragments = new ArrayList<>();
        BaseFragment fragment = CategoriesFragment.Companion.newInstance();
        fragment.setOnDirection(onDirection);
        fragments.add(fragment);

        fragment = (PicturesFragment.Companion.newInstance(new Category()));
        fragment.setOnDirection(onDirection);
        fragments.add(fragment);

        fragment = (PicturesFragment.Companion.newInstance(new Category()));
        fragment.setOnDirection(onDirection);
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
