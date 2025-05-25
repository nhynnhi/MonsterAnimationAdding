package logic;

import entity.Entity;
import main.GamePanel;

public class CollisionChecker {
    private final GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        // Initialize collision states: 0 = no collision
        for (int i = 0; i < 5; i++) {
            entity.setCollisionOn(i, false);
        }
        int entityX = entity.getCollisionBox().x;
        int entityY = entity.getCollisionBox().y;
        int entityXend = entityX + entity.getCollisionBox().width;
        int entityYend = entityY + entity.getCollisionBox().height;
        
        int entityLeftCol = entityX / gp.tileSize;
        int entityRightCol = entityXend / gp.tileSize;
        int entityTopRow = entityY / gp.tileSize;
        int entityBottomRow = entityYend / gp.tileSize;
        
        int tileNum1;
        int tileNum2;
        
        // Upward collision check
        if (entity.getSpeedY() < 0) {
            entityTopRow = (entityY - gp.mainCharacter.getSpeedY()) / gp.tileSize - 1;

            if (entityTopRow < 0) {
                entity.setCollisionOn(1, true);
                entity.setCollisionTile(1, 1);
            } else {
                tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityTopRow];
                if (gp.tileManager.tile[tileNum1].isSolid() || gp.tileManager.tile[tileNum2].isSolid()) {
                    entity.setCollisionOn(1, true);
                    entity.setCollisionTile(1, entityTopRow);
                }
            }
            entityTopRow = entityY / gp.tileSize;
        }
        
        // Downward collision check
        if (entity.getSpeedY() >= 0) {
            entityBottomRow = (entityYend + gp.mainCharacter.getSpeedY() + 1) / gp.tileSize;
            if (entityBottomRow >= gp.tileManager.mapTileNum[0].length) {
                entity.setCollisionOn(2, true);
                entity.setCollisionTile(2, gp.tileManager.mapTileNum[0].length);
            } else {
                tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileManager.tile[tileNum1].isSolid() || gp.tileManager.tile[tileNum2].isSolid()) {
                    entity.setCollisionOn(2, true);
                    entity.setCollisionTile(2, entityBottomRow);
                }
            }
            entityBottomRow = entityYend / gp.tileSize;
        }

        // Leftward collision check
        if (entity.getSpeedX() < 0) {
            if (entityX < 0) {
                entity.setCollisionOn(3, true);
                entity.setCollisionTile(3, 0);
            } else {
                entityLeftCol = (entityX + gp.mainCharacter.getSpeedX()) / gp.tileSize;

                tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
                if (gp.tileManager.tile[tileNum1].isSolid() || gp.tileManager.tile[tileNum2].isSolid()) {
                    entity.setCollisionOn(3, true);
                    entity.setCollisionTile(3, entityLeftCol + 1);
                }

                entityLeftCol = entityX / gp.tileSize;
            }
        }

        // Rightward collision check
        if (entity.getSpeedX() > 0) {
            entityRightCol = (entityXend + gp.mainCharacter.getSpeedX()) / gp.tileSize;
            if (entityRightCol >= gp.tileManager.mapTileNum.length) {
                entity.setCollisionOn(4, true);
                entity.setCollisionTile(4, gp.tileManager.mapTileNum.length);
            } else {
                tileNum1 = gp.tileManager.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileManager.tile[tileNum1].isSolid() || gp.tileManager.tile[tileNum2].isSolid()) {
                    entity.setCollisionOn(4, true);
                    entity.setCollisionTile(4, entityRightCol);
                }
            }
            entityRightCol = entityXend / gp.tileSize;
        }


    }
}
