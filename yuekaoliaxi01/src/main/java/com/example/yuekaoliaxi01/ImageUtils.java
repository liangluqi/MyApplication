package com.example.yuekaoliaxi01;

import android.app.Application;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.youth.banner.loader.ImageLoader;

import java.io.File;

/**
 * Created by hp on 2017/8/24.
 */

public class ImageUtils  extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        File file=new File(Environment.getExternalStorageDirectory()+"/aaa");
        ImageLoaderConfiguration builder=new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(200,200)
                .memoryCacheSize(20*1024*1024)
                .threadPoolSize(3)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCache(new UnlimitedDiskCache(file))
                .build();
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(builder);

    }
}
