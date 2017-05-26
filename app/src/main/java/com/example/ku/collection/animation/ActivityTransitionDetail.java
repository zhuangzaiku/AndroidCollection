package com.example.ku.collection.animation;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.example.ku.collection.R;
import com.example.ku.collection.activity.BaseActivity;

/**
 * @author Ronan.zhuang
 * @Date 5/16/17.
 * All copyright reserved.
 */

public class ActivityTransitionDetail extends BaseActivity {

    private static final String KEY_ID = "ViewTransitionValues:id";
    private ImageView mImageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_detail);
        mImageView = (ImageView) findViewById(R.id.imageView);
        mImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher));
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityTransitionDetail.this,ActivityTransition.class);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(ActivityTransitionDetail.this,v,ActivityTransition.ELEMENT_NAME);
                startActivity(intent,options.toBundle());
            }
        });
    }
}
