package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JPanel;

import ai.PathFinder;
import data.SaveLoad;
import entity.Entity;
import entity.Player;
import environment.EnvironmentManager;
import monster.MON_Rockworm;
import tile.Map;
import tile.TileManager;
import tile_interactive.InteractiveTile;

/**
 * Main GamePanel class that serves as the core game engine
 * OOP Principles: 
 * - Encapsulation: Bundles all game systems and states
 * - Composition: Contains multiple system components
 * - Observer: Listens to keyboard input
 * - Runnable: Game loop implementation
 * 
 * Data Structures:
 * - Arrays: For entity management (objects, NPCs, monsters)
 * - ArrayList: For dynamic collections (particles, entity lists)
 * - Comparator: For rendering order sorting
 */
public class GamePanel extends JPanel implements Runnable {

    // ===== SCREEN SETTINGS =====
    final int originalTileSize = 32; // Base tile size 32x32 pixels
    final int scale = 2; // Scale factor for modern displays
    public final int tileSize = originalTileSize * scale; // Final tile size 64x64
    public final int maxScreenCol = 20; // Horizontal tiles on screen
    public final int maxScreenRow = 12; // Vertical tiles on screen
    public final int screenWidth = tileSize * maxScreenCol; // 1280 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 768 pixels
    
    // ===== WORLD SETTINGS =====
    public final int maxWorldCol = 35; // World width in tiles
    public final int maxWorldRow = 35; // World height in tiles
    public final int maxMap = 20; // Total number of maps
    public int currentMap = 0; // Current active map
    
    // ===== FULLSCREEN SETTINGS =====
    int screenWidth2 = screenWidth; // Dynamic screen width for fullscreen
    int screenHeight2 = screenHeight; // Dynamic screen height for fullscreen
    BufferedImage tempScreen; // Double buffering image
    Graphics2D g2; // Graphics context for tempScreen
    public boolean fullScreenOn = false; // Fullscreen toggle flag
    
    // ===== UNUSED WORLD DIMENSIONS (for potential future use) =====
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;
    
    // ===== GAME LOOP SETTINGS =====
    int FPS = 60; // Target frames per second
    
    // ===== GAME SYSTEMS (Composition Pattern) =====
    public TileManager tileM = new TileManager(this); // Manages tile rendering
    public KeyHandler keyH = new KeyHandler(this); // Handles keyboard input
    Sound music = new Sound(); // Background music controller
    Sound se = new Sound(); // Sound effects controller
    public CollisionChecker cChecker = new CollisionChecker(this); // Collision detection
    public AssetSetter aSetter = new AssetSetter(this); // Places entities in world
    public UI ui = new UI(this); // User interface renderer
    public EventHandler eHandler = new EventHandler(this); // Game event handler
    Config config = new Config(this); // Configuration settings
    public PathFinder pFinder = new PathFinder(this); // A* pathfinding system
    EnvironmentManager eManager = new EnvironmentManager(this); // Weather/lighting effects
    Map map = new Map(this); // Map display system
    SaveLoad saveLoad = new SaveLoad(this); // Save/load system
    public EntityGenerator eGenerator = new EntityGenerator(this); // Entity factory
    public CutsceneManager csManager = new CutsceneManager(this); // Cutscene controller
    Thread gameThread; // Main game thread
    
    // ===== ENTITY ARRAYS (Data Structure: 2D Arrays) =====
    public Player player = new Player(this, keyH); // Player character
    public MON_Rockworm rWOm = new MON_Rockworm(this, "right"); // Example monster
    public Entity obj[][] = new Entity[maxMap][100]; // Objects array [map][max objects]
    public Entity npc[][] = new Entity[maxMap][10]; // NPCs array [map][max NPCs]
    public Entity monster[][] = new Entity[maxMap][50]; // Monsters array [map][max monsters]
    public InteractiveTile iTile[][] = new InteractiveTile[maxMap][50]; // Interactive tiles
    public Entity projectile[][] = new Entity[maxMap][20]; // Projectiles array
    public ArrayList<Entity> particleList = new ArrayList<>(); // Particle effects list
    ArrayList<Entity> entityList = new ArrayList<>(); // Temporary list for sorted rendering
    
