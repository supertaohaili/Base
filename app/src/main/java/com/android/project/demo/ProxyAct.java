package com.android.project.demo;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.android.project.demo.ui.activity.GestureLoginActivity;
import com.android.project.demo.utils.imageloader.ImageLoaderUtils;
import com.umeng.analytics.MobclickAgent;

import www.thl.com.utils.AppUtils;
import www.thl.com.utils.DataHelper;

/**
 * 代理activity
 */
public class ProxyAct {

    public void proxy(Application application){
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                ActivityTack.tack.addActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                if (!(activity instanceof MainActivity)) {
                    MobclickAgent.onPageStart(activity.getClass().getSimpleName()); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
                }
                MobclickAgent.onResume(activity);
                byte[] pass = DataHelper.getDeviceData(activity, Config.GESTURE_PASSWORD);
                if (pass != null) {
                    String gesturePassword = new String(pass);
                    if (!TextUtils.isEmpty(gesturePassword)) {
                        if (VIdeoApplication.appIsBackground) {
                            VIdeoApplication.appIsBackground = false;
                            activity.startActivity(new Intent(activity, GestureLoginActivity.class));
                        }
                    }
                }
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                if (!(activity instanceof MainActivity)) {
                    MobclickAgent.onPageEnd(activity.getClass().getSimpleName()); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
                }
                MobclickAgent.onPause(activity);
                if (AppUtils.isAppOnBg()) {
                    VIdeoApplication.appIsBackground = true;
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                ActivityTack.tack.removeActivity(activity);
                ImageLoaderUtils.clearMemory(activity);
            }
        });
    }
}
