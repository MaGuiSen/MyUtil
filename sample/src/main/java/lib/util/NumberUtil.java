package lib.util;

import android.content.Context;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

public class NumberUtil {
    /**
     * 分页
     */
    public static <T> int getPageNum(List<T> data, int pageSize){
        int divider = data.size()/pageSize;
        int remainder = data.size()%pageSize;
        if(remainder !=0){
            divider += 1;
        }
        return divider;
    }

    public static boolean isDouble(String parseString){
        try{
            Double.parseDouble(parseString);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    /**
     *     保留几位
     */
    public static double KeepAfew(double value,int few){
        return new BigDecimal(value).setScale(few, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 判定，如果转换成功，返回成功值，失败返回默认值
     *
     * @param defaul_value
     * @param value
     * @return
     */
    public static int parseInt(String value, int defaul_value) {
        int result = defaul_value;
        try {
            result = Integer.parseInt(value);
        } catch (Exception e) {
            result = defaul_value;
            e.printStackTrace();
        }
        return result;
    }

    public static double parseDouble(String value, double defaul_value) {
        double result = defaul_value;
        try {
            result = new DecimalFormat().parse(value).doubleValue();
        } catch (Exception e) {
            result = defaul_value;
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

    /**
     * 米到千米
     * @param meter
     * @param context
     * @return
     */
    public static String meter2KM(int meter,Context context){
        double d1 = meter / 1000.0;
        String result = null;
        if (d1 < 1.0) {
            result = String.valueOf(meter)+"米";
        } else {
            BigDecimal b1 = new BigDecimal(d1);
            double f1 = b1.setScale(1, BigDecimal.ROUND_DOWN).doubleValue();
            result = String.valueOf(f1)+"千米";
        }

        return result;

    }
}
