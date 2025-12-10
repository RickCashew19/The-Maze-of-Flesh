package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_FLESH_WALL02 extends Entity {

	GamePanel gp;
	public static final String objName = "Flesh Wall";
	 public String wallDirection;

	public OBJ_FLESH_WALL02(GamePanel gp, String direction) {
		super(gp);
		this.gp = gp;
		this.wallDirection = direction;
		
		type = type_obstacle;
		name = objName;		
		collision = true;

		solidArea.x = 0;
		solidArea.y = 0;
		solidArea.width = gp.tileSize-10;
		solidArea.height = gp.tileSize-10;

		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		getImage(direction);
		setDialogue();
	}
	
    private void getImage(String direction) {
        if (direction.equals("vertical01")) {
            down1 = setup("/tiles/tile_111", gp.tileSize, gp.tileSize);
            
        } else if (direction.equals("vertical02")) {
            down1 = setup("/tiles/tile_112", gp.tileSize, gp.tileSize);
            
        } else if (direction.equals("vertical03")) {
            down1 = setup("/tiles/tile_113", gp.tileSize, gp.tileSize);
            
        } 
        
        else if (direction.equals("horizontal01")) {
            down1 = setup("/tiles/tile_084", gp.tileSize, gp.tileSize);
            
        } else if (direction.equals("horizontal02")) {
            down1 = setup("/tiles/tile_085", gp.tileSize, gp.tileSize);
            
        } else if (direction.equals("horizontal03")) {
            down1 = setup("/tiles/tile_086", gp.tileSize, gp.tileSize);
            
        } else if (direction.equals("horizontal04")) {
            down1 = setup("/tiles/tile_087", gp.tileSize, gp.tileSize);
            
        } else if (direction.equals("horizontal05")) {
            down1 = setup("/tiles/tile_088", gp.tileSize, gp.tileSize);
            
        } else if (direction.equals("horizontal06")) {
            down1 = setup("/tiles/tile_089", gp.tileSize, gp.tileSize);
        }
        
        down2 = down1; // Copy for animation if needed
    }

	public void setDialogue() {

		//dialogues[0][0] = "Bro where is your keys??";
	}

	public void interact() {

		//startDialogue(this, 0);
	}
}
