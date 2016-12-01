package lib.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;

/**
 * Created by 1ping on 2015/12/15.
 * 封装图像选择
 */
public class PhotoChoiceUtil {
    public final static int FOR_SELECT_PHOTO = 19;//选择图片
    public final static int FOR_START_CAMERA = 20;//拍照


    public static void onActivityResult(Activity mActivity, int requestCode, int resultCode, Intent data, SelectPhotoListener selectListner) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case FOR_SELECT_PHOTO:
                    if (data == null) {
                        ToastUtil.show("选择图片文件出错");
                        return;
                    }
                    Uri photoUri = data.getData();
                    if (photoUri == null) {
                        ToastUtil.show("选择图片文件出错");
                        return;
                    }
                    doPhoto(mActivity, photoUri, selectListner);
                    break;
                case FOR_START_CAMERA:
                    if (selectListner != null) {
                        selectListner.selectPhotoSuccess(photo_temp.getPath());
                    }
                    break;
            }
        }
    }


    /**
     * 选择图片
     */
    public static void selectPhoto(Activity mActivity) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        mActivity.startActivityForResult(intent, FOR_SELECT_PHOTO);
    }


    /**
     * 选择图片回来时候的操作
     *
     * @param photoUri
     */
    private static void doPhoto(Activity mActivity, Uri photoUri, SelectPhotoListener selectListner) {
        String[] pojo = {MediaStore.Images.Media.DATA};
        ContentResolver cr = mActivity.getContentResolver();
        Cursor cursor = cr.query(photoUri, pojo, null, null, null);
        String picPath = null;
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
            cursor.moveToFirst();
            picPath = cursor.getString(columnIndex);
            cursor.close();
        }
        if (selectListner != null) {
            if (picPath != null && (picPath.endsWith(".png") || picPath.endsWith(".PNG") || picPath.endsWith(".jpg") || picPath.endsWith(".JPG"))) {
                if (picPath != null) {
                    selectListner.selectPhotoSuccess(picPath);
                } else {
                    selectListner.selectPhotoFail("选择图片文件出错");
                }
            } else {
                selectListner.selectPhotoFail("选择图片文件出错");
            }
        }
    }


    //静态的原因是为了防止Activity回来的时候被销毁
    static File photo_temp = null;

    /**
     * 打开相机照相
     */
    public static void startCamera(Activity mActivity) {
        //执行拍照前，应该先判断SD卡是否存在
        String SDState = Environment.getExternalStorageState();
        if (SDState.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            photo_temp = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".png");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo_temp));
            mActivity.startActivityForResult(intent, FOR_START_CAMERA);
        } else {
            ToastUtil.show("内存卡不存在");
        }
    }


    public interface SelectPhotoListener {
        void selectPhotoSuccess(String picPath);

        void selectPhotoFail(String errMsg);
    }
}
