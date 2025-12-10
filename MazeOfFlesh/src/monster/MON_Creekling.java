package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Heart;

public class MON_Creekling extends Entity {

	int soundCounter;
	
	GamePanel gp;

	public static final String monName = "Creekling";

	public MON_Creekling(GamePanel gp) {
		super(gp);
		this.gp = gp;

		type = type_monster;
		name = monName;
		defaultSpeed = 4;
		speed = defaultSpeed;
		maxLife = 6;
		life = maxLife;
		attack = 2;
		defense = 0;
		exp = 2;

		solidArea.x = 46;
		solidArea.y = 46;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 46;
		solidArea.height = 46;
		attackArea.width = 32;
		attackArea.height = 32;
		motion1_duration = 20;
		motion2_duration = 40;

		getImage();
		//getAttackImage();

	}

	private void getImage() {

		up1 = setup("/monster/eyeball_right_1", gp.tileSize*2, gp.tileSize*2);
		up2 = setup("/monster/eyeball-left_1", gp.tileSize*2, gp.tileSize*2);
		down1 = setup("/monster/eyeball_right_1", gp.tileSize*2, gp.tileSize*2);
		down2 = setup("/monster/eyeball-left_1", gp.tileSize*2, gp.tileSize*2);
		left1 = setup("/monster/eyeball-left_1", gp.tileSize*2, gp.tileSize*2);
		left2 = setup("/monster/eyeball-left_1", gp.tileSize*2, gp.tileSize*2);
		right1 = setup("/monster/eyeball_right_1", gp.tileSize*2, gp.tileSize*2);
		right2 = setup("/monster/eyeball_right_1", gp.tileSize*2, gp.tileSize*2);
	}

	private void getAttackImage() {

		attackUp1 = setup("/monster/eyeball_right_1", gp.tileSize, gp.tileSize*2);
		attackUp2 = setup("/monster/eyeball-left_1", gp.tileSize, gp.tileSize*2);
		attackDown1 = setup("/monster/eyeball_right_1", gp.tileSize, gp.tileSize*2);
		attackDown2 = setup("/monster/eyeball-left_1", gp.tileSize, gp.tileSize*2);
		attackLeft1 = setup("/monster/eyeball-left_1", gp.tileSize*2, gp.tileSize);
		attackLeft2 = setup("/monster/eyeball-left_1", gp.tileSize*2, gp.tileSize);
		attackRight1 = setup("/monster/eyeball_right_1", gp.tileSize*2, gp.tileSize);
		attackRight2 = setup("/monster/eyeball_right_1", gp.tileSize*2, gp.tileSize);
	}

	public void setAction() {

		if (onPath == true) {

		    detectedPlayer = true;
			
			// When player move this MF move too
			if (gp.keyH.upPressed == true || gp.keyH.downPressed == true || gp.keyH.leftPressed == true
					|| gp.keyH.rightPressed == true) {
				canMove = true;
				speed = defaultSpeed;
			} else {
				speed = 0;
				canMove = false;
			}
			
			// Check if it stops chasing
			checkStopChasingOrNot(gp.player, 20, 100);
			
			// Search direction to go
			searchPath(getGoalCol(gp.player), getGoalRow(gp.player),canMove);	
			
			soundCounter++;
		} else {
			detectedPlayer = false;
			// Check if it starts chasing
			checkStartChasingOrNot(gp.player, 20, 100);

			actionLockCounter++;
		}
		

	}

	public void damageReaction() {

		actionLockCounter = 0;
		onPath = true;

	}

	public void checkDrop() {

	}

}
