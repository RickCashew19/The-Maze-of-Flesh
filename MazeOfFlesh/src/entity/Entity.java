package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool;

/**
 * Base Entity class representing all game objects (player, NPCs, monsters, items)
 * Implements core OOP principles: inheritance, encapsulation, polymorphism
 */
public class Entity {

    // ===== GAME PANEL REFERENCE =====
    GamePanel gp;  // Reference to main game panel for accessing shared resources
    
    // ===== SPRITE IMAGES =====
    public BufferedImage image, image2, image3;  // General purpose image holders
    // Movement animation sprites
    public BufferedImage up00, down00, left00, right00;  // Default/idle sprites
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;  // Walking animation sprites
    // Attack animation sprites
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, 
                         attackLeft1, attackLeft2, attackRight1, attackRight2;
    
    // ===== COLLISION AREAS =====
    public Rectangle solidArea = new Rectangle(0, 0, 64, 64);  // Collision boundary for entity
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);   // Attack hitbox area
    public int solidAreaDefaultX, solidAreaDefaultY;  // Default position of solid area
    public boolean collision = false;  // Global collision flag
    
    // ===== DIALOGUE SYSTEM =====
    public String dialogues[][] = new String[20][20];  // Dialogue lines [set][line]
    
    // ===== ENTITY INTERACTIONS =====
    public Entity attacker;          // Reference to entity that attacked this one
    public boolean canMove = true;   // Movement permission flag
    public boolean temp = false;     // Temporary state flag
    public boolean detectedPlayer = false;  // Player detection flag for AI
    
    // ===== ENTITY STATE FLAGS =====
    public int worldX, worldY;       // World coordinates in pixels
    public String direction = "down"; // Current facing direction
    public int spriteNum = 1;        // Current sprite frame (1 or 2 for animation)
    public int dialogueSet = 0;      // Current dialogue set index
    public int dialogueIndex = 0;    // Current line within dialogue set
    public boolean collisionOn = false;  // Collision detection result
    public boolean invincible = false;   // Damage immunity state
    public boolean attacking = false;    // Attack in progress
    public boolean alive = true;         // Living/dead state
    public boolean dying = false;        // Currently in death animation
    boolean hpBarOn = false;             // Health bar display flag
    public boolean onPath = false;       // Following pathfinding route
    public boolean knockBack = false;    // Being knocked back
    public String knockBackDirection;    // Direction of knockback
    public Entity loot;                  // Item dropped on death
    public boolean opened = false;       // For containers/interactables
    public boolean drawing = true;       // Render visibility flag
    
    // ===== COUNTERS/TIMERS =====
    public int spriteCounter = 0;          // Animation frame counter
    public int actionLockCounter = 0;      // AI action cooldown
    public int invincibleCounter = 0;      // Invincibility timer
    public int shotAvailableCounter = 0;   // Projectile cooldown
    public int dyingCounter = 0;           // Death animation timer
    int hpBarCounter = 0;                  // Health bar display timer
    int knockBackCounter = 0;              // Knockback duration timer
    
    // ===== CHARACTER ATTRIBUTES (RPG Stats) =====
    public String name;              // Entity identifier
    public int defaultSpeed;         // Base movement speed
    public int maxLife;              // Maximum health points
    public int life;                 // Current health points
    public int maxMana;              // Maximum mana points
    public int mana;                 // Current mana points
    public int ammo;                 // Ammunition count
    public int speed;                // Current movement speed
    public int maxStamina;           // Maximum stamina points
    public int stamina;              // Current stamina points
    
    // ===== RPG ADVANCED ATTRIBUTES =====
    public int level;                // Character level
    public int strength;             // Physical power
    public int dexterity;            // Agility/accuracy
    public int attack;               // Attack power
    public int defense;              // Defense power
    public int exp;                  // Experience points
    public int nextExp;              // Exp needed for next level
    public int coin;                 // Currency
    public int motion1_duration;     // Attack animation phase 1 duration
    public int motion2_duration;     // Attack animation phase 2 duration
    public Entity currentWeapon;     // Equipped weapon
    public Entity currentShield;     // Equipped shield
    public Entity currentLight;      // Equipped light source
    public Projectile projectile;    // Projectile reference
    
    // ===== ITEM ATTRIBUTES =====
    public ArrayList<Entity> inventory = new ArrayList<>();  // Item storage
    public final int maxInventorySize = 20;  // Inventory capacity
    public int value;                // General value field
    public int attackValue;          // Weapon attack bonus
    public int defenseValue;         // Armor/Shield defense bonus
    public String description = "? ? ?";  // Item description
    public int useCost;              // Resource cost to use (mana/stamina)
    public int price;                // Buy/sell price
    public int knockBackPower = 0;   // Knockback force applied
    public boolean stackable = false;// Can items stack in inventory?
    public int amount = 1;           // Stack size for stackable items
    public int lightRadius;          // Light source illumination range
    
    // ===== ENTITY TYPE CONSTANTS =====
    public int type;  // Entity category identifier
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_axe = 4;
    public final int type_shield = 5;
    public final int type_consumable = 6;
    public final int type_pickupOnly = 7;
    public final int type_obstacle = 8;
    public final int type_light = 9;
    
    /**
     * Constructor - Initialize entity with game panel reference
     * OOP Principle: Encapsulation - hides implementation details
     */
    public Entity(GamePanel gp) {
        this.gp = gp;
    }
    
    // ===== POSITION/COLLISION UTILITIES =====
    
    /**
     * Get left boundary of collision area in world coordinates
     * OOP Principle: Encapsulation - provides controlled access to position data
     */
    public int getLeftX() {
        return worldX + solidArea.x;
    }
    
    /**
     * Get right boundary of collision area in world coordinates
     */
    public int getRightX() {
        return worldX + solidArea.x + solidArea.width;
    }
    
    /**
     * Get top boundary of collision area in world coordinates
     */
    public int getTopY() {
        return worldY + solidArea.y;
    }
    
    /**
     * Get bottom boundary of collision area in world coordinates
     */
    public int getBottomY() {
        return worldY + solidArea.y + solidArea.height;
    }
    
    /**
     * Get current column in tile grid based on collision area
     */
    public int getCol() {
        return (worldX + solidArea.x) / gp.tileSize;
    }
    
    /**
     * Get current row in tile grid based on collision area
     */
    public int getRow() {
        return (worldY + solidArea.y) / gp.tileSize;
    }
    
    /**
     * Calculate horizontal distance to target entity
     * OOP Principle: Polymorphism - works with any Entity subclass
     */
    public int getXdistance(Entity target) {
        int xDistance = Math.abs(worldX - target.worldX);
        return xDistance;
    }
    
    /**
     * Calculate vertical distance to target entity
     */
    public int getYdistance(Entity target) {
        int yDistance = Math.abs(worldY - target.worldY);
        return yDistance;
    }
    
    /**
     * Calculate Manhattan distance in tiles to target entity
     */
    public int getTileDistance(Entity target) {
        int tileDistance = (getXdistance(target) + getYdistance(target)) / gp.tileSize;
        return tileDistance;
    }
    
    /**
     * Get target entity's column in tile grid
     */
    public int getGoalCol(Entity target) {
        int goalCol = (target.worldX + target.solidArea.x) / gp.tileSize;
        return goalCol;
    }
    
    /**
     * Get target entity's row in tile grid
     */
    public int getGoalRow(Entity target) {
        int goalRow = (target.worldY + target.solidArea.y) / gp.tileSize;
        return goalRow;
    }
    
    /**
     * Reset all counters to zero
     * OOP Principle: Encapsulation - groups related reset operations
     */
    public void resetCounter() {
        spriteCounter = 0;
        actionLockCounter = 0;
        invincibleCounter = 0;
        shotAvailableCounter = 0;
        dyingCounter = 0;
        hpBarCounter = 0;
        knockBackCounter = 0;
    }
    
    // ===== ABSTRACT METHODS (to be overridden by subclasses) =====
    // OOP Principle: Abstraction - defines interface without implementation
    
    /**
     * Set loot dropped by this entity (override in subclasses)
     */
    public void setLoot(Entity loot) {
        // To be implemented by subclasses
    }
    
    /**
     * Set entity to idle state (override in subclasses)
     */
    public void setIdle() {
        // To be implemented by subclasses
    }
    
    /**
     * Determine entity's next action (override in subclasses)
     */
    public void setAction() {
        // To be implemented by subclasses
    }
    
    /**
     * React to taking damage (override in subclasses)
     */
    public void damageReaction() {
        // To be implemented by subclasses
    }
    
    /**
     * Initiate dialogue (override in subclasses)
     */
    public void speak() {
        // To be implemented by subclasses
    }
    
    /**
     * Face the player (mirror player's direction)
     */
    public void facePlayer() {
        direction = normalizeDirection(gp.player.direction);
        switch (direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }
    
    /**
     * Start dialogue with this entity
     * OOP Principle: Encapsulation - manages dialogue state
     */
    public void startDialogue(Entity entity, int setNum) {
        gp.gameState = gp.dialogueState;
        gp.ui.npc = entity;
        dialogueSet = setNum;
    }
    
    /**
     * Handle interaction with entity (override in subclasses)
     */
    public void interact() {
        // To be implemented by subclasses
    }
    
    /**
     * Use this entity/item (override in subclasses)
     * @param entity The entity using this item
     * @return true if use was successful
     */
    public boolean use(Entity entity) {
        return false;
    }
    
    /**
     * Determine what to drop on death (override in subclasses)
     */
    public void checkDrop() {
        // To be implemented by subclasses
    }
    
    /**
     * Drop an item into the world
     * OOP Principle: Encapsulation - handles item dropping logic
     */
    public void dropItem(Entity droppedItem) {
        for (int i = 0; i < gp.obj[1].length; i++) {
            if (gp.obj[gp.currentMap][i] == null) {
                gp.obj[gp.currentMap][i] = droppedItem;
                gp.obj[gp.currentMap][i].worldX = worldX;
                gp.obj[gp.currentMap][i].worldY = worldY;
                break;
            }
        }
    }
    
    // ===== PARTICLE SYSTEM METHODS =====
    
    /**
     * Get particle color (override in subclasses)
     */
    public Color getParticleColor() {
        Color color = null;
        return color;
    }
    
    /**
     * Get particle size (override in subclasses)
     */
    public int getParticleSize() {
        int size = 0;
        return size;
    }
    
    /**
     * Get particle speed (override in subclasses)
     */
    public int getParticleSpeed() {
        int speed = 0;
        return speed;
    }
    
    /**
     * Get particle lifetime (override in subclasses)
     */
    public int getParticleMaxLife() {
        int maxLife = 0;
        return maxLife;
    }
    
    /**
     * Generate particles around a target
     * OOP Principle: Polymorphism - works with any Entity generator/target
     */
    public void generateParticle(Entity generator, Entity target) {
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();
        
        // Create particles in four directions
        Particle p1 = new Particle(gp, target, color, size, speed, maxLife, -2, -1);
        Particle p2 = new Particle(gp, target, color, size, speed, maxLife, 2, -1);
        Particle p3 = new Particle(gp, target, color, size, speed, maxLife, -2, 1);
        Particle p4 = new Particle(gp, target, color, size, speed, maxLife, 2, 1);
        
        // Add to global particle list
        gp.particleList.add(p1);
        gp.particleList.add(p2);
        gp.particleList.add(p3);
        gp.particleList.add(p4);
    }
    
    // ===== COLLISION AND MOVEMENT =====
    
    /**
     * Check for collisions with tiles, objects, and other entities
     * OOP Principle: Encapsulation - centralizes collision detection logic
     */
    public void checkCollision() {
        collisionOn = false;
        
        // Check various collision types
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        gp.cChecker.checkEntity(this, gp.iTile);
        
        // Check player collision (monster-specific)
        boolean contactPlayer = gp.cChecker.checkPlayer(this);
        if (this.type == type_monster && contactPlayer == true) {
            damagePlayer(attack);
        }
    }
    
    /**
     * Main update method called every frame
     * OOP Principle: Polymorphism - can be overridden by subclasses
     */
    public void update() {
        // Handle knockback state
        if (knockBack == true) {
            checkCollision();
            
            if (collisionOn == true) {
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            } else if (collisionOn == false) {
                // Apply knockback movement
                switch (knockBackDirection) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }
            
            knockBackCounter++;
            if (knockBackCounter == 5) {
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
        }
        // Handle attacking state
        else if (attacking == true) {
            attacking();
        }
        // Normal movement state
        else {
            setAction();  // Determine next action (AI)
            checkCollision();
            
            // Move if no collision
            if (collisionOn == false) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }
            
            // Animation frame update
            spriteCounter++;
            if (spriteCounter > 15) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
        
        // Handle invincibility frames
        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        
        // Update projectile cooldown
        if (shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }
    }
    
    // ===== AI/COMBAT METHODS =====
    
    /**
     * Check if entity should attack based on player proximity
     * OOP Principle: Encapsulation - combines distance check with random chance
     */
    public void checkAttackOrNot(int rate, int straight, int horizontal) {
        boolean targetInRange = false;
        int xDis = getXdistance(gp.player);
        int yDis = getYdistance(gp.player);
        
        // Check if player is in attack range based on direction
        switch (direction) {
            case "up":
                if (gp.player.worldY < worldY && yDis < straight && xDis < horizontal) {
                    targetInRange = true;
                }
                break;
            case "down":
                if (gp.player.worldY > worldY && yDis < straight && xDis < horizontal) {
                    targetInRange = true;
                }
                break;
            case "left":
                if (gp.player.worldX < worldX && xDis < straight && yDis < horizontal) {
                    targetInRange = true;
                }
                break;
            case "right":
                if (gp.player.worldX > worldX && xDis < straight && yDis < horizontal) {
                    targetInRange = true;
                }
                break;
        }
        
        // Random chance to attack if in range
        if (targetInRange == true) {
            int i = new Random().nextInt(rate);
            if (i == 0) {
                attacking = true;
                spriteNum = 1;
                spriteCounter = 0;
                shotAvailableCounter = 0;
            }
        }
    }
    
    /**
     * Check if entity should shoot a projectile
     * OOP Principle: Encapsulation - manages projectile firing logic
     */
    public void checkShotOrNot(int rate, int shotInterval) {
        int i = new Random().nextInt(rate);
        if (i == 0 && projectile.alive == false && shotAvailableCounter == shotInterval) {
            // Set up projectile
            projectile.set(worldX, worldY, direction, true, this);
            
            // Add to projectile array
            for (int ii = 0; ii < gp.projectile[1].length; ii++) {
                if (gp.projectile[gp.currentMap][ii] == null) {
                    gp.projectile[gp.currentMap][ii] = projectile;
                    break;
                }
            }
            shotAvailableCounter = 0;
        }
    }
    
    /**
     * Start chasing player if within detection range
     */
    public void checkStartChasingOrNot(Entity target, int distance, int rate) {
        if (getTileDistance(target) < distance) {
            int i = new Random().nextInt(rate);
            if (i == 0) {
                onPath = true;
            }
        }
    }
    
    /**
     * Stop chasing player if beyond range
     */
    public void checkStopChasingOrNot(Entity target, int distance, int rate) {
        if (getTileDistance(target) > distance) {
            int i = new Random().nextInt(rate);
            if (i == 0) {
                onPath = false;
            }
        }
    }
    
    /**
     * Get random direction for wandering AI
     * OOP Principle: Encapsulation - combines timer with random direction selection
     */
    public void getRandomDirection() {
        actionLockCounter++;
        
        // Change direction every 120 frames (~2 seconds at 60 FPS)
        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;
            
            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }
            actionLockCounter = 0;
        }
    }
    
    /**
     * Handle attack animation and collision detection
     * OOP Principle: Encapsulation - combines animation with damage logic
     */
    public void attacking() {
        spriteCounter++;
        
        // Phase 1: Wind-up animation
        if (spriteCounter <= motion1_duration) {
            spriteNum = 1;
        }
        
        // Phase 2: Active attack frames
        if (spriteCounter > motion1_duration && spriteCounter <= motion2_duration) {
            spriteNum = 2;
            
            // Save original position and collision area
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;
            
            // Adjust position for attack hitbox
            direction = normalizeDirection(direction);
            switch (direction) {
                case "up":
                    worldY -= attackArea.height;
                    break;
                case "down":
                    worldY += attackArea.height;
                    break;
                case "left":
                    worldX -= attackArea.width;
                    break;
                case "right":
                    worldX += attackArea.width;
                    break;
            }
            
            // Temporarily use attack area as collision area
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;
            
            // Check for hits based on entity type
            if (type == type_monster) {
                if (gp.cChecker.checkPlayer(this) == true) {
                    damagePlayer(attack);
                }
            } else { // Player attacking
                // Check monster collision
                int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
                gp.player.damageMonster(monsterIndex, this, attack, currentWeapon.knockBackPower);
                
                // Check interactive tile collision
                int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
                gp.player.damageInteractiveTile(iTileIndex);
                
                // Check projectile collision
                int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
                gp.player.damageProjectile(projectileIndex);
            }
            
            // Restore original position and collision area
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        
        // Phase 3: Reset after attack
        if (spriteCounter > motion2_duration) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }
    
    /**
     * Apply damage to player
     * OOP Principle: Encapsulation - handles damage calculation and invincibility
     */
    public void damagePlayer(int attack) {
        if (gp.player.invincible == false) {
            gp.playSE(10);  // Play damage sound effect
            
            // Calculate damage with defense reduction
            int damage = attack - gp.player.defense;
            if (damage < 0) {
                damage = 0;
            }
            gp.player.life -= damage;
            
            gp.player.invincible = true;
        }
    }
    
    /**
     * Apply knockback to target entity
     * OOP Principle: Polymorphism - works with any Entity type
     */
    public void setKnockBack(Entity target, Entity attacker, int knockBackPower) {
        this.attacker = attacker;
        target.knockBackDirection = attacker.normalizeDirection(attacker.direction);
        target.speed += knockBackPower;
        target.knockBack = true;
    }
    
    // ===== RENDERING METHODS =====
    
    /**
     * Draw entity on screen
     * OOP Principle: Polymorphism - can be overridden for custom rendering
     */
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        
        // Culling: Only draw if entity is within viewport
        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
                && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
                && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
            
            int tempScreenX = screenX;
            int tempScreenY = screenY;
            
            // Select appropriate sprite based on state and direction
            switch (direction) {
                case "up":
                    if (attacking == false) {
                        image = (spriteNum == 1) ? up1 : up2;
                    }
                    if (attacking == true) {
                        tempScreenY = screenY - gp.tileSize;  // Offset for attack animation
                        image = (spriteNum == 1) ? attackUp2 : attackUp1;
                    }
                    break;
                case "down":
                    if (attacking == false) {
                        image = (spriteNum == 1) ? down1 : down2;
                    }
                    if (attacking == true) {
                        image = (spriteNum == 1) ? attackDown2 : attackDown1;
                    }
                    break;
                case "left":
                    if (attacking == false) {
                        image = (spriteNum == 1) ? left1 : left2;
                    }
                    if (attacking == true) {
                        tempScreenX = screenX - gp.tileSize;  // Offset for attack animation
                        image = (spriteNum == 1) ? attackLeft2 : attackLeft1;
                    }
                    break;
                case "right":
                    if (attacking == false) {
                        image = (spriteNum == 1) ? right1 : right2;
                    }
                    if (attacking == true) {
                        image = (spriteNum == 1) ? attackRight2 : attackRight1;
                    }
                    break;
            }
            
            // Draw monster health bar if needed
            if (type == 2 && hpBarOn == true) {
                double oneScale = (double) gp.tileSize / maxLife;
                double hpBarValue = oneScale * life;
                
                g2.setColor(new Color(35, 35, 35));  // Background
                g2.fillRect(screenX - 1, screenY - 1, gp.tileSize + 2, 12);
                
                g2.setColor(new Color(255, 0, 30));  // Health fill
                g2.fillRect(screenX, screenY, (int) hpBarValue, 10);
                
                hpBarCounter++;
                if (hpBarCounter > 600) {
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }
            
            // Apply invincibility flicker effect
            if (invincible == true) {
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(g2, 0.5f);
            }
            
            // Handle death animation
            if (dying == true) {
                dyingAnimation(g2);
            }
            
            // Draw the entity
            g2.drawImage(image, tempScreenX, tempScreenY, null);
            
            // Reset alpha to normal
            changeAlpha(g2, 1f);
        }
        
        // Debug: Draw collision area (commented out in production)
        // g2.setColor(Color.RED);
        // g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
    }
    
    /**
     * Handle dying animation (flicker effect)
     * OOP Principle: Encapsulation - isolates death animation logic
     */
    public void dyingAnimation(Graphics2D g2) {
        dyingCounter++;
        int i = 5;
        
        // Create flicker effect by changing alpha at intervals
        if (dyingCounter <= i) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i && dyingCounter <= i * 2) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 2 && dyingCounter <= i * 3) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i * 3 && dyingCounter <= i * 4) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 4 && dyingCounter <= i * 5) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i * 5 && dyingCounter <= i * 6) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 6 && dyingCounter <= i * 7) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i * 7 && dyingCounter <= i * 8) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 8) {
            alive = false;
        }
    }
    
    /**
     * Change alpha (transparency) of graphics context
     */
    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }
    
    // ===== UTILITY METHODS =====
    
    /**
     * Normalize direction strings (convert "up00" to "up", etc.)
     * OOP Principle: Encapsulation - provides consistent direction handling
     */
    public String normalizeDirection(String direction) {
        if (direction == "up00")
            direction = "up";
        if (direction == "down00")
            direction = "down";
        if (direction == "left00")
            direction = "left";
        if (direction == "right00")
            direction = "right";
        
        return direction;
    }
    
    /**
     * Get screen X coordinate (for camera-relative positioning)
     */
    public int getScreenX() {
        return worldX - gp.player.worldX + gp.player.screenX;
    }
    
    /**
     * Get screen Y coordinate (for camera-relative positioning)
     */
    public int getScreenY() {
        return worldY - gp.player.worldY + gp.player.screenY;
    }
    
    /**
     * Load and scale an image from resources
     * OOP Principle: Encapsulation - combines loading and scaling operations
     */
    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return image;
    }
    
    // ===== PATHFINDING METHODS =====
    
    /**
     * Use A* pathfinding to move toward a goal position
     * OOP Principle: Encapsulation - integrates pathfinding with movement logic
     */
    public void searchPath(int goalCol, int goalRow, boolean canMove) {
        int startCol = (worldX + solidArea.x) / gp.tileSize;
        int startRow = (worldY + solidArea.y) / gp.tileSize;
        
        // Set up pathfinder
        gp.pFinder.setNode(startCol, startRow, goalCol, goalRow);
        
        if (gp.pFinder.search() == true) {
            // Get next position from path
            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;
            
            // Calculate entity boundaries
            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;
            
            if (canMove) {
                // Determine direction based on relative position to next tile
                if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                    direction = "up";
                } else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                    direction = "down";
                } else if (enTopY >= nextY && enBottomY < nextY + gp.tileSize) {
                    // Move horizontally
                    if (enLeftX > nextX) {
                        direction = "left";
                    }
                    if (enLeftX < nextX) {
                        direction = "right";
                    }
                } else if (enTopY > nextY && enLeftX > nextX) {
                    // Diagonal: prioritize up, fallback to left
                    direction = "up";
                    checkCollision();
                    if (collisionOn == true) {
                        direction = "left";
                    }
                } else if (enTopY > nextY && enLeftX < nextX) {
                    // Diagonal: prioritize up, fallback to right
                    direction = "up";
                    checkCollision();
                    if (collisionOn == true) {
                        direction = "right";
                    }
                } else if (enTopY < nextY && enLeftX > nextX) {
                    // Diagonal: prioritize down, fallback to left
                    direction = "down";
                    checkCollision();
                    if (collisionOn == true) {
                        direction = "left";
                    }
                } else if (enTopY < nextY && enLeftX < nextX) {
                    // Diagonal: prioritize down, fallback to right
                    direction = "down";
                    checkCollision();
                    if (collisionOn == true) {
                        direction = "right";
                    }
                }
            }
        }
    }
    
    /**
     * Remove all entities of a specific name from the target array
     * OOP Principle: Polymorphism - works with any Entity array
     */
    public void removeAllDetected(Entity target[][], String targetName) {
        // Iterate through all entities in the current map
        for (int i = 0; i < target[gp.currentMap].length; i++) {
            if (target[gp.currentMap][i] != null) {
                // Check if the entity matches the target name
                if (target[gp.currentMap][i].name.equals(targetName)) {
                    // Remove the entity by setting it to null
                    target[gp.currentMap][i] = null;
                }
            }
        }
    }
    
    /**
     * Detect if a specific entity is in front of this entity
     * OOP Principle: Encapsulation - combines direction checking with entity detection
     */
    public int getDetected(Entity user, Entity target[][], String targetName) {
        int index = 999;  // Default "not found" value
        
        // Calculate position in front of entity based on direction
        int nextWorldX = user.getLeftX();
        int nextWorldY = user.getTopY();
        
        user.direction = normalizeDirection(user.direction);
        switch (user.direction) {
            case "up":
                nextWorldY = user.getTopY() - gp.player.speed;
                break;
            case "down":
                nextWorldY = user.getBottomY() + gp.player.speed;
                break;
            case "left":
                nextWorldX = user.getLeftX() - gp.player.speed;
                break;
            case "right":
                nextWorldX = user.getRightX() + gp.player.speed;
                break;
        }
        
        // Convert to tile coordinates
        int col = nextWorldX / gp.tileSize;
        int row = nextWorldY / gp.tileSize;
        
        // Search for target entity at that position
        for (int i = 0; i < target[1].length; i++) {
            if (target[gp.currentMap][i] != null) {
                if (target[gp.currentMap][i].getCol() == col 
                        && target[gp.currentMap][i].getRow() == row
                        && target[gp.currentMap][i].name.equals(targetName)) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }
}