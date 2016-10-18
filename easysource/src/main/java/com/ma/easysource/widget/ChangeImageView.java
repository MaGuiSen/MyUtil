package com.ma.easysource.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.nineoldandroids.animation.ObjectAnimator;

/**
 * 点击切换图片的View
 *
 * @author gsma
 * @date 2015-6-29
 */
public class ChangeImageView extends ImageView implements OnClickListener{
//	int[] imageId = new int[]{R.drawable.arrow_down};
	public static int Status_UnChoiced = 0;
	public static int Status_Choiced = 1;
	int status = Status_UnChoiced;
	public ChangeImageView(Context context) {
		super(context);
		init();
	}
	public  void setStatus(int status){
		this.status = status;
		init();
	}
	public int getStatus(){
		return this.status;
	}
	private void init() {
		this.setOnClickListener(this);
//		this.setImageResource(imageId[0]);
		if(status == Status_Choiced){
			//选中
			ObjectAnimator.ofFloat(this, "rotationX", 0.0F, 180.0F).setDuration(500).start(); 
//			this.setImageResource(imageId[1]);
		}else if(status == Status_UnChoiced){
			//未选中
			ObjectAnimator.ofFloat(this, "rotationX",  180.0F, 0.0F).setDuration(500).start(); 
//			this.setImageResource(imageId[0]);
		}
	}

	public ChangeImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public ChangeImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
//	public void setImage(int[] imageId ){
//		this.imageId = imageId;
//	}
	public void changeStatus(){
		if(status == Status_Choiced){
			status = Status_UnChoiced;
			//选中
			ObjectAnimator.ofFloat(this, "rotationX",  180.0F, 0.0F).setDuration(500).start();
//			this.setImageResource(imageId[0]);
		}else if(status == Status_UnChoiced){
			status = Status_Choiced;
			//未选中
			ObjectAnimator.ofFloat(this, "rotationX", 0.0F, 180.0F).setDuration(500).start();
//			this.setImageResource(imageId[1]);
		}
		changeViewOnClickListener.changeViewOnClick(status);
	}
	
	@Override
	public void onClick(View arg0) {
		changeStatus();	
	}
	private ChangeViewOnClickListener changeViewOnClickListener;
	public void setChangeViewOnClickListener(
			ChangeViewOnClickListener changeViewOnClickListener) {
		this.changeViewOnClickListener = changeViewOnClickListener;
	}
	public interface ChangeViewOnClickListener{
		/**
		 * 1  代表接下来要变为关闭状态，0代表接下来要变为打开状态
		 * @param status
		 */
		void changeViewOnClick(int status);
	}
}
