package lib.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Build;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 图片处理类
 * Created by 马贵森 on 2015/9/23.
 */
public class BitmapUtil {

    // 将Bitmap转换成InputStream
    public static InputStream Bitmap2InputStream(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //Hint to the compressor, 0-100. 0 meaning compress for small size, 100 meaning compress for max quality. Some formats, like PNG which is lossless, will ignore the quality setting
        bm.compress(Bitmap.CompressFormat.JPEG, 50, baos);//100 是压缩率，表示压缩0%; 如果不压缩是100，表示压缩率为0
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        return is;
    }

    public static InputStream Bitmap2InputStream(Bitmap bm, int quality) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //Hint to the compressor, 0-100. 0 meaning compress for small size, 100 meaning compress for max quality. Some formats, like PNG which is lossless, will ignore the quality setting
        bm.compress(Bitmap.CompressFormat.JPEG, quality, baos);//100 是压缩率，表示压缩0%; 如果不压缩是100，表示压缩率为0
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        return is;
    }

    public static long getBitmapsize(Bitmap bitmap){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return bitmap.getByteCount();
        }
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    public static Bitmap File2Bitmap(String aImageName){
        if (aImageName != null & aImageName.length() > 0) {
            File b = new File(aImageName);
            if (b.exists() && b.length() > 0) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;//不加载图片到内存，仅获得图片宽高
                BitmapFactory.decodeFile(aImageName, options);
                try {
                    ExifInterface exif = new ExifInterface(aImageName);

                    int picWidth = exif.getAttributeInt(
                            ExifInterface.TAG_IMAGE_WIDTH, ExifInterface.ORIENTATION_NORMAL);
                    int picHeight = exif.getAttributeInt(
                            ExifInterface.TAG_IMAGE_LENGTH, ExifInterface.ORIENTATION_NORMAL);
                    if(options.outHeight == -1 || options.outWidth == -1){
                        options.outWidth = picWidth;
                        options.outHeight = picHeight;
                    }
                    int be = 1;
                    if(picWidth > 1000 && picHeight > 1000){
                        if(picWidth > picHeight){

                        }else if(picHeight > picWidth){

                        }else if(picWidth == picHeight){

                        }
                    }
//                    options.inSampleSize = calculateInSampleSize(options, (int)picWidth, (int)picHeight);
                    options.inSampleSize = 6;
                    options.inJustDecodeBounds = false;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return BitmapFactory.decodeFile(aImageName, options);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 仅接上面方法配合获得图片采样率，是个不错的方案就拷过来了
     * @param options
     * @param requestWidth
     * @param requestHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int requestWidth, int requestHeight){
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if(height > requestHeight || width > requestWidth){
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            //计算采样率
            while ((halfHeight / inSampleSize) > requestHeight
                    && (halfWidth / width) > requestWidth){
                inSampleSize *= 2;
            }

            long totalPixels = width * height / inSampleSize;

            final long totalReqPixelsCap = requestWidth * requestHeight * 2;

            while (totalPixels > totalReqPixelsCap){
                inSampleSize *= 2;
                totalPixels /= 2;
            }
        }
        return inSampleSize;
    }

    public static Bitmap File2Bitmap(String aImageName,int scaleWidth){
        if (aImageName != null & aImageName.length() > 0) {
            File b = new File(aImageName);
            if (b.exists() && b.length() > 0) {
                BitmapFactory.Options cwj = new BitmapFactory.Options();
                try {
                    ExifInterface exif = new ExifInterface(aImageName);

                    int picwidth = exif.getAttributeInt(
                            ExifInterface.TAG_IMAGE_WIDTH, 1000);

                    int be = picwidth / scaleWidth;
                    if (be <= 0) {
                        be = 1;
                    }
                    cwj.inSampleSize = be;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return BitmapFactory.decodeFile(aImageName, cwj);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static Bitmap file2Bitmap(String aImageName){
        if (aImageName != null & aImageName.length() > 0) {
            File b = new File(aImageName);
            if (b.exists() && b.length() > 0) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeFile(aImageName, options);
                try {
                    ExifInterface exif = new ExifInterface(aImageName);

                    double picWidth = exif.getAttributeInt(
                            ExifInterface.TAG_IMAGE_WIDTH, ExifInterface.ORIENTATION_NORMAL);
                    double picHeight = exif.getAttributeInt(
                            ExifInterface.TAG_IMAGE_LENGTH, ExifInterface.ORIENTATION_NORMAL);
                    if (options.outHeight == -1 || options.outWidth == -1) {
                        options.outWidth = (int) picWidth;
                        options.outHeight = (int) picHeight;
                    }
                    Matrix matrix = new Matrix();
                    float scaleWidth = 1;
                    float scaleHeight = 1;
                    if(picWidth > 1000 && picHeight > 1000){
                        if(picWidth > picHeight){
                            double width = (picWidth / picHeight) * 1000;
                            scaleWidth = (float) (width / picWidth);
                            scaleHeight = (float) (1000 / picHeight);
                        }else if(picHeight > picWidth){
                            double height = 1000 / (picWidth / picHeight);
                            scaleWidth = (float) (1000 / picWidth);
                            scaleHeight = (float) (height / picHeight);
                        }
                    }
                    matrix.postScale(scaleWidth, scaleHeight);
                    return Bitmap.createBitmap(bitmap, 0, 0, (int)picWidth, (int)picHeight, matrix, false);
                }catch (IOException e) {
                    e.printStackTrace();
                    return bitmap;
                }
            }else {
                return null;
            }
        }else{
            return null;
        }
    }

    public static void compressPhotoAndCopy(String srcPath, String destPath, FileModel fileModel){
        if (srcPath != null & srcPath.length() > 0) {
            File srcFile = new File(srcPath);
            if (srcFile.exists() && srcFile.length() > 0) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_4444;
                Bitmap bitmap = null;
                Bitmap resizeBitmap = null;
                FileOutputStream fos = null;
                try {
                    bitmap = BitmapFactory.decodeFile(srcPath, options);
                    double picWidth = bitmap.getWidth();
                    double picHeight = bitmap.getHeight();
//                    ExifInterface exif = new ExifInterface(srcPath);
//
//                    double picWidth = exif.getAttributeInt(
//                            ExifInterface.TAG_IMAGE_WIDTH, ExifInterface.ORIENTATION_NORMAL);
//                    double picHeight = exif.getAttributeInt(
//                            ExifInterface.TAG_IMAGE_LENGTH, ExifInterface.ORIENTATION_NORMAL);
//                    if (picWidth <= 0 || picHeight <= 0){
//                        if(options.outHeight != -1 && options.outWidth != -1){
//                            picWidth = options.outHeight;
//                            picHeight = options.outWidth;
//                        }
//                    }
//                    if (options.outHeight == -1 || options.outWidth == -1) {
//                        options.outWidth = (int) picWidth;
//                        options.outHeight = (int) picHeight;
//                    }
                    Matrix matrix = new Matrix();
                    float scaleWidth = 1;
                    float scaleHeight = 1;
                    if(picWidth > 1000 && picHeight > 1000){
                        if(picWidth > picHeight){
                            double width = (picWidth / picHeight) * 1000;
                            scaleWidth = (float) (width / picWidth);
                            scaleHeight = (float) (1000 / picHeight);
                        }else if(picHeight > picWidth){
                            double height = 1000 / (picWidth / picHeight);
                            scaleWidth = (float) (1000 / picWidth);
                            scaleHeight = (float) (height / picHeight);
                        }
                    }
                    matrix.postScale(scaleWidth, scaleHeight);
                    resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, (int)picWidth, (int)picHeight, matrix, false);
//                    File destFile = new File(destPath);
                    fos = new FileOutputStream(destPath);
                    if(resizeBitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos)){
                        fos.flush();
                        fileModel.setFile_size(fos.getChannel().size() + "");
                    }
                }catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(bitmap != null && !bitmap.isRecycled()){
                        bitmap.recycle();
                        bitmap = null;
                    }
                    if(resizeBitmap != null && !resizeBitmap.isRecycled()){
                        resizeBitmap.recycle();
                        resizeBitmap = null;
                    }
                    if(fos != null)
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }
            }
        }
    }

    /**
     * 获取拍照后，图片的旋转角度
     * @param path 图片的绝对路径
     * @return  图片的旋转角度
     */
    public static int getBitmapDegree(String path){
        int degree = 0;
        try {
            //从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            //获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation){
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 将图片按照指定的角度进行旋转
     * @param bitmap 需要旋转的图片
     * @param degree 指定的旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bitmap, int degree){
        //根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        //将原始图片按照旋转矩阵进行旋转，并得到新的图片
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if(bitmap != null && !bitmap.isRecycled()){
            bitmap.recycle();
        }
        return newBitmap;
    }
}
