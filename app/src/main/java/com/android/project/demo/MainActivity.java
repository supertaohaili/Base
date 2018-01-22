package com.android.project.demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.project.demo.ui.activity.GestureLoginActivity;
import com.android.project.demo.ui.fragment.BookFragment;
import com.android.project.demo.ui.fragment.ImageFragment;
import com.android.project.demo.ui.fragment.SettingFragment;

import www.thl.com.utils.DataHelper;
import www.thl.com.utils.ToastUtils;
import com.android.project.demo.ui.fragment.VideoFragment;

public class MainActivity extends FragmentActivity {

    private RadioButton rbVideo;
    private RadioButton rbBook;
    private RadioButton rbSetting;
    private RadioButton rbImage;
    private RadioGroup rg;

    private int position = 0;
    private final String TAG = "MainActivity";
    private VideoFragment mVideoFragment;
    private ImageFragment mImageFragment;
    private BookFragment mBookFragment;
    private SettingFragment mSettingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        byte[] pass = DataHelper.getDeviceData(this, Config.GESTURE_PASSWORD);
        if (pass != null) {
            String gesturePassword = new String(pass);
            if (!TextUtils.isEmpty(gesturePassword)) {
                startActivity(new Intent(this, GestureLoginActivity.class));
            }
        }
        setContentView(R.layout.activity_main);
        rbVideo = (RadioButton) findViewById(R.id.rdb_video);
        rbBook = (RadioButton) findViewById(R.id.rdb_book);
        rbImage = (RadioButton) findViewById(R.id.rdb_image);
        rbSetting = (RadioButton) findViewById(R.id.rdb_setting);
        rg = (RadioGroup) findViewById(R.id.rdg_main);
        initView();
        setTabSelection(position);
    }

    private void initView() {
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rdb_video:
                        Log.v(TAG, "setupWidgets():radio0 clicked");
                        setTabSelection(0);
                        break;
                    case R.id.rdb_book:
                        Log.v(TAG, "setupWidgets():radio1 clicked");
                        setTabSelection(1);
                        break;
                    case R.id.rdb_image:
                        Log.v(TAG, "setupWidgets():radio1 clicked");
                        setTabSelection(2);
                        break;
                    case R.id.rdb_setting:
                        Log.v(TAG, "setupWidgets():radio1 clicked");
                        setTabSelection(3);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putInt("position", position);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        position = savedInstanceState.getInt("position");
        setTabSelection(position);
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void setTabSelection(int position) {
        this.position = position;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragments(transaction);
        transaction = getSupportFragmentManager().beginTransaction();
        switch (position) {
            case 0:
                rbVideo.setChecked(true);
                if (mVideoFragment == null) {
                    mVideoFragment = new VideoFragment();
                    transaction.add(R.id.center_layout, mVideoFragment, "VideoFragment");
                } else {
                    transaction.show(mVideoFragment);
                }
                break;
            case 1:
                rbBook.setChecked(true);
                if (mBookFragment == null) {
                    mBookFragment = new BookFragment();
                    transaction.add(R.id.center_layout, mBookFragment, "BookFragment");
                } else {
                    transaction.show(mBookFragment);
                }
                break;
            case 2:
                rbImage.setChecked(true);
                if (mImageFragment == null) {
                    mImageFragment = new ImageFragment();
                    transaction.add(R.id.center_layout, mImageFragment, "ImageFragment");
                } else {
                    transaction.show(mImageFragment);
                }
                break;
            case 3:
                rbSetting.setChecked(true);
                if (mSettingFragment == null) {
                    mSettingFragment = new SettingFragment();
                    transaction.add(R.id.center_layout, mSettingFragment, "SettingFragment");
                } else {
                    transaction.show(mSettingFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (mVideoFragment != null)
            transaction.hide(mVideoFragment);
        if (mImageFragment != null)
            transaction.hide(mImageFragment);
        if (mSettingFragment != null)
            transaction.hide(mSettingFragment);
        if (mBookFragment != null)
            transaction.hide(mBookFragment);
        transaction.commit();
    }

    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN
                && event.getRepeatCount() == 0) {
            if (System.currentTimeMillis() - mExitTime > 2000) {
                mExitTime = System.currentTimeMillis();
                ToastUtils.showShort("再次点击返回确认退出");
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
