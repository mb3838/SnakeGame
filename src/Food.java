import java.awt.Graphics2D;
import java.awt.Color;

public class Food {

    public Rect background, foodRect;
    public int width, height;
    public Color color;
    public Snake snake;
    public int padding;
    public boolean isSpawned;


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
            double randomX = (int)(Math.random() * (int)(background.width / Constants.CELL_WIDTH)) * Constants.CELL_WIDTH + background.x;
            double randomY = (int)(Math.random() * (int)(background.height / Constants.CELL_HEIGHT)) * Constants.CELL_HEIGHT + background.y;
            this.foodRect.x = randomX;
            this.foodRect.y = randomY;
        }while(snake.intersectingRect(this.foodRect));
        this.isSpawned = true;
    }

    public void draw(Graphics2D g2D){
        g2D.setColor(Color.RED);
        g2D.fillRect((int)this.foodRect.x + padding,(int)this.foodRect.y + padding,width,height);
    }

    public void update(double deltaTime){
        // check if snake has hit food
        if(snake.intersectingRect(this.foodRect)){
            snake.grow();
            // move food off-screen until respawned
            this.foodRect.x = 1000;
            this.foodRect.y = 1000;
            isSpawned = false;
        }
    }
}
