package www.thl.com.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 单布局的Adapter
 * ---------------
 * 数据类型是泛型
 */
public abstract class SingleAdapter<T> extends RecyclerView.Adapter<SuperViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<T> items = new ArrayList<>();
    private int layoutId;

    public SingleAdapter(Context context, int layoutId) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.layoutId = layoutId;
    }


    @Override
    public SuperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SuperViewHolder(inflater.inflate(layoutId, parent, false));
    }

    @Override
    public void onBindViewHolder(SuperViewHolder holder, int position) {
        bindData(holder, items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public Context getContext() {
        return context;
    }

    public void setData(List<T> data) {
        this.items = data;
        notifyDataSetChanged();
    }

    public void addData(List<T> data) {
        this.items.addAll(data);
        notifyDataSetChanged();
    }

    public void addNewData(List<T> data) {
        this.items.clear();
        this.items.addAll(data);
        notifyDataSetChanged();
    }

    protected abstract void bindData(SuperViewHolder holder, T item);
}
