package entity;

import main.GamePanel;

public class PlayerDummy extends Entity {
	
	public static final String npcName = "Dummy";

	public PlayerDummy(GamePanel gp) {
		super(gp);
		
		name = npcName;
		getImage();
	}
	
	public void getImage() {
		
		// -- IDLE -- //Stay
		up00 = setup("/player/up00",gp.tileSize,gp.tileSize);
		down00 = setup("/player/down00",gp.tileSize,gp.tileSize);
		left00 = setup("/player/left00",gp.tileSize,gp.tileSize);
		right00 = setup("/player/right00",gp.tileSize,gp.tileSize);
		
		
		// -- MOVEMENT --
		up1 = setup("/player/up01",gp.tileSize,gp.tileSize);
		up2 = setup("/player/up02",gp.tileSize,gp.tileSize);
		
		down1 = setup("/player/down01",gp.tileSize,gp.tileSize);
		down2 = setup("/player/down02",gp.tileSize,gp.tileSize);
		
		left1 = setup("/player/left01",gp.tileSize,gp.tileSize);
		left2 = setup("/player/left02",gp.tileSize,gp.tileSize);
		
		right1 = setup("/player/right01",gp.tileSize,gp.tileSize);
		right2 = setup("/player/right02",gp.tileSize,gp.tileSize);
		
	}
	
}
