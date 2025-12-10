package entity;

import main.GamePanel;

/**
 * PlayerDummy class - A placeholder player entity for testing or NPC purposes
 * OOP Principle: Inheritance - Extends Entity class, inheriting all properties
 * Design Pattern: Template Method - Uses parent Entity's setup() method for loading images
 */
public class PlayerDummy extends Entity {

    // ===== CONSTANTS =====
    public static final String npcName = "Dummy";  // Class constant for entity name
    
    /**
     * Constructor for PlayerDummy
     * OOP Principle: Inheritance - Calls superclass constructor
     * @param gp GamePanel reference for accessing game systems
     */
    public PlayerDummy(GamePanel gp) {
        super(gp);  // Call parent Entity constructor
        
        name = npcName;  // Set entity name using class constant
        getImage();      // Load sprite images
    }
    
    /**
     * Load sprite images for the dummy player
     * OOP Principle: Encapsulation - Image loading is handled internally
     * Method uses inherited setup() method from Entity class
     */
    private void getImage() {
        // IDLE SPRITES (stationary poses)
        up00 = setup("/player/up00", gp.tileSize, gp.tileSize);
        down00 = setup("/player/down00", gp.tileSize, gp.tileSize);
        left00 = setup("/player/left00", gp.tileSize, gp.tileSize);
        right00 = setup("/player/right00", gp.tileSize, gp.tileSize);

        // MOVEMENT ANIMATION SPRITES (walking poses)
        up1 = setup("/player/up01", gp.tileSize, gp.tileSize);
        up2 = setup("/player/up02", gp.tileSize, gp.tileSize);

        down1 = setup("/player/down01", gp.tileSize, gp.tileSize);
        down2 = setup("/player/down02", gp.tileSize, gp.tileSize);

        left1 = setup("/player/left01", gp.tileSize, gp.tileSize);
        left2 = setup("/player/left02", gp.tileSize, gp.tileSize);

        right1 = setup("/player/right01", gp.tileSize, gp.tileSize);
        right2 = setup("/player/right02", gp.tileSize, gp.tileSize);
    }
    
    // Note: No update() method override means this entity uses parent Entity's default behavior
    // This makes it a simple, non-interactive entity by default
}