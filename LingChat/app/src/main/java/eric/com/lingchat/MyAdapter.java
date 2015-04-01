package eric.com.lingchat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Eric on 27/03/2015.
 */
public class MyAdapter extends BaseAdapter {

    private List<ListData> lists;
    private Context context;
    private RelativeLayout layout;

    public MyAdapter(List<ListData> lists, Context context) {
        this.lists = lists;
        this.context = context;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        TextView tvRobot,tvUser;

//        if (convertView == null) {

            LayoutInflater inflater = LayoutInflater.from(context);
            if (lists.get(position).getFlag() == ListData.RECEIVER) {
                layout = (RelativeLayout) inflater.inflate(R.layout.left_item,null);
                tvRobot = (TextView) layout.findViewById(R.id.tvRobot);
                tvRobot.setText(lists.get(position).getContent());

            } else if (lists.get(position).getFlag() == ListData.SEND) {
                layout = (RelativeLayout) inflater.inflate(R.layout.right_item,null);
                tvUser = (TextView) layout.findViewById(R.id.tvUser);
                tvUser.setText(lists.get(position).getContent());
            }
        TextView tvTime = (TextView) layout.findViewById(R.id.tvTime);
        tvTime.setText(lists.get(position).getTime());
//        } else {
//            if (lists.get(position).getFlag() == ListData.RECEIVER) {
//
//                tvRobot = (TextView) convertView.findViewById(R.id.tvRobot);
//                tvRobot.setText(lists.get(position).getContent());
//
//            } else if (lists.get(position).getFlag() == ListData.SEND) {
//
//                tvUser = (TextView) convertView.findViewById(R.id.tvUser);
//                tvUser.setText(lists.get(position).getContent());
//            }
//        }

        return layout;
    }
}
