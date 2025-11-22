package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import object.OBJ_Coin_Gold;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_ManaCrystal;
import entity.Entity;

public class UI {

	GamePanel gp;
	Graphics2D g2;
	public Font purisaB;
	BufferedImage heart_full, heart_half, heart_empty, crystal_full, crystal_blank,coin;
	public boolean messageOn = false;
	ArrayList<String> message = new ArrayList<>();
	ArrayList<Integer> messageCounter = new ArrayList<>();
	public boolean gameOver = false;
	public String currentDialogue = "";
	public int commandNum = 0;
	public int titleScreenState = 0; // 0: the first screen, 1: the second screen
	public int playerSlotCol = 0;
	public int playerSlotRow = 0;
	public int npcSlotCol = 0;
	public int npcSlotRow = 0;
	int subState = 0;
	int counter = 0;
	public Entity npc;
	int charIndex = 0;
	String combinedText = "";
	
	public UI (GamePanel gp) {
		this.gp = gp;
		
		try {
			InputStream is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
			purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// CREATE HUD OBJECT
		Entity heart = new OBJ_Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_empty = heart.image3;
		Entity crystal = new OBJ_ManaCrystal(gp);
		crystal_full = crystal.image;
		crystal_blank = crystal.image2;
		Entity goldCoin = new OBJ_Coin_Gold(gp);
		coin = goldCoin.down1;
	}
	
	public void addMessage(String text) {
		
		message.add(text);
		messageCounter.add(0);
	}
	
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		g2.setFont(purisaB);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setColor(Color.YELLOW);
		
		// TITLE STATE
		if(gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		
		// PLAY STATE
		if(gp.gameState == gp.playState) {
			drawPlayerlife();
			drawMessage();
		}
		// PAUSE STATE
		if(gp.gameState == gp.pauseState) {
			drawPlayerlife();
			drawPauseScreen();
		}
		// DIALOGUE STATE
		if(gp.gameState == gp.dialogueState) {
			drawPlayerlife();
			drawDialogueScreen();
		}
		// CHARACTER STATE
		if(gp.gameState == gp.characterState) {
			drawCharacterScreen();
			drawInventory(gp.player,true);
		}
		// OPTION STATE
		if(gp.gameState == gp.optionsState) {
			drawOptionsScreen();
		}
		// GAME OVER STATE
		if(gp.gameState == gp.gameOverState) {
			drawGameOverScreen();
		}
		// TRANSITION STATE
		if(gp.gameState == gp.transitionState) {
			drawTransistion();
		}
		// TRADE STATE
		if(gp.gameState == gp.tradeState) {
			drawTradeScreen();
		}
	}
	
	public void drawPlayerlife() {
		
		int x = gp.tileSize/2;
		int y = gp.tileSize/2;
		int i = 0;
		
		// DRAW MAX LIFE
		while(i < gp.player.maxLife/2) {
			g2.drawImage(heart_empty, x, y, null);
			i++;
			x += gp.tileSize;
		}
		// RESET
		x = gp.tileSize/2;
		y = gp.tileSize/2;
		i = 0;
		
		// DRAW CURRENT LIFE
		while(i < gp.player.life) {
			g2.drawImage(heart_half, x, y, null);
			i++;
			if(i < gp.player.life) {
				g2.drawImage(heart_full, x, y, null);
			}
			i++;
			x += gp.tileSize;
		}
		// --------------------------------------------------------- //
		// DRAW MAX MANA
		x = (gp.tileSize/2) - 5;
		y = (int)(gp.tileSize*1.5);
		i = 0;
		while(i < gp.player.maxMana) {
			g2.drawImage(crystal_blank, x, y, null);
			i++;
			x += 40;
		}
		// RESET
		x = (gp.tileSize/2) - 5;
		y = (int)(gp.tileSize*1.5);
		i = 0;
		
		// DRAW MANA
		while(i < gp.player.mana) {
			g2.drawImage(crystal_full, x, y, null);
			i++;
			x += 40;
		}
		
	}
	
	public void drawMessage() {
		
		int messageX = gp.tileSize;
		int messageY = gp.tileSize*4;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32f));
		
