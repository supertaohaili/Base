package com.android.project.demo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.project.demo.Config;
import com.android.project.demo.R;
import com.android.project.demo.ui.activity.CreateGestureActivity;
import com.android.project.demo.ui.activity.GestureConfrimActivity;

import www.thl.com.base.fragment.BaseLazyFragment;
import www.thl.com.utils.DataHelper;
import www.thl.com.utils.ToastUtils;

public class SettingFragment extends BaseLazyFragment implements View.OnClickListener {

    private ImageView iv_isopen;
    private LinearLayout llt_change_pass;
    private boolean isOpen = false;

    @Override
    protected String getSimpleName() {
        return "SettingFragment";
    }

    @Override
    protected void initView(View mView) {
        iv_isopen = (ImageView) mView.findViewById(R.id.iv_isopen);
        llt_change_pass = (LinearLayout) mView.findViewById(R.id.llt_change_pass);
        mView.findViewById(R.id.llt_safe_setting).setOnClickListener(this);
        mView.findViewById(R.id.llt_change_pass).setOnClickListener(this);

        mView.findViewById(R.id.tv_video).setOnClickListener(this);
        mView.findViewById(R.id.tv_book).setOnClickListener(this);
        mView.findViewById(R.id.tv_image).setOnClickListener(this);
        mView.findViewById(R.id.tv_clear).setOnClickListener(this);
        mView.findViewById(R.id.tv_updata).setOnClickListener(this);
    }

    @Override
    protected void LazyData(@Nullable Bundle savedInstanceState) {
        ToastUtils.showLong("SettingFragment");
    }

    @Override
    public void onResume() {
        super.onResume();
        byte[] pass = DataHelper.getDeviceData(getActivity(), Config.GESTURE_PASSWORD);
        if (pass != null) {
            String gesturePassword = new String(pass);
            if (!TextUtils.isEmpty(gesturePassword)) {
                iv_isopen.setBackgroundResource(R.drawable.sz_switch_on);
                llt_change_pass.setVisibility(View.VISIBLE);
                isOpen = true;
            } else {
                iv_isopen.setBackgroundResource(R.drawable.sz_switch_off);
                llt_change_pass.setVisibility(View.GONE);
                isOpen = false;
            }
        } else {
            iv_isopen.setBackgroundResource(R.drawable.sz_switch_off);
            llt_change_pass.setVisibility(View.GONE);
            isOpen = false;
        }
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_setting;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_video:
//                intent = new Intent(getActivity(), VideoRecordingActivity.class);
//                startActivity(intent);
                break;
            case R.id.tv_book:
//                intent = new Intent(getActivity(), LocalBookshelfActivity.class);
//                startActivity(intent);
                break;
            case R.id.tv_image:
//                intent = new Intent(getActivity(), CollectImageActivity.class);
//                startActivity(intent);
                break;
            case R.id.tv_clear:
//                new DialogUtils.Builder(getActivity())
//                        .setCanceledOnTouchOutside(false)
//                        .setCancelable(false)
//                        .setTitle("提示")
//                        .setText("是否确认清除缓存")
//                        .setNegative("取消", null)
//                        .setPositive("确定", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                final DialogFragment dialogFragment = new DialogUtils.Builder(getActivity())
//                                        .configProgress(new ConfigProgress() {
//                                            @Override
//                                            public void onConfig(ProgressParams progressParams) {
//                                                progressParams.backgroundColor = 0x00000001;
//                                                progressParams.textColor = 0xffffffff;
//                                            }
//                                        })
//                                        .setProgressText("清除缓存中...")
//                                        .setProgressStyle(ProgressParams.STYLE_SPINNER)
//                                        .show();
//                                Handler handler = new Handler() {
//                                    @Override
//                                    public void handleMessage(Message msg) {
//                                        super.handleMessage(msg);
//                                        dialogFragment.dismiss();
//                                    }
//                                };
//                                Glide.get(getActivity()).clearMemory();
//                                new Thread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        Glide.get(getActivity()).clearDiskCache();
//                                        try {
//                                            Thread.sleep(1000);
//                                        } catch (InterruptedException e) {
//                                            e.printStackTrace();
//                                        }
//                                        handler.sendEmptyMessage(1);
//                                    }
//                                }).start();
//                            }
//                        })
//                        .show();
                break;
            case R.id.tv_updata:
                ToastUtils.showShort("当前是最新版本");
                break;

            case R.id.llt_safe_setting:
//                new DialogUtils.Builder(getActivity())
//                        .setCanceledOnTouchOutside(false)
//                        .setCancelable(false)
//                        .setTitle("提示")
//                        .setText(isOpen ? "是否关闭手势密码" : "是否开启手势密码")
//                        .setNegative("取消", null)
//                        .setPositive("确定", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                if (isOpen) {
//                                    Intent intent = new Intent(getActivity(), GestureConfrimActivity.class);
//                                    startActivityForResult(intent, 102);
//                                    startActivity(intent);
//                                } else {
//                                    startActivity(new Intent(getActivity(), CreateGestureActivity.class));
//                                }
//                            }
//                        })
//                        .show();
                break;
            case R.id.llt_change_pass:
                intent = new Intent(getActivity(), GestureConfrimActivity.class);
                startActivityForResult(intent, 101);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == 10001) {
            startActivity(new Intent(getActivity(), CreateGestureActivity.class));
        } else if (requestCode == 102 && resultCode == 10001) {
            DataHelper.removeSF(getActivity(), Config.GESTURE_PASSWORD);
            iv_isopen.setBackgroundResource(R.drawable.sz_switch_off);
            llt_change_pass.setVisibility(View.GONE);
            isOpen = false;
        }
    }
}
