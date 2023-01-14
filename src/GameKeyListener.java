import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameKeyListener extends KeyAdapter implements KeyListener {
    // store the keys being pressed at that moment - ascii character codes has 128 codes - true if being pressed
    private boolean[] keyPressed = new boolean[128];

    @Override
    public void keyPressed(KeyEvent keyEvent){
        keyPressed[keyEvent.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent keyEvent){
        keyPressed[keyEvent.getKeyCode()] = false;
    }

    // return whether the key is pressed or not
    public boolean isKeyPressed(int keyCode){
        return keyPressed[keyCode];
    }
}
