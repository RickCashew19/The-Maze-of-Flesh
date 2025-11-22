package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shards extends Entity{

	GamePanel gp;
	public static final String objName = "Shard";
	
	public OBJ_Shards(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_consumable;
		name = objName;
		down1 = setup("/objects/shard",gp.tileSize,gp.tileSize);
		price = 150;
		description = "[" + name + "]\nWTF IS THIS THING??.";
		
		setDialogue();
	}
	
	public void setDialogue() {
		
		dialogues[0][0] = "You place the " + name + " in the alter.";
		dialogues[1][0] = "I wonder what does this do?";
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
