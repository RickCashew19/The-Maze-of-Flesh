package main;

import monster.MON_Allknowing;
import monster.MON_Creekling;
import monster.MON_MACHROMMAN;
import monster.MON_MON1;
import monster.MON_MUSHMUSH;
import monster.MON_Minicutie;
import monster.MON_Mossrock;
import monster.MON_Rockworm;
import monster.MON_WEEPING_ANGEL;
import object.OBJ_Bondfire;
import object.OBJ_FLESH_WALL;
import object.OBJ_FLESH_WALL02;
import object.OBJ_FLESH_WALL03;
import object.OBJ_Shards;
import object.OBJ_ShardsGreen;
import object.OBJ_ShardsPuple;

public class AssetSetter {

	GamePanel gp;

	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}

	public void setObject() {
		int mapNum = 0;
		int i = 0;
		gp.obj[mapNum][i] = new OBJ_Bondfire(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 15;
		gp.obj[mapNum][i].worldY = gp.tileSize * 16;
		i++;
		mapNum = 1;
		i = 0;
		gp.obj[mapNum][i] = new OBJ_Bondfire(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 17;
		gp.obj[mapNum][i].worldY = gp.tileSize * 19;
		i++;
		mapNum = 2;
		i = 0;
		gp.obj[mapNum][i] = new OBJ_Bondfire(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 18;
		gp.obj[mapNum][i].worldY = gp.tileSize * 18;
		i++;
//		gp.obj[mapNum][i] = new OBJ_Chest(gp);
//		gp.obj[mapNum][i].setLoot(new OBJ_Key(gp));
//		gp.obj[mapNum][i].worldX = gp.tileSize*15;
//		gp.obj[mapNum][i].worldY = gp.tileSize*8;
//		i++;
		
		// MAP maze01
		mapNum = 4;
		i = 0;
		// HORIZONTAL WALL (T^T)
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL(gp,"horizontal01");
		gp.obj[mapNum][i].worldX = gp.tileSize * 2;
		gp.obj[mapNum][i].worldY = gp.tileSize * 33;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL(gp,"horizontal02");
		gp.obj[mapNum][i].worldX = gp.tileSize * 3;
		gp.obj[mapNum][i].worldY = gp.tileSize * 33;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL(gp,"horizontal03");
		gp.obj[mapNum][i].worldX = gp.tileSize * 4;
		gp.obj[mapNum][i].worldY = gp.tileSize * 33;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL(gp,"horizontal04");
		gp.obj[mapNum][i].worldX = gp.tileSize * 2;
		gp.obj[mapNum][i].worldY = gp.tileSize * 34;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL(gp,"horizontal05");
		gp.obj[mapNum][i].worldX = gp.tileSize * 3;
		gp.obj[mapNum][i].worldY = gp.tileSize * 34;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL(gp,"horizontal06");
		gp.obj[mapNum][i].worldX = gp.tileSize * 4;
		gp.obj[mapNum][i].worldY = gp.tileSize * 34;
		i++;
		
		// SHARDS 3x
		gp.obj[mapNum][i] = new OBJ_Shards(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 7;
		gp.obj[mapNum][i].worldY = gp.tileSize * 7;
		i++;
		gp.obj[mapNum][i] = new OBJ_Shards(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 27;
		gp.obj[mapNum][i].worldY = gp.tileSize * 19;
		i++;
		gp.obj[mapNum][i] = new OBJ_Shards(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 3;
		gp.obj[mapNum][i].worldY = gp.tileSize * 27;
		i++;
		
		// MAP maze02
		mapNum = 5;
		i = 0;
		// VERTICAL WALL (T^T)
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL(gp,"vertical01");
		gp.obj[mapNum][i].worldX = gp.tileSize * 34;
		gp.obj[mapNum][i].worldY = gp.tileSize * 30;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL(gp,"vertical02");
		gp.obj[mapNum][i].worldX = gp.tileSize * 34;
		gp.obj[mapNum][i].worldY = gp.tileSize * 31;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL(gp,"vertical03");
		gp.obj[mapNum][i].worldX = gp.tileSize * 34;
		gp.obj[mapNum][i].worldY = gp.tileSize * 32;
		i++;

		
		// SHARDS 3x
		gp.obj[mapNum][i] = new OBJ_Shards(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 23;
		gp.obj[mapNum][i].worldY = gp.tileSize * 7;
		i++;
		gp.obj[mapNum][i] = new OBJ_Shards(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 23;
		gp.obj[mapNum][i].worldY = gp.tileSize * 31;
		i++;
		gp.obj[mapNum][i] = new OBJ_Shards(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 3;
		gp.obj[mapNum][i].worldY = gp.tileSize * 31;
		i++;
		
		// MAP maze03
		mapNum = 6;
		i = 0;
		// HORIZONTAL WALL (T^T)
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL(gp,"horizontal01");
		gp.obj[mapNum][i].worldX = gp.tileSize * 18;
		gp.obj[mapNum][i].worldY = gp.tileSize * 33;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL(gp,"horizontal02");
		gp.obj[mapNum][i].worldX = gp.tileSize * 19;
		gp.obj[mapNum][i].worldY = gp.tileSize * 33;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL(gp,"horizontal03");
		gp.obj[mapNum][i].worldX = gp.tileSize * 20;
		gp.obj[mapNum][i].worldY = gp.tileSize * 33;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL(gp,"horizontal04");
		gp.obj[mapNum][i].worldX = gp.tileSize * 18;
		gp.obj[mapNum][i].worldY = gp.tileSize * 34;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL(gp,"horizontal05");
		gp.obj[mapNum][i].worldX = gp.tileSize * 19;
		gp.obj[mapNum][i].worldY = gp.tileSize * 34;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL(gp,"horizontal06");
		gp.obj[mapNum][i].worldX = gp.tileSize * 20;
		gp.obj[mapNum][i].worldY = gp.tileSize * 34;
		i++;

		// SHARDS 3x
		gp.obj[mapNum][i] = new OBJ_Shards(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 23;
		gp.obj[mapNum][i].worldY = gp.tileSize * 7;
		i++;
		gp.obj[mapNum][i] = new OBJ_Shards(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 3;
		gp.obj[mapNum][i].worldY = gp.tileSize * 27;
		i++;
		gp.obj[mapNum][i] = new OBJ_Shards(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 27;
		gp.obj[mapNum][i].worldY = gp.tileSize * 31;
		i++;
		
		// MAP maze04
		mapNum = 7;
		i = 0;
		// VIRTICAL WALL (T^T)
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL02(gp,"vertical01");
		gp.obj[mapNum][i].worldX = gp.tileSize * 33;
		gp.obj[mapNum][i].worldY = gp.tileSize * 30;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL02(gp,"vertical02");
		gp.obj[mapNum][i].worldX = gp.tileSize * 33;
		gp.obj[mapNum][i].worldY = gp.tileSize * 31;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL02(gp,"vertical03");
		gp.obj[mapNum][i].worldX = gp.tileSize * 33;
		gp.obj[mapNum][i].worldY = gp.tileSize * 32;
		i++;

		// SHARDS 3x
		gp.obj[mapNum][i] = new OBJ_ShardsGreen(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 31;
		gp.obj[mapNum][i].worldY = gp.tileSize * 4;
		i++;
		gp.obj[mapNum][i] = new OBJ_ShardsGreen(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 23;
		gp.obj[mapNum][i].worldY = gp.tileSize * 18;
		i++;
		gp.obj[mapNum][i] = new OBJ_ShardsGreen(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 11;
		gp.obj[mapNum][i].worldY = gp.tileSize * 23;
		i++;
		
		// MAP maze05
		mapNum = 8;
		i = 0;
		// HORIZONTAL WALL (T^T)
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL02(gp,"horizontal01");
		gp.obj[mapNum][i].worldX = gp.tileSize * 2;
		gp.obj[mapNum][i].worldY = gp.tileSize * 0;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL02(gp,"horizontal02");
		gp.obj[mapNum][i].worldX = gp.tileSize * 3;
		gp.obj[mapNum][i].worldY = gp.tileSize * 0;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL02(gp,"horizontal03");
		gp.obj[mapNum][i].worldX = gp.tileSize * 4;
		gp.obj[mapNum][i].worldY = gp.tileSize * 0;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL02(gp,"horizontal04");
		gp.obj[mapNum][i].worldX = gp.tileSize * 2;
		gp.obj[mapNum][i].worldY = gp.tileSize * 1;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL02(gp,"horizontal05");
		gp.obj[mapNum][i].worldX = gp.tileSize * 3;
		gp.obj[mapNum][i].worldY = gp.tileSize * 1;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL02(gp,"horizontal06");
		gp.obj[mapNum][i].worldX = gp.tileSize * 4;
		gp.obj[mapNum][i].worldY = gp.tileSize * 1;
		
		// SHARDS 3x
		gp.obj[mapNum][i] = new OBJ_ShardsGreen(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 19;
		gp.obj[mapNum][i].worldY = gp.tileSize * 4;
		i++;
		gp.obj[mapNum][i] = new OBJ_ShardsGreen(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 3;
		gp.obj[mapNum][i].worldY = gp.tileSize * 15;
		i++;
		gp.obj[mapNum][i] = new OBJ_ShardsGreen(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 3;
		gp.obj[mapNum][i].worldY = gp.tileSize * 20;
		i++;
		
		// MAP maze06
		mapNum = 9;
		i = 0;
		// HORIZONTAL WALL (T^T)
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL02(gp,"horizontal01");
		gp.obj[mapNum][i].worldX = gp.tileSize * 30;
		gp.obj[mapNum][i].worldY = gp.tileSize * 1;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL02(gp,"horizontal02");
		gp.obj[mapNum][i].worldX = gp.tileSize * 31;
		gp.obj[mapNum][i].worldY = gp.tileSize * 1;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL02(gp,"horizontal03");
		gp.obj[mapNum][i].worldX = gp.tileSize * 32;
		gp.obj[mapNum][i].worldY = gp.tileSize * 1;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL02(gp,"horizontal04");
		gp.obj[mapNum][i].worldX = gp.tileSize * 30;
		gp.obj[mapNum][i].worldY = gp.tileSize * 2;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL02(gp,"horizontal05");
		gp.obj[mapNum][i].worldX = gp.tileSize * 31;
		gp.obj[mapNum][i].worldY = gp.tileSize * 2;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL02(gp,"horizontal06");
		gp.obj[mapNum][i].worldX = gp.tileSize * 32;
		gp.obj[mapNum][i].worldY = gp.tileSize * 2;
		i++;
		
		// SHARDS 3x
		gp.obj[mapNum][i] = new OBJ_ShardsGreen(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 11;
		gp.obj[mapNum][i].worldY = gp.tileSize * 7;
		i++;
		gp.obj[mapNum][i] = new OBJ_ShardsGreen(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 31;
		gp.obj[mapNum][i].worldY = gp.tileSize * 15;
		i++;
		gp.obj[mapNum][i] = new OBJ_ShardsGreen(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 15;
		gp.obj[mapNum][i].worldY = gp.tileSize * 28;
		i++;
		
		// MAP maze07
		mapNum = 10;
		i = 0;
		// VERTICAL WALL (T^T)
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL03(gp,"vertical01");
		gp.obj[mapNum][i].worldX = gp.tileSize * 34;
		gp.obj[mapNum][i].worldY = gp.tileSize * 18;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL03(gp,"vertical02");
		gp.obj[mapNum][i].worldX = gp.tileSize * 34;
		gp.obj[mapNum][i].worldY = gp.tileSize * 19;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL03(gp,"vertical03");
		gp.obj[mapNum][i].worldX = gp.tileSize * 34;
		gp.obj[mapNum][i].worldY = gp.tileSize * 20;
		i++;
		
		// SHARDS 3x
		gp.obj[mapNum][i] = new OBJ_ShardsPuple(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 27;
		gp.obj[mapNum][i].worldY = gp.tileSize * 7;
		i++;
		gp.obj[mapNum][i] = new OBJ_ShardsPuple(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 11;
		gp.obj[mapNum][i].worldY = gp.tileSize * 23;
		i++;
		gp.obj[mapNum][i] = new OBJ_ShardsPuple(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 23;
		gp.obj[mapNum][i].worldY = gp.tileSize * 27;
		i++;
		
		// MAP maze08
		mapNum = 11;
		i = 0;
		// HORIZONTAL WALL (T^T)
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL03(gp,"horizontal01");
		gp.obj[mapNum][i].worldX = gp.tileSize * 6;
		gp.obj[mapNum][i].worldY = gp.tileSize * 33;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL03(gp,"horizontal02");
		gp.obj[mapNum][i].worldX = gp.tileSize * 7;
		gp.obj[mapNum][i].worldY = gp.tileSize * 33;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL03(gp,"horizontal03");
		gp.obj[mapNum][i].worldX = gp.tileSize * 8;
		gp.obj[mapNum][i].worldY = gp.tileSize * 33;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL03(gp,"horizontal04");
		gp.obj[mapNum][i].worldX = gp.tileSize * 6;
		gp.obj[mapNum][i].worldY = gp.tileSize * 34;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL03(gp,"horizontal05");
		gp.obj[mapNum][i].worldX = gp.tileSize * 7;
		gp.obj[mapNum][i].worldY = gp.tileSize * 34;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL03(gp,"horizontal06");
		gp.obj[mapNum][i].worldX = gp.tileSize * 8;
		gp.obj[mapNum][i].worldY = gp.tileSize * 34;
		i++;
		
		// SHARDS 3x
		gp.obj[mapNum][i] = new OBJ_ShardsPuple(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 23;
		gp.obj[mapNum][i].worldY = gp.tileSize * 11;
		i++;
		gp.obj[mapNum][i] = new OBJ_ShardsPuple(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 15;
		gp.obj[mapNum][i].worldY = gp.tileSize * 19;
		i++;
		gp.obj[mapNum][i] = new OBJ_ShardsPuple(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 31;
		gp.obj[mapNum][i].worldY = gp.tileSize * 31;
		i++;
		
		
		// MAP maze09
		mapNum = 12;
		i = 0;
		// HORIZONTAL WALL (T^T)
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL03(gp,"horizontal01");
		gp.obj[mapNum][i].worldX = gp.tileSize * 30;
		gp.obj[mapNum][i].worldY = gp.tileSize * 33;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL03(gp,"horizontal02");
		gp.obj[mapNum][i].worldX = gp.tileSize * 31;
		gp.obj[mapNum][i].worldY = gp.tileSize * 33;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL03(gp,"horizontal03");
		gp.obj[mapNum][i].worldX = gp.tileSize * 32;
		gp.obj[mapNum][i].worldY = gp.tileSize * 33;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL03(gp,"horizontal04");
		gp.obj[mapNum][i].worldX = gp.tileSize * 30;
		gp.obj[mapNum][i].worldY = gp.tileSize * 34;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL03(gp,"horizontal05");
		gp.obj[mapNum][i].worldX = gp.tileSize * 31;
		gp.obj[mapNum][i].worldY = gp.tileSize * 34;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL03(gp,"horizontal06");
		gp.obj[mapNum][i].worldX = gp.tileSize * 32;
		gp.obj[mapNum][i].worldY = gp.tileSize * 34;
		i++;

		
		// --- cell - 1
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL03(gp,"horizontal01");
		gp.obj[mapNum][i].worldX = gp.tileSize * 6;
		gp.obj[mapNum][i].worldY = gp.tileSize * 9;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL03(gp,"horizontal02");
		gp.obj[mapNum][i].worldX = gp.tileSize * 7;
		gp.obj[mapNum][i].worldY = gp.tileSize * 9;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL03(gp,"horizontal03");
		gp.obj[mapNum][i].worldX = gp.tileSize * 8;
		gp.obj[mapNum][i].worldY = gp.tileSize * 9;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL03(gp,"horizontal04");
		gp.obj[mapNum][i].worldX = gp.tileSize * 6;
		gp.obj[mapNum][i].worldY = gp.tileSize * 10;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL03(gp,"horizontal05");
		gp.obj[mapNum][i].worldX = gp.tileSize * 7;
		gp.obj[mapNum][i].worldY = gp.tileSize * 10;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL03(gp,"horizontal06");
		gp.obj[mapNum][i].worldX = gp.tileSize * 8;
		gp.obj[mapNum][i].worldY = gp.tileSize * 10;
		i++;
		
		// -- cell 2
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL03(gp,"vertical01");
		gp.obj[mapNum][i].worldX = gp.tileSize * 29;
		gp.obj[mapNum][i].worldY = gp.tileSize * 6;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL03(gp,"vertical02");
		gp.obj[mapNum][i].worldX = gp.tileSize * 29;
		gp.obj[mapNum][i].worldY = gp.tileSize * 7;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL03(gp,"vertical03");
		gp.obj[mapNum][i].worldX = gp.tileSize * 29;
		gp.obj[mapNum][i].worldY = gp.tileSize * 8;
		i++;
		
		// -- cell 3
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL03(gp,"vertical01");
		gp.obj[mapNum][i].worldX = gp.tileSize * 21;
		gp.obj[mapNum][i].worldY = gp.tileSize * 18;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL03(gp,"vertical02");
		gp.obj[mapNum][i].worldX = gp.tileSize * 21;
		gp.obj[mapNum][i].worldY = gp.tileSize * 19;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL03(gp,"vertical03");
		gp.obj[mapNum][i].worldX = gp.tileSize * 21;
		gp.obj[mapNum][i].worldY = gp.tileSize * 20;
		i++;
		
		// -- cell 4
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL03(gp,"vertical01");
		gp.obj[mapNum][i].worldX = gp.tileSize * 9;
		gp.obj[mapNum][i].worldY = gp.tileSize * 30;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL03(gp,"vertical02");
		gp.obj[mapNum][i].worldX = gp.tileSize * 9;
		gp.obj[mapNum][i].worldY = gp.tileSize * 31;
		i++;
		gp.obj[mapNum][i] = new OBJ_FLESH_WALL03(gp,"vertical03");
		gp.obj[mapNum][i].worldX = gp.tileSize * 9;
		gp.obj[mapNum][i].worldY = gp.tileSize * 32;
		i++;
		
		// SHARDS 5x
		gp.obj[mapNum][i] = new OBJ_ShardsPuple(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 23;
		gp.obj[mapNum][i].worldY = gp.tileSize * 7;
		i++;
		gp.obj[mapNum][i] = new OBJ_ShardsPuple(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 11;
		gp.obj[mapNum][i].worldY = gp.tileSize * 15;
		i++;
		gp.obj[mapNum][i] = new OBJ_ShardsPuple(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 3;
		gp.obj[mapNum][i].worldY = gp.tileSize * 27;
		i++;
		gp.obj[mapNum][i] = new OBJ_ShardsPuple(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 19;
		gp.obj[mapNum][i].worldY = gp.tileSize * 31;
		i++;
		gp.obj[mapNum][i] = new OBJ_ShardsPuple(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 27;
		gp.obj[mapNum][i].worldY = gp.tileSize * 23;
		i++;	

	}

	public void setNPC() {

	}

	public void setMonster() {
		int mapNum = 0;
		int i = 0;
		
		// Map 01
		mapNum = 4;
		i = 0;
				
		gp.monster[mapNum][i] = new MON_Minicutie(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*10;
		gp.monster[mapNum][i].worldY = gp.tileSize*3;
		i++;
		gp.monster[mapNum][i] = new MON_Minicutie(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*27;
		gp.monster[mapNum][i].worldY = gp.tileSize*7;
		i++;
		gp.monster[mapNum][i] = new MON_Minicutie(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*17;
		gp.monster[mapNum][i].worldY = gp.tileSize*16;
		i++;
		gp.monster[mapNum][i] = new MON_Creekling(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*11;
		gp.monster[mapNum][i].worldY = gp.tileSize*24;
		i++;
		
		// Map 02
		mapNum = 5;
		i = 0;	
		gp.monster[mapNum][i] = new MON_Minicutie(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*27;
		gp.monster[mapNum][i].worldY = gp.tileSize*7;
		i++;
		gp.monster[mapNum][i] = new MON_Minicutie(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*3;
		gp.monster[mapNum][i].worldY = gp.tileSize*15;
		i++;
		gp.monster[mapNum][i] = new MON_Creekling(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*7;
		gp.monster[mapNum][i].worldY = gp.tileSize*3;
		i++;
		gp.monster[mapNum][i] = new MON_Creekling(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*7;
		gp.monster[mapNum][i].worldY = gp.tileSize*31;
		i++;
		
		// Map 03
		mapNum = 6;
		i = 0;	
		gp.monster[mapNum][i] = new MON_Creekling(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*23;
		gp.monster[mapNum][i].worldY = gp.tileSize*15;
		i++;
		gp.monster[mapNum][i] = new MON_Creekling(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*15;
		gp.monster[mapNum][i].worldY = gp.tileSize*3;
		i++;
		gp.monster[mapNum][i] = new MON_Creekling(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*27;
		gp.monster[mapNum][i].worldY = gp.tileSize*27;
		i++;
		gp.monster[mapNum][i] = new MON_Allknowing(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*3;
		gp.monster[mapNum][i].worldY = gp.tileSize*30;
		i++;
		
		// Map 04
		mapNum = 7;
		i = 0;	
		gp.monster[mapNum][i] = new MON_Mossrock(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*4;
		gp.monster[mapNum][i].worldY = gp.tileSize*31;
		i++;
		gp.monster[mapNum][i] = new MON_Mossrock(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*18;
		gp.monster[mapNum][i].worldY = gp.tileSize*31;
		i++;
		gp.monster[mapNum][i] = new MON_WEEPING_ANGEL(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*19;
		gp.monster[mapNum][i].worldY = gp.tileSize*12;
		i++;
		
		// Map 05
		mapNum = 8;
		i = 0;	
		gp.monster[mapNum][i] = new MON_Mossrock(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*7;
		gp.monster[mapNum][i].worldY = gp.tileSize*12;
		i++;
		gp.monster[mapNum][i] = new MON_Mossrock(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*27;
		gp.monster[mapNum][i].worldY = gp.tileSize*20;
		i++;
		gp.monster[mapNum][i] = new MON_WEEPING_ANGEL(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*15;
		gp.monster[mapNum][i].worldY = gp.tileSize*15;
		i++;
		
		// Map 06
		mapNum = 9;
		i = 0;	
		gp.monster[mapNum][i] = new MON_Rockworm(gp,"right");
		gp.monster[mapNum][i].worldX = gp.tileSize*0;
		gp.monster[mapNum][i].worldY = gp.tileSize*31;
		i++;
		gp.monster[mapNum][i] = new MON_Rockworm(gp,"left");
		gp.monster[mapNum][i].worldX = gp.tileSize*34;
		gp.monster[mapNum][i].worldY = gp.tileSize*23;
		i++;
		gp.monster[mapNum][i] = new MON_Rockworm(gp,"right");
		gp.monster[mapNum][i].worldX = gp.tileSize*0;
		gp.monster[mapNum][i].worldY = gp.tileSize*19;
		i++;
		gp.monster[mapNum][i] = new MON_Rockworm(gp,"left");
		gp.monster[mapNum][i].worldX = gp.tileSize*34;
		gp.monster[mapNum][i].worldY = gp.tileSize*11;
		i++;
		gp.monster[mapNum][i] = new MON_Rockworm(gp,"right");
		gp.monster[mapNum][i].worldX = gp.tileSize*0;
		gp.monster[mapNum][i].worldY = gp.tileSize*3;
		i++;
		
		// Map 07
		mapNum = 10;
		i = 0;	
		gp.monster[mapNum][i] = new MON_MUSHMUSH(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*18;
		gp.monster[mapNum][i].worldY = gp.tileSize*3;
		i++;
		gp.monster[mapNum][i] = new MON_MUSHMUSH(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*32;
		gp.monster[mapNum][i].worldY = gp.tileSize*3;
		i++;
		gp.monster[mapNum][i] = new MON_MUSHMUSH(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*16;
		gp.monster[mapNum][i].worldY = gp.tileSize*11;
		i++;
		gp.monster[mapNum][i] = new MON_MUSHMUSH(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*7;
		gp.monster[mapNum][i].worldY = gp.tileSize*27;
		i++;
		gp.monster[mapNum][i] = new MON_MUSHMUSH(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*18;
		gp.monster[mapNum][i].worldY = gp.tileSize*23;
		i++;
		gp.monster[mapNum][i] = new MON_MUSHMUSH(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*18;
		gp.monster[mapNum][i].worldY = gp.tileSize*32;
		i++;
		gp.monster[mapNum][i] = new MON_MUSHMUSH(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*31;
		gp.monster[mapNum][i].worldY = gp.tileSize*15;
		i++;
		gp.monster[mapNum][i] = new MON_MUSHMUSH(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*31;
		gp.monster[mapNum][i].worldY = gp.tileSize*31;
		i++;
		gp.monster[mapNum][i] = new MON_MUSHMUSH(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*31;
		gp.monster[mapNum][i].worldY = gp.tileSize*27;
		i++;
		
		gp.monster[mapNum][i] = new MON_MACHROMMAN(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*18;
		gp.monster[mapNum][i].worldY = gp.tileSize*17;
		i++;
		
		// Map 08
		mapNum = 11;
		i = 0;	
		gp.monster[mapNum][i] = new MON_MUSHMUSH(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*11;
		gp.monster[mapNum][i].worldY = gp.tileSize*8;
		i++;
		gp.monster[mapNum][i] = new MON_MUSHMUSH(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*15;
		gp.monster[mapNum][i].worldY = gp.tileSize*3;
		i++;
		gp.monster[mapNum][i] = new MON_MUSHMUSH(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*31;
		gp.monster[mapNum][i].worldY = gp.tileSize*3;
		i++;
		gp.monster[mapNum][i] = new MON_MUSHMUSH(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*7;
		gp.monster[mapNum][i].worldY = gp.tileSize*12;
		i++;
		gp.monster[mapNum][i] = new MON_MUSHMUSH(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*11;
		gp.monster[mapNum][i].worldY = gp.tileSize*16;
		i++;
		gp.monster[mapNum][i] = new MON_MUSHMUSH(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*19;
		gp.monster[mapNum][i].worldY = gp.tileSize*20;
		i++;
		gp.monster[mapNum][i] = new MON_MUSHMUSH(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*31;
		gp.monster[mapNum][i].worldY = gp.tileSize*15;
		i++;
		gp.monster[mapNum][i] = new MON_MUSHMUSH(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*31;
		gp.monster[mapNum][i].worldY = gp.tileSize*24;
		i++;
		gp.monster[mapNum][i] = new MON_MUSHMUSH(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*27;
		gp.monster[mapNum][i].worldY = gp.tileSize*24;
		i++;
		gp.monster[mapNum][i] = new MON_MUSHMUSH(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*25;
		gp.monster[mapNum][i].worldY = gp.tileSize*26;
		i++;
		
		gp.monster[mapNum][i] = new MON_MACHROMMAN(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*27;
		gp.monster[mapNum][i].worldY = gp.tileSize*31;
		i++;
		
		// Map 09
		mapNum = 12;
		i = 0;	
		gp.monster[mapNum][i] = new MON_MON1(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*7;
		gp.monster[mapNum][i].worldY = gp.tileSize*8;
		i++;
		gp.monster[mapNum][i] = new MON_MON1(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*31;
		gp.monster[mapNum][i].worldY = gp.tileSize*7;
		i++;
		gp.monster[mapNum][i] = new MON_MON1(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*23;
		gp.monster[mapNum][i].worldY = gp.tileSize*19;
		i++;
		gp.monster[mapNum][i] = new MON_MON1(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*7;
		gp.monster[mapNum][i].worldY = gp.tileSize*31;
		i++;

	}

	public void setInteractiveTile() {
	}
}
