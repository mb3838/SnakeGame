import java.awt.Graphics2D;
import java.awt.Color;

public class Food {

    private Rect background, foodRect;
    private int width, height;
    private Color color;
    public Snake snake;
    private int padding;

    private boolean isSpawned;


    public Food(Rect background, Snake snake, int width, int height, Color color){
        this.background = background;
        this.foodRect = new Rect(0,0,width,height);
        this.width = width;
        this.height = height;
        this.color = color;
        this.snake = snake;
        padding = (int) ((Constants.CELL_WIDTH - this.width) / 2.0);
        isSpawned = false;
    }

    // spawn food within a grid tile that is not occupied by the snake
    public void spawn(){
        // generate a random position for the food until one is found that doesn't intersect with snake
        do{
            double randomX = (int)(Math.random() * (int)(background.getWidth() / Constants.CELL_WIDTH)) * Constants.CELL_WIDTH + background.getX();
            double randomY = (int)(Math.random() * (int)(background.getHeight() / Constants.CELL_HEIGHT)) * Constants.CELL_HEIGHT + background.getY();
            this.foodRect.setX(randomX);
            this.foodRect.setY(randomY);
        }while(snake.intersectingRect(this.foodRect));
        this.isSpawned = true;
    }

    public void draw(Graphics2D g2D){
        g2D.setColor(Color.RED);
        g2D.fillRect((int)this.foodRect.getX() + padding,(int)this.foodRect.getY() + padding,width,height);
    }

    public void update(double deltaTime){
        // check if snake has hit food
        if(snake.intersectingRect(this.foodRect)){
            snake.grow();
            // move food off-screen until respawned
            this.foodRect.setX(1000);
            this.foodRect.setY(1000);
            isSpawned = false;
        }
    }

    public boolean isSpawned() {
        return isSpawned;
    }
}
