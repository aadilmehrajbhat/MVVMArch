package com.aadilmehraj.android.mvvmarch.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.aadilmehraj.android.mvvmarch.adapter.CategoryItemAdapter;
import com.aadilmehraj.android.mvvmarch.adapter.CategoryItemAdapter.OnItemClickListener;
import com.aadilmehraj.android.mvvmarch.databinding.ItemListFragmentBinding;
import com.aadilmehraj.android.mvvmarch.service.model.Item;
import com.aadilmehraj.android.mvvmarch.service.repository.MainRepository;
import com.aadilmehraj.android.mvvmarch.viewmodel.MainViewModel;
import com.aadilmehraj.android.mvvmarch.viewmodel.MainViewModelFactory;
import java.util.List;

public class ItemListFragment extends Fragment implements OnItemClickListener {

    private static final String TAG = ItemListFragment.class.getSimpleName();
    public static final String EXTRA_CAT_ID = "extra-category-id";
    private MainViewModel mViewModel;


    public interface OnItemClickListener {

        void onItemClick(Item item);
    }

    ItemListFragmentBinding mItemListBinding;
    private OnItemClickListener mItemClickListener;

    public ItemListFragment() {
    }

    public static ItemListFragment newInstance(String categoryId) {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_CAT_ID, categoryId);
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
        String catId = bundle.getString(EXTRA_CAT_ID);

        MainRepository mainRepository = MainRepository.getInstance(getActivity().getApplication());
        MainViewModelFactory factory = new MainViewModelFactory(getActivity().getApplication(), mainRepository);
        mViewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);

        mViewModel.fetchItems(catId);


        final CategoryItemAdapter adapter = new CategoryItemAdapter(getContext(), this);

        mItemListBinding.itemListRecyclerView
            .setLayoutManager(new GridLayoutManager(getContext(), 2));
        mItemListBinding.itemListRecyclerView.setAdapter(adapter);
        mItemListBinding.loadingBar.setVisibility(View.VISIBLE);

        mViewModel.getItemsLive().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable List<Item> items) {
                mViewModel.getItemsLive().removeObserver(this);
                Log.i(TAG, "Items loaded: " + items);
                Log.e(TAG, "getItems called");
                adapter.setData(items);
                mItemListBinding.loadingBar.setVisibility(View.GONE);
                Log.e(TAG, "After transaction back stack count: " + getActivity().getSupportFragmentManager()
                    .getBackStackEntryCount());
            }
        });

        return mItemListBinding.getRoot();
    }

    @Override
    public void onItemClick(Item item) {
        mItemClickListener.onItemClick(item);
    }
}
