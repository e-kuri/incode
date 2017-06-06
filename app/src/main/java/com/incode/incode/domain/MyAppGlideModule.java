package com.incode.incode.domain;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.AppGlideModule;

import java.io.File;
import java.lang.annotation.Annotation;

/**
 * Created by kuri on 6/5/17.
 */

@GlideModule
public final class MyAppGlideModule extends AppGlideModule implements GlideModule {
    private static final int IMAGE_CACHE_SIZE = 200_000_000;
    @Override
    public String glideName() {
        return null;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDiskCache(new DiskCacheFactory(context, ".", IMAGE_CACHE_SIZE));
    }

    private static class DiskCacheFactory extends DiskLruCacheFactory {

        /**
         * Store the images in disk to be able to share them
         * @param context Context to store the files
         * @param diskCacheName Parameter not used!!
         * @param diskCacheSize Size of the cache
         */
        DiskCacheFactory(final Context context, final String diskCacheName, long diskCacheSize) {
            super(new CacheDirectoryGetter() {
                @Override
                public File getCacheDirectory() {
                    File cacheDirectory = new File(context.getExternalFilesDir(null), "Cache");
                    if(!cacheDirectory.exists()){
                        cacheDirectory.mkdir();
                    }
                    return cacheDirectory;
                }

            }, (int) diskCacheSize);
        }
    }
}
