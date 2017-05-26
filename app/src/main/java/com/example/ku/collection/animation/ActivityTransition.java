package com.example.ku.collection.animation;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.SharedElementCallback;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.example.ku.collection.R;
import com.example.ku.collection.activity.BaseActivity;

import java.util.List;
import java.util.Map;

/**
 * @author Ronan.zhuang
 * @Date 5/16/17.
 * All copyright reserved.
 */

public class ActivityTransition extends BaseActivity {

    public static final String ELEMENT_NAME = "img";
    private static final String KEY_ID = "ViewTransitionValues:id";
    private ImageView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);
        mImageView = (ImageView) findViewById(R.id.imageView);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityTransition.this,ActivityTransitionDetail.class);
                intent.putExtra(KEY_ID, v.getTransitionName());
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(ActivityTransition.this,v,ELEMENT_NAME);
                startActivity(intent,options.toBundle());
            }
        });
        setup();
    }

    private void setup() {
        String name = getIntent().getStringExtra(KEY_ID);
        if (name != null) {
            setEnterSharedElementCallback(new SharedElementCallback() {
                @Override
                public void onMapSharedElements(List<String> names,
                                                Map<String, View> sharedElements) {
                    sharedElements.put(ELEMENT_NAME, mImageView);
                }
            });
        }
    }
}
