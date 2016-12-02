package module.search;

import android.content.Context;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import lib.util.CacheJsonUtil;

//历史搜索条目
public class HistorySearchModel {
	private List<String> labelNames;
	public List<String> getLabelNames() {
		if(labelNames == null)
			labelNames = new ArrayList<>();
		return labelNames;
	}
	public void setLabelNames(List<String> labelNames) {
		this.labelNames = labelNames;
	}
	public static void clearHistory(Context context){
		CacheJsonUtil.getInstance(context).saveJson("{}", HistorySearchModel.class.getSimpleName(),false);
	}
	public static void saveHistory(Context context,HistorySearchModel bean){
		String jsonStr = JSON.toJSONString(bean);
		CacheJsonUtil.getInstance(context).saveJson(jsonStr, HistorySearchModel.class.getSimpleName(),false);
	}
	public static HistorySearchModel getHistory(Context context){
		Object object = CacheJsonUtil.getInstance(context).getJsonObject(HistorySearchModel.class);
		HistorySearchModel bean = null;
		if (object != null) {
			bean = (HistorySearchModel) object;
			return bean;
		}
		return null;
	}
}
