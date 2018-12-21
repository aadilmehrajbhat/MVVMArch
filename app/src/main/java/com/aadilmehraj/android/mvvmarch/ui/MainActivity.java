package com.aadilmehraj.android.mvvmarch.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.aadilmehraj.android.mvvmarch.R;
import com.aadilmehraj.android.mvvmarch.adapter.CategoryItemAdapter;
import com.aadilmehraj.android.mvvmarch.adapter.ExpandableListAdapter;
import com.aadilmehraj.android.mvvmarch.adapter.MainListAdapter;
import com.aadilmehraj.android.mvvmarch.adapter.PagerAdapter;
import com.aadilmehraj.android.mvvmarch.service.model.Category;
import com.aadilmehraj.android.mvvmarch.service.model.CategoryItem;
import com.aadilmehraj.android.mvvmarch.service.model.Item;
import com.aadilmehraj.android.mvvmarch.service.model.Model;
import com.aadilmehraj.android.mvvmarch.service.repository.MainRepository;
import com.aadilmehraj.android.mvvmarch.viewmodel.MainViewModel;
import com.aadilmehraj.android.mvvmarch.viewmodel.MainViewModelFactory;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private MainViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private MainListAdapter mAdapter;
    private ProgressBar mLoadingBar;
    private TabLayout mTabLayout;
    private PagerAdapter mPagerAdapter;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        if (intent == null || !intent.hasExtra(SplashActivity.EXTRA_CATEGORY)) {
            throw new IllegalArgumentException("Categories Argument not found");
        }
        ArrayList<Category> categories = intent
            .getParcelableArrayListExtra(SplashActivity.EXTRA_CATEGORY);


        MainRepository mainRepository = MainRepository.getInstance(getApplication());
        MainViewModelFactory factory = new MainViewModelFactory(getApplication(), mainRepository);
        mViewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);
        mViewModel.setCategoryList(categories);

        mRecyclerView = findViewById(R.id.recyclerView);
        mLoadingBar = findViewById(R.id.loading_bar);
        mTabLayout =  findViewById(R.id.tabLayout);
        mViewPager = findViewById(R.id.viewPager);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MainListAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        DividerItemDecoration divider = new DividerItemDecoration(this,
            DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(divider);

        initViewModel();

        initExpandable();
        setUpTabLayout();
    }

    private void initExpandable() {
        final ExpandableListView expandableListView = findViewById(R.id.expandableListView);

        final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        RecyclerView expandRecyclerView = findViewById(R.id.expand_recycler_view);
        final CategoryItemAdapter adapter = new CategoryItemAdapter(this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                int childPosition, final long id) {
                CategoryItem mCategory = (CategoryItem) parent.getExpandableListAdapter()
                    .getChild(groupPosition, childPosition);
                Toast.makeText(getApplicationContext(), mCategory.getTitle(), Toast.LENGTH_SHORT)
                    .show();
                mLoadingBar.setVisibility(View.VISIBLE);
                drawerLayout.closeDrawer(Gravity.START);
                adapter.setData(null);
                mViewModel.fetchItems(mCategory.getId());
                mViewModel.getItemsLive().observe(MainActivity.this, new Observer<List<Item>>() {
                    @Override
                    public void onChanged(@Nullable List<Item> items) {
                        Log.i(TAG, "Items loaded: " + items);
                        mLoadingBar.setVisibility(View.GONE);
                        adapter.setData(items);
                    }
                });

                return true;
            }
        });

        ExpandableListAdapter mExpandableListAdapter = new ExpandableListAdapter(MainActivity.this,
            mViewModel.getCategories());
        expandableListView.setAdapter(mExpandableListAdapter);

        // Configure navigation drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        );

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        expandRecyclerView.setAdapter(adapter);
        expandRecyclerView.setLayoutManager(layoutManager);
    }



    private void setUpTabLayout() {

        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());

        List<Category> categories= mViewModel.getCategories();
        for (int i=0;i<categories.size();i++){
            String tabTitle= categories.get(i).getTitle();
            mPagerAdapter.addFragment(new CategoryListFragment(),tabTitle);
            Log.d("test",categories.get(i).getTitle());

                  }

        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

    }


    /**
     * Adds observable to {@link MainViewModel}'s {@link LiveData}
     */
    private void initViewModel() {
        mViewModel.getItemListLive().observe(this, new Observer<Model>() {
            @Override
            public void onChanged(@Nullable Model model) {
                // Data loaded
                if (model != null) {
                    Log.i(TAG, "Data loaded: " + model);
                    mLoadingBar.setVisibility(View.GONE);
                    mAdapter.setData(model.getModelItems());

                    ActionBar actionBar = getSupportActionBar();
                    if (actionBar != null) {
                        actionBar.setTitle(model.getTitle());
                    }
                }
            }
        });

        mViewModel.getCategoryListLive().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categories) {
                Log.i(TAG, "Categories loaded: " + categories);
                mLoadingBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Categories loaded", Toast.LENGTH_SHORT).show();
            }
        });

        mViewModel.getErrorLive().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String errorMsg) {
                mLoadingBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
