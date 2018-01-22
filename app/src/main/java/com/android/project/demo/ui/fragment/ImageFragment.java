package com.android.project.demo.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import www.thl.com.base.adapter.BaseFragmentStateAdapter;
import www.thl.com.base.fragment.BaseLazyFragment;
import com.android.project.demo.R;
import com.android.project.demo.bean.ImageFrom;
import com.android.project.demo.ui.widget.CustomPopWindow;


import java.util.ArrayList;
import java.util.List;

public class ImageFragment extends BaseLazyFragment implements View.OnClickListener {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private BaseFragmentStateAdapter fAdapter;
    private List<Fragment> listFragment;
    private List<String> listTitle;
    private View ib_more;
    private ArrayList<ImageFrom> mImageFromData;

    @Override
    protected String getSimpleName() {
        return "ImageFragment";
    }

    @Override
    protected void initView(View mView) {
        ib_more = mView.findViewById(R.id.ib_more);
        ib_more.setOnClickListener(this);
        mTabLayout = (TabLayout) mView.findViewById(R.id.tablayout);
        mViewPager = (ViewPager) mView.findViewById(R.id.viewPager);
        TextView tvTitle = (TextView) mView.findViewById(R.id.tv_title);
        tvTitle.setText("图片专区");
    }

    @Override
    protected void LazyData(@Nullable Bundle savedInstanceState) {
        mImageFromData = new ArrayList<>();
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                listFragment = new ArrayList<>();
                listTitle = new ArrayList<>();
                if (mImageFromData != null && mImageFromData.size() > 0) {
                    for (ImageFrom data : mImageFromData) {
                        listFragment.add( ImageTypeFragment.newInstance(data));
                        listTitle.add(data.getName());
                        List<ImageFrom> list = data.getList();
                        if (list!=null){
                            for (ImageFrom mData:list){
                                Log.e("taohaili",mData.getName()+":"+mData.getHerf());
                            }
                        }
                    }
                }
                fAdapter = new BaseFragmentStateAdapter(getChildFragmentManager(), listFragment, listTitle);
                mViewPager.setAdapter(fAdapter);
                mTabLayout.setupWithViewPager(mViewPager);

            }
        };
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