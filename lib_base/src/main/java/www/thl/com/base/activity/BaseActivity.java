package www.thl.com.base.activity;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import www.thl.com.utils.KeyboardUtils;

/**
 * 功能描述：activity 基类
 **/
public abstract class BaseActivity extends FragmentActivity {

    protected Activity mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(initLayout());
        initView();
        initListener();
        initData(savedInstanceState);
    }

    protected abstract int initLayout();

    protected abstract void initView();

    protected void initListener() {
    }

    protected abstract void initData(Bundle savedInstanceState);


    /*处理权限问题*/
    private SparseArray<Runnable> allowablePermissionRunnables;
    private SparseArray<Runnable> disallowablePermissionRunnables;
    private int permissionItem = 0;

    protected void requestPermission(String permission, Runnable allowableRunnable, Runnable disallowableRunnable) {
        permissionItem++;
        if (allowableRunnable != null) {
            if (allowablePermissionRunnables == null) {
                allowablePermissionRunnables = new SparseArray<>();
            }
            allowablePermissionRunnables.put(permissionItem, allowableRunnable);
        }
        if (disallowableRunnable != null) {
            if (disallowablePermissionRunnables == null) {
                disallowablePermissionRunnables = new SparseArray<>();
            }
            disallowablePermissionRunnables.put(permissionItem, disallowableRunnable);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //减少是否拥有权限
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(getApplicationContext(), permission);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {//没有获取到权限
                ActivityCompat.requestPermissions(BaseActivity.this, new String[]{permission}, permissionItem);
            } else {
                if (allowableRunnable != null) {
                    allowableRunnable.run();
                }
            }
        } else {
            if (allowableRunnable != null) {
                allowableRunnable.run();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults != null && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (allowablePermissionRunnables != null) {
                    Runnable allowRun = allowablePermissionRunnables.get(requestCode);
                    if (allowRun != null) {
                        allowRun.run();
                    }
                }
            } else {
                if (disallowablePermissionRunnables != null) {
                    Runnable disallowRun = disallowablePermissionRunnables.get(requestCode);
                    if (disallowRun != null) {
                        disallowRun.run();
                    }
                }
            }
        }
    }


    //region软键盘的处理
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (isTouchView(filterViewByIds(), ev)) return super.dispatchTouchEvent(ev);
            if (hideSoftByEditViewIds() == null || hideSoftByEditViewIds().length == 0)
                return super.dispatchTouchEvent(ev);
            View v = getCurrentFocus();
            if (isFocusEditText(v, hideSoftByEditViewIds())) {
                if (isTouchView(hideSoftByEditViewIds(), ev))
                    return super.dispatchTouchEvent(ev);
                //隐藏键盘
                KeyboardUtils.hideSoftInput(this);
                clearViewFocus(v, hideSoftByEditViewIds());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 清除editText的焦点
     *
     * @param v   焦点所在View
     * @param ids 输入框
     */
    protected void clearViewFocus(View v, int... ids) {
        if (null != v && null != ids && ids.length > 0) {
            for (int id : ids) {
                if (v.getId() == id) {
                    v.clearFocus();
                    break;
                }
            }
        }
    }

    /**
     * 隐藏键盘
     *
     * @param v   焦点所在View
     * @param ids 输入框
     * @return true代表焦点在edit上
     */
    protected boolean isFocusEditText(View v, int... ids) {
        if (v instanceof EditText) {
            EditText tmp_et = (EditText) v;
            for (int id : ids) {
                if (tmp_et.getId() == id) {
                    return true;
                }
            }
        }
        return false;
    }

    //是否触摸在指定view上面,对某个控件过滤
    protected boolean isTouchView(View[] views, MotionEvent ev) {
        if (views == null || views.length == 0) return false;
        int[] location = new int[2];
        for (View view : views) {
            view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            if (ev.getX() > x && ev.getX() < (x + view.getWidth())
                    && ev.getY() > y && ev.getY() < (y + view.getHeight())) {
                return true;
            }
        }
        return false;
    }

    //是否触摸在指定view上面,对某个控件过滤
    protected boolean isTouchView(int[] ids, MotionEvent ev) {
        int[] location = new int[2];
        for (int id : ids) {
            View view = findViewById(id);
            if (view == null) continue;
            view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            if (ev.getX() > x && ev.getX() < (x + view.getWidth())
                    && ev.getY() > y && ev.getY() < (y + view.getHeight())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 传入EditText的Id
     * 没有传入的EditText不做处理
     *
     * @return id 数组
     */
    protected int[] hideSoftByEditViewIds() {
        return null;
    }

    /**
     * 传入要过滤的View
     * 过滤之后点击将不会有隐藏软键盘的操作
     *
     * @return id 数组
     */
    protected View[] filterViewByIds() {
        return null;
    }

}