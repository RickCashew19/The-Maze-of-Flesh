package tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ProceduralLayout {
    RoomManager roomManager;
    Random random;
    
    // Track which rooms are used in the current layout
    private int[] layout; // layout[position] = roomIndex
    private boolean[] usedRooms; // Track which room indexes are already used
    
    // Layout dimensions
    public int layoutWidth = 5;
    public int layoutHeight = 5;
    public int totalPositions;
    
    // Room type constants
    private static final int SAFE_ROOM_START = 0;
    private static final int SAFE_ROOM_END = 3;
    private static final int BOSS_ROOM_START = 4;
    private static final int BOSS_ROOM_END = 7;
    private static final int NORMAL_ROOM_START = 8;
    private static final int NORMAL_ROOM_END = 24;
    private static final int TOTAL_ROOMS = 25;
    
    // Track how many normal rooms we've placed
    private int normalRoomsPlaced = 0;
    private int maxNormalRoomsToPlace;
    
    public ProceduralLayout(RoomManager roomManager) {
        this.roomManager = roomManager;
        this.random = new Random();
        this.totalPositions = layoutWidth * layoutHeight;
        this.layout = new int[totalPositions];
        this.usedRooms = new boolean[TOTAL_ROOMS];
        
        // Get the normal room limit from RoomManager
        this.maxNormalRoomsToPlace = getMaxNormalRoomsFromRoomManager();
        
        System.out.println("ProceduralLayout will place " + maxNormalRoomsToPlace + " normal rooms");
    }
    
    /**
     * Get the normal room limit from RoomManager
     */
    private int getMaxNormalRoomsFromRoomManager() {
        // Since RoomManager doesn't expose maxNormalRoomsToUse directly,
        // we'll use the same logic based on difficulty
        // You could modify RoomManager to expose this value if needed
        return 8; // Default to normal difficulty (8 rooms)
    }
    
    /**
     * GENERATE A NEW RANDOM LAYOUT EACH GAME
     */
    public void generateNewLayout() {
        // Reset everything
        Arrays.fill(layout, -1); // -1 means empty position
        Arrays.fill(usedRooms, false);
        normalRoomsPlaced = 0;
        
        // Step 1: Place starting safe room in the center
        int startX = layoutWidth / 2;
        int startY = layoutHeight / 2;
        int startPos = startY * layoutWidth + startX;
        
        int startRoom = getRandomSafeRoom();
        layout[startPos] = startRoom;
        usedRooms[startRoom] = true;
        
        System.out.println("Start room: " + startRoom + " at position (" + startX + "," + startY + ")");
        
        // Step 2: Generate path to boss room (respecting normal room limits)
        generatePathToBoss(startPos);
        
        // Step 3: Fill remaining positions with normal rooms (if we haven't reached the limit)
        fillRemainingPositions();
        
        // Step 4: Print layout for debugging
        printLayout();
        
        // Step 5: Validate that boss room was actually placed
        validateBossRoomPlacement();
        
        System.out.println("Total normal rooms placed: " + normalRoomsPlaced + "/" + maxNormalRoomsToPlace);
    }
    
    /**
     * GENERATE PATH FROM START TO BOSS ROOM (RESPECTING NORMAL ROOM LIMITS)
     */
    private void generatePathToBoss(int startPos) {
        int currentPos = startPos;
        List<Integer> path = new ArrayList<>();
        path.add(startPos);
        
        // Create a random path, but stop if we reach the normal room limit
        int maxPathLength = 3 + random.nextInt(4);
        System.out.println("Generating path of max length: " + maxPathLength);
        
        for (int i = 0; i < maxPathLength; i++) {
            // Check if we've reached the normal room limit
            if (normalRoomsPlaced >= maxNormalRoomsToPlace) {
                System.out.println("Normal room limit reached at step " + i + ", ending path early");
                break;
            }
            
            // Get available directions from current position
            List<Integer> availableDirs = getAvailableDirections(currentPos);
            if (availableDirs.isEmpty()) {
                System.out.println("No available directions at step " + i + ", ending path early");
                break;
            }
            
            // Pick random direction
            int direction = availableDirs.get(random.nextInt(availableDirs.size()));
            int newPos = movePosition(currentPos, direction);
            
            // Make sure we haven't visited this position already
            if (layout[newPos] != -1) {
                System.out.println("Position " + newPos + " already occupied, skipping");
                continue;
            }
            
            currentPos = newPos;
            path.add(currentPos);
            
            // Place a normal room (if we haven't reached the limit)
            if (normalRoomsPlaced < maxNormalRoomsToPlace) {
                int normalRoom = getRandomNormalRoom();
                layout[currentPos] = normalRoom;
                usedRooms[normalRoom] = true;
                normalRoomsPlaced++;
                
                System.out.println("Step " + i + ": Placed normal room " + normalRoom + 
                                 " at position " + currentPos + " (" + normalRoomsPlaced + "/" + maxNormalRoomsToPlace + ")");
            } else {
                // We've reached the limit, place a boss room instead
                int bossRoom = getRandomBossRoom();
                layout[currentPos] = bossRoom;
                usedRooms[bossRoom] = true;
                System.out.println("Normal room limit reached, placing boss room " + bossRoom + " instead");
                break;
            }
        }
        
        // Place boss room at the end of path (if we haven't already)
        if (layout[currentPos] >= NORMAL_ROOM_START && layout[currentPos] <= NORMAL_ROOM_END) {
            // Replace the last normal room with a boss room
            int bossRoom = getRandomBossRoom();
            int previousRoom = layout[currentPos];
            layout[currentPos] = bossRoom;
            usedRooms[bossRoom] = true;
            // Free the previous normal room slot
            usedRooms[previousRoom] = false;
            normalRoomsPlaced--;
            
            System.out.println("Replaced final normal room with boss room " + bossRoom + " at position " + currentPos);
        } else if (layout[currentPos] == -1) {
            // Empty position at the end, place a boss room
            int bossRoom = getRandomBossRoom();
            layout[currentPos] = bossRoom;
            usedRooms[bossRoom] = true;
            System.out.println("Placed boss room " + bossRoom + " at final position " + currentPos);
        }
    }
    
    /**
     * FILL EMPTY POSITIONS WITH RANDOM NORMAL ROOMS (IF UNDER LIMIT)
     */
    private void fillRemainingPositions() {
        int roomsPlaced = 0;
        
        // Create a list of all empty positions
        List<Integer> emptyPositions = new ArrayList<>();
        for (int pos = 0; pos < totalPositions; pos++) {
            if (layout[pos] == -1) {
                emptyPositions.add(pos);
            }
        }
        
        // Shuffle the empty positions for random placement
        for (int i = emptyPositions.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = emptyPositions.get(i);
            emptyPositions.set(i, emptyPositions.get(j));
            emptyPositions.set(j, temp);
        }
        
        // Fill empty positions with normal rooms (if under limit)
        for (int pos : emptyPositions) {
            if (normalRoomsPlaced < maxNormalRoomsToPlace) {
                // 80% chance to place a room in empty spots
                if (random.nextFloat() < 0.8f) {
                    int normalRoom = getRandomNormalRoom();
                    layout[pos] = normalRoom;
                    usedRooms[normalRoom] = true;
                    normalRoomsPlaced++;
                    roomsPlaced++;
                }
            } else {
                break; // Stop if we've reached the limit
            }
        }
        
        System.out.println("Filled " + roomsPlaced + " additional normal rooms");
    }
    
    /**
     * GET RANDOM ROOM OF EACH TYPE (that hasn't been used yet)
     */
    private int getRandomSafeRoom() {
        List<Integer> available = new ArrayList<>();
        for (int i = SAFE_ROOM_START; i <= SAFE_ROOM_END; i++) {
            if (!usedRooms[i]) available.add(i);
        }
        int room = available.isEmpty() ? SAFE_ROOM_START : available.get(random.nextInt(available.size()));
        System.out.println("Selected safe room: " + room + " from " + available.size() + " options");
        return room;
    }
    
    private int getRandomBossRoom() {
        List<Integer> available = new ArrayList<>();
        for (int i = BOSS_ROOM_START; i <= BOSS_ROOM_END; i++) {
            if (!usedRooms[i]) available.add(i);
        }
        int room = available.isEmpty() ? BOSS_ROOM_START : available.get(random.nextInt(available.size()));
        System.out.println("Selected boss room: " + room + " from " + available.size() + " options");
        return room;
    }
    
    private int getRandomNormalRoom() {
        List<Integer> available = new ArrayList<>();
        for (int i = NORMAL_ROOM_START; i <= NORMAL_ROOM_END; i++) {
            if (!usedRooms[i]) available.add(i);
        }
        
        if (available.isEmpty()) {
            System.out.println("WARNING: No unused normal rooms available!");
            return NORMAL_ROOM_START; // Fallback
        }
        
        return available.get(random.nextInt(available.size()));
    }
    
    /**
     * GET AVAILABLE DIRECTIONS FROM A POSITION
     */
    private List<Integer> getAvailableDirections(int pos) {
        List<Integer> directions = new ArrayList<>();
        int x = pos % layoutWidth;
        int y = pos / layoutWidth;
        
        // Check if we can move in each direction (within grid bounds and empty)
        if (x > 0 && layout[pos - 1] == -1) directions.add(0); // Left
        if (x < layoutWidth - 1 && layout[pos + 1] == -1) directions.add(1); // Right
        if (y > 0 && layout[pos - layoutWidth] == -1) directions.add(2); // Up
        if (y < layoutHeight - 1 && layout[pos + layoutWidth] == -1) directions.add(3); // Down
        
        return directions;
    }
    
    /**
     * MOVE POSITION IN A DIRECTION
     */
    private int movePosition(int pos, int direction) {
        switch (direction) {
            case 0: return pos - 1; // Left
            case 1: return pos + 1; // Right
            case 2: return pos - layoutWidth; // Up
            case 3: return pos + layoutWidth; // Down
            default: return pos;
        }
    }
    
    /**
     * GET ROOM INDEX AT GRID POSITION
     */
    public int getRoomAtPosition(int gridX, int gridY) {
        if (gridX < 0 || gridX >= layoutWidth || gridY < 0 || gridY >= layoutHeight) {
            return -1; // Out of bounds
        }
        int roomIndex = layout[gridY * layoutWidth + gridX];
        return roomIndex;
    }
    
    /**
     * GET ROOM TYPE AT GRID POSITION
     */
    public int getRoomTypeAtPosition(int gridX, int gridY) {
        int roomIndex = getRoomAtPosition(gridX, gridY);
        if (roomIndex == -1) return -1;
        
        if (roomIndex >= SAFE_ROOM_START && roomIndex <= SAFE_ROOM_END) return RoomManager.SAFE_ROOM;
        if (roomIndex >= BOSS_ROOM_START && roomIndex <= BOSS_ROOM_END) return RoomManager.BOSS_ROOM;
        if (roomIndex >= NORMAL_ROOM_START && roomIndex <= NORMAL_ROOM_END) return RoomManager.NORMAL_ROOM;
        
        return -1;
    }
    
    /**
     * SET MAX NORMAL ROOMS (call this after RoomManager is initialized)
     */
    public void setMaxNormalRooms(int maxNormalRooms) {
        this.maxNormalRoomsToPlace = maxNormalRooms;
        System.out.println("ProceduralLayout normal room limit set to: " + maxNormalRoomsToPlace);
    }
    
    /**
     * VALIDATE THAT BOSS ROOM WAS PLACED
     */
    private void validateBossRoomPlacement() {
        boolean hasBossRoom = false;
        for (int roomIndex : layout) {
            if (roomIndex >= BOSS_ROOM_START && roomIndex <= BOSS_ROOM_END) {
                hasBossRoom = true;
                break;
            }
        }
        
        if (!hasBossRoom) {
            System.out.println("WARNING: No boss room placed in layout! Forcing boss room placement...");
            forceBossRoomPlacement();
        }
    }
    
    /**
     * FORCE BOSS ROOM PLACEMENT IF NONE EXISTS
     */
    private void forceBossRoomPlacement() {
        // Find an empty position or replace a normal room at the edge
        for (int pos = totalPositions - 1; pos >= 0; pos--) {
            if (layout[pos] == -1 || (layout[pos] >= NORMAL_ROOM_START && layout[pos] <= NORMAL_ROOM_END)) {
                int bossRoom = getRandomBossRoom();
                if (layout[pos] != -1) {
                    // If replacing a normal room, decrement the count
                    if (layout[pos] >= NORMAL_ROOM_START && layout[pos] <= NORMAL_ROOM_END) {
                        normalRoomsPlaced--;
                    }
                    usedRooms[layout[pos]] = false; // Free the previous room
                }
                layout[pos] = bossRoom;
                usedRooms[bossRoom] = true;
                System.out.println("Forced boss room " + bossRoom + " at position " + pos);
                break;
            }
        }
    }
    
    /**
     * DEBUG: PRINT THE LAYOUT
     */
    private void printLayout() {
        System.out.println("=== GENERATED LAYOUT ===");
        System.out.println("Normal rooms used: " + normalRoomsPlaced + "/" + maxNormalRoomsToPlace);
        
        for (int y = 0; y < layoutHeight; y++) {
            for (int x = 0; x < layoutWidth; x++) {
                int roomIndex = layout[y * layoutWidth + x];
                if (roomIndex == -1) {
                    System.out.print("[ ]");
                } else {
                    char roomType = 'N'; // Normal
                    if (roomIndex <= SAFE_ROOM_END) roomType = 'S'; // Safe
                    else if (roomIndex <= BOSS_ROOM_END) roomType = 'B'; // Boss
                    System.out.print("[" + roomType + "]");
                }
            }
            System.out.println();
        }
    }
    
    /**
     * GET STARTING POSITION (center of grid)
     */
    public int[] getStartPosition() {
        return new int[] { layoutWidth / 2, layoutHeight / 2 };
    }
    
    /**
     * GET TOTAL NORMAL ROOMS PLACED
     */
    public int getNormalRoomsPlaced() {
        return normalRoomsPlaced;
    }
}