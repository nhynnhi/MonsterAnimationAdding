package entity;

import main.GamePanel;
import java.awt.Rectangle;


public class Entity {
    protected GamePanel gp;
    protected int worldX, worldY;      // World coordinates
    protected int screenX, screenY;    // Screen coordinates

    protected int speedX = 0;
    protected int speedY = 0;
    protected String direction = "right";
    protected String oldAction = "idle";
    protected boolean grounded = false;
    protected boolean[] collisionOn = new boolean[5]; // 1 = up, 2 = down, 3 = left, 4 = right check direction of collision
    protected int[] collisionTile = new int[5]; // 1 = up, 2 = down, 3 = left, 4 = right check tile number of collision
    protected Rectangle collisionBox;
    protected boolean isAlive = true;

    public Entity(GamePanel gp) {
        this.gp = gp;

        // Initialize collision box
        collisionBox = new Rectangle(0, 0, 32, 32); // Default size, can be adjusted
    }

    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public int getSpeedX() {
        return speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public Rectangle getCollisionBox() {
        return collisionBox;
    }

    public boolean[] getCollisionOn() {
        return collisionOn;
    }

    public int[] getCollisionTile() {
        return collisionTile;
    }

    public void setCollisionBox(Rectangle collisionBox) {
        this.collisionBox = collisionBox;
    }

    public void setCollisionOn(int index, boolean collisionOn) {
        this.collisionOn[index] = collisionOn;
    }

    public void setCollisionTile(int index, int value) {
        this.collisionTile[index] = value;
    }
}
