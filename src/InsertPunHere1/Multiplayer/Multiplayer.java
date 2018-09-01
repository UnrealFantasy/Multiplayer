package InsertPunHere1.Multiplayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.Random;

public class Multiplayer extends Canvas implements Runnable {
    private static final int TARGET_WIDTH = 1270;
    private static final int TARGET_HEIGHT = 700;

    private static int SCALE = 6;

    private static final int WIDTH = TARGET_WIDTH / SCALE;
    private static final int HEIGHT = TARGET_HEIGHT / SCALE;

    private static final String TITLE = "Multiplayer";

    private JFrame frame;

    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    private Player player;

    private Level level;

    private HUD HUD = new HUD(new Sheet("/guiSpriteSheet.png"), WIDTH, HEIGHT);

    private Input input;

    private boolean running = false;

    private boolean shouldRender = false;

    private Multiplayer() throws IOException {
        setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

        if(frame != null)
            frame.dispose();

        frame = new JFrame(TITLE);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.setLayout(new BorderLayout());
        frame.add(this, BorderLayout.CENTER);

        frame.pack();

        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);

        input = new Input();

        addKeyListener(input);
    }

    private synchronized void initialize() {

    }

    private synchronized void start() {
        running = true;

        new Thread(this).start();

        JOptionPane.showMessageDialog(this, "Assets finished loading.");

        String name = JOptionPane.showInputDialog("Enter your desired username: ");

        player = new Player(new Sheet("/playerSpriteSheet.png"), name, WIDTH, HEIGHT, 0, 0, 2);

        level = new Level("/level.lvl", new Sheet("/levelSpriteSheet.png"), player, WIDTH, HEIGHT);

        shouldRender = true;
    }

    private synchronized void stop() {
        running = false;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        long lastTime = System.nanoTime();

        double nsPerTick = 1000000000D / 60D;

        int frames = 0, ticks = 0;

        long lastTimer = System.currentTimeMillis();

        double delta = 0;

        while (running) {
            long now = System.nanoTime();

            delta += (now - lastTime) / nsPerTick;

            lastTime = now;

            while (delta >= 1) {
                tick();

                ticks++;

                delta--;
            }

            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            frames++;

            render();

            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;

                System.out.println("FPS: " + frames + ", UPS (TPS): " + ticks);

                frames = ticks = 0;
            }
        }
    }

    private int tickCount = 0;

    private void tick() {
        tickCount++;

        if(shouldRender) {
            player.tick(input, level, tickCount);

            HUD.tick(input);

            if(input.getKey(KeyEvent.VK_0) && tickCount % 8 == 0) {
                level.write("./resources/level.lvl", player);

                System.out.println("Save completed.");
            }

            if(input.getKey(KeyEvent.VK_R) && tickCount % 8 == 0) {
                for(int index = 0; index < Level.levelSize * Level.levelSize; index++) {
                    int tile = new Random().nextInt(10);

                    while(tile == Level.Tiles.WATER)
                        tile = new Random().nextInt(10);

                    level.getTiles()[index] = tile;
                }
            }

            if(input.getKey(KeyEvent.VK_M) && tickCount % 8 == 0)
                player.setHealth(player.getHealth() + 1);

            if(input.getKey(KeyEvent.VK_N) && tickCount % 8 == 0)
                player.setHealth(player.getHealth() - 1);

            for (int index = 0; index < pixels.length; index++)
                pixels[index] = 0;
        }else {
            for(int index = 0; index < pixels.length; index++)
                pixels[index] = index + tickCount;
        }
    }

    private void render() {
        BufferStrategy bufferStrategy = getBufferStrategy();

        if (bufferStrategy == null) {
            createBufferStrategy(3);

            return;
        }

        if(shouldRender) {
            level.render(pixels);

            player.render(pixels);

            HUD.render(player, pixels);
        }

        Graphics graphics = bufferStrategy.getDrawGraphics();

        graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);

        graphics.dispose();

        bufferStrategy.show();
    }

    public static void main(String[] args) throws IOException {
        new Multiplayer().start();
    }
}
