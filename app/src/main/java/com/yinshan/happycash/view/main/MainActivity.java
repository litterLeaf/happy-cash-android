package com.yinshan.happycash.view.main;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import com.yinshan.happycash.R;
import com.yinshan.happycash.framework.BaseActivity;
import com.yinshan.happycash.framework.DateManager;
import com.yinshan.happycash.framework.MessageEvent;
import com.yinshan.happycash.utils.AppLoanStatus;
import com.yinshan.happycash.utils.SPKeyUtils;
import com.yinshan.happycash.view.fragments.BuildUpFragment;
import com.yinshan.happycash.view.loan.view.impl.LoanInProcessFragment;
import com.yinshan.happycash.view.loan.view.impl.LoaningFragment;
import com.yinshan.happycash.view.loan.view.impl.RejectFragment;
import com.yinshan.happycash.view.loan.view.impl.RepaymentFragment;
import com.yinshan.happycash.view.information.view.InformationFragment;
import com.yinshan.happycash.view.me.view.impl.MeFragment;
import com.yinshan.happycash.view.loan.view.impl.UnLoanFragment;
import com.yinshan.happycash.widget.DataGenerator;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
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
 *        ┃　　　┃   神兽保佑
 *        ┃　　　┃   代码无BUG！
 *        ┃　　　┗━━━┓
 *        ┃　　　　　　　┣┓
 *        ┃　　　　　　　┏┛
 *        ┗┓┓┏━┳┓┏┛
 *           ┃┫┫　┃┫┫
 *           ┗┻┛　┗┻┛
 *
 * @author admin
 *         on 2018/1/11
 */
public class MainActivity extends BaseActivity  {

//    @BindView(R.id.bottom_tab_layout)
//    TabLayout mTabLayout;
    @BindView(R.id.id_textview_tab_loan)
    TextView textViewLoan;
    @BindView(R.id.id_textview_tab_certification)
    TextView textViewCertification;
    @BindView(R.id.id_textview_tab_me)
    TextView textViewMe;
    @BindView(R.id.id_textview_tab_online_qa)
    TextView textViewOnlineQA;
    @BindView(R.id.fragment_container)
    FrameLayout homeContainer;

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private UnLoanFragment unLoanFrag;
    private LoaningFragment loaningFrag;

    private LoanInProcessFragment processFragment;
    private InformationFragment inforFragament;
    private MeFragment meFrag;
    private BuildUpFragment buildUpFragment;
    private RejectFragment rejectFragment;
    private RepaymentFragment repaymentFragment;

    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void secondLayout() {

    }

    @Override
    protected void secondInit() {

    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

        initBottomBar();

    }

    private void initBottomBar() {

    }

    @OnClick({R.id.id_textview_tab_loan, R.id.id_textview_tab_certification,
            R.id.id_textview_tab_online_qa , R.id.id_textview_tab_me})
    public void click(View view){
        switch (view.getId()){

            case R.id.id_textview_tab_loan:
                String status =(String)DateManager.getInstance().getMessage(SPKeyUtils.APP_STATUES);
                if(status==null){
                    showFragment(AppLoanStatus.UNLOAN);
                }else {
                    showFragment(status);
                }
                textViewLoan.setSelected(true);
                break;
            case R.id.id_textview_tab_certification:
                manageFragament(false,true,false,false,false,
                        false,false,false);
                break;
            case R.id.id_textview_tab_me:
                manageFragament(false,false,true,false,false,
                        false,false,false);
                break;
            case R.id.id_textview_tab_online_qa:
                mStartActivity(MainActivity.this, HotLineActivity.class);
                break;
        }
    }


