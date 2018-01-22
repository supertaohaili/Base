package www.thl.com.base.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import www.thl.com.base.R;

/**
 * 创建时间：2017/8/21 10:12
 * 编写人：taohaili
 * 功能描述：欢迎页面
 */
public abstract class SplashActivity extends BaseActivity {

    protected int TIME = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.SplashTheme);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        ImageView ivSplash = (ImageView) findViewById(R.id.imageview_splash);
        showImage(ivSplash, getImageId());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                toNext();
            }
        }, getTime()==-1?TIME:getTime());
    }

    protected abstract  int getImageId();

    protected abstract void showImage(ImageView ivSplash, int imageId);

    protected abstract void toNext();


    protected long getTime() {
        return -1;
    }


    @Override
    public void initView() {
//        QMUIDisplayHelper.setFullScreen(this);
    }


}
