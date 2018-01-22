package com.android.project.demo.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import www.thl.com.base.adapter.DataHolder;
import www.thl.com.base.adapter.SuperAdapter;
import www.thl.com.base.fragment.ViewPagerLazyFragment;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;
import com.android.project.demo.R;
import www.thl.com.base.adapter.LayoutWrapper;
import com.android.project.demo.bean.Image;
import com.android.project.demo.bean.ImageFrom;


import java.util.ArrayList;
import java.util.List;

public class ImageTypeFragment extends ViewPagerLazyFragment {

    private TwinklingRefreshLayout refreshLayout;
    private RecyclerView mRecyclerView;
    private ImageFrom mImageFrom;
    private static final String ARG_PARAM1 = "param1";
    private SuperAdapter adapter;
    private DataHolder<ImageFrom> holderSimple;
    private DataHolder<Image> holderSuper;
    private List<LayoutWrapper> mLayoutWrapperList = new ArrayList<>();

    public static ImageTypeFragment newInstance(ImageFrom mImageFrom) {
        ImageTypeFragment fragment = new ImageTypeFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, mImageFrom);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mImageFrom = (ImageFrom) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    protected String getSimpleName() {
        return "ImageTypeFragment";
    }

    @Override
    protected void initView(View mView) {
        refreshLayout = (TwinklingRefreshLayout) mView.findViewById(R.id.refresh);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recyclerview);
        ProgressLayout headerView = new ProgressLayout(getActivity());
        refreshLayout.setHeaderView(headerView);
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setAutoLoadMore(false);
        refreshLayout.setOverScrollRefreshShow(false);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                getData();
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mLayoutWrapperList.get(position).getSpanSize();
            }
        });
        mRecyclerView.setLayoutManager(gridLayoutManager);
//        int[] layoutIds = {R.layout.item_image_title, R.layout.item_image,};
//        adapter = new SuperAdapter(getActivity(), layoutIds);
//        mRecyclerView.setAdapter(adapter);
//
//        holderSimple = new DataHolder<ImageFrom>() {
//            @Override
//            public void bind(Context context, SuperViewHolder holder, ImageFrom item, int position) {
//                TextView tvName = holder.getView(R.id.tv_group);
//                tvName.setText(item.getName());
//                holder.getRootView().setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(getActivity(), PhotoAlbumActivity.class);
//                        intent.putExtra("data",item);
//                        startActivity(intent);
//                    }
//                });
//            }
//        };

//        holderSuper = new DataHolder<Image>() {
//            @Override
//            public void bind(Context context, SuperViewHolder holder, Image item, int position) {
//                LinearLayout llt_bottom = holder.getView(R.id.llt_bottom);
//                llt_bottom.removeAllViews();
//                String substring = item.num.substring(0, item.num.length()==0?0:(item.num.length()-1))+"";
//
//                for (int i = 0; i < (Integer.parseInt(substring)); i++) {
//                    View view = new View(getActivity());
//                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams
//                            .MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                    params.weight = 1.0f;
//                    params.topMargin = 2;
//                    view.setBackgroundColor(Color.parseColor("#353d3f"));
//                    view.setLayoutParams(params);
//                    llt_bottom.addView(view);
//                }
//
//                TextView tv_name = holder.getView(R.id.tv_name);
//                tv_name.setText(item.name);
//                TextView tv_msg = holder.getView(R.id.tv_msg);
//                tv_msg.setText(item.num);
//                ImageView imageView = holder.getView(R.id.imageView);
//                ImageLoaderUtils.loadImage(getActivity(), imageView, item.src);
//                holder.getRootView().setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(getActivity(), PhotoListActivity.class);
//                        intent.putExtra("data",item);
//                        startActivity(intent);
//                    }
//                });
//            }
//        };
    }

    private void getData() {

        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                adapter.setData(mLayoutWrapperList);
                refreshLayout.finishRefreshing();
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
//                List<ImageFrom> list = mImageFrom.getList();
//                if (list != null && list.size() > 0) {
//                    for (ImageFrom data : list) {
//                        try {
//                            mLayoutWrapperList.add(new LayoutWrapper(R.layout.item_image_title, 2, data, holderSimple));
//                            Document doc = Jsoup.connect(data.getHerf()).get();
//                            Elements select = doc.select("content#J_content.news-list>div#J_news_list" +
//                                    ".news-list-wrap>section.news-item.news-item-pic-link>a");
//                            for (int j = 0; j < select.size(); j++) {
//                                Element element = select.get(j);
//                                Image mImage = new Image();
//                                mImage.url = element.attr("href");
//                                mImage.src = element.select("div.img-wrap>img").attr("src");
//                                mImage.name = element.select("h3").get(0).text();
//                                mImage.num = element.select("div.img-wrap>span.num").text();
//                                mImage.time = element.select("p.tags.clearfix>em.time").text();
//                                mImage.type = element.select("p.tags.clearfix>em.src.fr").text();
//                                mLayoutWrapperList.add(new LayoutWrapper(R.layout.item_image, 1, mImage, holderSuper));
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                Message msg2 = new Message();
//                msg2.arg1 = 1;
//                handler.sendMessage(msg2);
            }
        }).start();
    }


    @Override
    protected int initLayout() {
        return R.layout.fragment_image_type;
    }

    @Override
    protected void LazyData() {
        refreshLayout.startRefresh();
    }

}