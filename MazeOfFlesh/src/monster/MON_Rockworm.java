package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_worm;

public class MON_Rockworm extends Entity {

	GamePanel gp;
	public int shootDelay = 0;

	public static final String monName = "Roockworm";

	public MON_Rockworm(GamePanel gp, String dir) {
		super(gp);
		this.gp = gp;

		type = type_monster;
		name = monName;
		defaultSpeed = 0;
		speed = defaultSpeed;
		maxLife = 6;
		life = maxLife;
		this.direction = dir;
		attack = 5;
		defense = 0;
		exp = 2;
		direction = dir;
		projectile = new OBJ_worm(gp);

		solidArea.x = 14;
		solidArea.y = 28;
		solidArea.width = 32;
		solidArea.height = 32;

		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		getImage();
		setAction();
	}

	private void getImage() {

		up1 = setup("/tiles/tile_105", gp.tileSize, gp.tileSize);
		up2 = setup("/tiles/tile_105", gp.tileSize, gp.tileSize);
		down1 = setup("/tiles/tile_105", gp.tileSize, gp.tileSize);
		down2 = setup("/tiles/tile_105", gp.tileSize, gp.tileSize);
		left1 = setup("/tiles/tile_105", gp.tileSize, gp.tileSize);
		left2 = setup("/tiles/tile_105", gp.tileSize, gp.tileSize);
		right1 = setup("/tiles/tile_105", gp.tileSize, gp.tileSize);
		right2 = setup("/tiles/tile_105", gp.tileSize, gp.tileSize);
	}

	public void setAction() {


		int i = new Random().nextInt(120);
		if (i == 0 && projectile.alive == false && shotAvailableCounter == 30) {
			projectile.set(worldX, worldY, direction, true, this);
			
			gp.playSE(17);
			// CHECK VACINITY
			for (int ii = 0; ii < gp.projectile[1].length; ii++) {
				if (gp.projectile[gp.currentMap][ii] == null) {
					gp.projectile[gp.currentMap][ii] = projectile;
					break;
				}
			}
			shotAvailableCounter = 0;
		}
		
	}

	public void damageReaction() {

		actionLockCounter = 0;
		onPath = true;

	}

}
