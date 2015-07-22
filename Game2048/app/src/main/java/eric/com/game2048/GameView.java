package eric.com.game2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

import java.util.Vector;

import static eric.com.game2048.MainActivity.getMainActivity;

/**
 * Created by Eric on 24/03/2015.
 */
public class GameView extends GridLayout implements View.OnTouchListener {

    private int bestScore=0;
    private Card [][] cards = new Card [4][4];
    private float startX,startY,offSetX,offSetY;
    private Vector<Point> cardss = new Vector<>();


    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs) {

        super(context, attrs);
        initGameView();
    }

    public GameView(Context context) {
        super(context);
        initGameView();

    }

    protected void initGameView() {
        setOnTouchListener(this);
        setColumnCount(4);
        setBackgroundColor(0xffbbada0);

    }


    public boolean onTouch(View v, MotionEvent event) {


//        System.out.println(event.getX()+"------------"+event.getY());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                System.out.println("Down: "+event.getX()+"------------"+event.getY());
                startX = event.getX();
                startY = event.getY();

                if (getMainActivity().isRestart() == true) {
                    startGame();
                    getMainActivity().setRestart(false);
                }
                break;

            case MotionEvent.ACTION_UP:
//                System.out.println("Up "+event.getX()+"------------"+event.getY());
                offSetX = event.getX()- startX;
                offSetY = event.getY()-startY;
//                System.out.println(offSetX + ":"+offSetY);

                if (Math.abs(offSetX) > Math.abs(offSetY)) {
                    if (offSetX<-5) {
//                        System.out.println("Left");
//                        Toast.makeText(getContext(),"Left",Toast.LENGTH_SHORT).show();
                        swipeLeft();
                    } else if (offSetX>5) {
//                        System.out.println("Right");
//                        Toast.makeText(getContext(),"Right",Toast.LENGTH_SHORT).show();
                        swipeRight();
                    }

                } else {

                    if (offSetY > 5) {
//                        System.out.println("Down");
//                        Toast.makeText(getContext(), "Down", Toast.LENGTH_SHORT).show();
                        swipeDown();
                    } else if (offSetY < -5) {
//                        System.out.println("Up");
//                        Toast.makeText(getContext(), "Up", Toast.LENGTH_SHORT).show();
                        swipeUp();
                    }
                }

                break;
        }
        return true;
    }

    @Override
    protected void onSizeChanged(int w,int h,int oldw,int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int cardWidth = (Math.min(w,h)-10)/4;
//        System.out.println("card width "+cardWidth+"w->"+w+"h--->"+h);
        addCard(cardWidth, cardWidth);
        startGame();


    }



    private void addCard(int cardWidth, int cardHeight) {

        Card card;

        for (int i = 0; i < 4; i++) {
            for (int k = 0; k < 4; k++) {
//                System.out.println(k+": "+cardWidth+", "+cardHeight);
                card = new Card(getContext());
                card.setNum(0);
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(cardWidth,cardHeight);
                addView(card,lp);

                cards[k][i] = card;

            }

        }

//        System.out.println(cards.length);
//        System.out.println(cards[0].length);
//        System.out.println(cardss.size());


    }

    private void swipeUp() {

        boolean merge = false;

        for (int x=0;x<4;x++) {
            for (int y=0;y<4;y++) {
                for (int y1 = y+1;y1<4;y1++) {
                    if (cards[x][y1].getNum()>0) {

                        if (cards[x][y].getNum()<=0) {
                            cards[x][y].setNum(cards[x][y1].getNum());
                            cards[x][y1].setNum(0);
                            y--;
                            merge = true;
                            break;
                        } else if (cards[x][y].equals(cards[x][y1])) {
                            cards[x][y].setNum(cards[x][y].getNum()*2);
                            cards[x][y1].setNum(0);
                            getMainActivity().addScore(cards[x][y].getNum());
                            merge = true;
                            break;
                        }

                    }

                }
            }
        }
        if (merge) {
            addRandom();
            checkFinish();
        }
    }

    private void swipeDown() {

        boolean merge = false;

        for (int x=0;x<4;x++) {
            for (int y=3;y>=0;y--) {
                for (int y1 = y-1;y1>=0;y1--) {
                    if (cards[x][y1].getNum()>0) {

                        if (cards[x][y].getNum()<=0) {
                            cards[x][y].setNum(cards[x][y1].getNum());
                            cards[x][y1].setNum(0);
                            y++;
                            merge = true;
                            break;
                        } else if (cards[x][y].equals(cards[x][y1])) {
                            cards[x][y].setNum(cards[x][y].getNum()*2);
                            cards[x][y1].setNum(0);
                            getMainActivity().addScore(cards[x][y].getNum());
                            merge = true;
                            break;
                        }

                    }


                }
            }
        }
        if (merge) {
            addRandom();
            checkFinish();
        }
    }

    private void swipeRight() {

        boolean merge = false;

        for (int y=0;y<4;y++) {
            for (int x=3;x>=0;x--) {
                for (int x1 = x-1;x1>=0;x1--) {
                    if (cards[x1][y].getNum()>0) {
                        if (cards[x][y].getNum()<=0) {
                            cards[x][y].setNum(cards[x1][y].getNum());
                            cards[x1][y].setNum(0);
                            x++;
                            merge = true;
                            break;
                        } else if (cards[x][y].equals(cards[x1][y])) {
                            cards[x][y].setNum(cards[x][y].getNum()*2);
                            cards[x1][y].setNum(0);
                            getMainActivity().addScore(cards[x][y].getNum());
                            merge = true;
                            break;
                        }

                    }

                    ;
                }
            }
        }
        if (merge) {
            addRandom();
            checkFinish();
        }
    }

    private void swipeLeft() {

        boolean merge = false;

        for (int y=0;y<4;y++) {
            for (int x=0;x<4;x++) {
                for (int x1 = x+1;x1<4;x1++) {
                    if (cards[x1][y].getNum()>0) {

                        if (cards[x][y].getNum()<=0) {
                            cards[x][y].setNum(cards[x1][y].getNum());
                            cards[x1][y].setNum(0);
                            x--;
                            merge = true;
                            break;
                        } else if (cards[x][y].equals(cards[x1][y])) {
                            cards[x][y].setNum(cards[x][y].getNum()*2);
                            cards[x1][y].setNum(0);
                            getMainActivity().addScore(cards[x][y].getNum());
                            merge = true;
                            break;
                        }

                    }
                }
            }
        }
        if (merge) {
            addRandom();
            checkFinish();
        }

    }



    protected void startGame(){

        getMainActivity().clearScore();
        getMainActivity().setScore(0);

        for (int y = 0;y<4;y++) {
            for (int x=0;x<4;x++) {
                cards[x][y].setNum(0);
            }
        }
        addRandom();
        addRandom();

    }

    protected void addRandom() {

        cardss.clear();

        for (int y=0;y<4;y++) {
            for (int x=0;x<4;x++) {
                if (cards[x][y].getNum()<=0) {
                    cardss.add(new Point(x,y));
                }
            }
        }
        Point p = cardss.remove((int)(Math.random()*cardss.size()));
        cards[p.x][p.y].setNum(Math.random()>0.1?2:4);
    }

    private void checkFinish() {

        boolean finish = true;

        ALL:

        for (int y=0;y<4;y++) {
            for (int x=0;x<4;x++) {
                if (cards[x][y].getNum()==0 ||
                        (x>0&&cards[x][y].equals(cards[x-1][y])) ||
                        (x<3&&cards[x][y].equals(cards[x+1][y])) ||
                        (y>0&&cards[x][y].equals(cards[x][y-1])) ||
                        (y<3&&cards[x][y].equals(cards[x][y+1]))) {

                    finish = false;
                    break ALL;
                }

            }
        }

        if (finish) {
            new AlertDialog.Builder(getContext()).setTitle("Finish").setPositiveButton("Restart",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startGame();
                }
            }).show();

            int tempScore = getMainActivity().getScore();
            if (bestScore<tempScore) {
                bestScore = tempScore;
                getMainActivity().showBestScore(bestScore);
            } else {
                getMainActivity().showBestScore(bestScore);
            }
        }

    }

}
