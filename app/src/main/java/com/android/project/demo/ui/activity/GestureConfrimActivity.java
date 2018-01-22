package com.android.project.demo.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.project.demo.Config;
import www.thl.com.base.activity.BaseActivity;
import com.android.project.demo.ui.widget.LockPatternView;
import com.android.project.demo.utils.LockPatternUtil;
import com.android.project.demo.R;

import java.util.List;

import www.thl.com.utils.DataHelper;
import www.thl.com.utils.ScreenUtils;

public class GestureConfrimActivity extends BaseActivity {

    private LockPatternView lockPatternView;
    private TextView messageTv;
    private static final long DELAYTIME = 600l;
    private byte[] gesturePassword;


    @Override
    protected void initView() {
        ScreenUtils.setFullScreen(this);
        lockPatternView = (LockPatternView) findViewById(R.id.lockPatternView);
        messageTv = (TextView) findViewById(R.id.messageTv);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        gesturePassword = DataHelper.getDeviceData(this, Config.GESTURE_PASSWORD);
        lockPatternView.setOnPatternListener(patternListener);
        updateStatus(Status.DEFAULT);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_gesture_login;
    }


    private LockPatternView.OnPatternListener patternListener = new LockPatternView.OnPatternListener() {

        @Override
        public void onPatternStart() {
            lockPatternView.removePostClearPatternRunnable();
        }

        @Override
        public void onPatternComplete(List<LockPatternView.Cell> pattern) {
            if (pattern != null) {
                if (LockPatternUtil.checkPattern(pattern, gesturePassword)) {
                    updateStatus(Status.CORRECT);
                } else {
                    updateStatus(Status.ERROR);
                }
            }
        }
    };

    /**
     * 更新状态
     *
     * @param status
     */
    private void updateStatus(Status status) {
        messageTv.setText(status.strId);
        messageTv.setTextColor(getResources().getColor(status.colorId));
        switch (status) {
            case DEFAULT:
                lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
                break;
            case ERROR:
                lockPatternView.setPattern(LockPatternView.DisplayMode.ERROR);
                lockPatternView.postClearPatternRunnable(DELAYTIME);
                break;
            case CORRECT:
                lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
                loginGestureSuccess();
                break;
        }
    }

    /**
     * 手势登录成功（去首页）
     */
    private void loginGestureSuccess() {
        setResult(10001);
        this.finish();
    }

    private enum Status {
        //默认的状态
        DEFAULT(R.string.gesture_default2, R.color.grey_a5a5a5),
        //密码输入错误
        ERROR(R.string.gesture_error, R.color.red_f4333c),
        //密码输入正确
        CORRECT(R.string.gesture_correct, R.color.grey_a5a5a5);

        private Status(int strId, int colorId) {
            this.strId = strId;
            this.colorId = colorId;
        }

        private int strId;
        private int colorId;
    }

}
