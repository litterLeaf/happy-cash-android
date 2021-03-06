package com.yinshan.happycash.application;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import com.yinshan.happycash.utils.UiHelper;

import java.util.List;
import java.util.Stack;

/**
 * Created by huxin on 2018/3/2.
 */

public class AppManager {

    private static final String TAG = AppManager.class.getSimpleName();

    /**
     * activity栈
     */
    private static Stack<Activity> activityStack;

    private static AppManager instance;

    /**
     * 单实例，UI无需考虑多线程同步问题
     */
    public static AppManager getInstance(){
        if(instance == null){
            instance = new AppManager();
        }
        return instance;
    }

    /**
     * 添加Activity到栈
     */
    public void addActivity(Activity activity) {

        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（栈顶Activity）
     */
    public Activity currentActivity() {
        if (activityStack == null || activityStack.isEmpty()) {
            return null;
        }
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 获取当前Activity（栈顶Activity） 没有找到则返回null
     */
    public Activity findActivity(Class<?> cls) {
        Activity activity = null;
        for (Activity aty : activityStack) {
            if (aty.getClass().equals(cls)) {
                activity = aty;
                break;
            }
        }
        return activity;
    }

    public void finishToActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (!activity.getClass().equals(cls)) {
                activity.finish();
            }
        }
    }

    /**
     * 结束当前Activity（栈顶Activity）
     */
    public void finishActivity() {
        if (activityStack != null && activityStack.size() > 0) {
            Activity activity = activityStack.lastElement();
            UiHelper.hideSoftInput(activity);
            finishActivity(activity);
        }
    }

    /**
     * 结束指定的Activity(重载)
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定的Activity(重载)
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                break;
            }
        }
    }

    /**
     * 关闭除了指定activity以外的全部activity 如果cls不存在于栈中，则栈全部清空
     *
     * @param cls
     */
    public void finishOthersActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (!(activity.getClass().equals(cls))) {
                finishActivity(activity);
            }
        }
    }

    public boolean isLastActivity() {
        if (null != activityStack && 1 == activityStack.size()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    public void init() {

    }
}
