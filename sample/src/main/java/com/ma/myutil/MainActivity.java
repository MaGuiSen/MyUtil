package com.ma.myutil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import module.list.ListActivity;
import module.nine_grid.NieGridActivity;
import module.user.forget.ForgetActivity;
import module.user.login.LoginActivity;
import module.user.register.RegisterActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.txt_nine_grid,R.id.txt_login,
            R.id.txt_regist,
            R.id.txt_forget,
            R.id.txt_list})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_nine_grid:
                startActivity(new Intent(this, NieGridActivity.class));
                break;
            case R.id.txt_login:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.txt_regist:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.txt_forget:
                startActivity(new Intent(this, ForgetActivity.class));
                break;
            case R.id.txt_list:
                startActivity(new Intent(this, ListActivity.class));
                break;
        }
    }
}
