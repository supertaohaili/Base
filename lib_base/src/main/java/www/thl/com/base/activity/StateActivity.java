package www.thl.com.base.activity;

import android.os.Bundle;
import android.view.View;

import www.thl.com.base.interfaces.State;
import www.thl.com.base.view.statemanager.StateListener;
import www.thl.com.base.view.statemanager.StateManager;
import www.thl.com.utils.NetworkUtils;

/**
 * 创建时间：2017/8/21 14:16
 * 编写人：taohaili
 * 功能描述：可以显示状态的activity
 */
public abstract class StateActivity extends BaseActivity implements State {

    protected StateManager mStateManager;

    @Override
    public void initData(Bundle savedInstanceState) {
        mStateManager = StateManager.builder(this)
                .setContent(getStateContent())//为哪部分内容添加状态管理。这里可以是Activity，Fragment或任何View。
                .setErrorOnClickListener(getErrorListener())
                .setNetErrorOnClickListener(getNetErrorListener())
                .setEmptyOnClickListener(getEmptyListener())
                .setConvertListener(getConvertListener())
                .build();//构建
        if (!NetworkUtils.isNetworkConnected() && isCheckNet()) {
            showNetError();
        }else{
            showLoading();
            loadNetData();
        }
    }

    private Object getStateContent() {
        return this;
    }

    protected boolean isCheckNet() {
        return false;
    }

    protected StateListener.ConvertListener getConvertListener() {
        return null;
    }

    protected StateListener.OnClickListener getEmptyListener() {
        if (isDefaultLoad()) {
            return new StateListener.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!NetworkUtils.isNetworkConnected()) {
                        showNetError();
                        return;
                    }
                    showLoading();
                    loadNetData();
                }
            };
        } else {
            return null;
        }
    }

    protected StateListener.OnClickListener getNetErrorListener() {
        if (isDefaultLoad()) {
            return new StateListener.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!NetworkUtils.isNetworkConnected()) {
                        return;
                    }
                    showLoading();
                    loadNetData();
                }
            };
        } else {
            return null;
        }
    }

    protected StateListener.OnClickListener getErrorListener() {
        if (isDefaultLoad()) {
            return new StateListener.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!NetworkUtils.isNetworkConnected()) {
                        showNetError();
                        return;
                    }
                    showLoading();
                    loadNetData();
                }
            };
        } else {
            return null;
        }
    }

    private boolean isDefaultLoad() {
        return true;
    }

    protected void loadNetData() {

    }

    @Override
    public void showLoading() {
        if (mStateManager != null) {
            mStateManager.showLoading();
        }
    }

    @Override
    public void showError() {
        if (mStateManager != null) {
            mStateManager.showError();
        }
    }

    @Override
    public void showNetError() {
        if (mStateManager != null) {
            mStateManager.showNetError();
        }
    }

    @Override
    public void showContent() {
        if (mStateManager != null) {
            mStateManager.showContent();
        }
    }

    @Override
    public void showEmpty() {
        if (mStateManager != null) {
            mStateManager.showEmpty();
        }
    }
}
