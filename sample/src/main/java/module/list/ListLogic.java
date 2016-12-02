package module.list;

import android.content.Context;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * 登陆逻辑
 */
public class ListLogic {
    Context context;
    Listener listener;
    int pageIndex = 1,pageSize = 15;

    public ListLogic(Context context, Listener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void loadDatas(){
            List<TextModel> datas = new ArrayList<>();
            if (pageIndex < 3){
                for(int i=0;i<15;i++){
                    TextModel textModel = new TextModel();
                    datas.add(textModel);
                }
            }else if(pageIndex == 3){
                for(int i=0;i<11;i++){
                    TextModel textModel = new TextModel();
                    datas.add(textModel);
                }
            }
            if(listener!=null){
                listener.loadDataSuccess(datas);
            }
    }

    interface Listener<T>{
        void loadDataSuccess(List<T> t);
        void loadDataFail(int code,String msg);
    }
}
