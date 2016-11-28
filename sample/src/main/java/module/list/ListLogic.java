package module.list;

import android.content.Context;

import org.w3c.dom.Text;

import java.util.List;

/**
 * 登陆逻辑
 */
public class ListLogic {
    Context context;
    Listener listener;

    public ListLogic(Context context, Listener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void loadDatas(){
        if(listener!=null){
            listener.loadDataSuccess(null);
        }
    }

    interface Listener<T>{
        void loadDataSuccess(List<T> t);
        void loadDataFail(int code,String msg);
    }
}
