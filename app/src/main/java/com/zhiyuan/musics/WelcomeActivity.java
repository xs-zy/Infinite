package com.zhiyuan.musics;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener{

    //是否是第一次进入
    private boolean isFirstUse;


    private int recLen = 5;//跳过倒计时提示5秒
    private TextView button;
    Timer timer = new Timer();
    private Handler handler;
    private Runnable runnable;

    private AlphaAnimation mHideAnimation;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //定义全屏参数
        int flag= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //设置当前窗体为全屏显示
        getWindow().setFlags(flag, flag);
        setContentView(R.layout.activity_welcome);
        initView();
        timer.schedule(task, 1000, 1000);//等待时间一秒，停顿时间一秒
        normalStartUp();


    }

    //初始化控件
    private void initView() {

        button = (TextView) findViewById(R.id.button);
        button.setOnClickListener(this);
    }



    private void normalStartUp(){

        /**
         * 正常情况下不点击跳过
         */
        handler = new Handler();
        handler.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {
                /*//从闪屏界面跳转到首界面
                */
                /* *
                 *如果用户不是第一次使用则直接调转到显示界面,否则调转到引导界面
                 */
                preferences = getSharedPreferences("isFirstUse", Context.MODE_PRIVATE);
                isFirstUse = preferences.getBoolean("isFirstUse", true);
                if (isFirstUse) {
                    startActivity(new Intent(WelcomeActivity.this, GuideActivitys.class));
                } else {
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                finish();
                //实例化Editor对象
                SharedPreferences.Editor editor = preferences.edit();
                //存入数据
                editor.putBoolean("isFirstUse", false);
                //提交修改
                editor.commit();
            }
        }, 5000);//延迟5S后发送handler信息
    }



    /**
     * View渐隐动画效果
     */
    public  void setHideAnimation( View view, int duration)
    {
        if (null == view || duration < 0)
        {
            return;
        }

        if (null != mHideAnimation)
        {
            mHideAnimation.cancel();
        }
        // 监听动画结束的操作
        mHideAnimation = new AlphaAnimation(1.0f, 0.0f);
        mHideAnimation.setDuration(duration);
        mHideAnimation.setFillAfter(true);
        view.startAnimation(mHideAnimation);
        startActivity(new Intent(this, MainActivity.class));
    }


    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() { // UI thread
                @Override
                public void run() {
                    recLen--;
                    button.setText("跳过 " + recLen);
                    if (recLen < 0) {
                        timer.cancel();
                        button.setVisibility(View.GONE);//倒计时到0隐藏字体
                    }
                }
            });
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button:{
                //从闪屏界面跳转到首界面
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                if (runnable != null) {
                    handler.removeCallbacks(runnable);
                }
            }
            break;
        }
    }
}
