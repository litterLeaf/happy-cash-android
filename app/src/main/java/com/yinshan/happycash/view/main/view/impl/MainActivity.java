package com.yinshan.happycash.view.main.view.impl;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.yinshan.happycash.R;
import com.yinshan.happycash.analytic.callLog.CallLogDBController;
import com.yinshan.happycash.analytic.contacts.ContactDBController;
import com.yinshan.happycash.analytic.event.MobAgent;
import com.yinshan.happycash.analytic.event.MobEvent;
import com.yinshan.happycash.analytic.sms.SmsDBController;
import com.yinshan.happycash.application.AppApplication;
import com.yinshan.happycash.application.AppContext;
import com.yinshan.happycash.application.HappyAppSP;
import com.yinshan.happycash.config.inner.AppDataConfig;
import com.yinshan.happycash.framework.BaseActivity;
import com.yinshan.happycash.framework.MessageEvent;
import com.yinshan.happycash.framework.TokenManager;
import com.yinshan.happycash.utils.AppLoanStatus;
import com.yinshan.happycash.utils.LoggerWrapper;
import com.yinshan.happycash.utils.MyDebugUtils;
import com.yinshan.happycash.utils.SPKeyUtils;
import com.yinshan.happycash.utils.StatusManagementUtils;
import com.yinshan.happycash.utils.ToastUtils;
import com.yinshan.happycash.utils.ToolsUtils;
import com.yinshan.happycash.view.bindcard.model.BandCardBean;
import com.yinshan.happycash.view.loan.view.impl.ApplyFragment;
import com.yinshan.happycash.view.loan.view.impl.BuildUpFragment;
import com.yinshan.happycash.utils.SPUtils;
import com.yinshan.happycash.utils.SystemUtil;
import com.yinshan.happycash.view.information.view.InformationFragment;
import com.yinshan.happycash.view.information.view.impl.support.InfoUploadEvent;
import com.yinshan.happycash.view.loan.view.impl.LoaningFragment;
import com.yinshan.happycash.view.loan.view.impl.RejectFragment;
import com.yinshan.happycash.view.loan.view.impl.RepaymentFragment;
import com.yinshan.happycash.view.loan.view.impl.UnLoanFragment;
import com.yinshan.happycash.view.main.SplashActivity;
import com.yinshan.happycash.view.main.contract.ChatClientContract;
import com.yinshan.happycash.view.main.model.HXBean;
import com.yinshan.happycash.view.main.model.LastLoanAppBean;
import com.yinshan.happycash.view.main.model.YWUser;
import com.yinshan.happycash.view.main.model.ProfileBean;
import com.yinshan.happycash.view.main.presenter.ChatClientPresenter;
import com.yinshan.happycash.view.main.presenter.GetStatusPresenter;
import com.yinshan.happycash.view.main.presenter.UpdateDialogPresenter;
import com.yinshan.happycash.view.main.presenter.VersionPresenter;
import com.yinshan.happycash.view.main.view.IGetStatusView;
import com.yinshan.happycash.view.main.view.IUpdateDialogView;
import com.yinshan.happycash.view.main.view.IVersionView;
import com.yinshan.happycash.view.me.view.impl.MeFragment;
import com.yinshan.happycash.widget.common.CommonClickListener;
import com.yinshan.happycash.widget.common.ToastManager;
import com.yinshan.happycash.widget.dialog.CheckPermissionDialog;
import com.yinshan.happycash.widget.dialog.CommonDialog;
import com.yinshan.happycash.widget.dialog.PerGuideDialogFragment;
import com.yinshan.happycash.widget.dialog.PowerDialog;


import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;


/**
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 *        ┃　　　┃   兽神保佑
 *        ┃　　　┃   代码无BUG！
 *        ┃　　　┗━━━┓
 *        ┃　　　　　　　┣┓
 *        ┃　　　　　　　┏┛
 *        ┗┓┓┏━┳┓┏┛
 *           ┃┫┫　┃┫┫
 *           ┗┻┛　┗┻┛
 *
 *    描述：          贷款状态机+权限申请
 *    创建人：     admin
 *    创建时间：2018/1/11
 *
 */
public class MainActivity extends BaseActivity implements PerGuideDialogFragment.GuideListener, IGetStatusView, IVersionView, ChatClientContract.View, IUpdateDialogView {

    boolean isFirstEnter = true;
    public static boolean isNotResume = false;

    public static final long MIN_VALUE = 2000000;
    public static final long MAX_VALUE = 6000000;
    public static final int MONEY_SEG = 4;
    public static final int RATE = 8;
    public static int seg;

