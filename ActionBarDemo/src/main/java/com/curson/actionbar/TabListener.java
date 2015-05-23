package com.curson.actionbar;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

/**
 * 添加导航Tabs
 */
public class TabListener<T extends Fragment> implements ActionBar.TabListener {

    private Fragment mFragment;

    private Activity mActivity;

    private String mTag;

    private Class mClass;

    public TabListener(Activity activity, String tag, Class<T> aClass) {
        mActivity = activity;
        mTag = tag;
        mClass = aClass;
    }

    /**
     * 当Tabl被选中的时候
     *
     * @param tab
     * @param ft
     */
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        //当Tab被选中的时候
        //判断Fragment是否为空
        if (mFragment == null) {
            //如果为空就创建一个Fragment
            mFragment = Fragment.instantiate(mActivity, mClass.getName());

            //将新创建的Fragment添加到活动中
            ft.add(android.R.id.content, mFragment, mTag);
        } else {
            //如果不为空就显示出来
            ft.attach(mFragment);
        }
    }

    /**
     * 当Tab没有被选中的时候
     *
     * @param tab
     * @param ft
     */
    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        //如果Fragment不等于空
        if (mFragment != null) {
            //将资源释放掉
            ft.detach(mFragment);
        }
    }

    /**
     * 当Tab重新被选中的时候，如果没有特殊要求是不进行处理的
     *
     * @param tab
     * @param ft
     */
    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
