package com.curson.actionbar;

import android.content.Context;
import android.support.v4.view.ActionProvider;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

/**
 * 自定义Action Provider
 */
public class MyActionProvider extends ActionProvider {

    /**
     * Creates a new instance.
     *
     * @param context Context for accessing resources.
     */
    public MyActionProvider(Context context) {
        super(context);
    }

    @Override
    public View onCreateActionView() {
        return null;
    }

    /**
     * 自定义ActionProvider的子菜单
     *
     * @param subMenu 子菜单
     */
    @Override
    public void onPrepareSubMenu(SubMenu subMenu) {
        subMenu.clear();
        subMenu.add("item 1").setIcon(R.mipmap.ic_launcher).
                setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return true;
                    }
                });

        subMenu.add("item 2").setIcon(R.mipmap.ic_launcher).
                setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return true;
                    }
                });
    }

    /**
     * 为了表示这个Action Provider是有子菜单的，需要重写hasSubMenu并返回true
     *
     * @return true
     */
    @Override
    public boolean hasSubMenu() {
        return true;
    }
}
