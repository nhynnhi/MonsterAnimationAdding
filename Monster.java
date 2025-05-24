package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import main.GamePanel;

public class Monster extends Entity{
    private int movingLength;
    private int initialX;

    public void setDefaultValue() {
        speedX = 0;
    }

    public Monster(GamePanel gp, int worldX, int worldY) {
        super(gp);
        this.worldX = worldX;
        this.worldY = worldY;
        setDefaultValue();
        movingLength = 2 * gp.tileSize;
        initialX = worldX;
        this.collisionBox.width = 48; // Set the width of the collision box
        this.collisionBox.height = 48; // Set the height of the collision box
    }

    public void setInitialX(int initialX) {
        this.initialX = initialX;
    }

    public void setMovingLength(int movingLength) {
        this.movingLength = movingLength;
    }

    public void draw(Graphics2D monster) {
        if(isAlive) {
            screenX = worldX - gp.mainCharacter.worldX + gp.mainCharacter.screenX;
            screenY = worldY - gp.mainCharacter.worldY + gp.mainCharacter.screenY;
            monster.setColor(Color.WHITE);
            monster.fillRect(screenX, screenY, collisionBox.width, collisionBox.height);
        }
    }

    public void update(){
        if (isAlive) {
            if (worldX >= initialX + movingLength) {
                speedX = -1; // Move left
            } else if (worldX <= initialX) {
                speedX = 1; // Move right
            }
            worldX += speedX;

            checkCollision();
        }
    }

    public void checkCollision() {
        collisionBox.setLocation(worldX, worldY);

        if (isAlive && gp.mainCharacter.collisionBox.intersects(this.collisionBox)) {
            int mainBottom = gp.mainCharacter.worldY + gp.tileSize;
            int monsterTop = this.worldY;

            if (mainBottom >= monsterTop && gp.mainCharacter.speedY > 0) {
                isAlive = false;
                gp.mainCharacter.speedY = -10; // Bounce up after kill
            } else {
                gp.mainCharacter.isAlive = false;
            }
        }
    }
}