    // ===== GAME STATE CONSTANTS (State Pattern) =====
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionsState = 5;
    public final int gameOverState = 6;
    public final int transitionState = 7;
    public final int tradeState = 8;
    public final int mapState = 9;
    public final int cutsceneState = 10;
    
    // ===== AREA MANAGEMENT =====
    public int currentArea; // Current game area
    public int nextArea; // Next area to transition to
    public final int safeArea = 0; // Safe zone area type
    public final int mazeArea = 1; // Maze area type
    
    /**
     * Constructor - Initializes the game panel
     * OOP Principle: Encapsulation - Sets up all panel properties
     */
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // Enable double buffering for smooth rendering
        this.addKeyListener(keyH); // Register keyboard listener
        this.setFocusable(true); // Allow panel to receive keyboard focus
    }
    
    /**
     * Initialize game world and systems
     * OOP Principle: Composition - Sets up all game components
     */
    public void setupGame() {
        aSetter.setObject(); // Place objects in world
        aSetter.setNPC(); // Place NPCs in world
        aSetter.setMonster(); // Place monsters in world
        aSetter.setInteractiveTile(); // Place interactive tiles
        eManager.setup(); // Initialize environment effects
        
        gameState = titleState; // Start with title screen
        currentArea = mazeArea; // Set initial area
        
        // Setup double buffering
        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();
        
        // Apply fullscreen if enabled
        if (fullScreenOn == true) {
            setFullScreen();
        }
    }
    
    /**
     * Reset game to initial state or restart
     * OOP Principle: Encapsulation - Groups reset logic together
     * @param restart If true, completely restart game; if false, reset positions only
     */
    public void resetGame(boolean restart) {
        stopMusic(); // Stop current music
        player.setDefaultPositions(); // Reset player position
        removeTempEntity(); // Remove temporary entities
        player.restorStatus(); // Restore player health/stats
        player.resetCounter(); // Reset player counters
        aSetter.setNPC(); // Reset NPCs
        aSetter.setMonster(); // Reset monsters
        
        // Full restart - reset everything
        if (restart == true) {
            player.setDefaultValues(); // Reset player attributes
            aSetter.setObject(); // Reset objects
            aSetter.setInteractiveTile(); // Reset interactive tiles
        }
    }
    
    /**
     * Switch to fullscreen mode
     * OOP Principle: Abstraction - Hides complex fullscreen setup
     */
    public void setFullScreen() {
        // Get graphics device for fullscreen
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);
        
        // Update screen dimensions for fullscreen
        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
    }
    
    /**
     * Start the game thread (main game loop)
     * OOP Principle: Runnable Interface - Implements game loop via threading
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    /**
     * Main game loop implementation
     * OOP Principle: Runnable pattern - Fixed timestep game loop
     * Algorithm: Fixed timestep with FPS control
     */
    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS; // Time between frames in nanoseconds
        double delta = 0; // Accumulated time
        long lastTime = System.nanoTime();
        long currentTime;
        
        // FPS measurement variables
        long timer = 0;
        int drawCount = 0;
        
        // Main game loop
        while (gameThread != null) {
            currentTime = System.nanoTime();
            
            // Accumulate time since last frame
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            
            // When enough time has passed, update game
            if (delta >= 1) {
                update(); // Update game state
                drawToTempScreen(); // Render to buffer
                drawToScreen(); // Display buffer
                delta--; // Decrement accumulated time
                drawCount++; // Increment frame counter
            }
            
            // Output FPS every second (debug)
            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }
    
    /**
     * Update all game entities and systems
     * OOP Principle: State Pattern - Different update logic for different game states
     */
    public void update() {
        // Only update entities during play state
        if (gameState == playState) {
            // Update player
            player.update();
            
            // Update all NPCs on current map
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    npc[currentMap][i].update();
                }
            }
            
            // Update all objects on current map
            for (int i = 0; i < obj[1].length; i++) {
                if (obj[currentMap][i] != null) {
                    obj[currentMap][i].update();
                }
            }
            
            // Update all monsters on current map
            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i] != null) {
                    if (monster[currentMap][i].alive == true && monster[currentMap][i].dying == false) {
                        monster[currentMap][i].update();
                    }
                    // Remove dead monsters and trigger drops
                    if (monster[currentMap][i].alive == false) {
                        monster[currentMap][i].checkDrop();
                        monster[currentMap][i] = null;
                    }
                }
            }
            
            // Update all projectiles on current map
            for (int i = 0; i < projectile[1].length; i++) {
                if (projectile[currentMap][i] != null) {
                    if (projectile[currentMap][i].alive == true) {
                        projectile[currentMap][i].update();
                    }
                    // Remove inactive projectiles
                    if (projectile[currentMap][i].alive == false) {
                        projectile[currentMap][i] = null;
                    }
                }
            }
            
            // Update all particles
            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    if (particleList.get(i).alive == true) {
                        particleList.get(i).update();
                    }
                    // Remove dead particles
                    if (particleList.get(i).alive == false) {
                        particleList.remove(i);
                    }
                }
            }
            
            // Update all interactive tiles on current map
            for (int i = 0; i < iTile[1].length; i++) {
                if (iTile[currentMap][i] != null) {
                    iTile[currentMap][i].update();
                }
            }
            
            // Update environment effects
            eManager.update();
        }
        
        // Pause state - no updates
        if (gameState == pauseState) {
            // Time is effectively stopped
        }
    }
    
    /**
     * Render game to temporary buffer (double buffering)
     * OOP Principle: Strategy Pattern - Different rendering strategies for different states
     */
    public void drawToTempScreen() {
        // Debug performance measurement
        long drawStart = 0;
        if (keyH.showDebugText == true) {
            drawStart = System.nanoTime();
        }
        
        // Render based on current game state
        if (gameState == titleState) {
            ui.draw(g2); // Draw title screen
        } else if (gameState == mapState) {
            map.drawFullMapScreen(g2); // Draw full map view
        } else {
            // Normal game rendering
            renderGameWorld();
        }
        
        // Debug information overlay
        if (keyH.showDebugText == true) {
            renderDebugInfo(drawStart);
        }
    }
    
    /**
     * Render the main game world
     * OOP Principle: Template Method - Defines rendering pipeline
     */
    private void renderGameWorld() {
        // 1. Draw tile background
        tileM.draw(g2);
        
        // 2. Draw interactive tiles
        for (int i = 0; i < iTile[1].length; i++) {
            if (iTile[currentMap][i] != null) {
                iTile[currentMap][i].draw(g2);
            }
        }
        
        // 3. Collect all entities for sorted rendering
        collectEntitiesForRendering();
        
        // 4. Sort entities by Y-coordinate for correct depth rendering
        sortEntitiesByDepth();
        
        // 5. Draw all entities in sorted order
        renderSortedEntities();
        
        // 6. Draw environment effects (lighting, weather)
        eManager.draw(g2);
        
        // 7. Draw minimap
        map.drawMiniMap(g2);
        
        // 8. Draw cutscenes if active
        csManager.draw(g2);
        
        // 9. Draw UI overlay
        ui.draw(g2);
    }
    
    /**
     * Collect all entities into a single list for sorting
     * Data Structure: ArrayList - Dynamic collection for entities
     */
    private void collectEntitiesForRendering() {
        entityList.clear();
        entityList.add(player); // Add player
        
        // Add NPCs
        for (int i = 0; i < npc[1].length; i++) {
            if (npc[currentMap][i] != null) {
                entityList.add(npc[currentMap][i]);
            }
        }
        
        // Add objects
        for (int i = 0; i < obj[1].length; i++) {
            if (obj[currentMap][i] != null) {
                entityList.add(obj[currentMap][i]);
            }
        }
        
        // Add monsters
        for (int i = 0; i < monster[1].length; i++) {
            if (monster[currentMap][i] != null) {
                entityList.add(monster[currentMap][i]);
            }
        }
        
        // Add projectiles
        for (int i = 0; i < projectile[1].length; i++) {
            if (projectile[currentMap][i] != null) {
                entityList.add(projectile[currentMap][i]);
            }
        }
        
        // Add particles
        for (int i = 0; i < particleList.size(); i++) {
            if (particleList.get(i) != null) {
                entityList.add(particleList.get(i));
            }
        }
    }
    
    /**
     * Sort entities by Y-coordinate for depth rendering
     * Data Structure: Collections.sort with Comparator
     * Algorithm: Sort by worldY (lower Y = higher up = drawn first)
     */
    private void sortEntitiesByDepth() {
        Collections.sort(entityList, new Comparator<Entity>() {
            @Override
            public int compare(Entity e1, Entity e2) {
                // Sort by worldY position for proper depth rendering
                return Integer.compare(e1.worldY, e2.worldY);
            }
        });
    }
    
    /**
     * Render all entities in sorted order
     */
    private void renderSortedEntities() {
        for (int i = 0; i < entityList.size(); i++) {
            entityList.get(i).draw(g2);
        }
        entityList.clear(); // Clear list for next frame
    }
    
    /**
     * Render debug information overlay
     * @param drawStart Start time for performance measurement
     */
    private void renderDebugInfo(long drawStart) {
        long drawEnd = System.nanoTime();
        long passed = drawEnd - drawStart;
        
        // Debug text styling
        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        g2.setColor(Color.WHITE);
        
        int x = 10;
        int y = 400;
        int lineHeight = 20;
        
        // Player position information
        g2.drawString("WorldX:" + player.worldX, x, y);
        y += lineHeight;
        g2.drawString("WorldY:" + player.worldY, x, y);
        y += lineHeight;
        g2.drawString("COL:" + (player.worldX + player.solidArea.x) / tileSize, x, y);
        y += lineHeight;
        g2.drawString("ROW:" + (player.worldY + player.solidArea.y) / tileSize, x, y);
        y += lineHeight;
        g2.drawString("Draw Time: " + passed, x, y);
        
        // Player collision box (red)
        g2.setColor(Color.RED);
        g2.drawRect(player.screenX + player.solidArea.x, 
                   player.screenY + player.solidArea.y,
                   player.solidArea.width, player.solidArea.height);
        
        // Monster collision boxes (yellow)
        g2.setColor(Color.YELLOW);
        for (int i = 0; i < monster[1].length; i++) {
            if (monster[currentMap][i] != null) {
                Entity m = monster[currentMap][i];
                g2.drawRect(m.getScreenX() + m.solidArea.x, 
                           m.getScreenY() + m.solidArea.y,
                           m.solidArea.width, m.solidArea.height);
            }
        }
    }
    
    /**
     * Display the rendered buffer on screen
     * OOP Principle: Double Buffering - Prevents screen tearing
     */
    public void drawToScreen() {
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }
    
    /**
     * Play background music
     * @param i Music track index
     */
    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }
    
    /**
     * Stop background music
     */
    public void stopMusic() {
        music.stop();
    }
    
    /**
     * Play sound effect
     * @param i Sound effect index
     */
    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }
    
    /**
     * Play sound effect (monster variant)
     * @param i Sound effect index
     */
    public void playSE_M(int i) {
        se.setFile(i);
        se.play();
    }
    
    /**
     * Handle area transitions and music changes
     * OOP Principle: State Pattern - Different music for different areas
     */
    public void changeArea() {
        // Only change music if area actually changed
        if (nextArea != currentArea) {
            stopMusic(); // Stop current music
            
            // Play appropriate music for new area
            if (nextArea == safeArea) {
                playMusic(0);
            }
            if (nextArea == mazeArea) {
                playMusic(0); // Same music for now, could be different
            }
        }
        
        currentArea = nextArea; // Update current area
    }
    
    /**
     * Remove temporary entities from all maps
     * Data Structure: Nested loops through 2D entity arrays
     */
    public void removeTempEntity() {
        // Check all maps
        for (int mapNum = 0; mapNum < maxMap; mapNum++) {
            // Check all objects in this map
            for (int i = 0; i < obj[1].length; i++) {
                if (obj[mapNum][i] != null && obj[mapNum][i].temp == true) {
                    obj[mapNum][i] = null; // Remove temporary objects
                }
            }
        }
    }
}