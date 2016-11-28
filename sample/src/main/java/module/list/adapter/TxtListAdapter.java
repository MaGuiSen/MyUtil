package module.list.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ma.myutil.R;

import butterknife.Bind;
import butterknife.ButterKnife;
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
        Holder holder = new Holder(convertView);
        return holder;
    }

    @Override
    public void fillView(final int position, final View convertView, Object mHolder) {
        Holder holder = (Holder) mHolder;
        final TextModel model = datas.get(position);
        holder.txtComment.setText("position"+position);
    }

    class Holder {
        @Bind(R.id.txt_tt)
        TextView txtComment;


        public Holder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
