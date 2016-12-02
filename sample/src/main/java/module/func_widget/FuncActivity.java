package module.func_widget;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.SeekBar;

import com.ma.myutil.BaseActivity;
import com.ma.myutil.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import lib.util.ToastUtil;

public class FuncActivity extends BaseActivity {
    //星评论
    @Bind(R.id.star_rating_small)
    RatingBar starRatingSmall;
    @Bind(R.id.star_rating_big)
    RatingBar starRatingBig;

    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.seekBar)
    SeekBar seekBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_widget);
        ButterKnife.bind(this);
        ratingUse();
        initSeekBar();
    }

    public void ratingUse() {
        //设置为指示器，不可滑动
        starRatingSmall.setIsIndicator(true);
        //设置为非指示器，可华东
        starRatingBig.setIsIndicator(true);
        starRatingBig.setIsIndicator(false);
        starRatingBig.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar arg0, float arg1, boolean arg2) {
                ToastUtil.show(arg1 + "");
            }
        });
    }

    public void initSeekBar(){
        progressBar.setMax(100);
        progressBar.setProgress(50);

        seekBar.setMax(100);
        seekBar.setProgress(50);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ToastUtil.show(progress + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
