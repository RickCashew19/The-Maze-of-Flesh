package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity{

	GamePanel gp;
	public static final String objName = "Key";
	
	public OBJ_Key(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_consumable;
		name = objName;
		down1 = setup("/objects/key",gp.tileSize,gp.tileSize);
		price = 150;
		description = "[" + name + "]\nWhat can this thing\nbe able to open?.";
		
		setDialogue();
	}
	
	public void setDialogue() {
		
		dialogues[0][0] = "Ohh got the " + name + " now it's open.";
		dialogues[1][0] = "Why are you holding the " + name + " like that??";
	}
	public boolean use(Entity entity) {
		
		int objIndex = getDetected(entity, gp.obj, "Door");
		
		if(objIndex != 999) {
			startDialogue(this, 0);
			gp.playSE(15);
			gp.obj[gp.currentMap][objIndex] = null;
			return true;
		}
		else {
			startDialogue(this, 1);
			return false;
		}
	}
}
