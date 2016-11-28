//package module;
//
//import android.annotation.TargetApi;
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.os.Build;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.view.inputmethod.InputMethodManager;
//
//import com.readystatesoftware.systembartint.SystemBarTintManager;
//
//import de.greenrobot.event.EventBus;
//
//public class BaseActivity extends FragmentActivity {
//    public static final String IS_FROM_PUSH = "isFromPush";//传递路径
//    protected Boolean isLoading;
//    protected LoadingDialog mLoadingDialog;
//
//    private SystemBarTintManager tintManager;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        //设定状态栏的颜色，当版本大于4.4时起作用
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//
//            setTranslucentStatus(true);
//
//            tintManager = new SystemBarTintManager(this);
//            tintManager.setStatusBarTintEnabled(true);
//            tintManager.setNavigationBarTintEnabled(true);
//            //此处可以重新指定状态栏颜色
//            tintManager.setTintColor(0xff01aa94);
////          tintManager.setTintResource(R.drawable.bg_bar_status);
//        }
//
//        initBase();
//    }
//
//    protected void transparencyStatusBar(boolean isTransparency) {
//        //版本大于4.4
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            if (isTransparency)
//                tintManager.setTintColor(0x00000000);
//            else {
//                tintManager.setTintColor(0xff01aa94);
////              tintManager.setTintResource(R.drawable.bg_bar_status);
//            }
//        }
//    }
//
//    protected void transparencyStatusBar() {
//        //版本大于4.4
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            tintManager.setTintColor(0x00000000);
//        }
//    }
//
//    @Override
//    public void finish() {
//        hideSoftInput();
//        super.finish();
//    }
//
//    @Override
//    protected void  onDestroy() {
//        super.onDestroy();
//        if(isRegisterEventBus)
//            EventBus.getDefault().unregister(this);
//    }
//
//    private boolean isRegisterEventBus = false;//判断是否进行EventBus的注册，用于取消注册时的判断
//    /**
//     * 注册EventBus ，如果调用此方法，必须要声明接收函数，否则报错
//     * onEvent(AnyTypeEvent event)/onEventMainThread(AnyTypeEvent event)..
//     * 具体参见EventBus使用介绍http://blog.csdn.net/harvic880925/article/details/40787203
//     */
//    protected void registerEventBus(){
//        EventBus.getDefault().register(this);
//        isRegisterEventBus = true;
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//    }
//
//    private void initBase() {
//        ToastUtil.setContext(this);
//    }
//
//    @Override
//    public void setContentView(View view) {
//        super.setContentView(view);
//    }
//
//    @TargetApi(19)
//    private void setTranslucentStatus(boolean on) {
//        Window win = getWindow();
//        WindowManager.LayoutParams winParams = win.getAttributes();
//        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
//        if (on) {
//            winParams.flags |= bits;
//        } else {
//            winParams.flags &= ~bits;
//        }
//        win.setAttributes(winParams);
//    }
//
//    public Activity getActivity(){
//        return this;
//    }
//
//
//    /**
//     * 处理加载
//     *
//     * @param message
//     */
//    public void showLoadingDialog(String message) {
//        this.showLoadingDialog(message, true, true);
//    }
//
//    public void showLoadingDialog(String message, boolean cancelable, boolean otoCancelable) {
//        mLoadingDialog = new LoadingDialog(this, message, cancelable, otoCancelable);
//        mLoadingDialog.show();
//    }
//
//    /**
//     * 关闭进度条
//     */
//    public void dismissLoadingDialog() {
//        if (mLoadingDialog != null) {
//            mLoadingDialog.dismiss();
//        }
//        isLoading = false;
//    }
//
//
//    /**
//     * 隐藏软键盘
//     */
//    public void hideSoftInput() {
//        /** 隐藏软键盘 **/
//        View view = getWindow().peekDecorView();
//        if (view != null) {
//            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
//        }
//    }
//
//    /**
//     * 返回键按下时候进行是否从极光通知进入的提示,并进行activity的启动
//     */
//    public void endJPushNew(){
//        boolean isFromJpush = getIntent().getBooleanExtra(IS_FROM_PUSH,false);
//
//        if (isFromJpush &&!SystemUtil.isLuncher(this, MainActivity.class.getName())) {
//            Intent intent = new Intent();
//            intent.setClass(this, SplashActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            this.startActivity(intent);
//        }
//        this.finish();
//    }
//
//    /**
//     * Manifest.permission. READ_PHONE_STATE
//     * Manifest.permission. WRITE_EXTERNAL_STORAGE
//     * Manifest.permission.ACCESS_FINE_LOCATION
//     * Manifest.permission.CAMERA
//     * Manifest.permission.RECORD_AUDIO
//     * Manifest.permission.READ_PHONE_STATE
//     * Manifest.permission.WRITE_EXTERNAL_STORAGE
//     *权限检查
//     */
//    public final static int REQ_CAMERA_PMS = 0x001;
//    public final static int REQ_RECORD_PMS = 0x002;
//    public final static int REQ_lOCATION_PMS = 0x003;
//    public final static int REQ_READ_PHONE_STATE_PMS = 0x004;
//    public final static int REQ_WRITE_EXTERNAL_STORAGE_PMS = 0x005;
//    public boolean permissionIsGet(int reqCode,String permission){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(new String[]{permission}, reqCode);
//            return false;
//        }else{
//            return true;
//        }
//    }
//
//    //并监听用户权限选择返回
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        switch (requestCode) {
//            case REQ_RECORD_PMS:
//                if (grantResults != null && grantResults.length != 0)
//                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
//                        ToastUtil.show("您需要开启语音权限,并重启应用");
//                    }
//                break;
//            case REQ_CAMERA_PMS:
//                if (grantResults != null && grantResults.length != 0)
//                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
//                        ToastUtil.show("您需要开启照相权限,并重启应用");
//                    }
//                break;
//            case REQ_lOCATION_PMS:
//                if (grantResults != null && grantResults.length > 1)
//                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED && grantResults[1] != PackageManager.PERMISSION_GRANTED) {
//                        ToastUtil.show("您需要开启定位权限,并重启应用");
//                    }else{
//                        permissionLocGeted();
//                    }
//                break;
//            case REQ_READ_PHONE_STATE_PMS:
//                if (grantResults != null && grantResults.length > 1)
//                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED && grantResults[1] != PackageManager.PERMISSION_GRANTED) {
//                        ToastUtil.show("您需要开启获取手机状态权限,并重启应用");
//                    }else{
//                        permissionLocGeted();
//                    }
//                break;
//            case REQ_WRITE_EXTERNAL_STORAGE_PMS:
//                if (grantResults != null && grantResults.length > 1)
//                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED && grantResults[1] != PackageManager.PERMISSION_GRANTED) {
//                        ToastUtil.show("您需要开启读取内存权限,并重启应用");
//                    }else{
//                        permissionLocGeted();
//                    }
//                break;
//            default:
//                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }
//    }
//
//    /**
//     *定位权限获取到时候的处理
//     */
//    public void permissionLocGeted(){
//
//    }
//}
