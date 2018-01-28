package com.aeiric.customscrollview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import custom.HScrollView;

/**
 * Created by Aeiric on 2018/1/28.
 */

public class HActivity extends AppCompatActivity {

    private HScrollView mScrollView;
    private TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal);
        mScrollView = findViewById(R.id.scroll);
        mText = findViewById(R.id.tv_scroll);
        mScrollView.setOnOnHScrollListener(new HScrollView.OnHScrollListener() {
            @Override
            public void onScroll(int scrollX) {
                mText.setText("scrolling  X=" + scrollX);
                Log.e("HActivity", "------scrollX----" + scrollX);
            }

            @Override
            public void onStop(int scrollX) {
                mText.setText("scroll stop X=" + scrollX);
                Log.e("HActivity", "------scroll---stop---" + scrollX);
            }
        });

    }

}
