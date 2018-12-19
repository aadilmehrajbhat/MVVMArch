package com.aadilmehraj.android.mvvmarch.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.aadilmehraj.android.mvvmarch.service.model.Model;
import com.aadilmehraj.android.mvvmarch.service.repository.MainRepository;
import com.google.firebase.database.FirebaseDatabase;

public class MainViewModel extends ViewModel {

    private Application mAppContext;
    private MainRepository mMainRepository;

    private MutableLiveData<Model> mItemListLive = new MutableLiveData<>();

    public MainViewModel(Application appContext, MainRepository mainRepository) {
        mAppContext = appContext;
        mMainRepository = mainRepository;

        fetchData();
    }

    /**
     * Fetches the data from the {@link FirebaseDatabase}
     *
     */
    private void fetchData() {
        mItemListLive = (MutableLiveData<Model>) mMainRepository.queryFirebase();
    }

    public LiveData<Model> getItemListLive() {
        return mItemListLive;
    }
}
