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
import module.list.MsgListActivity;
import module.list.SidebarListActivity;
import module.nine_grid.NieGridActivity;
import module.qrcode.QRcodeActivity;
import module.search.SearchActivity;
import module.switch_widget.SwitchActivity;
import module.user.forget.ForgetActivity;
import module.user.login.LoginActivity;
import module.user.register.RegisterActivity;

/**
 * Created by mags on 2016/8/16.
 */
public class HomeFragment extends Fragment{
    View currView = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        currView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, currView);
        return currView;
    }

    @OnClick({R.id.txt_nine_grid,
            R.id.txt_list,
            R.id.txt_msg_list,
            R.id.txt_widget,
            R.id.txt_indicate,
            R.id.txt_qrcode,
            R.id.txt_search,
            R.id.txt_switch,
            R.id.txt_side_bar,
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_nine_grid:
                startActivity(new Intent(getActivity(), NieGridActivity.class));
                break;
            case R.id.txt_list:
                startActivity(new Intent(getActivity(), ListActivity.class));
                break;
            case R.id.txt_widget:
                startActivity(new Intent(getActivity(), FuncActivity.class));
                break;
            case R.id.txt_indicate:
                startActivity(new Intent(getActivity(), IndicateActivity.class));
                break;
            case R.id.txt_qrcode:
                startActivity(new Intent(getActivity(), QRcodeActivity.class));
                break;
            case R.id.txt_msg_list:
                startActivity(new Intent(getActivity(), MsgListActivity.class));
                break;
            case R.id.txt_search:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
            case R.id.txt_side_bar:
                startActivity(new Intent(getActivity(), SidebarListActivity.class));
                break;
            case R.id.txt_switch:
                startActivity(new Intent(getActivity(), SwitchActivity.class));
                break;

        }
    }
}
