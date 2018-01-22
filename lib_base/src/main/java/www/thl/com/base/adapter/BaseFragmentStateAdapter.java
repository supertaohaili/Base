package www.thl.com.base.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import www.thl.com.utils.EmptyUtils;

public class BaseFragmentStateAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private List<String> mTitles;

    public BaseFragmentStateAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList) {
        super(fragmentManager);
        this.mFragmentList = fragmentList;
    }

    public BaseFragmentStateAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList, List<String> mTitles) {
        super(fragmentManager);
        this.mTitles = mTitles;
        this.mFragmentList = fragmentList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return !EmptyUtils.isEmpty(mTitles) ? mTitles.get(position) : "";
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
