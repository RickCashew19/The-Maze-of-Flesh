package main;

import data.Progress;
import entity.Entity;

public class EventHandler {

	GamePanel gp;
	EventRect eventRect[][][];
	Entity eventMaster;
	
	int previousEventX, previousEventY;
	boolean canTouchEvent = true;
	int tempMap, tempCol, tempRow;
	
	public EventHandler(GamePanel gp) {
		this.gp = gp;
		
		eventMaster = new Entity(gp);
		
		eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
		
		int map = 0;
		int col = 0;
		int row = 0;
		
		while(map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldCol) {

			eventRect[map][col][row] = new EventRect();
			eventRect[map][col][row].x = 20;
			eventRect[map][col][row].y = 20;
			eventRect[map][col][row].width = 8;
			eventRect[map][col][row].height = 8;
			eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
			eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;
			
			col++;
			if(col == gp.maxWorldCol) {
				col = 0;
				row++;
				
				if(row == gp.maxWorldRow) {
					row = 0;
					map++;
				}
			}
		}
		
		setDialogue();
	}
	
	public void setDialogue() {
		
		eventMaster.dialogues[0][0] = "You step on a shit!!";
		
		eventMaster.dialogues[1][0] = "HEAL!!!!\n(Progress Saved)";
		eventMaster.dialogues[2][0] = "Your State is Full!!\n(Progressed saved)";
	
		eventMaster.dialogues[3][0] = "You step on a shit!!";
	}
	
	public void checkEvent() {
	    int xDistance = Math.abs(gp.player.worldX - previousEventX);
	    int yDistance = Math.abs(gp.player.worldY - previousEventY);
	    int distance = Math.max(xDistance, yDistance);

	    if (distance > gp.tileSize) canTouchEvent = true;

	    if (canTouchEvent) {
	        // Check if player is on maze entrance tile
	        if (hit(0, 15, 9, "any")) {  teleport(1, 1, 16, gp.mazeArea); }
	        else if (hit(0, 16, 9, "any")) {  teleport(1, 1, 17, gp.mazeArea); }
	        if (hit(1,0,16, "any")) {  teleport(0, 15, 9, gp.mazeArea); }
	        else if (hit(1,0,17, "any")) {  teleport(0, 16, 9, gp.mazeArea); }
	        
	        
	        else if (hit(1, 31, 10, "any")) {  teleport(2, 14, 1, gp.mazeArea); }
	        else if (hit(1, 31, 11, "any")) {  teleport(2, 15, 1, gp.mazeArea); }
	        else if (hit(2,14,0, "any")) {  teleport(1,31,10, gp.mazeArea); }
	        else if (hit(2,15,0, "any")) {  teleport(1,31,11, gp.mazeArea); }
	        
	        else if (hit(2, 31, 16, "any")) {  teleport(3, 17, 1, gp.mazeArea); }
	        else if (hit(2, 31, 17, "any")) {  teleport(3, 18, 1, gp.mazeArea); }
	        else if (hit(3, 17, 1, "any")) {  teleport(2, 31, 16, gp.mazeArea); }
	        else if (hit(3, 18, 1, "any")) {  teleport(2, 31, 17, gp.mazeArea); }
	    }
	}

	
	public boolean hit(int map, int col, int row, String reqDirection) {
		
		boolean hit = false;

		if(map == gp.currentMap) {
			gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
			gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
			eventRect[map][col][row].x = col*gp.tileSize + eventRect[map][col][row].x;
			eventRect[map][col][row].y = row*gp.tileSize + eventRect[map][col][row].y;
			
			if(gp.player.solidArea.intersects(eventRect[map][col][row]) && eventRect[map][col][row].eventDone == false) {	
				if(gp.player.direction.contentEquals(reqDirection) || reqDirection.equals("any")) {
					hit = true;
					
					previousEventX = gp.player.worldX;
					previousEventY = gp.player.worldY;
				}
			}
			gp.player.solidArea.x = gp.player.solidAreaDefaultX;
			gp.player.solidArea.y = gp.player.solidAreaDefaultY;
			eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
			eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
		}
		
		return hit;
	}
	
	public void damagePit(int gameState) {
		
		gp.gameState = gameState;
		gp.playSE(6);
		eventMaster.startDialogue(eventMaster, 0);
		gp.player.life -= 1;
		// eventRect[col][row].eventDone = true;
		canTouchEvent = false;
	}
	
	public void healing(int gameState) {
		
		if(gp.keyH.enterPressed == true) {
			gp.gameState = gameState;
			gp.player.attackCanceled = true;
			gp.playSE(3);
			if(gp.player.life < gp.player.maxLife || gp.player.mana < gp.player.maxMana) {
				eventMaster.startDialogue(eventMaster, 1);
				gp.player.life += 1;
				gp.player.mana += 1;
				gp.saveLoad.save();
			} else {
				eventMaster.startDialogue(eventMaster, 2);
			}
			
		}	
	}
	
	public void teleport(int map, int col, int row,int area) {
			
		//Smooth TRANSITION
		gp.gameState = gp.transitionState;
		gp.nextArea = area;
		tempMap = map;
		tempCol = col;
		tempRow = row;
		canTouchEvent = false;
		gp.playSE(14);
	}


	public void speak(Entity entity) {
		
		if(gp.keyH.enterPressed == true) {
			gp.gameState = gp.dialogueState;
			gp.player.attackCanceled = true;
			entity.speak();
			
		}
	}
	
	public void ending() {
		
		if(Progress.endingSceneDone == false) {
			gp.gameState = gp.cutsceneState;
			gp.csManager.sceneNum = gp.csManager.ending;
		}
	}
}
