package com.example.test_team_manager.view;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.example.test_team_manager.utils.BDLog;

public class PlayTimerView extends RelativeLayout {

	public PlayTimerView(Context context) {
		super(context);
		init(context);
	}

	public PlayTimerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public PlayTimerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private int backGroundColor = Color.rgb(233, 255, 233);
	
	private RectF rectTimerArc1 = new RectF();
	
	private Paint paintTimerCircle1 = new Paint();
	private Paint paintTimerCircle2 = new Paint();
	private Paint paintTimerArc1 = new Paint();
	private Paint paintTimerText = new Paint();
	private Paint paintTimerTextHour = new Paint();
	private Paint paintTimerTextMinute = new Paint();
	private Paint paintTimerTextSecond = new Paint();

	private void init(Context context) {
		setBackgroundColor(backGroundColor);
		//
		paintTimerText.setColor(Color.rgb(243, 242, 222));
		paintTimerText.setTextSize(40);
		paintTimerText.setStrokeWidth(5f);
		paintTimerText.setTypeface(Typeface.DEFAULT_BOLD);
		//
		paintTimerTextHour.setColor(Color.rgb(243, 242, 222));
		paintTimerTextHour.setTextSize(40);
		paintTimerTextHour.setStrokeWidth(5f);
		paintTimerTextHour.setTypeface(Typeface.DEFAULT_BOLD);
		//
		paintTimerTextMinute.setColor(Color.rgb(243, 242, 222));
		paintTimerTextMinute.setTextSize(40);
		paintTimerTextMinute.setStrokeWidth(5f);
		paintTimerTextMinute.setTypeface(Typeface.DEFAULT_BOLD);
		//
		paintTimerTextSecond.setColor(Color.rgb(243, 242, 222));
		paintTimerTextSecond.setTextSize(40);
		paintTimerTextSecond.setStrokeWidth(5f);
		paintTimerTextSecond.setTypeface(Typeface.DEFAULT_BOLD);
		//
		paintTimerCircle1.setColor(Color.rgb(13, 13, 13));
		paintTimerCircle1.setAntiAlias(true);
		paintTimerCircle2.setColor(Color.rgb(153, 162, 182));
		paintTimerCircle2.setAntiAlias(true);
		paintTimerArc1.setColor(Color.rgb(73, 72, 72));
		paintTimerArc1.setAntiAlias(true);
	}
	
	private static final int MSG_INTERVAL_NOTIFIER_UPDATE = 0x01;
	private int intervalNotifyTimeMs = 1;
	private Timer intervalNotifier;
	
	private class IntervalNotifier extends TimerTask {
		@Override
		public void run() {
			uiHandler.sendEmptyMessage(MSG_INTERVAL_NOTIFIER_UPDATE);
		}
	}

	private Handler uiHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == MSG_INTERVAL_NOTIFIER_UPDATE) {
				invalidate();
			}
			super.handleMessage(msg);
		}
	};
	
	private void startUpdateTimerView() {
		if (intervalNotifyTimeMs > 0) {
			intervalNotifier = new Timer();
			intervalNotifier.schedule(new IntervalNotifier(), 0, intervalNotifyTimeMs);
		}
	}
	
	private void stopUpdateTimerView() {
		if (intervalNotifier == null) {
			return;
		}
		intervalNotifier.cancel();
	}
	
	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		startUpdateTimerView();
	}
	
	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		stopUpdateTimerView();
	}

	private int left, top, right, bottom;
	private int centerX, centerY;
	private int viewWidth, viewHeight;
	private float radius;

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		boolean isFirst = (0 == left && 0 == top && 0 == right && 0 == bottom);
		left = l;
		top = t;
		right = r;
		bottom = b;
		if (changed || isFirst) {
			viewWidth = right - left;
			viewHeight = bottom - top;
			centerX = (right + left) / 2;
			centerY = (top + bottom) / 2;
			//
			radius = Math.min(viewWidth, viewHeight) / 2f;
			//
			rectTimerArc1.set(l, t, r, b);
		}
		BDLog.e("onLayout left:" + left + ", top:" + top + ", right:" + right + ", bottom:" + bottom);
		super.onLayout(changed, l, t, r, b);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// step.1 outter circle
		canvas.drawCircle(centerX, centerY, radius, paintTimerCircle1);
		// step.2 outter arc
		canvas.drawArc(rectTimerArc1, 0, 15, true, paintTimerArc1);
		// step.3 inner circle
		canvas.drawCircle(centerX, centerY, radius - 10, paintTimerCircle1);
		// step.4 text (HOUR : MIN : SEC : MilliSEC)
		drawCountDownText(canvas);
	}
	
	private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault());
	private long timeStarted = 0;
	
	public void setTimeStarted() {
		timeStarted = System.currentTimeMillis();
	}
	
	private String getCountDownText(){
		long gap = System.currentTimeMillis();
		if(timeStarted > 0){
			gap = timeStarted - System.currentTimeMillis();
		}
		return sdf.format(new Date(gap));
	}
	
	private void drawCountDownText(Canvas canvas){
		canvas.drawText(getCountDownText(), centerX, centerY, paintTimerText);
	}
}
