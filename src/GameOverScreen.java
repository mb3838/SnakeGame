import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class GameOverScreen extends Screen{
    private BufferedImage gameOver, back, backHover, backCurrent;
    private Rect gameOverRect, backRect;
    // this list will contain 2 numbers - the first number is the players last score and the second is the high-score
    private ArrayList<Integer> scoreList = new ArrayList<>();
    private Font scoreFont;
    public GameMouseListener mouseListener;

    public GameOverScreen(GameMouseListener mouseListener){
        this.mouseListener = mouseListener;

        try{
            // try to open game over and back image files
            gameOver = ImageIO.read(new File("assets/RED_GAMEOVER.png"));
            back = ImageIO.read(new File("assets/RED_BACK.png"));
            backHover = ImageIO.read(new File("assets/WHITE_BACK.png"));
            // get last score and highs-core from score text file & add them to the score array list
            File scoreFile = new File("assets/scores.txt");
            Scanner scoreReader = new Scanner(scoreFile);
            while(scoreReader.hasNextInt()){
                scoreList.add(scoreReader.nextInt());
            }
            // try to open the font file
            scoreFont = Font.createFont(Font.TRUETYPE_FONT, new File("assets/Eight-Bit_Madness.ttf")).deriveFont(40f);
        }catch(Exception e){
            e.printStackTrace();
        }

        // initialise back images (not hovered)
        backCurrent = back;

        gameOverRect = new Rect((float)(Constants.SCREEN_WIDTH/2 - gameOver.getWidth()/2), 100, gameOver.getWidth(), gameOver.getHeight());
        backRect = new Rect((float)(Constants.SCREEN_WIDTH/2 - back.getWidth()/2), 500, back.getWidth(), back.getHeight());
    }

    @Override
    public void update(double deltaTime){
        // check if the mouse is hovering over the back image
        if(mouseListener.getX() >= backRect.getX() && mouseListener.getX() <= backRect.getX() + backRect.getWidth() &&
                mouseListener.getY() >= backRect.getY() && mouseListener.getY() <= backRect.getY() + backRect.getHeight()){
            backCurrent = backHover;
            // if mouse is pressed on back image - change state to menu state
            if(mouseListener.isPressed()){
                GameWindow.getWindow().changeState(0);
            }
        // if back image is no longer being hovered over - change to normal back image
        }else{
            backCurrent = back;
        }
    }

    @Override
    public void draw(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0,0,Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        // add game over text image
        g.drawImage(gameOver, (int)gameOverRect.getX(), (int)gameOverRect.getY(), null);
        // add player score text
        g.setColor(Color.WHITE);
        g.setFont(scoreFont);
        g.drawString("Score:  " + scoreList.get(0), (int)gameOverRect.getX(), 270);
        // add high-score text
        g.drawString("High-Score:  " + scoreList.get(1), (int)gameOverRect.getX(), 350);
        // add back text image
        g.drawImage(backCurrent, (int)backRect.getX(), (int)backRect.getY(), null);
    }
}
