package monster;

import java.awt.Rectangle;
import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class MON_MON1 extends Entity {

	GamePanel gp;

	public static final String monName = "MON 1";

	int soundCounter = 0;
	
	public MON_MON1(GamePanel gp) {
		super(gp);
		this.gp = gp;

		type = type_monster;
		name = monName;
		defaultSpeed = 10;
		speed = defaultSpeed;
		maxLife = 6;
		life = maxLife;
		attack = 10;
		defense = 0;
		exp = 2;

		solidArea = new Rectangle();
		solidArea.x = 32;
		solidArea.y = 36;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 58;
		solidArea.height = 58;

		getImage();
	}

	private void getImage() {

		up1 = setup("/monster/mon1", gp.tileSize*2, gp.tileSize*2);
		up2 = setup("/monster/mon2", gp.tileSize*2, gp.tileSize*2);
		down1 = setup("/monster/mon1", gp.tileSize*2, gp.tileSize*2);
		down2 = setup("/monster/mon2", gp.tileSize*2, gp.tileSize*2);
		left1 = setup("/monster/mon1", gp.tileSize*2, gp.tileSize*2);
		left2 = setup("/monster/mon2", gp.tileSize*2, gp.tileSize*2);
		right1 = setup("/monster/mon1", gp.tileSize*2, gp.tileSize*2);
		right2 = setup("/monster/mon2", gp.tileSize*2, gp.tileSize*2);
	}

	public void setAction() {

		if (onPath == true) {

			// Check if it stops chasing
			checkStopChasingOrNot(gp.player, 1000, 100);
			
			if(soundCounter >= 300) {
				soundCounter = 0;
				gp.playSE(16);
			}
			

			// Search direction to go
			searchPath(getGoalCol(gp.player), getGoalRow(gp.player),canMove);
			soundCounter++;
		} else {
			// Check if it starts chasing
			checkStartChasingOrNot(gp.player, 1000, 100);

			// Get a random direction
			getRandomDirection();
		}
	}

	public void damageReaction() {

		actionLockCounter = 0;
		onPath = true;

	}

	public void checkDrop() {
		
	}

}
