package eric.com.lingchat.Utility;

/**
 * Created by Eric on 27/03/2015.
 */
import android.content.Context;
import android.widget.Toast;

public class T {

    private final static boolean isShow = true;

    private T(){
        throw new UnsupportedOperationException("T cannot be instantiated");
    }

    public static void showShort(Context context,CharSequence text) {
        if(isShow)Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }

    public static void showLong(Context context,CharSequence text) {
        if(isShow)Toast.makeText(context,text,Toast.LENGTH_LONG).show();
    }
}
