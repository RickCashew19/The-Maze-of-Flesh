package main;

import entity.NPC_1;
import entity.NPC_Girl;
import monster.MON_BlueSlime;
import monster.MON_FleshM;
import monster.MON_Orc;
import object.OBJ_Axe_Normal;
import object.OBJ_Chest;
import object.OBJ_Coin_Gold;
import object.OBJ_Death;
import object.OBJ_Door;
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
	
	public AssetSetter (GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
		int mapNum = 0;
		int i = 0;
		gp.obj[mapNum][i] = new OBJ_Chest(gp);
		gp.obj[mapNum][i].setLoot(new OBJ_Key(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize*15;
		gp.obj[mapNum][i].worldY = gp.tileSize*8;
		i++;
		gp.obj[mapNum][i] = new OBJ_Shards(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*11;
		gp.obj[mapNum][i].worldY = gp.tileSize*8;
		i++;
		gp.obj[mapNum][i] = new OBJ_Shards(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*12;
		gp.obj[mapNum][i].worldY = gp.tileSize*8;
		i++;
		gp.obj[mapNum][i] = new OBJ_Shards(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*13;
		gp.obj[mapNum][i].worldY = gp.tileSize*8;
		i++;

	}
	
	public void setNPC() {
		int mapNum = 0;
		int i = 0;
		
		gp.npc[mapNum][i] = new NPC_Girl(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize*10;
		gp.npc[mapNum][i].worldY = gp.tileSize*8;
		i++;

	}
	
	public void setMonster() {
		int mapNum = 0;
		int i = 0;
		
		gp.monster[mapNum][i] = new MON_FleshM(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*18;
		gp.monster[mapNum][i].worldY = gp.tileSize*20;
		i++;
		gp.monster[mapNum][i] = new MON_FleshM(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*16;
		gp.monster[mapNum][i].worldY = gp.tileSize*20;
		i++;
		gp.monster[mapNum][i] = new MON_FleshM(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*15;
		gp.monster[mapNum][i].worldY = gp.tileSize*20;
		i++;
		gp.monster[mapNum][i] = new MON_FleshM(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*14;
		gp.monster[mapNum][i].worldY = gp.tileSize*20;
		i++;

	}
	
	public void setInteractiveTile() {
		int mapNum = 0;
		int i = 0;
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp,18,40); i++;
	}
}
