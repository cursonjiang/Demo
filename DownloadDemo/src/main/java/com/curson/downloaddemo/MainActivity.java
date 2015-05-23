package com.curson.downloaddemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.curson.downloaddemo.entities.FileInfo;
import com.curson.downloaddemo.service.DownloadService;


public class MainActivity extends ActionBarActivity {

    private TextView tv_FileName;
    private ProgressBar mProgressBar;
    private Button mBtStart;
    private Button mBtStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    /**
     * 初始化组件
     */
    private void initView() {

        tv_FileName = (TextView) findViewById(R.id.tv_FileName);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mBtStart = (Button) findViewById(R.id.bt_Start);
        mBtStop = (Button) findViewById(R.id.bt_Stop);
        mProgressBar.setMax(100);

        //创建文件信息对象
        final FileInfo fileInfo = new FileInfo(
                "imooc.apk",
                0,
                0,
                0,
                "http://www.imooc.com/mobile/imooc.apk"
        );

        tv_FileName.setText(fileInfo.getFileName());

        //添加事件监听
        mBtStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //通过Intent传递参数给Service
                Intent intent = new Intent(MainActivity.this, DownloadService.class);
                intent.setAction(DownloadService.ACTION_START);
                intent.putExtra("fileInfo", fileInfo);
                startService(intent);
            }
        });

        mBtStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DownloadService.class);
                intent.setAction(DownloadService.ACTION_STOP);
                intent.putExtra("fileInfo", fileInfo);
                startService(intent);
            }
        });

        //注册广播接收器
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadService.ACTION_UPDATE);
        registerReceiver(mReceiver, filter);
    }

    /**
     * 更新UI的广播接收器
     */
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (DownloadService.ACTION_UPDATE.equals(intent.getAction())) {
                int finished = intent.getIntExtra("finished", 0);
                mProgressBar.setProgress(finished);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}
