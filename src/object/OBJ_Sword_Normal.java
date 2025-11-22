package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword_Normal extends Entity{

	public static final String objName = "Normal Sword";
	
	public OBJ_Sword_Normal(GamePanel gp) {
		super(gp);

		type = type_sword;
		name = objName;
		down1 = setup("/objects/sword_normal",gp.tileSize,gp.tileSize);
		
		attackValue = 1;
		knockBackPower = 2;
		price = 75;
		description = "[" + name + "]\nWhat a Rusty Sword.";
		attackArea.width = 64;
		attackArea.height = 64;
		motion1_duration = 5;
		motion2_duration = 25;
	}

}
