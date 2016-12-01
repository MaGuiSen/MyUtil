package com.ma.myutil;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import lib.widget.NestRadioGroup;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;
    @Bind(R.id.tab_home)
    RadioButton tabHome;
    @Bind(R.id.main_radio)
    NestRadioGroup mainRadio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initBottomTab();
        initFragment();
        initRadioBtn();
    }

    /**
     * 另外的
     */
    private void initRadioBtn(){
        tabHome.setChecked(true);
        mainRadio.setOnCheckedChangeListener(new NestRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(NestRadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.tab_home:
                        switchContent(currFrm, getHomeFrm());
                        break;
                    case R.id.tab_mine:
                        switchContent(currFrm, getMineFrm());
                        break;
                    default:
                        break;
                }
            }
        });
    }
    /*
    三方的
    * */
    private void initBottomTab() {
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.ic_tab_juqing_white_24dp, "首頁"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_tab_mine_white_24dp, "我的"))
                .initialise();
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switchFragment(position);
            }

            @Override
            public void onTabUnselected(int position) {
            }

            @Override
            public void onTabReselected(int position) {
            }
        });
    }

    private void switchFragment(int position) {
        switch (position) {
            case 0:
                switchContent(currFrm, getHomeFrm());
                break;
            case 1:
                switchContent(currFrm, getMineFrm());
                break;
        }
    }

    /**
     * 切换fragment
     */
    public void switchContent(Fragment from, Fragment to) {
        if (currFrm != to) {
            currFrm = to;
            FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();//.setCustomAnimations(android.R.anim.fade_in, R.anim.fade_out);
            if (!to.isAdded()) {    // 先判断是否被add过
                transaction.hide(from).add(R.id.frm_content, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
    }

    private void initFragment() {
        currFrm = new HomeFragment();
        this.getSupportFragmentManager().beginTransaction().add(R.id.frm_content, currFrm).commit();
    }

    Fragment currFrm, homeFram, mineFrm;

    public Fragment getHomeFrm() {
        if (homeFram == null) homeFram = new HomeFragment();
        return homeFram;
    }

    public Fragment getMineFrm() {
        if (mineFrm == null) mineFrm = new MineFragment();
        return mineFrm;
    }
}
