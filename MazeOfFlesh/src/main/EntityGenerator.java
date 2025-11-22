package main;

import entity.Entity;
import object.OBJ_Axe_Normal;
import object.OBJ_Chest;
import object.OBJ_Coin_Gold;
import object.OBJ_Death;
import object.OBJ_Door;
import object.OBJ_Fireball;
import object.OBJ_Health_Potion;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_Lantern;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;
import object.OBJ_Shards;
import object.OBJ_Shield_Iron;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;

public class EntityGenerator {

	GamePanel gp;
	
	public EntityGenerator(GamePanel gp) {
		this.gp = gp;
	}
	public Entity getObject(String itemName) {
		
		Entity obj = null;
		
		switch(itemName) {
		case OBJ_Axe_Normal.objName: obj = new OBJ_Axe_Normal(gp); break;
		case OBJ_Chest.objName: obj = new OBJ_Chest(gp); break;
		case OBJ_Coin_Gold.objName: obj = new OBJ_Coin_Gold(gp); break;
		case OBJ_Death.objName: obj = new OBJ_Death(gp); break;
		case OBJ_Door.objName: obj = new OBJ_Door(gp); break;
		case OBJ_Fireball.objName: obj = new OBJ_Fireball(gp); break;
		case OBJ_Health_Potion.objName: obj = new OBJ_Health_Potion(gp); break;
		case OBJ_Heart.objName: obj = new OBJ_Heart(gp); break;
		case OBJ_Key.objName: obj = new OBJ_Key(gp); break;
		case OBJ_Lantern.objName: obj = new OBJ_Lantern(gp); break;
		case OBJ_ManaCrystal.objName: obj = new OBJ_ManaCrystal(gp); break;
		case OBJ_Rock.objName: obj = new OBJ_Rock(gp); break;
		case OBJ_Shards.objName: obj = new OBJ_Shards(gp); break;
		case OBJ_Shield_Iron.objName: obj = new OBJ_Shield_Iron(gp); break;
		case OBJ_Shield_Wood.objName: obj = new OBJ_Shield_Wood(gp); break;
		case OBJ_Sword_Normal.objName: obj = new OBJ_Sword_Normal(gp); break;
		}
		
		return obj;
	}
}
