package com.aeiric.customscrollview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import custom.VScrollView;

/**
 * Created by Aeiric on 2018/1/28.
 */

public class VActivity extends AppCompatActivity {
    private VScrollView mScrollView;
    private TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical);
        mScrollView = findViewById(R.id.scroll);
        mText = findViewById(R.id.tv_scroll);
        mScrollView.setOnOnVScrollListener(new VScrollView.OnVScrollListener() {
            @Override
            public void onScroll(int scrollY) {
                mText.setTextColor(Color.parseColor("#000000"));
                mText.setText("scrolling  Y=" + scrollY);
                Log.e("VActivity", "------scrollX----" + scrollY);
            }

            @Override
            public void onStop(int scrollY) {
                mText.setText("scroll stop y=" + scrollY);
                mText.setTextColor(Color.parseColor("#FF0000"));
                Log.e("VActivity", "------scroll---stop---" + scrollY);
            }
        });

    }
}
