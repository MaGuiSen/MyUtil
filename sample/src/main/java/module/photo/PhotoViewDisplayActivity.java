package module.photo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ma.myutil.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lib.util.ImageUtil;
import uk.co.senab.photoview.PhotoView;

public class PhotoViewDisplayActivity extends FragmentActivity {

    public static final String IMAGE_PATH = "IMAGE_PATH";
    public static final String SELECT_POSITION = "select_position";
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.cb_pic_org)
    CheckBox cbPicOrg;
    @Bind(R.id.tv_size)
    TextView tvSize;
    @Bind(R.id.cb_choice)
    CheckBox cbChoice;
    @Bind(R.id.rlay_bottom_bar)
    RelativeLayout rlayBottomBar;
    @Bind(R.id.txt_bar_title)
    TextView txtBarTitle;
    @Bind(R.id.txt_bar_right_btn)
    TextView txtBarRightBtn;
    @Bind(R.id.rlay_top_bar)
    RelativeLayout rlayTopBar;

    private int position = 0;
    private boolean isOrigin = false;//是原图
    private int maxNum = 9;
    private int currChoiceNum = 0;

    private List<String> imagePaths = new ArrayList<>();
    private List<Map<String, Object>> imageStatus = new ArrayList<Map<String, Object>>();
    private String key_path = "key_path";
    private String key_choice = "key_choice";
    private String key_size = "key_size";
    private String key_view = "key_view";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photoview);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            position = bundle.getInt(SELECT_POSITION, 0);
            imagePaths = (List<String>) bundle.getSerializable(IMAGE_PATH);
        }
        if (imagePaths == null || imagePaths.size() == 0) {
            return;
        }
        initPage();
        initUI();
    }

    long totalFileSize = 0;
    private void initPage() {
        for (int i = 0; i < imagePaths.size(); i++) {
            Map<String, Object> data = new HashMap<>();
            data.put(key_path, imagePaths.get(i));
            data.put(key_choice, true);
            //size
            long currSize = imagePaths.get(i).startsWith("http") ? 0 : getFileSize(imagePaths.get(i));
            totalFileSize = totalFileSize + currSize;
            data.put(key_size, currSize);

            PhotoView pv = new PhotoView(this);
            pv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            pv.setScaleType(ImageView.ScaleType.FIT_CENTER);
            ImageUtil.loadImage(this, imagePaths.get(i), pv, R.mipmap.ic_default_3);
            data.put(key_view, pv);
            viewpager.addView(pv);
            imageStatus.add(data);
        }
        viewpager.setOffscreenPageLimit(imagePaths.size());
        currChoiceNum = imagePaths.size();
        tvSize.setText("原图("+FormetFileSize(totalFileSize)+")");
        refreshSendBtn();
        txtBarTitle.setText(position+"/"+currChoiceNum);
    }

    private void refreshSendBtn() {
        if(currChoiceNum > 0){
            txtBarRightBtn.setClickable(true);
            txtBarRightBtn.setText("发送"+ currChoiceNum +"/"+maxNum);
        }else{
            txtBarRightBtn.setClickable(false);
            txtBarRightBtn.setText("发送");
        }
    }

    private void initUI() {
        viewpager.setCurrentItem(position);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position_) {
                position = position_;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        viewpager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rlayBottomBar.getVisibility() == View.GONE) {
                    rlayBottomBar.setVisibility(View.VISIBLE);
                } else {
                    rlayBottomBar.setVisibility(View.GONE);
                }
                if (rlayTopBar.getVisibility() == View.GONE) {
                    rlayTopBar.setVisibility(View.VISIBLE);
                } else {
                    rlayTopBar.setVisibility(View.GONE);
                }
            }
        });

        //checkBox
        cbPicOrg.setVisibility(View.VISIBLE);
        cbPicOrg.setChecked(isOrigin);
        cbPicOrg.setBackgroundResource(isOrigin?R.mipmap.ic_check_down :R.mipmap.ic_check_out);
        tvSize.setText("原图("+ totalFileSize +")");
        cbPicOrg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isOrigin = isChecked;
                cbPicOrg.setBackgroundResource(isChecked?R.mipmap.ic_check_down :R.mipmap.ic_check_out);
            }
        });

        cbChoice.setVisibility(View.VISIBLE);
        cbChoice.setChecked(true);
        cbChoice.setBackgroundResource(R.mipmap.ic_check_down);
        cbPicOrg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Map<String,Object> image = imageStatus.get(position);
                image.put(key_choice,isChecked);
                cbChoice.setBackgroundResource(isChecked ? R.mipmap.ic_check_down :R.mipmap.ic_check_out);
                //触发尺寸的变化
                long currSize = (long) image.get(key_size);
                totalFileSize = isChecked ? totalFileSize +currSize: totalFileSize -currSize;
                tvSize.setText("原图("+FormetFileSize(totalFileSize)+")");
                currChoiceNum = isChecked ? currChoiceNum + 1: currChoiceNum - 1;
                refreshSendBtn();
            }
        });
    }

    private static long getFileSize(String filePath) {
        long size = 0;
        FileInputStream fis = null;
        try {
            File file = new File(filePath);
            if (file.exists()) {
                fis = new FileInputStream(file);
                size = fis.available();
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return size;
    }


    private static String FormetFileSize(long size) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (size == 0) {
            return wrongSize;
        }
        if (size < 1024) {
            fileSizeString = df.format((double) size) + "B";
        } else if (size < 1048576) {
            fileSizeString = df.format((double) size / 1024) + "K";
        } else if (size < 1073741824) {
            fileSizeString = df.format((double) size / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) size / 1073741824) + "G";
        }
        return fileSizeString;
    }

    @OnClick(R.id.txt_bar_right_btn)
    public void onClick() {
    }
}
