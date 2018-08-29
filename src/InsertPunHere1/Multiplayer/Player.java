package InsertPunHere1.Multiplayer;

import java.awt.event.KeyEvent;

public class Player {
    private static final int FRAMES = 4;

    private int width, height;

    private int x, y;

    private int scale;

    private boolean idle = false;

    private Sheet sheet;

    private int frame = 0, sequence = 0;

    Player(Sheet sheet, int width, int height, int x, int y, int scale) {
        this.width = width;
        this.height = height;

        this.x = x;
        this.y = y;

        this.scale = scale;

        this.sheet = sheet;
    }

    void render(int[] pixels) {
        int[] sprite = sheet.getSprite(frame, sequence);

        int px = (width / 2) - (Sheet.SPRITE_WIDTH * scale);
        int py = (height / 2) - (Sheet.SPRITE_HEIGHT * scale);

        for (int x = 0; x <= (Sheet.SPRITE_WIDTH - 1) * scale; x += scale) {
            for (int y = 0; y <= (Sheet.SPRITE_HEIGHT - 1) * scale; y += scale) {
                int mx = px + x;
                int my = py + y;

                for (int sx = 0; sx < scale; sx++) {
                    for (int sy = 0; sy < scale; sy++) {
                        int fx = mx + sx;
                        int fy = my + sy;

                        pixels[fx + fy * width] = sprite[(x / scale) + (y / scale) * Sheet.SPRITE_WIDTH];
                    }
                }
            }
        }
    }

    void tick(Input input, Level level, int tickCount) {
        if (tickCount % 8 == 0) {
            if (frame <= FRAMES)
                frame++;

            if (frame == FRAMES)
                frame = 0;
        }

        if (input.getKey(KeyEvent.VK_W) || input.getKey(KeyEvent.VK_UP)) {
            level.yOffset++;

            sequence = 0;
        }

        if (input.getKey(KeyEvent.VK_A) || input.getKey(KeyEvent.VK_LEFT)) {
            level.xOffset++;

            sequence = 1;
        }

        if (input.getKey(KeyEvent.VK_S) || input.getKey(KeyEvent.VK_DOWN)) {
            level.yOffset--;

            sequence = 2;
        }

        if (input.getKey(KeyEvent.VK_D) || input.getKey(KeyEvent.VK_RIGHT)) {
            level.xOffset--;

            sequence = 3;
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public boolean isIdle() {
        return idle;
    }

    public void setIdle(boolean idle) {
        this.idle = idle;
    }

    public Sheet getSheet() {
        return sheet;
    }

    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }
}
