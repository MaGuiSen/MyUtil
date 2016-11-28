package module.user.forget;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ma.myutil.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetActivity extends FragmentActivity implements ForgetLogic.Listener {
    boolean canSeePwd;
    @Bind(R.id.edit_phone)
    EditText editPhone;
    @Bind(R.id.edit_vcode)
    EditText editVCode;
    @Bind(R.id.edit_pwd)
    EditText editPwd;
    @Bind(R.id.img_pwd_can_see)
    ImageView imgPwdCanSee;
    @Bind(R.id.txt_vcode)
    TextView txtVCode;

    ForgetLogic currLogic;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        currLogic = new ForgetLogic(this, this);
    }

    @Override
    public void getVCodeSuccess(Object object) {

    }

    @Override
    public void getVCodeFail(int code, String msg) {

    }

    @Override
    public void registerSuccess(Object object) {

    }

    @Override
    public void registerFail(int code, String msg) {

    }

    /**
     * 根据状态切换按键的状态
     *
     * @param status              0:代表计时结束，1：代表计时中
     * @param millisUntilFinished 表示剩余的计数值  如果是停止了，则可以随意
     */
    @Override
    public void changeVCodeBtnStatus(int status, long millisUntilFinished) {
        if (status == 1) {
            txtVCode.setText(millisUntilFinished / 1000+"秒后重新获取");
            txtVCode.setBackgroundColor(0xffff8800);
            txtVCode.setClickable(false);
        } else if (status == 0) {
            txtVCode.setText("获取验证码");
            txtVCode.setBackgroundColor(0xffff8800);
            txtVCode.setClickable(true);
        }
    }

    @OnClick({R.id.txt_vcode, R.id.img_pwd_can_see, R.id.txt_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_vcode:
                currLogic.getVCode(editPhone.getText().toString());
                break;
            case R.id.img_pwd_can_see:
                canSeePwd = !canSeePwd;
                changeCanSeePwd(canSeePwd);
                break;
            case R.id.txt_commit:
                currLogic.register(editPhone.getText().toString(), editPwd.getText().toString(), editVCode.getText().toString());
                break;
        }
    }

    public void changeCanSeePwd(boolean canSee) {
        if (canSee) {
            imgPwdCanSee.setImageResource(R.mipmap.ic_eye_light);
            editPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            editPwd.requestFocus();
            editPwd.setSelection(editPwd.getText().length());
        } else {
            imgPwdCanSee.setImageResource(R.mipmap.ic_eye_dark);
            editPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            editPwd.requestFocus();
            editPwd.setSelection(editPwd.getText().length());
        }
    }
}
