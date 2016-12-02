package module.list.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ma.myutil.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import module.BaseMyAdapter;
import module.list.SideBarBaseModel;

public class SideBarListAdapter extends BaseMyAdapter<SideBarBaseModel> {
    private int lastPosition = -1;

    public SideBarListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.layout_contact_item;
    }

    @Override
    public Object initView(int position, View convertView) {
        ViewHolder holder = new ViewHolder(convertView);
        return holder;
    }

    @Override
    public void fillView(final int position, final View convertView, Object mHolder) {
        ViewHolder holder = (ViewHolder) mHolder;
        final SideBarBaseModel model = datas.get(position);
        // 根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);

        // 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            holder.llayMain.setVisibility(View.VISIBLE);
            holder.txtOrder.setText(model.getSortLetters());
            holder.txtOrder.setVisibility(View.VISIBLE);
        } else {
            holder.txtOrder.setVisibility(View.GONE);
            holder.llayMain.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return datas.get(position).getSortLetters().charAt(0);
    }

    public String getSortLetters(int position) {
        return datas.get(position).getSortLetters();
    }


    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        int pos = 0;
        for (int i = 0; i < getCount(); i++) {
            String sortStr = datas.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                pos = i;
                break;
            }
        }
        return pos;
    }


    /**
     * 获取第一次出现的位置
     * @param letter
     * @return
     */
    public int getPositionForSectionString(String letter) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = datas.get(i).getSortLetters();
            if (letter.equals(sortStr)) {
                lastPosition = i;
                return i;
            }
        }
        return lastPosition;
    }

    class ViewHolder {
        @Bind(R.id.txt_order)
        TextView txtOrder;
        @Bind(R.id.img_show)
        ImageView imgShow;
        @Bind(R.id.img_level)
        ImageView imgLevel;
        @Bind(R.id.txt_title)
        TextView txtTitle;
        @Bind(R.id.txt_detail)
        TextView txtDetail;
        @Bind(R.id.llay_main)
        LinearLayout llayMain;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
