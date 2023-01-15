import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame implements Runnable {

    // the window is a global variable but its wrapped so that the rest of the class is not global
    // initialise static window - this will be the object returned from creating the window
    // this window is static so that not all variables and methods in this class need to be static - it can just be returned that way
    // designed as there should only ever be 1 window - singleton
    // it is initially null as no window has been created yet
    public static GameWindow window = null;
    public boolean gameRunning;
    // current state
    public  int currentState;
    // current screen
    public  Screen currentScreen;
    public  GameKeyListener keyListener = new GameKeyListener();
    // static mouse listener to pass into screen classes
    public  GameMouseListener mouseListener = new GameMouseListener();
    public GameWindow(){
        setSize(Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT);
        setTitle("Snake Game");
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // register listeners
        addKeyListener(keyListener);
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);
        setBackground(Color.BLACK);
        //set game running to true as default
        gameRunning = true;
        // start in menu state
        changeState(0);
    }

    // created so we can call this method from anywhere in the code and can call the other methods in this class w/o making them static
    // this is what will be called in order to create a window
    // this method will in turn call the constructor and return a static window
    // if the window has already been created - it will return the window
    public static GameWindow getWindow(){
        // if no window has been created yet (window still null) - create the window
        if(GameWindow.window == null){
            GameWindow.window = new GameWindow();
        }
        return GameWindow.window;
    }

    public void close(){
        gameRunning = false;
    }

    public void changeState(int newState){
        currentState = newState;
        // tell us which screen to build based on which state we're in
        switch(currentState){
            // draw main menu
            case 0:
                currentScreen = new MenuScreen(keyListener, mouseListener);
                break;
            // draw game screen
            case 1:
                currentScreen = new GameScreen(keyListener);
                break;
            // draw game over screen
            case 2:
                currentScreen = new GameOverScreen(mouseListener);
                break;
            // should never be reached - throw exception
            default:
                System.out.print("Unknown screen");
                currentScreen = null;
                break;
        }
    }

    public void update(double deltaTime){
        // double buffer image
        Image dbImage = createImage(getWidth(), getHeight());
        Graphics dbg = dbImage.getGraphics();
        this.draw(dbg);
        getGraphics().drawImage(dbImage,0,0,this);

        currentScreen.update(deltaTime);
    }

    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D)g;

        currentScreen.draw(g);
    }

    @Override
    public void run() {
        double lastFrameTime = 0.0;
        //game loop
        try{
            while (gameRunning) {
                double time = Time.getTime();
                double deltaTime = time - lastFrameTime;
                lastFrameTime = time;
                update(deltaTime);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        // close window if game isn't running/exit pressed
        GameWindow.window.dispose();
    }
}
