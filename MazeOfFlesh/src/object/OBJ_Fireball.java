package object;

import java.awt.Color;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_Fireball extends Projectile {

	GamePanel gp;
	public static final String objName = "FireBall";

	public OBJ_Fireball(GamePanel gp) {
		super(gp);
		this.gp = gp;

		name = objName;
		speed = 10;
		maxLife = 80;
		life = maxLife;
		attack = 100;
		knockBackPower = 20;
		useCost = 1;
		alive = false;
		getImage();
	}

	public void getImage() {

		up1 = setup("/projectile/fireball_up_1", gp.tileSize*10, gp.tileSize*10);
		up2 = setup("/projectile/fireball_up_2", gp.tileSize*10, gp.tileSize*10);
		down1 = setup("/projectile/fireball_down_1", gp.tileSize*10, gp.tileSize*10);
		down2 = setup("/projectile/fireball_down_2", gp.tileSize*10, gp.tileSize*10);
		left1 = setup("/projectile/fireball_left_1", gp.tileSize*10, gp.tileSize*10);
		left2 = setup("/projectile/fireball_left_2", gp.tileSize*10, gp.tileSize*10);
		right1 = setup("/projectile/fireball_right_1", gp.tileSize*10, gp.tileSize*10);
		right2 = setup("/projectile/fireball_right_2", gp.tileSize*10, gp.tileSize*10);
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
		Color color = new Color(240, 50, 0);
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
