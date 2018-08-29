package InsertPunHere1.Multiplayer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Level {
    private static int levelSize = 128;

    private int[] tiles = new int[levelSize * levelSize];

    private Sheet sheet;

    private int width, height;

    int xOffset = (width / 2), yOffset = (width / 2);

    static class Tiles {
        static final int WATER = 0;
        static final int GRASS = 1;
        static final int WOOD = 2;
        static final int STONE = 3;
        static final int METAL = 4;
        static final int IRON = 5;
        static final int GOLD = 6;
        static final int DIAMOND = 7;
        static final int OBSIDIAN = 8;
    }

    Level(String file, Sheet sheet, int width, int height) {
        this.sheet = sheet;

        this.width = width;
        this.height = height;

        try {
            tiles = read(file);
        }catch(IOException e) {
            e.printStackTrace();
        }

        xOffset += levelSize * Sheet.SPRITE_WIDTH;
        yOffset += levelSize * Sheet.SPRITE_HEIGHT;
    }

    Level(Sheet sheet, int width, int height) {
        for (int lx = 0; lx < levelSize; lx++) {
            for (int ly = 0; ly < levelSize; ly++) {
                tiles[lx + ly * levelSize] = Tiles.IRON;
            }
        }

        this.sheet = sheet;

        this.width = width;
        this.height = height;
    }

    private int[] read(String file) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(file));

        List<Integer> xTiles = new ArrayList<>();
        List<Integer> yTiles = new ArrayList<>();

        List<Integer> zTiles = new ArrayList<>();

        for (String line : lines) {
            String[] tokens = line.split(" ");

            switch(tokens[0]) {
                case "t":
                    xTiles.add(Integer.parseInt(tokens[1]));
                    yTiles.add(Integer.parseInt(tokens[2]));

                    zTiles.add(Integer.parseInt(tokens[3]));

                    break;
                default:
                    break;

                    // TODO: File format features.
            }
        }

        int greatestX = xTiles.get(0);

        for (Integer xTile : xTiles) {
            if (xTile > greatestX)
                greatestX = xTile;
        }

        int greatestY = yTiles.get(0);

        for (Integer yTile : yTiles) {
            if (yTile > greatestY)
                greatestY = yTile;
        }

        greatestX++;
        greatestY++;

        if(greatestX != greatestY)
            throw new IllegalStateException("Level file size must be square.");

        levelSize = greatestX;

        int index = 0;

        int[] tiles = new int[levelSize * levelSize];

        for(Integer zTile : zTiles)
            tiles[index++] = zTile;

        return tiles;
    }

    void render(int[] pixels) {
        for (int lx = 0; lx < levelSize; lx++) {
            for (int ly = 0; ly < levelSize; ly++) {
                int[] sprite = sheet.getSprite(tiles[lx + ly * levelSize], 0);

                for (int sx = 0; sx < Sheet.SPRITE_WIDTH; sx++) {
                    for (int sy = 0; sy < Sheet.SPRITE_HEIGHT; sy++) {
                        int px = (lx * Sheet.SPRITE_WIDTH) + sx + xOffset;
                        int py = (ly * Sheet.SPRITE_HEIGHT) + sy + yOffset;

                        if (px >= width)
                            continue;

                        if (px < 0)
                            continue;

                        if (py >= height)
                            continue;

                        if (py < 0)
                            continue;

                        pixels[px + py * width] = sprite[sx + sy * Sheet.SPRITE_WIDTH];
                    }
                }
            }
        }
    }

    int[] getTiles() {
        return tiles;
    }

    public void setTiles(int[] tiles) {
        this.tiles = tiles;
    }

    public Sheet getSheet() {
        return sheet;
    }

    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
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
}
