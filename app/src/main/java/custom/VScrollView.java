package custom;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ScrollingView;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Aeiric on 2018/1/28.
 */

public class VScrollView extends ScrollView {
    private Timer mTimer;
    private int mScrollY;
    private int mOldSrollY;
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
                        mListener.onScroll(mScrollY);
                    }
                    break;
                case HANDLER_STOP:
                    stopTimer();
                    if (mListener != null) {
                        mListener.onStop(mScrollY);
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
                if (mOldSrollY == mScrollY) {
                    count++;
                    if (count == 3) {
                        mHandler.sendEmptyMessage(HANDLER_STOP);

                    }
                } else {
                    mOldSrollY = mScrollY;
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

    public VScrollView(Context context) {
        super(context);
    }

    public VScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void listenerScroll() {
        this.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                mScrollY = getScrollY();
                mHandler.sendEmptyMessage(HANDLER_SCROLL);
            }
        });
    }

    private void unlistenerScroll() {
        getViewTreeObserver().addOnScrollChangedListener(null);
    }

    private OnVScrollListener mListener;

    public void setOnOnVScrollListener(VScrollView.OnVScrollListener listener) {
        if (listener != null) {
            this.mListener = listener;
            listenerScroll();
        } else {
            unlistenerScroll();
        }
    }

    public interface OnVScrollListener {

        public void onScroll(int scrollX);

        public void onStop(int scrollX);
    }
}
