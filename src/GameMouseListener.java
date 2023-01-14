import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class GameMouseListener extends MouseAdapter implements MouseMotionListener {
    public boolean isPressed = false;
    double x = 0.0, y = 0.0;

    @Override
    public void mousePressed(MouseEvent e){
        isPressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e){
        isPressed = false;
    }

    @Override
    public void mouseMoved(MouseEvent e){
        this.x = e.getX();
        this.y = e.getY();
    }

    // return x/y of where the mouse is on the screen
    public double getX(){
        return this.x;
    }
    public double getY(){
        return this.y;
    }

    // return if the mouse is currently pressed
    public boolean isPressed(){
        return this.isPressed;
    }
}
