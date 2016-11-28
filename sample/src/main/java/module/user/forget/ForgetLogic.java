package module.user.forget;

import android.content.Context;

import lib.util.TimeCountDownUtil;

public class ForgetLogic {
    Context context;
    Listener listener;
    TimeCountDownUtil timeCount;

    public ForgetLogic(Context context, Listener listener) {
        this.context = context;
        this.listener = listener;
        init();
    }

    private void init() {
        timeCount = new TimeCountDownUtil(60, new TimeCountDownUtil.TimeCountListener() {
            @Override
            public void onTimeCountFinish() {
                if (listener != null)
                    listener.changeVCodeBtnStatus(0, 0);
            }

            @Override
            public void onTimeCountRunning(long millisUntilFinished) {
                if (listener != null)
                    listener.changeVCodeBtnStatus(1, millisUntilFinished * 1000);
            }
        });
    }

    public void getVCode(String phone){
        /*处理VCode*/
        String msg = allowGetVCode(phone);
        if (!"".equals(msg)) {
            if (listener != null)listener.getVCodeFail(-100,msg);
            return;
        }

        timeCount.start();

        if (listener != null)listener.getVCodeSuccess("获取验证码成功");
    }

    /*这边处理验证部分，返回的String如果不为空，则显示错误提示信息*/
    public String allowGetVCode(String phone){
        return "";
    }

    public void register(String phone,String pwd,String vCode){
        String msg = allowRegister(phone,pwd,vCode);
        if (!"".equals(msg)) {
            if (listener != null)listener.registerFail(-200,msg);
            return;
        }
        if (listener != null)listener.registerSuccess("注册成功");
    }

    public String allowRegister(String phone,String pwd,String vCode){
        return "";
    }

    interface Listener {
        void getVCodeSuccess(Object object);
        void getVCodeFail(int code, String msg);

        void registerSuccess(Object object);
        void registerFail(int code, String msg);

        void changeVCodeBtnStatus(int status, long millisUntilFinished);
    }
}
