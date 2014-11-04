package com.example.test_team_manager.model;

import android.graphics.Paint;


public class PlayerChip {

	public int id;
	public int number;
	public int age;
	public String name;
	public float length;
	public ViewPoint viewPoint;
	public PlayerFormation formation;
	public Paint paint;
	public boolean grab = false;
	
	public static class ViewPoint{
		
		public float x, y;
		
		public ViewPoint(float x, float y){
			this.x = x;
			this.y = y;
		}
	}
}
