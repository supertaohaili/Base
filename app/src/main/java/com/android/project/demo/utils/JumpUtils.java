package com.android.project.demo.utils;

import android.app.Activity;
import android.view.View;


public class JumpUtils {

    /**
     * 跳转到视频播放
     *
     * @param activity
     * @param view
     */
    public static void goToVideoPlayer(Activity activity, View view) {
//        Intent intent = new Intent(activity, PlayActivity.class);
//        intent.putExtra(PlayActivity.TRANSITION, true);
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            Pair pair = new Pair<>(view, PlayActivity.IMG_TRANSITION);
//            ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                    activity, pair);
//            ActivityCompat.startActivity(activity, intent, activityOptions.toBundle());
//        } else {
//            activity.startActivity(intent);
//            activity.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
//        }
    }

}
