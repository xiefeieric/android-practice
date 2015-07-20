package eric.com.lingchat.Utility;

/**
 * Created by Eric on 27/03/2015.
 */
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateUtil {

    //转换中文对应的时段
    public static String convertNowHour2CN(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        String hourString = sdf.format(date);
        int hour = Integer.parseInt(hourString);
        if(hour < 6) {
            return "凌晨";
        }else if(hour >= 6 && hour < 12) {
            return "早上";
        }else if(hour == 12) {
            return "中午";
        }else if(hour > 12 && hour <=18) {
            return "下午";
        }else if(hour >=19) {
            return "晚上";
        }
        return "";
    }

    //剩余秒数转换
    public static String convertSecond2Day(int time) {
        int day = time/86400;
        int hour = (time - 86400*day)/3600;
        int min = (time - 86400*day - 3600*hour)/60;
        int sec = (time - 86400*day - 3600*hour - 60*min);
        StringBuilder sb = new StringBuilder();
        sb.append(day);
        sb.append("天");
        sb.append(hour);
        sb.append("时");
        sb.append(min);
        sb.append("分");
        sb.append(sec);
        sb.append("秒");
        return sb.toString();
    }

}
