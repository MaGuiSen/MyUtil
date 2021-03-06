package module.list.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.ma.myutil.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import module.BaseMyAdapter;
import module.list.TextModel;

public class TxtListAdapter extends BaseMyAdapter<TextModel> {
    public TxtListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.layout_txt_item;
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
        holder.txtComment.setText("position"+position);
    }

    class ViewHolder {
        @Bind(R.id.txt_tt)
        TextView txtComment;


        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
