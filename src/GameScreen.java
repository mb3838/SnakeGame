import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

public class GameScreen extends Screen{
    // rectangles for background colour and foreground colour/panel for the snake to be on
    Rect background, gameArea;
    Snake snake;
    GameKeyListener keyListener;
    public Food food;
    public BufferedImage scoreTitle;
    public Font scoreFont;

    public GameScreen(GameKeyListener keyListener){
        this.keyListener = keyListener;
        background = new Rect(0,0,Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT);
        // grid of 31 columns and 25 rows - width and height of 24
        // x = (screen width - (24*31)) / 2
        // y = (screen height - (24*25)) - x
        gameArea = new Rect(Constants.GAME_AREA_X,Constants.GAME_AREA_Y,Constants.GAME_AREA_WIDTH,Constants.GAME_AREA_HEIGHT);
        snake = new Snake(5,Constants.GAME_AREA_X + Constants.CELL_WIDTH, Constants.GAME_AREA_Y+Constants.CELL_HEIGHT, Constants.CELL_WIDTH,Constants.CELL_HEIGHT, gameArea);
        food = new Food(gameArea, snake, 12,12, Color.RED);
        food.spawn();

        try{
            // try to open image file for score title
            scoreTitle = ImageIO.read(new File("assets/WHITE_SCORE.png"));
            // try to open font file for score text
            scoreFont = Font.createFont(Font.TRUETYPE_FONT, new File("assets/Eight-Bit_Madness.ttf")).deriveFont(40f);
        }catch(Exception e){
            e.printStackTrace();
        }
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
        // spawn food if no current food is spawned
        if(!food.isSpawned){
            food.spawn();
        }
        snake.update(deltaTime);
        food.update(deltaTime);
    }

    @Override
    public void draw(Graphics g){
        // cast graphics object to graphics2D object
        Graphics2D g2D = (Graphics2D)g;
        // background colour
        g2D.setColor(Color.BLACK);
        g2D.draw(new Rectangle2D.Double(background.x, background.y, background.width, background.height));
        // game area outline colour
        g2D.setColor(Color.WHITE);
        g2D.draw(new Rectangle2D.Double(gameArea.x, gameArea.y, gameArea.width, gameArea.height));

        // draw snake
        snake.draw(g2D);
        // draw food
        food.draw(g2D);
    }
}
