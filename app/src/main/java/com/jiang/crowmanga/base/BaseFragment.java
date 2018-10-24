package com.jiang.crowmanga.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract  class BaseFragment extends Fragment {
    protected View rootView;
    protected Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (rootView == null) {
            if (setContentViewId() == 0) throw new RuntimeException("must set a layout for this fragment");
            rootView = inflater.inflate(setContentViewId(), null);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }

        unbinder= ButterKnife.bind(this,rootView);
        return rootView;
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    /**
     * 设置主体页面
     */
    @LayoutRes
    protected abstract int setContentViewId();
}
