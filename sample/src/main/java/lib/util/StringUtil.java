package lib.util;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作工具包
 */
public class StringUtil {
    /**
     * 保留两位小数  并去小数位数0
     * @param str
     * @return
     */
    public static String getTwoPointNumber(String str){
        String strValue = str;
        try {
            DecimalFormat df = new DecimalFormat("0.00");
            strValue = df.format(Double.parseDouble(strValue));

            if(strValue.indexOf(".") > 0){
                //正则表达
                strValue = strValue.replaceAll("0+?$", "");//去掉后面无用的零
                strValue = strValue.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return strValue;
    }

    /**
     * 将电话号码转化为密文
     */
    public static String changePhoneTOSecret(String phone) {
        if(TextUtils.isEmpty(phone))return "";
        try {
            phone = phone.substring(0, 3) + "****" + phone.substring(phone.length() - 4, phone.length());
        } catch (Exception e) {
            phone = "********";
        }
        return phone;
    }

    /**
     * 是否两个字符串是否匹配，即匹配字符串是否是元字符串的开头/拼音小写的开头
     */
    public static boolean isMatching(String filterRes, String filterString) {
        if (filterRes == null || filterString == null) {
            return false;
        }
        CharacterParserUtil characterParser = CharacterParserUtil.getInstance();
        //过滤词是开头
        if (filterRes.contains(filterString)) {
            return true;
        }
        //过滤词是原字符串的拼音小写开头
        if (characterParser.getSelling(filterRes).toLowerCase().contains(filterString.toLowerCase())) {
            return true;
        }
        return false;
    }

    /**
     * 替换\n
     */
    public static String replaceWrap(String str) {
        if(!TextUtils.isEmpty(str)){
            str = str.replaceAll("\\r\\n", "\n").replaceAll("\\\\n", "\n").replaceAll("\\r\\r","\n");
        }
        return str;
    }

    public static boolean isJsonObject(String str){
        return str != null && str.startsWith("{") && str.endsWith("}");
    }

    public static boolean isJsonArray(String str){
        return str != null && str.startsWith("[") && str.endsWith("]");
    }

    /**
     * 验证或关系
     * @param str
     * @param regexs 规则
     * @return
     */
    public static boolean validateStr_or(String str, String... regexs){
        for(String regex : regexs){
            if(TextUtils.equals(str, regex))
                return true;
        }
        return false;
    }

    /**
     * 高亮显示
     * @param str 显示的字符串
     * @param color 显示的颜色值  eg:Color.RED or 0xff000000
     * @param lightStr 要高亮显示的字段数组
     */
    public static SpannableStringBuilder setLight(String str, int color, Object...lightStr) {

        SpannableStringBuilder style = new SpannableStringBuilder(str);

        for(int i = 0; i<lightStr.length; i++){
            if(lightStr[i] != null && !"".equals(lightStr[i])){
                String light_str = lightStr[i].toString();
                if(str.contains(light_str)){
                    int start = str.indexOf(light_str);
                    style.setSpan(new ForegroundColorSpan(color), start, start
                            + lightStr[i].toString().length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                }
            }
        }
        return style;
    }

    /**
     * 解析字符串并匹配相应的正则表达式
     * 此方法目前主要服务于解析模版消息中的text、functions
     * @param str 字符串
     * @param regex 正则表达式  "\\{\\{([\\s\\S]*?)\\}\\}"
     * @return 返回符合规则的字符串list
     */
    public static List<String> analyzeStringMatchRegex(String str, String regex){
        List<String> stringList = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()){
            stringList.add(matcher.group());
        }
        return stringList;
    }

    /**
     * 从匹配的字符串中取出需要用到的值
     * @param str 字符串
     * @return 返回值key
     */
    public static String extractStringKey(String str){
        str = str.replace("{{", "").replace("}}", "");
        //.需要转义
        String[] strArr = str.split("\\.");
        if(strArr.length > 0){
            return strArr[0];
        }else{
            return "";
        }
    }

    /**
     * 给文字添加颜色，并返回一个font标签
     * @param value 文字值
     * @param color 颜色值
     * @return font标签
     */
    public static String addColor(String value, String color){
        if(TextUtils.isEmpty(value)) return "";
        if(TextUtils.isEmpty(color)) color = "#ff6496c8";
        return "<font color='"+color+"'>"+value+"</font>";
    }

    public static String addColorAndClick(String value, String color, String click){
        if(TextUtils.isEmpty(value)) return "";
        if(TextUtils.isEmpty(color)) color = "#ff6496c8";
        if(TextUtils.isEmpty(click)) click = "";
        return "<font color='"+color+"'><a href='"+click+"'>"+value+"</a></font>";
    }
}