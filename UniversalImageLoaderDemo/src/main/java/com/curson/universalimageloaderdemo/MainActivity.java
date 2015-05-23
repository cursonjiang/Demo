package com.curson.universalimageloaderdemo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;


public class MainActivity extends ActionBarActivity {

    String url = "http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg";

    private ImageView mImageView1;
    private ImageView mImageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        disPlayImage();

        loadImage();


    }

    private void disPlayImage() {
        mImageView2 = (ImageView) findViewById(R.id.imageview2);

        //图片的配置信息
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(android.R.drawable.sym_contact_card)//加载中显示的图片
                .showImageOnFail(android.R.drawable.stat_notify_error)//加载失败显示的图片
                .cacheInMemory(true)//缓存到内存中
                .cacheOnDisk(true)//缓存到SD卡中
                .bitmapConfig(Bitmap.Config.RGB_565)//设置解码类型
                .build();

        //会根据控件的大小和imageScaleType来自动裁剪图片
        ImageLoader.getInstance().displayImage(
                url,//地址
                mImageView2,//控件
                options,//图片的配置信息
                new SimpleImageLoadingListener(),//没用到,填补位置
                new ImageLoadingProgressListener() {//获取图片加载进度
                    @Override
                    public void onProgressUpdate(String s, View view, int i, int i1) {

                    }
                });
    }

    private void loadImage() {
        mImageView1 = (ImageView) findViewById(R.id.imageview1);

        //指定图片的宽高
        ImageSize mImageSize = new ImageSize(400, 400);

        //显示图片的配置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)//缓存在内存中
                .cacheOnDisk(true)//缓存在SD卡中
                .bitmapConfig(Bitmap.Config.RGB_565)//设置解码类型
                .build();

        ImageLoader.getInstance().loadImage(url, mImageSize, options, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                //设置给ImageView
                mImageView1.setImageBitmap(loadedImage);
            }
        });
    }


}
