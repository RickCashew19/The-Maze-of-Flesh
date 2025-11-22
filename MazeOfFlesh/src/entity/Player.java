package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.GamePanel;
import main.KeyHandler;
import object.OBJ_Fireball;
import object.OBJ_Lantern;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;

public class Player extends Entity{

	KeyHandler keyH;
	String lastKeyPressed = "down00"; //Monitor the last keyStroke
	public final int screenX;
	public final int screenY;
	int standCounter = 0;
	boolean textOn = true;
	int textCounter = 0;
	public boolean attackCanceled = false;
	public boolean lightUpdated = false;
	public boolean exhausted = false;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		super(gp);
		this.keyH = keyH;
		
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		
		// SOLID AREA
		solidArea = new Rectangle();
		solidArea.x = 16;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 32;
		solidArea.height = 48;
		// ATTACK AREA
//		attackArea.width = 64;
//		attackArea.height = 64;
		
		setDefaultValues();

	}
	
	public void setDefaultValues() {
		
//		worldX = gp.tileSize * 24;
//		worldY = gp.tileSize * 24;
		worldX = gp.tileSize * 11;
		worldY = gp.tileSize * 7;
		defaultSpeed = 4;
		speed = defaultSpeed;
		
		direction = "down00";
		
		// PLAYER STATUS
		maxLife = 8;
		life = maxLife;
		maxStamina = 180; 
		stamina = maxStamina;
		
		// --- FPR RPG ---
		level =1;
		strength = 2;
		dexterity = 2;
		maxMana = 0;
		mana = maxMana;
		ammo = 10;
		defense = 1;
		exp = 0;
		nextExp = 5;
		coin = 9999;
		currentWeapon = new OBJ_Sword_Normal(gp);
		currentShield = new OBJ_Shield_Wood(gp);
		currentLight = new OBJ_Lantern(gp);
		projectile = new OBJ_Fireball(gp);
		attack = getAttack();
		defense = getDefense();
		
		getPlayerImage();
		getPlayerAttackImage();
		setItems();
		setDialogue();
	}
	
	public void setDefaultPositions() {
		
		worldX = gp.tileSize * 11;
		worldY = gp.tileSize * 7;
		direction = "down00";
	}
	
	public void setDialogue() {
		
		dialogues[0][0] = "You are level " + level + " now!\n" 
				+ "You feel Stronger!!";
	}
	
	public void restorStatus() {
		
		life = maxLife;
		mana = maxMana;
		speed = defaultSpeed;
		invincible = false;
		attacking = false;
		knockBack = false;
		lightUpdated = true;
	}
	
	public void setItems() {
		
		inventory.clear();
		inventory.add(currentWeapon);
		inventory.add(currentShield);
		inventory.add(currentLight);
				
	}
	
	public int getAttack() {
		
		attackArea = currentWeapon.attackArea;
		motion1_duration = currentWeapon.motion1_duration;
		motion2_duration = currentWeapon.motion2_duration;
		return attack = strength * currentWeapon.attackValue;
	}
	
	public int getDefense() {
		
		return defense = dexterity * currentShield.defenseValue;
	}
	
	public void getPlayerImage() {
		
		// -- IDLE -- //Stay
		up00 = setup("/player/up00",gp.tileSize,gp.tileSize);
		down00 = setup("/player/down00",gp.tileSize,gp.tileSize);
		left00 = setup("/player/left00",gp.tileSize,gp.tileSize);
		right00 = setup("/player/right00",gp.tileSize,gp.tileSize);
		
		
		// -- MOVEMENT --
		up1 = setup("/player/up01",gp.tileSize,gp.tileSize);
		up2 = setup("/player/up02",gp.tileSize,gp.tileSize);
		
		down1 = setup("/player/down01",gp.tileSize,gp.tileSize);
		down2 = setup("/player/down02",gp.tileSize,gp.tileSize);
		
		left1 = setup("/player/left01",gp.tileSize,gp.tileSize);
		left2 = setup("/player/left02",gp.tileSize,gp.tileSize);
		
		right1 = setup("/player/right01",gp.tileSize,gp.tileSize);
		right2 = setup("/player/right02",gp.tileSize,gp.tileSize);
		
	}
	
	public void getPlayerAttackImage() {
		
		// ATTACK SPRITE USING SWORD
		if(currentWeapon.type == type_sword) {
			attackUp1 = setup("/player/attackUp01",gp.tileSize,gp.tileSize*2);
			attackUp2 = setup("/player/attackUp02",gp.tileSize,gp.tileSize*2);
			attackDown1 = setup("/player/attackDown01",gp.tileSize,gp.tileSize*2);
			attackDown2 = setup("/player/attackDown02",gp.tileSize,gp.tileSize*2);
			attackLeft1 = setup("/player/attackLeft01",gp.tileSize*2,gp.tileSize);
			attackLeft2 = setup("/player/attackLeft02",gp.tileSize*2,gp.tileSize);
			attackRight1 = setup("/player/attackRight01",gp.tileSize*2,gp.tileSize);
			attackRight2 = setup("/player/attachRight02",gp.tileSize*2,gp.tileSize);
		}
		// ATTACK SPRITE USING AXE (NOT YET CHANGE)
		if(currentWeapon.type == type_axe) {
			attackUp1 = setup("/player/attackUp01",gp.tileSize,gp.tileSize*2);
			attackUp2 = setup("/player/attackUp02",gp.tileSize,gp.tileSize*2);
			attackDown1 = setup("/player/attackDown01",gp.tileSize,gp.tileSize*2);
			attackDown2 = setup("/player/attackDown02",gp.tileSize,gp.tileSize*2);
			attackLeft1 = setup("/player/attackLeft01",gp.tileSize*2,gp.tileSize);
			attackLeft2 = setup("/player/attackLeft02",gp.tileSize*2,gp.tileSize);
			attackRight1 = setup("/player/attackRight01",gp.tileSize*2,gp.tileSize);
			attackRight2 = setup("/player/attachRight02",gp.tileSize*2,gp.tileSize);
		}
		
	}
	
	public void update() {
		
		if(attacking == true) {
			attacking();
		}	
		else if(keyH.upPressed == true || keyH.downPressed == true || 
				keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed == true) {
			
			if(keyH.upPressed == true) {
				direction = "up";
				lastKeyPressed = "up";
			} 
			else if (keyH.downPressed == true) {	
				direction = "down";					
				lastKeyPressed = "down";
			} 
			else if (keyH.leftPressed == true) {	
				direction = "left";					
				lastKeyPressed = "left";
			} 
			else if (keyH.rightPressed == true) {	
				direction = "right";					
				lastKeyPressed = "right";
			}
			// here ang sprint
			
			// --- CHECK TILE COLLISION
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			// --- CHECK OBJECT COLLISION
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);
			
			// --- CHECK NPC COLLISION
			int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
			interactNPC(npcIndex);
			
			// --- CHECK MONSTER COLLISION
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			contactMonster(monsterIndex);
			
			// --- CHECK INTERACTIVE TILE COLLISION
			gp.cChecker.checkEntity(this, gp.iTile);
			
			// --- CHECK EVENT
			gp.eHandler.checkEvent();
			
			// IF COLLISION IS FALSE, PLAYER CANT MOVE
			if(collisionOn == false && keyH.enterPressed == false) {
				
				switch(direction) {
				case "up": worldY -= speed; break;
				case "down": worldY += speed; break;
				case "left": worldX -= speed; break;
				case "right": worldX += speed; break;
				}
			}
			
			// SPRINT INPUT  PLAYER SPRINT
			boolean wantsToSprint = keyH.sprintPressed;

			// STAMINA LOGIC
			if (wantsToSprint && stamina > 0 && exhausted == false) {

			    // Sprinting allowed
			    speed = defaultSpeed + 2;
			    stamina -= 2; // drain per frame

			    if (stamina <= 0) {
			        stamina = 0;
			        exhausted = true; // no more sprinting
			    }
			}

			// PLAYER ATTACK
			if(keyH.enterPressed == true && attackCanceled == false) {
				gp.playSE(5);
				attacking = true;
				spriteCounter = 0;
			}
			
			attackCanceled = false;
			
			gp.keyH.enterPressed = false;
			
			spriteCounter++;
			if(spriteCounter > 15) {
				if(spriteNum == 1) {
					spriteNum = 2;
				} 
				else if(spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
			
			if(life > maxLife) {
				life = maxLife;
			}
			if(mana > maxMana) {
				mana = maxMana;
			}
			if(life <= 0) {
				gp.gameState = gp.gameOverState;
				gp.ui.commandNum = -1;
				gp.stopMusic();
				gp.playSE(8);
			}
			
		} 
		else {
			/// IF we want to to be less creepy use a circular Queue to get the last input
			if(lastKeyPressed == "up") {
				direction = "up00";
			}
			if(lastKeyPressed == "down") {
				direction = "down00";
			}
			if(lastKeyPressed == "left") {
				direction = "left00";
			}
			if(lastKeyPressed == "right") {
				direction = "right00";
			}
			
		}
		// STAMINA REGENERATION
		if(gp.keyH.sprintPressed == false) {
		    // Not sprinting → regen
		    speed = defaultSpeed;
		    if (stamina < maxStamina) {
		        stamina += 1; // regen per frame
		    }
		    // If stamina has recovered enough, remove exhaustion
		    if (exhausted && stamina > maxStamina * 0.3) {
		        exhausted = false;
		    }
		}
		
		// PROJECTILE
		if(gp.keyH.shotKeyPressed == true && projectile.alive == false && 
				shotAvailableCounter == 30 && projectile.haveResource(this) == true) {
			
			// SET DEFAULT COORDINATES, DIRECTION AND USER
			projectile.set(worldX, worldY, direction, true, this);
			
			// SUBTRACT THE COST
			projectile.subtractResource(this);
			
			// CHECK VACANCY
			for(int i = 0; i < gp.projectile[1].length; i++) {
				if(gp.projectile[gp.currentMap][i] == null) {
					gp.projectile[gp.currentMap][i] = projectile;
					break;
				}
			}
			
			shotAvailableCounter = 0;
			gp.playSE(7);
		}
		
		if(invincible == true) {
			invincibleCounter++;
			if(invincibleCounter > 60) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
		
		if(shotAvailableCounter < 30) {
			shotAvailableCounter++;
		}
	}
	

	
	public void pickUpObject(int index) {
		
		if (index != 999) {
			
			// PICK-UP ONLY
			if(gp.obj[gp.currentMap][index].type == type_pickupOnly) {
				
				gp.obj[gp.currentMap][index].use(this);
				gp.obj[gp.currentMap][index] = null;
			}
			// OBSTACLE
			else if (gp.obj[gp.currentMap][index].type == type_obstacle) {
				
				if(gp.keyH.enterPressed == true) {
					attackCanceled = true;
					gp.obj[gp.currentMap][index].interact();
				}
			}
			// INVENTORY ITEMS
			else {
				String text;
				
				if(canObtainItem(gp.obj[gp.currentMap][index]) == true){

					gp.playSE(1);
					text = "Bro pick up a " + gp.obj[gp.currentMap][index].name + "!!";
				}
				else {
					text = "Bro! Reduce some wight man you're full as f*ck!";
				}
				gp.ui.addMessage(text);
				gp.obj[gp.currentMap][index] = null;
			}
		}
	}
	
	public void interactNPC(int index) {
		
		if(gp.keyH.enterPressed == true) {
			if (index != 999) {
				attackCanceled = true;
				gp.npc[gp.currentMap][index].speak();
			} 
		}
	}
	
	public void contactMonster(int index) {
		
		if (index != 999) {
			if(invincible == false && gp.monster[gp.currentMap][index].dying == false) {
				gp.playSE(10);
				
				int damage = gp.monster[gp.currentMap][index].attack - defense;
				if(damage < 0) {
					damage = 0;
				}
				life -= damage;
				invincible = true;
			}
		}
	}
	
	public void damageMonster(int index,Entity attacker, int attack, int knockBackPower) {
		
		if(index != 999) {
			
			if(gp.monster[gp.currentMap][index].invincible == false) {
				
				gp.playSE(6);
				if(knockBackPower > 0) {
					setKnockBack(gp.monster[gp.currentMap][index],attacker, knockBackPower);
				}
				
				int damage = attack - gp.monster[gp.currentMap][index].defense;
				if(damage < 0) {
					damage = 0;
				}
				gp.monster[gp.currentMap][index].life -= damage;
				gp.ui.addMessage(damage + " damage!");
				
				gp.monster[gp.currentMap][index].invincible = true;
				gp.monster[gp.currentMap][index].damageReaction();
				
				if(gp.monster[gp.currentMap][index].life <= 0) {
					gp.monster[gp.currentMap][index].dying = true;
					gp.ui.addMessage("Killed the " + gp.monster[gp.currentMap][index].name + "!");
					gp.ui.addMessage("Exp +" + gp.monster[gp.currentMap][index].exp);
					exp += gp.monster[gp.currentMap][index].exp;
					checkLevelUp();
				}
			}
			
		}
	}
	

	public void damageInteractiveTile(int index) {
		
		if(index != 999 && gp.iTile[gp.currentMap][index].destructible == true && 
				gp.iTile[gp.currentMap][index].isCorrectItem(this) == true && gp.iTile[gp.currentMap][index].invincible == false) {
			
			gp.iTile[gp.currentMap][index].playSE();
			gp.iTile[gp.currentMap][index].life--;
			gp.iTile[gp.currentMap][index].invincible = true;
			
			// GENERATE PARTICLES
			generateParticle(gp.iTile[gp.currentMap][index], gp.iTile[gp.currentMap][index]);
			
			if(gp.iTile[gp.currentMap][index].life == 0) {
				gp.iTile[gp.currentMap][index] = gp.iTile[gp.currentMap][index].getDestroyedForm();
			}
		}
	}
	
	public void damageProjectile(int index) {
		
		if(index != 999) {
			Entity projectile = gp.projectile[gp.currentMap][index];
			projectile.alive = false;
			generateParticle(projectile,projectile);
		}
	}
	
	public void checkLevelUp() {
		
		if(exp >= nextExp) {
			
			level++;
			exp -= nextExp;
			nextExp = nextExp*2;
			maxLife += 2;
			strength++;
			dexterity++;
			attack = getAttack();
			defense = getDefense();
			
			gp.playSE(11);
			gp.gameState = gp.dialogueState;
			
			setDialogue();
			startDialogue(this,0);
		}
	}
	
	public void selectItem() {
		
		int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol,gp.ui.playerSlotRow);
		
		if(itemIndex < inventory.size()) {
			
			Entity selectedItem = inventory.get(itemIndex);
			
			if(selectedItem.type == type_sword || selectedItem.type == type_axe ) {
				
				currentWeapon = selectedItem;
				attack = getAttack();
				getPlayerAttackImage();
			}
			if (selectedItem.type == type_shield) {
				
				currentShield = selectedItem;
				defense = getDefense();
			}
			if(selectedItem.type == type_light) {
				
				if(currentLight == selectedItem) {
					currentLight = null;
				}
				else {
					currentLight = selectedItem;
				}
				lightUpdated = true;
			}
			if (selectedItem.type == type_consumable) {

				if(selectedItem.use(this) == true) {
					if(selectedItem.amount > 1) {
						selectedItem.amount--;
					}
					else {
						inventory.remove(itemIndex);
					}
				}
			}
		}
	}
	public int searchItemInInventory(String itemName) {
		
		int itemIndex = 999;
		for(int i = 0; i < inventory.size(); i++) {
			if(inventory.get(i).name.equals(itemName)) {
				itemIndex = i;
				break;
			}
		}
		return itemIndex;
	}
	public boolean canObtainItem(Entity item) {
		
		boolean canObtain = false;
		
		Entity newItem = gp.eGenerator.getObject(item.name);
		
		// CHECK IF STACKABLE
		if(newItem.stackable == true) {
			
			int index = searchItemInInventory(newItem.name);
			
			if(index != 999) {
				inventory.get(index).amount++;
				canObtain = true;
			}
			else { // NEW ITEM NEED TO CHECK VECANCY
				if(inventory.size() != maxInventorySize) {
					inventory.add(newItem);
					canObtain = true;
				}
			}
		}
		else { // NOT STACKABLE
			
			if(inventory.size() != maxInventorySize) {
				inventory.add(newItem);
				canObtain = true;
			}
		}
		return canObtain;
	}
	
	public void draw(Graphics2D g2) {
		
		// g2.setColor(Color.WHITE);
		// g2.fillRect(x, y, gp.tileSize, gp.tileSize);
		
		BufferedImage image = null;
		
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		
		switch(direction) {
		// -- Idle ---
		case "down00": 
			if(attacking == false) { image = down00;}
			if(attacking == true) {
				if(spriteNum == 1) { image = attackDown2; }
				if(spriteNum == 2) { image = attackDown1; }
			}
			break; 
		case "up00": 
			if(attacking == false) { image = up00; }
			if(attacking == true) {
				tempScreenY = screenY - gp.tileSize;
				if(spriteNum == 1) { image = attackUp2; }
				if(spriteNum == 2) { image = attackUp1; }
			}
			break; 
		case "left00": 
			if(attacking == false) { image = left00; }
			if(attacking == true) {
				tempScreenX = screenX - gp.tileSize;
				if(spriteNum == 1) { image = attackLeft2; }
				if(spriteNum == 2) { image = attackLeft1; }
			}
			break; 
		case "right00": 
			if(attacking == false) { image = right00; }
			if(attacking == true) {
				if(spriteNum == 1) { image = attackRight2; }
				if(spriteNum == 2) { image = attackRight1; }
			}
			break; 
			
		// --- MOVEMENT ---
		case "up":
			if(attacking == false) {
				if(spriteNum == 1) { image = up1; }
				if(spriteNum == 2) { image = up2; }
			}
			if(attacking == true) {
				tempScreenY = screenY - gp.tileSize;
				if(spriteNum == 1) { image = attackUp2; }
				if(spriteNum == 2) { image = attackUp1; }
			}
			break;
		case "down":
			if(attacking == false) {
				if(spriteNum == 1) { image = down1; }
				if(spriteNum == 2) { image = down2; }
			}
			if(attacking == true) {
				if(spriteNum == 1) { image = attackDown2; }
				if(spriteNum == 2) { image = attackDown1; }
			}
			break;
		case "left":
			if(attacking == false) {
				if(spriteNum == 1) { image = left1; }
				if(spriteNum == 2) { image = left2; }
			}
			if(attacking == true) {
				tempScreenX = screenX - gp.tileSize;
				if(spriteNum == 1) { image = attackLeft2; }
				if(spriteNum == 2) { image = attackLeft1; }
			}
			break;
		case "right":
			if(attacking == false) {
				if(spriteNum == 1) { image = right1; }
				if(spriteNum == 2) { image = right2; }
			}
			if(attacking == true) {
				if(spriteNum == 1) { image = attackRight2; }
				if(spriteNum == 2) { image = attackRight1; }
			}
			break;
		}
		
		// FOR PLAYER DMG ANIMATION
		if(invincible == true) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		}
		// DRAW
		g2.drawImage(image, tempScreenX, tempScreenY, null);
		
		// RESET ALPHA
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
		// DEBUG
//		g2.setFont(new Font("Arail", Font.PLAIN, 26));
//		g2.setColor(Color.white);
//		g2.drawString("Invincible: " + invincibleCounter, 10, 400);
		
		// --- SHOW THE Solid Area of the Player == COLLISION AREA 
//		g2.setColor(Color.RED);
//		g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
	}
	
}
