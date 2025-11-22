package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe_Normal extends Entity{

	public static final String objName = "Normal Axe";
	
	public OBJ_Axe_Normal(GamePanel gp) {
		super(gp);

		type = type_axe;
		name = objName;
		down1 = setup("/objects/axe",gp.tileSize,gp.tileSize);
		
		attackValue = 2;
		knockBackPower = 4;
		description = "[" + name + "]\nWhat a Rusty axe.";
		price = 100;
		attackArea.width = 64;
		attackArea.height = 64;
		motion1_duration = 20;
		motion2_duration = 40;
	}

}
