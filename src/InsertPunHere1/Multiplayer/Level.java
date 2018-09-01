package InsertPunHere1.Multiplayer;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Level {
    public static int levelSize = 128;

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

    Level(String file, Sheet sheet, Player player, int width, int height) {
        this(sheet, width, height);

        this.sheet = sheet;

        this.width = width;
        this.height = height;


    }

    Level(Sheet sheet, int width, int height) {
        this.sheet = sheet;

        this.width = width;
        this.height = height;

        for (int lx = 0; lx < levelSize; lx++) {
            for (int ly = 0; ly < levelSize; ly++) {
                if (lx % 4 == 0 && ly % 4 == 0)
                    tiles[lx + ly * levelSize] = Tiles.STONE;
                else if (lx % 6 == 0 && ly % 6 == 0)
                    tiles[lx + ly * levelSize] = Tiles.METAL;
                else if (lx % 7 == 0 && ly % 7 == 0)
                    tiles[lx + ly * levelSize] = Tiles.IRON;
                else if (lx % 9 == 0 && ly % 9 == 0)
                    tiles[lx + ly * levelSize] = Tiles.DIAMOND;
                else
                    tiles[lx + ly * levelSize] = Tiles.GRASS;
            }
        }
    }

    private void read(String file, Player player) throws IOException, URISyntaxException {
        List<String> lines = Files.readAllLines(Paths.get(this.getClass().getResource(file).toURI()));

        List<Integer> xTiles = new ArrayList<>();
        List<Integer> yTiles = new ArrayList<>();

        List<Integer> zTiles = new ArrayList<>();

        for (String line : lines) {
            String[] tokens = line.split("\\s+");

            switch (tokens[0]) {
                case "tile":
                    xTiles.add(Integer.parseInt(tokens[1]));
                    yTiles.add(Integer.parseInt(tokens[2]));

                    zTiles.add(Integer.parseInt(tokens[3]));

                    break;
                case "player":
                    switch (tokens[2]) {
                        case "position":
                            player.setX(Integer.parseInt(tokens[3]));
                            player.setY(Integer.parseInt(tokens[4]));

                            xOffset = -player.getX();
                            yOffset = -player.getY();
                        case "sequence":
                            player.setSequence(Integer.parseInt(tokens[3]));
                            break;
                        case "health":
                            player.setHealth(Integer.parseInt(tokens[3]));
                        case "frame":
                            player.setFrame(Integer.parseInt(tokens[3]));
                            break;
                    }

                    break;
                default:
                    // TODO: File format features;

                    break;
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

        if (greatestX != greatestY)
            throw new IllegalStateException("Level file failed to be loaded.");

        levelSize = greatestX;

        int index = 0;

        for (Integer zTile : zTiles)
            tiles[index++] = zTile;
    }

    void write(String file, Player player) {
        try {
            FileWriter fileWriter = new FileWriter(new File(file));

            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.printf("\nplayer position %s %d %d", player.getName(), player.getX(), player.getY());
            printWriter.printf("\nplayer sequence %s %d", player.getName(), player.getSequence());
            printWriter.printf("\nplayer health %s %d", player.getName(), player.getHealth());

            for (int lx = 0; lx < levelSize; lx++) {
                for (int ly = 0; ly < levelSize; ly++) {
                    printWriter.printf("\ntile %d %d %d", lx, ly, tiles[lx + ly * levelSize]);
                }
            }

            fileWriter.flush();

            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
