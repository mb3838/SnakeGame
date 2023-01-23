public class Rect {

    private double x, y, width, height;
    private Direction direction;
    public Rect(double x, double y, double width, double height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rect(double x, double y, double width, double height, Direction direction){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.direction = direction;
    }

    public Direction getDirection(){
        return this.direction;
    }

    public void setDirection(Direction direction){
        this.direction = direction;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
