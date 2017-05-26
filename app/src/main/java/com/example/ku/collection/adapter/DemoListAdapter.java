package com.example.ku.collection.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ku.collection.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronan.zhuang on 7/21/16.
 */

public class DemoListAdapter extends BaseAdapter {

    private static final String TAG = DemoListAdapter.class.getSimpleName();
    ArrayList<Class> mDemoList = null;
    Context mContext = null;

    public DemoListAdapter(ArrayList<Class> mDemoList, Context mContext) {
        this.mDemoList = mDemoList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mDemoList.size();
    }

    @Override
    public Object getItem(int i) {
        if(mDemoList != null && mDemoList.size() > 0)
        return mDemoList.get(i);
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null) {
            view = View.inflate(mContext, R.layout.item_demo_list, null);
            holder = new ViewHolder();
            holder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);
        }else{
            holder  = (ViewHolder)view.getTag();
        }

        holder.name.setText(mDemoList.get(i).getSimpleName());
        return view;
    }

    public class ViewHolder{
       public TextView name;

    }
}
