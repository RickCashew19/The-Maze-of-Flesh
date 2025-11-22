package entity;

import java.util.Random;

import main.GamePanel;

public class NPC_1 extends Entity{
	
	public NPC_1(GamePanel gp) {
		super(gp);
		
		direction = "down";
		speed = 1;
		
		solidArea.x = 14;
		solidArea.y = 28;
		
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		solidArea.width = 38;
		solidArea.height = 38;
		
		getImage();
		setDialogue();
	}
	
	public void setDialogue() {
		dialogues[0][0] = "..."; 
	}
	
	public void getImage() {
		
		// -- IDLE -- //Stay
		up00 = setup("/player/frontU",gp.tileSize,gp.tileSize);
		down00 = setup("/player/frontD",gp.tileSize,gp.tileSize);
		left00 = setup("/player/frontL",gp.tileSize,gp.tileSize);
		right00 = setup("/player/frontR",gp.tileSize,gp.tileSize);
		
		
		// -- MOVEMENT --
		up1 = setup("/player/up1",gp.tileSize,gp.tileSize);
		up2 = setup("/player/up2",gp.tileSize,gp.tileSize);
		
		down1 = setup("/player/down1",gp.tileSize,gp.tileSize);
		down2 = setup("/player/down2",gp.tileSize,gp.tileSize);
		
		left1 = setup("/player/sideL1",gp.tileSize,gp.tileSize);
		left2 = setup("/player/sideL2",gp.tileSize,gp.tileSize);
		
		right1 = setup("/player/sideR1",gp.tileSize,gp.tileSize);
		right2 = setup("/player/sideR2",gp.tileSize,gp.tileSize);
		
	}
	
	public void setAction() {
		
		if(onPath == true) {
			
			int goalCol = 12;
			int goalRow = 9;
			
//			int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
//			int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;
			
			searchPath(goalCol,goalRow);
			System.out.println("Hey!!");
		}
		else {
			actionLockCounter++;
			
			if(actionLockCounter == 120) {
				Random random = new Random();
				int i = random.nextInt(100) + 1; // pick up a number from 1 to 100
				
				if(i <= 25) {
					direction = "up";
				}
				if(i > 25 && i <= 50) {
					direction = "down";
				}
				if(i > 50 && i <= 75) {
					direction = "left";
				}
				if(i > 75 && i <= 100) {
					direction = "right";
				}
				actionLockCounter = 0;
			}
		}
	}
	
	public void speak() {
		
		// TO DO A CHARACTER UNIQUE STUFF
		facePlayer();
		startDialogue(this, dialogueSet);
	}
	
}
