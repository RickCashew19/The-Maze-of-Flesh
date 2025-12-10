package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.GamePanel;
import main.KeyHandler;
import object.OBJ_FLESH_WALL;
import object.OBJ_Lantern;
import object.OBJ_Shards;
import object.OBJ_worm;

/**
 * Player class extending Entity - represents the player character
 * OOP Principle: Inheritance - Player inherits all properties and behaviors from Entity
 * Data Structures: ArrayList for inventory, arrays for object management
 */
public class Player extends Entity {

    // ===== INPUT HANDLER =====
    KeyHandler keyH;  // Reference to keyboard input handler
    
    // ===== PLAYER STATE VARIABLES =====
    String lastKeyPressed = "down00";  // Tracks last movement direction for idle animation
    public final int screenX;          // Screen X coordinate (fixed camera position)
    public final int screenY;          // Screen Y coordinate (fixed camera position)
    int standCounter = 0;              // Counter for standing/idle time
    boolean textOn = true;             // Controls text display state
    int textCounter = 0;               // Counter for text display timing
    public boolean attackCanceled = false;  // Flag to cancel attack animations
    public boolean lightUpdated = false;    // Flag for light source changes
    public boolean exhausted = false;       // Stamina exhaustion state
    boolean godMode = false;                // Debug/invincibility mode
    
    // ===== SHARD SYSTEM =====
    public int shardCount;             // Current number of shards collected
    public int maxShard = 3;           // Maximum shards needed for special ability

