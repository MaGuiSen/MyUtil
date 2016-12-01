package module.func_widget;

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
import module.user.forget.ForgetLogic;

public class FuncActivity extends FragmentActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_widget);
        ButterKnife.bind(this);
    }
}
