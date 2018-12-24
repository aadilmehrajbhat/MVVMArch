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
import com.aadilmehraj.android.mvvmarch.service.model.Category;
import com.aadilmehraj.android.mvvmarch.service.model.CategoryItem;
import com.bumptech.glide.Glide;

public class CategoryListAdapter extends
    RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder> {

    public interface OnItemClickListener {

        void onItemClick(CategoryItem categoryItem);
    }

    private Context mContext;
    private Category mCategory;
    private OnItemClickListener mClickListener;

    public CategoryListAdapter(Context mContext, OnItemClickListener onItemClickListener) {
        this.mContext = mContext;
        mClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext)
            .inflate(R.layout.main_category_list_item, viewGroup, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int i) {

        CategoryItem categoryItem = mCategory.getCategoryItems().get(i);
        holder.bind(categoryItem);
    }

    @Override
    public int getItemCount() {
        if (mCategory != null) {
            return mCategory.getCategoryItems().size();
        }
        return 0;
    }

    public void setCategories(Category categories) {
        mCategory = categories;
        notifyDataSetChanged();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener {

        TextView mCategoryName;
        ImageView mCategoryImage;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            mCategoryName = itemView.findViewById(R.id.category_name);
            mCategoryImage = itemView.findViewById(R.id.category_imageView);
            itemView.setOnClickListener(this);

        }

        void bind(CategoryItem item) {
            mCategoryName.setText(item.getTitle());
            Glide.with(mContext).load(item.getUrl()).into(mCategoryImage);
        }

        @Override
        public void onClick(View view) {
            mClickListener.onItemClick(mCategory.getCategoryItems().get(getAdapterPosition()));
        }
    }
}
