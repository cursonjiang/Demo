package com.curson.slidingmdemoenu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Curson on 15/2/15.
 */
public class LeftMenuFragment extends Fragment implements AdapterView.OnItemClickListener {

    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 相当于Avtivity的setContentView();
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.left_menu_layout, null);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListView lv = (ListView) rootView.findViewById(R.id.listview);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                initData());

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(this);
    }

    private List<String> initData() {
        List<String> list = new ArrayList<String>();
        list.add(Fragment1.class.getSimpleName());
        list.add(Fragment2.class.getSimpleName());
        list.add(Fragment3.class.getSimpleName());
        list.add(Fragment4.class.getSimpleName());
        list.add(Fragment5.class.getSimpleName());
        return list;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //每点击一个item的时候,通知主页面显示该Fragment
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new Fragment1();
                break;
            case 1:
                fragment = new Fragment2();
                break;
            case 2:
                fragment = new Fragment3();
                break;
            case 3:
                fragment = new Fragment4();
                break;
            case 4:
                fragment = new Fragment5();
                break;
            default:
                break;
        }

        switchFragment(fragment);
    }

    private void switchFragment(Fragment fragment) {
        //通知主页面改变当前显示的fragment
        if (fragment != null) {
            if (getActivity() instanceof MainActivity) {
                MainActivity activity = (MainActivity) getActivity();
                activity.switchFragment(fragment);
            }
        }
    }
}
