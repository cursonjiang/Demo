package com.curson.viewpagerdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private String[] titles;

    private List<View> mPagerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        mPagerList = new ArrayList<>();
        initPagerTitle();
        PagerAdapter pagerAdapter = new MyPagerAdapter();
        viewPager.setAdapter(pagerAdapter);
        setPagerTabStrip();

    }

    /**
     * 设置PagerTabStrip的样式
     */
    private void setPagerTabStrip() {
        PagerTabStrip pagerTabStrip = (PagerTabStrip) findViewById(R.id.pagertabstrip);

        //删除pager和选项卡之间的分割线
        pagerTabStrip.setDrawFullUnderline(false);

        //修改选项卡下划线
        pagerTabStrip.setTabIndicatorColor(Color.BLUE);
    }

    /**
     * 初始化PagerTitle
     */
    private void initPagerTitle() {
        titles = new String[]{"啊啊", "呵呵", "哈哈"};

        TextView item = new TextView(this);
        item.setText("啊啊");
        mPagerList.add(item);

        item = new TextView(this);
        item.setText("呵呵");
        mPagerList.add(item);

        item = new TextView(this);
        item.setText("哈哈");
        mPagerList.add(item);

    }

    private class MyPagerAdapter extends PagerAdapter {
        /**
         * 获取PagerTitle
         *
         * @param position
         * @return
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return mPagerList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = mPagerList.get(position);
            container.removeView(view);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = mPagerList.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
