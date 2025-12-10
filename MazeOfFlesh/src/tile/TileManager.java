package tile;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
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

		tile = new Tile[220];
		mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

		getTileImage();
		// MAP Safe
		loadMap("/maps/safe01.txt", 0);
		loadMap("/maps/safe02.txt", 1);
		loadMap("/maps/safe03.txt", 2);
		loadMap("/maps/safe04.txt", 3);
		
		// MAZE
		loadMap("/maze/maze01.txt", 4);
		loadMap("/maze/maze02.txt", 5);
		loadMap("/maze/maze03.txt", 6);
		
		loadMap("/maze/maze04.txt", 7); 
		loadMap("/maze/maze05.txt", 8);
		loadMap("/maze/maze06.txt", 9);
		
		loadMap("/maze/maze07.txt", 10);
		loadMap("/maze/maze08.txt", 11);
		loadMap("/maze/maze09.txt", 12);


	}
	
	public int getMapIndexForRoom(int roomIndex) {
	    if (roomIndex >= 0 && roomIndex < 25) {
	        return roomIndex;
	    }
	    return 0;
	}

	private void getTileImage() {
		
		setup(0, "tile_000", false);
		setup(1, "tile_001", false);
		setup(2, "tile_002", false);
		setup(3, "tile_003", false);
		setup(4, "tile_004", false);
		setup(5, "tile_005", false);
		setup(6, "tile_006", false);
		setup(7, "tile_007", false);
		setup(8, "tile_008", false);
		setup(9, "tile_009", false);
		setup(10, "tile_010", false);
		setup(11, "tile_011", false);
		setup(12, "tile_012", false);
		setup(13, "tile_013", false);
		setup(14, "tile_014", false);
		setup(15, "tile_015", false);
		setup(16, "tile_016", false);
		setup(17, "tile_017", false);
		setup(18, "tile_018", false);
		setup(19, "tile_019", false);
		setup(20, "tile_020", false);
		setup(21, "tile_021", false);
		setup(22, "tile_022", false);
		setup(23, "tile_023", false);
		setup(24, "tile_024", false);
		setup(25, "tile_025", false);
		setup(26, "tile_026", false);
		setup(27, "tile_027", false);
		setup(28, "tile_028", false);
		setup(29, "tile_029", false);
		setup(30, "tile_030", true);
		setup(31, "tile_031", true);
		setup(32, "tile_032", true);
		setup(33, "tile_033", true);
		setup(34, "tile_034", true);
		setup(35, "tile_035", true);
		setup(36, "tile_036", true);
		setup(37, "tile_037", true);
		setup(38, "tile_038", true);
		setup(39, "tile_039", true);
		setup(40, "tile_040", true);
		setup(41, "tile_041", true);
		setup(42, "tile_042", true);
		setup(43, "tile_043", true);
		setup(44, "tile_044", true);
		setup(45, "tile_045", true);
		setup(46, "tile_046", false);
		setup(47, "tile_047", false);
		setup(48, "tile_048", false);
		setup(49, "tile_049", false);
		setup(50, "tile_050", false);
		setup(51, "tile_051", false);
		setup(52, "tile_052", false);
		setup(53, "tile_053", false);
		setup(54, "tile_054", false);
		setup(55, "tile_055", false);
		setup(56, "tile_056", false);
		setup(57, "tile_057", false);
		setup(58, "tile_058", false);
		setup(59, "void", false);
		setup(60, "tile_060", false);
		setup(61, "tile_061", false);
		setup(62, "tile_062", false);
		setup(63, "tile_063", false);
		setup(64, "tile_064", false);
		setup(65, "tile_065", false);
		setup(66, "tile_066", false);
		setup(67, "tile_067", false);
		setup(68, "tile_068", false);
		setup(69, "tile_069", false);
		setup(70, "tile_070", false);
		setup(71, "tile_071", false);
		setup(72, "tile_072", false);
		setup(73, "tile_073", false);
		setup(74, "tile_074", false);
		setup(75, "tile_075", false);
		setup(76, "tile_076", false);
		setup(77, "tile_077", false);
		setup(78, "tile_078", false);
		setup(79, "tile_079", false);
		setup(80, "tile_080", false);
		setup(81, "tile_081", false);
		setup(82, "tile_082", false);
		setup(83, "tile_083", false);
		setup(84, "tile_084", false);
		setup(85, "tile_085", false);
		setup(86, "tile_086", false);
		setup(87, "tile_087", false);
		setup(88, "tile_088", false);
		setup(89, "tile_089", false);
		setup(90, "tile_090", true);
		setup(91, "tile_091", true);
		setup(92, "tile_092", true);
		setup(93, "tile_093", true);
		setup(94, "tile_094", true);
		setup(95, "tile_095", true);
		setup(96, "tile_096", true);
		setup(97, "tile_097", true);
		setup(98, "tile_098", true);
		setup(99, "tile_099", true);
		setup(100, "tile_100", true);
		setup(101, "tile_101", true);
		setup(102, "tile_102", true);
		setup(103, "tile_103", true);
		setup(104, "tile_104", true);
		setup(105, "tile_105", true);
		setup(106, "tile_106", false);
		setup(107, "tile_107", false);
		setup(108, "tile_108", false);
		setup(109, "tile_109", false);
		setup(110, "tile_110", false);
		setup(111, "tile_111", false);
		setup(112, "tile_112", false);
		setup(113, "tile_113", false);
		setup(114, "tile_114", false);
		setup(115, "tile_115", false);
		setup(116, "tile_116", false);
		setup(117, "tile_117", false);
		setup(118, "tile_118", false);
		setup(119, "void", false);
		setup(120, "tile_120", false);
		setup(121, "tile_121", false);
		setup(122, "tile_122", false);
		setup(123, "tile_123", false);
		setup(124, "tile_124", false);
		setup(125, "tile_125", false);
		setup(126, "tile_126", false);
		setup(127, "tile_127", false);
		setup(128, "tile_128", false);
		setup(129, "tile_129", false);
		setup(130, "tile_130", false);
		setup(131, "tile_131", false);
		setup(132, "tile_132", false);
		setup(133, "tile_133", false);
		setup(134, "tile_134", false);
		setup(135, "tile_135", false);
		setup(136, "tile_136", false);
		setup(137, "tile_137", false);
		setup(138, "tile_138", false);
		setup(139, "tile_139", false);
		setup(140, "tile_140", false);
		setup(141, "tile_141", false);
		setup(142, "tile_142", false);
		setup(143, "tile_143", false);
		setup(144, "tile_144", false);
		setup(145, "tile_145", false);
		setup(146, "tile_146", false);
		setup(147, "tile_147", false);
		setup(148, "tile_148", false);
		setup(149, "tile_149", false);
		setup(150, "tile_150", true);
		setup(151, "tile_151", true);
		setup(152, "tile_152", true);
		setup(153, "tile_153", true);
		setup(154, "tile_154", true);
		setup(155, "tile_155", true);
		setup(156, "tile_156", true);
		setup(157, "tile_157", true);
		setup(158, "tile_158", true);
		setup(159, "tile_159", true);
		setup(160, "tile_160", true);
		setup(161, "tile_161", true);
		setup(162, "tile_162", true);
		setup(163, "tile_163", true);
		setup(164, "tile_164", true);
		setup(165, "tile_165", true);
		setup(166, "tile_166", false);
		setup(167, "tile_167", false);
		setup(168, "tile_168", false);
		setup(169, "tile_169", false);
		setup(170, "tile_170", false);
		setup(171, "tile_171", false);
		setup(172, "tile_172", false);
		setup(173, "tile_173", false);
		setup(174, "tile_174", false);
		setup(175, "tile_175", false);
		setup(176, "tile_176", false);
		setup(177, "tile_177", false);
		setup(178, "tile_178", false);
		setup(179, "void", false);
		setup(180, "tile_180", true);
		setup(181, "tile_181", true);
		setup(182, "tile_182", true);
		setup(183, "tile_183", true);
		setup(184, "void", true);
		setup(185, "tile_185", true);
		setup(186, "tile_186", true);
		setup(187, "tile_187", true);
		setup(188, "tile_188", true);
		setup(189, "void", true);
		setup(190, "tile_190", false);
		setup(191, "tile_191", false);
		setup(192, "tile_192", false);
		setup(193, "tile_193", false);
		setup(194, "tile_194", true);
		setup(195, "tile_195", true);
		setup(196, "tile_196", true);
		setup(197, "tile_197", true);
		setup(198, "void", true);
		setup(199, "void", true);
		setup(200, "tile_200", false);
		setup(201, "tile_201", false);
		setup(202, "tile_202", false);
		setup(203, "tile_203", false);
		setup(204, "tile_207", false);
		setup(205, "tile_205", false);
		setup(206, "tile_206", false);
		setup(207, "tile_207", false);
		setup(208, "tile_208", false);
		setup(209, "void", false);
		
	}

	private void setup(int index, String imageName, boolean collision) {

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

	private void loadMap(String filePath, int map) {

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
}

