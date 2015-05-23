package com.curson.baseadapterdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by root on 15/4/12.
 */
public class MyAdapter extends BaseAdapter {

    private List<ItemBean> mList;
    private LayoutInflater mInflater;

    public MyAdapter(Context context, List<ItemBean> list) {
        mList = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.lv_item, null);
            viewHolder.mImageView = (ImageView) convertView.findViewById(R.id.iv_image);
            viewHolder.mContent = (TextView) convertView.findViewById(R.id.tv_content);
            viewHolder.mTitle = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ItemBean bean = mList.get(position);
        viewHolder.mImageView.setImageResource(bean.ItemImageResid);
        viewHolder.mContent.setText(bean.ItemContent);
        viewHolder.mTitle.setText(bean.ItemTitle);
        return convertView;
    }

    class ViewHolder {
        public ImageView mImageView;
        public TextView mContent;
        public TextView mTitle;
    }
}
