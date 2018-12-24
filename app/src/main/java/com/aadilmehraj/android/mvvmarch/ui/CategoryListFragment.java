package com.aadilmehraj.android.mvvmarch.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.aadilmehraj.android.mvvmarch.R;
import com.aadilmehraj.android.mvvmarch.adapter.CategoryListAdapter;
import com.aadilmehraj.android.mvvmarch.adapter.CategoryListAdapter.OnItemClickListener;
import com.aadilmehraj.android.mvvmarch.service.model.Category;
import com.aadilmehraj.android.mvvmarch.service.model.CategoryItem;

public class CategoryListFragment extends Fragment implements OnItemClickListener {


    public interface OnCategoryClickListener {

        void onCategoryClick(CategoryItem categoryItem);
    }

    public static final String EXTRA_CATEGORY = "Extra category";

    RecyclerView mRecyclerView;
    private CategoryListAdapter adapter;
    private CategoryItem category;
    private OnCategoryClickListener mCategoryClickListener;

    public CategoryListFragment() {

    }

    public static CategoryListFragment newInstance(Category category) {
        CategoryListFragment categoryListFragment = new CategoryListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_CATEGORY, category);
        categoryListFragment.setArguments(bundle);
        return categoryListFragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCategoryClickListener = (OnCategoryClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                "Activity must implement OnCategoryClickListener interface");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_list_fragment, container, false);

        Bundle bundle = getArguments();
        Category category = bundle.getParcelable(EXTRA_CATEGORY);

        mRecyclerView = view.findViewById(R.id.categoryRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        adapter = new CategoryListAdapter(getContext(), this);
        adapter.setCategories(category);
        mRecyclerView.setAdapter(adapter);

        return view;

    }

    @Override
    public void onItemClick(CategoryItem categoryItem) {
        mCategoryClickListener.onCategoryClick(categoryItem);
    }


}
