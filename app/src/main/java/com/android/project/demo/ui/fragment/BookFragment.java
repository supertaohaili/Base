package com.android.project.demo.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import www.thl.com.base.adapter.BaseFragmentStateAdapter;
import www.thl.com.base.fragment.StateBaseLazyFragment;
import www.thl.com.utils.ToastUtils;
import com.android.project.demo.R;
import com.android.project.demo.ui.widget.CustomPopWindow;

import java.util.ArrayList;
import java.util.List;


public class BookFragment extends StateBaseLazyFragment implements View.OnClickListener {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private BaseFragmentStateAdapter fAdapter;
    private List<Fragment> listFragment;
    private List<String> listTitle;
    private View ib_more;

    private LinearLayout llt_content;

    @Override
    protected String getSimpleName() {
        return "ImageFragment";
    }

    @Override
    protected void initView(View mView) {
        llt_content =(LinearLayout) mView.findViewById(R.id.llt_content);
        ib_more = mView.findViewById(R.id.ib_more);
        ib_more.setOnClickListener(this);
        mTabLayout = (TabLayout) mView.findViewById(R.id.tablayout);
        mViewPager = (ViewPager) mView.findViewById(R.id.viewPager);
        TextView tvTitle = (TextView) mView.findViewById(R.id.tv_title);
        tvTitle.setText("图书专区");
        listFragment = new ArrayList<>();
        listFragment.add(new BookTypeFragment());
        listFragment.add(new BookTypeFragment());
        listFragment.add(new BookTypeFragment());
        listFragment.add(new BookTypeFragment());
        listFragment.add(new BookTypeFragment());

        listTitle = new ArrayList<>();
        listTitle.add("人物");
        listTitle.add("人物");
        listTitle.add("人物");
        listTitle.add("人物");
        listTitle.add("人物");

        fAdapter = new BaseFragmentStateAdapter(getChildFragmentManager(), listFragment, listTitle);
        mViewPager.setAdapter(fAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void LazyData(@Nullable Bundle savedInstanceState) {
        super.LazyData(savedInstanceState);
        ToastUtils.showLong("ImageFragment");
    }

    @Override
    protected Object getStateContent() {
        return llt_content;
    }

    @Override
    protected boolean isCheckNet() {
        return true;
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

