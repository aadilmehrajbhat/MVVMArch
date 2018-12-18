package com.aadilmehraj.android.mvvmarch.viewmodel;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import com.aadilmehraj.android.mvvmarch.service.repository.MainRepository;

public class MainViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private Application mAppContext;
    private MainRepository mMainRepository;

    public MainViewModelFactory(Application appContext, MainRepository mainRepository) {
        mAppContext = appContext;
        mMainRepository = mainRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainViewModel(mAppContext, mMainRepository);
    }
}
