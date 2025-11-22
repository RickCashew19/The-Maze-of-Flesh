package tile_interactive;

import java.awt.Color;

import entity.Entity;
import main.GamePanel;

public class IT_DryTree extends InteractiveTile{

	public IT_DryTree(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.gp = gp;
		
		this.worldX = gp.tileSize*col;
		this.worldY = gp.tileSize*row;
		
		life = 5;
		
		down1 = setup("/tile_interactive/drytree",gp.tileSize, gp.tileSize);
		destructible = true;
	}
	
	public boolean isCorrectItem(Entity entity) {
		boolean isCorrectItem = false;
		
		if(entity.currentWeapon.type == type_axe) {
			isCorrectItem = true;
		}
		
		return isCorrectItem;
	}
	
	public void playSE() {
		gp.playSE(13);
	}
	
	public InteractiveTile getDestroyedForm() {
		InteractiveTile tile = new IT_Trunk(gp, worldX/gp.tileSize, worldY/gp.tileSize);
		return tile;
	}
	
	public Color getParticleColor() {
		Color color = new Color(63,50,30);
		return color;
	}
	
	public int getParticleSize() {
		int size = 8; // This is PIXELS
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
