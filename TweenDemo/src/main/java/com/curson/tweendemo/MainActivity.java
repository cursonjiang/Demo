package com.curson.tweendemo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.image);
    }

    /**
     * 旋转动画
     *
     * @param view
     */
    public void rotate(View view) {
        RotateAnimation rotateAnimation = new RotateAnimation(
                0,
                360,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
        );
        rotateAnimation.setDuration(2000);
        rotateAnimation.setRepeatCount(1);
        rotateAnimation.setRepeatMode(Animation.REVERSE);
        imageView.startAnimation(rotateAnimation);
    }

    /**
     * 缩放动画
     *
     * @param view
     */
    public void scale(View view) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                0.1f,
                2.0f,
                0.1f,
                2.0f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);
        scaleAnimation.setDuration(2000);
        scaleAnimation.setRepeatCount(1);
        scaleAnimation.setRepeatMode(Animation.REVERSE);
        imageView.startAnimation(scaleAnimation);
    }

    /**
     * 位移动画
     *
     * @param view
     */
    public void trans(View view) {
        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, -0.5f,
                Animation.RELATIVE_TO_PARENT, 0.5f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        translateAnimation.setDuration(2000);
        translateAnimation.setRepeatMode(Animation.REVERSE);//反向
        translateAnimation.setRepeatCount(1);
        imageView.startAnimation(translateAnimation);
    }

    /**
     * 透明度动画
     *
     * @param view
     */
    public void alpha(View view) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(2000);//持续时间
        alphaAnimation.setRepeatCount(1);//重复播放次数
        imageView.startAnimation(alphaAnimation);
    }

    /**
     * 组合动画
     *
     * @param view
     */
    public void set(View view) {
        //动画集合
        AnimationSet animationSet = new AnimationSet(false);

        RotateAnimation rotateAnimation = new RotateAnimation(
                0,
                360,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
        );
        rotateAnimation.setDuration(2000);
        rotateAnimation.setRepeatCount(1);
        rotateAnimation.setRepeatMode(Animation.REVERSE);

        ScaleAnimation scaleAnimation = new ScaleAnimation(
                0.1f,
                2.0f,
                0.1f,
                2.0f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);
        scaleAnimation.setDuration(2000);
        scaleAnimation.setRepeatCount(1);
        scaleAnimation.setRepeatMode(Animation.REVERSE);

        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, -0.5f,
                Animation.RELATIVE_TO_PARENT, 0.5f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        translateAnimation.setDuration(2000);
        translateAnimation.setRepeatMode(Animation.REVERSE);//反向
        translateAnimation.setRepeatCount(1);

        //把动画添加到动画集合中
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(translateAnimation);

        //开始播放集合动画
        imageView.startAnimation(animationSet);
    }
}
