package com.ma.myutil;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import de.greenrobot.event.EventBus;
import lib.util.ToastUtil;
import lib.widget.LoadingDialog;

public class BaseFragment extends Fragment {

	protected Boolean isLoading;
	protected LoadingDialog mLoadingDialog;

	public Boolean getIsLoading() {
		return isLoading;
	}

	private LayoutInflater mInflater;
	private boolean isNeedInit = false;
	private View root;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		initBase();
		super.onCreate(savedInstanceState);
	}
	
	private void initBase(){
		ToastUtil.setContext(getActivity());
	}

	private boolean isRegisterEventBus = false;//判断是否进行EventBus的注册，用于取消注册时的判断
	/**
	 * 注册EventBus ，如果调用此方法，必须要声明接收函数，否则报错
	 * onEvent(AnyTypeEvent event)/onEventMainThread(AnyTypeEvent event)..
	 * 具体参见EventBus使用介绍http://blog.csdn.net/harvic880925/article/details/40787203
	 */
	protected void registerEventBus(){
		EventBus.getDefault().register(this);
		isRegisterEventBus = true;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		hideSoftInput();
		if(isRegisterEventBus)
			EventBus.getDefault().unregister(this);
	}

	/**
	 * 处理加载
	 * @param message
	 */
	public void showLoadingDialog(String message){
		this.showLoadingDialog(message, true, true);
	}

	public void showLoadingDialog(String message,boolean cancelable,boolean otoCancelable){
		mLoadingDialog = new LoadingDialog(this.getActivity(),message,cancelable,otoCancelable);
		mLoadingDialog.show();
	}

	/**
	 * 关闭进度条
	 */
	public void dismissLoadingDialog(){
		if(mLoadingDialog!=null){
			mLoadingDialog.dismiss();
		}
		isLoading = false;
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	/**
	 * 隐藏软键盘
	 */
	public void hideSoftInput() {
		/** 隐藏软键盘 **/
		View view = getActivity().getWindow().peekDecorView();
		if (view != null) {
			InputMethodManager inputmanger = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
			inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}
	
}
