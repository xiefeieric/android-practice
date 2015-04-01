package eric.com.lingchat;

/**
 * Created by Eric on 27/03/2015.
 */
public class ListData {

    private String content;
    public static final int SEND = 1;
    public static final int RECEIVER = 2;
    private int flag;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ListData(String content, int flag, String time) {
        this.content = content;
        this.flag = flag;
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
