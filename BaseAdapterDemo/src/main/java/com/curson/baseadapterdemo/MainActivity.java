package com.curson.baseadapterdemo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private List<ItemBean> itemBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemBeanList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            itemBeanList.add(new ItemBean("内容" + i, R.mipmap.ic_launcher, "标题" + i));
        }

        ListView listView = (ListView) findViewById(R.id.lv_main);
        listView.setAdapter(new MyAdapter(this, itemBeanList));
    }


}
