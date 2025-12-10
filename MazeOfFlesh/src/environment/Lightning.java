package environment;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class Lightning {

	GamePanel gp;
	BufferedImage fogFilter;

	public Lightning(GamePanel gp) {
		this.gp = gp;
		setLightSource();

	}

	private void setLightSource() {

		// CREATE a bufferedImage
		fogFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) fogFilter.getGraphics();

		if (gp.player.currentLight == null) {
			g2.setColor(new Color(0, 0, 0, 240));
		} else {
			// GET THE CENTER OF THE LIGHT AREA
			int centerX = gp.player.screenX + (gp.tileSize) / 2;
			int centerY = gp.player.screenY + (gp.tileSize) / 2;

			// Create a gradation effect within the light
			Color color[] = new Color[12];
			float fraction[] = new float[12];

			color[11] = new Color(0, 0, 0, 220);
			color[10] = new Color(0, 0, 0, 220);
			color[9] = new Color(0, 0, 0, 210);
			color[8] = new Color(0, 0, 0, 200);
			color[7] = new Color(0, 0, 0, 190);
			color[6] = new Color(0, 0, 0, 180);
			color[5] = new Color(0, 0, 0, 170);
			color[4] = new Color(0, 0, 0, 160);
			color[3] = new Color(0, 0, 0, 150);
			color[2] = new Color(0, 0, 0, 140);
			color[1] = new Color(0, 0, 0, 130);
			color[0] = new Color(0, 0, 0, 100);

			fraction[0] = 0f;
			fraction[1] = 0.4f;
			fraction[2] = 0.5f;
			fraction[3] = 0.6f;
			fraction[4] = 0.65f;
			fraction[5] = 0.7f;
			fraction[6] = 0.75f;
			fraction[7] = 0.8f;
			fraction[8] = 0.85f;
			fraction[9] = 0.9f;
			fraction[10] = 0.95f;
			fraction[11] = 0.97f;

			// Create a gradation paint settings for the light circle
			RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY, gp.player.currentLight.lightRadius,
					fraction, color);

			// Set gradient on g2
			g2.setPaint(gPaint);
		}

		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

		g2.dispose();

	}

	public void update() {

		if (gp.player.lightUpdated == true) {
			setLightSource();
			gp.player.lightUpdated = false;
		}
	}

	public void draw(Graphics2D g2) {

		if (gp.currentArea == gp.safeArea) {
			// Slight darkener overlay
			g2.setColor(new Color(0, 0, 0, 50));
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

		} else if (gp.currentArea == gp.mazeArea) {
			g2.drawImage(fogFilter, 0, 0, null);
		}
	}
}
