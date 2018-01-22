package www.thl.com.base.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umeng.analytics.MobclickAgent;


public abstract class BaseLazyFragment extends Fragment {

    protected View mView;
    protected Activity mActivity;
    protected Context mContext;

    private boolean mInited = false;
    private Bundle mSavedInstanceState;

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(initLayout(), null);
        initView(mView);
        return mView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSavedInstanceState = savedInstanceState;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            if (!isHidden()) {
                mInited = true;
                LazyData(null);
            }
        } else {
            if (!isHidden()) {
                mInited = true;
                LazyData(savedInstanceState);
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!mInited && !hidden) {
            mInited = true;
            LazyData(mSavedInstanceState);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getSimpleName());
    }

    @Override
    public void onStop() {
        super.onStop();
        MobclickAgent.onPageEnd(getSimpleName());
    }

    protected abstract int initLayout();

    protected abstract String getSimpleName();

    protected abstract void initView(View mView);

    /**
     * 懒加载
     */
    protected abstract void LazyData(@Nullable Bundle savedInstanceState);

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
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), permission);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {//没有获取到权限
                ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, permissionItem);
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
