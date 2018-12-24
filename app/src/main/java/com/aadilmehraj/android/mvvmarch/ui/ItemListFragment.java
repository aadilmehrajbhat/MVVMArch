package com.aadilmehraj.android.mvvmarch.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.aadilmehraj.android.mvvmarch.adapter.CategoryItemAdapter;
import com.aadilmehraj.android.mvvmarch.adapter.CategoryItemAdapter.OnItemClickListener;
import com.aadilmehraj.android.mvvmarch.databinding.ItemListFragmentBinding;
import com.aadilmehraj.android.mvvmarch.service.model.Item;
import java.util.ArrayList;
import java.util.List;

public class ItemListFragment extends Fragment implements OnItemClickListener {

    private static final String TAG = ItemListFragment.class.getSimpleName();
    public static final String EXTRA_ITEMS = "extra-items";


    public interface OnItemClickListener {
        void onItemClick(Item item);
    }

    ItemListFragmentBinding mItemListBinding;
    private OnItemClickListener mItemClickListener;

    public ItemListFragment() {
    }

    public static ItemListFragment newInstance(List<Item> items) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(EXTRA_ITEMS, (ArrayList<? extends Parcelable>) items);
        ItemListFragment itemListFragment = new ItemListFragment();
        itemListFragment.setArguments(bundle);
        return itemListFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mItemClickListener = (OnItemClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement OnItemClickListener");
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        mItemListBinding = ItemListFragmentBinding.inflate(inflater, container, false);

        Bundle bundle = getArguments();
       List<Item> items = bundle.getParcelableArrayList(EXTRA_ITEMS);

        CategoryItemAdapter adapter = new CategoryItemAdapter(getContext(), this);
        adapter.setData(items);
        mItemListBinding.itemListRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mItemListBinding.itemListRecyclerView.setAdapter(adapter);

        return mItemListBinding.getRoot();
    }

    @Override
    public void onItemClick(Item item) {
        mItemClickListener.onItemClick(item);
    }
}
