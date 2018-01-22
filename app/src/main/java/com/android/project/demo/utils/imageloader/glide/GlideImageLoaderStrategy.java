package com.android.project.demo.utils.imageloader.glide;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.android.project.demo.utils.imageloader.BaseImageLoaderStrategy;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;

import java.io.Serializable;



public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy<GlideImageConfig> {

    @Override
    public void loadImage(Context ctx, GlideImageConfig config) {
        if (ctx == null) throw new NullPointerException("Context is required");
        if (config == null) throw new NullPointerException("GlideImageConfig is required");
        if (TextUtils.isEmpty(config.getUrl())&&config.getResourceId()==-1) throw new NullPointerException("Url is required");
        if (config.getImageView() == null) throw new NullPointerException("Imageview is required");

        RequestManager manager;
        manager = Glide.with(ctx);//如果context是activity则自动使用Activity的生命周期
        DrawableRequestBuilder<? extends Serializable> requestBuilder = manager.load(TextUtils.isEmpty(config.getUrl())?config.getResourceId():config.getUrl())
                .crossFade()
                .thumbnail(0.1f)
                .centerCrop();

        switch (config.getCacheStrategy()) {//缓存策略
            case 0:
                requestBuilder.diskCacheStrategy(DiskCacheStrategy.ALL);
                break;
            case 1:
                requestBuilder.diskCacheStrategy(DiskCacheStrategy.NONE);
                break;
            case 2:
                requestBuilder.diskCacheStrategy(DiskCacheStrategy.SOURCE);
                break;
            case 3:
                requestBuilder.diskCacheStrategy(DiskCacheStrategy.RESULT);
                break;
        }

        if (config.getTransformation() != null) {//glide用它来改变图形的形状
            requestBuilder.transform(config.getTransformation());
        }

        if (config.getPlaceholder() != 0)//设置占位符
            requestBuilder.placeholder(config.getPlaceholder());

        if (config.getErrorPic() != 0)//设置错误的图片
            requestBuilder.error(config.getErrorPic());
        requestBuilder.into(config.getImageView());

    }

    @Override
    public void clear(final Context ctx, GlideImageConfig config) {
        if (ctx == null) throw new NullPointerException("Context is required");
        if (config == null) throw new NullPointerException("GlideImageConfig is required");

        if (config.getImageViews() != null && config.getImageViews().length > 0) {//取消在执行的任务并且释放资源
            for (ImageView imageView : config.getImageViews()) {
                Glide.clear(imageView);
            }
        }

        if (config.getTargets() != null && config.getTargets().length > 0) {//取消在执行的任务并且释放资源
            for (Target target : config.getTargets())
                Glide.clear(target);
        }

        if (config.isClearDiskCache()) {//清除本地缓存
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Glide.get(ctx).clearDiskCache();
                }
            }).start();
        }

        if (config.isClearMemory()) {//清除内存缓存
            Glide.get(ctx).clearMemory();
        }

    }
}
