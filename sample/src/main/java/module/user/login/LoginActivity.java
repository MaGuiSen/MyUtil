package module.user.login;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.ma.myutil.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登陆ui界面
 */
public class LoginActivity extends FragmentActivity implements LoginLogic.Listener {
    @Bind(R.id.edit_phone)
    EditText editPhone;
    @Bind(R.id.edit_pwd)
    EditText editPwd;
    @Bind(R.id.img_pwd_can_see)
    ImageView imgPwd;

    LoginLogic currLogic;
    boolean canSeePwd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        currLogic = new LoginLogic(this,this);
        Log.e("aaa","");
    }

    @OnClick({R.id.img_pwd_can_see, R.id.txt_commit, R.id.txt_register, R.id.img_wx, R.id.img_qq, R.id.img_xl, R.id.txt_forget})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_pwd_can_see:
                canSeePwd = !canSeePwd;
                changeCanSeePwd(canSeePwd);
                break;
            case R.id.txt_commit:
                /*添加加载圈提示*/
                currLogic.comLogin(editPhone.getText().toString(),editPwd.getText().toString());
                break;
            case R.id.txt_register:
                break;
            case R.id.img_wx:
                /*添加加载圈提示*/
                currLogic.thirdLogin(0);
                break;
            case R.id.img_qq:
                /*添加加载圈提示*/
                currLogic.thirdLogin(1);
                break;
            case R.id.img_xl:
                /*添加加载圈提示*/
                currLogic.thirdLogin(2);
                break;
            case R.id.txt_forget:
                break;
        }
    }

    public void changeCanSeePwd(boolean canSee){
        if(canSee){
            imgPwd.setImageResource(R.mipmap.ic_eye_light);
            editPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            editPwd.requestFocus();
            editPwd.setSelection(editPwd.getText().length());
        }else{
            imgPwd.setImageResource(R.mipmap.ic_eye_dark);
            editPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            editPwd.requestFocus();
            editPwd.setSelection(editPwd.getText().length());
        }
    }

    @Override
    public void comLoginSuccess(Object object) {
        //处理登陆成功事宜
    }

    @Override
    public void comLoginFail(int code, String msg) {

    }

    @Override
    public void thirdLoginSuccess(Object object) {
        //处理登陆成功事宜
    }

    @Override
    public void thirdLoginFail(int code, String msg) {

    }
}
