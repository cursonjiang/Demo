package com.curson.actionbar;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import com.curson.actionbar.fragment.OneFragment;
import com.curson.actionbar.fragment.TwoFragment;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //得到ActionBar实例
        ActionBar actionBar = getActionBar();

        //启用左上角的返回按钮
        actionBar.setDisplayHomeAsUpEnabled(true);

        //设置导航模式
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        //创建一个Tab实例，设置标题，设置监听器
        ActionBar.Tab tab = actionBar
                .newTab()
                .setText("Tab1")
                .setTabListener(
                        new TabListener<OneFragment>(this, "one", OneFragment.class));

        //将tab添加到ActionBar中
        actionBar.addTab(tab);


        tab = actionBar
                .newTab()//创建一个Tab实例
                .setText("Tab2")//设置标题
                .setTabListener(//设置监听器
                        new TabListener<TwoFragment>(this, "two", TwoFragment.class));
        //将tab添加到ActionBar中
        actionBar.addTab(tab);


        setOverflowShowingAlways();
    }

    /**
     * 设置显示overflow
     */
    private void setOverflowShowingAlways() {
        try {
            ViewConfiguration configuration = ViewConfiguration.get(this);

            //系统就是根据这个变量的值来判断手机有没有物理Menu键
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");

            //设置为true防止出现IllegalAccessExceptions异常
            menuKeyField.setAccessible(true);

            //通过反射修改值，永远为false
            menuKeyField.setBoolean(configuration, false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * overflow中的Action按钮应不应该显示图标，
     * 是由  MenuBuilder   这个类的   setOptionalIconsVisible  方法来决定的，
     * 如果我们在overflow被展开的时候给这个方法传入true，那么里面的每一个Action按钮对应的图标就都会显示出来
     * <p/>
     * 当overflow被展开的时候就会回调这个方法，
     * 接着在这个方法的内部通过返回反射的方法将MenuBuilder的setOptionalIconsVisible变量设置为true
     *
     * @param featureId
     * @param menu
     * @return
     */
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    /**
     * 创建菜单
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem shareItem = menu.findItem(R.id.action_share);

        //获取ShareActionProvider对象
        ShareActionProvider provider = (ShareActionProvider) shareItem.getActionProvider();

        //构建自己想要的意图
        provider.setShareIntent(getDefaultIntent());
        return true;
    }

    private Intent getDefaultIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        return intent;
    }

    /**
     * 菜单选项
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                Toast.makeText(this, "哈哈哈", Toast.LENGTH_SHORT).show();
                Intent intent = NavUtils.getParentActivityIntent(this);//获取跳转至父Activity的Intent
                if (NavUtils.shouldUpRecreateTask(this, intent)) {  //如果父Activity和当前Activity不在同一个任务栈
                    TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);//就新创建一个任务栈
                    taskStackBuilder.addNextIntentWithParentStack(intent);//把父Activity的Intent传入到新的任务栈
                    taskStackBuilder.startActivities();//在新的任务栈启动Activity
                } else {
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    NavUtils.navigateUpTo(this, intent);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
