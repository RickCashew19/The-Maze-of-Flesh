package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Chest extends Entity{

	GamePanel gp;
	public static final String objName = "Chest";
	
	public OBJ_Chest(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_obstacle;
		name = objName;
		image = setup("/objects/chest",gp.tileSize,gp.tileSize);
		image2 = setup("/objects/chest_opened",gp.tileSize,gp.tileSize);
		down1 = image;
		collision = true;
		
		solidArea.x = 0;
		solidArea.y = 32;
		solidArea.width = 64;
		solidArea.height = 32;
		
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}
	
	public void setLoot(Entity loot) {
		
		this.loot = loot;
		setDialogue();
	}
	
	public void setDialogue() {
		dialogues[0][0] = "Bro open a chest the found some "+loot.name+"!\n...Bruhh, WHAHAHAH Full inventory!!\nToo badd for YOU!!!";
		dialogues[1][0] = "Bro open a chest the found some "+loot.name+"!\n\nDamn Bro gain some "+loot.name+" stuff!";
		dialogues[2][0] = "WTF bro what are you looking at \nthe empty chest???\nIs There something inside???";
	}
	public void interact() {
		
		if(opened == false) {
			gp.playSE(15);
			
			if(gp.player.canObtainItem(loot) == false) {
				startDialogue(this, 0);
			} 
			else {
				startDialogue(this, 1);
				down1 = image2;
				opened = true;
			}
		}
		else {
			startDialogue(this, 3);
		}
	}
}
