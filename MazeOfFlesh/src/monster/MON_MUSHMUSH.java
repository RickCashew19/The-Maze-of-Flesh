package monster;

import java.awt.Rectangle;
import java.util.Random;

import entity.Entity;
import main.GamePanel;
import main.Sound;

public class MON_MUSHMUSH extends Entity {

	int soundCounter;
	GamePanel gp;
	
	// Add sound instances for this monster
	private Sound walkSound;
	private Sound attackSound;
	private Sound hurtSound;

	public static final String monName = "Rock Monster";

	public MON_MUSHMUSH(GamePanel gp) {
		super(gp);
		this.gp = gp;

		type = type_monster;
		name = monName;
		defaultSpeed = 2;
		speed = defaultSpeed;
		maxLife = 6;
		life = maxLife;
		attack = 2;
		defense = 0;
		exp = 2;

		solidArea = new Rectangle();
		solidArea.x = 16;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 32;
		solidArea.height = 46;
		attackArea.width = 32;
		attackArea.height = 32;
		motion1_duration = 20;
		motion2_duration = 40;
		knockBackPower = 1;

		// Initialize sounds for this monster instance
		walkSound = new Sound();
		walkSound.setFile(7, Sound.VOLUME_SFX); // burning.wav for walking sound
		walkSound.setMonsterVolume("flesh_monster");
		
		attackSound = new Sound();
		attackSound.setFile(5, Sound.VOLUME_SFX); // stick_slash.wav for attack
		attackSound.setMonsterVolume("flesh_monster");
		
		hurtSound = new Sound();
		hurtSound.setFile(6, Sound.VOLUME_SFX); // hitmonster.wav for hurt
		hurtSound.setMonsterVolume("flesh_monster");

		getImage();
		getAttackImage();
	}

	private void getImage() {
		up1 = setup("/monster/mossyrock_down1", gp.tileSize, gp.tileSize);
		up2 = setup("/monster/mossrock_side01", gp.tileSize, gp.tileSize);
		down1 = setup("/monster/mossyrock_down1", gp.tileSize, gp.tileSize);
		down2 = setup("/monster/mossrock_side01", gp.tileSize, gp.tileSize);
		left1 = setup("/monster/mossrock_side01", gp.tileSize, gp.tileSize);
		left2 = setup("/monster/mossrock_side02", gp.tileSize, gp.tileSize);
		right1 = setup("/monster/mossrock_side01", gp.tileSize, gp.tileSize);
		right2 = setup("/monster/mossrock_side02", gp.tileSize, gp.tileSize);
	}

	private void getAttackImage() {
		attackUp1 = setup("/monster/mossyrock_down1", gp.tileSize, gp.tileSize*2);
		attackUp2 = setup("/monster/mossyrock_down1", gp.tileSize, gp.tileSize*2);
		attackDown1 = setup("/monster/mossyrock_down1", gp.tileSize, gp.tileSize*2);
		attackDown2 = setup("/monster/mossyrock_down1", gp.tileSize, gp.tileSize*2);
		attackLeft1 = setup("/monster/mossyrock_down1", gp.tileSize*2, gp.tileSize);
		attackLeft2 = setup("/monster/mossyrock_down1", gp.tileSize*2, gp.tileSize);
		attackRight1 = setup("/monster/mossyrock_down1", gp.tileSize*2, gp.tileSize);
		attackRight2 = setup("/monster/mossyrock_down1", gp.tileSize*2, gp.tileSize);
	}

	public void update() {
		super.update();
		// Update sound volumes based on distance from player
		updateSoundVolumes();
	}

	private void updateSoundVolumes() {
		int maxHearingDistance = 15; // tiles
		
		// Update all this monster's sounds based on distance from player
		walkSound.setVolumeByPosition(worldX, worldY, 
									gp.player.worldX, gp.player.worldY, 
									maxHearingDistance, gp.tileSize);
		attackSound.setVolumeByPosition(worldX, worldY, 
									  gp.player.worldX, gp.player.worldY, 
									  maxHearingDistance, gp.tileSize);
		hurtSound.setVolumeByPosition(worldX, worldY, 
									gp.player.worldX, gp.player.worldY, 
									maxHearingDistance, gp.tileSize);
	}

	public void setAction() {
		if (onPath == true) {
			detectedPlayer = true;

			if(soundCounter >= 30) {
				soundCounter = 0;
				// Use the monster's own walk sound instead of global SE
				walkSound.play();
			}
			
			// Check if it stops chasing
			checkStopChasingOrNot(gp.player, 5, 100);

			// Search direction to go
			searchPath(getGoalCol(gp.player), getGoalRow(gp.player), canMove);
			soundCounter++;
		} else {
			detectedPlayer = false;
			// Check if it starts chasing
			checkStartChasingOrNot(gp.player, 3, 100);
			
			actionLockCounter++;

			if (actionLockCounter == 120) {
				Random random = new Random();
				int i = random.nextInt(100) + 1; // pick up a number from 1 to 100

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
	}

	public void damageReaction() {
		actionLockCounter = 0;
		onPath = true;
		
		// Play hurt sound when damaged
		hurtSound.play();
	}
	
	// Override the attack method to play attack sound
	@Override
	public void damagePlayer(int attack) {
		// Play attack sound when attacking player
		attackSound.play();
		super.damagePlayer(attack);
	}

	public void checkDrop() {
		
	}
	
	// Clean up sounds when monster is removed
	public void cleanup() {
		if (walkSound != null) {
			walkSound.stop();
		}
		if (attackSound != null) {
			attackSound.stop();
		}
		if (hurtSound != null) {
			hurtSound.stop();
		}
	}
}