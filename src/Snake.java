import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class Snake {
    // array of rectangles to hold the body pieces - singly linked list
    private Rect[] body = new Rect[100];
    // width and height of the snake's individual body pieces - 24*24 grid size
    private double bodyWidth, bodyHeight;
    // snake size - how many body pieces in its length
    private int size;
    private int tailIndex = 0;
    private int headIndex = 0;
    private int score;
    private Rect gameArea;
    // variable frame-rate - how much time we wait before each piece moves
    private double waitBetweenUpdates = 0.1f;
    // how much time we have left before the pieces move
    private double waitTimeLeft = waitBetweenUpdates;
    public Snake(int size, double startX, double startY, double bodyWidth, double bodyHeight, Rect gameArea){
        this.size = size;
        this.bodyWidth = bodyWidth;
        this.bodyHeight = bodyHeight;
        this.gameArea = gameArea;
        this.score = 0;
        // initialise array that holds snake body
        for(int i = 0; i <= size; i++){
            // increment x to one body piece to the right after each loop
            Rect bodyPiece = new Rect(startX + i * bodyWidth, startY, bodyWidth, bodyHeight, Direction.RIGHT);
            body[i] = bodyPiece;
            // head is the last index of the array - rightmost piece of snake
            headIndex++;
        }
        // minus 1 to get the correct index (as array index starts at 0)
        headIndex--;
    }

    public void grow(){
        //append new rectangle to tail of array in direction snake is travelling in

        double newX = 0;
        double newY = 0;
        Direction newDirection = Direction.RIGHT;
        // get x & y of 1 behind the tail - in the direction of the current tail piece
        if(body[tailIndex].getDirection() == Direction.RIGHT){
            newX = body[tailIndex].getX() - bodyWidth;
            newY = body[tailIndex].getY();
            newDirection = Direction.RIGHT;
        }else if(body[tailIndex].getDirection() == Direction.LEFT){
            newX = body[tailIndex].getX() + bodyWidth;
            newY = body[tailIndex].getY();
            newDirection = Direction.LEFT;
        }else if(body[tailIndex].getDirection() == Direction.UP){
            newX = body[tailIndex].getX();
            newY = body[tailIndex].getY() + bodyHeight;
            newDirection = Direction.UP;
        }else if(body[tailIndex].getDirection() == Direction.DOWN){
            newX = body[tailIndex].getX();
            newY = body[tailIndex].getY() - bodyHeight;
            newDirection = Direction.DOWN;
        }

        Rect newBodyPiece = new Rect(newX, newY, bodyWidth, bodyHeight, newDirection);
        // if the old tail's index is the start of the array (index 0) - new tail piece index should be at the end of the array
        if(tailIndex == 0){
            tailIndex = body.length - 1;
        }
        // get new tail index one position before old tail
        tailIndex = tailIndex - 1;
        body[tailIndex] = newBodyPiece;

        score += 10;
    }

    public boolean intersectingBoundary(Rect head){
        return (head.getX() < gameArea.getX() || (head.getX() + head.getWidth()) > gameArea.getX() + gameArea.getWidth() ||
                head.getY() < gameArea.getY() || (head.getY() + head.getHeight()) > gameArea.getY() + gameArea.getHeight());
    }

    public boolean intersectingSelf(){
        Rect head = body[headIndex];
        // for every rectangle inside the snake body (except for the head) check if it's colliding with the head
        return intersectingRect(head) || intersectingBoundary(head);
    }

    public boolean intersectingRect(Rect rect){
        // for every rectangle inside the snake body check if it's colliding with the rectangle
        for(int i = tailIndex; i != headIndex; i = (i + 1) % body.length){
            if(intersecting(rect, body[i])){
                return true;
            }
        }
        return false;
    }

    // check whether 2 rectangles are intersecting
    public boolean intersecting(Rect r1, Rect r2){
        return (r1.getX() >= r2.getX() && r1.getX() + r1.getWidth() <= r2.getX() + r2.getWidth() &&
                r1.getY() >= r2.getY() && r1.getY() + r1.getHeight() <= r2.getY() + r2.getHeight());
    }

    // player controller
    public void changeDirection(Direction newDirection){
        // don't allow user to go back on themselves
        // check that the current direction that the snake head is moving in is not the opposite of what the user has pressed
        if(body[headIndex].getDirection() == Direction.LEFT && newDirection != Direction.RIGHT) {
            body[headIndex].setDirection(newDirection);
        }else if(body[headIndex].getDirection() == Direction.RIGHT && newDirection != Direction.LEFT) {
            body[headIndex].setDirection(newDirection);
        }else if(body[headIndex].getDirection() == Direction.UP && newDirection != Direction.DOWN) {
            body[headIndex].setDirection(newDirection);
        }else if(body[headIndex].getDirection() == Direction.DOWN && newDirection != Direction.UP) {
            body[headIndex].setDirection(newDirection);
        }
    }

    public void update(double deltaTime){
        if(waitTimeLeft > 0){
            // minus how much time has passed within this frame
            waitTimeLeft -= deltaTime;
            // get out of this function
            return;
        }

        // check if the snake's head is intersecting with its body
        if(intersectingSelf()){
            // game over - write player score to score text file & change state to main menu
            ArrayList<String> scoreList = new ArrayList<>();
            try{
                File scoreFile = new File("assets/scores.txt");
                Scanner scoreReader = new Scanner(scoreFile);
                while(scoreReader.hasNext()){
                    scoreList.add(scoreReader.next());
                }
                // replace last score
                scoreList.set(0, Integer.toString(score));
                // if last score is higher than high-score - replace high-score
                if(score > Integer.parseInt(scoreList.get(1))){
                    scoreList.set(1, Integer.toString(score));
                }
                Files.write(Path.of("assets/scores.txt"), scoreList, StandardCharsets.UTF_8);
            }catch(Exception e){
                e.printStackTrace();
            }
            GameWindow.getWindow().changeState(2);
        }

        // we've just waited the wait time - so reset it again
        waitTimeLeft = waitBetweenUpdates;

        // new x and y to move the tail piece to - picking up body piece at the tail and moving it to 1 in front of head
        double newX = 0;
        double newY = 0;
        Direction currentDirection = body[headIndex].getDirection();
        Direction newDirection = Direction.RIGHT;

        // get the head position and increment it 1 piece in correct direction
        if(currentDirection == Direction.RIGHT){
            newX = body[headIndex].getX() + bodyWidth;
            newY = body[headIndex].getY();
            newDirection = Direction.RIGHT;
        }else if(currentDirection == Direction.LEFT){
            newX = body[headIndex].getX() - bodyWidth;
            newY = body[headIndex].getY();
            newDirection = Direction.LEFT;
        }else if(currentDirection == Direction.UP){
            newX = body[headIndex].getX();
            newY = body[headIndex].getY() - bodyHeight;
            newDirection = Direction.UP;
        }else if(currentDirection == Direction.DOWN){
            newX = body[headIndex].getX();
            newY = body[headIndex].getY() + bodyHeight;
            newDirection = Direction.DOWN;
        }


        // pick up the tail piece and put it in the head's position plus 1 mod body length (1 in front of head)
        // shifting the array over by 1 (to the right) e.g. [1,1,1,0,0] = [0,1,1,1,0]
        body[(headIndex + 1) % body.length] = body[tailIndex];
        body[tailIndex] = null;
        // set new index for head and tail
        headIndex = (headIndex + 1) % body.length;
        tailIndex = (tailIndex + 1) % body.length;
        // update head location and direction to the new piece in front
        body[headIndex].setX(newX);
        body[headIndex].setY(newY);
        body[headIndex].setDirection(newDirection);
    }

    public void draw(Graphics2D g2D){
        // increment index by 1 but if the snake wraps around the end of the array it will loop back to index 0 e.g. [1,1,0,0,1,1,1]
        // e.g. if the snake wraps from the end of the list to the start - when it checks the last index of array it will be directed to 0 (start of array) (as array length mod array length = 0)
        for(int i = tailIndex; i != headIndex; i = (i + 1) % body.length){
            // get the piece of snake
            Rect piece = body[i];

            // check if the piece is the head
            if(i == headIndex-1){
                // make the head of the snake a slightly lighter green colour
                g2D.setColor(Color.GREEN);
            }else{
                g2D.setColor(new Color(49, 197, 0));
            }
            // update the direction of the tail piece of the snake
            if(i == tailIndex-1){
                // set the current direction of the tail by looking at
            }

            g2D.fill(new Rectangle2D.Double(piece.getX(), piece.getY(), piece.getWidth(), piece.getHeight()));
        }
    }
}
