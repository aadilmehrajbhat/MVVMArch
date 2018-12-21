package com.aadilmehraj.android.mvvmarch.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;
import com.aadilmehraj.android.mvvmarch.service.model.Category;
import com.aadilmehraj.android.mvvmarch.service.model.Item;
import com.aadilmehraj.android.mvvmarch.service.model.Model;
import com.aadilmehraj.android.mvvmarch.service.repository.MainRepository;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {

    private Application mAppContext;
    private MainRepository mMainRepository;

    private MediatorLiveData<Model> mItemListLive = new MediatorLiveData<>();
    private MediatorLiveData<List<Category>> mCategoryListLive = new MediatorLiveData<>();
    private MediatorLiveData<List<Item>> mItemsLive = new MediatorLiveData();
    private LiveData<String> mErrorLive;

    private ArrayList<Category> mCategories;

    public MainViewModel(Application appContext, MainRepository mainRepository) {
        mAppContext = appContext;
        mMainRepository = mainRepository;

        mErrorLive = mainRepository.getErrorLive();
    }

    /**
     * Fetches the data from the {@link FirebaseDatabase}
     */
    public void fetchData() {

        final LiveData<Model> modelLiveData = mMainRepository.queryFirebase();
        LiveData<List<Category>> categoriesLiveData = mMainRepository.getCategoryList();

        mItemListLive.addSource(modelLiveData, new Observer<Model>() {
            @Override
            public void onChanged(@Nullable Model model) {
                mItemListLive.removeSource(modelLiveData);
                mItemListLive.setValue(model);
            }
        });

        mCategoryListLive.addSource(categoriesLiveData,
            new Observer<List<Category>>() {
                @Override
                public void onChanged(@Nullable List<Category> categories) {
                    mCategoryListLive.removeSource(mCategoryListLive);
                    mCategories = (ArrayList<Category>) categories;
                    mCategoryListLive.setValue(categories);
                }
            });
    }

    public void fetchItems(String itemId) {
        final LiveData<List<Item>> modelLiveData = mMainRepository.queryItems(itemId);

        mItemsLive.addSource(modelLiveData, new Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable List<Item> items) {
                mItemsLive.removeSource(modelLiveData);
                mItemsLive.setValue(items);
            }
        });
    }


    public void setCategoryList(List<Category> categories) {
        mCategories = (ArrayList<Category>) categories;
        mCategoryListLive.setValue(categories);
    }

    public LiveData<Model> getItemListLive() {
        return mItemListLive;
    }

    public LiveData<String> getErrorLive() {
        return mErrorLive;
    }

    public LiveData<List<Category>> getCategoryListLive() {
        return mCategoryListLive;
    }

    public ArrayList<Category> getCategories() {
        return mCategories;
    }

    public LiveData<List<Item>> getItemsLive() {
        return mItemsLive;
    }
}
