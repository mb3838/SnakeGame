public class Main {
    public static void main(String[] args) {
        GameWindow window = GameWindow.getWindow();
        Thread thread = new Thread(window);
        thread.start();
    }
}