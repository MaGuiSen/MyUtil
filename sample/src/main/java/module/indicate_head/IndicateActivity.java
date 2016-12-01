package module.indicate_head;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ma.myutil.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import lib.util.ScreenUtil;

public class IndicateActivity extends FragmentActivity {

    @Bind(R.id.mRadioGroup_content)
    LinearLayout mRadioGroup_content;
    @Bind(R.id.mColumnHorizontalScrollView)
    ColumnHorizontalScrollView mColumnHorizontalScrollView;
    @Bind(R.id.shade_left)
    ImageView shadeLeft;
    @Bind(R.id.shade_right)
    ImageView shadeRight;
    @Bind(R.id.rl_column)
    RelativeLayout rlColumn;
    @Bind(R.id.button_more_columns)
    ImageView buttonMoreColumns;
    @Bind(R.id.ll_more_columns)
    LinearLayout llMoreColumns;
    @Bind(R.id.category_line)
    View categoryLine;
    @Bind(R.id.mViewPager)
    ViewPager mViewPager;

    int columnSelectIndex = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indcate);
        ButterKnife.bind(this);
        newsClassify.add("AAAA");
        newsClassify.add("BBB");
        newsClassify.add("cc");
        newsClassify.add("dd");
        newsClassify.add("AAAA");
        newsClassify.add("BBB");
        newsClassify.add("cc");
        newsClassify.add("dd");
        newsClassify.add("AAAA");
        newsClassify.add("BBB");
        newsClassify.add("cc");
        newsClassify.add("dd");
        newsClassify.add("AAAA");
        newsClassify.add("BBB");
        newsClassify.add("cc");
        newsClassify.add("dd");
        //这步骤需要
        mColumnHorizontalScrollView.setParam(this,ScreenUtil.getScreenSize(this,null)[0],mRadioGroup_content,shadeLeft,shadeRight,llMoreColumns,rlColumn);
        init();
    }

    private ArrayList<String> newsClassify = new ArrayList<String>();
    List<TextView> labels = new ArrayList<>();
    public void init() {
        int count = newsClassify.size();
        for (int i = 0; i < count; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 10;
            params.rightMargin = 10;
            TextView localTextView = new TextView(this);
            localTextView.setLayoutParams(params);
            localTextView.setBackgroundColor(columnSelectIndex == i ? 0xff00ff :0xffff0000);
            localTextView.setGravity(Gravity.CENTER);
            localTextView.setPadding(10, 10, 10, 10);
            localTextView.setId(i);
            localTextView.setTextColor(0xff000000);
            localTextView.setText(newsClassify.get(i));
            final int position = i;
            localTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectTab(position);
                }
            });
            labels.add(localTextView);
            mRadioGroup_content.addView(localTextView);
        }
    }
    /**
     *  选择的Column里面的Tab
     * */
    private void selectTab(int position) {
        columnSelectIndex = position;
        for (int i = 0; i < mRadioGroup_content.getChildCount(); i++) {
            View checkView = mRadioGroup_content.getChildAt(position);
            int k = checkView.getMeasuredWidth();
            int l = checkView.getLeft();
            int i2 = l + k / 2 - ScreenUtil.getScreenSize(this,null)[0] / 2;
            mColumnHorizontalScrollView.smoothScrollTo(i2, 0);
        }
        for (int i = 0; i < labels.size(); i++) {
            if(i == position){
                labels.get(i).setBackgroundColor(0xffff00ff);
            }else{
                labels.get(i).setBackgroundColor(0xffff0000);
            }
        }
    }
}
