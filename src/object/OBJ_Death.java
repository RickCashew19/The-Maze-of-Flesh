package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Death extends Entity{

	public static final String objName = "Death";
	
	public OBJ_Death(GamePanel gp) {
		super(gp);
		
		name = objName;
		down1 = setup("/objects/death",gp.tileSize,gp.tileSize);
		price = 999;
	}
}
