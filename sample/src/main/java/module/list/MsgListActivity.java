package module.list;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;

import com.ma.myutil.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import lib.widget.FrameProgressLayout;
import lib.xlistview.XListView;
import module.list.adapter.MsgListAdapter;
import module.list.adapter.TxtListAdapter;

public class MsgListActivity extends FragmentActivity implements XListView.IXListViewListener,ListLogic.Listener<TextModel> {
    @Bind(R.id.lv_data)
    XListView lvData;
    @Bind(R.id.frame_progress)
    FrameProgressLayout frameProgress;

    MsgListAdapter adapter;
    ListLogic currLogic;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        currLogic = new ListLogic(this,this);
        initLv();
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

    private void initLv() {
        adapter = new MsgListAdapter(this);
        lvData.setAdapter(adapter);
        lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < 1) {
                    return;
                }
                TextModel model = (TextModel) parent.getItemAtPosition(position);
            }
        });

        lvData.setXListViewListener(this);
        lvData.setPullLoadEnable(false);
        lvData.setPullRefreshEnable(true);
    }

    public void loadDataSuccess(List<TextModel> datas) {
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
        frameProgress.dismiss();
        if (datas.size() < currLogic.pageSize) {
            lvData.setPullLoadEnable(false);
        } else {
            lvData.setPullLoadEnable(true);
        }
        if (currLogic.pageIndex == 1) {
            adapter.setData(datas);
        } else {
            adapter.addData(datas);
        }
        currLogic.pageIndex++;
    }

    public void loadDataFail(int code, String msg) {
        frameProgress.dismiss();
        if (adapter != null && adapter.getCount() == 0) {
            frameProgress.loadFail();
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
