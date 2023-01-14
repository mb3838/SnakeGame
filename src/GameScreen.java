import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

public class GameScreen extends Screen{
    // rectangles for background colour and foreground colour/panel for the snake to be on
    Rect background, foreground;
    Snake snake;
    GameKeyListener keyListener;

    public GameScreen(GameKeyListener keyListener){
        this.keyListener = keyListener;
        background = new Rect(0,0,Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT);
        // grid of 31 columns and 22 rows - width and height of 24
        // x = (screen width - (24*31)) / 2
        // y = (screen height - (24*22)) - x
        foreground = new Rect(28,94,24*31,24*22);
        snake = new Snake(5,52, 94+24, 24,24);
    }
    @Override
    public void update(double deltaTime){
        // check which arrow the user is pressing
        if(keyListener.isKeyPressed(KeyEvent.VK_UP)){
            snake.changeDirection(Direction.UP);
        }else if(keyListener.isKeyPressed(KeyEvent.VK_DOWN)){
            snake.changeDirection(Direction.DOWN);
        }else if(keyListener.isKeyPressed(KeyEvent.VK_LEFT)){
            snake.changeDirection(Direction.LEFT);
        }else if(keyListener.isKeyPressed(KeyEvent.VK_RIGHT)){
            snake.changeDirection(Direction.RIGHT);
        }

        snake.update(deltaTime);
    }

    @Override
    public void draw(Graphics g){
        // cast graphics object to graphics2D object
        Graphics2D g2D = (Graphics2D)g;
        // background colour
        g2D.setColor(Color.BLACK);
        g2D.draw(new Rectangle2D.Double(background.x, background.y, background.width, background.height));
        // foreground colour
        g2D.setColor(Color.WHITE);
        g2D.draw(new Rectangle2D.Double(foreground.x, foreground.y, foreground.width, foreground.height));

        // draw snake
        snake.draw(g2D);
    }
}
