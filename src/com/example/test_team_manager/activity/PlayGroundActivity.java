package com.example.test_team_manager.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.test_team_manager.R;
import com.example.test_team_manager.utils.BDLog;
import com.example.test_team_manager.view.PlayGroundView;

public class PlayGroundActivity extends Activity {

	private PlayGroundView playGroundView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_ground);

		playGroundView = (PlayGroundView) findViewById(R.id.playGroundView);
	}

	public void onClickAddPlayer(View view) {
		playGroundView.addPlayerChip();
	}

	public void onClickRemovePlayer(View view) {
		playGroundView.removePlayerChip();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//BDLog.w("onTouchEvent on activity:" + event);
		return playGroundView.dispatchTouchEvent(event);
		//return super.onTouchEvent(event);
	}
}
