package com.example.test_team_manager.view;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.example.test_team_manager.helper.PlayerChipHelper;
import com.example.test_team_manager.model.PlayerChip;
import com.example.test_team_manager.utils.BDLog;
import com.example.test_team_manager.view.rules.PlayerChipInterface;

public class PlayGroundView extends RelativeLayout implements PlayerChipInterface {

	public PlayGroundView(Context context) {
		super(context);
		init(context);
	}

	public PlayGroundView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public PlayGroundView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private int backGroundColor = Color.rgb(33, 155, 33);
	private Paint paintPlayerChipCircle = new Paint();
	private Paint paintPlayerChipText = new Paint();
	private PlayerChipHelper playerChipHelper = new PlayerChipHelper();
	private PlayerChip playerChipGrab;

	private GestureDetector gestureDetector;
	
	public void init(Context context) {
		setBackgroundColor(backGroundColor);
		paintPlayerChipCircle.setColor(Color.rgb(188, 77, 77));
		paintPlayerChipText.setColor(Color.rgb(33, 22, 22));

		gestureDetector = new GestureDetector(context, new GestureListener());
	}
	private class GestureListener extends GestureDetector.SimpleOnGestureListener {

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e)
		{
			BDLog.i("onSingleTapConfirmed");
			return performClick();
		}

		@Override
		public void onLongPress(MotionEvent e)
		{
			BDLog.i("onLongPress");
			performLongClick();
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
		{
			BDLog.i("onFling");
			return super.onFling(e1, e2, velocityX, velocityY);
		}

		@Override
		public boolean onDoubleTap(MotionEvent e) {
			BDLog.i("onDoubleTap");
			return true;
		}

		@Override
		public boolean onDoubleTapEvent(MotionEvent e) {
			return false;
		}
	}

	@Override
	public void addPlayerChip() {
		playerChipHelper.addPlayerChip();
		invalidate();
	}

	@Override
	public void addPlayerChip(int id) {

	}

	@Override
	public void removePlayerChip() {
		playerChipHelper.removePlayerChip();
		invalidate();
	}

	@Override
	public void removePlayerChip(int id) {

	}

	@Override
	protected void onDraw(Canvas canvas) {
		drawPlayGround(canvas);
		drawPlayerChips(canvas);
		super.onDraw(canvas);
	}

	private void drawPlayGround(Canvas canvas) {

	}

	private void drawPlayerChips(Canvas canvas) {
		ArrayList<PlayerChip> playerChipList = playerChipHelper.getPlayerChipList();
		if (playerChipList == null) {
			return;
		}
		// grab chip
		PlayerChip grabChip = playerChipGrab;
		if (grabChip != null) {
			// draw outer circle
			int radius = (int) grabChip.length / 2;
			canvas.drawCircle(grabChip.viewPoint.x, grabChip.viewPoint.y, radius, paintPlayerChipCircle);
			// draw inner text (number)
			canvas.drawText(String.valueOf(grabChip.number), grabChip.viewPoint.x + radius, grabChip.viewPoint.y + radius, paintPlayerChipText);
		}

		// non-grab chips
		for (PlayerChip chip : playerChipList) {
			//
			if (chip.grab) {
				continue;
			}
			// draw outer circle
			int radius = (int) chip.length / 2;
			canvas.drawCircle(chip.viewPoint.x, chip.viewPoint.y, radius, paintPlayerChipCircle);
			// draw inner text (number)
			canvas.drawText(String.valueOf(chip.number), chip.viewPoint.x + radius, chip.viewPoint.y + radius, paintPlayerChipText);
		}
	}

	// grab player chip
	@Override
	@SuppressLint("ClickableViewAccessibility")
	public boolean onTouchEvent(MotionEvent event) {
		
		gestureDetector.onTouchEvent(event);
		
		int action = event.getAction();
		//BDLog.i("onTouchEvent on view:" + event);
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			handleTouchActionDown(event);
			break;
		case MotionEvent.ACTION_MOVE:
			handleTouchActionMove(event);
			break;
		case MotionEvent.ACTION_UP:
			handleTouchActionUp(event);
			break;
		}
		return super.onTouchEvent(event);
	}

	private enum ChipTouchMode {
		NONE, GRAB,
	}

	private ChipTouchMode chipTouchMode = ChipTouchMode.NONE;

	private void handleTouchActionDown(MotionEvent event) {
		// find if it exist
		ArrayList<PlayerChip> playerChipList = playerChipHelper.getPlayerChipList();
		if (playerChipList == null) {
			return;
		}
		chipTouchMode = ChipTouchMode.NONE;
		float touchX = event.getX();
		float touchY = event.getY();
		BDLog.d("handleTouchActionDown size:" + playerChipList.size());
		for (PlayerChip chip : playerChipList) {
			if (isGrabPointOnChip(chip, touchX, touchY)) {
				playerChipGrab = chip;
				playerChipGrab.grab = true;
				chipTouchMode = ChipTouchMode.GRAB;
				BDLog.e("handleTouchActionDown GRAB!!! :" + chip.number);
				break;
			}
		}
		if(ChipTouchMode.NONE.equals(chipTouchMode)){
			playerChipGrab = null;
		}
	}

	private boolean isGrabPointOnChip(PlayerChip chip, float touchX, float touchY) {
		Rect rect = new Rect();
		BDLog.d("isGrabPointOnChip touchX:" + touchX + ",touchY:" + touchY + "// viewX:" + chip.viewPoint.x + ",viewY:" + chip.viewPoint.y + ", num:" + chip.number);
		rect.set((int) (chip.viewPoint.x - left), (int) (chip.viewPoint.y - top), (int) (chip.viewPoint.x + chip.length), (int) (chip.viewPoint.y + chip.length));
		return rect.contains((int) touchX, (int) touchY);
	}

	private void handleTouchActionMove(MotionEvent event) {
		BDLog.d("handleTouchActionMove chipTouchMode:" + chipTouchMode);
		if (ChipTouchMode.GRAB.equals(chipTouchMode)) {
			playerChipGrab.viewPoint.x = event.getX() - left;
			playerChipGrab.viewPoint.y = event.getY() - top;
			BDLog.d("handleTouchActionMove chipTouchMode:" + chipTouchMode);
			invalidate();
		}
	}

	private void handleTouchActionUp(MotionEvent event) {

		BDLog.d("handleTouchActionUp playerChipGrab:" + playerChipGrab);

		// set renew player chip
		if (playerChipGrab != null) {
			playerChipGrab.grab = false;
			playerChipHelper.renewPlayerChip(playerChipGrab);
		}
		chipTouchMode = ChipTouchMode.NONE;
	}
	
	private int left, top, right, bottom;
	private int viewWidth, viewHeight;
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
		}
		super.onLayout(changed, l, t, r, b);
	}
	
}
