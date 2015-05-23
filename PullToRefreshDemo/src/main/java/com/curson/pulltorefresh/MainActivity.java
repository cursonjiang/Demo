package com.curson.pulltorefresh;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private PullToRefreshListView mListView;
    private ArrayAdapter<String> adapter;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (PullToRefreshListView) findViewById(R.id.listview);
        List<String> stringList = new ArrayList<String>();
        stringList.add("ActionBar");
        stringList.add("DrawerLayout");
        stringList.add("PullToRefresh");
        stringList.add("Volley");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringList);
        mListView.setAdapter(adapter);


        //下拉监听
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            /**
             * 呈现正在载入..
             * @param refreshView
             */
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            Thread.sleep(3000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    /**
                     * 执行主线程操作
                     * @param aVoid
                     */
                    @Override
                    protected void onPostExecute(Void aVoid) {
                        adapter.addAll("android", "developer", "姜炳臣");

                        //刷新完成
                        mListView.onRefreshComplete();
                    }
                }.execute();
            }
        });
    }
}
