package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Iron extends Entity{

	public static final String objName = "Iron Shield";
	
	public OBJ_Shield_Iron(GamePanel gp) {
		super(gp);

		type = type_shield;
		name = objName;
		down1 = setup("/objects/shield_blue",gp.tileSize,gp.tileSize);
		
		defenseValue = 2;
		price = 100;
		description = "[" + name + "]\nAn old ass Rusted \nIron Shield.";
	}

}
