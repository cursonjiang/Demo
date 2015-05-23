package com.curson.drawerlayout;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends Activity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ArrayList<String> menuLists;
    private ArrayAdapter<String> adapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取标题内容
        mTitle = getTitle().toString();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        menuLists = new ArrayList<>();
        for (int i = 0; i <= 5; i++)
            menuLists.add("Curson" + i);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, menuLists);

        //填充到ListView
        mDrawerList.setAdapter(adapter);

        //设置左上角的图标显示,按钮可点击
        getActionBar().setDisplayHomeAsUpEnabled(true); //此方法里实现了setHomeButtonEnabled();

        //设置左上角的按钮可用
//        getActionBar().setHomeButtonEnabled(true);

        /**
         * 给ListView里面的每一项设置点击事件
         */
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //每点击一次,就动态插入一个Fragment到FrameLayout中
                Fragment contentFragment = new ContentFragment();
                Bundle bundle = new Bundle();

                bundle.putString("text", menuLists.get(position));
                contentFragment.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();

                //用当前点击的的fragment替换掉原来的FrameLayout.    最后一定要commit,事务才会生效
                fragmentManager.beginTransaction().replace(R.id.content_frame, contentFragment).commit();

                //点击之后需要关闭当前的侧边栏
                mDrawerLayout.closeDrawer(mDrawerList);
            }
        });


        /**
         * 它是ActionBar和DrawerLayout之间联系的纽带
         *
         * 它的作用:
         *  1.改变android.R.id.home图标
         *  2.Drawer拉出,隐藏的时候,带有android.R.id.home动画效果(syncState())
         *  3.监听Drawer拉出,隐藏事件
         */
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                   //Activity
                mDrawerLayout,          //DrawertLayout
                R.mipmap.ic_drawer,     //左上角的图标
                R.string.drawer_open,   //Drawer打开描述
                R.string.drawer_close   //Drawer关闭描述
        ) {
            /**
             *当抽屉打开时候执行
             * @param drawerView
             */
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                //打开的时候更改标题
                getActionBar().setTitle("请选择");

                //重绘ActionBar上的菜单项
                invalidateOptionsMenu();    //会调用onPrepareOptionsMenu(),所以重写此方法
            }

            /**
             *当抽屉关闭时候执行
             * @param drawerView
             */
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                //关闭的时候更改标题
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu();    //会调用onPrepareOptionsMenu(),所以重写此方法
            }
        };

        //设置抽屉的监听事件
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    /**
     * 执行invalidateOptionsMenu的时候调用此方法
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        //获取DrawerLayout打开或关闭的状态
        boolean isDrawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);

        //设置右上角图标的是否可见状态,当抽屉打开的时候图标隐藏,当抽屉关闭的时候图标关闭
        //所以这个Visible的的状态是跟isDrawerOpen的状态是相反的
        menu.findItem(R.id.action_search).setVisible(!isDrawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //将ActionBar上的图标与Drawer结合起来
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        if (item.getItemId() == R.id.action_search) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri uri = Uri.parse("http://cursonjiang.github.io");
            intent.setData(uri);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 将ActionBarDrawerToggle与DrawerLayout的状态同步
     * 需要将syncState方法写在onPostCreate()方法里
     *
     * @param savedInstanceState
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        //需要将ActionBarDrawerToggle与DrawerLayout的状态同步
        //将ActionBarDrawerToggle的drawerImages图标,设置为ActionBar中的左上角图标
        mDrawerToggle.syncState();
    }

    /**
     * 当配置发生变化的时候调用
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        //重新配置
        mDrawerToggle.onConfigurationChanged(newConfig);
        super.onConfigurationChanged(newConfig);
    }
}
