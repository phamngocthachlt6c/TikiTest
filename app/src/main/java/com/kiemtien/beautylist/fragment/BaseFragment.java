package com.kiemtien.beautylist.fragment;

import android.support.v4.app.Fragment;

import com.kiemtien.beautylist.callback.MainActivityCallback;

public abstract class BaseFragment extends Fragment {
    protected MainActivityCallback mOnDirection;
    public void setOnDirection(MainActivityCallback onDirection) {
        mOnDirection = onDirection;
    }
}
