package com.ma.myutil;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import module.func_widget.FuncActivity;
import module.indicate_head.IndicateActivity;
import module.list.ListActivity;
import module.nine_grid.NieGridActivity;
import module.qrcode.QRcodeActivity;
import module.user.forget.ForgetActivity;
import module.user.login.LoginActivity;
import module.user.register.RegisterActivity;

/**
 * Created by mags on 2016/8/16.
 */
public class MineFragment extends Fragment{
    View currView = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        currView = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, currView);
        return currView;
    }

    @OnClick({R.id.txt_login,
            R.id.txt_regist,
            R.id.txt_forget,})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_login:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.txt_regist:
                startActivity(new Intent(getActivity(), RegisterActivity.class));
                break;
            case R.id.txt_forget:
                startActivity(new Intent(getActivity(), ForgetActivity.class));
                break;
        }
    }
}
