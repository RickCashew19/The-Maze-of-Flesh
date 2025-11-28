package main;

import data.Progress;
import entity.Entity;
import tile.ProceduralLayout;
import tile.RoomManager;

public class EventHandler {

    GamePanel gp;
    EventRect eventRect[][][];
    Entity eventMaster;
    
    // Procedural generation system
    ProceduralLayout proceduralLayout;
    int currentGridX, currentGridY;
    int currentRoomIndex;

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    int tempMap, tempCol, tempRow;

    public EventHandler(GamePanel gp) {
        this.gp = gp;
        
        // Initialize RoomManager with normal difficulty
        gp.rManager = new RoomManager(gp, RoomManager.NORMAL);
        
        // Initialize procedural layout
        proceduralLayout = new ProceduralLayout(gp.rManager);
        proceduralLayout.setMaxNormalRooms(getMaxNormalRoomsFromDifficulty(RoomManager.NORMAL));
        
        eventMaster = new Entity(gp);
        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        initializeEventRects();
        generateNewGameLayout();
        setDialogue();
    }
    
    private void initializeEventRects() {
        for (int map = 0; map < gp.maxMap; map++) {
            for (int col = 0; col < gp.maxWorldCol; col++) {
                for (int row = 0; row < gp.maxWorldRow; row++) {
                    eventRect[map][col][row] = new EventRect();
                    eventRect[map][col][row].x = 20;
                    eventRect[map][col][row].y = 20;
                    eventRect[map][col][row].width = 8;
                    eventRect[map][col][row].height = 8;
                    eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
                    eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;
                }
            }
        }
    }
    
    public void generateNewGameLayout() {
        proceduralLayout.generateNewLayout();
        
        // Find starting room position (center of layout)
        int[] startPos = proceduralLayout.getStartPosition();
        currentGridX = startPos[0];
        currentGridY = startPos[1];
        
        // Get the starting room index
        currentRoomIndex = proceduralLayout.getRoomAtPosition(currentGridX, currentGridY);
        gp.currentArea = currentRoomIndex;
        
        // Mark starting room as used
        gp.rManager.markRoomUsed(currentRoomIndex);
        
        System.out.println("Game started in room " + currentRoomIndex + " at position (" + currentGridX + "," + currentGridY + ")");
    }
    
    private int getMaxNormalRoomsFromDifficulty(int difficulty) {
        switch (difficulty) {
            case RoomManager.EASY: return 4;
            case RoomManager.NORMAL: return 8;
            case RoomManager.HARD: return 17;
            default: return 8;
        }
    }
    
    /**
     * CALL THIS WHEN STARTING A NEW GAME
     */
    public void startNewGame() {
        gp.rManager.resetForNewGame();
        generateNewGameLayout();
    }
    
    /**
     * CHECK FOR ROOM TRANSITIONS WHEN PLAYER HITS EDGES
     */
    private void checkRoomTransitions() {
        int playerCol = gp.player.worldX / gp.tileSize;
        int playerRow = gp.player.worldY / gp.tileSize;
        
        // Check each direction if player is at the edge
        if (playerCol <= 0) {
            moveToAdjacentRoom(RoomManager.LEFT_DOOR);
        }
        else if (playerCol >= gp.maxWorldCol - 1) {
            moveToAdjacentRoom(RoomManager.RIGHT_DOOR);
        }
        else if (playerRow <= 0) {
            moveToAdjacentRoom(RoomManager.UP_DOOR);
        }
        else if (playerRow >= gp.maxWorldRow - 1) {
            moveToAdjacentRoom(RoomManager.DOWN_DOOR);
        }
    }
    
    private void moveToAdjacentRoom(int exitDirection) {
//        int newRoomIndex = gp.rManager.findConnectingRoom(currentRoomIndex, exitDirection);
//        
//        if (newRoomIndex != -1) {
//            // Mark the new room as used
//            gp.rManager.markRoomUsed(newRoomIndex);
//            
//            // Calculate spawn position based on exit direction
//            int spawnCol = 0, spawnRow = 0;
//            switch (exitDirection) {
//                case RoomManager.LEFT_DOOR:
//                    spawnCol = gp.maxWorldCol - 2; 
//                    spawnRow = gp.maxWorldRow / 2; 
//                    currentGridX--;
//                    break;
//                case RoomManager.RIGHT_DOOR:
//                    spawnCol = 1; 
//                    spawnRow = gp.maxWorldRow / 2; 
//                    currentGridX++;
//                    break;
//                case RoomManager.UP_DOOR:
//                    spawnCol = gp.maxWorldCol / 2; 
//                    spawnRow = gp.maxWorldRow - 2; 
//                    currentGridY--;
//                    break;
//                case RoomManager.DOWN_DOOR:
//                    spawnCol = gp.maxWorldCol / 2; 
//                    spawnRow = 1; 
//                    currentGridY++;
//                    break;
//            }
//            
//            // Update current room
//            currentRoomIndex = newRoomIndex;
//            gp.currentArea = newRoomIndex;
//            
//            // TELEPORT TO THE CORRECT MAP INDEX
//            // RoomManager roomIndex (0-24) corresponds to map index (1-25)
//            int targetMap = newRoomIndex + 1; 
//           // teleport(targetMap, spawnCol, spawnRow, gp.indoor);
//            
//            // Handle room-specific events
//            handleRoomEntry(newRoomIndex);
//            
//            System.out.println("Moved to room " + newRoomIndex + " (map " + targetMap + ") at grid (" + currentGridX + "," + currentGridY + ")");
//        } else {
//            System.out.println("Cannot move - no connecting room available!");
//        }
    }
    
