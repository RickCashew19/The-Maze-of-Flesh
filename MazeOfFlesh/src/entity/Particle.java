package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import main.GamePanel;

/**
 * Particle class - Visual effect particles for explosions, hits, magic, etc.
 * OOP Principle: Inheritance - Extends Entity for basic properties
 * Design Pattern: Flyweight - Many small, simple objects with shared behavior
 * Data Structure: Lightweight objects with minimal state
 */
public class Particle extends Entity {

    // ===== PARTICLE PROPERTIES =====
    Entity generator;  // Entity that created this particle (source)
    Color color;       // Particle color
    int size;          // Particle size in pixels
    int xd;            // X direction multiplier (-1, 0, 1)
    int yd;            // Y direction multiplier (-1, 0, 1)
    
    /**
     * Constructor for Particle
     * OOP Principle: Composition - Takes parameters for visual customization
     * @param gp GamePanel reference
     * @param generator Entity that created this particle
     * @param color Particle color
     * @param size Particle size in pixels
     * @param speed Movement speed
     * @param maxLife Maximum lifetime in frames
     * @param xd X direction (-1=left, 0=none, 1=right)
     * @param yd Y direction (-1=up, 0=none, 1=down)
     */
    public Particle(GamePanel gp, Entity generator, Color color, int size, int speed, int maxLife, int xd, int yd) {
        super(gp);
        
        // Store particle properties
        this.generator = generator;
        this.color = color;
        this.size = size;
        this.speed = speed;
        this.maxLife = maxLife;
        this.xd = xd;
        this.yd = yd;
        
        // Initialize particle state
        life = maxLife;  // Start with full life
        
        // Calculate spawn position centered on generator
        int offset = (gp.tileSize / 2) - (size / 2);
        worldX = generator.worldX + offset;
        worldY = generator.worldY + offset;
    }
    
    /**
     * Update particle state each frame
     * OOP Principle: Encapsulation - Particle physics logic contained here
     * Algorithm:
     * 1. Reduce lifetime
     * 2. Apply gravity effect in later life
     * 3. Move based on direction and speed
     * 4. Mark for removal when life ends
     */
    public void update() {
        // LIFE MANAGEMENT: Particles have limited duration
        life--;
        
        // GRAVITY SIMULATION: Apply downward acceleration in later life stages
        if (life < maxLife / 3) {
            yd++;  // Increase downward movement (simulated gravity)
        }
        
        // MOVEMENT: Update position based on direction and speed
        worldX += xd * speed;
        worldY += yd * speed;
        
        // LIFE CYCLE END: Mark particle for removal
        if (life == 0) {
            alive = false;
        }
    }
    
    /**
     * Draw particle on screen
     * OOP Principle: Polymorphism - Overrides Entity.draw() with particle-specific rendering
     * @param g2 Graphics2D context for drawing
     */
    public void draw(Graphics2D g2) {
        // Convert world coordinates to screen coordinates
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        
        // Set particle color and draw as a filled rectangle
        g2.setColor(color);
        g2.fillRect(screenX, screenY, size, size);
        
        // Note: Simple rectangle drawing - could be enhanced with:
        // - Gradient colors based on life
        // - Transparency based on life
        // - Different shapes (circles, stars, etc.)
        // - Rotation/scale animation
    }
}