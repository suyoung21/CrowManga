package com.jiang.crowmanga.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jiang.crowmanga.App;
import com.jiang.crowmanga.bus.BusProvider;
import com.jiang.crowmanga.permission.PermissionHelper;
import com.jiang.crowmanga.permission.PermissionInterface;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author jiangshuyang
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected BaseActivity context;
    private WeakReference<BaseActivity> weakContext;
    final ReferenceQueue<BaseActivity> weakContextQueue = new ReferenceQueue<BaseActivity>();
    private boolean isFinished;
    protected WeakHandler weakHandler;
    private PermissionHelper mPermissionHelper;
    private Unbinder unbinder;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        App.getInstance().createActivity(this);
        weakHandler = new WeakHandler(this);
        setContentView(getLayoutResId());
        BusProvider.getInstance().register(this);
        unbinder = ButterKnife.bind(this);
        //初始化并发起权限申请
        mPermissionHelper = new PermissionHelper(this, getPermissionInterface());
        mPermissionHelper.requestPermissions();
        initViews();
    }

    protected abstract int getLayoutResId();

    protected abstract void initViews();

    @Override
    protected void onDestroy() {
        App.getInstance().destroyActivity(context);
        isFinished = true;
        BusProvider.getInstance().unregister(this);
        unbinder.unbind();
        releaseContext();
        if (weakHandler != null) {
            weakHandler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();

    }

    public BaseActivity getContext() {
        if (weakContext != null && weakContext.get() != null) {
            return weakContext.get();
        }
        weakContext = new WeakReference<BaseActivity>(context);
        return weakContext.get();
    }

    private void releaseContext() {
        if (weakContext != null) {
            weakContext.clear();
            weakContext.enqueue();
            weakContext = null;
        }
        if (weakContextQueue != null) {
            weakContextQueue.poll();
        }
        context = null;
        System.gc();
    }

    protected static class WeakHandler extends Handler {

        WeakReference<BaseActivity> mReference = null;

        WeakHandler(BaseActivity activity) {
            this.mReference = new WeakReference<BaseActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            BaseActivity activity = mReference.get();
            if (activity == null || activity.isFinishing() || activity.isFinished()) {
                return;
            }

            activity.handleMessage(msg);
        }
    }

    /**
     * WeakHandler handleMessage
     *
     * @param msg
     */
    protected void handleMessage(Message msg) {
    }

    public boolean isFinished() {
        return isFinished;
    }

    protected abstract PermissionInterface getPermissionInterface();


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (mPermissionHelper.requestPermissionsResult(requestCode, permissions, grantResults)) {
            //权限请求结果，并已经处理了该回调
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}