    private void handleRoomEntry(int roomIndex) {
        int roomType = gp.rManager.getRoomType(roomIndex);
        
        switch (roomType) {
            case RoomManager.SAFE_ROOM:
                // Heal player, save game, etc.
                gp.player.life = gp.player.maxLife;
                gp.player.mana = gp.player.maxMana;
                gp.saveLoad.save();
                System.out.println("Entered safe room - fully healed!");
                break;
                
            case RoomManager.BOSS_ROOM:
                // Start boss battle
                System.out.println("BOSS ROOM ENTERED! Prepare for battle!");
                // Add your boss battle logic here
                break;
                
            case RoomManager.NORMAL_ROOM:
                // Spawn enemies or other normal room logic
                System.out.println("Entered normal room");
                break;
        }
    }

    public void setDialogue() {
        eventMaster.dialogues[0][0] = "You step on a shit!!";
        eventMaster.dialogues[1][0] = "HEAL!!!!\n(Progress Saved)";
        eventMaster.dialogues[2][0] = "Your State is Full!!\n(Progressed saved)";
        eventMaster.dialogues[3][0] = "You step on a shit!!";
    }

    public void checkEvent() {
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);

        if (distance > gp.tileSize)
            canTouchEvent = true;

        if (canTouchEvent) {
            // Check for room transitions first
            checkRoomTransitions();
            
            // Then check your existing teleport events
            if (hit(0, 15, 9, "any")) {
                teleport(1, 1, 16, gp.mazeArea);
            } else if (hit(0, 16, 9, "any")) {
                teleport(1, 1, 17, gp.mazeArea);
            }
            if (hit(1, 0, 16, "any")) {
                teleport(0, 15, 9, gp.mazeArea);
            } else if (hit(1, 0, 17, "any")) {
                teleport(0, 16, 9, gp.mazeArea);
            }
            else if (hit(1, 31, 16, "any")) {
                teleport(2, 16, 1, gp.mazeArea);
            } else if (hit(1, 31, 17, "any")) {
                teleport(2, 17, 1, gp.mazeArea);
            } else if (hit(2, 16, 0, "any")) {
                teleport(1, 31, 16, gp.mazeArea);
            } else if (hit(2, 17, 0, "any")) {
                teleport(1, 31, 17, gp.mazeArea);
            }
            else if (hit(2, 31, 16, "any")) {
                teleport(3, 17, 1, gp.mazeArea);
            } else if (hit(2, 31, 17, "any")) {
                teleport(3, 18, 1, gp.mazeArea);
            } else if (hit(3, 17, 1, "any")) {
                teleport(2, 31, 16, gp.mazeArea);
            } else if (hit(3, 18, 1, "any")) {
                teleport(2, 31, 17, gp.mazeArea);
            }
        }
    }

    public boolean hit(int map, int col, int row, String reqDirection) {
        boolean hit = false;

        if (map == gp.currentMap) {
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
            eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gp.tileSize + eventRect[map][col][row].y;

            if (gp.player.solidArea.intersects(eventRect[map][col][row])
                    && eventRect[map][col][row].eventDone == false) {
                if (gp.player.direction.contentEquals(reqDirection) || reqDirection.equals("any")) {
                    hit = true;
                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;
                }
            }
            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }

        return hit;
    }

    public void damagePit(int gameState) {
        gp.gameState = gameState;
        gp.playSE(6);
        eventMaster.startDialogue(eventMaster, 0);
        gp.player.life -= 1;
        canTouchEvent = false;
    }

    public void healing(int gameState) {
        if (gp.keyH.enterPressed == true) {
            gp.gameState = gameState;
            gp.player.attackCanceled = true;
            gp.playSE(3);
            if (gp.player.life < gp.player.maxLife || gp.player.mana < gp.player.maxMana) {
                eventMaster.startDialogue(eventMaster, 1);
                gp.player.life += 1;
                gp.player.mana += 1;
                gp.saveLoad.save();
            } else {
                eventMaster.startDialogue(eventMaster, 2);
            }
        }
    }

    public void teleport(int map, int col, int row, int area) {
        gp.gameState = gp.transitionState;
        gp.nextArea = area;
        tempMap = map;
        tempCol = col;
        tempRow = row;
        canTouchEvent = false;
        gp.playSE(14);
    }

    public void speak(Entity entity) {
        if (gp.keyH.enterPressed == true) {
            gp.gameState = gp.dialogueState;
            gp.player.attackCanceled = true;
            entity.speak();
        }
    }

    public void ending() {
        if (Progress.endingSceneDone == false) {
            gp.gameState = gp.cutsceneState;
            gp.csManager.sceneNum = gp.csManager.ending;
        }
    }
}