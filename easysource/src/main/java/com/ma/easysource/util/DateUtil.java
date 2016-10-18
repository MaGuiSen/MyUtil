package com.ma.easysource.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    /**
     * @param time yyyy MM dd HH:mm:ss
     * @return yyyy MM dd 星期日
     */
    public static String getTime(String time) {
        String[] days = {"日","一","二","三","四","五","六"};
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new SimpleDateFormat("yyy-MM-dd").parse(time));
            String dd = calendar.get(Calendar.YEAR) +"-"+ calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.DAY_OF_WEEK);
            return dd + " 星期" + days[calendar.get(Calendar.DAY_OF_WEEK)];
        } catch (ParseException e) {
            e.printStackTrace();
            return time;
        }
    }

    public static String getTimeStr(String time,String fmtStr) {
        try {
            DateFormat fmt = new SimpleDateFormat(fmtStr);
            Date date = fmt.parse(time);
            String dd = fmt.format(date);
            return dd;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 根据生日获取岁数
     *
     * @param birthday
     * @return
     */
    public static String getAgeByBirth(String birthday) {
        try {
            DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = fmt.parse(birthday);
            Calendar cal = Calendar.getInstance();

            if (cal.before(birthday)) {
                return "";
            }

            int yearNow = cal.get(Calendar.YEAR);
            int monthNow = cal.get(Calendar.MONTH) + 1;
            int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

            cal.setTime(date);
            int yearBirth = cal.get(Calendar.YEAR);
            int monthBirth = cal.get(Calendar.MONTH) + 1;
            int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

            int age = yearNow - yearBirth;

            if (monthNow <= monthBirth) {
                if (monthNow == monthBirth) {
                    // monthNow==monthBirth
                    if (dayOfMonthNow < dayOfMonthBirth) {
                        age--;
                    }
                } else {
                    // monthNow>monthBirth
                    age--;
                }
            }
            return age+"";
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 返回秒
     * @return
     */
    public static long getCurrSeconds() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 返回毫秒
     * @return
     */
    public static long getCurrMills() {
        return System.currentTimeMillis();
    }

    /**
     * 6` 5``
     * @param seconds
     * @return
     */
    public static String toSeconds(String seconds){
        /*
        1.如果<60,直接显示秒
        2.如果>60,转成分秒
         */
        int hour = 0, minute = 0, second = 0;
        int sec = NumberUtil.parseInt(seconds, 0);
        if(sec < 60){
            second = sec;
        }else if(sec < 3600){
            minute = (sec / 60);
            second = (sec % 60);
        }else{
            hour = sec / 3600;
            minute = sec / 60 % 60;
            second = sec - hour * 3600 - minute * 60;
        }
        return (hour == 0 ? "" : hour + "'") + (minute == 0 ? "" : minute + "'")  + (second == 0 ? "" : second + "\"");
    }

    /**
     * 以友好的方式显示时间
     */
    public static String getFriendTimeStr_1(long timeMills) {
        Date time = new Date(timeMills);
        if (time == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        //判断是否是同一天
        String curDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        String paramDate = new SimpleDateFormat("yyyy-MM-dd").format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0) {
                long timeSpace = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1);// + "分钟前";
                if (timeSpace > 2) {
                    //大于两分钟的时候，显示具体时间
                    ftime = new SimpleDateFormat("HH:mm").format(time);//显示具体时间
                } else {//否则显示前后
                    ftime = "刚刚";
                }
            } else {
                ftime = new SimpleDateFormat("HH:mm").format(time);//显示具体时间
            }
            return ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0) {
                long timeSpace = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1);// + "分钟前";
                if (timeSpace > 2) {
                    //大于两分钟的时候，显示具体时间
                    ftime = new SimpleDateFormat("HH:mm").format(time);//显示具体时间
                } else {//否则显示前后
                    ftime = "刚刚";
                }
            } else {
                ftime = new SimpleDateFormat("HH:mm").format(time);//显示具体时间
            }
        } else if (days == 1) {
            ftime ="昨天";
        } else if (days >= 2) {
            ftime = new SimpleDateFormat("MM-dd").format(time);//显示具体时间
        }
        return ftime;
    }

    /**
     * 时间转化为聊天界面显示字符串
     * @param timeStamp 单位为毫秒
     */
    public static String getFriendTimeStr_2(long timeStamp) {
        if (timeStamp == 0) return "";
        Calendar inputTime = Calendar.getInstance();
        inputTime.setTimeInMillis(timeStamp);
        Date currenTimeZone = inputTime.getTime();
        Calendar calendar = Calendar.getInstance();
        if (calendar.before(inputTime)) {
            //当前时间在输入时间之前
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
            return sdf.format(currenTimeZone);
        }
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        if (calendar.before(inputTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            return sdf.format(currenTimeZone);
        }
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        if (calendar.before(inputTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            return "昨天" + sdf.format(currenTimeZone);
        } else {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.MONTH, Calendar.JANUARY);
            if (calendar.before(inputTime)) {
                SimpleDateFormat sdf = new SimpleDateFormat("M月d日 HH:mm");
                return sdf.format(currenTimeZone);
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
                return sdf.format(currenTimeZone);
            }
        }
    }

    /**
     * 判断日期是否是今天
     *
     * @param year
     * @param month
     * @param day
     * @return true 代表是
     */
    public static boolean isToday(int year, int month, int day) {
        //判断日期是否大于当前的日期
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        int current_year = cal.get(Calendar.YEAR);
        int current_month = cal.get(Calendar.MONTH);
        int current_day = cal.get(Calendar.DAY_OF_MONTH);
        return year == current_year && month == current_month && day == current_day;
    }

    /**
     *
     * 比较两个时间大小
     * @param date1
     * @param date2
     * @return   true：date1 <= date2   false:date1 > date2
     */
    public static boolean compare(Calendar date1,Calendar date2){
        long diff = date2.getTimeInMillis() - date1.getTimeInMillis();
        return diff >= 0;
    }

    /**
     * 判断是否大于几岁
     */
    public static boolean isOverAge(Calendar birthDay,int compareAge){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        int current_year = cal.get(Calendar.YEAR);
        int current_month = cal.get(Calendar.MONTH);
        int current_day = cal.get(Calendar.DAY_OF_MONTH);
        if(birthDay.get(Calendar.YEAR) >= (current_year - compareAge)){
            if(birthDay.get(Calendar.YEAR) == (current_year - compareAge)){
                if(birthDay.get(Calendar.MONTH) >= current_month){
                    if(birthDay.get(Calendar.MONTH) == current_month){
                        if(birthDay.get(Calendar.DAY_OF_WEEK) >= current_day){
                            return true;
                        }
                    }else{
                        return true;
                    }
                }
            }else{
                return true;
            }

        }
        return false;
    }

    /**
     * 判断是否大于今天
     */
    public static boolean isOverToday(Calendar birthDay){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        int current_year = cal.get(Calendar.YEAR);
        int current_month = cal.get(Calendar.MONTH);
        int current_day = cal.get(Calendar.DAY_OF_MONTH);
        if(birthDay.get(Calendar.YEAR) >= (current_year)){
            if(birthDay.get(Calendar.YEAR) == (current_year)){
                if(birthDay.get(Calendar.MONTH) >= current_month){
                    if(birthDay.get(Calendar.MONTH) == current_month){
                        if(birthDay.get(Calendar.DAY_OF_WEEK) >= current_day){
                            return true;
                        }
                    }else{
                        return true;
                    }
                }
            }else{
                return true;
            }

        }
        return false;
    }

    /*
    判断是周末
     */
    public static boolean isWeekend(Calendar cal){
        return cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY||cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY;
    }
}
