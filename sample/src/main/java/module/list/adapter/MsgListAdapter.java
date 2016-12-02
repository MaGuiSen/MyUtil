package module.list.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ma.myutil.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import module.BaseMyAdapter;
import module.list.TextModel;

public class MsgListAdapter extends BaseMyAdapter<TextModel> {
    public MsgListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.layout_msg_item;
    }

    @Override
    public Object initView(int position, View convertView) {
        ViewHolder holder = new ViewHolder(convertView);
        return holder;
    }

    @Override
    public void fillView(final int position, final View convertView, Object mHolder) {
        ViewHolder holder = (ViewHolder) mHolder;
        final TextModel model = datas.get(position);
        holder.txtTitle.setText("position" + position);
    }

    static class ViewHolder {
        @Bind(R.id.img_show)
        ImageView imgShow;
        @Bind(R.id.txt_msg_num)
        TextView txtMsgNum;
        @Bind(R.id.txt_msg_point)
        TextView txtMsgPoint;
        @Bind(R.id.txt_title)
        TextView txtTitle;
        @Bind(R.id.txt_time)
        TextView txtTime;
        @Bind(R.id.txt_detail)
        TextView txtDetail;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
