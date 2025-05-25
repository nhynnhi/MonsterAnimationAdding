package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import main.GamePanel;

public class Trap extends Entity { 
    private final GamePanel gp;
    public int worldX, worldY;
    public int screenX, screenY;
    public int width, height;
    public boolean isSolid = true;

    public Trap(GamePanel gp, int worldX, int worldY) {
        super(gp);
        this.gp = gp;
        this.worldX = worldX;
        this.worldY = worldY;
        this.width = 32; // Set the width of the trap
        this.height = 32; // Set the height of the trap
        this.collisionBox.width = width; // Set the width of the collision box
        this.collisionBox.height = height; // Set the height of the collision box
        this.collisionBox.x = worldX; // Set the x position of the collision box
        this.collisionBox.y = worldY; // Set the y position of the collision box
    }

    public void draw(Graphics2D g2d) {
        screenX = worldX - gp.mainCharacter.worldX + gp.mainCharacter.screenX;
        screenY = worldY - gp.mainCharacter.worldY + gp.mainCharacter.screenY;

        g2d.setColor(Color.YELLOW);
        g2d.fillRect(screenX, screenY, width, height);
    }

    public void checkCollision() {
        if (gp.mainCharacter.collisionBox.intersects(this.collisionBox)) {
            gp.mainCharacter.isAlive = false; // Main character dies
        }
    }
}
