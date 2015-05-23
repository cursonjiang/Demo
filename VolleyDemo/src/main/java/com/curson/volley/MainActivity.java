package com.curson.volley;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.curson.volley.bean.Weather;
import com.curson.volley.bean.WeatherInfo;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

/**
 * Volley是Android平台网络通信库:它使网络通信 更快.更简单.更健壮
 * <p/>
 * Volley提供的功能:
 * 1.JSON字符串,图片(异步方式)
 * 2.网络请求的排序
 * 3.网络请求的优先级处理
 * 4.缓存
 * 5.多级别的取消请求
 * 6.与Activity生命周期联动
 */
public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    private ImageView mImageView;

    private NetworkImageView mNetworkImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.imageView);
        mNetworkImageView = (NetworkImageView) findViewById(R.id.network_imageView);

        getStringVolley();
        getJSONVolley();
        getImageLoaderVolley();
        NetWorkImageViewVolley();
        XMLRequest();
        GsonRequest();
    }

    /**
     * 请求一条文本数据
     */
    public void getStringVolley() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(
                "http://www.baidu.com",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.i(TAG, s);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.i(TAG, volleyError.getMessage(), volleyError);
                    }
                }
        );
        requestQueue.add(stringRequest);
    }

    /**
     * 获取JSON字符串
     */
    public void getJSONVolley() {

        //获取一个请求队列对象
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                "http://m.weather.com.cn/data/101010100.html",  //url
                null,
                new Response.Listener<JSONObject>() {   //服务器响应成功的回调
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.i(TAG, jsonObject.toString());
                    }
                },
                new Response.ErrorListener() {          //服务器响应失败的回调
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.i(TAG, volleyError.getMessage(), volleyError);
                    }
                }
        );

        //将请求添加到请求队列中
        requestQueue.add(jsonObjectRequest);
    }


    /**
     * 异步加载图片
     */
    public void getImageLoaderVolley() {
        //创建一个请求队列对象
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //LruCache缓存
        final LruCache<String, Bitmap> lruCache = new LruCache<>(20);

        //创建一个ImageLoader对象
        ImageLoader imageLoader = new ImageLoader(
                requestQueue,                   //RequestQueue对象
                new ImageLoader.ImageCache() {  //ImageCache对象
                    @Override
                    public Bitmap getBitmap(String key) {
                        return lruCache.get(key);
                    }

                    @Override
                    public void putBitmap(String key, Bitmap bitmap) {
                        lruCache.put(key, bitmap);
                    }
                }
        );

        //获取一个ImageListener对象
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(
                mImageView,             //ImageView
                R.mipmap.ic_launcher,   //加载过程中显示的图片
                R.mipmap.ic_launcher    //加载失败显示的图片
        );

        //第一个参数是url , 第二个参数是ImageListener对象
        imageLoader.get("http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg", imageListener);
    }

    /**
     * NetworkImageView是一个自定义控制，它是继承自ImageView的，
     * 具备ImageView控件的所有功能，并且在原生的基础之上加入了加载网络图片的功能
     */
    public void NetWorkImageViewVolley() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final LruCache<String, Bitmap> lruCache = new LruCache<>(20);
        ImageLoader imageLoader = new ImageLoader(
                requestQueue,
                new ImageLoader.ImageCache() {
                    @Override
                    public Bitmap getBitmap(String key) {
                        return lruCache.get(key);
                    }

                    @Override
                    public void putBitmap(String key, Bitmap bitmap) {
                        lruCache.put(key, bitmap);
                    }
                }
        );
        mNetworkImageView.setImageUrl("http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg", imageLoader);
        mNetworkImageView.setDefaultImageResId(R.mipmap.ic_launcher);
        mNetworkImageView.setErrorImageResId(R.mipmap.ic_launcher);
    }


    /**
     * 使用自定义XML解析
     */
    public void XMLRequest() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        XMLRequest xmlRequest = new XMLRequest(
                "http://flash.weather.com.cn/wmaps/xml/china.xml",
                new Response.Listener<XmlPullParser>() {
                    @Override
                    public void onResponse(XmlPullParser xmlPullParser) {
                        try {
                            int eventType = xmlPullParser.getEventType();
                            while (eventType != XmlPullParser.END_DOCUMENT) {
                                switch (eventType) {
                                    case XmlPullParser.START_TAG:
                                        String nodeName = xmlPullParser.getName();
                                        if ("city".equals(nodeName)) {
                                            String pName = xmlPullParser.getAttributeValue(0);
                                            Log.i(TAG, " pName is " + pName);
                                        }
                                        break;
                                }
                                eventType = xmlPullParser.next();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.i(TAG, volleyError.getMessage(), volleyError);
                    }
                }
        );
        requestQueue.add(xmlRequest);
    }

    /**
     * 自定义GSON解析
     */
    public void GsonRequest() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        GSONRequest<Weather> gsonRequest = new GSONRequest<>(
                "http://www.weather.com.cn/data/sk/101010100.html",
                Weather.class,
                new Response.Listener<Weather>() {
                    @Override
                    public void onResponse(Weather weather) {
                        WeatherInfo weatherInfo = weather.getWeatherInfo();
                        Log.i(TAG, "city is:" + weatherInfo.getCity());
                        Log.i(TAG, "temp is:" + weatherInfo.getTemp());
                        Log.i(TAG, "time is:" + weatherInfo.getTime());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e(TAG, volleyError.getMessage(), volleyError);
                    }
                }
        );
        requestQueue.add(gsonRequest);
    }


}
