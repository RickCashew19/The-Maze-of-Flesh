package tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import main.GamePanel;

public class RoomManager {
    GamePanel gp;
    Random rand;
    
    // Room types
    public static final int SAFE_ROOM = 0;
    public static final int NORMAL_ROOM = 1; 
    public static final int BOSS_ROOM = 2;
    
    // Door directions - use these to check connections
    public static final int LEFT_DOOR = 0;
    public static final int RIGHT_DOOR = 1;
    public static final int UP_DOOR = 2;
    public static final int DOWN_DOOR = 3;
    
    // Store which rooms have which doors
    public boolean[][] roomDoors; // [roomIndex][direction]
    public int[] roomTypes; // What type each room is
    
    private boolean[] usedRooms;
    private int maxNormalRoomsToUse;
    private int normalRoomsUsed = 0;
    
    // Difficulty settings
    public static final int EASY = 0;
    public static final int NORMAL = 1;
    public static final int HARD = 2;
    
    public RoomManager(GamePanel gp, int difficulty) {
        this.gp = gp;
        this.rand = new Random();
        this.usedRooms = new boolean[25]; 
        
        // Set limits based on difficulty
        switch (difficulty) {
            case EASY:
                maxNormalRoomsToUse = 4;  // 4 out of 17 normal rooms
                break;
            case NORMAL:
                maxNormalRoomsToUse = 8; // 8 out of 17 normal rooms
                break;
            case HARD:
                maxNormalRoomsToUse = 17; // All 17 normal rooms
                break;
            default:
                maxNormalRoomsToUse = 8;
        }
        
        initializeRooms();
    }
    
