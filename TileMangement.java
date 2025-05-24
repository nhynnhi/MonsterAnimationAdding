package logic;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;

import entity.Tile;
import main.GamePanel;

public class TileMangement {
    private GamePanel gp;

    protected Tile[] tile;
    protected int[][] mapTileNum;

    public TileMangement(GamePanel gp) {
        this.gp = gp;
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        loadMap("/res/maps/map1.txt");
        loadTile();
    }

    private void loadTile() {
        tile = new Tile[2];
        tile[0] = new Tile(gp,0);
        tile[1] = new Tile(gp,1);
    }

    private void loadMap(String filename) {
        try {
            InputStream is = getClass().getResourceAsStream(filename);
            
            if (is == null) {
                throw new IllegalArgumentException("Map file not found: " + filename);
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (row < gp.maxWorldRow) {
                String line = br.readLine();

                while (col < gp.maxWorldCol) {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                col = 0;
                row++;
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2d) {
        for (int j = 0; j < gp.maxWorldRow; j++) {
            for (int i = 0; i < gp.maxScreenCol; i++) {
                int worldX = i * gp.tileSize;  //tileCordinate
                int worldY = j * gp.tileSize;  //tileCordinate
                int screenX = worldX - gp.mainCharacter.getWorldX() + gp.screenWidth/2;
                int screenY = worldY - gp.mainCharacter.getWorldY() + gp.screenHeight/2;

                if( worldX > gp.mainCharacter.getWorldX() - gp.screenWidth/2 - gp.tileSize &&
                    worldX < gp.mainCharacter.getWorldX() + gp.screenWidth/2 + gp.tileSize &&
                    worldY > gp.mainCharacter.getWorldY() - gp.screenHeight/2 - gp.tileSize &&
                    worldY < gp.mainCharacter.getWorldY() + gp.screenHeight/2 + gp.tileSize
                ){
                    tile[mapTileNum[i][j]].draw(g2d, screenX, screenY);
                }
            }
        }
    }

}
