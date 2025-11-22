package entity;

import java.util.Random;

import main.GamePanel;
import object.OBJ_Axe_Normal;
import object.OBJ_Health_Potion;
import object.OBJ_Key;
import object.OBJ_Rock;
import object.OBJ_Shield_Iron;
import object.OBJ_Sword_Normal;

public class NPC_Girl extends Entity{
	
	public NPC_Girl(GamePanel gp) {
		super(gp);
		
		direction = "down";
		speed = 1;
		
		solidArea.x = 14;
		solidArea.y = 28;
		solidArea.width = 32;
		solidArea.height = 32;
		
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		dialogueSet = -1;
		
		getImage();
		setDialogue();
		setItems();
	}
	
	public void setDialogue() {
		
		dialogues[0][0] = "Who are you???"; 
		dialogues[0][1] = "Why Am I Here???"; 
		dialogues[0][2] = "Why did the world stop everytime \nyou talk to me??"; 
		dialogues[0][3] = "hey!"; 
		dialogues[0][4] = "Are you listining??"; 
		
		dialogues[1][0] = "Who are you???"; 
		dialogues[1][1] = "Why Am I Here???"; 
		dialogues[1][2] = "Why did the world stop everytime \nyou talk to me??"; 
		dialogues[1][3] = "hey!"; 
		dialogues[1][4] = "Are you listining??"; 
		
		dialogues[2][0] = "Who are you???"; 
		dialogues[2][1] = "Why Am I Here???"; 
		dialogues[2][2] = "Wait."; 
		dialogues[2][3] = "Am I Looping???"; 
		dialogues[2][4] = "Hey WAIT!!!!"; 
		
		// For trades
//		dialogues[3][0] = "Did you steal something From me?"; 
//		
//		dialogues[4][0] = "What? \nYou can't buy that!!";
//		dialogues[5][0] = "Really?? \nYou can still carry more??";
//		
//		dialogues[6][0] = "You cant sell that to mee (T^T)";
	}
	
	public void getImage() {
		
		up00 = setup("/npc/pixilB0",gp.tileSize,gp.tileSize);
		down00 = setup("/npc/pixilF0",gp.tileSize,gp.tileSize);
		left00 = setup("/npc/pixilF0",gp.tileSize,gp.tileSize);
		right00 = setup("/npc/pixilF0",gp.tileSize,gp.tileSize);
		
		// -- MOVEMENT --
		up1 = setup("/npc/pixilB1",gp.tileSize,gp.tileSize);
		up2 = setup("/npc/pixilB2",gp.tileSize,gp.tileSize);
		
		down1 = setup("/npc/pixilF1",gp.tileSize,gp.tileSize);
		down2 = setup("/npc/pixilF2",gp.tileSize,gp.tileSize);
		
		
		// Default
		left1 = setup("/npc/pixilF0",gp.tileSize,gp.tileSize);
		left2 = setup("/npc/pixilF0",gp.tileSize,gp.tileSize);
		
		right1 = setup("/npc/pixilF0",gp.tileSize,gp.tileSize);
		right2 = setup("/npc/pixilF0",gp.tileSize,gp.tileSize);
		
	}
	
	public void setItems() {
		
		inventory.add(new OBJ_Health_Potion(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Rock(gp));
		inventory.add(new OBJ_Sword_Normal(gp));
		inventory.add(new OBJ_Axe_Normal(gp));
		inventory.add(new OBJ_Shield_Iron(gp));
	}
	
	public void setAction() {
		
		actionLockCounter++;
		
		if(actionLockCounter == 120) {
			Random random = new Random();
			int i = random.nextInt(100) + 1; // pick up a number from 1 to 100
			
			if(i <= 50) {
				direction = "up";
			}
			if(i > 50) {
				direction = "down";
			}
			
			actionLockCounter = 0;
		}
	}
	
	public void speak() {
		
		// TO DO A CHARACTER UNIQUE STUFF
		facePlayer();
		startDialogue(this, dialogueSet);
		
		// TO INCREASE THE SET OF DIALOGUE
		dialogueSet++;
		if(dialogues[dialogueSet][0] == null) {
			dialogueSet = 0; // if you want to go back to first set
			
			//dialogueSet--; // if you want to repeat the last set
		}
		
		// AND YOU CAN SET ANYCONDITIONS LIKE IF PLAYERS HEALTH IS LOW OR SOMTHING
		// TRADE MODE
//		gp.gameState = gp.tradeState;
//		gp.ui.npc = this;
	}
	
}
