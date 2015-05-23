package com.curson.universalimageloaderdemo;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
    }


    private void initImageLoader() {

        //创建默认的ImageLoader配置参数   也可以自己配置
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCacheExtraOptions(480, 800)//保存的每个缓存文件的最大长宽
                .threadPoolSize(3)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 1)//线程池的优先级
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheSize(50 * 1024 * 1024)//缓存硬盘50MB
                .diskCacheFileCount(100)//缓存的File的数量
                .writeDebugLogs()//打印Log信息
                .build();

        ImageLoader.getInstance().init(configuration);
    }
}
