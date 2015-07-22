package eric.com.game2048;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by Eric on 24/03/2015.
 */
public class Card extends FrameLayout {

    private int num = 0;
    private TextView label;

    public Card(Context context) {
        super(context);


        label = new TextView(getContext());
        label.setTextSize(35);
        label.setTextColor(Color.BLACK);
//        label.setPadding(50,10,10,10);
        label.setBackgroundColor(0x33ffffff);
        label.setGravity(Gravity.CENTER);

        LayoutParams lp = new LayoutParams(-1,-1);
        lp.setMargins(10,10,0,0);
        addView(label, lp);

//        setNum(0);
    }


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
        if (getNum()<=0) {
            label.setText("");
        } else {
            label.setText(num+"");
        }

    }

    public boolean equals(Card card) {
        return getNum() == card.getNum();
    }


}
