package tile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {

	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][][];
	boolean drawPath = false;
	
	// Below for tile editor
	ArrayList<String> fileNames = new ArrayList<>();
	ArrayList<String> collisionStatus = new ArrayList<>();
	// ----
	
	public TileManager(GamePanel gp) {
		
		this.gp = gp;
		
//		/// Below tile editor
//		InputStream is = getClass().getResourceAsStream("maps/safeZone.txt");
//		BufferedReader br = new BufferedReader(new InputStreamReader(is));
//		
//		// Getting Tile names and Collision info of a tile
//		String line;
//		
//		try {
//			while((line = br.readLine()) != null) {
//				fileNames.add(line);
//				collisionStatus.add(br.readLine());
//			}
//			br.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		// INITIALIZE THE TILE ARRAY BASE ON THE FILENAMES SIZE
//		//tile = new Tile[fileNames.size()];
//		//getTileImage();
//		
//		is = getClass().getResourceAsStream("maps/smaple.txt");
//		br = new BufferedReader(new InputStreamReader(is));
//		try {
//			String line2 = br.readLine();
//			String maxTile[] = line2.split(" ");
//			
//			gp.maxWorldCol = maxTile.length;
//			gp.maxWorldRow = maxTile.length;
//			mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
//			
//			br.close();
//		} catch (IOException e) {
//			System.out.println("Exception!");
//		}
//		
//		// MAP
//		loadMap("/maps/safeZone.txt",0);
//		
//		// MAZE
//		loadMap("/maze/maze001.txt",1);
//		loadMap("/maze/maze002.txt",2);
//		loadMap("/maze/maze003.txt",3);
//		loadMap("/maze/maze004.txt",4);
//		loadMap("/maze/maze005.txt",5);
//		/// ---
		
		
		tile = new Tile[50];
		mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
		
		getTileImage();
		// MAP
		loadMap("/maps/safeZone.txt",0);

		// MAZE
		loadMap("/maze/maze001.txt",1);
		loadMap("/maze/maze002.txt",2);
		loadMap("/maze/maze003.txt",3);
		loadMap("/maze/maze004.txt",4);
		loadMap("/maze/maze005.txt",5);
	}
	
	public void getTileImage() {
		
//		/// BELOW ARE FOR TILE EDITOR
//		for(int i = 0; i < fileNames.size(); i++) {
//			String fileName;
//			boolean collision;
//			
//			// Get file name
//			fileName = fileNames.get(i);
//			// Get a collision status
//			if(collisionStatus.get(i).equals("true")) {
//				collision = true;
//			} else {
//				collision = false;
//			}
//			
//			setup(i,fileName,collision);
//		}
//		/// -------------------------
		
		// PLACE HOLDER
		for(int i = 0; i < 10; i++) {
			setup(i,"void", true);
		}
		setup(10,"void", true);
		
		// FLOOR ----
        setup(12,"bloodfloor00", false);
        setup(13,"bloodfloor01", false);
        setup(14,"bloodfloor02", false);
        
        // WALLS ----
        setup(15,"walls01", true);
        setup(16,"walls02", true);
        setup(17,"walls03", true);
        setup(18,"walls04", true);
        setup(19,"walls05", true);
        setup(20,"walls06", true);
        setup(21,"walls07", true);
        setup(22,"walls08", true);
        setup(23,"walls09", true);
        setup(24,"walls10", true);
        setup(25,"walls11", true);
	}
	
	public void setup(int index, String imageName, boolean collision) {
		
		UtilityTool uTool = new UtilityTool();
		
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png")); // For manual
//			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName));// For tile editor
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize,gp.tileSize);
			tile[index].collision = collision;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void loadMap(String filePath, int map) {
		
		try {
			
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
				
				String line = br.readLine();
				
				while(col < gp.maxWorldCol) {
					
					String numbers[] = line.split(" ");
					
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[map][col][row] = num;
					col++;
				}
				if(col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			br.close();
			
		} catch (Exception e) {}
	}
	
	public void draw(Graphics2D g2) {
		
		// Clear background first
		g2.setColor(Color.black);
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		int worldCol = 0;
		int worldRow = 0;
		
		while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			
			int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];
			
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			// --- This block of code will save run processing by only drawing the tiles that players see ---
			if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
			   worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
			   worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
			   worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
				
				g2.drawImage(tile[tileNum].image, screenX, screenY, null);
			}
			
			worldCol++;
			
			if(worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}
		
		// JUST DRAW THE PATH OF THE NPC NOTHING SPCIAL
		if (drawPath == true) {
			g2.setColor(new Color(255,255,255,70));
			
			for(int i = 0; i < gp.pFinder.pathList.size(); i++) {
				
				int worldX = gp.pFinder.pathList.get(i).col * gp.tileSize;
				int worldY = gp.pFinder.pathList.get(i).row * gp.tileSize;
				int screenX = worldX - gp.player.worldX + gp.player.screenX;
				int screenY = worldY - gp.player.worldY + gp.player.screenY;
				
				g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
			}
		}
		
	}
}
