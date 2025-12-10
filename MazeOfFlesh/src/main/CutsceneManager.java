package main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import data.Progress;
import entity.PlayerDummy;
import object.OBJ_Shards;

public class CutsceneManager {

	GamePanel gp;
	Graphics2D g2;
	public int sceneNum;
	public int scenePhase;
	int counter = 0;
	float alpha = 0f;
	int y;
	String endCredit;

	BufferedImage bg;

	// Scene Number
	public final int NA = 0;
	public final int ending = 1;

	public CutsceneManager(GamePanel gp) {
		this.gp = gp;

		endCredit = "Programmer:\n" + "Khem" + "\n\n\n\n\n\n\n\n\n\n" + "Muisc:\n" + "Rick"
				+ "\n\n\n\n\n\n\n\n\n\n" + "Art:\n" + "Emmge & Rick" + "\n\n\n\n\n\n\n\n\n\n" + "Doccumentation:\n"
				+ "Kyle" + "\n\n\n\n\n\n\n\n\n\n" + "Special Thanks:\n" + "RyiSnow\n" + "ChatGPT\n" + "DeepSeek"+"\n\n\n\n\n\n\n\n\n"
				+ "Thank for playing!!\n" + "\n\n\n\n\n\n\n\n\n" + "But, Why are you playing???";
	}

	public void draw(Graphics2D g2) {
		this.g2 = g2;

		switch (sceneNum) {
		case ending:
			scene_ending();
			break;
		}
	}

	public void scene_something() {
		// ZERO PHASE
		if (scenePhase == 0) {

			// SOMTHING HERE?
			// This is for temporary entity or object in the scene
			for (int i = 0; i < gp.obj[1].length; i++) {
				// something in here?
				break;
			}
			// This if for npc, but first search if vacant slot
			for (int i = 0; i < gp.npc[1].length; i++) {
				// check if vacant, then add the dummy, this is if you want to move the camera
				// in the screen
				if (gp.npc[gp.currentMap][i] == null) {
					gp.npc[gp.currentMap][i] = new PlayerDummy(gp);
					gp.npc[gp.currentMap][i].worldX = gp.player.worldX;
					gp.npc[gp.currentMap][i].worldY = gp.player.worldY;
					gp.npc[gp.currentMap][i].direction = gp.player.direction;
					break;
				}
			}

			// This is for if you want to move the camera and you will make the player
			// disappear
			gp.player.drawing = false;
			// ----------------
			scenePhase++;
		}
		// FIRST PHASE
		if (scenePhase == 1) {

			// ALSO SOMTHING YOU WANT TO DO
			// you can move the camera by moving the player

			// example
			gp.player.worldY -= 2; // camera move up

			if (gp.player.worldY < gp.tileSize * 16) { // this is for to where to stop the camera
				scenePhase++;
			}
		}
		// SECOND PHASE
		if (scenePhase == 2) {

			// I GUESS YOU KNOW THE DRILL, the pattern here?
			// Also if or a dialogue cut Scene you need a whole 1 phase

		}
		// PHASE BLA BLA
		if (scenePhase == 3) {

			// I GUESS YOU KNOW THE DRILL, the pattern here?
			// Lets make this the final cut scene

			// search for dummy
			for (int i = 0; i < gp.npc[1].length; i++) {

				if (gp.npc[gp.currentMap][i] == null && gp.npc[gp.currentMap][i].name.equals(PlayerDummy.npcName)) {
					// Restore players position
					gp.player.worldX = gp.npc[gp.currentMap][i].worldX;
					gp.player.worldY = gp.npc[gp.currentMap][i].worldY;
					// DELETE DUMMY
					gp.npc[gp.currentMap][i] = null;
					break;
				}
			}

			// Start drawing the player
			gp.player.drawing = true;

			// Reset
			sceneNum = NA;
			scenePhase = 0;
			gp.gameState = gp.playState;

			// And yeah you can add music or something
			// gp.stopMusic();
			// gp.playMusic();
		}
	}

	public void scene_ending() {

		if (scenePhase == 0) {

			gp.stopMusic();
			scenePhase++;
		}
		if (scenePhase == 1) {

			// Display dialogue
			gp.ui.drawDialogueScreen();
		}
		if (scenePhase == 2) {

			gp.playSE(1);
			scenePhase++;
		}
		if (scenePhase == 3) {

			// wait?
			if (counterReached(180) == true) {
				scenePhase++;
			}
		}
		if (scenePhase == 4) {

			// Screen get darker
			alpha += 0.005f;
			if (alpha > 1f) {
				alpha = 1f;
			}
			drawBlackBackground(alpha);

			if (alpha == 1f) {
				alpha = 0;
				scenePhase++;
			}
		}
		if (scenePhase == 5) {

			drawBlackBackground(1f);

			alpha += 0.005f;
			if (alpha > 1f) {
				alpha = 1f;
			}

			String text = "Lets make this short shall wee? \n" + "I don't know how but....\n"
					+ "Guess what?\n" + "You winn!!!\n" + "<(^-^)>";
			drawString(alpha, 40f, 200, text, 70);
			if (counterReached(360) == true) {
				gp.playMusic(0);
				scenePhase++;
			}
		}
		if (scenePhase == 6) {

			drawBlackBackground(1f);

			drawString(1f, 120f, gp.screenHeight / 2, "Maze of Flesh", 40);

			if (counterReached(360) == true) {
				scenePhase++;
			}
		}
		if (scenePhase == 7) {

			drawBlackBackground(1f);

			y = gp.screenHeight / 2;

			drawString(1f, 38f, y, endCredit, 40);

			if (counterReached(360) == true) {
				scenePhase++;
			}
		}
		if (scenePhase == 8) {

			drawBlackBackground(1f);

			// Scroll the credit
			y--;
			drawString(1f, 38f, y, endCredit, 40);

			if (counterReached(3200) == true) {
				scenePhase++;
			}
		}
		if (scenePhase == 9) {

			drawBlackBackground(1f);

			drawString(1f, 120f, y, "THE END", 40);
			
			if (counterReached(50) == true) {
				scenePhase++;
			}
		} 
		if (scenePhase == 10) {
			// Reset
			sceneNum = NA;
			scenePhase = 0;
			Progress.endingSceneDone = false;
			gp.saveLoad.save();
			gp.resetGame(true);
			gp.gameState = gp.titleState;
		}

	}

	public boolean counterReached(int target) {

		boolean counterReached = false;

		counter++;
		if (counter > target) {
			counterReached = true;
			counter = 0;
		}
		return counterReached;
	}

	public void drawBlackBackground(float alpha) {

		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g2.setColor(Color.white);
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

	}

	public void drawString(float alpha, float fontSize, int y, String text, int lineHeight) {

		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

		g2.setColor(Color.red);
		g2.setFont(g2.getFont().deriveFont(fontSize));

		for (String line : text.split("\n")) {
			int x = gp.ui.getXforCenteredText(line);
			g2.drawString(line, x, y);
			y += lineHeight;

		}

	}
}
