package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class MON_WEEPING_ANGEL extends Entity {

	int soundCounter;
	
	GamePanel gp;

	public static final String monName = "Angle";

	public MON_WEEPING_ANGEL(GamePanel gp) {
		super(gp);
		this.gp = gp;

		type = type_monster;
		name = monName;
		defaultSpeed = 6;
		speed = defaultSpeed;
		maxLife = 6;
		life = maxLife;
		attack = 2;
		defense = 0;
		exp = 2;

		solidArea.x = 8;
		solidArea.y = 0;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 48;
		solidArea.height = 48;
		attackArea.width = 32;
		attackArea.height = 32;
		motion1_duration = 20;
		motion2_duration = 40;

		getImage();
		getAttackImage();

	}

	private void getImage() {

		up1 = setup("/monster/weepingangel", gp.tileSize, (int)(gp.tileSize));
		up2 = setup("/monster/weepingangel", gp.tileSize,(int)(gp.tileSize));
		down1 = setup("/monster/weepingangel", gp.tileSize, (int)(gp.tileSize));
		down2 = setup("/monster/weepingangel", gp.tileSize, (int)(gp.tileSize));
		left1 = setup("/monster/weepingangel", gp.tileSize, (int)(gp.tileSize));
		left2 = setup("/monster/weepingangel", gp.tileSize, (int)(gp.tileSize));
		right1 = setup("/monster/weepingangel", gp.tileSize,(int)(gp.tileSize));
		right2 = setup("/monster/weepingangel", gp.tileSize, (int)(gp.tileSize));
	}

	private void getAttackImage() {

		attackUp1 = setup("/monster/weepingangel", gp.tileSize, gp.tileSize*2);
		attackUp2 = setup("/monster/weepingangel", gp.tileSize, gp.tileSize*2);
		attackDown1 = setup("/monster/weepingangel", gp.tileSize, gp.tileSize*2);
		attackDown2 = setup("/monster/weepingangel", gp.tileSize, gp.tileSize*2);
		attackLeft1 = setup("/monster/weepingangel", gp.tileSize*2, gp.tileSize);
		attackLeft2 = setup("/monster/weepingangel", gp.tileSize*2, gp.tileSize);
		attackRight1 = setup("/monster/weepingangel", gp.tileSize*2, gp.tileSize);
		attackRight2 = setup("/monster/weepingangel", gp.tileSize*2, gp.tileSize);
	}

	public void setAction() {

		if (onPath == true) {

		    detectedPlayer = true;
			
			// When player move this MF move too
			if (!gp.player.isLookingAt(this)) {
				canMove = true;
				speed = defaultSpeed;
			} else {
				speed = 0;
				canMove = false;
			}
			
			// Check if it stops chasing
			checkStopChasingOrNot(gp.player, 100, 100);
			
			// Search direction to go
			searchPath(getGoalCol(gp.player), getGoalRow(gp.player),canMove);	
			
			soundCounter++;
		} else {
			detectedPlayer = false;
			// Check if it starts chasing
			checkStartChasingOrNot(gp.player, 5, 100);

			// Get a random direction
			//getRandomDirection();
		}
		
	}

	public void damageReaction() {

		actionLockCounter = 0;
		onPath = true;

	}

	public void checkDrop() {

	}

}
