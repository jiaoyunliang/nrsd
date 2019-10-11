package com.rsd.utils;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {


	private static final String STR_DATE = "yyyy-MM-dd";
	private static final String STR_TIME = "yyyy-MM-dd HH:mm";
	private static final String STR_FULL_TIME = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 使用用户格式格式化日期
	 * 
	 * @param date
	 *            日期
	 * @param pattern
	 *            日期格式
	 * @return
	 */
	public static String format(Date date, String pattern) {
		String returnValue = "";
		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			returnValue = df.format(date);
		}
		return returnValue;
	}

	/**
	 * 比较两个时间的大小 得到两个日期的时间戳
	 * 
	 */
	public static int compare_date(String date1, String date2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date dt1 = df.parse(date1);
			Date dt2 = df.parse(date2);
			if (dt1.getTime() > dt2.getTime()) {
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	public static SimpleDateFormat getDateFormat() {
		return new SimpleDateFormat(STR_DATE);
	}

	public static SimpleDateFormat getTimeFormat() {
		return new SimpleDateFormat(STR_TIME);
	}

	public static SimpleDateFormat getFullTimeFormat() {
		return new SimpleDateFormat(STR_FULL_TIME);
	}

	public static Date getNowTime() {
		return new Date();
	}

	public static Date getNowDate() {
		return parseStringToDate(parseDateToString(getNowTime()));
	}

	public static String parseDateToString(Date date) {
		String rtn = getDateFormat().format(date);
		return rtn;
	}

	public static String parseTimeToString(Date date) {
		if (date == null){
			return "";
		} else {
			return getTimeFormat().format(date);
		}

	}

	public static String parseFullTimeToString(Date date) {
		if (date == null){
			return "";
		}
		else {
			return getFullTimeFormat().format(date);
		}

	}

	public static Date parseStringToDate(String sDate) {
		if (sDate == null || sDate.trim().length() == 0){
			return null;
		} else {
			sDate = sDate.trim();
		}


		try {
			return getDateFormat().parse(sDate);
		} catch (Exception e) {
			return null;
		}
	}

	public static Date parseStringToTime(String sDate) {
		if (sDate == null || sDate.trim().length() == 0) {
			return null;
		}

		else {
			sDate = sDate.trim();
		}


		try {
			return getTimeFormat().parse(sDate);
		} catch (Exception e) {
			return null;
		}
	}

	public static Date parseStringToFullTime(String sDate) {
		if (sDate == null || sDate.trim().length() == 0){
			return null;
		} else {
			sDate = sDate.trim();
		}


		try {
			return getFullTimeFormat().parse(sDate);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 将long类型的时间转换成标准格式（yyyy-MM-dd HH:mm:ss）
	 * 
	 * @param longTime
	 * @return
	 */
	public static String formatTime(long longTime) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date(longTime));
	}

	public static String getNowDateString() {
		return parseDateToString(getNowTime());
	}

	public static String getNowTimeString() {
		return parseTimeToString(getNowTime());
	}

	public static String getNowFullTimeString() {
		return parseFullTimeToString(getNowTime());
	}

	public static Date getNextDay(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

	public static Date getNextMonth(Date date, int mons) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, mons);
		return cal.getTime();
	}

	public static int getDateInt(String date) {
		return Integer.parseInt(date.substring(0, 4) + date.substring(5, 7) + date.substring(8, 10));
	}

	public static int getMonthInt(String month) {
		return Integer.parseInt(month.substring(0, 4) + month.substring(5, 7));
	}

	public static String isValidDate(int date) {
		String s = Integer.toString(date);
		s = s.substring(0, 4) + "-" + s.substring(4, 6) + "-" + s.substring(6, 8);
		SimpleDateFormat fmt = new SimpleDateFormat(STR_DATE);

		Date tmpDate = null;
		try {
			tmpDate = fmt.parse(s);
		} catch (Exception e) {
			return null;
		}

		String tmps = fmt.format(tmpDate);
		if (tmps.equals(s)) {
			return s;
		} else {
			return null;
		}

	}

	public static String isValidMonth(int month) {
		String s = Integer.toString(month);
		s = s.substring(0, 4) + "-" + s.substring(4) + "-01";
		SimpleDateFormat fmt = new SimpleDateFormat(STR_DATE);

		Date tmpDate = null;
		try {
			tmpDate = fmt.parse(s);
		} catch (Exception e) {
			return null;
		}

		String tmps = fmt.format(tmpDate);
		if (tmps.equals(s)){
			return s.substring(0, 7);
		} else {
			return null;
		}

	}

	/**
	 *  * 根据开始时间和结束时间返回时间段内的时间集合  * @param beginDate  * @param endDate
	 *  * @return List  
	 */
	public static List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate) {
		List<Date> lDate = new ArrayList<Date>();
		lDate.add(beginDate);// 把开始时间加入集合
		Calendar cal = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		cal.setTime(beginDate);
		boolean bContinue = true;
		while (bContinue) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			cal.add(Calendar.DAY_OF_MONTH, 1);
			// 测试此日期是否在指定日期之后
			if (endDate.after(cal.getTime())) {
				lDate.add(cal.getTime());
			} else {
				break;
			}
		}
		lDate.add(endDate);// 把结束时间加入集合
		return lDate;
	}

	/**
	 * 计算两个日期之间相差的天数,不包括小时分钟秒
	 *
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int daysBetween(Date startDate, Date endDate) {
		SimpleDateFormat sf = new SimpleDateFormat(STR_DATE);
		try {
			Calendar cal = Calendar.getInstance();
			String startDateStr = sf.format(startDate);
			cal.setTime(sf.parse(startDateStr));
			long time1 = cal.getTimeInMillis();

			String endDateStr= sf.format(endDate);
			cal.setTime(sf.parse(endDateStr));
			long time2 = cal.getTimeInMillis();
			long between_days = (time2 - time1) / (1000 * 3600 * 24);
			return Integer.parseInt(String.valueOf(between_days));

		} catch (Exception e) {

		}
		return 0;
	}

	// public static SimpleDateFormat getUltimateTimeFormat(){
	// return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	// }
	//
	//

	//
	// public static Date parseStringToFullTime(String sDate) {
	// if (sDate == null || sDate.trim().equals("")) {
	// return null;
	// }
	//
	// Date tempDate = null;
	// try {
	// tempDate = getFullTimeFormat().parse(sDate);
	// } catch (Exception e) {
	// return null;
	// }
	// return tempDate;
	// }
	//
	// public static Date parseStringToUltimateTime(String sDate) {
	// if (sDate == null || sDate.trim().equals("")) {
	// return null;
	// }
	//
	// Date tempDate = null;
	// sDate = sDate.substring(0, 23);
	// try {
	// tempDate = getUltimateTimeFormat().parse(sDate);
	// } catch (Exception e) {
	// return null;
	// }
	// return tempDate;
	// }
	//

	//

	//
	//
	//
	// public static String parseUltimateTimeToString(Date date) {
	// // modi liqian
	// if (date == null) {
	// return "";
	// }
	// String rtn = getUltimateTimeFormat().format(date) + "000";
	// return rtn;
	// }
	//
	//
	//
	//
	//
	// public static boolean between(Date date, Date begin, Date end) {
	// if (date.compareTo(begin) >= 0 && date.compareTo(end) <= 0)
	// return true;
	// else
	// return false;
	// }
	//
	// public static Date getNextDay(Date date) {
	// return getNextDay(date, 1);
	// }
	//
	// public static Date getNextDay(Date date, int days) {
	// Date newDate = null;
	// long time = date.getTime();
	// if (days <= 20) {
	// newDate = new Date(time + days * 86400000);
	// } else {
	// newDate = new Date(time);
	// while (days > 20) {
	// newDate = getNextDay(newDate, 20);
	// days = days - 20;
	// }
	// newDate = getNextDay(newDate, days);
	// }
	//
	// return newDate;
	// }
	//
	// public static Date getPreDay(Date date) {
	// return getPreDay(date, 1);
	// }
	//
	// public static Date getPreDay(Date date, int i) {
	// long time = date.getTime();
	// date = new Date(time - i * 86400000);
	//
	// return date;
	// }
	//
	// public static boolean isOutOfRange(Date begin, Date end, int range) {
	// begin = parseStringToDate(parseDateToString(begin));
	// end = parseStringToDate(parseDateToString(end));
	// long rg = (long) range;
	// if (end.getTime() - begin.getTime() >= rg * 86400000) {
	// return true;
	// }
	// return false;
	// }
	//
	// public static Date getPreMonth() {
	// String now = getNowDateString();
	// String firstS = now.substring(0, 7) + "-01";
	// Date first = parseStringToDate(firstS);
	// long time = first.getTime();
	// Date date = new Date(time - 86400000);
	// String dateS = parseDateToString(date);
	// dateS = dateS.substring(0, 7) + "-01";
	// return parseStringToDate(dateS);
	// }
	//
	// // public static int getLastDay(String month){
	// // //����month��ʽΪ2005-01
	// // int last=31;
	// // String pre=month+"-";
	// // String lasts=pre+Integer.toString(last);
	// // String tmps=null;
	// // while (true) {
	// // try
	// // {
	// // tmps=DateUtil.parseDateToString(dateFormat.parse(lasts));
	// // if(tmps.equals(lasts))
	// // break;
	// // else{
	// // last=last - 1;
	// // lasts=pre+Integer.toString(last);
	// // continue;
	// // }
	// // }
	// // catch(Exception e)
	// // {
	// // last=last - 1;
	// // lasts=pre+Integer.toString(last);
	// // continue;
	// // }
	// // }
	// // return last;
	// // }
	//
	// public static int getDateInt(String date) {
	// date = date.substring(0, 4) + date.substring(5, 7)
	// + date.substring(8, 10);
	// return Integer.parseInt(date);
	// }
	//
	// public static int getMonthInt(String year, String month) {
	// return Integer.parseInt(year) * 100 + Integer.parseInt(month);
	// }
	//

	//
	// public static boolean isValidDateTime(String sDate) {
	// if (sDate == null || sDate.trim().equals("")) {
	// return true;
	// } else {
	// try {
	// Date dDate = getFullTimeFormat().parse(sDate);
	// String s = getFullTimeFormat().format(dDate);
	// if (!s.equals(sDate))
	// return false;
	// else
	// return true;
	// } catch (Exception e) {
	// return false;
	// }
	// }
	// }
	//
	// public static String isValidDate(int date) {
	// String s = Integer.toString(date);
	// s = s.substring(0, 4) + "-" + s.substring(4, 6) + "-"
	// + s.substring(6, 8);
	//
	// Date tmpDate = null;
	// try {
	// tmpDate = getDateFormat().parse(s);
	// } catch (Exception e) {
	// return null;
	// }
	//
	// String tmps = getDateFormat().format(tmpDate);
	// if (tmps.equals(s))
	// return s;
	// else
	// return null;
	// }
	//

	//
	// public static String getFormatDate(String date) {
	// date = date.substring(0, 4) + "-" + date.substring(4, 6) + "-"
	// + date.substring(6, 8);
	// return date;
	// }
	//
	// public static String getFormatTime(String time) {
	// time = time.substring(0, 4) + "-" + time.substring(4, 6) + "-"
	// + time.substring(6, 8) + " " + time.substring(8, 10) + ":"
	// + time.substring(10, 12) + ":" + time.substring(12);
	// return time;
	// }
	//
	// public static String getCompactDate(String date) {
	// date = date.substring(0, 4) + date.substring(5, 7)
	// + date.substring(8, 10);
	// return date;
	// }
	//
	// public static String getCompactTime(String time) {
	// time = time.substring(0, 4) + time.substring(5, 7)
	// + time.substring(8, 10) + time.substring(11, 13)
	// + time.substring(14, 16) + time.substring(17);
	// return time;
	// }
	//
	// public static Calendar parseDate(String simpleDateStr) {
	// try {
	// Calendar calendar = Calendar.getInstance();
	// Date date = parseTime.parse(simpleDateStr);
	// calendar.setTime(date);
	// return calendar;
	// } catch (ParseException e) {
	// log.error("parse date error");
	// return null;
	// }
	// }
	//
	// public static List<Date> getDays(Date begin, Date end) {
	// String beginStr = parseDateToString(begin);
	// String endStr = parseDateToString(end);
	// int beginInt = Integer.parseInt(beginStr.substring(0, 4) +
	// beginStr.substring(5, 7) + beginStr.substring(8, 10));
	// int endInt = Integer.parseInt(endStr.substring(0, 4) +
	// endStr.substring(5, 7) + endStr.substring(8, 10));
	// return getDays(beginInt, endInt);
	// }
	//
	// public static List<Date> getDays(int begin, int end) {
	// List<Date> dates = new ArrayList<Date>();
	// String dateStr = null;
	// if (begin == end) {
	// String s = Integer.toString(end);
	// s = s.substring(0, 4) + "-" + s.substring(4, 6) + "-" + s.substring(6,
	// 8);
	// dates.add(parseStringToDate(s));
	// } else {
	// for (int i = begin; i <= end; i++) {
	// dateStr = DateUtil.isValidDate(i);
	// if (dateStr != null)
	// dates.add(parseStringToDate(dateStr));
	// }
	// }
	// return dates;
	// }
	//
	// public static Date getNextMinute(Date date) {
	// return new Date(date.getTime() + 60000);
	// }
	//
	// public static Date getNextSecond(Date date) {
	// return new Date(date.getTime() + 1000);
	// }

	public static void main(String[] args) {
		String start = "2017-01-01"; 
		String end = "2017-03-01"; 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		Date dBegin;
		Date dEnd;
//		HcDateMemo memo=new HcDateMemo();
//		try {
//			dBegin = sdf.parse(start);
//		    dEnd = sdf.parse(end);
//			List<Date> list=getDatesBetweenTwoDate(dBegin,dEnd);
//			for (int i = 0; i < list.size(); i++) {
//				Date date= list.get(i);
//				memo.setDay(date);
//				memo.setMonth(DateUtil.format(date, "YYYY-MM"));
//				memo.setYear(DateUtil.format(date, "YYYY"));
//				System.out.println(DateUtil.format(date, "YYYY-MM"));
//			}
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	
	}

}
