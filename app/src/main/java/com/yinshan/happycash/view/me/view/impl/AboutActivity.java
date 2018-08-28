package com.yinshan.happycash.view.me.view.impl;

import android.support.v4.content.ContextCompat;

import com.yinshan.happycash.R;
import com.yinshan.happycash.framework.BaseSingleActivity;

/**
 * Created by huxin on 2018/3/20.
 */

public class AboutActivity extends BaseSingleActivity{

    @Override
    protected String bindTitle() {
        return getResources().getString(R.string.about_happy_cash);
    }

    @Override
    protected int bindDownLayout() {
        return R.layout.activity_about;
    }

    @Override
    protected void secondInit() {
        mLowestBg.setBackgroundColor(ContextCompat.getColor(this,R.color.app_yellow));
    }
}