    /**
     * Constructor for Player class
     * OOP Principle: Encapsulation - Initializes player with controlled access to dependencies
     */
    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);  // Call parent Entity constructor
        this.keyH = keyH;

        // Calculate screen position (center of screen)
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        // Define collision area (smaller than tile for smoother movement)
        solidArea = new Rectangle();
        solidArea.x = 16;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 46;

        setDefaultValues();
    }

    /**
     * Set default player attributes and starting values
     * OOP Principle: Encapsulation - Groups initialization logic together
     */
    public void setDefaultValues() {
        // Starting position
        worldX = gp.tileSize * 16;
        worldY = gp.tileSize * 15;
        
        // Movement attributes
        defaultSpeed = 5;
        speed = defaultSpeed;
        direction = "down00";

        // Player status attributes
        maxLife = 8;
        life = maxLife;
        maxStamina = 100;
        stamina = maxStamina;
        dexterity = 0;
        
        // Initialize equipment
        projectile = new OBJ_worm(gp);
        currentLight = new OBJ_Lantern(gp);
        defense = getDefense();

        // Load resources
        getImage();
        setItems();
        setDialogue();
    }

    /**
     * Set default respawn positions
     */
    public void setDefaultPositions() {
        worldX = gp.tileSize * 11;
        worldY = gp.tileSize * 7;
        direction = "down00";
    }

    /**
     * Initialize dialogue system
     */
    private void setDialogue() {
        dialogues[0][0] = "You are level " + level + " now!\n" + "You feel Stronger!!";
    }

    /**
     * Restore player status to full
     * OOP Principle: Abstraction - Simple method hiding complex state restoration
     */
    public void restorStatus() {
        life = maxLife;
        mana = maxMana;
        speed = defaultSpeed;
        invincible = false;
        attacking = false;
        knockBack = false;
        lightUpdated = true;
    }

    /**
     * Initialize player inventory with starting items
     * Data Structure: ArrayList - Dynamic collection for inventory items
     */
    private void setItems() {
        inventory.clear();  // Clear existing items
        inventory.add(currentLight);  // Add lantern as starting item
    }

    /**
     * Calculate attack value based on strength
     */
    private int getAttack() {
        return attack = strength;  // In future: * currentWeapon.attackValue;
    }

    /**
     * Calculate defense value based on dexterity
     */
    private int getDefense() {
        return defense = dexterity;  // In future: * currentShield.defenseValue;
    }

    /**
     * Load player sprite images
     * OOP Principle: Encapsulation - Handles all image loading in one method
     */
    private void getImage() {
        // IDLE SPRITES
        up00 = setup("/player/Up01", gp.tileSize, gp.tileSize);
        down00 = setup("/player/Down01", gp.tileSize, gp.tileSize);
        left00 = setup("/player/Left01", gp.tileSize, gp.tileSize);
        right00 = setup("/player/Right01", gp.tileSize, gp.tileSize);

        // MOVEMENT ANIMATION SPRITES
        up1 = setup("/player/Up02", gp.tileSize, gp.tileSize);
        up2 = setup("/player/Up03", gp.tileSize, gp.tileSize);

        down1 = setup("/player/Down02", gp.tileSize, gp.tileSize);
        down2 = setup("/player/Down03", gp.tileSize, gp.tileSize);

        left1 = setup("/player/Left02", gp.tileSize, gp.tileSize);
        left2 = setup("/player/Left03", gp.tileSize, gp.tileSize);

        right1 = setup("/player/Right02", gp.tileSize, gp.tileSize);
        right2 = setup("/player/Right03", gp.tileSize, gp.tileSize);
    }

    /**
     * Placeholder for attack animation images
     */
    private void getPlayerAttackImage() {
        // To be implemented when attack animations are added
    }

    /**
     * Main update method called every frame
     * OOP Principle: Polymorphism - Overrides Entity.update() with player-specific logic
     * Data Structure: Arrays - gp.obj[], gp.npc[], gp.monster[] for entity management
     */
    public void update() {
        // Monitor shard collection system
        monitorShards();

        // Handle attack state
        if (attacking == true) {
            attacking();
        } 
        // Handle movement and interaction input
        else if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true
                || keyH.rightPressed == true || keyH.enterPressed == true) {

            // Set direction based on input
            if (keyH.upPressed == true) {
                direction = "up";
                lastKeyPressed = "up";
            } else if (keyH.downPressed == true) {
                direction = "down";
                lastKeyPressed = "down";
            } else if (keyH.leftPressed == true) {
                direction = "left";
                lastKeyPressed = "left";
            } else if (keyH.rightPressed == true) {
                direction = "right";
                lastKeyPressed = "right";
            }
            
            // Check sprint input
            boolean wantsToSprint = keyH.sprintPressed;

            // Check collisions with different entity types
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // Object collision and pickup
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // NPC collision and interaction
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            // Monster collision and damage
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            // Interactive tile collision
            gp.cChecker.checkEntity(this, gp.iTile);

            // Event system check
            gp.eHandler.checkEvent();

            // Apply movement if no collision
            if (collisionOn == false && keyH.enterPressed == false) {
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

            // STAMINA SYSTEM: Handle sprinting
            if (wantsToSprint && stamina > 0 && exhausted == false) {
                // Apply sprint speed
                speed = defaultSpeed + 4;
                stamina -= 3;  // Drain stamina per frame
                
                if (stamina <= 0) {
                    stamina = 0;
                    exhausted = true;  // Enter exhaustion state
                    speed = defaultSpeed;
                }
            }

            // Reset enter key state
            gp.keyH.enterPressed = false;

            // Update animation frames
            spriteCounter++;
            if (spriteCounter > 15) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                    gp.playSE(18);  // Play footstep sound
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }

        } 
        // Handle idle state - use last direction for idle animation
        else {
            // Set idle direction based on last movement
            switch (lastKeyPressed) {
                case "up":
                    direction = "up00";
                    break;
                case "down":
                    direction = "down00";
                    break;
                case "left":
                    direction = "left00";
                    break;
                case "right":
                    direction = "right00";
                    break;
            }
        }
        
        // Clamp health and mana to maximum values
        if (life > maxLife) {
            life = maxLife;
        }
        if (mana > maxMana) {
            mana = maxMana;
        }
        
        // Check for death
        if (life <= 0) {
            gp.gameState = gp.gameOverState;
            gp.ui.commandNum = -1;
            gp.stopMusic();
            gp.playSE(8);  // Play death sound
        }
        
        // STAMINA REGENERATION: Recover when not sprinting
        if (gp.keyH.sprintPressed == false) {
            speed = defaultSpeed;
            if (stamina < maxStamina) {
                stamina += 1;  // Regenerate stamina per frame
            }
            // Recover from exhaustion if stamina is sufficiently restored
            if (exhausted && stamina > maxStamina * 0.3) {
                exhausted = false;
            }
        }

        // PROJECTILE SYSTEM: Handle shooting
        if (gp.keyH.shotKeyPressed == true && projectile.alive == false && shotAvailableCounter == 30
                && projectile.haveResource(this) == true) {

            // Configure projectile
            projectile.set(worldX, worldY, direction, true, this);
            projectile.subtractResource(this);  // Deduct resource cost

            // Add to projectile array (find empty slot)
            for (int i = 0; i < gp.projectile[1].length; i++) {
                if (gp.projectile[gp.currentMap][i] == null) {
                    gp.projectile[gp.currentMap][i] = projectile;
                    break;
                }
            }

            shotAvailableCounter = 0;
            gp.playSE(7);  // Play shooting sound
        }

        // INVINCIBILITY SYSTEM: Handle god mode and normal invincibility frames
        if (godMode == true) {
            invincible = true;  // Permanent invincibility for debug
        } else {
            if (invincible == true) {
                invincibleCounter++;
                if (invincibleCounter > 60) {
                    invincible = false;
                    invincibleCounter = 0;
                }
            }
        }

        // Update projectile cooldown
        if (shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }
    }

    /**
     * Handle object pickup interactions
     * OOP Principle: Polymorphism - Uses Entity.type to determine different behaviors
     */
    public void pickUpObject(int index) {
        if (index != 999) {  // 999 means "not found"
            Entity obj = gp.obj[gp.currentMap][index];
            
            switch (obj.type) {
                case type_pickupOnly:
                    // Use immediately and remove
                    obj.use(this);
                    gp.obj[gp.currentMap][index] = null;
                    break;
                    
                case type_obstacle:
                    // Interact on enter press
                    if (gp.keyH.enterPressed == true) {
                        attackCanceled = true;
                        obj.interact();
                    }
                    break;
                    
                default:
                    // Regular inventory items
                    String text;
                    if (canObtainItem(obj) == true) {
                        gp.playSE(1);  // Pickup sound
                        text = "You picked up a " + obj.name + "!!";
                    } else {
                        text = "Bro! Reduce some wight man you're full!";
                    }
                    gp.ui.addMessage(text);
                    gp.obj[gp.currentMap][index] = null;
                    break;
            }
        }
    }

    /**
     * Handle NPC interactions
     */
    public void interactNPC(int index) {
        if (gp.keyH.enterPressed == true) {
            if (index != 999) {
                attackCanceled = true;
                gp.npc[gp.currentMap][index].speak();  // Trigger NPC dialogue
            }
        }
    }

    /**
     * Handle monster contact (taking damage)
     */
    public void contactMonster(int index) {
        if (index != 999) {
            Entity monster = gp.monster[gp.currentMap][index];
            if (invincible == false && monster.dying == false) {
                gp.playSE(10);  // Damage sound
                
                // Calculate damage with defense reduction
                int damage = monster.attack - defense;
                if (damage < 0) {
                    damage = 0;
                }
                life -= damage;
                invincible = true;  // Start invincibility frames
            }
        }
    }

    /**
     * Deal damage to monster
     * OOP Principle: Encapsulation - Handles all damage calculation and effects
     */
    public void damageMonster(int index, Entity attacker, int attack, int knockBackPower) {
        if (index != 999) {
            Entity monster = gp.monster[gp.currentMap][index];
            
            if (monster.invincible == false) {
                gp.playSE(6);  // Attack hit sound
                
                // Apply knockback if weapon has knockback power
                if (knockBackPower > 0) {
                    setKnockBack(monster, attacker, knockBackPower);
                }

                // Calculate damage
                int damage = attack - monster.defense;
                if (damage < 0) {
                    damage = 0;
                }
                monster.life -= damage;
                gp.ui.addMessage(damage + " damage!");

                monster.invincible = true;
                monster.damageReaction();  // Trigger monster's reaction

                // Check for monster death
                if (monster.life <= 0) {
                    monster.dying = true;
                    gp.ui.addMessage("Killed the " + monster.name + "!");
                    gp.ui.addMessage("Exp +" + monster.exp);
                    exp += monster.exp;
                    checkLevelUp();  // Check if player levels up
                }
            }
        }
    }

    /**
     * Damage interactive/destructible tiles
     */
    public void damageInteractiveTile(int index) {
        if (index != 999 && gp.iTile[gp.currentMap][index].destructible == true
                && gp.iTile[gp.currentMap][index].isCorrectItem(this) == true
                && gp.iTile[gp.currentMap][index].invincible == false) {

            Entity tile = gp.iTile[gp.currentMap][index];
            tile.life--;
            tile.invincible = true;

            // Generate destruction particles
            generateParticle(tile, tile);
        }
    }

    /**
     * Destroy projectile on contact
     */
    public void damageProjectile(int index) {
        if (index != 999) {
            Entity projectile = gp.projectile[gp.currentMap][index];
            projectile.alive = false;
            generateParticle(projectile, projectile);  // Create destruction effect
        }
    }

    /**
     * Check and handle level up
     * OOP Principle: Encapsulation - Groups all level-up logic
     */
    private void checkLevelUp() {
        if (exp >= nextExp) {
            // Level up attributes
            level++;
            exp -= nextExp;
            nextExp = nextExp * 2;  // Exponential exp requirement
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();

            gp.playSE(11);  // Level up sound
            gp.gameState = gp.dialogueState;

            // Update dialogue and trigger level up message
            setDialogue();
            startDialogue(this, 0);
        }
    }

    /**
     * Select and use item from inventory
     * OOP Principle: Polymorphism - Different item types have different behaviors
     * Data Structure: ArrayList - Inventory item management
     */
    public void selectItem() {
        int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);

        if (itemIndex < inventory.size()) {
            Entity selectedItem = inventory.get(itemIndex);

            switch (selectedItem.type) {
                case type_sword:
                case type_axe:
                    // Equip weapon
                    currentWeapon = selectedItem;
                    attack = getAttack();
                    getPlayerAttackImage();  // Update attack sprites
                    break;
                    
                case type_shield:
                    // Equip shield
                    currentShield = selectedItem;
                    defense = getDefense();
                    break;
                    
                case type_light:
                    // Toggle light source
                    if (currentLight == selectedItem) {
                        currentLight = null;  // Unequip
                    } else {
                        currentLight = selectedItem;  // Equip
                    }
                    lightUpdated = true;
                    break;
                    
                case type_consumable:
                    // Use consumable
                    if (selectedItem.use(this) == true) {
                        if (selectedItem.amount > 1) {
                            selectedItem.amount--;  // Reduce stack
                        } else {
                            inventory.remove(itemIndex);  // Remove if last item
                        }
                    }
                    break;
            }
        }
    }

    /**
     * Search for item by name in inventory
     * Data Structure: Linear search through ArrayList
     * @param itemName Name of item to find
     * @return Index if found, 999 if not found
     */
    public int searchItemInInventory(String itemName) {
        int itemIndex = 999;
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).name.equals(itemName)) {
                itemIndex = i;
                break;
            }
        }
        return itemIndex;
    }

    /**
     * Check if player can obtain an item (inventory management)
     * OOP Principle: Encapsulation - Handles inventory capacity and stacking logic
     * Data Structure: ArrayList - Dynamic inventory with stackable items
     */
    public boolean canObtainItem(Entity item) {
        boolean canObtain = false;
        Entity newItem = gp.eGenerator.getObject(item.name);

        // STACKABLE ITEMS
        if (newItem.stackable == true) {
            int index = searchItemInInventory(newItem.name);
            
            if (index != 999) {
                // Add to existing stack
                inventory.get(index).amount++;
                canObtain = true;
            } else {
                // New stackable item - check inventory space
                if (inventory.size() != maxInventorySize) {
                    inventory.add(newItem);
                    canObtain = true;
                }
            }
        } 
        // NON-STACKABLE ITEMS
        else {
            if (inventory.size() != maxInventorySize) {
                inventory.add(newItem);
                canObtain = true;
            }
        }
        return canObtain;
    }
    
    /**
     * Monitor shard collection and trigger special effects when threshold reached
     * Data Structure: Uses ArrayList inventory to track shard count
     */
    public void monitorShards() {
        int shardIndex = searchItemInInventory(OBJ_Shards.objName);
       
        // Update shard count
        if(shardIndex != 999) shardCount = inventory.get(shardIndex).amount;
        
        // Check if enough shards collected
        if (shardIndex != 999 && inventory.get(shardIndex).amount >= maxShard) {
            // Remove consumed shards
            inventory.get(shardIndex).amount -= maxShard;
            shardCount = 0;
            
            // Remove item if stack is empty
            if (inventory.get(shardIndex).amount <= 0) {
                inventory.remove(shardIndex);
            }
            
            // Trigger effects
            gp.playSE(15);  // Special ability sound
            removeAllFleshWalls();  // Remove obstacles
        }
    }
    
    /**
     * Remove all flesh wall obstacles from current map
     * Data Structure: Array - Iterates through object array
     */
    private void removeAllFleshWalls() {
        for (int i = 0; i < gp.obj[gp.currentMap].length; i++) {
            if (gp.obj[gp.currentMap][i] != null && 
                gp.obj[gp.currentMap][i].name.equals(OBJ_FLESH_WALL.objName)) {
                gp.obj[gp.currentMap][i] = null;
            }
        }
    }

    /**
     * Draw player on screen
     * OOP Principle: Polymorphism - Overrides Entity.draw() with player-specific rendering
     */
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        // Select sprite based on direction and state
        switch (direction) {
            // IDLE STATES (with "00" suffix)
            case "down00":
                image = (attacking == false) ? down00 : 
                       (spriteNum == 1) ? attackDown2 : attackDown1;
                break;
            case "up00":
                if (attacking == false) {
                    image = up00;
                } else {
                    tempScreenY = screenY - gp.tileSize;
                    image = (spriteNum == 1) ? attackUp2 : attackUp1;
                }
                break;
            case "left00":
                if (attacking == false) {
                    image = left00;
                } else {
                    tempScreenX = screenX - gp.tileSize;
                    image = (spriteNum == 1) ? attackLeft2 : attackLeft1;
                }
                break;
            case "right00":
                image = (attacking == false) ? right00 : 
                       (spriteNum == 1) ? attackRight2 : attackRight1;
                break;

            // MOVEMENT STATES
            case "up":
                if (attacking == false) {
                    image = (spriteNum == 1) ? up1 : up2;
                } else {
                    tempScreenY = screenY - gp.tileSize;
                    image = (spriteNum == 1) ? attackUp2 : attackUp1;
                }
                break;
            case "down":
                if (attacking == false) {
                    image = (spriteNum == 1) ? down1 : down2;
                } else {
                    image = (spriteNum == 1) ? attackDown2 : attackDown1;
                }
                break;
            case "left":
                if (attacking == false) {
                    image = (spriteNum == 1) ? left1 : left2;
                } else {
                    tempScreenX = screenX - gp.tileSize;
                    image = (spriteNum == 1) ? attackLeft2 : attackLeft1;
                }
                break;
            case "right":
                if (attacking == false) {
                    image = (spriteNum == 1) ? right1 : right2;
                } else {
                    image = (spriteNum == 1) ? attackRight2 : attackRight1;
                }
                break;
        }

        // Apply invincibility flicker effect
        if (invincible == true) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        }
        
        // Draw player if visible
        if (drawing == true) {
            g2.drawImage(image, tempScreenX, tempScreenY, null);
        }

        // Reset transparency
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
    
    /**
     * Check if player is looking at a target entity
     * OOP Principle: Encapsulation - Calculates line of sight based on player's facing direction
     */
    public boolean isLookingAt(Entity target) {
        // Calculate vector from player to target
        int dx = target.worldX - this.worldX;
        int dy = target.worldY - this.worldY;
        
        // Determine cardinal direction to target
        String directionToTarget;
        
        // Compare absolute differences to determine primary direction
        if (Math.abs(dx) > Math.abs(dy)) {
            // Target is more horizontal
            directionToTarget = (dx > 0) ? "right" : "left";
        } else {
            // Target is more vertical
            directionToTarget = (dy > 0) ? "down" : "up";
        }
        
        // Normalize player's direction (remove "00" suffix for idle states)
        String playerFacing = this.direction;
        if (playerFacing.endsWith("00")) {
            playerFacing = playerFacing.substring(0, playerFacing.length() - 2);
        }
        
        // Check if player is facing the target
        return playerFacing.equals(directionToTarget);
    }
}