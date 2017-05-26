package com.example.ku.collection.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.ku.collection.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Ronan.zhuang
 * @Date 5/17/17.
 * All copyright reserved.
 */

public class CardViewActivity extends BaseActivity {
    private RecyclerView mRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardview);
        mRecycleView = (RecyclerView) findViewById(R.id.list);
        // 设置LinearLayoutManager
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        // 设置ItemAnimator
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        // 设置固定大小
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setAdapter(new MyAdapter(this, getData()));
    }

    public List<String> getData() {
        List<String> result = new ArrayList<>();
        result.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495013244667&di=c4d7bd183b1f83b800d55054932e3965&imgtype=0&src=http%3A%2F%2Fweitang.b0.upaiyun.com%2F2016%2F16600%2F1458565562109900.jpg");
        result.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495607994&di=f4d3172f2d14373a0849ec6ee36b82c0&imgtype=jpg&er=1&src=http%3A%2F%2Fmvimg1.meitudata.com%2F55306317f101c8666.jpg");
        result.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495013291903&di=79fd396d8ed19c883838f5bef5c50223&imgtype=0&src=http%3A%2F%2Fimgbdb2.bendibao.com%2Fsuzhoubdb%2F20175%2F04%2F2017504102832_63261.jpg");
        result.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495013305642&di=281218f86ba16b9792e9e2e4d9cfe67d&imgtype=0&src=http%3A%2F%2Fimage16-c.poco.cn%2Fmypoco%2Fmyphoto%2F20140827%2F00%2F17490702620140827004731062.jpg%3F1000x667_120");
        return result;
    }


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private Context mContext;
        private List<String> mData;

        public MyAdapter(Context mContext, List<String> mData) {
            this.mContext = mContext;
            this.mData = mData;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false));
        }

        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
            Glide.with(mContext).load(mData.get(position)).asBitmap().into(holder.mImageView);
//            holder.mImageView.setImageResource(R.drawable.ic_launcher);
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView mImageView;

            public ViewHolder(View itemView) {
                super(itemView);
                this.mImageView = (ImageView) itemView.findViewById(R.id.pic);
            }
        }
    }
}
