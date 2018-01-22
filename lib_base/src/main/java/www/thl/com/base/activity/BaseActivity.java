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
}