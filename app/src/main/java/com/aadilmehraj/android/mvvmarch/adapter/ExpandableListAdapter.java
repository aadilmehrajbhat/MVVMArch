package com.aadilmehraj.android.mvvmarch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aadilmehraj.android.mvvmarch.R;
import com.aadilmehraj.android.mvvmarch.service.model.Category;
import com.aadilmehraj.android.mvvmarch.service.model.CategoryItem;
import com.bumptech.glide.Glide;

import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<Category> categoryList;

    public ExpandableListAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @Override
    public int getGroupCount() {
        return categoryList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        List<CategoryItem> categoryItems = categoryList.get(groupPosition).getCategoryItems();
        return categoryItems.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return categoryList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        List<CategoryItem> categoryItemList = categoryList.get(groupPosition).getCategoryItems();
        return categoryItemList.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
        ViewGroup parent) {
        Category category = (Category) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group_header, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.group_header_label);
        ImageView imageView = convertView.findViewById(R.id.group_head_iv);
        ImageView indicator = convertView.findViewById(R.id.group_header_indicator);
        indicator.setImageResource(
            (isExpanded) ? R.drawable.ic_expand_less_black_24dp
                : R.drawable.ic_expand_more_black_24dp
        );
        Glide.with(context).load(category.getUrl()).into(imageView);
        textView.setText(category.getTitle());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
        View convertView, ViewGroup parent) {
        CategoryItem item = (CategoryItem) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group_child, null);
        }
        TextView titleTextView = convertView.findViewById(R.id.group_child_label_tv);
        titleTextView.setText(item.getTitle());
        ImageView mImageView = convertView.findViewById(R.id.group_child_iv);
        Glide.with(context).load(item.getUrl()).into(mImageView);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
