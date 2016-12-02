package module.search;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.ma.myutil.BaseActivity;
import com.ma.myutil.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lib.util.ToastUtil;
import lib.widget.FlowLayout;
import lib.widget.MyListView;

public class SearchActivity extends BaseActivity {
    public static final int CANCEL = 501;
    public static final int SEARCH = 502;

    @Bind(R.id.edit_search)
    EditText editSearch;
    @Bind(R.id.img_edit_delete)
    ImageView imgEditDelete;
    @Bind(R.id.txt_search_or_cancel)
    TextView txtSearchOrCancel;
    @Bind(R.id.lv_word_suggest)
    ListView lvWordSuggest;
    @Bind(R.id.llay_word_suggest)
    LinearLayout llayWordSuggest;
    @Bind(R.id.flay_hot_search)
    FlowLayout flayHotSearch;
    @Bind(R.id.llay_hot_search)
    LinearLayout llayHotSearch;
    @Bind(R.id.lv_history_search)
    MyListView lvHistorySearch;
    @Bind(R.id.llay_clear_history)
    LinearLayout llayClearHistory;
    @Bind(R.id.llay_history_search)
    LinearLayout llayHistorySearch;

    HistorySearchAdapter historySearchAdapter;
    public int operation = CANCEL;
    public List<String> historyLabels = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initHistory();
        initSearchEdit();
    }

    private void initSearchEdit() {
        editSearch.addTextChangedListener(mTextWatcher);
    }

    public void initHistory() {
        historySearchAdapter = new HistorySearchAdapter(SearchActivity.this);
        lvHistorySearch.setAdapter(historySearchAdapter);
        HistorySearchModel bean = HistorySearchModel.getHistory(getActivity());
        if (bean != null) {
            historyLabels = bean.getLabelNames();
            refreshHistoryLayout();
        }
    }

    private void refreshHistoryLayout() {
        if (historyLabels!=null && historyLabels.size() != 0) {
            llayHistorySearch.setVisibility(View.VISIBLE);
            historySearchAdapter.notifyData(historyLabels);
            llayClearHistory.setVisibility(View.VISIBLE);
        } else {
            llayHistorySearch.setVisibility(View.GONE);
            llayClearHistory.setVisibility(View.GONE);
        }
    }

    // 文本框输入变化事件
    TextWatcher mTextWatcher = new TextWatcher() {
        private String content;

        @Override
        public void afterTextChanged(Editable arg0) {
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2,int arg3) {
            if (arg0 == null) {
                return;
            }
            content = editSearch.getText().toString();
            if (!content.trim().equals("")) {
                operation = SEARCH;
                imgEditDelete.setVisibility(View.VISIBLE);
                txtSearchOrCancel.setText("搜索");
            } else {
                operation = CANCEL;
                imgEditDelete.setVisibility(View.GONE);
                txtSearchOrCancel.setText("取消");
            }
        }
    };

    private void startSearch(String searchWord) {
        saveHistory(searchWord);
        ToastUtil.show("缓存搜索结果，并跳转搜索");
//        Intent intent = new Intent(getActivity(),MovieListActivity.class);
//        intent.putExtra(MovieListActivity.KeyWord, searchWord);
//        startActivity(intent);
    }

    // 保存历史搜索
    public void saveHistory(String searchWord) {
        HistorySearchModel bean = HistorySearchModel.getHistory(getActivity());
        if (bean == null) {
            bean = new HistorySearchModel();
            bean.setLabelNames(new ArrayList<String>());
        }
        List<String> labels = bean.getLabelNames();
        for (String label : labels) {
            if (searchWord.equals(label)) {
                // 存在就不添加
                return;
            }
        }
        labels.add(searchWord);
        bean.setLabelNames(labels);
        HistorySearchModel.saveHistory(getActivity(), bean);
    }

    public void startSearchOrCancel() {
        if (operation == CANCEL) {
            hideSoftInput();
            finish();
        } else if (operation == SEARCH) {
            hideSoftInput();
            startSearch(editSearch.getText().toString().trim());
        }
    }

    @OnClick({R.id.img_edit_delete, R.id.txt_search_or_cancel, R.id.llay_clear_history})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_edit_delete:
                editSearch.setText("");
                break;
            case R.id.txt_search_or_cancel:
                startSearchOrCancel();
                break;
            case R.id.llay_clear_history:
                clearHistory();
                break;
        }
    }

    // 删除历史搜所
    public void clearHistory() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("清除历史搜索？")
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                HistorySearchModel.clearHistory(getActivity());
                                historyLabels.clear();
                                historySearchAdapter.notifyData(historyLabels);
                                refreshHistoryLayout();
                                dialog.cancel();// 取消弹出框
                            }
                        })
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.cancel();// 取消弹出框
                            }
                        }).create().show();
    }
}
