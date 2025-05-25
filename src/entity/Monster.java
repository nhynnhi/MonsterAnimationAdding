package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Monster extends Entity {
    private int movingLength;
    private int initialX;
    private BufferedImage[] leftImages = new BufferedImage[7];
    private BufferedImage[] rightImages = new BufferedImage[7];
    
    public Monster(GamePanel gp, int worldX, int worldY) {
        super(gp);
        this.worldX = worldX;
        this.worldY = worldY;
        setDefaultValue();
        movingLength = 2 * gp.tileSize;
        initialX = worldX;
        this.collisionBox.width = 48; // Set the width of the collision box
        this.collisionBox.height = 48; // Set the height of the collision box
        getImage();
    }

    public void setDefaultValue() {
        speedX = 0;
    }

    private BufferedImage setup(String string) throws IOException {
        return ImageIO.read(getClass().getResourceAsStream(string + ".png"));
    }

    public void getImage() {
        try {
            leftImages[0] = setup("/res/monster/left/1");
            leftImages[1] = setup("/res/monster/left/2");
            leftImages[2] = setup("/res/monster/left/3");
            leftImages[3] = setup("/res/monster/left/4");
            leftImages[4] = setup("/res/monster/left/5");
            leftImages[5] = setup("/res/monster/left/6");
            leftImages[6] = setup("/res/monster/left/7");

            rightImages[0] = setup("/res/monster/right/1");
            rightImages[1] = setup("/res/monster/right/2");
            rightImages[2] = setup("/res/monster/right/3");
            rightImages[3] = setup("/res/monster/right/4");
            rightImages[4] = setup("/res/monster/right/5");
            rightImages[5] = setup("/res/monster/right/6");
            rightImages[6] = setup("/res/monster/right/7");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setInitialX(int initialX) {
        this.initialX = initialX;
    }

    public void setMovingLength(int movingLength) {
        this.movingLength = movingLength;
    }

    public void draw(Graphics2D monster) {
        if (isAlive) {
            screenX = worldX - gp.mainCharacter.worldX + gp.mainCharacter.screenX;
            screenY = worldY - gp.mainCharacter.worldY + gp.mainCharacter.screenY;
            // Animation logic
            int frameCount = leftImages.length;
            int frameIndex = (int)((System.currentTimeMillis() / 100) % frameCount);

            BufferedImage currentImage;
            if (direction.equals("left")) {
                currentImage = leftImages[frameIndex];
            } else {
                currentImage = rightImages[frameIndex];
            }
            monster.drawImage(currentImage, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }

    public void update() {
        if (isAlive) {
            if (worldX >= initialX + movingLength) {
                speedX = -1; // Move left
                direction = "left";
            } else if (worldX <= initialX) {
                speedX = 1; // Move right
                direction = "right";
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
