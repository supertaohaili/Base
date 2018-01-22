package com.android.project.demo;

import android.content.Intent;
import android.widget.ImageView;

import com.android.project.demo.utils.imageloader.ImageLoaderUtils;

public class SplashActivity extends www.thl.com.base.activity.SplashActivity  {

    @Override
    protected int getImageId() {
        return R.drawable.photo1;
    }

    @Override
    protected void showImage(ImageView ivSplash, int imageId) {
        ImageLoaderUtils.loadImageResourceId(this, ivSplash,imageId);
    }

    @Override
    protected void toNext() {
        Intent mIntent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(mIntent);
        SplashActivity.this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageLoaderUtils.clearMemory(this);
    }
}
