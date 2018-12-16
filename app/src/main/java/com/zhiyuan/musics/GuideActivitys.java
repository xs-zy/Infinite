package com.zhiyuan.musics;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhiyuan.musics.adapter.GuidePageAdapter;

import java.util.ArrayList;
import java.util.List;

public class GuideActivitys extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    //最后一页的按钮
    private Button ib_start;
    private ViewPager vp;
    private int []imageIdArray;//图片资源的数组
    private List<View> viewList;
    private ViewGroup vg;//放置圆点
    //实例化原点View
    private ImageView iv_point;
    private ImageView []ivPointArray;
    //设置按钮点击一次
    private boolean isoncl = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide_activitys);
        ib_start = (Button) findViewById(R.id.guide_ib_start);
        ib_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isoncl){
                    startActivity(new Intent(GuideActivitys.this,MainActivity.class));
                    finish();
                    isoncl = false;
                }
            }
        });
        //加载图片ViewPager
        initViewPager();
        //加载底部圆点
        initPoint();
    }

    //加载底部圆点
    private void initPoint() {
        //这里实例化Lin
        vg = (ViewGroup) findViewById(R.id.guide_ll_point);
        //根据ViewPager的item数量实例化数组
        ivPointArray = new ImageView[viewList.size()];
        //循环新建底部圆点ImageView，将生成的ImageView保存到数组中
        int size = viewList.size();
        for (int i = 0;i<size;i++){
            iv_point = new ImageView(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(20, 20);
            lp.setMargins(12, 0, 12, 0);
            iv_point.setLayoutParams(lp);
            ivPointArray[i] = iv_point;
            //第一个页面需要设置为选中状态，这里采用两张不同的图片
            if (i == 0){
                iv_point.setBackgroundResource(R.drawable.fullpoint);
            }else{
                iv_point.setBackgroundResource(R.drawable.nullpoint);
            }
            //将数组中的ImageView加入到ViewGroup
            vg.addView(ivPointArray[i]);
        }

    }

    //加载图片ViewPager
    private void initViewPager() {
        vp = (ViewPager) findViewById(R.id.guide_vp);
        imageIdArray = new int[]{R.drawable.aa,R.drawable.bb,R.drawable.cc,R.drawable.dd};
        viewList = new ArrayList<>();
        //获取一个线性布局设置为全屏
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //循环创建View并且加入集合
        int length = imageIdArray.length;
        for (int i=0;i<length;i++){
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(params);
            imageView.setBackgroundResource(imageIdArray[i]);
            viewList.add(imageView);
        }

        vp.setAdapter(new GuidePageAdapter(viewList));
        vp.setOnPageChangeListener(this);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int length = imageIdArray.length;
        for(int i = 0;i < length;i++){
            ivPointArray[position].setBackgroundResource(R.drawable.fullpoint);
            if (position != i){
                ivPointArray[i].setBackgroundResource(R.drawable.nullpoint);
            }
        }
        if(position == imageIdArray.length - 1){
            ib_start.setVisibility(View.VISIBLE);
        }else {
            ib_start.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
