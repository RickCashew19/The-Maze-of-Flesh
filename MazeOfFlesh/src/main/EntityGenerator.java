package main;

import entity.Entity;
import object.OBJ_Bondfire;
import object.OBJ_Fireball;
import object.OBJ_Health_Potion;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_Lantern;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;
import object.OBJ_Shards;
import object.OBJ_worm;

public class EntityGenerator {

	GamePanel gp;

	public EntityGenerator(GamePanel gp) {
		this.gp = gp;
	}

	public Entity getObject(String itemName) {

		Entity obj = null;

		switch (itemName) {
		case OBJ_Fireball.objName:
			obj = new OBJ_Fireball(gp);
			break;
		case OBJ_Health_Potion.objName:
			obj = new OBJ_Health_Potion(gp);
			break;
		case OBJ_Heart.objName:
			obj = new OBJ_Heart(gp);
			break;
		case OBJ_Key.objName:
			obj = new OBJ_Key(gp);
			break;
		case OBJ_Lantern.objName:
			obj = new OBJ_Lantern(gp);
			break;
		case OBJ_ManaCrystal.objName:
			obj = new OBJ_ManaCrystal(gp);
			break;
		case OBJ_Rock.objName:
			obj = new OBJ_Rock(gp);
			break;
		case OBJ_Shards.objName:
			obj = new OBJ_Shards(gp);
			break;
		case OBJ_Bondfire.objName:
			obj = new OBJ_Bondfire(gp);
			break;
		case OBJ_worm.objName:
			obj = new OBJ_worm(gp);
			break;
		}

		return obj;
	}
}
