package com.curson.drawerlayout;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Curson on 15/2/2.
 */
public class ContentFragment extends Fragment {

    private TextView mTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        mTextView = (TextView) view.findViewById(R.id.textview);

        //提取传过来的参数
        String text = getArguments().getString("text");
        mTextView.setText(text);
        return view;
    }
}
