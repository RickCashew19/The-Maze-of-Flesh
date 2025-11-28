package tile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {

	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][][];
	boolean drawPath = false;

	public TileManager(GamePanel gp) {

		this.gp = gp;

		tile = new Tile[60];
		mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

		getTileImage();
		// MAP
		loadMap("/maps/safeZone.txt", 0);

		// MAZE
		loadMap("/maze/Maze01.txt", 1);
		loadMap("/maze/Maze02.txt", 2);
		loadMap("/maze/maze003.txt", 3);
		loadMap("/maze/maze004.txt", 4);
		loadMap("/maze/maze005.txt", 5);
	}

	public void getTileImage() {
		
		setup(0, "tile_0", false);
		setup(1, "tile_1", false);
		setup(2, "tile_2", false);
		setup(3, "tile_3", false);
		setup(4, "tile_4", false);
		setup(5, "tile_5", false);
		setup(6, "tile_6", false);
		setup(7, "tile_7", false);
		setup(8, "tile_8", false);
		setup(9, "tile_9", false);
		setup(10, "tile_10", false);
		setup(11, "tile_11", false);
		setup(12, "tile_12", false);
		setup(13, "tile_13", false);
		setup(14, "tile_14", false);
		setup(15, "tile_15", false);
		setup(16, "tile_16", false);
		setup(17, "tile_17", false);
		setup(18, "tile_18", false);
		setup(19, "tile_19", false);
		setup(20, "tile_20", false);
		setup(21, "tile_21", false);
		setup(22, "tile_22", false);
		setup(23, "tile_23", false);
		
		// VOID
		setup(24, "void", true);
		setup(25, "void", true);
		setup(26, "void", true);
		setup(27, "void", true);
		setup(28, "void", true);
		setup(29, "void", true);
		
		// WALLS
		setup(30, "tile_30", true);
		setup(31, "tile_31", true);
		setup(32, "tile_32", true);
		setup(33, "tile_33", true);
		setup(34, "tile_34", true);
		setup(35, "tile_35", true);
		setup(36, "tile_36", true);
		setup(37, "tile_37", true);
		setup(38, "tile_38", true);
		setup(39, "tile_39", true);
		setup(40, "tile_40", true);
		setup(41, "tile_41", true);
		setup(42, "tile_42", true);
		setup(43, "tile_43", true);
		setup(44, "tile_44", true);
		setup(45, "tile_45", true);
		
		setup(46, "tile_46", false);
		setup(47, "tile_47", false);
		setup(48, "tile_48", false);
		setup(49, "tile_49", false);
		setup(50, "tile_49", false);
		
	}

	public void setup(int index, String imageName, boolean collision) {

		UtilityTool uTool = new UtilityTool();

		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
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

			while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

				String line = br.readLine();

				while (col < gp.maxWorldCol) {

					String numbers[] = line.split(" ");

					int num = Integer.parseInt(numbers[col]);

					mapTileNum[map][col][row] = num;
					col++;
				}
				if (col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			br.close();

		} catch (Exception e) {
		}
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

			// --- This block of code will save run processing by only drawing the tiles
			// that players see ---
			if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
					&& worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
					&& worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
					&& worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

				g2.drawImage(tile[tileNum].image, screenX, screenY, null);
			}

			worldCol++;

			if (worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}

		// JUST DRAW THE PATH OF THE NPC NOTHING SPCIAL
		if (drawPath == true) {
			g2.setColor(new Color(255, 255, 255, 150));

			for (int i = 0; i < gp.pFinder.pathList.size(); i++) {

				int worldX = gp.pFinder.pathList.get(i).col * gp.tileSize;
				int worldY = gp.pFinder.pathList.get(i).row * gp.tileSize;
				int screenX = worldX - gp.player.worldX + gp.player.screenX;
				int screenY = worldY - gp.player.worldY + gp.player.screenY;

				g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
			}
		}

	}
}
/*
package tile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][][];
    boolean drawPath = false;
    
    // Room template file paths - match these to your room templates
    private String[] roomTemplatePaths = {
        "/maze/safe_room_0.txt",  // Map 0: Safe room template 0
        "/maze/safe_room_1.txt",  // Map 1: Safe room template 1  
        "/maze/safe_room_2.txt",  // Map 2: Safe room template 2
        "/maze/safe_room_3.txt",  // Map 3: Safe room template 3
        "/maze/boss_room_0.txt",  // Map 4: Boss room template 0
        "/maze/boss_room_1.txt",  // Map 5: Boss room template 1
        "/maze/boss_room_2.txt",  // Map 6: Boss room template 2
        "/maze/boss_room_3.txt",  // Map 7: Boss room template 3
        "/maze/normal_room_0.txt", // Map 8: Normal room template 0
        "/maze/normal_room_1.txt", // Map 9: Normal room template 1
        "/maze/normal_room_2.txt", // Map 10: Normal room template 2
        "/maze/normal_room_3.txt", // Map 11: Normal room template 3
        "/maze/normal_room_4.txt", // Map 12: Normal room template 4
        "/maze/normal_room_5.txt", // Map 13: Normal room template 5
        "/maze/normal_room_6.txt", // Map 14: Normal room template 6
        "/maze/normal_room_7.txt", // Map 15: Normal room template 7
        "/maze/normal_room_8.txt", // Map 16: Normal room template 8
        "/maze/normal_room_9.txt", // Map 17: Normal room template 9
        "/maze/normal_room_10.txt", // Map 18: Normal room template 10
        "/maze/normal_room_11.txt", // Map 19: Normal room template 11
        "/maze/normal_room_12.txt", // Map 20: Normal room template 12
        "/maze/normal_room_13.txt", // Map 21: Normal room template 13
        "/maze/normal_room_14.txt", // Map 22: Normal room template 14
        "/maze/normal_room_15.txt", // Map 23: Normal room template 15
        "/maze/normal_room_16.txt"  // Map 24: Normal room template 16
    };

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[50];
        mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        
        // Load the main safe zone map (map 0)
        loadMap("/maps/safeZone.txt", 0);
        
        // Load all room templates into their respective map indexes
        loadAllRoomTemplates();
    }

    public void getTileImage() {
        // PLACE HOLDER
        for (int i = 0; i < 10; i++) {
            setup(i, "void", true);
        }
        setup(10, "void", true);

        // FLOOR ----
        setup(12, "bloodfloor00", false);
        setup(13, "bloodfloor01", false);
        setup(14, "bloodfloor02", false);

        // WALLS ----
        setup(15, "walls01", true);
        setup(16, "walls02", true);
        setup(17, "walls03", true);
        setup(18, "walls04", true);
        setup(19, "walls05", true);
        setup(20, "walls06", true);
        setup(21, "walls07", true);
        setup(22, "walls08", true);
        setup(23, "walls09", true);
        setup(24, "walls10", true);
        setup(25, "walls11", true);
    }

    public void setup(int index, String imageName, boolean collision) {
        UtilityTool uTool = new UtilityTool();

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadAllRoomTemplates() {
        // Load each room template into its corresponding map index
        for (int mapIndex = 1; mapIndex < roomTemplatePaths.length && mapIndex < gp.maxMap; mapIndex++) {
            loadMap(roomTemplatePaths[mapIndex], mapIndex);
            System.out.println("Loaded room template: " + roomTemplatePaths[mapIndex] + " into map " + mapIndex);
        }
    }

    public void loadMap(String filePath, int map) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            if (is == null) {
                System.out.println("WARNING: Could not load map file: " + filePath);
                return;
            }
            
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();
                if (line == null) break;

                while (col < gp.maxWorldCol) {
                    String numbers[] = line.split(" ");
                    if (col >= numbers.length) break;

                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[map][col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();

        } catch (Exception e) {
            System.out.println("ERROR loading map " + map + ": " + filePath);
            e.printStackTrace();
        }
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

            // Only draw tiles that are visible to the player
            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
                    && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                    && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
                    && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }

            worldCol++;
            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }

        // Draw pathfinding debug
        if (drawPath == true) {
            g2.setColor(new Color(255, 255, 255, 70));
            for (int i = 0; i < gp.pFinder.pathList.size(); i++) {
                int worldX = gp.pFinder.pathList.get(i).col * gp.tileSize;
                int worldY = gp.pFinder.pathList.get(i).row * gp.tileSize;
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;
                g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
            }
        }
    }
    public int getMapIndexForRoom(int roomType, String doorConfiguration) {
        // You can implement logic here to match room types and door configs to specific map files
        // For now, we'll use a simple mapping based on roomType
        switch (roomType) {
            case 0: // SAFE_ROOM
                return 1 + (int)(Math.random() * 4); // Maps 1-4 for safe rooms
            case 1: // NORMAL_ROOM  
                return 8 + (int)(Math.random() * 17); // Maps 8-24 for normal rooms
            case 2: // BOSS_ROOM
                return 4 + (int)(Math.random() * 4); // Maps 4-7 for boss rooms
            default:
                return 8; // Default to a normal room
        }
    }
} */
