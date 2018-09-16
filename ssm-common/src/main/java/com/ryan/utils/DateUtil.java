package com.ryan.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间工具类
 * @author YoriChen
 * @date 2018/5/21
 */
public class DateUtil {

    /**
     * 一秒中的等价毫秒数
     */
    public static final long SECOND = 1000;

    /**
     * 一分钟的等价毫秒数
     */
    public static final long MINUTE = 60 * SECOND;

    /**
     * 一小时的等价毫秒数
     */
    public static final long HOUR = 60 * MINUTE;

    /**
     * 一天的等价毫秒数
     */
    public static final long DAY = 24 * HOUR;

    /**
     * 半小时的等价毫秒数
     */
    public static final long HALF_HOUR = HOUR / 2;

    /**
     * 格式化日期（yyyyMMdd）
     */
    public static final String SHORT_DATEPATTERN = "yyyyMMdd";

    /**
     * 格式化日期（yyyy-MM-dd hh:mm:ss）
     */
    public static final String DEFAULT_DATEPATTERN = "yyyy-MM-dd hh:mm:ss";

    /**
     * 格式化日期（yyyy-MM-dd HH:mm:ss）
     */
    public static final String DEFAULT_DATESFM = "yyyy-MM-dd HH:mm:ss";

    /**
     * 格式化日期（yyyy-MM-dd HH:mm）
     */
    public static final String DEFAULT_MINUS = "yyyy-MM-dd HH:mm";

    /**
     * 格式化日期（yyyyMMddHHmmss）
     */
    public static String STR_PATTERN = "yyyyMMddHHmmss";

    /**
     * 格式化日期（yyyy-MM-dd）
     */
    public static final String SAMPLE_DATEPATTERN = "yyyy-MM-dd";

    /**
     * 格式化日期（yyyyMMdd）
     */
    public static final String SHORT_DATE_PATTERN = "yyyyMMdd";

    /**
     * 格式化日期(yyyy年MM月dd日)
     * */
    public static final String SHORT_CHAR_DATE_PATTERN="yyyy年MM月dd日";

    /**
     * 格式化日期（HHmmss）
     */
    public static final String StR_PATTERN_HHMMSS = "HHmmss";
    
    /**
     * 格式化时间（HH:mm:ss）
     */
    public static final String StR_PATTERN_HHMMSS1 = "HH:mm:ss";
    
    public static final String StR_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    /**
     * 格式：yyyyMMdd，8位
     */
    public static final String PATTERN_NUM_DATE = "yyyyMMdd";

    /**
     * 格式：yyyyMMddHHmmss，14位
     */
    public static final String PATTERN_NUM_SEC = "yyyyMMddHHmmss";

    /**
     * 格式：yyyyMMddHHmmssSSS，17位
     */
    public static final String PATTERN_NUM_MS = "yyyyMMddHHmmssSSS";
    
    public static String defaultFormat(Date date) {
    	return new SimpleDateFormat(DEFAULT_DATEPATTERN).format(date);
    }
    
    /**
     * 获取今天凌晨的0分0秒这个时间
     */
    public static Date getDayBegin() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取昨天凌晨的0分0秒这个时间
     */
    public static Date getYesterdayDayBegin() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    /**
     * 获取指定日期凌晨的0分0秒这个时间
     *
     * @param date
     * @return
     */
    public static Date getBegin(Date date){
    	String dateStr = DateUtil.shortDateString(date, SHORT_DATE_PATTERN);
    	Date dayBegin = DateUtil.shortStringToDate(dateStr, SHORT_DATE_PATTERN);
    	return dayBegin;
    }
    
