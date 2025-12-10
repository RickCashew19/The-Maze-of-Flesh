package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {

	GamePanel gp;
	Clip clip;
	URL soundURL[] = new URL[30];
	FloatControl fc;
	int volumeScale = 3;
	float volume;
	
	// Add volume type constants
	public static final int VOLUME_MASTER = 0;
	public static final int VOLUME_MUSIC = 1;
	public static final int VOLUME_SFX = 2;
	
	// Add separate volume scales
	private static int masterVolumeScale = 3;
	private static int musicVolumeScale = 3;
	private static int sfxVolumeScale = 3;
	
	// Add volume type for each sound instance
	private int volumeType = VOLUME_MASTER;
	
	// Add instance-specific volume multiplier (0.0 to 1.0)
	private float instanceVolume = 1.0f;

	public Sound() {
		soundURL[0] = getClass().getResource("/sound/BackgroundMusic.wav");
		soundURL[1] = getClass().getResource("/sound/pickupKey.wav");
		soundURL[2] = getClass().getResource("/sound/chestOpen.wav");
		soundURL[3] = getClass().getResource("/sound/powerUp.wav");
		soundURL[4] = getClass().getResource("/sound/pixel-game-over.wav");
		soundURL[5] = getClass().getResource("/sound/stick_slash.wav");
		soundURL[6] = getClass().getResource("/sound/hitmonster.wav");
		soundURL[7] = getClass().getResource("/sound/burning.wav");
		soundURL[8] = getClass().getResource("/sound/gameover.wav");
		soundURL[10] = getClass().getResource("/sound/receivedamage.wav");
		soundURL[11] = getClass().getResource("/sound/levelup.wav");
		soundURL[12] = getClass().getResource("/sound/cursor.wav");
		soundURL[13] = getClass().getResource("/sound/cuttree.wav");
		soundURL[14] = getClass().getResource("/sound/stairs.wav");
		soundURL[15] = getClass().getResource("/sound/dooropen.wav");
		soundURL[16] = getClass().getResource("/sound/mon1.wav");
		soundURL[17] = getClass().getResource("/sound/worm.wav");
		soundURL[18] = getClass().getResource("/sound/foot2.wav");
	}

	// Overloaded method to set file with volume type
	public void setFile(int i, int volumeType) {
		this.volumeType = volumeType;
		setFile(i);
	}

	public void setFile(int i) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
			fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			checkVolume();
		} catch (Exception e) {
		}
	}

	public void play() {
		if (clip != null) {
			clip.start();
		}
	}

	public void loop() {
		if (clip != null) {
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}

	public void stop() {
		if (clip != null) {
			clip.stop();
		}
	}

	public void checkVolume() {
		if (fc != null) {
			// Calculate volume based on type and instance volume
			float baseVolume = getBaseVolumeForType();
			float masterMultiplier = getMasterVolumeMultiplier();
			
			// Apply instance-specific volume
			volume = baseVolume * masterMultiplier * instanceVolume;
			fc.setValue(volume);
		}
	}
	
	// New method to set instance-specific volume (0.0 to 1.0)
	public void setInstanceVolume(float volume) {
		this.instanceVolume = Math.max(0.0f, Math.min(1.0f, volume));
		checkVolume();
	}
	
	// New method to get instance volume
	public float getInstanceVolume() {
		return instanceVolume;
	}
	
	// New method to set volume based on distance (for monsters)
	public void setVolumeByDistance(float distance, float maxDistance) {
		// Volume decreases as distance increases
		float volume = 1.0f - (distance / maxDistance);
		setInstanceVolume(Math.max(0.0f, volume));
	}
	
	// New method to set volume based on position
	public void setVolumeByPosition(int monsterX, int monsterY, int playerX, int playerY, int maxDistance, int tileSize) {
		// Calculate distance between monster and player
		double distance = Math.sqrt(Math.pow(monsterX - playerX, 2) + Math.pow(monsterY - playerY, 2));
		
		// Convert to tile distance
		double tileDistance = distance / tileSize;
		
		// Calculate volume based on distance (closer = louder, farther = quieter)
		float volume = (float) Math.max(0, 1.0 - (tileDistance / maxDistance));
		
		// Apply some curve to make it more natural
		volume = (float) Math.pow(volume, 0.7);
		
		setInstanceVolume(volume);
	}
	
	// New method to set volume for monster type
	public void setMonsterVolume(String monsterType) {
		float baseVolume = 1.0f;
		
		// Different monsters can have different base volumes
		switch (monsterType) {
			case "boss":
				baseVolume = 1.3f; // Boss monsters are louder
				break;
			case "flesh_monster":
				baseVolume = 1.0f; // Standard volume
				break;
			case "small_monster":
				baseVolume = 0.6f; // Small monsters are quieter
				break;
			default:
				baseVolume = 1.0f;
		}
		
		setInstanceVolume(baseVolume);
	}

	private float getBaseVolumeForType() {
		int scale;
		switch (volumeType) {
			case VOLUME_MUSIC:
				scale = musicVolumeScale;
				break;
			case VOLUME_SFX:
				scale = sfxVolumeScale;
				break;
			default:
				scale = volumeScale;
				break;
		}
		
		switch (scale) {
			case 0: return -80f;
			case 1: return -20f;
			case 2: return -12f;
			case 3: return -5f;
			case 4: return 1f;
			case 5: return 6f;
			default: return -5f;
		}
	}
	
	private float getMasterVolumeMultiplier() {
		switch (masterVolumeScale) {
			case 0: return 0.0f;
			case 1: return 0.3f;
			case 2: return 0.6f;
			case 3: return 0.8f;
			case 4: return 0.9f;
			case 5: return 1.0f;
			default: return 0.8f;
		}
	}
	
	// Static volume control methods
	public static void setMasterVolume(int scale) {
		masterVolumeScale = Math.max(0, Math.min(5, scale));
	}
	
	public static void setMusicVolume(int scale) {
		musicVolumeScale = Math.max(0, Math.min(5, scale));
	}
	
	public static void setSfxVolume(int scale) {
		sfxVolumeScale = Math.max(0, Math.min(5, scale));
	}
	
	public static int getMasterVolume() {
		return masterVolumeScale;
	}
	
	public static int getMusicVolume() {
		return musicVolumeScale;
	}
	
	public static int getSfxVolume() {
		return sfxVolumeScale;
	}
	
	public void updateVolume() {
		if (clip != null && clip.isOpen()) {
			checkVolume();
		}
	}
	
	// Set GamePanel reference for tile size
	public void setGamePanel(main.GamePanel gp) {
		this.gp = gp;
	}
}