    public static long loanMoney;
    public static int choosePeriod;

    FrameLayout fragmentContainer;
    TextView tabLoan;
    LinearLayout idLinearlayoutLoan;
    TextView tabInformation;
    LinearLayout idLinearlayoutCertification;
    TextView tabMe;
    LinearLayout idLinearlayoutMe;
    TextView abOnlineQa;
    LinearLayout idLinearlayoutOnlineQa;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private UnLoanFragment unLoanFrag;
    private LoaningFragment loaningFrag;

    private ApplyFragment applyFragment;
    private InformationFragment inforFragament;
    private MeFragment meFrag;
    private BuildUpFragment buildUpFragment;
    private RejectFragment rejectFragment;
    private RepaymentFragment repaymentFragment;

    public static int chooseIndex;

    private GetStatusPresenter mPresenter;
    private VersionPresenter mVersionPresenter;
    private UpdateDialogPresenter mUpdateDialogPresenter;
    private ArrayList<String> permissionsList;
    private ArrayList<String> permissionsNeeded;
    private ChatClientPresenter chatClientPresenter;

    private String[] mustPermission = {
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_CONTACTS, Manifest.permission.READ_PHONE_STATE
            , Manifest.permission.READ_SMS, Manifest.permission.READ_CALL_LOG, Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void secondLayout() {

    }

    @Override
    protected void secondInit() {
        requestProfile();
    }

    @Override
    protected void onDestroy() {
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        if(null!=unLoanFrag){
            transaction.remove(unLoanFrag);
        }
        if(null!=loaningFrag){
            transaction.remove(loaningFrag);
        }
        if(null!=applyFragment){
            transaction.remove(applyFragment);
        }
        if(null!=inforFragament){
            transaction.remove(inforFragament);
        }
        if(null!=meFrag){
            transaction.remove(meFrag);
        }
        if(null!=buildUpFragment){
            transaction.remove(buildUpFragment);
        }
        if(null!=rejectFragment){
            transaction.remove(rejectFragment);
        }
        if(null!=repaymentFragment){
            transaction.remove(repaymentFragment);
        }
        transaction.commitAllowingStateLoss();

        super.onDestroy();
        MyDebugUtils.v("mainActivity wantImage onDestroy ");
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initUI();
        RxBus.get().register(this);
        reSetTab(1);

        MainActivity.choosePeriod = 3;

        mPresenter = new GetStatusPresenter(this, this);
        mUpdateDialogPresenter = new UpdateDialogPresenter(this, this);
        chatClientPresenter = new ChatClientPresenter(this);
        chatClientPresenter.attachView(this);

        if (!judgeMustPermission()) {
            PowerDialog powerDialog = new PowerDialog(MainActivity.this, new PowerListener());
            powerDialog.setCancelable(false);
            powerDialog.show();
//            PerGuideDialogFragment fragment = new PerGuideDialogFragment();
//            fragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogTheme);
//            fragment.setCancelable(false);
//            fragment.show(getSupportFragmentManager(), "guide");
        } else {
            checkPermissons();
        }
        if(TokenManager.getInstance().hasLogin()) {
            if (SplashActivity.isGetStatus) {
                LastLoanAppBean object = SPUtils.getInstance().getObject(SPKeyUtils.LOANAPPBEAN, LastLoanAppBean.class);
                if (object != null && object.getStatus() != null) {
                    dealResult(object);
                } else {
                    showFragment(AppLoanStatus.UNLOAN);
                    mPresenter.getStatusInfo(TokenManager.getInstance().getToken());
                }
            } else {
                if (TokenManager.getInstance().hasLogin()) {
                    showFragment(AppLoanStatus.UNLOAN);
                    mPresenter.getStatusInfo(TokenManager.getInstance().getToken());
//                LastLoanAppBean object = SPUtils.getInstance().getObject(SPKeyUtils.LOANAPPBEAN, LastLoanAppBean.class);
//                if (object != null && object.getStatus() != null) {
//                    dealResult(object);
//                }else{
//                    showFragment(AppLoanStatus.UNLOAN);
//
//                }
                }else
                    showFragment(AppLoanStatus.UNLOAN);
            }
        }else{
            showFragment(AppLoanStatus.UNLOAN);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (chooseIndex == 1 && TokenManager.getInstance().hasLogin()) {
            if (!isFirstEnter) {
                if (!isNotResume) {
                    reUpdateStatus();
                } else {
                    MainActivity.isNotResume = false;
                }
            }
        } else if (chooseIndex == 2 || chooseIndex == 3) {
            reSetTab(chooseIndex);
            if(chooseIndex==3){
                manageFragment(false,false,true,false,false,
                        false,false,false,false,false);
            }
        }

        if (isFirstEnter)
            isFirstEnter = false;
    }

    private void dealResult(LastLoanAppBean bean) {
        if (bean == null || bean.getStatus() == null) {
            return;
        }
        String loanStatus = bean.getStatus();
        if (loanStatus == null) {
            return;
        } else if ("CURRENT".equals(loanStatus)) {//成功放款进入还款期
            if (!TextUtils.isEmpty(bean.getAppCurrentShownStatus()) && bean.getAppCurrentShownStatus().equals(AppDataConfig.DIALOG_SHOW_TIPS)) {
                mUpdateDialogPresenter.updateDialog(Long.valueOf(bean.getLoanAppId()), SPKeyUtils.DialogType_CURRENT);
            }
        } else if (loanStatus.equals("NOLOANAPP") ||
                loanStatus.equals("CANCELED") ||
                loanStatus.equals("PAID_OFF")
                ) {//没有贷款，取消？？   还款成功
            if (loanStatus.equals("PAID_OFF") && !TextUtils.isEmpty(bean.getAppPaidoffShownStatus()) && bean.getAppPaidoffShownStatus().equals(AppDataConfig.DIALOG_SHOW_TIPS)) {
                mUpdateDialogPresenter.updateDialog(Long.valueOf(bean.getLoanAppId()), SPKeyUtils.DialogType_PAID_OFF);
            }
        } else if (loanStatus.equals("CLOSED")) {//后台关闭，不同于拒绝
            ToastManager.showToast("Akhiri pinjaman ini");
        }
        String lastAppStatus = StatusManagementUtils.loanStatusClassify(bean);
        showFragment(lastAppStatus);
    }

    /**
     * manage  fragment  with status
     *
     * @param isUnLoan
     * @param isInfor
     * @param isMeFragment
     * @param isLoaning
     * @param isProcess
     * @param isBuildUp
     * @param isRepayment
     * @param isReject
     */

    private void manageFragment(boolean isUnLoan, boolean isInfor, boolean isMeFragment, boolean isLoaning, boolean isProcess,
                                 boolean isBuildUp, boolean isRepayment, boolean isExpiryRepayment, boolean isRollover, boolean isReject) {
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();

        //repaymentFragment
        if (isRepayment && null == repaymentFragment) {
            Fragment tab1 = getSupportFragmentManager().findFragmentByTag(SPKeyUtils.REPAYMENT_FRAG);
            if (null != tab1) {
                repaymentFragment = (RepaymentFragment) tab1;
            } else {
                repaymentFragment = new RepaymentFragment();
                transaction.add(R.id.fragment_container, repaymentFragment, SPKeyUtils.REPAYMENT_FRAG);
            }
        }
        if (isRepayment && null != repaymentFragment) {
            transaction.show(repaymentFragment);
        } else if (!isRepayment && null != repaymentFragment) {
            transaction.hide(repaymentFragment);
        }

        //Rejec
        if (isReject && null == rejectFragment) {
            Fragment tab1 = getSupportFragmentManager().findFragmentByTag(SPKeyUtils.REJECT_FRAG);
            if (null != tab1) {
                rejectFragment = (RejectFragment) tab1;
            } else {
                rejectFragment = new RejectFragment();
                transaction.add(R.id.fragment_container, rejectFragment, SPKeyUtils.REJECT_FRAG);
            }
        }
        if (isReject && null != rejectFragment) {
            transaction.show(rejectFragment);
        } else if (!isReject && null != rejectFragment) {
            transaction.hide(rejectFragment);
        }

        //BuildUp
        if (isBuildUp && null == buildUpFragment) {
            Fragment tab1 = getSupportFragmentManager().findFragmentByTag(SPKeyUtils.BUILDUP_FRAG);
            if (null != tab1) {
                buildUpFragment = (BuildUpFragment) tab1;
            } else {
                buildUpFragment = new BuildUpFragment();
                transaction.add(R.id.fragment_container, buildUpFragment, SPKeyUtils.BUILDUP_FRAG);
            }
        }
        if (isBuildUp && null != buildUpFragment) {
            transaction.show(buildUpFragment);
        } else if (!isBuildUp && null != buildUpFragment) {
            transaction.hide(buildUpFragment);
        }

        //isProcess
        boolean isApplyReShow = false;
        if(isProcess){
            if(null==applyFragment){
                Fragment tab1 = getSupportFragmentManager().findFragmentByTag(SPKeyUtils.PROCESS_FRAG);
                if (null != tab1) {
                    applyFragment = (ApplyFragment) tab1;
                    isApplyReShow = true;
                } else {
                    isApplyReShow = false;
                    applyFragment = new ApplyFragment();
                    transaction.add(R.id.fragment_container, applyFragment, SPKeyUtils.PROCESS_FRAG);
                }
            }else{
                isApplyReShow = true;
            }
        }
        if (isProcess && null != applyFragment) {
            transaction.show(applyFragment);
            if(isApplyReShow)
                applyFragment.resume();
        } else if (!isProcess && null != applyFragment) {
            transaction.hide(applyFragment);
        }

        //loaning
        boolean isLoaningFirstReShow = false;
        if(isLoaning){
            if (null == loaningFrag) {
                Fragment tab1 = getSupportFragmentManager().findFragmentByTag(SPKeyUtils.LOANING_FRAG);
                if (null != tab1) {
                    loaningFrag = (LoaningFragment) tab1;
                    isLoaningFirstReShow = true;
                } else {
                    isLoaningFirstReShow = false;
                    loaningFrag = new LoaningFragment();
                    transaction.add(R.id.fragment_container, loaningFrag, SPKeyUtils.LOANING_FRAG);
                }
            }else{
                isLoaningFirstReShow = true;
            }
        }
        if (isLoaning && null != loaningFrag) {
            transaction.show(loaningFrag);
            if(isLoaningFirstReShow)
                loaningFrag.resume();
        } else if (!isLoaning && null != loaningFrag) {
            transaction.hide(loaningFrag);
        }

        //unLoan
        boolean isUnLoanFirstReshow = false;
        if(isUnLoan){
            if(null==unLoanFrag){
                Fragment tab1 = getSupportFragmentManager().findFragmentByTag(SPKeyUtils.UNLOAN_FRAG);
                if (null != tab1) {
                    unLoanFrag = (UnLoanFragment) tab1;
                    isUnLoanFirstReshow = true;
                } else {
                    isUnLoanFirstReshow = false;
                    unLoanFrag = new UnLoanFragment();
                    transaction.add(R.id.fragment_container, unLoanFrag, SPKeyUtils.UNLOAN_FRAG);
                }
            }else{
                isUnLoanFirstReshow = true;
            }
        }
        if (isUnLoan && null != unLoanFrag) {
            transaction.show(unLoanFrag);
            if(isUnLoanFirstReshow)
                unLoanFrag.resume();
        } else if (!isUnLoan && null != unLoanFrag) {
            transaction.hide(unLoanFrag);
        }

        //inforFragament
        boolean isInfoFirstReShow = false;
        if(isInfor){
            if (null == inforFragament) {
                Fragment tab1 = getSupportFragmentManager().findFragmentByTag(SPKeyUtils.CERTIFICATION_TAB);
                if (null != tab1) {
                    inforFragament = (InformationFragment) tab1;
                    isInfoFirstReShow = true;
                } else {
                    isInfoFirstReShow = false;
                    inforFragament = new InformationFragment();
                    transaction.add(R.id.fragment_container, inforFragament, SPKeyUtils.CERTIFICATION_TAB);
                }
            }else{
                isInfoFirstReShow = true;
            }
        }
        if (isInfor && null != inforFragament) {
            transaction.show(inforFragament);
            if(isInfoFirstReShow)
                inforFragament.resume();
        } else if (!isInfor && null != inforFragament) {
            transaction.hide(inforFragament);
        }

        //meFragment
        boolean isMeFirstReShow = false;
        if (isMeFragment) {
            if (null == meFrag) {
                Fragment tab1 = getSupportFragmentManager().findFragmentByTag(SPKeyUtils.ME_TAB);
                if (null != tab1) {
                    meFrag = (MeFragment) tab1;
                    isMeFirstReShow = true;
                } else {
                    isMeFirstReShow = false;
                    meFrag = new MeFragment();
                    transaction.add(R.id.fragment_container, meFrag, SPKeyUtils.ME_TAB);
                }
            } else {
                isMeFirstReShow = true;
            }
        }
        if (isMeFragment && null != meFrag) {
            transaction.show(meFrag);
            if (isMeFirstReShow)
                meFrag.resume();
        } else if (!isMeFragment && null != meFrag) {
            transaction.hide(meFrag);
        }
        transaction.commitAllowingStateLoss();
    }

    private void showFragment(String status) {
        if (AppLoanStatus.UNLOAN.equals(status)) {
            manageFragment(true, false, false, false, false, false, false,
                    false, false, false);
        } else if (AppLoanStatus.REVIEW.equals(status)) {
            manageFragment(false, false, false, false, true, false, false,
                    false, false, false);
        } else if (AppLoanStatus.REVIEW_SUPPLEMENT.equals(status)) {
            manageFragment(false, false, false, false, false, true, false,
                    false, false, false);
        } else if (AppLoanStatus.REPAYMENT.equals(status)) {
            manageFragment(false, false, false, false, false, false, true,
                    false, false, false);
        } else if (AppLoanStatus.OVERDUE.equals(status)) {
//            manageFragment(false, false, false, false, false, false, true,
//                    false,false,false);
        } else if (AppLoanStatus.REJECT.equals(status)) {
            manageFragment(false, false, false, false, false, false, false,
                    false, false, true);
        }
    }

    @Subscribe
    public void goInformationFragment(MessageEvent messageEvent) {
        manageFragment(false, true, false, false, false,
                false, false, false, false, false);
        reSetTab(2);
    }

    //数据刷新
    public void updateStatus(final String token) {
        if (TokenManager.getInstance().hasLogin())
            mPresenter.getStatusInfo(token);
    }

    public void reUpdateStatus() {
        if (TokenManager.getInstance().hasLogin())
            mPresenter.getStatusInfo(TokenManager.getInstance().getToken());
    }

    @Subscribe
    public void goBackUnLoanFragment(InfoUploadEvent messageEvent) {
        manageFragment(false, false, false, true, false,
                false, false, false, false, false);
        reSetTab(1);
    }

    @OnClick({R.id.id_textview_tab_loan, R.id.id_textview_tab_certification, R.id.id_textview_tab_me, R.id.id_textview_tab_online_qa})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_textview_tab_loan:
                reSetTab(1);
                LastLoanAppBean object = SPUtils.getInstance().getObject(SPKeyUtils.LOANAPPBEAN, LastLoanAppBean.class);
                if (object != null && object.getStatus() != null) {
                    dealResult(object);
                } else {
                    showFragment(AppLoanStatus.UNLOAN);
                }
                break;
            case R.id.id_textview_tab_certification:
                reSetTab(2);
                manageFragment(false, true, false, false, false,
                        false, false, false, false, false);
                break;
            case R.id.id_textview_tab_me:
                reSetTab(3);
                manageFragment(false, false, true, false, false,
                        false, false, false, false, false);
                break;
            case R.id.id_textview_tab_online_qa:
//                if(TokenManager.getInstance().hasLogin()){
//                    if(LockUtils.Instance.isFastClick()){
//                        return;
//                    }
//                    if(ChatClient.getInstance().isLoggedInBefore()){
//                        String token = TokenManager.getInstance().getToken();
//                        if (TextUtils.isEmpty(token) || TokenManager.isExpired) {
//                            ToastUtils.showShort(R.string.show_not_login_yet);
//                            startActivity(new Intent(this, LoginActivity.class));
//                            return;
//                        }
//                        //获得常见问题列表
//                        chatClientPresenter.getMessage(SPKeyUtils.TENANTID);
//                    }else {
//                        initYW(0);
//                    }
//                }else {
//                    mStartActivity(MainActivity.this,LoginActivity.class);
//                }

                break;
        }
    }

