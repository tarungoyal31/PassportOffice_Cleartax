package passportscheduling;

/**
 * Handles the current time in PassportTimer.
 * TODO: Make this class singleTon.
 */
public class PassportTimer {
    
    public static final int TOTAL_TIME = 8 * 60;
    public static final int MAX_ADMISSION_TIME = 4 * 60;
    private int currentTime;
    
    public PassportTimer() {
        currentTime = 0;
    }
    
    public int getTime() {
        return currentTime;
    }
    
    public void incrementMinute() {
        currentTime += 1;
    }
}
