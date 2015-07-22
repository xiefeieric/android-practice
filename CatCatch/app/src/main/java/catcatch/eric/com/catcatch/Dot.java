package catcatch.eric.com.catcatch;

/**
 * Created by Eric on 23/03/2015.
 */
public class Dot {

    private int x;
    private int y;
    private int status;

    public static final int STATUS_OFF = 0;
    public static final int STATUS_ON = 1;
    public static final int STATUS_IN = 9;

    public Dot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setXY(int x,int y) {
        this.y = y;
        this.x = x;
    }
}
