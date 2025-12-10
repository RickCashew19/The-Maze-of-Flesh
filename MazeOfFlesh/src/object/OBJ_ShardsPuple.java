package object;

import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class OBJ_ShardsPuple extends Entity {

    GamePanel gp;
    public static final String objName = "Shard";

    public OBJ_ShardsPuple(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_consumable;
        name = objName;
        stackable = true;
        price = 150;
        description = "[" + name + "]\nA Purple Shard.\nEngulfed with mysterious energy.";

        getImage();
        setDialogue();
    }
    
	private void getImage() {
		down1 = setup("/objects/PURPSHARD", gp.tileSize, gp.tileSize);
		down2 = setup("/objects/PURPSHARD1", gp.tileSize, gp.tileSize);
	}
	
	
	
	public void setIdle() {

	}
	

    public void setDialogue() {
        dialogues[0][0] = "What was that sound?.";
        dialogues[1][0] = "You need 5 shards to use this!";
        dialogues[2][0] = "I wonder what does this do?";
    }

    public boolean use(Entity entity) {
        
            startDialogue(this, 1);
            return false;
        
    }
    
}