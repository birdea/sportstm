package com.example.test_team_manager.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.test_team_manager.utils.BDLog;

public class DBOpenHelper extends SQLiteOpenHelper{

	static final int DB_VERSION = 1;
	static final String DB_NAME = "coachbook.db";
	
	public DBOpenHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (oldVersion >= newVersion) {
			return;
		}
		BDLog.i("CameraDBOpenHelper.onUpgrade oldVersion = " + oldVersion + ", newVersion = " + newVersion);
	}

}
