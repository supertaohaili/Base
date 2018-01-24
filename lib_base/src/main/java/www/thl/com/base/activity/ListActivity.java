package www.thl.com.base.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

import www.thl.com.base.R;
import www.thl.com.base.fragment.StateBaseLazyFragment;

public abstract class ListActivity extends StateActivity {

    private TwinklingRefreshLayout refreshLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    protected void initView() {
        refreshLayout = (TwinklingRefreshLayout) findViewById(R.id.refresh);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        ProgressLayout headerView = new ProgressLayout(this);
        refreshLayout.setHeaderView(headerView);
        refreshLayout.setAutoLoadMore(true);
        refreshLayout.setOverScrollRefreshShow(false);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                refresh(refreshLayout);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                loadMore(refreshLayout);
            }
        });
        initTwinklingRefreshLayout(refreshLayout);
        initRecyclerView(mRecyclerView);
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (layoutManager != null) {
            mRecyclerView.setLayoutManager(layoutManager);
        }
        adapter = initAdapter();
        if (adapter != null) {
            mRecyclerView.setAdapter(adapter);
        }
    }

    protected void initRecyclerView(RecyclerView mRecyclerView) {

    }

    protected void initTwinklingRefreshLayout(TwinklingRefreshLayout refreshLayout) {

    }

    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    @Override
    protected boolean isCheckNet() {
        return true;
    }

    protected abstract RecyclerView.LayoutManager getLayoutManager();

    protected abstract RecyclerView.Adapter initAdapter();

    protected void loadMore(TwinklingRefreshLayout refreshLayout) {
        loadNetData();
    }

    protected void refresh(TwinklingRefreshLayout refreshLayout) {
    }

    protected void finishRefreshing() {
        if (refreshLayout != null) {
            refreshLayout.finishRefreshing();
        }
    }

    protected void finishLoadmore() {
        if (refreshLayout != null) {
            refreshLayout.finishLoadmore();
        }
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_list;
    }

}