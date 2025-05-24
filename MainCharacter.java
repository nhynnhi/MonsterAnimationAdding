package entity;

import java.awt.Color;
import java.awt.Graphics2D;

import logic.KeyHandler;
import main.GamePanel;
import main.Main;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class MainCharacter extends Entity {
    private static MainCharacter instance = null;
    private final int collisionGap = (gp.tileSize - collisionBox.width) / 2;
    private int clock = 0;
    private int delay = 0;
    private BufferedImage leftIdle1, leftIdle2, leftIdle3, leftIdle4, rightIdle1, rightIdle2, rightIdle3, rightIdle4;
    private BufferedImage leftJump1, leftJump2, leftJump3, leftJump4, leftJump5, leftJump6, rightJump1, rightJump2, rightJump3, rightJump4, rightJump5, rightJump6;
    private BufferedImage leftRun1, leftRun2, leftRun3, rightRun1, rightRun2, rightRun3;

    void setDefaultValue() {
        screenX = gp.screenWidth / 2;
        screenY = gp.screenHeight / 2;
        worldX = gp.tileSize * 5 + collisionGap;
        worldY = 48 * 11 + 40;
        speedX = 0;
        speedY = 0;
    }

    void getImage() {
        try {
            leftIdle1 = setup("/res/maincharacter/idle/left/1");
            leftIdle2 = setup("/res/maincharacter/idle/left/2");
            leftIdle3 = setup("/res/maincharacter/idle/left/3");
            leftIdle4 = setup("/res/maincharacter/idle/left/4");
            rightIdle1 = setup("/res/maincharacter/idle/right/1");
            rightIdle2 = setup("/res/maincharacter/idle/right/2");
            rightIdle3 = setup("/res/maincharacter/idle/right/3");
            rightIdle4 = setup("/res/maincharacter/idle/right/4");

            leftJump1 = setup("/res/maincharacter/jump/left/1");
            leftJump2 = setup("/res/maincharacter/jump/left/2");
            leftJump3 = setup("/res/maincharacter/jump/left/3");
            leftJump4 = setup("/res/maincharacter/jump/left/4");
            leftJump5 = setup("/res/maincharacter/jump/left/5");
            leftJump6 = setup("/res/maincharacter/jump/left/6");
            rightJump1 = setup("/res/maincharacter/jump/right/1");
            rightJump2 = setup("/res/maincharacter/jump/right/2");
            rightJump3 = setup("/res/maincharacter/jump/right/3");
            rightJump4 = setup("/res/maincharacter/jump/right/4");
            rightJump5 = setup("/res/maincharacter/jump/right/5");
            rightJump6 = setup("/res/maincharacter/jump/right/6");

            leftRun1 = setup("/res/maincharacter/run/left/1");
            leftRun2 = setup("/res/maincharacter/run/left/2");
            leftRun3 = setup("/res/maincharacter/run/left/3");
            rightRun1 = setup("/res/maincharacter/run/right/1");
            rightRun2 = setup("/res/maincharacter/run/right/2");
            rightRun3 = setup("/res/maincharacter/run/right/3");
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error loading tile image: " + e.getMessage());
        }
    }

    private BufferedImage setup(String string) throws IOException {
        return ImageIO.read(getClass().getResourceAsStream(string + ".png"));
    }

    public static MainCharacter getInstance(GamePanel gp) {
        if (instance == null) {
            instance = new MainCharacter(gp);
        }
        return instance;
    }

    private MainCharacter(GamePanel gp) {
        super(gp);
        setDefaultValue();
        getImage();
    }

    private void handleMovementInput(KeyHandler keyH) {
        if (isAlive) {
            if (keyH.upPressed && collisionOn[2] == true && delay > 5) {
                speedY = -20;
                delay = 0;
            }

            if (keyH.leftPressed) {
                speedX = -5;
            } else if (keyH.rightPressed) {
                speedX = 5;
            }

            if (speedY < 0 && collisionOn[1] == true) {
                speedY = 0;
                worldY = (collisionTile[1] + 1) * gp.tileSize + 1;
            }

            if (!keyH.leftPressed && !keyH.rightPressed) {
                speedX = 0;
            } else {
                if (collisionOn[4] == true) {
                    worldX = (collisionTile[4] - 1) * gp.tileSize + collisionGap - 6;
                } else if (collisionOn[3] == true) {
                    worldX = collisionTile[3] * gp.tileSize - collisionGap + 6;
                }
            }
            if(speedX > 0){
                direction = "right";
            }else if (speedX < 0) {
                direction = "left";
            }
            worldY += speedY;
            worldX += speedX;
        }
    }

    private void falling() {
        if (collisionOn[2] == false) {
            speedY += 1;
            delay = 0;
        } else if (collisionOn[2] == true) {
            speedY = 0;
            worldY = (collisionTile[2] - 1) * gp.tileSize;
            delay++;
        }

    }

    private void drawIdle(Graphics2D g2) {
        if (oldAction != "idle") {
            clock = 0;
            oldAction = "idle";
        }
        BufferedImage imgToDraw = null;
        int phase = (clock % 40) / 10;
        switch (phase) {
            case 0: imgToDraw = (direction.equals("left")) ? leftIdle1 : rightIdle1; break;
            case 1: imgToDraw = (direction.equals("left")) ? leftIdle2 : rightIdle2; break;
            case 2: imgToDraw = (direction.equals("left")) ? leftIdle3 : rightIdle3; break;
            case 3: imgToDraw = (direction.equals("left")) ? leftIdle4 : rightIdle4; break;
        }
        if (imgToDraw != null) {
            g2.drawImage(imgToDraw, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }

    private void drawRun(Graphics2D g2) {
        if (oldAction != "run") {
            clock = 0;
            oldAction = "run";
        }
        BufferedImage imgToDraw = null;
        int phase = (clock % 18) / 6;
        switch (phase) {
            case 0: imgToDraw = (direction.equals("left")) ? leftRun1 : rightRun1; break;
            case 1: imgToDraw = (direction.equals("left")) ? leftRun2 : rightRun2; break;
            case 2: imgToDraw = (direction.equals("left")) ? leftRun3 : rightRun3; break;
        }
        if (imgToDraw != null) {
            g2.drawImage(imgToDraw, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }

    private void drawJump(Graphics2D g2) {
        if (oldAction != "jump") {
            clock = 0;
            oldAction = "jump";
        }
        BufferedImage imgToDraw = null;
        int calClock;
        int phase;
        if (clock % 60 < 1) {
            phase = 0;
        }else if (clock % 60 < 5) {
            phase = 1;
        }else if (clock % 60 < 20) {
            phase = 2;
        }else if (clock % 60 < 30) {
            phase = 3;
        }else if (clock % 60 < 40) {
            phase = 4;
        }else {
            phase = 5;
        }
        
        switch (phase) {
            case 0: imgToDraw = (direction.equals("left")) ? leftJump1 : rightJump1; break;
            case 1: imgToDraw = (direction.equals("left")) ? leftJump2 : rightJump2; break;
            case 2: imgToDraw = (direction.equals("left")) ? leftJump3 : rightJump3; break;
            case 3: imgToDraw = (direction.equals("left")) ? leftJump4 : rightJump4; break;
            case 4: imgToDraw = (direction.equals("left")) ? leftJump5 : rightJump5; break;
            case 5: imgToDraw = (direction.equals("left")) ? leftJump6 : rightJump6; break;
        }
        if (imgToDraw != null) {
            g2.drawImage(imgToDraw, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }

    private void imageDisplayLogic(Graphics2D g2) {
        if (speedX == 0 && collisionOn[2] == true) {
            drawIdle(g2);
        } else if (collisionOn[2] == false) {
            drawJump(g2);
        } else if (speedX != 0 && collisionOn[2] == true) {
            drawRun(g2);
        }
    }

    public void draw(Graphics2D g2) {
        if (isAlive) {
            imageDisplayLogic(g2);
        }
    }

    public void update(KeyHandler keyH) {
        clock++;
        collisionBox.x = worldX + collisionGap;
        collisionBox.y = worldY + gp.tileSize - collisionBox.height - 1;
        gp.collisionChecker.checkTile(this);
        falling();
        // System.out.println(worldX + "\t" + worldY);
        // System.out.println("Collision on: " + collisionOn[1] + "\t" + collisionOn[2]
        // + "\t" + collisionOn[3] + "\t" + collisionOn[4] + "\t" + speedX);
        // System.out.println("SpeedX: " + speedX + "\tSpeedY: " + speedY);
        handleMovementInput(keyH);
    }
}
