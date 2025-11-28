package main;

import entity.NPC_1;
import entity.NPC_Girl;
import monster.MON_BlueSlime;
import monster.MON_Creekling;
import monster.MON_FleshM;
import monster.MON_Orc;
import object.OBJ_Axe_Normal;
import object.OBJ_Chest;
import object.OBJ_Coin_Gold;
import object.OBJ_Death;
import object.OBJ_Door;
import object.OBJ_FLESH_WALL;
import object.OBJ_Health_Potion;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_Lantern;
import object.OBJ_ManaCrystal;
import object.OBJ_Shards;
import object.OBJ_Shield_Iron;
import tile_interactive.IT_DryTree;

public class AssetSetter {

	GamePanel gp;

	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}

	public void setObject() {
		int mapNum = 1;
		int i = 0;
//		gp.obj[mapNum][i] = new OBJ_Chest(gp);
//		gp.obj[mapNum][i].setLoot(new OBJ_Key(gp));
//		gp.obj[mapNum][i].worldX = gp.tileSize*15;
//		gp.obj[mapNum][i].worldY = gp.tileSize*8;
//		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 31;
		gp.obj[mapNum][i].worldY = gp.tileSize * 16;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 31;
		gp.obj[mapNum][i].worldY = gp.tileSize * 17;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Shards(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 26;
		gp.obj[mapNum][i].worldY = gp.tileSize * 14;
		i++;

		gp.obj[mapNum][i] = new OBJ_Shards(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 27;
		gp.obj[mapNum][i].worldY = gp.tileSize * 14;
		i++;

		gp.obj[mapNum][i] = new OBJ_Shards(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 28;
		gp.obj[mapNum][i].worldY = gp.tileSize * 14;
		i++;

		gp.obj[mapNum][i] = new OBJ_Shards(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 29;
		gp.obj[mapNum][i].worldY = gp.tileSize * 14;
		i++;

		gp.obj[mapNum][i] = new OBJ_Shards(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 30;
		gp.obj[mapNum][i].worldY = gp.tileSize * 14;
		i++;

	}

	public void setNPC() {
		int mapNum = 0;
		int i = 0;

		gp.npc[mapNum][i] = new NPC_Girl(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize * 15;
		gp.npc[mapNum][i].worldY = gp.tileSize * 15;
		i++;

	}

	public void setMonster() {
		int mapNum = 1;
		int i = 0;
		
		gp.monster[mapNum][i] = new MON_FleshM(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*14;
		gp.monster[mapNum][i].worldY = gp.tileSize*16;
		i++;
		
		gp.monster[mapNum][i] = new MON_Creekling(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*23;
		gp.monster[mapNum][i].worldY = gp.tileSize*10;
		i++;

	}

	public void setInteractiveTile() {
		int mapNum = 0;
		int i = 0;

		gp.iTile[mapNum][i] = new IT_DryTree(gp, 18, 40);
		i++;
	}
}
