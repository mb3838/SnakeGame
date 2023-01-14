import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

public class MenuScreen extends Screen {

    public GameKeyListener keyListener;
    public GameMouseListener mouseListener;
    public BufferedImage title, play, playHover, exit, exitHover;
    public BufferedImage playCurrent, exitCurrent;
    public Rect titleRect, playRect, exitRect;
    public MenuScreen(GameKeyListener keyListener, GameMouseListener mouseListener){
        this.keyListener = keyListener;
        this.mouseListener = mouseListener;

        // try to open image file
        try{
            title = ImageIO.read(new File("assets/WHITE_SNAKE.png"));
            play = ImageIO.read(new File("assets/WHITE_PLAY.png"));
            playHover = ImageIO.read(new File("assets/RED_PLAY.png"));
            exit = ImageIO.read(new File("assets/WHITE_EXIT.png"));
            exitHover = ImageIO.read(new File("assets/RED_EXIT.png"));
        }catch(Exception e){
            e.printStackTrace();
        }

        // initialise current play and exit images (not pressed)
        playCurrent = play;
        exitCurrent = exit;

        titleRect = new Rect((float)(Constants.SCREEN_WIDTH/2 - title.getWidth()/2), 100, title.getWidth(), title.getHeight());
        playRect = new Rect((float)(Constants.SCREEN_WIDTH/2 - play.getWidth()/2), 270, play.getWidth(), play.getHeight());
        exitRect = new Rect((float)(Constants.SCREEN_WIDTH/2 - exit.getWidth()/2), 340, exit.getWidth(), exit.getHeight());
    }
    @Override
    public void update(double deltaTime){
        // check if the mouse is hovering over the play image
        if(mouseListener.getX() >= playRect.x && mouseListener.getX() <= playRect.x + playRect.width &&
            mouseListener.getY() >= playRect.y && mouseListener.getY() <= playRect.y + playRect.height){
            playCurrent = playHover;
            // if mouse is pressed on play image - change state to game state
            if(mouseListener.isPressed()){
                GameWindow.getWindow().changeState(1);
            }
        // if play image is no longer being hovered over - go back to normal play image
        }else{
            playCurrent = play;
        }
        // check if the mouse is hovering over the exit image
        if(mouseListener.getX() >= exitRect.x && mouseListener.getX() <= exitRect.x + exitRect.width &&
                mouseListener.getY() >= exitRect.y && mouseListener.getY() <= exitRect.y + exitRect.height){
            exitCurrent = exitHover;
            // if mouse is pressed on exit image - exit game
            if(mouseListener.isPressed()){
                GameWindow.getWindow().close();
            }
        // if exit image is no longer being hovered over - go back to normal exit image
        }else{
        exitCurrent = exit;
        }
    }

    @Override
    public void draw(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0,0,Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        // add game title
        g.drawImage(title, (int)titleRect.x, (int)titleRect.y, null);
        // add play
        g.drawImage(playCurrent, (int)playRect.x, (int)playRect.y, null);
        // add exit
        g.drawImage(exitCurrent, (int)exitRect.x, (int)exitRect.y, null);
    }
}
