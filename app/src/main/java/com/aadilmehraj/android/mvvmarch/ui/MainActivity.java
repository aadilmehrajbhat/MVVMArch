package com.aadilmehraj.android.mvvmarch.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.aadilmehraj.android.mvvmarch.R;
import com.aadilmehraj.android.mvvmarch.service.model.Model;
import com.aadilmehraj.android.mvvmarch.service.repository.MainRepository;
import com.aadilmehraj.android.mvvmarch.viewmodel.MainViewModel;
import com.aadilmehraj.android.mvvmarch.viewmodel.MainViewModelFactory;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainRepository mainRepository = MainRepository.getInstance(getApplication());
        MainViewModelFactory factory = new MainViewModelFactory(getApplication(), mainRepository);
        mViewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);

        initViewModel();
    }

    private void initViewModel() {
        mViewModel.getItemListLive().observe(this, new Observer<Model>() {
            @Override
            public void onChanged(@Nullable Model model) {
                // Data loaded
                Log.i(TAG, "Data loaded: " + model);

            }
        });
    }
}
