package com.example.test_team_manager.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import android.graphics.Rect;

import com.example.test_team_manager.model.PlayerChip;
import com.example.test_team_manager.model.PlayerChip.ViewPoint;

public class PlayerChipHelper {

	public Map<Integer, PlayerChip> playerChipMap = Collections.synchronizedMap(new HashMap<Integer, PlayerChip>());
	public ArrayList<PlayerChip> playerChipList;
	
	public PlayerChipHelper() {
		playerChipMap.clear();
	}
	
	public void setViewBounds(int width, int height){
		viewWidth = width;
		viewHeight = height;
	}

	private int viewWidth = 480;
	private int viewHeight = 800;
	private int lastId = 0;
	private int lastNumber = 0;

	public PlayerChip makeRandomPlayerChip() {
		Random random = new Random();
		PlayerChip item = new PlayerChip();
		item.age = 20;
		item.id = lastId;
		item.number = lastNumber++;
		item.length = 50;
		item.viewPoint = new ViewPoint(random.nextInt(viewWidth), random.nextInt(viewHeight));
		return item;
	}

	public void addPlayerChip() {
		lastId++;
		playerChipMap.put(lastId, makeRandomPlayerChip());
		refreshList();
	}

	public void removePlayerChip() {
		playerChipMap.remove(lastId);
		refreshList();
	}
	
	public void refreshList(){
		ArrayList<PlayerChip> list = new ArrayList<PlayerChip>();
		Iterator<PlayerChip> iter = playerChipMap.values().iterator();
		while (iter.hasNext()) {
			list.add(iter.next());
		}
		playerChipList = list;
	}
	
	public void renewPlayerChip(PlayerChip chip){
		playerChipMap.put(chip.id, chip);
		refreshList();		
	}

	public PlayerChip getPlayerChip(int key) {
		return playerChipMap.get(key);
	}

	public ArrayList<PlayerChip> getPlayerChipList() {
		return playerChipList;
	}
}
