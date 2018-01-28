package custom;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Aeiric on 2018/1/24.
 */

public class HScrollView extends HorizontalScrollView {
    private Timer mTimer;
    private int mScrollX;
    private int mOldSrollX;
    public static final int HANDLER_STOP = 1;
    public static final int HANDLER_SCROLL = 2;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLER_SCROLL:
                    startTimer();
                    if (mListener != null) {
                        mListener.onScroll(mScrollX);
                    }
                    break;
                case HANDLER_STOP:
                    stopTimer();
                    if (mListener != null) {
                        mListener.onStop(mScrollX);
                    }
                    break;
            }
        }
    };

    private int count = 0;

    private TimerTask task = null;

    private volatile boolean isTimer = false;

    private void startTimer() {
        if (isTimer) {
            return;
        }
        isTimer = true;
        stopTimer();
        mTimer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                if (mOldSrollX == mScrollX) {
                    count++;
                    if (count == 3) {
                        mHandler.sendEmptyMessage(HANDLER_STOP);

                    }
                } else {
                    mOldSrollX = mScrollX;
                }
            }
        };
        mTimer.schedule(task, 0, 100);
    }

    private void stopTimer() {
        isTimer = false;
        count = 0;
        if (mTimer != null) {
            if (task != null) {
                task.cancel();
            }
            mTimer.cancel();
            mTimer = null;
            task = null;
        }
    }

    public HScrollView(Context context) {
        super(context);
    }

    public HScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void listenerScroll() {
        this.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                mScrollX = getScrollX();
                mHandler.sendEmptyMessage(HANDLER_SCROLL);
            }
        });
    }

    private void unlistenerScroll() {
        getViewTreeObserver().addOnScrollChangedListener(null);
    }

    private OnHScrollListener mListener;

    public void setOnOnHScrollListener(OnHScrollListener listener) {
        if (listener != null) {
            this.mListener = listener;
            listenerScroll();
        } else {
            unlistenerScroll();
        }
    }

    public interface OnHScrollListener {

        public void onScroll(int scrollX);

        public void onStop(int scrollX);
    }
}
