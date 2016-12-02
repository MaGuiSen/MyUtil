package module.list;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;

import com.ma.myutil.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import lib.widget.FrameProgressLayout;
import lib.xlistview.XListView;
import module.list.adapter.SideBarListAdapter;

public class SidebarListActivity extends FragmentActivity implements XListView.IXListViewListener, SideBarLogic.Listener<SideBarBaseModel> {
    @Bind(R.id.lv_data)
    XListView lvData;
    @Bind(R.id.frame_progress)
    FrameProgressLayout frameProgress;
    @Bind(R.id.txt_dialog)
    TextView txtDialog;

    @Bind(R.id.txt_order)
    TextView txtOrder;
    @Bind(R.id.side_bar)
    SideBar sideBar;

    SideBarListAdapter adapter;
    SideBarLogic currLogic;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sidebar_list);
        ButterKnife.bind(this);
        currLogic = new SideBarLogic(this, this);
        initLv();
        initSideBar();
        frameProgress.show();
        frameProgress.setOnClickRefreshListener(new FrameProgressLayout.OnClickRefreshListener() {
            @Override
            public void onClickRefresh() {
                currLogic.pageIndex = 1;
                currLogic.loadDatas();
            }
        });
        currLogic.loadDatas();
    }

    /*用于匹配输入狂框*/
    public void filterByWord(String keyword){
        currLogic.filterData(keyword,adapter.getData());
    }

    private void initSideBar() {
        sideBar.setTextView(txtDialog);
        sideBar.setStrArray(1);
        // 设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                if (s.equals("*")) {
                    lvData.setSelection(0);
                } else {
                    // 该字母首次出现的位置
                    int position = adapter.getPositionForSectionString(s);
                    lvData.setSelection(position + 1);
                }
            }
        });
    }

    private void initLv() {
        adapter = new SideBarListAdapter(this);
        lvData.setAdapter(adapter);
        lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < 1) {
                    return;
                }
//                TextModel model = (TextModel) parent.getItemAtPosition(position);
            }
        });
        lvData.setMyScrollListener(new XListView.MyScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
                //第一个可见的，检测他的
                String sortLetters = adapter.getSortLetters(firstVisibleItem);
                if(TextUtils.isEmpty(sortLetters)){
                    txtOrder.setVisibility(View.GONE);
                }else{
                    txtOrder.setVisibility(View.VISIBLE);
                    txtOrder.setText(sortLetters);
                }
            }
        });
        lvData.setXListViewListener(this);
        lvData.setPullLoadEnable(false);
        lvData.setPullRefreshEnable(true);
    }

    public void loadDataSuccess(List<SideBarBaseModel> datas) {
        lvData.stopLoadMore();
        lvData.stopRefresh();
        if (datas == null || datas.size() == 0) {
            lvData.setPullLoadEnable(false);
            if (adapter.getCount() == 0) {
                frameProgress.noData("暂无数据");
            } else {
                frameProgress.dismiss();
            }
            return;
        }
        if (datas.size() < currLogic.pageSize) {
            lvData.setPullLoadEnable(false);
        } else {
            lvData.setPullLoadEnable(true);
        }
        /*这里不太一样*/
        List<SideBarBaseModel> allDatas = new ArrayList<>();
        if (currLogic.pageIndex == 1) {
            allDatas.addAll(datas);
        } else {
            allDatas.addAll(adapter.getData());
            allDatas.addAll(datas);
        }
        //进入转换  成功之后调用sortDataSuccess
        currLogic.parseDataAndSort(allDatas);
        //这边不太一样
        frameProgress.dismiss();
        currLogic.pageIndex++;
    }

    public void loadDataFail(int code, String msg) {
        frameProgress.dismiss();
        if (adapter != null && adapter.getCount() == 0) {
            frameProgress.loadFail();
        }
    }

    @Override
    public void sortDataSuccess(List<SideBarBaseModel> datas) {
        //更新完成，就刷新界面
        if(adapter != null){
            adapter.setData(datas);
        }
    }

    @Override
    public void onRefresh() {
        currLogic.pageIndex = 1;
        currLogic.loadDatas();
    }

    @Override
    public void onLoadMore() {
        currLogic.loadDatas();
    }
}
