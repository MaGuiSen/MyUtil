package com.ma.easysource.widget;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 跑马灯View
 * @author M
 *
	android:ellipsize="marquee"
	android:focusable="true"
	android:gravity="center_vertical"
	android:marqueeRepeatLimit="marquee_forever"
	android:singleLine="true"
 */
public class AlwaysMarqueeTextView extends TextView{
	public AlwaysMarqueeTextView(Context context) {  
	      super(context);  
	}  
	
	public AlwaysMarqueeTextView(Context context, AttributeSet attrs) {  
	      super(context, attrs);  
	}  
	  
	public AlwaysMarqueeTextView(Context context, AttributeSet attrs, int defStyle) {  
	      super(context, attrs, defStyle);  
	}  
//保持焦点
	@Override  
	public boolean isFocused() {  
	      return true;  
	}  

} 

