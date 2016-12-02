package module.list;

import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import lib.util.CharacterParserUtil;
import lib.util.PinyinSortUtil;
import lib.util.StringUtil;

/**
 * 登陆逻辑
 */
public class SideBarLogic {
    Context context;
    Listener listener;
    int pageIndex = 1,pageSize = 15;

    public SideBarLogic(Context context, Listener listener) {
        this.context = context;
        this.listener = listener;
    }

    String[] names = {"A","B","C","D","122","E","F","G","A","B","A","B","C","D","","E","F","G","A","B"};
    public void loadDatas(){
            List<SideBarBaseModel> datas = new ArrayList<>();
            if (pageIndex < 3){
                for(int i=0;i<15;i++){
                    SideBarBaseModel model = new SideBarBaseModel();
                    model.setSortName(""+names[i]);
                    datas.add(model);
                }
            }else if(pageIndex == 3){
                for(int i=0;i<11;i++){
                    SideBarBaseModel model = new SideBarBaseModel();
                    model.setSortName(""+names[i]);
                    datas.add(model);
                }
            }
            if(listener!=null){
                listener.loadDataSuccess(datas);
            }
    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     * 这里要设置为大小忽略
     * @param filterStr
     */
    public void filterData(String filterStr,List<SideBarBaseModel> datas) {
        List<SideBarBaseModel> newDatas = new ArrayList<SideBarBaseModel>();
        if (TextUtils.isEmpty(filterStr)) {
            newDatas = datas;
        } else {
            newDatas.clear();
            for (SideBarBaseModel sortModel: datas) {
                String name = sortModel.getSortName();
                if(StringUtil.isMatching(name, filterStr)){
                    newDatas.add(sortModel);
                }
            }
        }
        // 根据a-z进行排序 这步骤可能不需要，因为过滤，正常已经排好序号了
        PinyinSortUtil.Sort(newDatas, "getSortLetters");
        if(listener != null){
            listener.sortDataSuccess(newDatas);
        }
    }

    /*转换数据，并排序*/
    public void parseDataAndSort(List<SideBarBaseModel> datas) {
        List<SideBarBaseModel> newDatas = new ArrayList<SideBarBaseModel>();
        for(int j=0;j<datas.size();j++){
            SideBarBaseModel sortModel = datas.get(j);
            String sortName = sortModel.getSortName();
            if(sortName != null){
                String pinyin= CharacterParserUtil.getSelling(sortName);
                String sortString = "".equals(pinyin) ? "" : pinyin.substring(0, 1).toUpperCase();

                if (sortString.matches("[A-Z]")) {
                    sortModel.setSortLetters(sortString.toUpperCase());
                } else{
                    sortModel.setSortLetters("#");
                }
            }else{
                sortModel.setSortLetters("#");
            }
            newDatas.add(sortModel);
        }
        // 根据a-z进行排序源数据
        PinyinSortUtil.Sort(newDatas, "getSortLetters");
        if(listener != null){
            listener.sortDataSuccess(newDatas);
        }
    }

    interface Listener<T>{
        void loadDataSuccess(List<T> t);
        void loadDataFail(int code, String msg);

        void sortDataSuccess(List<T> datas);
    }
}