    /**
     * SETUP YOUR ROOM TEMPLATES HERE
     * Map your 25 rooms (4 safe + 4 boss + 17 normal) to indexes
     */
    private void initializeRooms() {
        // Total rooms: 4 safe + 4 boss + 17 normal = 25 rooms
        roomDoors = new boolean[25][4]; // 25 rooms, 4 directions
        roomTypes = new int[25];
        
        // Initialize all rooms to have no doors by default
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 4; j++) {
                roomDoors[i][j] = false;
            }
        }
        
        // SAFE ROOMS (indexes 0-3)
        setupSafeRooms();
        
        // BOSS ROOMS (indexes 4-7)  
        setupBossRooms();
        
        // NORMAL ROOMS (indexes 8-24)
        setupNormalRooms();
    }
    
    private void setupSafeRooms() {
        // Safe Room 0: Left door only (index 0)
        roomTypes[0] = SAFE_ROOM;
        roomDoors[0][LEFT_DOOR] = true;
        
        // Safe Room 1: Right door only (index 1)
        roomTypes[1] = SAFE_ROOM;
        roomDoors[1][RIGHT_DOOR] = true;
        
        // Safe Room 2: Up door only (index 2)
        roomTypes[2] = SAFE_ROOM;
        roomDoors[2][UP_DOOR] = true;
        
        // Safe Room 3: Down door only (index 3)
        roomTypes[3] = SAFE_ROOM;
        roomDoors[3][DOWN_DOOR] = true;
    }
    
    private void setupBossRooms() {
        // Boss Room 0: Left door only (index 4)
        roomTypes[4] = BOSS_ROOM;
        roomDoors[4][LEFT_DOOR] = true;
        
        // Boss Room 1: Right door only (index 5)
        roomTypes[5] = BOSS_ROOM;
        roomDoors[5][RIGHT_DOOR] = true;
        
        // Boss Room 2: Up door only (index 6)
        roomTypes[6] = BOSS_ROOM;
        roomDoors[6][UP_DOOR] = true;
        
        // Boss Room 3: Down door only (index 7)
        roomTypes[7] = BOSS_ROOM;
        roomDoors[7][DOWN_DOOR] = true;
    }
    
    private void setupNormalRooms() {
        // Single door rooms (8-11)
        roomTypes[8] = NORMAL_ROOM;  // L
        roomDoors[8][LEFT_DOOR] = true;
        
        roomTypes[9] = NORMAL_ROOM;  // R
        roomDoors[9][RIGHT_DOOR] = true;
        
        roomTypes[10] = NORMAL_ROOM; // U
        roomDoors[10][UP_DOOR] = true;
        
        roomTypes[11] = NORMAL_ROOM; // D
        roomDoors[11][DOWN_DOOR] = true;

        // Two-door rooms (12-17)
        roomTypes[12] = NORMAL_ROOM; // LR
        roomDoors[12][LEFT_DOOR] = true;
        roomDoors[12][RIGHT_DOOR] = true;

        roomTypes[13] = NORMAL_ROOM; // UD
        roomDoors[13][UP_DOOR] = true;
        roomDoors[13][DOWN_DOOR] = true;

        roomTypes[14] = NORMAL_ROOM; // RU
        roomDoors[14][RIGHT_DOOR] = true;
        roomDoors[14][UP_DOOR] = true;

        roomTypes[15] = NORMAL_ROOM; // LU
        roomDoors[15][LEFT_DOOR] = true;
        roomDoors[15][UP_DOOR] = true;

        roomTypes[16] = NORMAL_ROOM; // RD
        roomDoors[16][RIGHT_DOOR] = true;
        roomDoors[16][DOWN_DOOR] = true;

        roomTypes[17] = NORMAL_ROOM; // LD
        roomDoors[17][LEFT_DOOR] = true;
        roomDoors[17][DOWN_DOOR] = true;
        
        // Three-door rooms (18-21)
        roomTypes[18] = NORMAL_ROOM; // LRD
        roomDoors[18][LEFT_DOOR] = true;
        roomDoors[18][RIGHT_DOOR] = true;
        roomDoors[18][DOWN_DOOR] = true;
        
        roomTypes[19] = NORMAL_ROOM; // LRU
        roomDoors[19][LEFT_DOOR] = true;
        roomDoors[19][RIGHT_DOOR] = true;
        roomDoors[19][UP_DOOR] = true;
        
        roomTypes[20] = NORMAL_ROOM; // LUD
        roomDoors[20][LEFT_DOOR] = true;
        roomDoors[20][UP_DOOR] = true;
        roomDoors[20][DOWN_DOOR] = true;
        
        roomTypes[21] = NORMAL_ROOM; // RUD
        roomDoors[21][RIGHT_DOOR] = true;
        roomDoors[21][UP_DOOR] = true;
        roomDoors[21][DOWN_DOOR] = true;
        
        // Four-door room (22)
        roomTypes[22] = NORMAL_ROOM; // LRUD
        roomDoors[22][LEFT_DOOR] = true;
        roomDoors[22][RIGHT_DOOR] = true;
        roomDoors[22][UP_DOOR] = true;
        roomDoors[22][DOWN_DOOR] = true;
        
        // More two-door combinations (23-24)
        roomTypes[23] = NORMAL_ROOM; // UD
        roomDoors[23][UP_DOOR] = true;
        roomDoors[23][DOWN_DOOR] = true;
        
        roomTypes[24] = NORMAL_ROOM; // LR
        roomDoors[24][LEFT_DOOR] = true;
        roomDoors[24][RIGHT_DOOR] = true;
    }
    
    /**
     * CHECK IF TWO ROOMS CAN CONNECT IN A DIRECTION
     */
    public boolean canConnect(int roomA, int roomB, int directionFromA) {
        if (roomA < 0 || roomA >= 25 || roomB < 0 || roomB >= 25) {
            return false;
        }
        
        if (!roomDoors[roomA][directionFromA]) return false;
        
        int oppositeDir = getOppositeDoor(directionFromA);
        return roomDoors[roomB][oppositeDir];
    }
    
    /**
     * FIND A CONNECTING ROOM THAT HASN'T BEEN USED YET
     */
    public int findConnectingRoom(int currentRoom, int exitDirection) {
        int requiredDoor = getOppositeDoor(exitDirection);
        List<Integer> availableRooms = new ArrayList<>();
        
        boolean canUseNormalRooms = normalRoomsUsed < maxNormalRoomsToUse;
        
        for (int roomIndex = 0; roomIndex < roomDoors.length; roomIndex++) {
            if (roomIndex == currentRoom || usedRooms[roomIndex]) continue;
            
            // Skip normal rooms if we've reached the limit
            if (!canUseNormalRooms && getRoomType(roomIndex) == NORMAL_ROOM) {
                continue;
            }
            
            if (roomDoors[roomIndex][requiredDoor]) {
                availableRooms.add(roomIndex);
            }
        }
        
        if (availableRooms.isEmpty()) {
            System.out.println("No available rooms found for direction: " + exitDirection);
            return -1;
        }
        
        int selectedRoom = availableRooms.get(rand.nextInt(availableRooms.size()));
        usedRooms[selectedRoom] = true;
        
        // Track normal room usage
        if (getRoomType(selectedRoom) == NORMAL_ROOM) {
            normalRoomsUsed++;
            System.out.println("Normal rooms used: " + normalRoomsUsed + "/" + maxNormalRoomsToUse);
        }
        
        return selectedRoom;
    }
    
    /**
     * RESET FOR NEW GAME
     */
    public void resetForNewGame() {
        Arrays.fill(usedRooms, false);
        normalRoomsUsed = 0;
        System.out.println("RoomManager reset for new game");
    }
    
    /**
     * MARK A ROOM AS USED
     */
    public void markRoomUsed(int roomIndex) {
        if (roomIndex >= 0 && roomIndex < usedRooms.length) {
            usedRooms[roomIndex] = true;
        }
    }
    
    /**
     * GET ROOM TYPE
     */
    public int getRoomType(int roomIndex) {
        if (roomIndex < 0 || roomIndex >= 25) return -1;
        return roomTypes[roomIndex];
    }
    
    /**
     * CHECK IF A ROOM HAS A SPECIFIC DOOR
     */
    public boolean hasDoor(int roomIndex, int direction) {
        if (roomIndex < 0 || roomIndex >= 25) return false;
        return roomDoors[roomIndex][direction];
    }
    
    /**
     * GET OPPOSITE DOOR DIRECTION
     */
    public int getOppositeDoor(int direction) {
        switch (direction) {
            case LEFT_DOOR: return RIGHT_DOOR;
            case RIGHT_DOOR: return LEFT_DOOR;
            case UP_DOOR: return DOWN_DOOR;
            case DOWN_DOOR: return UP_DOOR;
            default: return -1;
        }
    }
    
    /**
     * DEBUG: PRINT ROOM INFO
     */
    public void printRoomInfo(int roomIndex) {
        if (roomIndex < 0 || roomIndex >= 25) {
            System.out.println("Invalid room index: " + roomIndex);
            return;
        }
        
        String type = "Unknown";
        switch (roomTypes[roomIndex]) {
            case SAFE_ROOM: type = "Safe"; break;
            case NORMAL_ROOM: type = "Normal"; break;
            case BOSS_ROOM: type = "BOSS"; break;
        }
        
        StringBuilder doors = new StringBuilder();
        if (roomDoors[roomIndex][LEFT_DOOR]) doors.append("L");
        if (roomDoors[roomIndex][RIGHT_DOOR]) doors.append("R");
        if (roomDoors[roomIndex][UP_DOOR]) doors.append("U");
        if (roomDoors[roomIndex][DOWN_DOOR]) doors.append("D");
        
        System.out.println("Room " + roomIndex + " [" + type + "]: Doors " + doors.toString());
    }
    
    /**
     * GET AVAILABLE ROOM COUNT
     */
    public int getAvailableRoomCount() {
        int count = 0;
        for (boolean used : usedRooms) {
            if (!used) count++;
        }
        return count;
    }
}