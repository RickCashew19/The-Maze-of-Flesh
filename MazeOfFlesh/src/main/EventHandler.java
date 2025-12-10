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

        initializeEventRects();
        setDialogue();
    }
    
    public void initializeEventRects() {
        for (int map = 0; map < gp.maxMap; map++) {
            for (int col = 0; col < gp.maxWorldCol; col++) {
                for (int row = 0; row < gp.maxWorldRow; row++) {
                    eventRect[map][col][row] = new EventRect();
                    eventRect[map][col][row].x = 20;
                    eventRect[map][col][row].y = 20;
                    eventRect[map][col][row].width =20;
                    eventRect[map][col][row].height = 20;
                    eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
                    eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;
                }
            }
        }
    }

    private void setDialogue() {
        eventMaster.dialogues[0][0] = "";
        eventMaster.dialogues[1][0] = "HEAL!!!!\n(Progress Saved)";
        eventMaster.dialogues[2][0] = "Your Health is Full!!\n(Progressed saved)";
        eventMaster.dialogues[3][0] = "";
    }

    public void checkEvent() {
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);

        if (distance > gp.tileSize)
            canTouchEvent = true;

        if (canTouchEvent) {
        	
        	// safe to maze01
            hitTele(0,34,14,"safeArea",4,0,14,"mazeArea");
            hitTele(0,34,15,"safeArea",4,0,15,"mazeArea");
            hitTele(0,34,16,"safeArea",4,0,16,"mazeArea");

            hitTele(4,2,34,"mazeArea",5,2,0,"mazeArea");
            hitTele(4,3,34,"mazeArea",5,3,0,"mazeArea");
            hitTele(4,4,34,"mazeArea",5,4,0,"mazeArea");
            
            hitTele(5,34,30,"mazeArea",6,0,2,"mazeArea");
            hitTele(5,34,31,"mazeArea",6,0,3,"mazeArea");
            hitTele(5,34,32,"mazeArea",6,0,4,"mazeArea");
            
            hitTele(6,18,34,"mazeArea",1,16,0,"safeArea");
            hitTele(6,19,34,"mazeArea",1,17,0,"safeArea");
            hitTele(6,20,34,"mazeArea",1,18,0,"safeArea");
            
            hitTele(1,34,18,"safeArea",7,0,2,"mazeArea");
            hitTele(1,34,19,"safeArea",7,0,3,"mazeArea");
            hitTele(1,34,20,"safeArea",7,0,4,"mazeArea");
            
            hitTele(7,33,30,"mazeArea",8,0,30,"mazeArea");
            hitTele(7,33,31,"mazeArea",8,0,31,"mazeArea");
            hitTele(7,33,32,"mazeArea",8,0,32,"mazeArea");
            
            hitTele(8,2,0,"mazeArea",9,2,34,"mazeArea");
            hitTele(8,3,0,"mazeArea",9,3,34,"mazeArea");
            hitTele(8,4,0,"mazeArea",9,4,34,"mazeArea");
            
            hitTele(9,30,0,"mazeArea",2,17,34,"safeArea");
            hitTele(9,31,0,"mazeArea",2,18,34,"safeArea");
            hitTele(9,32,0,"mazeArea",2,19,34,"safeArea");
            
            hitTele(2,34,7,"safeArea",10,0,2,"mazeArea");
            hitTele(2,34,8,"safeArea",10,0,3,"mazeArea");
            hitTele(2,34,9,"safeArea",10,0,4,"mazeArea");
            
            hitTele(10,34,18,"mazeArea",11,0,30,"mazeArea");
            hitTele(10,34,19,"mazeArea",11,0,31,"mazeArea");
            hitTele(10,34,20,"mazeArea",11,0,32,"mazeArea");
            
            hitTele(11,6,34,"mazeArea",12,14,0,"mazeArea");
            hitTele(11,7,34,"mazeArea",12,15,0,"mazeArea");
            hitTele(11,8,34,"mazeArea",12,16,0,"mazeArea");
            
            hitTele(12,30,34,"mazeArea",3,1,0,"safeArea");
            hitTele(12,31,34,"mazeArea",3,2,0,"safeArea");
            hitTele(12,32,34,"mazeArea",3,1,0,"safeArea");
            
            if (hit(3, 16, 0, "any")) {
            	ending() ;
            }
            if (hit(3, 17, 0, "any")) {
            	ending() ;
            }
             
        }
    }
    private void hitTele(int areaMap, int areaRow,int areaCol,String area1, int areaMap2, int areaRow2, int areaCol2, String area2) {
        
    	// Go to next
    	if (hit(areaMap, areaRow, areaCol, "any")) { 
    		if(area2.equals("safeArea")) teleport(areaMap2, areaRow2, areaCol2, gp.safeArea);
    		if(area2.equals("mazeArea")) teleport(areaMap2, areaRow2, areaCol2, gp.mazeArea);
    	}
    	
    	// Go back
    	if (hit(areaMap2, areaRow2, areaCol2, "any")) { 
    		if(area1.equals("safeArea")) teleport(areaMap, areaRow, areaCol, gp.safeArea); 
    		if(area1.equals("mazeArea")) teleport(areaMap, areaRow, areaCol, gp.mazeArea); 
    	} 
    }

    private boolean hit(int map, int col, int row, String reqDirection) {
        boolean hit = false;

        if (map == gp.currentMap) {
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
            eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gp.tileSize + eventRect[map][col][row].y;

            if (gp.player.solidArea.intersects(eventRect[map][col][row])
                    && eventRect[map][col][row].eventDone == false) {
                if (gp.player.direction.contentEquals(reqDirection) || reqDirection.equals("any")) {
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
        canTouchEvent = false;
    }

    public void healing(int gameState) {
        if (gp.keyH.enterPressed == true) {
            gp.gameState = gameState;
            gp.player.attackCanceled = true;
            gp.playSE(3);
            if (gp.player.life < gp.player.maxLife || gp.player.mana < gp.player.maxMana) {
                eventMaster.startDialogue(eventMaster, 1);
                gp.player.life = gp.player.maxLife;
                gp.player.mana += 1;
                gp.saveLoad.save();
            } else {
                eventMaster.startDialogue(eventMaster, 2);
            }
        }
    }

    private void teleport(int map, int col, int row, int area) {
        gp.gameState = gp.transitionState;
        gp.nextArea = area;
        tempMap = map;
        tempCol = col;
        tempRow = row;
        canTouchEvent = false;
        gp.playSE(14);
    }

    private void ending() {
        if (Progress.endingSceneDone == false) {
            gp.gameState = gp.cutsceneState;
            gp.csManager.sceneNum = gp.csManager.ending;
        }
    }
}