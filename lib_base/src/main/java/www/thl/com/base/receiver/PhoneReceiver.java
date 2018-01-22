package www.thl.com.base.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
 * <uses-permission android:name="android.permission.PROCESS_OUTGOING_CALLS" />
 * <p/>
 * action: android.intent.action.PHONE_STATE;  android.intent.action.NEW_OUTGOING_CALL;
 * <p/>
 * 去电时：
 * 未接：phone_state=OFFHOOK;
 * 挂断：phone_state=IDLE
 * 来电时:
 * 未接：phone_state=RINGING
 * 已接：phone_state=OFFHOOK;
 * 挂断：phone_state=IDLE
 */
public class PhoneReceiver extends BroadcastReceiver {

    private static final String TAG = "PhoneReceiver";

    private static final String RINGING = "RINGING";
    private static final String OFFHOOK = "OFFHOOK";
    private static final String IDLE = "IDLE";

    private static final String PHONE_STATE = "android.intent.action.PHONE_STATE";
    private static final String NEW_OUTGOING_CALL = "android.intent.action.NEW_OUTGOING_CALL";
    private static final String INTENT_STATE = "state";
    private static final String INTENT_INCOMING_NUMBER = "incoming_number";
    private PhoneListener phoneListener;
    private boolean isDialOut;
    private String number;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (NEW_OUTGOING_CALL.equals(intent.getAction())) {
            isDialOut = true;
            String outNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            if (!TextUtils.isEmpty(outNumber)) {
                this.number = outNumber;
            }
            if (phoneListener != null) {
                phoneListener.onPhoneStateChanged(CallState.Outgoing, number);
            }
        } else if (PHONE_STATE.equals(intent.getAction())) {
            String state = intent.getStringExtra(INTENT_STATE);
            String inNumber = intent.getStringExtra(INTENT_INCOMING_NUMBER);
            if (!TextUtils.isEmpty(inNumber)) {
                this.number = inNumber;
            }
            if (RINGING.equals(state)) {
                isDialOut = false;
                if (phoneListener != null) {
                    phoneListener.onPhoneStateChanged(CallState.IncomingRing, number);
                }
            } else if (OFFHOOK.equals(state)) {
                if (!isDialOut && phoneListener != null) {
                    phoneListener.onPhoneStateChanged(CallState.Incoming, number);
                }
            } else if (IDLE.equals(state)) {
                if (isDialOut) {
                    if (phoneListener != null) {
                        phoneListener.onPhoneStateChanged(CallState.OutgoingEnd, number);
                    }
                } else {
                    if (phoneListener != null) {
                        phoneListener.onPhoneStateChanged(CallState.IncomingEnd, number);
                    }
                }
            }
        }
    }

    /**
     * 去电时：
     * 未接：phone_state=OFFHOOK;
     * 挂断：phone_state=IDLE
     * 来电时:
     * 未接：phone_state=RINGING
     * 已接：phone_state=OFFHOOK;
     * 挂断：phone_state=IDLE
     */
    public void registerCallStateListener(Context context, PhoneStateListener listener) {
        try {
            //获取电话通讯服务
            TelephonyManager tpm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            tpm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 注册广播接收
     *
     * @param context       上下文
     * @param phoneListener 电话状态回调
     */
    public void registerReceiver(Context context, PhoneListener phoneListener) {
        try {
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.intent.action.PHONE_STATE");
            filter.addAction("android.intent.action.NEW_OUTGOING_CALL");
            filter.setPriority(Integer.MAX_VALUE);
            context.registerReceiver(this, filter);
            this.phoneListener = phoneListener;
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
     * 电话状态回调
     */
    public interface PhoneListener {
        void onPhoneStateChanged(CallState state, String number);
    }

    /**
     * 电话状态枚举
     */
    public enum CallState {
        Outgoing,     // 播出电话
        OutgoingEnd,  // 播出电话结束
        IncomingRing, // 接入电话铃响
        Incoming,     // 接入通话中
        IncomingEnd   // 接入通话完毕
    }

}
