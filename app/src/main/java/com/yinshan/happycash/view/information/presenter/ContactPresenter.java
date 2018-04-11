package com.yinshan.happycash.view.information.presenter;

import android.content.Context;

import com.yinshan.happycash.framework.TokenManager;
import com.yinshan.happycash.network.api.RecordApi;
import com.yinshan.happycash.network.common.RxHttpUtils;
import com.yinshan.happycash.network.common.base.ApiException;
import com.yinshan.happycash.network.common.base.BaseObserver;
import com.yinshan.happycash.network.common.base.RxTransformer;
import com.yinshan.happycash.view.information.model.ContactBean;
import com.yinshan.happycash.view.information.view.IContactView;

import java.lang.ref.SoftReference;
import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by huxin on 2018/4/2.
 */

public class ContactPresenter {

    Context mContext;
    IContactView mView;

    public ContactPresenter(Context context,IContactView view){
        mContext = context;
        mView = view;
    }

    public void getContactInfo(){
        RxHttpUtils.getInstance().createApi(RecordApi.class)
                .getContactInfo(TokenManager.getInstance().getToken())
                .compose(RxTransformer.io_main())
                .subscribe(new BaseObserver<List<ContactBean>>(new SoftReference(mContext)){
                    @Override
                    public void onNext(List<ContactBean> value) {
                        super.onNext(value);
                        mView.showContactInfo(value);
                    }

                    @Override
                    protected void onError(ApiException ex) {
                        super.onError(ex);
                    }
                });
    }
}
