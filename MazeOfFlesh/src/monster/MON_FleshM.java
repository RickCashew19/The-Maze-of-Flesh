package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Gold;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

public class MON_FleshM extends Entity{
	
	GamePanel gp;

	public MON_FleshM(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_monster;
		name = "Flesh Monster";
		defaultSpeed = 1;
		speed = defaultSpeed;
		maxLife = 6;
		life = maxLife;
		attack = 3;
		defense = 0;
		exp = 2;
		
		solidArea.x = 16;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 32;
		solidArea.height = 48;
		attackArea.width = 32;
		attackArea.height = 32;
		motion1_duration = 20;
		motion2_duration = 40;
		
		getImage();
		getAttackImage();
		
	}
	
	public void getImage() {
		
		
		up1 = setup("/monster/fleshwalk_up_1",gp.tileSize,gp.tileSize);
		up2 = setup("/monster/fleshwalk_up_2",gp.tileSize,gp.tileSize);
		down1 = setup("/monster/fleshwalk_down_1",gp.tileSize,gp.tileSize);
		down2 = setup("/monster/fleshwalk_down_2",gp.tileSize,gp.tileSize);
		left1 = setup("/monster/fleshwalk_left_1",gp.tileSize,gp.tileSize);
		left2 = setup("/monster/fleshwalk_left_2",gp.tileSize,gp.tileSize);
		right1 = setup("/monster/fleshwalk_right_1",gp.tileSize,gp.tileSize);
		right2 = setup("/monster/fleshwalk_right_2",gp.tileSize,gp.tileSize);
	}
	
	public void getAttackImage() {
		
		attackUp1 = setup("/monster/flesh_attack_0",gp.tileSize,gp.tileSize);
		attackUp2 = setup("/monster/flesh_attack-up_1",gp.tileSize,gp.tileSize);
		attackDown1 = setup("/monster/flesh_attack_0",gp.tileSize,gp.tileSize);
		attackDown2 = setup("/monster/flesh_attack_down_1",gp.tileSize,gp.tileSize);
		attackLeft1 = setup("/monster/flesh_attack_0",gp.tileSize,gp.tileSize);
		attackLeft2 = setup("/monster/flesh_attack_left_1",gp.tileSize,gp.tileSize);
		attackRight1 = setup("/monster/flesh_attack_0",gp.tileSize,gp.tileSize);
		attackRight2 = setup("/monster/flesh_attack_right_1",gp.tileSize,gp.tileSize);
	}

	public void setAction() {
		
		if(onPath == true) {
			
			// Check if it stops chasing
			checkStopChasingOrNot(gp.player,5,100);

			// Search direction to go
			searchPath(getGoalCol(gp.player),getGoalRow(gp.player));
			
		}
		else {
			// Check if it starts chasing
			checkStartChasingOrNot(gp.player, 5, 100);
			
			// Get a random direction
			getRandomDirection();
		}
		
		// Check if it attacks
		if(attacking == false) {
			checkAttackOrNot(30, gp.tileSize+15,gp.tileSize);
		}
	}
	
	public void damageReaction() {
		
		actionLockCounter = 0;
		onPath = true;
		
	}
	
	public void checkDrop() {
		
		// CHANCES
		int i = new Random().nextInt(100)+1;
		
		// SET MONSTER DROP
		if(i < 50) {
			dropItem(new OBJ_Coin_Gold(gp));
		}
		if(i >= 50 && i < 75) {
			dropItem(new OBJ_Heart(gp));
		}
		if(i >= 75 && i < 100) {
			dropItem(new OBJ_ManaCrystal(gp));
		}
	}

}
