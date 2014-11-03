package com.example.test_team_manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.test_team_manager.activity.PlayGroundActivity;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void onClickReadContacts(View view) {

	}

	public void onClickRegisterMember(View view) {

	}

	public void onClickShowPlayground(View view) {
		startActivity(new Intent(this, PlayGroundActivity.class));
	}

	public void onClickMakeSchedule(View view) {

	}

	public void onClickShareSchedule(View view) {

	}
}
