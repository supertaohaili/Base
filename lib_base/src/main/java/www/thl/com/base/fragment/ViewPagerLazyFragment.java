package www.thl.com.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umeng.analytics.MobclickAgent;


public abstract class ViewPagerLazyFragment extends Fragment {

    private boolean isVisible = false;//当前Fragment是否可见
    private boolean isInitView = false;//是否与View建立起映射关系
    private boolean isFirstLoad = true;//是否是第一次加载数据
    protected View convertView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (convertView == null) {
            convertView = inflater.inflate(initLayout(), container, false);
            initView(convertView);
        }
        if (convertView != null) {
            if (convertView.getParent() != null) {
                ((ViewGroup) convertView.getParent()).removeView(convertView);
            }
        }
        isInitView = true;
        lazyLoadData();
        return convertView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            isVisible = true;
            lazyLoadData();
        } else {
            isVisible = false;
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void lazyLoadData() {
        if (isFirstLoad) {
        } else {
        }
        if (!isFirstLoad || !isVisible || !isInitView) {
            return;
        }
        LazyData();
        isFirstLoad = false;
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

    /**
     * 加载页面布局文件
     *
     * @return
     */
    protected abstract int initLayout();

    protected abstract void initView(View mView);

    protected abstract String getSimpleName();

    /**
     * 加载要显示的数据
     */
    protected abstract void LazyData();


}
