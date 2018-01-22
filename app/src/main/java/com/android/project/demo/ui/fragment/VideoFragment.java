package com.android.project.demo.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import www.thl.com.base.adapter.BaseFragmentStateAdapter;
import www.thl.com.base.fragment.BaseLazyFragment;
import com.android.project.demo.ui.widget.CustomPopWindow;
import www.thl.com.utils.ToastUtils;
import com.android.project.demo.R;

import java.util.ArrayList;
import java.util.List;


public class VideoFragment extends BaseLazyFragment implements View.OnClickListener {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private BaseFragmentStateAdapter fAdapter;
    private List<Fragment> listFragment;
    private List<String> listTitle;
    private View ib_more;

    @Override
    protected String getSimpleName() {
        return "VideoFragment";
    }

    @Override
    protected void initView(View mView) {
        ib_more = mView.findViewById(R.id.ib_more);
        ib_more.setOnClickListener(this);
        mTabLayout = (TabLayout) mView.findViewById(R.id.tablayout);
        mViewPager = (ViewPager) mView.findViewById(R.id.viewPager);
        TextView tvTitle = (TextView) mView.findViewById(R.id.tv_title);
        tvTitle.setText("视频专区");
        listFragment = new ArrayList<>();
        listFragment.add(new VideoTypeFragment());
        listFragment.add(new VideoTypeFragment());
        listFragment.add(new VideoTypeFragment());
        listFragment.add(new VideoTypeFragment());
        listFragment.add(new VideoTypeFragment());

        listTitle = new ArrayList<>();
        listTitle.add("动作片");
        listTitle.add("动作片");
        listTitle.add("动作片");
        listTitle.add("动作片");
        listTitle.add("动作片");

        fAdapter = new BaseFragmentStateAdapter(getChildFragmentManager(), listFragment, listTitle);
        mViewPager.setAdapter(fAdapter);
        mViewPager.setOffscreenPageLimit(1);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    protected void LazyData(@Nullable Bundle savedInstanceState) {
        ToastUtils.showLong("VideoFragment");
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_tab;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_more:
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.pop_from, null);
                CustomPopWindow popWindow = new CustomPopWindow.PopupWindowBuilder(getActivity())
                        .setView(view)
                        .enableBackgroundDark(true)
                        .setFocusable(true)
                        .setOutsideTouchable(true)
                        .create();
                popWindow.showAsDropDown(ib_more, 0, 0);
                break;
        }
    }
}
