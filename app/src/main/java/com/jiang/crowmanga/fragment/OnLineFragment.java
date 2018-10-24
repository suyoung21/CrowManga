package com.jiang.crowmanga.fragment;

import android.support.v7.widget.RecyclerView;

import com.jiang.crowmanga.R;
import com.jiang.crowmanga.base.BaseFragment;

import butterknife.BindView;
import butterknife.Unbinder;

public class OnLineFragment extends BaseFragment {

    @BindView(R.id.rv_fragment_online)
    RecyclerView recycleView;



    @Override
    protected int setContentViewId() {
        return R.layout.fragment_online;
    }
}
