package com.example.ku.collection.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

import com.example.ku.collection.R;
import com.example.ku.collection.widget.StrokeTextView;

/**
 * @author Ronan.zhuang
 * @date 8/22/16
 */

public class WidgetActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget);
        initView();
    }

    public void initView(){
        TextView tv = (TextView) findViewById(R.id.combo);
        tv.setText(combo(100));
    }

    public SpannableString combo(int count) {

        SpannableString spanStr = new SpannableString("X" + String.valueOf(count));
        spanStr.setSpan(new RelativeSizeSpan(1.7f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanStr.setSpan(new RelativeSizeSpan(3.1f), 1, spanStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanStr;
    }
}
