package module.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ma.myutil.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 历史搜索适配器
 */
public class HistorySearchAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	List<String> labels = new ArrayList<String>();
	public HistorySearchAdapter(Context mContext){
		mInflater = LayoutInflater.from(mContext);
	}
	public void notifyData(List<String> labels){
		this.labels = labels;
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		if(labels!=null && labels.size() != 0)
			return labels.size();
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if(labels!=null && labels.size() != 0)
			return labels.get(position);
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder mHolder;
		if(convertView==null){
			convertView = mInflater.inflate(R.layout.layout_history_search_list_item, null);
			mHolder = new Holder(convertView);
			convertView.setTag(mHolder);
		}else{
			mHolder = (Holder) convertView.getTag();
		}
		mHolder.mLabel.setText(labels.get(position));
		return convertView;
	}
	
	public class Holder {
		TextView mLabel;
		
		public Holder(View view) {
			mLabel = (TextView)view.findViewById(R.id.tvLabel);
		}
	}

}
