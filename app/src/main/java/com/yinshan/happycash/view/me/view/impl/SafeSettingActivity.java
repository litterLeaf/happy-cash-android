package com.yinshan.happycash.view.me.view.impl;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.yinshan.happycash.R;
import com.yinshan.happycash.application.AppManager;
import com.yinshan.happycash.framework.BaseSingleNoScrollActivity;
import com.yinshan.happycash.utils.SPUtils;
import com.yinshan.happycash.view.main.MainActivity;
import com.yinshan.happycash.view.me.presenter.SafeSettingPresenter;
import com.yinshan.happycash.view.me.view.ISafeSettingView;

import butterknife.OnClick;

/**
 * Created by huxin on 2018/3/20.
 */

public class SafeSettingActivity extends BaseSingleNoScrollActivity implements ISafeSettingView{

    SafeSettingPresenter mPresenter;

    @Override
    protected String bindTitle() {
        return getResources().getString(R.string.safe_setting);
    }

    @Override
    protected int bindDownLayout() {
        return R.layout.activity_safe_setting;
    }

    @Override
    protected void secondInit() {
    }

    @OnClick({R.id.btnQuit})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.btnQuit:
                submitQuit();
                break;
        }
    }

    private void submitQuit(){
        mPresenter.logout();
    }

    private void quitClear(){
        SPUtils.clearAll();
    }

    @Override
    public void logoutOk() {
        quitClear();
        startActivity(new Intent(this, MainActivity.class));
        AppManager.getInstance().finishAllActivity();
    }
}