    /**
     * 判断今天是否是周末。是则返回true,否则返回false
     */
    public static boolean isWeekEnd() {
        Calendar cal = Calendar.getInstance();
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return true;
        }
        return false;
    }

    /**
     * 判断今天是否是星期一，是则返回true,否则返回false
     */
    public static boolean isWeekStart() {
        Calendar cal = Calendar.getInstance();
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            return true;
        }
        return false;
    }

    /**
     * 判断今天是否是一号，是则返回true,否则返回false
     */
    public static boolean isMonthStart() {
        Calendar cal = Calendar.getInstance();
        if (cal.get(Calendar.DAY_OF_MONTH) == 1) {
            return true;
        }
        return false;
    }

    /**
     * 判断今天是否是月末，是则返回true,否则返回false
     */
    public static boolean isMonthEnd() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        if (cal.get(Calendar.DAY_OF_MONTH) == 1) {
            return true;
        }
        return false;
    }

    /**
     * @return 返回明天凌晨0点0分0秒
     */
    public static Date getNextDay() {
        return getNextHour(0);
    }

    /**
     * @return 返回指定时间(x点整)最靠近的时间, 如果今天已经过了那个时间, 则返回明天的那个时间,否则返回今天的那个整点时间
     */
    public static Date getNextHour(int hour) {
        return getNextTime(hour, 0);
    }

    /**
     * @param hour 几点
     * @param min  几分
     * @return 获得指定时间(x点y分)最靠近的时间, 如果今天已经过了那个时间, 则返回明天的那个时间,否则返回今天的那个时间
     */
    public static Date getNextTime(int hour, int min) {
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.HOUR_OF_DAY) > hour) {
            calendar.add(Calendar.DATE, 1);
        } else if (calendar.get(Calendar.HOUR_OF_DAY) == hour &&
                calendar.get(Calendar.MINUTE) >= min) {
            calendar.add(Calendar.DATE, 1);
        }
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取指定的日期
     *
     * @param year  年
     * @param month 月
     * @param day   日
     * @param hour  小时
     * @param min   分
     * @return
     */
    public static Date getTime(int year, int month, int day, int hour, int min) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取月初的时间
     *		年月 1号 0 时 0 分 0 秒
     * @return
     */
    public static Date getMonthBenin() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取当前月份+month月初时间，时间为  00:00:00
     * @return
     */
    public static Date getNextMonthBeginTime(int month){
    	Calendar calendar = Calendar.getInstance();
    	calendar.add(Calendar.MONTH, month);  
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        calendar.set(Calendar.HOUR,12);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
    	return calendar.getTime();
    }
    
    /**
     * 获取相继几天的时间
     *
     * @param before
     * @return
     */
    public static Date getDayBefore(int before) {
        return getDayBefore(null, before);
    }

    /**
     * 获取日期之后几天的的日期
     * 
     * @param date	为空时当前时间
     * @param before
     * @return
     */
    public static Date getDayBefore(Date date, int before) {
        Calendar calendar = Calendar.getInstance();
        if (date != null){
            calendar.setTime(date);
        }
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - before);
        return calendar.getTime();
    }

    /**
     * 获取date之前或之后number天的日期
     *
     * @param date
     * @param number 正数 当前日期之后   负数：当前日期之前
     * @return
     */
    public static Date getAfterOrBeforeDay(Date date, int number) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        calendar.add(Calendar.DAY_OF_MONTH, number);
        return calendar.getTime();
    }

    /**
     * 获取当前日期之前或之后number天的日期
     *
     * @param number 正数 当前日期之后   负数：当前日期之前
     * @return
     */
    public static Date getAfterOrBeforeDay(int number) {
        return getAfterOrBeforeDay(new Date(), number);
    }

    /**
     * @param date
     * @return
     */
    public static String shortDateString(Date date) {
        if (date == null){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(SAMPLE_DATEPATTERN);
        return sdf.format(date);
    }

    /**
     * 按照格式返回日期字符串
     *
     * @param date
     * @param format (如：yyyyMMdd )
     * @return
     */
    public static String shortDateString(Date date, String format) {
        if (date == null){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 根据时间字串和格式化标准进行格式化
     *
     * @param dateStr {String} 时间字串
     * @param format  {String} 格式化标准 【exam:'yyyy-MM-dd hh:mm:ss'】
     * @return {java.util.Date}
     */
    public static Date shortStringToDate(String dateStr, String format) {
        Date date = null;

        if (null == dateStr) {
            date = new Date();
        } else {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                date = sdf.parse(dateStr);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return date;
    }

    public static Date shortStringToDate(String dateStr) {
        return shortStringToDate(dateStr, SAMPLE_DATEPATTERN);
    }
    public static Date shortStringToDateFormat(String dateStr,String format) {
        return shortStringToDate(dateStr, format);
    }

    /**
     * 将不同类型的日期格式转成yyyy-MM-dd格式字符串
     *
     * @param date
     * @param format (如：yyyyMMdd )
     * @return
     */
    public static String shortDateString(String date, String format) {
        if (date == null){
            return "";
        }
        //SimpleDateFormat sdf = new SimpleDateFormat(format);
        return shortDateString(shortStringToDate(date, format));
    }

    /**
     * 计算2个日期的间隔的秒数
     *
     * @param d1
     * @param d2
     * @return
     */
    public static long elapsedSecond(Date d1, Date d2) {
        long d = Math.abs(d1.getTime() - d2.getTime());
        return d / 1000;
    }

    /**
     * 计算2个日期间隔的分钟数
     *
     * @param d1
     * @param d2
     * @return
     */
    public static long elapsedMinute(Date d1, Date d2) {
        return elapsedSecond(d1, d2) / 60;
    }

    /**
     * 计算2个日期间隔的小时数
     *
     * @param d1
     * @param d2
     * @return
     */
    public static long elapsedhour(Date d1, Date d2) {
        return elapsedMinute(d1, d2) / 60;
    }

    public static long elapsedMonth(Date d1, Date d2) {
        return elapsedDay(d1, d2) / 30;
    }

    /**
     * 计算2个日期间隔的天数
     *
     * @param d1
     * @param d2
     * @return
     */
    public static int elapsedDay(Date d1, Date d2) {
        return (int) elapsedhour(d1, d2) / 24;
    }

    /**
     * 计算2个日期间隔的年数
     *
     * @param d1
     * @param d2
     * @return
     */
    public static Double elapsedYear(Date d1, Date d2) {
        return new Double(elapsedDay(d1, d2) / 365.0);
    }

    /**
     * 获取当前月-number的日期
     *
     * @param number
     * @return
     */
    public static Date getDateBeforeOfMonth(int number) {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        now.set(Calendar.MONTH, now.get(Calendar.MONTH) - number);
        return now.getTime();
    }

    public static String simpleFormat(Date date){
        return new SimpleDateFormat(SHORT_DATE_PATTERN).format(date);
    }

    /**
     * 获取当前月-number的日期
     *
     * @param number
     * @return
     */
    public static Date getDateBeforeOfMonth(Date date, int number) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.MONTH, now.get(Calendar.MONTH) - number);
        return now.getTime();
    }

    /**
     * 获取当前月+number的日期
     *
     * @param month
     * @return
     */
    public static Date getDateAfterOfMonth(int month) {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        now.set(Calendar.MONTH, now.get(Calendar.MONTH) + month);
        return now.getTime();
    }

    /**
     * 获取月+number的日期
     *
     * @param month
     * @return
     */
    public static Date getDateAfterOfMonth(Date date, int month) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.MONTH, now.get(Calendar.MONTH) + month);
        return now.getTime();
    }
    /**
     * 获取当前年+number的日期
     *
     * @param year
     * @return
     */
    public static Date getDateAfterOfYear(int year) {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        now.set(Calendar.YEAR, now.get(Calendar.YEAR) + year);
        return now.getTime();
    }

    /**
     * 获取年+number的日期
     *
     * @param year
     * @return
     */
    public static Date getDateAfterOfYear(Date date, int year) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.YEAR, now.get(Calendar.YEAR) + year);
        return now.getTime();
    }

    /**
     * 获得2个日期之间的时间间隔
     *
     * @param da1(起始日期 格式：yyyy yyyyMM yyyyMMdd)
     * @param da2(截止日期)
     * @return
     */
    public static String getTimeBetweenDates(String da1, String da2) {
        String str = "";
        if (StringUtil.isEmpty(da1) || StringUtil.isEmpty(da2) || !StringUtil.isNumeric(da1) || !StringUtil.isNumeric(da2)||"19000101".equals(da1)) {
            return "--";
        }
        Date now = new Date();
        int years = 0;
        int months = 0;
        if (da1.length() == 4 || da2.length() == 4) {
            String dateStr1 = da1.substring(0, 4);
            String dateStr2 = da2.substring(0, 4);
            years = Integer.valueOf(dateStr2) - Integer.valueOf(dateStr1);
            str = years + "年";
        } else if (da1.length() == 6 || da2.length() == 6) {
            String yearStr1 = da1.substring(0, 4);
            String yearStr2 = da2.substring(0, 4);
            years = Integer.valueOf(yearStr2) - Integer.valueOf(yearStr1);
            String monthStr1 = da1.substring(4, 6);
            String monthStr2 = da2.substring(4, 6);
            months = Integer.valueOf(monthStr2) - Integer.valueOf(monthStr1);
            if (months < 0) {
                years = years - 1;
                months = months + 12;
            }
            if (years > 0) {
                str = years + "年";
            }
            if (months > 0) {
                str = str + months + "月";
            }
        } else {
            str = getTimeBetweenDates(shortStringToDate(da1, "yyyyMMdd"), shortStringToDate(da2, "yyyyMMdd"));
        }
        return str;
    }

    /**
     * 获得2个日期之间的时间间隔（如：1年124天）
     *
     * @param da1
     * @param da2
     * @return
     */
    public static String getTimeBetweenDates(Date da1, Date da2) {
        return null;
    }

    /**
     * 获取前几个季度的日期
     *
     * @return
     */
    public static List<String> getQuarter(int length, int number) {
        List<String> list = new ArrayList<String>();
        String[] dateStr = {"0331", "0630", "0930", "1231"};
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int quarter = cal.get(Calendar.MONTH) / 3 + 1;
        quarter = quarter - number;
        //jack change 2012.1.4
        while (quarter <= 0) {
            quarter = quarter + 4;
            year = year - 1;
        }

        for (int i = 0; i < length; i++) {
            if (quarter == 1) {
                quarter = 4;
                year -= 1;
            } else {
                quarter -= 1;
            }
            list.add(year + dateStr[quarter - 1]);
        }
        return list;
    }
    
	/**
	 * 取上季度末日期
	 *
	 * @param quarterDay
	 * @return
	 */
	public static String getLastQuarterDay(String quarterDay) {
		if ("0331".equals(quarterDay.substring(4))) {
			int year = Integer.valueOf(quarterDay.substring(0, 4)).intValue();
			return String.valueOf(year - 1) + "1231";
		} else if ("0630".equals(quarterDay.substring(4))) {
			return quarterDay.substring(0, 4) + "0331";
		} else if ("0930".equals(quarterDay.substring(4))) {
			return quarterDay.substring(0, 4) + "0630";
		} else if ("1231".equals(quarterDay.substring(4))) {
			return quarterDay.substring(0, 4) + "0930";
		}
		return null;
	}

    /**
     * 计算年龄
     *
     * @param birthday 生日日期
     * @return {int}
     */
    public static int getAge(Date birthday) {
        int age = 0;

        try {
            Calendar cal = Calendar.getInstance();

            if (!cal.before(birthday)) {
                //throw new IllegalArgumentException("出生时间大于当前时间!");
                int yearNow = cal.get(Calendar.YEAR);
                int monthNow = cal.get(Calendar.MONTH) + 1;
                int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
                cal.setTime(birthday);

                int yearBirth = cal.get(Calendar.YEAR);
                int monthBirth = cal.get(Calendar.MONTH) + 1;
                int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

                age = yearNow - yearBirth;

                if (monthNow <= monthBirth) {
                    if (monthNow == monthBirth) {
                        if (dayOfMonthNow < dayOfMonthBirth) {
                            age--;
                        }
                    } else {
                        age--;
                    }
                }
            }
        } catch (Exception e) {

        }

        return age;
    }

    /**
     * @param datePattern
     * @return
     */
    public static String formatNowDate(String datePattern) {
        if (datePattern == null) {
            datePattern = "yyyy-MM-dd";
        }
        SimpleDateFormat df = new SimpleDateFormat(datePattern);
        return df.format(new Date());
    }

    public static String thisYearStart() {
        Calendar cal = Calendar.getInstance();
        String date = cal.get(Calendar.YEAR) + "0101";
        return date;
    }

    /**
     * 得到日期的前或者后几小时
     *
     * @param iHour 如果要获得前几小时日期，该参数为负数；
     *              如果要获得后几小时日期，该参数为正数
     * @return Date 返回参数<code>curDate</code>定义日期的前或者后几小时
     * @see Calendar#add(int, int)
     */
    public static Date getDateBeforeOrAfterHours(Date curDate, int iHour) {
        Calendar cal = Calendar.getInstance();
        if (curDate != null) {
            cal.setTime(curDate);
        }
        cal.add(Calendar.HOUR_OF_DAY, iHour);
        return cal.getTime();
    }

    /**
     * 获得周五的日期
     *
     * @param date
     * @return
     */
    public static Date getFriday(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        return c.getTime();
    }

    /**
     * 时间转换
     *
     * @param d
     * @param fmt
     * @return
     */
    public static Date parseDate(String d, String fmt) {
        SimpleDateFormat sdf = new SimpleDateFormat(fmt);
        try {
            return sdf.parse(d);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static Date addDay(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + day);
        return calendar.getTime();
    }

    /**
     * 获取相继几天的时间
     *
     * @param before
     * @return
     */
    public static Date getDayBofore(int before) {
        return getDayBofore(null, before);
    }

    /**
     * @param date
     * @param before
     * @return
     */
    public static Date getDayBofore(Date date, int before) {
        Calendar calendar = Calendar.getInstance();
        if (date != null){
            calendar.setTime(date);
        }
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - before);
        return calendar.getTime();
    }

    /**
     * 得到日期的前或者后几分钟
     *
     * @param min 如果要获得前几分钟时间，该参数为负数；
     *              如果要获得后几分钟时间，该参数为正数
     * @return Date 返回参数<code>curDate</code>定义日期的前或者后几分钟
     * @see Calendar#add(int, int)
     */
    public static Date getDateBeforeOrAfterMinute(Date curDate, int min) {
        Calendar cal = Calendar.getInstance();
        if (curDate != null) {
            cal.setTime(curDate);
        }
        cal.add(Calendar.MINUTE, min);
        return cal.getTime();
    }

    /**
     * 得到日期的前或者后几秒钟
     *
     * @param second 如果要获得前几秒钟时间，该参数为负数；
     *              如果要获得后几秒钟时间，该参数为正数
     * @return Date 返回参数<code>curDate</code>定义日期的前或者后几秒钟
     */
    public static Date getDateBeforeOrAfterSecond(Date curDate, int second) {
        Calendar cal = Calendar.getInstance();
        if (curDate != null) {
            cal.setTime(curDate);
        }
        cal.add(Calendar.SECOND, second);
        return cal.getTime();
    }

    /**
     * 得到当前的的年
     *
     * @return 当前的年
     */
    public static int getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }
    
    /**
     * 根据日期得到当前季度数
     *
     * @param date  format yyyyMMdd
     * @return 1:一季度；2：二季度；3：三季度；4：四季度 ；0：无
     */
    public static int getSeasonNum(String date){
    	String month=date.substring(4, 6);
    	if("01".equals(month) ||"02".equals(month) ||"03".equals(month)){
    		return 1;
    	}else if("04".equals(month) ||"05".equals(month) ||"06".equals(month)){
    		return 2;
    	}else if("07".equals(month) ||"08".equals(month) ||"09".equals(month)){
    		return 3;
    	}else if("10".equals(month) ||"11".equals(month) ||"12".equals(month)){
    		return 4;
    	}
    	return 0;
    }
    
    /**
     * 取得某个年份某个季度的季末日期
     *
     * @param year 年份
     * @param season 季度数
     * @return 季末日期，如果无，返回null
     */
	public static String getSeasonEndDate(int year, int season) {				
		if (season == 1) {
			return year+"0331";
		} else if (season == 2) {
			return year+"0630";
		} else if (season == 3) {
			return year+"0930";
		} else if (season == 4) {
			return year+"1231";
		}

		return null;
	}
	
	/**
	 * 根据日期取得当前季度季末日期
     *
	 * @param date format: yyyyMMdd
	 * @return String 若无，返回null
	 */
	public static String  getCurSeasonEndDate(String date){
		String year =date.substring(0, 4);
		String month=date.substring(4, 6);
		if("01".equals(month) ||"02".equals(month) ||"03".equals(month)){
			return year+"0331";
    	}else if("04".equals(month) ||"05".equals(month) ||"06".equals(month)){
    		return year+"0630";
    	}else if("07".equals(month) ||"08".equals(month) ||"09".equals(month)){
    		return year+"0930";
    	}else if("10".equals(month) ||"11".equals(month) ||"12".equals(month)){
    		return year+"1231";
    	}
		return null;
	}
	
    /**
     * 获取当前时间，精确到秒
     * 时间格式为yyyyMMddHHmmss
     *
     * @return String
     * 精确到秒的当前时间
     */
    public static String getCurrentDateTime() {
        return getDateTimeByPattern(PATTERN_NUM_SEC);
    }

    /**
     * 获取当前时间，精确到毫秒
     * 时间格式为yyyyMMddHHmmssSSS
     *
     * @return String
     * 精确到毫秒的当前时间
     */
    public static String getCurrentDateTimeMs() {
        return getDateTimeByPattern(PATTERN_NUM_MS);
    }

    /**
     * 依据传入的时间格式获取当前时间
     * 比如传入的是yyyyMMdd,则获取的是精确到天的当前时间
     * 比如传入的是yyyyMMddHHmmss,则获取的是精确到秒的当前时间
     *
     * @param pattern 时间格式字符串
     * @return String
     * 符合要求的时间
     */
    public static String getDateTimeByPattern(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(new Date());
    }

    /**
     * 获取当前时间的前一天
     */
    public static Date getBeforeDate(Date date) {
        // 当前时间
        Date dNow = new Date();
        Date dBefore = new Date();
        // 得到日历
        Calendar calendar = Calendar.getInstance();
        // 把当前时间赋给日历
        calendar.setTime(dNow);
        // 设置为前一天
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        // 得到前一天的时间
        dBefore = calendar.getTime();
        return dBefore;
    }

    /**
     * 日期转换成字符串（格式：yyyyMMdd）
     * 
     * @param date
     * @return
     */
    public static String getDate(Date date){
        return new SimpleDateFormat(SHORT_DATE_PATTERN).format(date);
    }

    /**
     * 把yyyymmdd的字符串转化为yyyy-mm-dd格式的日期
     *
     * @param str
     * @return
     */
    public static Date strToDate(String str){
    	if(null == str || "".equals(str) || str.length() < 1){
    		return new Date();
    	}
    	StringBuffer sb = new StringBuffer();
    	String y = str.substring(0,4);
    	String m = str.substring(4,6);
    	String d = str.substring(6,8);
    	sb.append(y).append("-").append(m).append("-").append(d);
    	return shortStringToDate(sb.toString());
    }
    
    /**
     * TODO 获取当月的第一天或最后一天
     *
     * @author lsy
     * 2017年2月28日 上午10:49:31
     * @param a=0,b=1  当月第一天
     * @param a=1,b=0  当月最后一天
     * @return
     */
    public static String monthFirstOrLast(int a,int b){
    	Calendar cale = Calendar.getInstance();
    	cale.add(Calendar.MONTH, a);  
        cale.set(Calendar.DAY_OF_MONTH, b);
        return shortDateString(cale.getTime());
    }
    
}
