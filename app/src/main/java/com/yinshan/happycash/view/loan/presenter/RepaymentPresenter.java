package com.yinshan.happycash.view.loan.presenter;

import android.content.Context;
import android.util.Log;

import com.yinshan.happycash.application.AppException;
import com.yinshan.happycash.framework.TokenManager;
import com.yinshan.happycash.network.api.LoanApi;
import com.yinshan.happycash.network.common.RxHttpUtils;
import com.yinshan.happycash.network.common.base.ApiException;
import com.yinshan.happycash.network.common.base.BaseObserver;
import com.yinshan.happycash.network.common.base.RxTransformer;
import com.yinshan.happycash.view.loan.model.DepositMethodsBean;
import com.yinshan.happycash.view.loan.model.DepositResponseBean;
import com.yinshan.happycash.view.loan.view.IRepaymentView;

import java.lang.ref.SoftReference;

import io.reactivex.observers.DefaultObserver;

/**
 * Created by huxin on 2018/8/17.
 */
public class RepaymentPresenter {

    IRepaymentView mView;
    Context mContext;
    LoanApi mApi;

    public RepaymentPresenter(Context context,IRepaymentView view){
        mView = view;
        mContext = context;
        mApi = RxHttpUtils.getInstance().createApi(LoanApi.class);
    }

    public void getRepaymentList(double shouldPrePay){
        mView.showLoadingDialog();
        mApi.getDepostMethods(TokenManager.getInstance().getToken())
                .compose(RxTransformer.io_main())
                .subscribe(new BaseObserver<DepositMethodsBean>(new SoftReference(mContext)) {

                    @Override
                    public void onNext(DepositMethodsBean bean) {
                        super.onNext(bean);
                        mView.dismissLoadingDialog();
                        mView.getRepaymentListOk(bean,shouldPrePay);
                    }

                    @Override
                    protected void onError(ApiException ex) {
                        super.onError(ex);
                        mView.dismissLoadingDialog();
                        mView.getRepaymentListFail(ex);
                        AppException.handleException(mContext,ex.getCode(),ex.getMessage());
                    }
                });

    }

    public void doDeposit(String loanAppId, String method, double shouldPrePay) {
        Log.d("doDeposit",method);
        mApi.doDeposit(loanAppId,method,TokenManager.getInstance().getToken())
                .compose(RxTransformer.io_main())
                .subscribe(new BaseObserver<DepositResponseBean>(new SoftReference(mContext)){
                    @Override
                    public void onNext(DepositResponseBean bean) {
                        super.onNext(bean);
                        mView.getDepositOk(bean,shouldPrePay);
                    }

                    @Override
                    protected void onError(ApiException ex) {
                        super.onError(ex);
                        mView.getDepositFail(ex);
                        AppException.handleException(mContext,ex.getCode(),ex.getMessage());
                    }
                });
    }
}
