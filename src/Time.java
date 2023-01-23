public class Time {
    // used to handle the frame-rate and decide when to call the update and draw functions - to re-draw the screen
    public static double timeStarted = System.nanoTime();

    public static double getTime(){
        return (System.nanoTime() - timeStarted) * 1E-9;
    }
}
