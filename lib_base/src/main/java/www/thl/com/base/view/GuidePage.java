package www.thl.com.base.view;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import www.thl.com.base.R;
import www.thl.com.utils.DrawableUtils;
import www.thl.com.utils.SizeUtils;
import www.thl.com.utils.ToastUtils;

/**
 * 创建时间：2017/8/21 10:39
 * 编写人：taohaili
 * 功能描述：启动页的封装
 */
public class GuidePage extends FrameLayout implements ViewPager.OnPageChangeListener {
    public String tag = "GuidePage";
    private Context context;
    private List<View> imageViews = new ArrayList<>();//通过图片地址生成的view集合
    private ViewPager viewPager;
    private LinearLayout llIndicateContainer;
    private Button btnEntry;
    private GradientDrawable selectedDrawable;//选中圆点drawable
    private GradientDrawable unSelectedDrawable;//未选中圆点drawable
    private GuidePageAdapter adapter;
    private boolean isShowEntry = false;

    private OnClickLastItem mOnClickLastItem;

    public GuidePage(Context context) {
        this(context, null);
    }

    public GuidePage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuidePage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        View.inflate(context, R.layout.guide_page, this);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        llIndicateContainer = (LinearLayout) findViewById(R.id.llIndicateContainer);
        btnEntry = (Button) findViewById(R.id.btnEntry);
    }

    public GuidePage setmOnClickLastItem(OnClickLastItem mOnClickLastItem) {
        this.mOnClickLastItem = mOnClickLastItem;
        return this;
    }

    public GuidePage setLocalImageResList(List<Integer> imageUrls, ImageResourcesLoaderInterface imageLoader) {
        if (imageUrls == null || imageUrls.size() <= 0) {
            Log.e(tag, "请设置图片数据");
        } else {
            if (imageLoader == null) {
                for (int i = 0; i < imageUrls.size(); i++) {
                    ImageView imageView = null;
                    if (imageView == null) {
                        imageView = new ImageView(context);
                    }
                    Integer url = imageUrls.get(i);
                    imageView.setBackgroundResource(url);
                    imageViews.add(imageView);
                }
                if (adapter == null) {
                    adapter = new GuidePageAdapter();
                }
                viewPager.setAdapter(adapter);
                viewPager.setFocusable(true);
                viewPager.addOnPageChangeListener(this);
            } else {
                for (int i = 0; i < imageUrls.size(); i++) {
                    ImageView imageView = null;
                    if (imageView == null) {
                        imageView = new ImageView(context);
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    }
                    int url = imageUrls.get(i);
                    imageViews.add(imageView);
                    if (imageLoader != null)
                        imageLoader.displayImage(context, url, imageView);
                    else
                        Log.e(tag, "请设置图片加载器ImageLoader");
                }
                if (adapter == null) {
                    adapter = new GuidePageAdapter();
                }
                viewPager.setAdapter(adapter);
                viewPager.setFocusable(true);
                viewPager.addOnPageChangeListener(this);
            }
        }
        return this;
    }

    public GuidePage setImageList(List<String> imageUrls, ImageURLLoaderInterface imageLoader) {
        if (imageUrls == null || imageUrls.size() <= 0 || imageLoader == null) {
            Log.e(tag, "请设置图片数据,或者实现图片加载");
        } else {
            for (int i = 0; i < imageUrls.size(); i++) {
                ImageView imageView = null;
                if (imageView == null) {
                    imageView = new ImageView(context);
                }
                String url = imageUrls.get(i);
                imageViews.add(imageView);
                if (imageLoader != null)
                    imageLoader.displayImage(context, url, imageView);
                else
                    Log.e(tag, "请设置图片加载器ImageLoader");
            }
            if (adapter == null) {
                adapter = new GuidePageAdapter();
            }
            viewPager.setAdapter(adapter);
            viewPager.setFocusable(true);
            viewPager.addOnPageChangeListener(this);
        }
        return this;
    }

    /**
     * 设置圆点指示器
     *
     * @param selectedColorId
     * @param unselectedColorId
     * @return
     */
    public GuidePage setOvalIndicator(int selectedColorId, int unselectedColorId, float
            radius) {
        selectedDrawable = DrawableUtils.getOvalShapeDrawable(selectedColorId, radius);
        unSelectedDrawable = DrawableUtils.getOvalShapeDrawable(unselectedColorId, radius);
        llIndicateContainer.removeAllViews();
        for (int i = 0; i < imageViews.size(); i++) {
            ImageView imageView = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup
                    .LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            if (i == 0) {
                params.leftMargin = 0;
                imageView.setImageDrawable(selectedDrawable);
            } else {
                params.leftMargin = SizeUtils.dp2px(5);
                imageView.setImageDrawable(unSelectedDrawable);
            }
            imageView.setLayoutParams(params);
            llIndicateContainer.addView(imageView);
        }
        return this;
    }

    /**
     * 设置进入按钮点击监听
     *
     * @param listener
     * @return
     */
    public GuidePage setOnEntryClickListener(String text, int textColor, int textSize, int
            backgroundResId, OnClickListener listener) {
        this.isShowEntry = true;
        btnEntry.setText(text);
        btnEntry.setTextColor(textColor);
        btnEntry.setTextSize(textSize);
        btnEntry.setBackgroundResource(backgroundResId);
        btnEntry.setOnClickListener(listener);
        return this;
    }

    @Override
    public void onPageSelected(int position) {
        if (isShowEntry) {
            //最后一页显示进入按钮
            if (position == imageViews.size() - 1) {
                btnEntry.setVisibility(VISIBLE);
            } else {
                btnEntry.setVisibility(GONE);
            }
        }
        for (int i = 0; i < llIndicateContainer.getChildCount(); i++) {
            ImageView childView = (ImageView) llIndicateContainer.getChildAt(i);
            if (i == position) {
                childView.setImageDrawable(selectedDrawable);
            } else {
                childView.setImageDrawable(unSelectedDrawable);
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 引导图片显示适配器
     */
    class GuidePageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            container.addView(imageViews.get(position));
            View view = imageViews.get(position);
            if (position == imageViews.size() - 1) {
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mOnClickLastItem != null) {
                            ToastUtils.showLong("点击了最后一张");
                            mOnClickLastItem.onClick(view);
                        }
                    }
                });
            }
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    /**
     * 展示数据的接口
     *
     * @param <T>
     */
    public interface ImageURLLoaderInterface<T extends ImageView> extends Serializable {

        void displayImage(Context context, String path, T imageView);
    }

    public interface ImageResourcesLoaderInterface<T extends ImageView> extends Serializable {

        void displayImage(Context context, int resourcesId, T imageView);
    }

    public interface OnClickLastItem {

        void onClick(View view);
    }
}
