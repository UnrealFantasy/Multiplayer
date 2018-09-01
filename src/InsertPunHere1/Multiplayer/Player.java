package InsertPunHere1.Multiplayer;

import java.awt.event.KeyEvent;

public class Player {
    private static final int FRAMES = 4;

    private int health = 9;

    private String name;

    private int width, height;

    private int x, y;

    private int scale;

    private boolean idle = false;

    private Sheet sheet;

    private int frame = 0, sequence = 0;

    Player(Sheet sheet, String name, int width, int height, int x, int y, int scale) {
        this.width = width;
        this.height = height;

        this.x = x;
        this.y = y;

        this.scale = scale;

        this.sheet = sheet;

        this.name = name;
    }

    void render(int[] pixels) {
        int[] sprite;

        if (idle)
            sprite = sheet.getSprite(0, sequence);
        else
            sprite = sheet.getSprite(frame, sequence);

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

                        int pixel = sprite[(x / scale) + (y / scale) * Sheet.SPRITE_WIDTH];

                        if (pixel == 0xFFFF00FF)
                            continue;

                        pixels[fx + fy * width] = pixel;
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

        boolean idle = true;

        if (input.getKey(KeyEvent.VK_W) || input.getKey(KeyEvent.VK_UP)) {
            level.yOffset++;

            y--;

            sequence = 0;

            idle = false;
        }

        if (input.getKey(KeyEvent.VK_A) || input.getKey(KeyEvent.VK_LEFT)) {
            level.xOffset++;

            x--;

            sequence = 1;

            idle = false;
        }

        if (input.getKey(KeyEvent.VK_S) || input.getKey(KeyEvent.VK_DOWN)) {
            level.yOffset--;

            y++;

            sequence = 2;

            idle = false;
        }

        if (input.getKey(KeyEvent.VK_D) || input.getKey(KeyEvent.VK_RIGHT)) {
            level.xOffset--;

            x++;

            sequence = 3;

            idle = false;
        }

        this.idle = idle;
    }

    int getX() {
        return x;
    }

    void setX(int x) {
        this.x = x;
    }

    int getY() {
        return y;
    }

    void setY(int y) {
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

    public int getFrame() {
        return frame;
    }

    void setFrame(int frame) {
        this.frame = frame;
    }

    int getSequence() {
        return sequence;
    }

    void setSequence(int sequence) {
        this.sequence = sequence;
    }

    String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
