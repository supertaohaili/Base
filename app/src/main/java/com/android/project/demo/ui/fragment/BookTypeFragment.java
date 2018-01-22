package com.android.project.demo.ui.fragment;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.project.demo.R;
import www.thl.com.base.adapter.SingleAdapter;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

import java.io.File;
import java.util.ArrayList;

import www.thl.com.base.fragment.StateViewPagerLazyFragment;
import www.thl.com.utils.SDCardUtils;
import www.thl.com.utils.ToastUtils;

public class BookTypeFragment extends StateViewPagerLazyFragment {

    private TwinklingRefreshLayout refreshLayout;
    private RecyclerView mRecyclerView;
    private SingleAdapter<String> adapter;
    private ArrayList<String> sourceImageList;

    @Override
    protected String getSimpleName() {
        return "BookTypeFragment";
    }

    @Override
    protected void initView(View mView) {
        refreshLayout = (TwinklingRefreshLayout) mView.findViewById(R.id.refresh);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recyclerview);
        ProgressLayout headerView = new ProgressLayout(getActivity());
        refreshLayout.setHeaderView(headerView);
        refreshLayout.setAutoLoadMore(true);
        refreshLayout.setOverScrollRefreshShow(false);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefreshing();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishLoadmore();
                    }
                }, 2000);
            }
        });

        String sdCardPath = SDCardUtils.getSDCardPath();
        String path = sdCardPath + "book.txt";
        if (SDCardUtils.isSDCardEnable()) {
            File mFile = new File(path);
            if (!mFile.exists()) {

            }
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_book_type;
    }

    @Override
    protected void LazyData() {
        super.LazyData();
        ToastUtils.showLong("BookTypeFragment");
    }

    @Override
    protected Object getStateContent() {
        return refreshLayout;
    }

    @Override
    protected boolean isCheckNet() {
        return true;
    }
}