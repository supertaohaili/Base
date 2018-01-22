package www.thl.com.base.view.statemanager;

import android.view.View;

/**
 * 页面状态点击事件监听器。
 */

public interface StateListener {

    interface OnClickListener {
        void onClick(View view);
    }

    interface ConvertListener {
        void convert(BaseViewHolder holder, StateLayout stateLayout);
    }
}