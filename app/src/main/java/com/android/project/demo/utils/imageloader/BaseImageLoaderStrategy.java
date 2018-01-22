package com.android.project.demo.utils.imageloader;

import android.content.Context;

/**
 * Created by jess on 8/5/16 15:50
 */
public interface BaseImageLoaderStrategy<T extends ImageConfig> {
    void loadImage(Context ctx, T config);
    void clear(Context ctx, T config);
}
