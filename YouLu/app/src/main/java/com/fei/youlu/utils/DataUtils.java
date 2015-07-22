package com.fei.youlu.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataUtils {

	/**
	 * ��������ǰʱ��ĺ���ֵת��һ���ַ�����
	 * ת������
	 * ����ǵ��죺   HH:mm
	 * �����һ���ڣ� ����X
	 * �����һ��֮ǰ�� yyyy��MM��dd��
	 * @param time
	 * @return
	 */
	public static String parse(long time) {
		//��ǰʱ��
		Calendar now=Calendar.getInstance();
		//����timeʱ��
		Calendar other=Calendar.getInstance();
		other.setTimeInMillis(time);
		//�ж��ǲ���ͬһ�죺
		if(now.get(Calendar.DAY_OF_YEAR) == 
				other.get(Calendar.DAY_OF_YEAR) && 
				now.get(Calendar.YEAR) == 
				other.get(Calendar.YEAR)){
			SimpleDateFormat fmt=new SimpleDateFormat("HH:mm");
			return fmt.format(other.getTime());
		}
		//����ͬһ��Ļ�  �ж��Ƿ���һ������
		int day=other.get(Calendar.DAY_OF_WEEK);
		String daystring="";
		switch (day) {
		case Calendar.MONDAY:
			daystring="Monday";
			break;
		case Calendar.TUESDAY:
			daystring="Tuesday";
			break;
		case Calendar.WEDNESDAY:
			daystring="Wednesday";
			break;
		case Calendar.THURSDAY:
			daystring="Thursday";
			break;
		case Calendar.FRIDAY:
			daystring="Friday";
			break;
		case Calendar.SATURDAY:
			daystring="Saturday";
			break;
		case Calendar.SUNDAY:
			daystring="Sunday";
			break;
		}
		return daystring;
	}

}
