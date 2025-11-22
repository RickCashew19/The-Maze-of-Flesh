package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Gold;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;

public class MON_BlueSlime extends Entity{
	
	GamePanel gp;

	public MON_BlueSlime(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_monster;
		name = "Blue Slime";
		defaultSpeed = 1;
		speed = defaultSpeed;
		maxLife = 6;
		life = maxLife;
		attack = 5;
		defense = 0;
		exp = 2;
		projectile = new OBJ_Rock(gp);
		
		solidArea.x = 14;
		solidArea.y = 28;
		solidArea.width = 32;
		solidArea.height = 32;
		
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		getImage();
	}
	
	public void getImage() {
		
		
		up1 = setup("/monster/slime1",gp.tileSize,gp.tileSize);
		up2 = setup("/monster/slime0",gp.tileSize,gp.tileSize);
		down1 = setup("/monster/slime1",gp.tileSize,gp.tileSize);
		down2 = setup("/monster/slime0",gp.tileSize,gp.tileSize);
		left1 = setup("/monster/slime1",gp.tileSize,gp.tileSize);
		left2 = setup("/monster/slime3",gp.tileSize,gp.tileSize);
		right1 = setup("/monster/slime1",gp.tileSize,gp.tileSize);
		right2 = setup("/monster/slime2",gp.tileSize,gp.tileSize);
	}

	public void setAction() {
		
		if(onPath == true) {
			
			// Check if it stops chasing
			checkStopChasingOrNot(gp.player,5,100);

			// Search direction to go
			searchPath(getGoalCol(gp.player),getGoalRow(gp.player));
			
			// Check if it shots a projectile 
			checkShotOrNot(200,30);
		}
		else {
			// Check if it starts chasing
			checkStartChasingOrNot(gp.player, 5, 100);
			
			// Get a random direction
			getRandomDirection();
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
