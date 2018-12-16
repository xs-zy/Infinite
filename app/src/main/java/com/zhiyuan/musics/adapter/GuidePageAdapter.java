package com.zhiyuan.musics.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2018/12/15.
 */

public class GuidePageAdapter extends PagerAdapter {

    private List<View> viewList;


    public GuidePageAdapter(List<View> viewList){
        this.viewList = viewList;
    }
    /**
     * 返回页面数量
     * @return  页面数量
     */
    @Override
    public int getCount() {
        if(viewList != null){
            return viewList.size();
        }
        return 0;
    }

    /**
     * 判断对象是否生成界面
     * @param view  界面
     * @param object  对象
     * @return  结果
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }



    /**
     *  初始化索引位置的界面
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewList.get(position));
        return viewList.get(position);
    }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }
}
