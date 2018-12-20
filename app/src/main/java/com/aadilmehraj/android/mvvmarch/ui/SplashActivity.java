package com.aadilmehraj.android.mvvmarch.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.aadilmehraj.android.mvvmarch.R;
import com.aadilmehraj.android.mvvmarch.service.model.Category;
import com.aadilmehraj.android.mvvmarch.service.repository.MainRepository;
import com.aadilmehraj.android.mvvmarch.viewmodel.MainViewModel;
import com.aadilmehraj.android.mvvmarch.viewmodel.MainViewModelFactory;
import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    public static final String EXTRA_CATEGORY = "com.aadilmehraj.android.mvvmarch.ui.extra.category";

    private static final String LOG = SplashActivity.class.getSimpleName();
    private static final int LOGO_ANIM_DURATION = 1500;
    private static final int TITLE_ANIM_DURATION = 1500;

    private static final int SPLASH_DURATION = 3000;

    private ImageView mLogoView;
    private TextView mTitleView;
    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        mLogoView = findViewById(R.id.logo);
        mTitleView = findViewById(R.id.title);

        MainViewModelFactory factory = new MainViewModelFactory(getApplication(),
            MainRepository.getInstance(getApplication()));
        mViewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);
        mViewModel.fetchData();
        initViewModel();
    }

    private void initViewModel() {
        mViewModel.getCategoryListLive().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categories) {
                if (categories != null) {
                    navigateToMain((ArrayList<Category>) categories);
                }
            }
        });
    }

    private void navigateToMain(ArrayList<Category> categories) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putParcelableArrayListExtra(EXTRA_CATEGORY, categories);
        finish();
        startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();
        animateViews();
    }


    /**
     * Animate ImageView and TextView using alpha animation
     */
    private void animateViews() {
        ObjectAnimator logoAnim = ObjectAnimator.ofFloat(mLogoView, "alpha", 0f, 1f)
            .setDuration(LOGO_ANIM_DURATION);
        ObjectAnimator titleAnim = ObjectAnimator.ofFloat(mTitleView, "alpha", 0f, 1f)
            .setDuration(TITLE_ANIM_DURATION);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(logoAnim, titleAnim);
        animatorSet.start();
    }

}
