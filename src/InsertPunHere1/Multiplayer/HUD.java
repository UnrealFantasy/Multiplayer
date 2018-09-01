package InsertPunHere1.Multiplayer;

import java.awt.event.KeyEvent;

class HUD {
    private static final int SLOTS = 9;

    private int selectedSlot = 0;

    private int width, height;

    private int xOffset, yOffset;

    private Sheet sheet;

    HUD(Sheet sheet, int width, int height) {
        this.width = width;
        this.height = height;

        this.sheet = sheet;

        xOffset = (width - SLOTS * Sheet.SPRITE_WIDTH) / 2;
        yOffset = height - 12;
    }

    void render(Player player, int[] pixels) {
        int[] sprite = sheet.getSprite(0, 0);

        for (int ix = 0; ix < SLOTS; ix++) {
            for (int sx = 0; sx < Sheet.SPRITE_WIDTH; sx++) {
                for (int sy = 0; sy < Sheet.SPRITE_HEIGHT; sy++) {
                    int px = (ix * Sheet.SPRITE_WIDTH) + sx + xOffset;

                    int py = sy + yOffset;

                    if (px >= width)
                        continue;

                    if (px < 0)
                        continue;

                    if (py >= height)
                        continue;

                    if (py < 0)
                        continue;

                    int pixel = sprite[sx + sy * Sheet.SPRITE_WIDTH];

                    if (pixel == 0xFFFF00FF)
                        continue;

                    pixels[px + py * width] = pixel;
                }
            }
        }

        sprite = sheet.getSprite(1, 0);

        for (int sx = 0; sx < Sheet.SPRITE_WIDTH; sx++) {
            for (int sy = 0; sy < Sheet.SPRITE_HEIGHT; sy++) {
                int px = (selectedSlot * Sheet.SPRITE_WIDTH) + sx + xOffset;

                int py = sy + yOffset;

                if (px >= width)
                    continue;

                if (px < 0)
                    continue;

                if (py >= height)
                    continue;

                if (py < 0)
                    continue;

                int pixel = sprite[sx + sy * Sheet.SPRITE_WIDTH];

                if (pixel == 0xFFFF00FF)
                    continue;

                pixels[px + py * width] = pixel;
            }
        }

        int xOffsetHealth = xOffset;
        int yOffsetHealth = yOffset - 8;

        sprite = sheet.getSprite(2, 0);

        for (int index = 0; index < player.getHealth(); index++) {
            for (int sx = 0; sx < Sheet.SPRITE_WIDTH; sx++) {
                for (int sy = 0; sy < Sheet.SPRITE_HEIGHT; sy++) {
                    int px = (index * Sheet.SPRITE_WIDTH) + sx + xOffsetHealth;

                    int py = sy + yOffsetHealth;

                    if (px >= width)
                        continue;

                    if (px < 0)
                        continue;

                    if (py >= height)
                        continue;

                    if (py < 0)
                        continue;

                    int pixel = sprite[sx + sy * Sheet.SPRITE_WIDTH];

                    if (pixel == 0xFFFF00FF)
                        continue;

                    pixels[px + py * width] = pixel;
                }
            }
        }

        sprite = sheet.getSprite(4, 0);

        for (int index = player.getHealth(); index >= player.getHealth(); index--) {
            for (int sx = 0; sx < Sheet.SPRITE_WIDTH; sx++) {
                for (int sy = 0; sy < Sheet.SPRITE_HEIGHT; sy++) {
                    int px = (index * Sheet.SPRITE_WIDTH) + sx + xOffsetHealth;

                    int py = sy + yOffsetHealth;

                    if (px >= width)
                        continue;

                    if (px < 0)
                        continue;

                    if (py >= height)
                        continue;

                    if (py < 0)
                        continue;

                    int pixel = sprite[sx + sy * Sheet.SPRITE_WIDTH];

                    if (pixel == 0xFFFF00FF)
                        continue;

                    pixels[px + py * width] = pixel;
                }
            }
        }
    }

    void tick(Input input) {
        if (input.getKey(KeyEvent.VK_1))
            selectedSlot = 0;
        if (input.getKey(KeyEvent.VK_2))
            selectedSlot = 1;
        if (input.getKey(KeyEvent.VK_3))
            selectedSlot = 2;
        if (input.getKey(KeyEvent.VK_4))
            selectedSlot = 3;
        if (input.getKey(KeyEvent.VK_5))
            selectedSlot = 4;
        if (input.getKey(KeyEvent.VK_6))
            selectedSlot = 5;
        if (input.getKey(KeyEvent.VK_7))
            selectedSlot = 6;
        if (input.getKey(KeyEvent.VK_8))
            selectedSlot = 7;
        if (input.getKey(KeyEvent.VK_9))
            selectedSlot = 8;
    }
}
