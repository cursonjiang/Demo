package com.curson.slidingmdemoenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;


public class MainActivity extends SlidingFragmentActivity {

    private SlidingMenu sm;

    private Fragment contentFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_frame);

        //设置Slidingmenu使用的layout文件
        setBehindContentView(R.layout.menu_frame);

        //获得Slidingmenu的实例
        sm = getSlidingMenu();

        //设置默认显示的Fragment
        if (savedInstanceState == null) {
            contentFragment = new Fragment1();
        } else {
            contentFragment = getSupportFragmentManager().getFragment(savedInstanceState, "contentFragment");
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, contentFragment)
                .commit();

        //得到slidingmenu需要显示的fragment的实例
        Fragment leftFragment = new LeftMenuFragment();

        getSupportFragmentManager()//拿到Fragment管理器
                .beginTransaction()//Fragment的事务管理
                .replace(R.id.menu_frame, leftFragment)//参数1:layout的id 参数2:要显示的fragment的实例
                .commit();

        sm.setMode(SlidingMenu.LEFT_RIGHT);//Slidingmenu滑出的方向
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);//Slidingmenu滑出的范围
        sm.setShadowDrawable(R.drawable.shadow);//slidingmenu边缘的阴影图片
        sm.setShadowWidthRes(R.dimen.shadow_width);//设置阴影的宽度
        sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);//设置主页面显示的宽度

        //显示右侧Slidingmenu
        sm.setSecondaryMenu(R.layout.menu_frame_right);
        sm.setShadowDrawable(R.drawable.shadowright);

        RightMenuFragment rightMenuFragment = new RightMenuFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame_right, rightMenuFragment).commit();

        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setSlidingActionBarEnabled(false);
        

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "contentFragment", contentFragment);
    }

    /**
     * 通知主页面切换当前显示的fragment
     *
     * @param fragment
     */
    public void switchFragment(Fragment fragment) {
        contentFragment = fragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();

        //slidingmenu的动态开关
        sm.toggle();
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                sm.toggle();
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }
}
