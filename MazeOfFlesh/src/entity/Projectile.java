package entity;

import main.GamePanel;

/**
 * Projectile class - Represents any flying/hurled object in the game
 * OOP Principle: Inheritance - Extends Entity for basic entity properties
 * Design Pattern: Strategy - Different collision behavior based on user type
 * Data Structure: Reference-based ownership system
 */
public class Projectile extends Entity {

    // ===== PROJECTILE PROPERTIES =====
    Entity user;  // Reference to entity that fired this projectile (owner)
    
    /**
     * Constructor for Projectile
     * OOP Principle: Inheritance - Initializes as Entity with GamePanel reference
     * @param gp GamePanel reference for game systems access
     */
    public Projectile(GamePanel gp) {
        super(gp);
    }
    
    /**
     * Initialize projectile with starting parameters
     * OOP Principle: Encapsulation - Groups initialization parameters
     * @param worldX Starting X position in world coordinates
     * @param worldY Starting Y position in world coordinates
     * @param direction Firing direction (up, down, left, right)
     * @param alive Initial alive state
     * @param user Entity that fired this projectile (owner)
     */
    public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;      // Store reference to firing entity
        this.life = this.maxLife;  // Reset life/timer for projectile
    }
    
    /**
     * Update projectile state each frame
     * OOP Principle: Polymorphism - Overrides Entity.update() with projectile-specific behavior
     * Algorithm: 
     * 1. Check collisions based on owner type
     * 2. Move in fired direction
     * 3. Update animation and lifetime
     */
    public void update() {
        // COLLISION CHECKING BASED ON PROJECTILE OWNER
        // Player-fired projectiles: Check for monster collisions
        if (user == gp.player) {
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            if (monsterIndex != 999) {  // 999 means "no collision"
                // Damage monster and create hit effect
                gp.player.damageMonster(monsterIndex, this, attack, knockBackPower);
                generateParticle(user.projectile, gp.monster[gp.currentMap][monsterIndex]);
                alive = false;  // Destroy projectile on hit
            }
        } 
        // Enemy-fired projectiles: Check for player collisions
        else if (user != gp.player) {
            // Special handling for rockworm projectiles (piercing)
            if (user != gp.rWOm) {
                boolean contactPlayer = gp.cChecker.checkPlayer(this);
                if (gp.player.invincible == false && contactPlayer == true) {
                    damagePlayer(attack);  // Apply damage to player
                    generateParticle(user.projectile, gp.player);
                    alive = false;  // Destroy projectile on hit
                }
            } else {
                // Rockworm projectiles don't disappear on hit (piercing)
                boolean contactPlayer = gp.cChecker.checkPlayer(this);
                if (gp.player.invincible == false && contactPlayer == true) {
                    damagePlayer(attack);
                    generateParticle(user.projectile, gp.player);
                    // Note: Projectile continues after hitting player
                }
            }
        }
        
        // MOVEMENT: Update position based on direction
        direction = normalizeDirection(direction);  // Ensure direction is normalized
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
        
        // LIFETIME MANAGEMENT: Projectiles have limited duration
        life--;
        if (life <= 0) {
            alive = false;  // Destroy projectile when lifetime expires
        }
        
        // ANIMATION: Update sprite animation frame
        spriteCounter++;
        if (spriteCounter > 12) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }
    
    /**
     * Check if firing entity has resources to fire this projectile
     * OOP Principle: Abstraction - Template method to be implemented by subclasses
     * @param user Entity attempting to fire
     * @return true if entity has required resources (mana, ammo, etc.)
     */
    public boolean haveResource(Entity user) {
        boolean haveResource = false;
        // To be implemented by specific projectile types
        return haveResource;
    }
    
    /**
     * Deduct resources from firing entity
     * OOP Principle: Abstraction - Template method to be implemented by subclasses
     * @param user Entity that fired the projectile
     */
    public void subtractResource(Entity user) {
        // To be implemented by specific projectile types
        // Example: player.mana -= manaCost;
    }
}