    /**
     *manage  fragment  with status
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

    private void manageFragament( boolean isUnLoan,boolean isInfor,boolean isMeFragment,boolean isLoaning,boolean isProcess,
                                  boolean isBuildUp, boolean isRepayment, boolean isReject ){

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
        if (isProcess && null == processFragment) {
            Fragment tab1 = getSupportFragmentManager().findFragmentByTag(SPKeyUtils.PROCESS_FRAG);
            if (null != tab1) {
                processFragment = (LoanInProcessFragment) tab1;
            } else {
                processFragment = new LoanInProcessFragment();
                transaction.add(R.id.fragment_container, processFragment, SPKeyUtils.PROCESS_FRAG);
            }
        }
        if (isProcess && null != processFragment) {
            transaction.show(processFragment);
        } else if (!isProcess && null != processFragment) {
            transaction.hide(processFragment);
        }

        //loaning
        if (isLoaning && null == loaningFrag) {
            Fragment tab1 = getSupportFragmentManager().findFragmentByTag(SPKeyUtils.LOANING_FRAG);
            if (null != tab1) {
                loaningFrag = (LoaningFragment) tab1;
            } else {
                loaningFrag = new LoaningFragment();
                transaction.add(R.id.fragment_container, loaningFrag, SPKeyUtils.LOANING_FRAG);
            }
        }
        if (isLoaning && null != loaningFrag) {
            transaction.show(loaningFrag);
        } else if (!isLoaning && null != loaningFrag) {
            transaction.hide(loaningFrag);
        }

        //unLoan
        if (isUnLoan && null == unLoanFrag) {
            Fragment tab1 = getSupportFragmentManager().findFragmentByTag(SPKeyUtils.UNLOAN_FRAG);
            if (null != tab1) {
                unLoanFrag = (UnLoanFragment) tab1;
            } else {
                unLoanFrag = new UnLoanFragment();
                transaction.add(R.id.fragment_container, unLoanFrag, SPKeyUtils.UNLOAN_FRAG);
            }
        }
        if (isUnLoan && null != unLoanFrag) {
            transaction.show(unLoanFrag);
        } else if (!isUnLoan && null != unLoanFrag) {
            transaction.hide(unLoanFrag);
        }

        //inforFragament
        if (isInfor && null == inforFragament) {
            Fragment tab1 = getSupportFragmentManager().findFragmentByTag(SPKeyUtils.CERTIFICATION_TAB);
            if (null != tab1) {
                inforFragament = (InformationFragment) tab1;
            } else {
                inforFragament = new InformationFragment();
                transaction.add(R.id.fragment_container, inforFragament, SPKeyUtils.CERTIFICATION_TAB);
            }
        }
        if (isInfor && null != inforFragament) {
            transaction.show(inforFragament);
        } else if (!isInfor && null != inforFragament) {
            transaction.hide(inforFragament);
        }

        //meFragment
        if (isMeFragment && null == meFrag) {
            Fragment tab1 = getSupportFragmentManager().findFragmentByTag(SPKeyUtils.ME_TAB);
            if (null != tab1) {
                meFrag = (MeFragment) tab1;
            } else {
                meFrag = new MeFragment();
                transaction.add(R.id.fragment_container, meFrag, SPKeyUtils.ME_TAB);
            }
        }
        if (isMeFragment && null != meFrag) {
            transaction.show(meFrag);
        } else if (!isMeFragment && null != meFrag) {
            transaction.hide(meFrag);
        }
        transaction.commit();
    }

    private void showFragment(String  status) {

        if (AppLoanStatus.UNLOAN.equals(status)) {
            manageFragament(true, false, false, false, false, false, false,
                    false);
        } else if (AppLoanStatus.REVIEW.equals(status)) {
            manageFragament(false, false, false, false, true, false, false,
                    false);
        } else if (AppLoanStatus.REVIEW_SUPPLEMENT.equals(status)) {
            manageFragament(false, false, false, false, false, true, false,
                    false);
        } else if (AppLoanStatus.REPAYMENT.equals(status)) {
            manageFragament(false, false, false, false, false, false, true,
                    false);
        } else if (AppLoanStatus.OVERDUE.equals(status)) {
            manageFragament(false, false, false, false, false, false, true,
                    false);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void goInformationFragment(MessageEvent messageEvent) {
        manageFragament(false,true,false,false,false,
                false,false,false);
    }
}
