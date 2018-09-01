package InsertPunHere1.Multiplayer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;

public class Sheet {
    static final int SPRITE_WIDTH = 8;
    static final int SPRITE_HEIGHT = 8;

    private int width, height;

    private String file;

    private int[] sheet;

    private int[][] sprites;

    Sheet(String file) {
        this.file = file;

        try {
            BufferedImage image = ImageIO.read(Sheet.class.getResourceAsStream(file));

            this.width = image.getWidth();
            this.height = image.getHeight();

            this.sheet = image.getRGB(0, 0, width, height, null, 0, width);

            this.sprites = new int[(width / SPRITE_WIDTH) * (height / SPRITE_HEIGHT)][SPRITE_WIDTH * SPRITE_HEIGHT];

            for (int tx = 0; tx < width / SPRITE_WIDTH; tx++) {
                for (int ty = 0; ty < height / SPRITE_HEIGHT; ty++) {
                    sprites[tx + ty * (width / SPRITE_WIDTH)] = image.getSubimage(tx * SPRITE_WIDTH, ty * SPRITE_HEIGHT, SPRITE_WIDTH, SPRITE_HEIGHT).getRGB(0, 0, SPRITE_WIDTH, SPRITE_HEIGHT, null, 0, SPRITE_WIDTH);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    int[] getSprite(int tx, int ty) {
        while(tx > (width / SPRITE_WIDTH))
            ty++;

        int[] sprite = sprites[tx + ty * (width / SPRITE_WIDTH)];

        int whitePixels = 0;

        for(int sx = 0; sx < SPRITE_WIDTH; sx++) {
            for(int sy = 0; sy < SPRITE_HEIGHT; sy++) {
                if(sprite[sx + sy * SPRITE_WIDTH] == new Color(255, 255, 255).getRGB())
                    whitePixels++;
            }
        }

        if(whitePixels == SPRITE_WIDTH * SPRITE_HEIGHT)
            sprite = sprites[1];

        return sprite;
    }

    int getPixel(int[] sprite, int px, int py) {
        return sprite[px + py * SPRITE_WIDTH];
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public int[] getSheet() {
        return sheet;
    }

    public void setSheet(int[] sheet) {
        this.sheet = sheet;
    }

    public int[][] getSprites() {
        return sprites;
    }

    public void setSprites(int[][] sprites) {
        this.sprites = sprites;
    }
}
