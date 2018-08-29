package InsertPunHere1.Multiplayer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener {
    private boolean[] keys = new boolean[255];

    private int[] keyPresses = new int[255];

    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key typed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key pressed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of
     * a key released event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;

        keyPresses[e.getKeyCode()]++;
    }

    boolean getKey(int keyCode) {
        return keys[keyCode];
    }

    public boolean getKey(char keyChar) {
        return keys[keyChar];
    }

    public int getNumberOfPresses(int keyCode) {
        return keyPresses[keyCode];
    }

    public int getNumberOfPresses(char keyChar) {
        return keyPresses[keyChar];
    }

    public boolean[] getKeys() {
        return keys;
    }

    public void setKeys(boolean[] keys) {
        this.keys = keys;
    }

    public int[] getKeyPresses() {
        return keyPresses;
    }

    public void setKeyPresses(int[] keyPresses) {
        this.keyPresses = keyPresses;
    }
}
