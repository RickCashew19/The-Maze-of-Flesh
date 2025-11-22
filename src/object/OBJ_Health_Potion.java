package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Health_Potion extends Entity{

	GamePanel gp;
	public static final String objName = "Red Potion";
	
	public OBJ_Health_Potion(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_consumable;
		name = objName;
		value = 4;
		stackable = true;
		price = 50;
		down1 = setup("/objects/potion_red",gp.tileSize,gp.tileSize);
		description = "[" + name + "]\nThis doens't smell good.";
		
		setDialogue();
	}
	
	public void setDialogue() {
		dialogues[0][0] = "Bruhh, Did you just drink it?\nDoes it taste good?\n\n[Life recoverd by " + value + "]";
	}
	
	public boolean use(Entity entity) {
		
		startDialogue(this,0);
		entity.life += value;
		gp.playSE(3);
		return true;
	}
}
