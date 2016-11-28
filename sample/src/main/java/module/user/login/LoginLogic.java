package module.user.login;

import android.content.Context;
import android.text.TextUtils;

/**
 * 登陆逻辑
 */
public class LoginLogic {
    Context context;
    Listener listener;

    public LoginLogic(Context context, Listener listener) {
        this.context = context;
        this.listener = listener;
    }

    /*用户名登陆部分*/
    public void comLogin(String userName,String pwd){
        String msg = allowCommit();
        if (!"".equals(msg)) {
            if (listener != null)listener.comLoginFail(-100,msg);
            return;
        }
        /*处理登陆逻辑*/
        /*处理登陆逻辑*/
        if (listener != null)listener.comLoginSuccess("登陆成功");
    }

    /*这边处理验证部分，返回的String如果不为空，则显示错误提示信息*/
    public String allowCommit(){
        return "";
    }

    /*三方登陆部分*/
    public void thirdLogin(int plat){
        /*登陆逻辑*/
        switch(plat){
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
        }
        /*登陆逻辑*/
        if (listener != null)listener.thirdLoginSuccess("三方登陆成功");
        if (listener != null)listener.thirdLoginFail(-200,"三方登陆失败");
    }

    interface Listener{
        //用户名密码登陆成功 失败
        void comLoginSuccess(Object object);
        void comLoginFail(int code,String msg);

        //三方登陆成功失败
        void thirdLoginSuccess(Object object);
        void thirdLoginFail(int code,String msg);
    }
}
