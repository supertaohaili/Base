package www.thl.com.base.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * 网络状态改变接收器
 **/
public class NetworkStateReceiver extends BroadcastReceiver {

    private OnNetworkStateChangeListener mOnNetworkStateChangeListener;

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {

            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = manager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isAvailable()) {
                // 网络连接
                String name = netInfo.getTypeName();
                if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    // WiFi网络
                    if (mOnNetworkStateChangeListener != null) {
                        mOnNetworkStateChangeListener.onChange(NetworkType.WIFI);
                    }
                } else if (netInfo.getType() == ConnectivityManager.TYPE_ETHERNET) {
                    // 有线网络
                    if (mOnNetworkStateChangeListener != null) {
                        mOnNetworkStateChangeListener.onChange(NetworkType.ETHERNET);
                    }
                } else if (netInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    // 3g网络
                    if (mOnNetworkStateChangeListener != null) {
                        mOnNetworkStateChangeListener.onChange(NetworkType.MOBILE);
                    }
                }
            } else {
                // 网络断开
                if (mOnNetworkStateChangeListener != null) {
                    mOnNetworkStateChangeListener.onChange(NetworkType.NONE);
                }
            }
        }
    }

    /**
     * 注册广播接收
     *
     * @param context  上下文
     * @param listener 网络状态监听
     */
    public void registerReceiver(Context context, OnNetworkStateChangeListener listener) {
        try {
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            filter.setPriority(Integer.MAX_VALUE);
            context.registerReceiver(this, filter);
            this.mOnNetworkStateChangeListener = listener;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 注销广播接收
     *
     * @param context 上下文
     */
    public void unRegisterReceiver(Context context) {
        try {
            context.unregisterReceiver(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 网络状态监听
     */
    public interface OnNetworkStateChangeListener {
        void onChange(NetworkType type);
    }

    /**
     * 网络接入类型
     */
    public enum NetworkType {
        NONE, WIFI, MOBILE, ETHERNET
    }
}
