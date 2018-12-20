package com.aadilmehraj.android.mvvmarch.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aadilmehraj.android.mvvmarch.adapter.ExpandableListAdapter;
import com.aadilmehraj.android.mvvmarch.adapter.MainListAdapter;
import com.aadilmehraj.android.mvvmarch.R;
import com.aadilmehraj.android.mvvmarch.service.model.Category;
import com.aadilmehraj.android.mvvmarch.service.model.CategoryItem;
import com.aadilmehraj.android.mvvmarch.service.model.Model;
import com.aadilmehraj.android.mvvmarch.service.repository.MainRepository;
import com.aadilmehraj.android.mvvmarch.viewmodel.MainViewModel;
import com.aadilmehraj.android.mvvmarch.viewmodel.MainViewModelFactory;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    private MainViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private MainListAdapter mAdapter;
    private ProgressBar mLoadingBar;
    private ExpandableListView mExpandableListView;
    int lastpostion=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mExpandableListView=findViewById(R.id.expandableListView);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        if (intent == null || !intent.hasExtra(SplashActivity.EXTRA_CATEGORY)) {
            throw new IllegalArgumentException("Categories Argument not found");
        }
        ArrayList<Category> categories = intent.getParcelableArrayListExtra(SplashActivity.EXTRA_CATEGORY);

        MainRepository mainRepository = MainRepository.getInstance(getApplication());
        MainViewModelFactory factory = new MainViewModelFactory(getApplication(), mainRepository);
        mViewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);
        mViewModel.setCategoryList(categories);

        mRecyclerView = findViewById(R.id.recyclerView);
        mLoadingBar = findViewById(R.id.loading_bar);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MainListAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        DividerItemDecoration divider = new DividerItemDecoration(this,
            DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(divider);

        initViewModel();
        initExpandable();
        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
              //  Toast.makeText(getApplicationContext(),)
                return false;
            }
        });
        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    CategoryItem mCategory= (CategoryItem) parent.getExpandableListAdapter().getChild(groupPosition,childPosition);
                    Toast.makeText(getApplicationContext(),mCategory.getTitle(),Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if(lastpostion!=-1 && lastpostion!=groupPosition)
                {
                    mExpandableListView.collapseGroup(lastpostion);
                }
                lastpostion=groupPosition;
            }
        });
    }

    private void initExpandable() {
        ExpandableListAdapter  mExpandableListAdapter=new ExpandableListAdapter(MainActivity.this,mViewModel.getCategories());
        mExpandableListView.setAdapter(mExpandableListAdapter);

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
                    mAdapter.setData(model.getItems());

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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

}
