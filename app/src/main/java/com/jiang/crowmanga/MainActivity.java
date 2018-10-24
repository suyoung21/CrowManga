package com.jiang.crowmanga;

import android.Manifest;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.jiang.crowmanga.adapter.ContentPagerAdapter;
import com.jiang.crowmanga.base.BaseActivity;
import com.jiang.crowmanga.fragment.NewsFragment;
import com.jiang.crowmanga.fragment.OnLineFragment;
import com.jiang.crowmanga.permission.PermissionInterface;
import com.jiang.crowmanga.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author jiangshuyang
 */
public class MainActivity extends BaseActivity implements PermissionInterface {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        setSupportActionBar(toolbar);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new OnLineFragment());
        fragments.add(new NewsFragment());
        List<String> nameList = new ArrayList<>();
        nameList.add("在线漫画");
        nameList.add("资讯");
        viewPager.setAdapter(new ContentPagerAdapter(fragments, nameList, getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected PermissionInterface getPermissionInterface() {
        return this;
    }

    @Override
    public int getPermissionsRequestCode() {
        return 1001;
    }

    @Override
    public String[] getPermissions() {
        return new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
    }

    @Override
    public void requestPermissionsSuccess() {
        ToastUtils.showMsg(this, "requestPermissionsSuccess");
    }

    @Override
    public void requestPermissionsFail() {
        ToastUtils.showMsg(this, "requestPermissionsFail");
    }
}
