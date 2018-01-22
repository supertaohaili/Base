package com.android.project.demo.ui.fragment;


import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import www.thl.com.base.fragment.ViewPagerLazyFragment;
import www.thl.com.utils.ToastUtils;

import com.android.project.demo.utils.JumpUtils;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;
import com.android.project.demo.R;

import www.thl.com.base.adapter.SingleAdapter;
import www.thl.com.base.adapter.SuperViewHolder;

import java.util.ArrayList;
import java.util.List;

public class VideoTypeFragment extends ViewPagerLazyFragment {

    private TwinklingRefreshLayout refreshLayout;
    private RecyclerView mRecyclerView;
    private SingleAdapter<String> adapter;

    @Override
    protected String getSimpleName() {
        return "VideoTypeFragment";
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

        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter = new SingleAdapter<String>(getActivity(), R.layout.activity_splash) {
            @Override
            protected void bindData(final SuperViewHolder holder, String item) {
                holder.getRootView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        startActivity(new Intent(getActivity(), PlayActivity.class));
                        JumpUtils.goToVideoPlayer(getActivity(), holder.getRootView());
                    }
                });
            }
        };
        mRecyclerView.setAdapter(adapter);

    }

    @Override
    protected void LazyData() {
        ToastUtils.showLong("VideoTypeFragment");
//        refreshLayout.startRefresh();
        List<String> data = new ArrayList<>();
        data.add("");
        data.add("");
        data.add("");
        data.add("");
        data.add("");
        data.add("");
        data.add("");
        data.add("");
        data.add("");
        data.add("");
        data.add("");
        data.add("");
        data.add("");
        data.add("");
        data.add("");
        adapter.setData(data);
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_video_type;
    }

}