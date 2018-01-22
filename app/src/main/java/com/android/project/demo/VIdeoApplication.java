package com.android.project.demo;

import android.support.multidex.MultiDex;

import www.thl.com.base.BaseApplication;
import com.tencent.bugly.Bugly;
import com.umeng.analytics.MobclickAgent;

import www.thl.com.utils.Utils;

/**
 * 功能描述：实现了对logger的初始化、分包、刷新头的封装、全局activity的监听
 **/
public class VIdeoApplication extends BaseApplication {

    public static boolean appIsBackground = false;

    @Override
    protected void initConfig() throws Exception {
        MultiDex.install(this);
        Utils.init(this);
    }

    @Override
    protected void initConfigThread() throws Exception {
        //友盟统计
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        //bugly
        Bugly.init(this, Config.BUGLY_KEY, true);   //更新与崩溃统计初始化

        //代理activity
        new ProxyAct().proxy(this);
    }

}
