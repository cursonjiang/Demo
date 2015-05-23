package com.curson.volley;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

/**
 * 自定义GsonRequest
 */
public class GSONRequest<T> extends Request<T> {

    private final Response.Listener<T> mListener;

    private Gson mGson;

    private Class<T> mClass;

    public GSONRequest(int method, String url, Class<T> aClass,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mGson = new Gson();
        mClass = aClass;
        mListener = listener;
    }


    public GSONRequest(String url, Class<T> aClass, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        this(Method.GET, url, aClass, listener, errorListener);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {
        try {
            String jsonString = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
            return Response.success(mGson.fromJson(jsonString, mClass), HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

}
