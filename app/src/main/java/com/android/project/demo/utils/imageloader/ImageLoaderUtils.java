package com.android.project.demo.utils.imageloader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.android.project.demo.R;
import com.android.project.demo.utils.imageloader.glide.GlideImageConfig;
import com.android.project.demo.utils.imageloader.glide.GlideImageLoaderStrategy;

import www.thl.com.utils.ScreenUtils;


/**
 * 加载图片的工具类
 */
public class ImageLoaderUtils {

    private static ImageLoader mImageLoader = new ImageLoader(new GlideImageLoaderStrategy());

    private ImageLoaderUtils() {
    }

    public static void loadImage(Context context, GlideImageConfig config) {
        mImageLoader.loadImage(context, config);
    }

    public static void loadImage(Context context, ImageView mImageView, String url) {
        GlideImageConfig config = GlideImageConfig.builder()
                .url(url)
                .errorPic(R.drawable.mn_icon_load_fail)
                .placeholder(R.drawable.mn_icon_load_fail)
                .cacheStrategy(2)
                .imageView(mImageView)
                .build();
        mImageLoader.loadImage(context, config);
    }

    public static void loadImageFixCenter(Context context, ImageView mImageView, String url) {
        GlideImageConfig config = GlideImageConfig.builder()
                .url(url)
                .setType(GlideImageConfig.FIXCENT)
                .errorPic(R.drawable.mn_icon_load_fail)
                .placeholder(R.drawable.mn_icon_load_fail)
                .cacheStrategy(2)
                .imageView(mImageView)
                .build();
        mImageLoader.loadImage(context, config);
    }

    public static void loadImageResourceId(Context context, ImageView mImageView, int url) {
        GlideImageConfig config = GlideImageConfig.builder()
                .resourceId(url)
                .errorPic(R.drawable.mn_icon_load_fail)
                .placeholder(R.drawable.mn_icon_load_fail)
                .cacheStrategy(2)
                .imageView(mImageView)
                .build();
        mImageLoader.loadImage(context, config);
    }

    public static void loadImage(Context context, ImageView mImageView, int url) {
        Glide.with(context)
                .load(url)
                .override(ScreenUtils.getScreenWidth(), ScreenUtils.getScreenHeight())
                .into(mImageView);
    }

    public static void clearMemory(Context context) {
        Glide.get(context).clearMemory();
    }


}
