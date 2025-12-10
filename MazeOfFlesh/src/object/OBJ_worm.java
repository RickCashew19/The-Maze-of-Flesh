package object;

import java.awt.Color;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_worm extends Projectile {

	GamePanel gp;
	public static final String objName = "worm";

	public OBJ_worm(GamePanel gp) {
		super(gp);
		this.gp = gp;

		name = objName;
		speed = 7;
		solidArea.width = gp.tileSize*8;
		solidArea.height = gp.tileSize*2;
		maxLife = 320;
		life = maxLife;
		attack = 3;
		knockBackPower = 5;
		useCost = 1;
		alive = false;
		getImage();
	}

	public void getImage() {

		up1 = setup("/monster/rockworm01", gp.tileSize*8, gp.tileSize*2);
		up2 = setup("/monster/rockworm02", gp.tileSize*8, gp.tileSize*2);
		down1 = setup("/monster/rockworm01", gp.tileSize*8, gp.tileSize*2);
		down2 = setup("/monster/rockworm02", gp.tileSize*8, gp.tileSize*2);
		left1 = setup("/monster/rockworm04", gp.tileSize*8, gp.tileSize*2);
		left2 = setup("/monster/rockworm05", gp.tileSize*8, gp.tileSize*2);
		right1 = setup("/monster/rockworm01", gp.tileSize*8, gp.tileSize*2);
		right2 = setup("/monster/rockworm02", gp.tileSize*8, gp.tileSize*2);
	}

	public boolean haveResource(Entity user) {

		boolean haveResource = false;
		if (user.mana >= useCost) {
			haveResource = true;
		}
		return haveResource;
	}

	public void subtractResource(Entity user) {
		user.mana -= useCost;
	}

	public Color getParticleColor() {
		Color color = new Color(183, 65, 14);
		return color;
	}

	public int getParticleSize() {
		int size = 10;
		return size;
	}

	public int getParticleSpeed() {
		int speed = 1; // This is speed by pixels
		return speed;
	}

	public int getParticleMaxLife() {
		int maxLife = 20;
		return maxLife;
	}
}
