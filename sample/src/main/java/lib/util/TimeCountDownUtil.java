package lib.util;

import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 倒计时N秒
 */
public class TimeCountDownUtil extends android.os.Handler{
    private boolean timerIsRunning = false;//计时器是否正在计时
    private int totalTime = 60;//需要倒计时的时间
    private int initTime = 60;//初始设置时间
    private final int END = 1;
    private final int RUNNING = 2;
    private Timer timer = new Timer();
    private TimeCountListener timeCountListener;

    public TimeCountDownUtil(int countDownTime, TimeCountListener timeCountListener){
        this.timeCountListener = timeCountListener;
        this.initTime = countDownTime;
        this.totalTime  = countDownTime;
    }
    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case END:
                if(timeCountListener != null){
                    timeCountListener.onTimeCountFinish();
                }
                break;
            case RUNNING:
                if(timeCountListener != null){
                    timeCountListener.onTimeCountRunning(totalTime);
                }
                break;
            default:
                break;
        }

    }

    /**
     * 停止计时
     */
    public void stopTimeCount(){
        if(timerIsRunning && timer!=null) {
            totalTime = initTime;//代表结束
            timerIsRunning = false;
            TimeCountDownUtil.this.sendEmptyMessage(END);
            timer.cancel();
        }
    }

    public void start(){
        if(!timerIsRunning){
            timerIsRunning = true;
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (totalTime <= 0) {
                        totalTime = initTime;//代表结束
                        timerIsRunning = false;
                        TimeCountDownUtil.this.sendEmptyMessage(END);
                        timer.cancel();
                        return;
                    }
                    //否则继续计时
                    TimeCountDownUtil.this.sendEmptyMessage(RUNNING);
                    totalTime = totalTime - 1;
                }
            }, 0, 1000);
        }
    }
       /**
         * 计时接口
         */
        public interface TimeCountListener{
            void onTimeCountFinish() ;//计时结束
            void onTimeCountRunning(long millisUntilFinished);//正在计时
        }

    }