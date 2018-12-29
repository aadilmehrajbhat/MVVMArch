package com.aadilmehraj.android.mvvmarch.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.aadilmehraj.android.mvvmarch.R;
import com.aadilmehraj.android.mvvmarch.adapter.CategoryItemAdapter.ItemAdapterViewHolder;
import com.aadilmehraj.android.mvvmarch.service.model.Item;
import com.bumptech.glide.Glide;
import java.util.List;

public class CategoryItemAdapter extends RecyclerView.Adapter<ItemAdapterViewHolder> {

    private Context mContext;
    private OnItemClickListener mClickListener;
    private List<Item> mItems;

    public interface OnItemClickListener {

        void onItemClick(Item item);
    }

    public CategoryItemAdapter(Context context, OnItemClickListener onItemClickListener) {
        mContext = context;
        mClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ItemAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext)
            .inflate(R.layout.main_category_list_item, viewGroup, false);
        return new ItemAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapterViewHolder itemAdapterViewHolder,
        int position) {
        itemAdapterViewHolder.bind(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return (mItems != null) ? mItems.size() : 0;
    }

    class ItemAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTitleTv;
        ImageView mThumbnailIv;

        public ItemAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitleTv = itemView.findViewById(R.id.category_name);
            mThumbnailIv = itemView.findViewById(R.id.category_imageView);
            itemView.setOnClickListener(this);
        }

        public void bind(Item item) {

            mTitleTv.setText(item.getTitle());
            Glide.with(mContext).load(item.getUrl()).into(mThumbnailIv);
        }

        @Override
        public void onClick(View view) {
            mClickListener.onItemClick(mItems.get(getAdapterPosition()));
        }
    }

    public void setData(List<Item> items) {
        mItems = items;
        notifyDataSetChanged();
    }
}
