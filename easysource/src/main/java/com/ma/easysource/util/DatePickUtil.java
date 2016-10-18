package com.ma.easysource.util;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ma.easysource.widget.ToastUtil;
import com.ma.easyutil.R;

import java.util.Calendar;

public class DatePickUtil {
    /**
     * 日期控件
     * 只能选择 过去时间，操作今天时间为不合法时间
     * 用在：生日选择，治疗时间选择
     *
     * @param mContext
     * @param curEdit
     */
    public static void datePicker(final Context mContext, final TextView curEdit) {
        String curDate = curEdit.getText().toString().trim();
        int year;
        int month;
        int day;

        if ("".equals(curDate)) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);
            day = cal.get(Calendar.DAY_OF_MONTH);
        } else {
            String[] dateStrs = curDate.split("-");
            year = NumberUtil.parseInt(dateStrs[0],2016);
            month = NumberUtil.parseInt(dateStrs[1],12) - 1;
            day = NumberUtil.parseInt(dateStrs[2],1);
        }

        DatePickerDialog pickerDialog = new DatePickerDialog(
                mContext, new OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                String month = monthOfYear + 1 + "";
                String day = dayOfMonth + "";
                if (monthOfYear + 1 < 10)
                    month = "0" + month;
                if (dayOfMonth < 10)
                    day = "0" + day;
                if (DateCompare(year, monthOfYear, dayOfMonth))
                    curEdit.setText(year + "-" + month + "-" + day);
                else {
                    ToastUtil.show(mContext.getResources().getString(R.string.toast_select_date_fail));
                }
            }


        }, year, month, day);
        pickerDialog.show();
    }

    /**
     * 时间控件
     *
     * @param mContext
     * @param curEdit
     */
    public static void timePicker(Context mContext, final TextView curEdit) {

        String curTime = curEdit.getText().toString().trim();

        int hour;
        int minute;

        if ("".equals(curTime)) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());

            hour = cal.get(Calendar.HOUR_OF_DAY);
            minute = cal.get(Calendar.MINUTE);
        } else {
            String[] dateStrs = curTime.split(":");
            hour = NumberUtil.parseInt(dateStrs[0],23);
            minute = NumberUtil.parseInt(dateStrs[1],59);
        }

        TimePickerDialog pickerDialog = new TimePickerDialog(mContext, new OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker timePicker, int arg1, int arg2) {
                String month = arg1 + "";
                String day = arg2 + "";
                if (arg1 < 10)
                    month = "0" + month;
                if (arg2 < 10)
                    day = "0" + day;
                curEdit.setText(month + ":" + day + ":00");
            }
        }, hour, minute, true);
        pickerDialog.show();
    }

    /**
     * 日期比较
     * 判断日期是否大于当前的日期
     *
     * @param year
     * @param month
     * @param day
     * @return false代表是
     */
    public static boolean DateCompare(int year, int month, int day) {
        //判断日期是否大于当前的日期
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        int current_year = cal.get(Calendar.YEAR);
        int current_month = cal.get(Calendar.MONTH);
        int current_day = cal.get(Calendar.DAY_OF_MONTH);
        if (year <= current_year) {
            if (year == current_year) {
                if (month <= current_month) {
                    if (month == current_month) {
                        if (day > current_day)
                            return false;
                    }
                } else {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
