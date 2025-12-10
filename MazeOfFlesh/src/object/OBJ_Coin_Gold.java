package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Coin_Gold extends Entity {

	GamePanel gp;
	public static final String objName = "Coin";

	public OBJ_Coin_Gold(GamePanel gp) {
		super(gp);
		this.gp = gp;

		type = type_pickupOnly;
		name = objName;
		value = 10;
		down1 = setup("/objects/coin", gp.tileSize, gp.tileSize);
		description = "[" + name + "]\nWow!! Finally!!!\nSomething Shiny!!.";
	}

	public boolean use(Entity entity) {

		gp.playSE(1);
		gp.ui.addMessage("Coin +" + value);
		gp.player.coin += value;
		return true;
	}
}
