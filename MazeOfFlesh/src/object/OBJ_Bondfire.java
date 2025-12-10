package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Bondfire extends Entity {

	GamePanel gp;
	public static final String objName = "Bondfire";

	public OBJ_Bondfire(GamePanel gp) {
		super(gp);
		this.gp = gp;

		type = type_obstacle;
		name = objName;
		down1 = image;
		collision = true;

		solidArea.x = 0;
		solidArea.y = 32;
		solidArea.width = 64;
		solidArea.height = 32;

		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		getImage();
	}
	
	public void getImage() {
		up1 = setup("/objects/bondfire1", gp.tileSize, gp.tileSize);
		up2 = setup("/objects/bondfire2", gp.tileSize, gp.tileSize);
		left1 = setup("/objects/bondfire2", gp.tileSize, gp.tileSize);
		left2 = setup("/objects/bondfire1", gp.tileSize, gp.tileSize);
		down1 = setup("/objects/bondfire3", gp.tileSize, gp.tileSize);
		down2 = setup("/objects/bondfire4", gp.tileSize, gp.tileSize);
		right1 = setup("/objects/bondfire3", gp.tileSize, gp.tileSize);
		right2 = setup("/objects/bondfire4", gp.tileSize, gp.tileSize);

	}

	private void setDialogue() {
		dialogues[0][0] = "Bro open a chest the found some " + loot.name
				+ "!\n...Bruhh, WHAHAHAH Full inventory!!\nToo badd for YOU!!!";
		dialogues[1][0] = "Bro open a chest the found some " + loot.name + "!\n\nDamn Bro gain some " + loot.name
				+ " stuff!";
		dialogues[2][0] = "WTF bro what are you looking at \nthe empty chest???\nIs There something inside???";
	}

	public void interact() {

		gp.eHandler.healing(gp.playState);
	}
}
