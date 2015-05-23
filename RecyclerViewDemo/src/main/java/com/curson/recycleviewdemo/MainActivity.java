package com.curson.recycleviewdemo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private List<String> mDatas;
    private RecyclerView mRecyclerView;
    private MainAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recycleview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter = new MainAdapter());
    }

    private void initData() {
        mDatas = new ArrayList<>();
        for (int i = 'A'; i <= 'z'; i++) {
            mDatas.add("" + (char) i);
        }
    }

    class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHoder> {

        @Override
        public MainAdapter.ViewHoder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new ViewHoder(LayoutInflater.from(MainActivity.this).inflate(R.layout.item, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(MainAdapter.ViewHoder viewHoder, int i) {
            viewHoder.mTextView.setText(mDatas.get(i));
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        public class ViewHoder extends RecyclerView.ViewHolder {

            TextView mTextView;

            public ViewHoder(View itemView) {
                super(itemView);
                mTextView = (TextView) itemView.findViewById(R.id.id_textview);
            }
        }
    }


}
