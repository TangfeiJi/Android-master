package com.project.movice.modules.home.adappter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * the base adapter for RecyclerView
 * Created by huanghaibin on 16-5-3.
 */
@SuppressWarnings("unused")
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected List<T> mItems;
    protected Context mContext;
    protected LayoutInflater mInflater;

    protected String mSystemTime;

    protected int mSelectedPosition = -0;


    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    private OnClickListener onClickListener;
    private OnLongClickListener onLongClickListener;


    protected View mHeaderView;

    private OnLoadingHeaderCallBack onLoadingHeaderCallBack;

    public BaseRecyclerAdapter(Context context) {
        mItems = new ArrayList<>();
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        //mFooterView = mInflater.inflate(R.layout.footer_view, null);
        initListener();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    /**
     * 初始化listener
     */
    private void initListener() {
        onClickListener = new OnClickListener() {
            @Override
            public void onClick(int position, long itemId) {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(position, itemId);
            }
        };

        onLongClickListener = new OnLongClickListener() {
            @Override
            public boolean onLongClick(int position, long itemId) {
                if (onItemLongClickListener != null) {
                    onItemLongClickListener.onLongClick(position, itemId);
                    return true;
                }
                return false;
            }
        };
    }

    public void setSystemTime(String systemTime) {
        this.mSystemTime = systemTime;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = onCreateDefaultViewHolder(parent, viewType);
        if (holder != null) {
            holder.itemView.setTag(holder);
            holder.itemView.setOnLongClickListener(onLongClickListener);
            holder.itemView.setOnClickListener(onClickListener);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindDefaultViewHolder(holder, getItems().get(position), position);
    }


    public T getSelectedItem() {
        if (mSelectedPosition < 0 || mSelectedPosition >= mItems.size())
            return null;
        return mItems.get(mSelectedPosition);
    }


    /**
     * 单选
     */
    public void setSelectedPosition(int selectedPosition) {
        if (selectedPosition != mSelectedPosition) {
            updateItem(mSelectedPosition);
            mSelectedPosition = selectedPosition;
            updateItem(mSelectedPosition);
        }
        this.mSelectedPosition = selectedPosition;
        updateItem(selectedPosition);
    }

    public int getCount() {
        return mItems.size();
    }

    protected abstract RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type);

    protected abstract void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, T item, int position);

    public final View getHeaderView() {
        return this.mHeaderView;
    }

    public final void setHeaderView(View view) {
        this.mHeaderView = view;
    }

    public final List<T> getItems() {
        return mItems;
    }


    public void addAll(List<T> items) {
        if (items != null) {
            this.mItems.addAll(items);
            notifyItemRangeInserted(this.mItems.size(), items.size());
        }
    }

    public final void addItem(T item) {
        if (item != null) {
            this.mItems.add(item);
            notifyItemChanged(mItems.size());
        }
    }


    public void addItem(int position, T item) {
        if (item != null) {
            this.mItems.add(position, item);
            notifyItemInserted(position);
        }
    }

    public void replaceItem(int position, T item) {
        if (item != null) {
            this.mItems.set(position, item);
            notifyItemChanged(position);
        }
    }

    public void updateItem(int position) {
        if (getItemCount() > position) {
            notifyItemChanged(position);
        }
    }


    public final void removeItem(T item) {
        if (this.mItems.contains(item)) {
            int position = mItems.indexOf(item);
            this.mItems.remove(item);
            notifyItemRemoved(position);
        }
    }

    public final void removeItem(int position) {
        if (this.getItemCount() > position) {
            this.mItems.remove(position);
            notifyItemRemoved(position);
        }
    }

    public final T getItem(int position) {
        int p = position;
        if (p < 0 || p >= mItems.size())
            return null;
        return mItems.get(position);
    }

    public final void resetItem(List<T> items) {
        if (items != null) {
            clear();
            addAll(items);
        }
    }

    public final void clear() {
        this.mItems.clear();
        notifyDataSetChanged();
    }


    /**
     * 添加项点击事件
     *
     * @param onItemClickListener the RecyclerView item click listener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 添加项点长击事件
     *
     * @param onItemLongClickListener the RecyclerView item long click listener
     */
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    protected void setOnLoadingHeaderCallBack(OnLoadingHeaderCallBack listener) {
        onLoadingHeaderCallBack = listener;
    }


    /**
     * 可以共用同一个listener，相对高效
     */
    public static abstract class OnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) v.getTag();
            onClick(holder.getAdapterPosition(), holder.getItemId());
        }

        public abstract void onClick(int position, long itemId);
    }


    /**
     * 可以共用同一个listener，相对高效
     */
    public static abstract class OnLongClickListener implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View v) {
            RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) v.getTag();
            return onLongClick(holder.getAdapterPosition(), holder.getItemId());
        }

        public abstract boolean onLongClick(int position, long itemId);
    }


    /**
     *
     */
    public interface OnItemClickListener {
        void onItemClick(int position, long itemId);
    }


    public interface OnItemLongClickListener {
        void onLongClick(int position, long itemId);
    }

    /**
     * for load header view
     */
    public interface OnLoadingHeaderCallBack {
        RecyclerView.ViewHolder onCreateHeaderHolder(ViewGroup parent);

        void onBindHeaderHolder(RecyclerView.ViewHolder holder, int position);
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        ProgressBar pb_footer;
        TextView tv_footer;

        FooterViewHolder(View view) {
            super(view);
//            pb_footer = (ProgressBar) view.findViewById(R.id.pb_footer);
//            tv_footer = (TextView) view.findViewById(R.id.tv_footer);
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
}
