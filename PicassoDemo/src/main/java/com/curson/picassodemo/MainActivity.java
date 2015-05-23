package com.curson.picassodemo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class MainActivity extends ActionBarActivity {

    String url = "http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg";

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPicasso();
    }

    private void initPicasso() {
        mImageView = (ImageView) findViewById(R.id.imageview);
        Picasso.with(this).load(url).into(mImageView);
    }
}