		for(int i = 0; i < message.size(); i++) {
			
			if(message.get(i) != null) {
				
				g2.setColor(Color.black);
				g2.drawString(message.get(i), messageX+2, messageY+2);
				
				g2.setColor(Color.white);
				g2.drawString(message.get(i), messageX, messageY);
				
				int counter = messageCounter.get(i) + 1; // messageCounter++
				messageCounter.set(i, counter); // set the counter to array
				messageY += 50;
				
				if(messageCounter.get(i) > 180) {
					message.remove(i);
					messageCounter.remove(i);
				}
			}
		}
	}
	
	public void drawTitleScreen() {
		
		g2.setColor(new Color(200, 200, 200)); // BLACK NIG-  RGB
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		if(titleScreenState == 0) {
			
			// TITLE NAME
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
			String text = "GET OUT!!!";
			int x = getXforCenteredText(text);
			int y = gp.tileSize*3;
			
			// SHADOW
			g2.setColor(Color.black);
			g2.drawString(text, x+5, y+5);
			
			// MIAN COLOR
			g2.setColor(Color.red);
			g2.drawString(text, x, y);
			
			// IMAGE
			x = gp.screenWidth/2 - (gp.tileSize*2)/2;
			y += gp.tileSize*2;
			g2.drawImage(gp.player.down00, x, y, gp.tileSize*2, gp.tileSize*2, null);
			
			// MENU
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
			
			text = "NEW GAME";
			x = getXforCenteredText(text);
			y += gp.tileSize*3.5;
			g2.drawString(text, x, y);
			if(commandNum == 0) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			
			text = "LOAD GAME";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if(commandNum == 1) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			
			text = "QUIT";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if(commandNum == 2) {
				g2.drawString(">", x-gp.tileSize, y);
			}
		}
		else if (titleScreenState == 1) {
			// CHARACTER SELECTION SCREEN
			g2.setColor(Color.red);
			g2.setFont(g2.getFont().deriveFont(42F));
			
			String text = "Select Chracter:";
			int x = getXforCenteredText(text);
			int y = gp.tileSize*3;
			g2.drawString(text, x, y);
			
			text = "Player01";
			x = getXforCenteredText(text);
			y += gp.tileSize*2;
			g2.drawString(text, x, y);
			if(commandNum == 0) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			
			text = "Player02";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if(commandNum == 1) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			
			text = "Player03";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if(commandNum == 2) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			
			text = "Back";
			x = getXforCenteredText(text);
			y += gp.tileSize*2;
			g2.drawString(text, x, y);
			if(commandNum == 3) {
				g2.drawString(">", x-gp.tileSize, y);
			}
		}
		
		
		
	}
	
	public void drawPauseScreen() {
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
		String text = "TIME STOP!!";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight/2;
		g2.drawString(text,x,y);
		
	}
	
	public void drawDialogueScreen() {
		
		// WINDOW
		int x = gp.tileSize*3;
		int y = gp.tileSize/2;
		int width = gp.screenWidth - (gp.tileSize*6);
		int height = gp.tileSize*4;
		
		drawSubWindow(x,y,width,height);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
		x += gp.tileSize;
		y += gp.tileSize;
		
		if(npc.dialogues[npc.dialogueSet][npc.dialogueIndex] != null) {
			
			// THIS IS INSTANT DIALOGUE
			//currentDialogue = npc.dialogues[npc.dialogueSet][npc.dialogueIndex];
			
			// THIS IS LETER BY LETER DIALOGUE--------------
			char characters[] = npc.dialogues[npc.dialogueSet][npc.dialogueIndex].toCharArray();
			
			if(charIndex < characters.length) {
				
				// YOU CAN ADD SOUND EFFECT
				String s = String.valueOf(characters[charIndex]);
				combinedText = combinedText + s;
				currentDialogue = combinedText;
				charIndex++;
			}
			// ------------------------------------------------
			
			if(gp.keyH.enterPressed == true) {
				
				charIndex = 0;
				combinedText = "";
				
				if(gp.gameState == gp.dialogueState) {
					npc.dialogueIndex++;
					gp.keyH.enterPressed = false;
				}
			}
		}
		else {
			npc.dialogueIndex = 0;

			if(gp.gameState == gp.dialogueState) {
				gp.gameState = gp.playState;
			}
		}
		
		for(String line: currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y += 50;
		}
	}
	
	public void drawCharacterScreen() {
		
		// CREATE A FRAME
		final int frameX = gp.tileSize*2;
		final int frameY= gp.tileSize;
		final int frameWidth = gp.tileSize*5;
		final int frameHieght = gp.tileSize*10;
		drawSubWindow(frameX,frameY,frameWidth,frameHieght);
		
		// TEXT
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(28F));
		
		int textX = frameX + 20;
		int textY = frameY + gp.tileSize;
		final int lineHeight = 42; // same as font size, this for space
		
		// Names
		g2.drawString("Level: ",textX,textY); textY += lineHeight;
		g2.drawString("Life: ",textX,textY); textY += lineHeight;
		g2.drawString("Mana: ",textX,textY); textY += lineHeight;
		g2.drawString("STR: ",textX,textY); textY += lineHeight;
		g2.drawString("DEX: ",textX,textY); textY += lineHeight;
		g2.drawString("ATK: ",textX,textY); textY += lineHeight;
		g2.drawString("DEF: ",textX,textY); textY += lineHeight;
		g2.drawString("Speed: ",textX,textY); textY += lineHeight;
		g2.drawString("COIN: ",textX,textY); textY += lineHeight;
		g2.drawString("EXP: ",textX,textY); textY += lineHeight;
		g2.drawString("NEXT LVL: ",textX,textY); textY += lineHeight + 20;
		g2.drawString("WEAPON: ",textX,textY); textY += lineHeight + 10;
		g2.drawString("SHIELD: ",textX,textY); textY += lineHeight;
		
		// VALUE
		int tailX = (frameX + frameWidth) - 32;
		// RESET X and Y
		textY = frameY + gp.tileSize;
		String value;
		
		value = String.valueOf(gp.player.level);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.mana + "/" + gp.player.maxMana);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.strength);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.dexterity);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.attack);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.defense);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.speed);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.coin);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.exp);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.nextExp);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += 20;
		
		g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize,textY, null);
		textY += gp.tileSize;
		
		g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize,textY, null);
	}
	
	public void drawInventory(Entity entity, boolean cursor) {
		
		int frameX = 0;
		int frameY = 0;
		int frameWidth = 0;
		int frameHeight = 0;
		int slotCol = 0;
		int slotRow = 0;
		
		if(entity == gp.player) {
			frameX = gp.tileSize*12;
			frameY = gp.tileSize;
			frameWidth = gp.tileSize*6;
			frameHeight = gp.tileSize*5;
			slotCol = playerSlotCol;
			slotRow = playerSlotRow;
		}
		else {
			frameX = gp.tileSize*2;
			frameY = gp.tileSize;
			frameWidth = gp.tileSize*6;
			frameHeight = gp.tileSize*5;
			slotCol = npcSlotCol;
			slotRow = npcSlotRow;
		}
		
		// Frame

		drawSubWindow(frameX,frameY,frameWidth,frameHeight);
		
		// SLOT
		final int slotXstart = frameX + 24;
		final int slotYstart = frameY + 28;
		int slotX = slotXstart;
		int slotY = slotYstart;
		int slotSize = gp.tileSize+4;
		
		// DRAW PLAYER'S ITEMS
		for(int i = 0; i < entity.inventory.size(); i++) {
			
			// EQUIP CURSOR
			if(entity.inventory.get(i) == entity.currentWeapon ||
					entity.inventory.get(i) == entity.currentShield || 
					entity.inventory.get(i) == entity.currentLight) {
				g2.setColor(new Color(240,190,90));
				g2.fillRoundRect(slotX,slotY,gp.tileSize,gp.tileSize, 10, 10);
			}
			
			g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);
			
			// DISPLAY AMOUNT
			if(entity == gp.player && entity.inventory.get(i).amount > 1) {
				
				g2.setFont(g2.getFont().deriveFont(32f));
				int amountX;
				int amountY;
				
				String s = ""+entity.inventory.get(i).amount;
				amountX = getXforAlignToRightText(s, slotX+gp.tileSize);
				amountY = slotY + gp.tileSize;
				
				// SHADOW
				g2.setColor(new Color(60,60,60));
				g2.drawString(s, amountX, amountY);
				// NUMBER
				g2.setColor(Color.white);
				g2.drawString(s, amountX-3, amountY-3);
				
			}
			
			slotX += slotSize;
			
			if(i == 4 || i == 9 || i == 14) {
				slotX = slotXstart;
				slotY += slotSize;
			}
		}
		
		// CURSOR
		if(cursor == true) {
			int curosrX = slotXstart + (slotSize * slotCol);
			int cursorY = slotYstart + (slotSize * slotRow);
			int cursorWidth = gp.tileSize;
			int cursorHeight = gp.tileSize;
			// DRAW CURSOR
			g2.setColor(Color.white);
			g2.setStroke(new BasicStroke(3));
			g2.drawRoundRect(curosrX, cursorY, cursorWidth, cursorHeight, 10, 10);
			
			// SHOW DESCRIPTION FRAME
			int dFrameX = frameX;
			int dFrameY = frameY + frameHeight;
			int dFrameWidth = frameWidth;
			int dFrameHeight = gp.tileSize*3;
			
			// DRAW DISCRIPTION TEXT
			int textX = dFrameX + 24;
			int textY = dFrameY + gp.tileSize;
			g2.setFont(g2.getFont().deriveFont(20f));
			
			int itemIndex = getItemIndexOnSlot(slotCol,slotRow);
			
			if(itemIndex < entity.inventory.size()) {
				
				drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
				
				for(String line: entity.inventory.get(itemIndex).description.split("\n")) {
					g2.drawString(line, textX, textY);
					textY += 32;
				}
			}
		}
		
	}
	
	public void drawGameOverScreen() {
		
		g2.setColor(new Color(0,0,0,150));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		int x;
		int y;
		String text;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));
		
		text = "You Suck!!";
		// Shadow
		g2.setColor(Color.black);
		x = getXforCenteredText(text);
		y = gp.tileSize*4;
		g2.drawString(text, x, y);
		// Main
		g2.setColor(Color.red);
		g2.drawString(text, x-4, y-4);
		
		// Retry
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50f));
		text = "Retry";
		x = getXforCenteredText(text);
		y += gp.tileSize*4;
		g2.drawString(text, x, y);
		if(commandNum == 0) { 
			g2.drawString(">", x-40, y); 
			if(gp.keyH.enterPressed == true) {

			}
		}
		
		// Back to Title Screen
		text = "Quit";
		y += gp.tileSize;
		g2.drawString(text, x+20, y);
		if(commandNum == 1) { 
			g2.drawString(">", x-40, y); 
			if(gp.keyH.enterPressed == true) {
				
			}
		}
	}
	
	public void drawOptionsScreen() {
		
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32F));
		
		// SUB WINDOW
		// Frame
		int frameX = gp.tileSize*6;
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize*8;
		int frameHeight = gp.tileSize*10;
		drawSubWindow(frameX,frameY,frameWidth,frameHeight);
		
		switch(subState) {
		case 0: options_top(frameX,frameY); break;
		case 1: option_fullScreenNotification(frameX, frameY); break;
		case 2: options_control(frameX,frameY); break;
		case 3: option_endGameConfirmation(frameX, frameY); break;
		}
		
		gp.keyH.enterPressed = false;
	}
	
	public void options_top(int frameX, int frameY) {
		
		int textX;
		int textY;
		
		// TITLE
		String text = "Options";
		textX = getXforCenteredText(text);
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);
		
		// FULL SCREEN ON/OFF
		textX = frameX + gp.tileSize;
		textY += gp.tileSize*2;
		g2.drawString("Full Screen", textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY); 
			if(gp.keyH.enterPressed == true) {
				if(gp.fullScreenOn == false) {
					gp.fullScreenOn = true;
				}
				else if(gp.fullScreenOn == true) {
					gp.fullScreenOn = false;
				}
				subState = 1;
			}
		}
		
		// MUSIC
		textY += gp.tileSize;
		g2.drawString("Music", textX, textY);
		if(commandNum == 1) { g2.drawString(">", textX-25, textY); }
		
		// SE
		textY += gp.tileSize;
		g2.drawString("SE", textX, textY);
		if(commandNum == 2) { g2.drawString(">", textX-25, textY); }
		
		// CONTROL
		textY += gp.tileSize;
		g2.drawString("Control", textX, textY);
		if(commandNum == 3) { 
			g2.drawString(">", textX-25, textY); 
			if(gp.keyH.enterPressed == true) {
				subState = 2;
				commandNum = 0;
			}
		}
		
		// END GAME
		textY += gp.tileSize;
		g2.drawString("End Game", textX, textY);
		if(commandNum == 4) { 
			g2.drawString(">", textX-25, textY); 
			if(gp.keyH.enterPressed == true) {
				subState = 3;
				commandNum = 0;
			}
		}
		
		// BACK BUTTON
		textY += gp.tileSize*2;
		g2.drawString("Back", textX, textY);
		if(commandNum == 5) { 
			g2.drawString(">", textX-25, textY); 
			if(gp.keyH.enterPressed == true) {
				gp.gameState = gp.playState;
				commandNum = 0;
			}
		}
		
		// FULL SCREEN CHECK BOX
		textX = frameX + gp.tileSize*5;
		textY = frameY + gp.tileSize*2+36;
		g2.setStroke(new BasicStroke(3));
		g2.drawRect(textX, textY, 36, 36);
		if(gp.fullScreenOn == true) {
			g2.fillRect(textX, textY, 36, 36);
		}
		
		// MUSIC VOLUME
		textY += gp.tileSize;
		g2.drawRect(textX, textY, 125, 36); // 125/5 = 25
		int volumeWidth = 25 * gp.music.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 36);
		
		// SE VOLUME
		textY += gp.tileSize;
		g2.drawRect(textX, textY, 125, 36);
		volumeWidth = 25 * gp.se.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 36);
		
		gp.config.saveConfig();
		
	}
	
	public void option_fullScreenNotification(int frameX, int frameY) {
		
		int textX = frameX + gp.tileSize;
		int textY = frameY + gp.tileSize*3;
		
		currentDialogue = "The change will \ntake effect after \nrestarting the game";
		
		for(String line: currentDialogue.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += 40;
		}
		
		// BACK
		textY = frameY + gp.tileSize*9;
		g2.drawString("Back", textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) { subState = 0; }
		}
	}
	public void options_control(int frameX, int frameY) {
		
		int textX = frameX + gp.tileSize;
		int textY = frameY + gp.tileSize*3;
		
		// TITLE
		String text = "Control";
		textX = getXforCenteredText(text);
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);
		
		textX = frameX + gp.tileSize/2;
		textY += gp.tileSize;
		g2.drawString("Move", textX, textY); textY += gp.tileSize;
		g2.drawString("Confirm/Attack", textX, textY); textY += gp.tileSize;
		g2.drawString("Shoot/Cast", textX, textY); textY += gp.tileSize;
		g2.drawString("Character Screen", textX, textY); textY += gp.tileSize;
		g2.drawString("Pause screen", textX, textY); textY += gp.tileSize;
		g2.drawString("Options", textX, textY); textY += gp.tileSize;
		
		textX = frameX + (int)(gp.tileSize*5.5);
		textY = frameY + gp.tileSize*2;
		g2.drawString("WSAD", textX, textY); textY += gp.tileSize;
		g2.drawString("ENTER", textX, textY); textY += gp.tileSize;
		g2.drawString("F", textX+gp.tileSize/2, textY); textY += gp.tileSize;
		g2.drawString("C", textX+gp.tileSize/2, textY); textY += gp.tileSize;
		g2.drawString("P", textX+gp.tileSize/2, textY); textY += gp.tileSize;
		g2.drawString("ESC",textX, textY); textY += gp.tileSize;
		
		textX = frameX + gp.tileSize;
		textY = frameY + gp.tileSize*9;
		g2.drawString("Back", textX, textY); 
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) { 
				subState = 0; 
				commandNum = 3;
			}
		}
	}
	
	public void option_endGameConfirmation(int frameX, int frameY) {
		
		int textX = frameX + gp.tileSize;
		int textY = frameY + gp.tileSize*3;
		
		currentDialogue = "Quite the game and \n     return to \n    title screen?";
		
		for(String line: currentDialogue.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += 40;
		}
		
		// YES
		String text = "Yes";
		textX = getXforCenteredText(text);
		textY += gp.tileSize*3;
		g2.drawString(text, textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) { 
				subState = 0; 
				titleScreenState = 0;
				gp.stopMusic();
				gp.gameState = gp.titleState;
				gp.resetGame(true);
			}
		}
		
		// NO
		text = "No";
		textX = getXforCenteredText(text);
		textY += gp.tileSize;
		g2.drawString(text, textX, textY);
		if(commandNum == 1) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) { 
				subState = 0; 
				commandNum = 4;
			}
		}
	}
	public void drawTransistion() {
		
		counter++;
		g2.setColor(new Color(0,0,0,counter*5));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		if(counter == 50) {
			counter = 0;
			gp.gameState = gp.playState;
			gp.currentMap = gp.eHandler.tempMap;
			gp.player.worldX = gp.tileSize * gp.eHandler.tempCol;
			gp.player.worldY = gp.tileSize * gp.eHandler.tempRow;
			gp.eHandler.previousEventX = gp.player.worldX;
			gp.eHandler.previousEventY = gp.player.worldY;
			gp.changeArea();
		}
	}
	
	public void drawTradeScreen() {
		
		switch(subState) {
		case 0: trade_select(); break;
		case 1: trade_buy(); break;
		case 2: trade_sell(); break;
		}
		gp.keyH.enterPressed = false;
	}
	public void trade_select() {
		
		npc.dialogueSet = 0;
		drawDialogueScreen();
		
		// DRAW WINDOW
		int x = gp.tileSize*5;
		int y = gp.tileSize*4;
		int width = gp.tileSize*3;
		int height = (int)(gp.tileSize*3.5);
		drawSubWindow(x,y,width,height);
		
		// DRAW OPTION TEXT
		x += gp.tileSize;
		y += gp.tileSize;
		g2.drawString("Buy", x, y);
		if(commandNum == 0) {
			g2.drawString(">", x-25, y);
			if(gp.keyH.enterPressed == true) { 
				subState = 1;
			}
		}
		y +=  gp.tileSize;
		g2.drawString("Sell", x, y);
		if(commandNum == 1) {
			g2.drawString(">", x-25, y);
			if(gp.keyH.enterPressed == true) { 
				subState = 2;
			}
		}
		y +=  gp.tileSize;
		g2.drawString("leave", x, y);
		if(commandNum == 2) {
			g2.drawString(">", x-25, y);
			if(gp.keyH.enterPressed == true) { 
				commandNum = 0;
				npc.startDialogue(npc, 3);
			}
		}
		
	}
	public void trade_buy() {
		
		// Draw PLayer Inventory
		drawInventory(gp.player,false);
		// Draw NPC Inventory
		drawInventory(npc,true);
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 34f));
		// DRAW HINT WINDOW
		int x = gp.tileSize*2;
		int y = gp.tileSize*9;
		int width = gp.tileSize*6;
		int height = gp.tileSize*2;
		drawSubWindow(x,y,width,height);
		g2.drawString("[ESC] Back",x+35,y+75);
		
		// DRAW PLAYER COIN WINDOW
		x = gp.tileSize*12;
		y = gp.tileSize*9;
		width = gp.tileSize*6;
		height = gp.tileSize*2;
		drawSubWindow(x,y,width,height);
		g2.drawString("Your Coin: " + gp.player.coin,x+35,y+75);
		
		// DRAW PRICE WINDOW
		int itemIndex = getItemIndexOnSlot(npcSlotCol,npcSlotRow);
		if(itemIndex < npc.inventory.size()) {
			
			x = (int)(gp.tileSize*5.5);
			y = (int)(gp.tileSize*5.5);
			width = (int)(gp.tileSize*2.5);
			height = gp.tileSize;
			drawSubWindow(x,y,width,height);
			g2.drawImage(coin,x+16,y+16, 32,32,null);
			
			int price = npc.inventory.get(itemIndex).price;
			String text = "" + price;
			x = getXforAlignToRightText(text, gp.tileSize*7+30);
			g2.drawString(text, x, y+43);
			
			// BUY ITEM
			if(gp.keyH.enterPressed == true) {
				if(npc.inventory.get(itemIndex).price > gp.player.coin) {
					subState = 0;
					npc.startDialogue(npc, 4);
				}
				else {
					if(gp.player.canObtainItem(npc.inventory.get(itemIndex)) == true) {
						gp.player.coin -= npc.inventory.get(itemIndex).price;
					}
					else {
						subState = 0;
						npc.startDialogue(npc, 5);
					}
				}
			}
		}
	}
	public void trade_sell() {
		
		// DRAW PLAYERS INVENTORY
		drawInventory(gp.player,true);
		
		int x;
		int y;
		int width;
		int height;
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 34f));
		// DRAW HINT WINDOW
		x = gp.tileSize*2;
		y = gp.tileSize*9;
		width = gp.tileSize*6;
		height = gp.tileSize*2;
		drawSubWindow(x,y,width,height);
		g2.drawString("[ESC] Back",x+35,y+75);
		
		// DRAW PLAYER COIN WINDOW
		x = gp.tileSize*12;
		y = gp.tileSize*9;
		width = gp.tileSize*6;
		height = gp.tileSize*2;
		drawSubWindow(x,y,width,height);
		g2.drawString("Your Coin: " + gp.player.coin,x+35,y+75);
		
		// DRAW PRICE WINDOW
		int itemIndex = getItemIndexOnSlot(playerSlotCol,playerSlotRow);
		if(itemIndex < gp.player.inventory.size()) {
			
			x = (int)(gp.tileSize*15.5);
			y = (int)(gp.tileSize*5.5);
			width = (int)(gp.tileSize*2.5);
			height = gp.tileSize;
			drawSubWindow(x,y,width,height);
			g2.drawImage(coin,x+16,y+16, 32,32,null);
			
			int price = gp.player.inventory.get(itemIndex).price/2;
			String text = "" + price;
			x = getXforAlignToRightText(text, gp.tileSize*17+30);
			g2.drawString(text, x, y+43);
			
			// SELL ITEM
			if(gp.keyH.enterPressed == true) {
				
				if(gp.player.inventory.get(itemIndex) == gp.player.currentWeapon ||
						gp.player.inventory.get(itemIndex) == gp.player.currentShield ) {
					commandNum = 0;
					subState = 0;
					npc.startDialogue(npc, 6);
				}
				else {
					if(gp.player.inventory.get(itemIndex).amount > 1) {
						gp.player.inventory.get(itemIndex).amount--;
					}
					else {
						gp.player.inventory.remove(itemIndex);
					}
					gp.player.coin += price;
				}
			}
		}
		
	}
	public int getItemIndexOnSlot(int slotCol, int slotRow) {
		int itemIndex = slotCol + (slotRow*5);
		return itemIndex;
	}
	
	public void drawSubWindow(int x, int y, int width, int height) {
		
		Color c = new Color(0, 0, 0, 210); // RGB NUMBER: BLACK COLOR
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35); // JUST ADJUST TO YOUR LIKING
		
		c = new Color(255, 255, 255); // RGB NUMBER: WHITE COLOR
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
		
	}
	
	public int getXforCenteredText(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
	}
	
	public int getXforAlignToRightText(String text, int tailX) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = tailX - length;
		return x;
	}
}
