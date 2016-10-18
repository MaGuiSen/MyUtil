package com.ma.easysource.util;

import android.content.Context;

import com.ma.easyutil.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

/**
 * 处理此类，出现无法转换的问题
 *
 * @author gsma
 * @date 2015-6-29
 */
public class NumberUtil {

    public static <T> int getPageNum(List<T> data, int pageSize){
        int divider = data.size()/pageSize;
        int remainder = data.size()%pageSize;
        if(remainder !=0){
            divider += 1;
        }
        return divider;
    }

    /**
     * 判定，如果转换成功，返回成功值，失败返回默认值
     *
     * @param default_value
     * @param value
     * @return
     */
    public static int parseInt(String value, int default_value) {
        int result = default_value;
        try {
            result = Integer.parseInt(value);
        } catch (Exception e) {
            result = default_value;
            e.printStackTrace();
        }
        return result;
    }

    public static double parseDouble(String value, double default_value) {
        double result = default_value;
        try {
            result = new DecimalFormat().parse(value).doubleValue();
        } catch (Exception e) {
            result = default_value;
            e.printStackTrace();
        }
        return result;
    }

    public static long parseLong(String value, long default_value) {
        long result = default_value;
        try {
            result = Long.parseLong(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 提供精确的减法运算。
     * 避免浮点型相减出现小数点很多的情况
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    public static String meter2Kilometer(int meter,Context context){
        double d1 = meter / 1000.0;
        String result = null;
        if (d1 < 1.0) {
            result = String.valueOf(meter)+context.getResources().getString(R.string.drugstore_nearby_distance_unit_2);
        } else {
            BigDecimal b1 = new BigDecimal(d1);
            double f1 = b1.setScale(1, BigDecimal.ROUND_DOWN).doubleValue();
            result = String.valueOf(f1)+context.getResources().getString(R.string.drugstore_nearby_distance_unit_1);
        }

        return result;

    }
}
