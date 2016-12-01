package module.nine_grid;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ma.myutil.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import lib.util.ScreenUtil;

public class NieGridActivity extends FragmentActivity {

    @Bind(R.id.llay_grid)
    LinearLayout llayGrid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nine_grid);
        ButterKnife.bind(this);
        llayGrid.addView(getFuncLay(this,4,ic));
    }

    //组建这个功能列表
    Object[][] ic = new Object[][]{
            {R.mipmap.ic_check_down, "name", "iconUrl"},
            {-99, "name", ""},
            {-99, "name", ""},
            {R.mipmap.ic_check_down, "name", "iconUrl"},
            {R.mipmap.ic_check_down, "name", "iconUrl"},
    };

    public LinearLayout getFuncLay(Context context, int columns, Object[][] groups) {
        int screenW = ScreenUtil.getScreenSize(context,null)[0];
        int itemWidth = screenW / columns;


        LinearLayout outLay = new LinearLayout(context);
        outLay.setOrientation(LinearLayout.VERTICAL);
        outLay.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LinearLayout rowLay = null;
        for (int rowI = 0; rowI < groups.length; rowI++) {
            if (rowI % columns ==0){
                rowLay = new LinearLayout(context);
                rowLay.setOrientation(LinearLayout.HORIZONTAL);
                rowLay.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                outLay.addView(rowLay);
            }
            Object[] itemArray = groups[rowI];
            View itemView = View.inflate(context, R.layout.layout_txt_bottom_img_up_item, null);
            itemView.setLayoutParams(new ViewGroup.LayoutParams(itemWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
            ImageView imgShow = (ImageView) itemView.findViewById(R.id.img_show);
            TextView txtTitle = (TextView) itemView.findViewById(R.id.txt_title);
            TextView txtValue = (TextView) itemView.findViewById(R.id.txt_value);
            txtValue.setVisibility(View.GONE);

            if ((int)itemArray[0] == -99) {
                //说明用网络加载库进行加载
            } else {
                imgShow.setImageResource((Integer) itemArray[0]);
            }
            txtTitle.setText((String)itemArray[1]);
            rowLay.addView(itemView);
        }
        return outLay;
    }
}
