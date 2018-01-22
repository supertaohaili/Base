package www.thl.com.base.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import www.thl.com.base.R;
import www.thl.com.base.view.GuidePage;


/**
 * 创建时间：2017/8/21 10:13
 * 编写人：taohaili
 * 功能描述：guide页面，只需在子类中对guidepage进行设置即可
 */
public abstract class GuideActivity extends BaseActivity {

    @Override
    public int initLayout() {
        return R.layout.activity_guide;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        GuidePage guidePage = (GuidePage) findViewById(R.id.guidePage);
        guidePage.setLocalImageResList(getImages(),
                new GuidePage.ImageResourcesLoaderInterface() {
                    @Override
                    public void displayImage(Context context, int resourcesId, ImageView imageView) {
                        showImage(context, imageView, resourcesId);
                    }
                })
                .setOvalIndicator(getSelectedColorId(), getUnselectedColorId(), 10)
                .setmOnClickLastItem(new GuidePage.OnClickLastItem() {
                    @Override
                    public void onClick(View view) {
                        OnClickLastItem();
                    }
                });
    }

    protected abstract void showImage(Context context, ImageView imageView, int resourcesId);


    protected int getUnselectedColorId() {
        return 0xf5f5f5;
    }

    protected int getSelectedColorId() {
        return 0xfffff;
    }

    protected abstract List<Integer> getImages();

    protected abstract void OnClickLastItem();

    @Override
    public void initView() {

    }
}
