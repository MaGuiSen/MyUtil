package lib.util;

import android.content.Context;

import com.alibaba.fastjson.JSON;

import java.io.File;
import java.io.InputStream;

public class CacheJsonUtil {

	private static CacheJsonUtil sInstance;

    public static CacheJsonUtil getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new CacheJsonUtil(context);
        }
        return sInstance;
    }

	private String mDirPath;

	private CacheJsonUtil(Context context) {
		mDirPath = context.getFilesDir().getAbsolutePath();
	}

    /**
     * 根据类名获取缓存的数据
     * @param clazz
     * @return
     */
	public Object getJsonObject(Class<?> clazz) {
		return getJsonObject(clazz,clazz.getSimpleName());
	}

    /**
     * 根据类名及键名获取数据
     * @param clazz
     * @param cacheKey
     * @return
     */
    public Object getJsonObject(Class<?> clazz,String cacheKey) {
        Object object = null;
        File file = FileUtil.createFile(mDirPath, cacheKey);
        if(file.exists()){
            InputStream inputStream = FileUtil.getInputStreamFromFile(file);
            String jsonStr = FileUtil.getStringFromInputStream(inputStream);
            if (jsonStr == null) {
                return object;
            }
            try {
                if(jsonStr.startsWith("[")){
                    object = JSON.parseArray(jsonStr,clazz);
                }else if(jsonStr.startsWith("{")){
                    object = JSON.parseObject(jsonStr, clazz);
                }
            } catch (Exception e) {
                e.printStackTrace();
                object = null;
            }
        }
        return object;
    }

    public void saveJson(String jsonStr, String fileName,boolean isAdd) {
		if (jsonStr == null || jsonStr.isEmpty()) {
			return;
		}
        if (fileName == null || fileName.isEmpty()) {
            return;
        }
		File file = FileUtil.createFile(mDirPath, fileName);
		FileUtil.stringToFile(jsonStr, file,isAdd);
	}
}