    private void initYW(int i) {
        chatClientPresenter.getChatClientAccount(TokenManager.getInstance().getToken(), i);
    }


    private void reSetTab(int tab) {
        tabLoan.setSelected(false);
        tabInformation.setSelected(false);
        tabMe.setSelected(false);
        chooseIndex = tab;
        switch (tab) {
            case 1:
                tabLoan.setSelected(true);
                break;
            case 2:
                tabInformation.setSelected(true);
                break;
            case 3:
                tabMe.setSelected(true);
                break;
        }
    }

    //危险权限和权限组
    private final static int PERMISSION_CODE = 100;

    private void checkPermissons() {
        permissionsList = new ArrayList<>();
        permissionsNeeded = new ArrayList<>();

        //地理位置信息权限
        boolean hasPermission = hasSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        if (!hasPermission) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                permissionsNeeded.add(getString(R.string.GPS));
            }
            permissionsList.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        } else {
            switchGps();
        }

        //联系人相关权限
        hasPermission = hasSelfPermission(this, android.Manifest.permission.READ_CONTACTS);
        if (!hasPermission) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_CONTACTS)) {
                permissionsNeeded.add(getString(R.string.contacts));
            }
            permissionsList.add(Manifest.permission.READ_CONTACTS);
        } else {
            ContactDBController.getInstance().gainContacts();
        }

        //用于电话功能相关的权限
        hasPermission = hasSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE);
        if (!hasPermission) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_PHONE_STATE)) {
                permissionsNeeded.add(getString(R.string.phone_status));
            }
            permissionsList.add(Manifest.permission.READ_PHONE_STATE);
        } else {
            initImei();
//            initAppsFlyer();
        }

        //calllog相关的权限
        hasPermission = hasSelfPermission(this, Manifest.permission.READ_CALL_LOG);
        if (!hasPermission) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CALL_LOG)) {
                permissionsNeeded.add(getString(R.string.call_log));
            }
            permissionsList.add(Manifest.permission.READ_CALL_LOG);
        } else {
            CallLogDBController.getInstance().gainCallLogs();
        }

        //用于用户的SMS信息相关的运行时权限
        hasPermission = hasSelfPermission(this, android.Manifest.permission.READ_SMS);
        if (!hasPermission) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_SMS)) {
                permissionsNeeded.add(getString(R.string.SMS));
            }
            permissionsList.add(Manifest.permission.READ_SMS);
        } else {
            SmsDBController.getInstance().gainMessages();
        }
        //文件写的权限
        hasPermission = hasSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (!hasPermission) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissionsNeeded.add(getString(R.string.WRITE_EXTERNAL_STORAGE));
            }
            permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                StringBuilder sb = new StringBuilder(getString(R.string.permissions_rationale));
                sb.append("1." + permissionsNeeded.get(0) + "\n");
                for (int i = 1; i < permissionsNeeded.size(); i++) {
                    sb.append(i + 1 + "." + permissionsNeeded.get(i) + "\n");
                }

                String content = sb.toString();
                showPermisionDialog(content, SPKeyUtils.DIALOG_NULL, false);
            } else {
                ActivityCompat.requestPermissions(this,
                        permissionsList.toArray(new String[permissionsList.size()]),
                        PERMISSION_CODE);
            }
        }
    }

    //位置信息设置
    private void switchGps() {
        if (!SystemUtil.isGpsOpen(this)) {

            String content = getString(R.string.open_gps);
            showPermisionDialog(content, SPKeyUtils.DIALOG_GPS, false);
        }
    }

    private void gotoGpsSetting() {
        try {
            Intent localIntent = new Intent();
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            localIntent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(localIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //去设置未开放的权限
    private void gotoPermissionSetting() {
        try {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //crash 中出现Lenov的某机型会出现RuntimeExeception错误，应该是他们的底层做了修改
    //暂时先catch出来，后续找找解决方案
    public static boolean hasSelfPermission(Context context, String permission) {
        try {
            return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
        } catch (RuntimeException e) {
            return false;
        }
    }

    private void showPermisionDialog(String content, final int type, boolean showCancel) {
        CheckPermissionDialog dialog = new CheckPermissionDialog(this, () -> {
            switch (type) {
                case SPKeyUtils.DIALOG_GPS:
                    gotoGpsSetting();
                    break;
                case SPKeyUtils.DIALOG_SETTING:
                    gotoPermissionSetting();
                    break;
                case SPKeyUtils.DIALOG_NULL:
                    ActivityCompat.requestPermissions(MainActivity.this,
                            permissionsList.toArray(new String[permissionsList.size()]),
                            PERMISSION_CODE);
                    break;
            }
        });
        dialog.show();
        dialog.setMessageText(content);
        dialog.setCancelShow(showCancel);
    }

    public static void initImei() {
        String imei = HappyAppSP.getInstance().getImei();
        if (TextUtils.isEmpty(imei)) {
            imei = SystemUtil.getInstance().getImei();
            if (!TextUtils.isEmpty(imei)) {
                HappyAppSP.getInstance().setImei(imei);
            }
        }
    }

    //默认unLoan界面
    public void showDefaultView() {
        manageFragment(true, false, false, false, false, false, false,
                false, false, false);
    }

    @Override
    public void getStatusSuccess(LastLoanAppBean latestLoanAppBean) {
        if (latestLoanAppBean != null) {
            SPUtils.getInstance().setObject(SPKeyUtils.LOANAPPBEAN, latestLoanAppBean);
            if (latestLoanAppBean.getStatus() != null)
                dealResult(latestLoanAppBean);
        }
        LoggerWrapper.d("MainActivity: updatestatus--" + "success");
    }

    @Override
    public void getStatusError(String displayMessage) {
        ToastManager.showToast("Loading data tidak normal");
        LoggerWrapper.d("MainActivity: updatestatus--" + displayMessage);
        LastLoanAppBean object = SPUtils.getInstance().getObject(SPKeyUtils.LOANAPPBEAN, LastLoanAppBean.class);
        if (object != null) {
            dealResult(object);
        } else {
            showDefaultView();
        }
    }

    public static void setSeg(int i) {
        if (i < 3)
            MainActivity.seg = 0;
        else
            seg = (i - 1) / (100 / MainActivity.MONEY_SEG) + 1;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE:
                final List<String> permissionsNeeded = new ArrayList<>();
                for (int i = 0; i < grantResults.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        if (Manifest.permission.ACCESS_FINE_LOCATION.equals(permission)) {
                            switchGps();
                        } else if (Manifest.permission.READ_PHONE_STATE.equals(permission)) {
                            initImei();
//                            initAppsFlyer();
                            CallLogDBController.getInstance().gainCallLogs();
                        } else if (Manifest.permission.READ_CONTACTS.equals(permission)) {
                            ContactDBController.getInstance().gainContacts();
                        } else if (Manifest.permission.READ_SMS.equals(permission)) {
                            SmsDBController.getInstance().gainMessages();
                        }
                    } else {
                        if (Manifest.permission.ACCESS_FINE_LOCATION.equals(permission)) {
                            permissionsNeeded.add(getString(R.string.GPS));
                            MobAgent.onEvent(MobEvent.CLICK + MobEvent.PERMISSION_REJECT_LOCATION);
                        } else if (Manifest.permission.READ_PHONE_STATE.equals(permission)) {
                            permissionsNeeded.add(getString(R.string.call_log));
//                            initAppsFlyer();
                            MobAgent.onEvent(MobEvent.CLICK + MobEvent.PERMISSION_REJECT_PHONE);
                        } else if (Manifest.permission.READ_CONTACTS.equals(permission)) {
                            permissionsNeeded.add(getString(R.string.contacts));
                            MobAgent.onEvent(MobEvent.CLICK + MobEvent.PERMISSION_REJECT_CONTACT);
                        } else if (Manifest.permission.READ_SMS.equals(permission)) {
                            permissionsNeeded.add(getString(R.string.SMS));
                            MobAgent.onEvent(MobEvent.CLICK + MobEvent.PERMISSION_REJECT_SMS);
                        }
                    }
                }
                if (permissionsNeeded.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(permissionsNeeded.get(0));
                    for (int i = 1; i < permissionsNeeded.size(); i++) {
                        sb.append(",");
                        sb.append(permissionsNeeded.get(i));
                    }
                    String content = getString(R.string.permission_lzin_new, sb.toString());
                    showPermisionDialog(content, SPKeyUtils.DIALOG_SETTING, true);
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void guide() {
//        SPUtils.getInstance().setShowGuide(false);
        permissionsList = new ArrayList<>();
        for (String permission : mustPermission) {
            permissionsList.add(permission);
        }
        ActivityCompat.requestPermissions(MainActivity.this,
                permissionsList.toArray(new String[permissionsList.size()]),
                PERMISSION_CODE);
    }

    @Override
    public void getVersionOk(ProfileBean profileBean) {
        boolean forceUpgrade = profileBean.isForceUpgrade();
        //发布时修改
        if (forceUpgrade) {
            CommonDialog updateVersionDialog = new CommonDialog(this, R.layout.dialog_update_app,
                    ()-> ToolsUtils.launchAppDetail(AppContext.getContext()), "",
                    getResources().getString(R.string.please_update_the_version),
                    getResources().getString(R.string.oke), getResources().getString(R.string.update_version),
                    false);
            updateVersionDialog.show();
        }
    }

    @Override
    public void getVersionFail() {

    }

    /**
     * 暂时去掉
     * 获取留言板块
     *
     * @param hxBean
     */
    @Override
    public void getMessageSuccess(HXBean hxBean) {
//        int type = hxBean.getGreetingTextType();
//        String rob_welcome = hxBean.getGreetingText();
//        if(type==0){
//            SPUtils.put("rob_welcome",rob_welcome);
//        }else if(type==1){
//            final String str = rob_welcome.replaceAll("&quot;","\"");
//            JSONObject json = null;
//            try {
//                json = new JSONObject(str);
//                JSONObject ext = json.getJSONObject("ext");
//                final JSONObject msgtype = ext.getJSONObject("msgtype");
//                SPUtils.put("rob_welcome",msgtype.toString());
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            }
//        if(ChatClient.getInstance().isLoggedInBefore()){
//            inputMessage();
//            LastLoanAppBean  latestLoanAppBean= SPUtils.getInstance().getLatestBean();
//            String status;
//            String appId;
//            //已经登录，可以直接进入会话界面
//            //获取地址：kefu.easemob.com，“管理员模式 > 渠道管理 > 手机APP”页面的关联的“IM服务号”
//            if(latestLoanAppBean!=null){
//                status =latestLoanAppBean.getStatus();
//                appId = latestLoanAppBean.getLoanAppId();
//            }else {
//                status ="UNLOAN";
//                appId = "0000";
//            }
//            Intent intent = new IntentBuilder(MainActivity.this)
//                    .setVisitorInfo(ContentFactory.createVisitorInfo(null)
//                            .nickName(appId)
//                            .description(status))
//                    .setTitleName("Customer Service Kami")
//                    .setServiceIMNumber(SPKeyUtils.IMSERVICE)
//                    .build();
//            startActivity(intent);
//        }
    }

    @Override
    public void getMessageError(String message) {
        dismissLoadingDialog();
        ToastUtils.showShort(message);
    }

    /**
     * 获取登录的账号名和密码
     * @param ywUser
     */
    @Override
    public void getChatAccountSuccess(YWUser ywUser, int flag) {
        dismissLoadingDialog();
        if (ywUser != null) {
//            loginCEC(ywUser.getUserid(),ywUser.getPassword(),0);
        }
    }

    @Override
    public void getChatAccountFailure(String message) {
        dismissLoadingDialog();
        ToastUtils.showShort(message);
    }

    @Override
    public void updateDialogSuccess(String type) {
        if (type.equals(SPKeyUtils.DialogType_CURRENT)) {
            LastLoanAppBean object = SPUtils.getInstance().getObject(SPKeyUtils.LOANAPPBEAN, LastLoanAppBean.class);
            object.setAppCurrentShownStatus("");
            SPUtils.getInstance().setObject(SPKeyUtils.LOANAPPBEAN, object);
            CommonDialog paymentSuccessDialog = new CommonDialog(this, R.layout.dialog_current_suucess,
                    () -> ToolsUtils.launchAppDetail(AppApplication.appContext),
                    "", getResources().getString(R.string.current_tip1),
                    getResources().getString(R.string.oke), "Ayo rating kami", true);
            paymentSuccessDialog.show();
        } else if (type.equals(SPKeyUtils.DialogType_PAID_OFF)) {
            LastLoanAppBean bean = SPUtils.getInstance().getObject(SPKeyUtils.LOANAPPBEAN, LastLoanAppBean.class);
            bean.setAppPaidoffShownStatus("");
            SPUtils.getInstance().setObject(SPKeyUtils.LOANAPPBEAN, bean);
            CommonDialog paidOffSuccessDialog = new CommonDialog(this, R.layout.dialog_payment_suucess,
                    ()-> Log.e("paymentSuccessDialog", "success"), "",
                    getResources().getString(R.string.repayment_success),
                    getResources().getString(R.string.oke),
                    getResources().getString(R.string.oke), false);
            paidOffSuccessDialog.show();
        }
    }

    class PowerListener implements CommonClickListener {
        @Override
        public void onClick() {
            guide();
        }
    }

    public static long getLastMoney(){
        long sum = MainActivity.loanMoney+MainActivity.loanMoney*MainActivity.RATE*MainActivity.choosePeriod/100;
        double dSum = Double.valueOf(sum);
        double ceil = Math.ceil(dSum / MainActivity.choosePeriod);
        return Math.round(ceil);
    }

    public static long getLastMoney(double money,int period){
        long sum = (long) (money+money*MainActivity.RATE*period/100);
        double dSum = Double.valueOf(sum);
        double ceil = Math.ceil(dSum / period);
        return Math.round(ceil);
    }

    private boolean judgeMustPermission() {
        for (String permission : mustPermission) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    private void initUI() {
        fragmentContainer = (FrameLayout) findViewById(R.id.fragment_container);
        tabLoan = (TextView) findViewById(R.id.id_textview_tab_loan);
        idLinearlayoutLoan = (LinearLayout) findViewById(R.id.id_linearlayout_loan);
        tabInformation = (TextView) findViewById(R.id.id_textview_tab_certification);

        idLinearlayoutCertification = (LinearLayout) findViewById(R.id.id_linearlayout_certification);
        tabMe = (TextView) findViewById(R.id.id_textview_tab_me);
        idLinearlayoutMe = (LinearLayout) findViewById(R.id.id_linearlayout_me);
        abOnlineQa = (TextView) findViewById(R.id.id_textview_tab_online_qa);
        idLinearlayoutOnlineQa = (LinearLayout) findViewById(R.id.id_linearlayout_online_qa);
    }

    private void requestProfile() {


        mVersionPresenter = new VersionPresenter(this, this);
        String versionCode = SystemUtil.getInstance().getVersionCode();
        if (TextUtils.isEmpty(versionCode)) {
            return;
        }
        mVersionPresenter.getVersionInfo(versionCode);
    }
}
