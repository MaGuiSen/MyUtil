package lib.util;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MD5Util {

    protected static char sHexDigits[] = {'0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    //密码串的MD5需要用小写的这个，不懂为什么
    protected static char  hexDigits[] = {  '0' ,  '1' ,  '2' ,  '3' ,  '4' ,  '5' ,  '6' ,
            '7' ,  '8' ,  '9' ,  'a' ,  'b' ,  'c' ,  'd' ,  'e' ,  'f'  };
    private static final String LOG_TAG = "MD5";
    private static final String ALGORITHM = "MD5";

    private static MessageDigest sDigest;

    static {
        try {
            sDigest = MessageDigest.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "Get MD5 Digest failed.");
        }
    }

    /**
     * 创建参数请求标识
     *
     * @param map
     * @param token_key
     * @return
     */
    public static String createParamSign(Map<String, String> map, String token_key) {

        List<String> strList = new ArrayList<String>();
        String[] keys = map.keySet().toArray(new String[0]);
        for (int i = 0; i < keys.length; i++) {
            strList.add(keys[i]);
        }
        // 排序
        Collections.sort(strList);

        StringBuilder builder = new StringBuilder();
        builder.append(token_key);
        for (int i = 0, size = strList.size(); i < size; i++) {
            builder.append(strList.get(i) + map.get(strList.get(i)));
        }
        builder.append(token_key);

        return encode(builder.toString());
    }

    final private static String encode(String source) {
        byte[] btyes = source.getBytes();
        byte[] encodedBytes = sDigest.digest(btyes);

        return hexString(encodedBytes);
    }

    public static String hexString(byte[] source) {
        if (source == null || source.length <= 0) {
            return "";
        }

        final int size = source.length;
        final char str[] = new char[size * 2];
        int index = 0;
        byte b;
        for (int i = 0; i < size; i++) {
            b = source[i];
            str[index++] = sHexDigits[b >>> 4 & 0xf];
            str[index++] = sHexDigits[b & 0xf];
        }
        return new String(str);
    }

    /**
     * 生成字符串的MD5检验值
     * @param str
     * @return
     */
    public static String getMD5String(String str){
        if(str == null)
            return null;

        MessageDigest mMessageDigest = null;
        try {
            mMessageDigest = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        mMessageDigest.update(str.getBytes());
        byte[] digest = mMessageDigest.digest();
        StringBuffer sBuffer = new StringBuffer(2*digest.length);
        for(int i = 0; i < digest.length; i++){
            char c0 = hexDigits[(digest[i] & 0xf0) >> 4];	//取字节中高 4 位的数字转换, >>> 为逻辑右移，将符号位一起右移
            char c1 = hexDigits[digest[i] & 0xf];			//取字节中低 4 位的数字转换
            sBuffer.append(c0);
            sBuffer.append(c1);
        }
        return sBuffer.toString();
    }

    /**
     * 生成字符串的MD5检验值
     * @param data
     * @return
     */
    public static String getMD5String(byte[] data){
        if(data == null)
            return null;

        MessageDigest mMessageDigest = null;
        try {
            mMessageDigest = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        mMessageDigest.update(data);
        byte[] digest = mMessageDigest.digest();
        StringBuffer sBuffer = new StringBuffer(2*digest.length);
        for(int i = 0; i < digest.length; i++){
            char c0 = hexDigits[(digest[i] & 0xf0) >> 4];	//取字节中高 4 位的数字转换, >>> 为逻辑右移，将符号位一起右移
            char c1 = hexDigits[digest[i] & 0xf];			//取字节中低 4 位的数字转换
            sBuffer.append(c0);
            sBuffer.append(c1);
        }
        return sBuffer.toString();
    }
}
