package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class MON_Allknowing extends Entity {

	int soundCounter;
	
	GamePanel gp;

	public static final String monName = "Allknowing";

	public MON_Allknowing(GamePanel gp) {
		super(gp);
		this.gp = gp;

		type = type_monster;
		name = monName;
		defaultSpeed = 5;
		speed = defaultSpeed;
		maxLife = 6;
		life = maxLife;
		attack = 2;
		defense = 0;
		exp = 2;
		knockBackPower = 3;

		solidArea.x = 16;
		solidArea.y = 36;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 64+32;
		solidArea.height = 64;
		attackArea.width = 32;
		attackArea.height = 32;
		motion1_duration = 20;
		motion2_duration = 40;

		getImage();
		getAttackImage();

	}

	private void getImage() {

		up1 = setup("/monster/allknow01", gp.tileSize*2, gp.tileSize*2);
		up2 = setup("/monster/allknow03", gp.tileSize*2, gp.tileSize*2);
		down1 = setup("/monster/allknow01", gp.tileSize*2, gp.tileSize*2);
		down2 = setup("/monster/allknow03", gp.tileSize*2, gp.tileSize*2);
		left1 = setup("/monster/allknow01", gp.tileSize*2, gp.tileSize*2);
		left2 = setup("/monster/allknow02", gp.tileSize*2, gp.tileSize*2);
		right1 = setup("/monster/allknow01", gp.tileSize*2, gp.tileSize*2);
		right2 = setup("/monster/allknow02", gp.tileSize*2, gp.tileSize*2);
	}

	private void getAttackImage() {

		attackUp1 = setup("/monster/allknow01", gp.tileSize, gp.tileSize*2);
		attackUp2 = setup("/monster/allknow02", gp.tileSize, gp.tileSize*2);
		attackDown1 = setup("/monster/allknow01", gp.tileSize, gp.tileSize*2);
		attackDown2 = setup("/monster/allknow02", gp.tileSize, gp.tileSize*2);
		attackLeft1 = setup("/monster/allknow01", gp.tileSize*2, gp.tileSize);
		attackLeft2 = setup("/monster/allknow02", gp.tileSize*2, gp.tileSize);
		attackRight1 = setup("/monster/allknow01", gp.tileSize*2, gp.tileSize);
		attackRight2 = setup("/monster/allknow02", gp.tileSize*2, gp.tileSize);
	}

	public void setAction() {

		if (onPath == true) {

		    detectedPlayer = true;

			canMove = true;

			// Check if it stops chasing
			checkStopChasingOrNot(gp.player, 100, 100);
			
			// Search direction to go
			searchPath(getGoalCol(gp.player), getGoalRow(gp.player),canMove);	
			
			soundCounter++;
		} else {
			detectedPlayer = false;
			// Check if it starts chasing
			checkStartChasingOrNot(gp.player, 100, 100);

			actionLockCounter++;

			if (actionLockCounter == 120) {
				Random random = new Random();
				int i = random.nextInt(100) + 1; // pick up a number from 1 to 100

				if (i <= 25) {
					direction = "up";
				}
				if (i > 25 && i <= 50) {
					direction = "down";
				}
				if (i > 50 && i <= 75) {
					direction = "left";
				}
				if (i > 75 && i <= 100) {
					direction = "right";
				}
				actionLockCounter = 0;
			}
			// Get a random direction
			//getRandomDirection();
		}
		
		// Check if it attacks
//		if (attacking == false) {
//			checkAttackOrNot(30, gp.tileSize * 2, gp.tileSize);
//		}

	}

	public void damageReaction() {

		actionLockCounter = 0;
		onPath = true;

	}

	public void checkDrop() {

	}

}
