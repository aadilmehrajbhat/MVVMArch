package com.aadilmehraj.android.mvvmarch.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.aadilmehraj.android.mvvmarch.R;
import com.aadilmehraj.android.mvvmarch.adapter.DetailActivity;
import com.aadilmehraj.android.mvvmarch.adapter.ExpandableListAdapter;
import com.aadilmehraj.android.mvvmarch.adapter.MainListAdapter;
import com.aadilmehraj.android.mvvmarch.adapter.PagerAdapter;
import com.aadilmehraj.android.mvvmarch.databinding.ActivityMainDrawerBinding;
import com.aadilmehraj.android.mvvmarch.databinding.ActivityMainViewPagerBinding;
import com.aadilmehraj.android.mvvmarch.service.model.Category;
import com.aadilmehraj.android.mvvmarch.service.model.CategoryItem;
import com.aadilmehraj.android.mvvmarch.service.model.Item;
import com.aadilmehraj.android.mvvmarch.service.model.Model;
import com.aadilmehraj.android.mvvmarch.service.repository.MainRepository;
import com.aadilmehraj.android.mvvmarch.ui.CategoryListFragment.OnCategoryClickListener;
import com.aadilmehraj.android.mvvmarch.ui.ItemListFragment.OnItemClickListener;
import com.aadilmehraj.android.mvvmarch.utils.PreferenceUtils;
import com.aadilmehraj.android.mvvmarch.viewmodel.MainViewModel;
import com.aadilmehraj.android.mvvmarch.viewmodel.MainViewModelFactory;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnCategoryClickListener,
    OnItemClickListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    private ActivityMainDrawerBinding mDrawerBinding;

    private ActivityMainViewPagerBinding mViewPagerBinding;

    private MainViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private MainListAdapter mAdapter;
    private ProgressBar mLoadingBar;
    private TabLayout mTabLayout;
    private PagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private String fragmentId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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


        if (PreferenceUtils.isDrawerLayout(this)) {
            mDrawerBinding = DataBindingUtil.setContentView(this, R.layout.activity_main_drawer);
            setSupportActionBar(mDrawerBinding.mainToolbar);
            initExpandable();

        } else {
            mViewPagerBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_main_view_pager);
            setSupportActionBar(mViewPagerBinding.mainToolbar);
            setUpTabLayout();
        }



//        mRecyclerView = findViewById(R.id.recyclerView);
//        mLoadingBar = findViewById(R.id.loading_bar);
//        mTabLayout = findViewById(R.id.tabLayout);
//        mViewPager = findViewById(R.id.viewPager);
//
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mAdapter = new MainListAdapter(this);
//        mRecyclerView.setAdapter(mAdapter);
//        DividerItemDecoration divider = new DividerItemDecoration(this,
//            DividerItemDecoration.VERTICAL);
//        mRecyclerView.addItemDecoration(divider);
//
        initViewModel();


    }

    private void initExpandable() {

        assert mDrawerBinding != null;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, mDrawerBinding.drawerLayout, mDrawerBinding.mainToolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        );

        mDrawerBinding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mDrawerBinding.expandableListView
            .setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                    int childPosition, final long id) {
//                CategoryItem mCategory = (CategoryItem) parent.getExpandableListAdapter()
//                    .getChild(groupPosition, childPosition);
//                Toast.makeText(getApplicationContext(), mCategory.getTitle(), Toast.LENGTH_SHORT)
//                    .show();
//                mLoadingBar.setVisibility(View.VISIBLE);
//                drawerLayout.closeDrawer(Gravity.START);
//                adapter.setData(null);
//                mViewModel.fetchItems(mCategory.getId());
//                mViewModel.getItemsLive().observe(MainActivity.this, new Observer<List<Item>>() {
//                    @Override
//                    public void onChanged(@Nullable List<Item> items) {
//                        Log.i(TAG, "Items loaded: " + items);
//                        mLoadingBar.setVisibility(View.GONE);
//                        adapter.setData(items);
//                    }
//                });

                    return true;
                }
            });

        ExpandableListAdapter mExpandableListAdapter = new ExpandableListAdapter(MainActivity.this,
            mViewModel.getCategories());
        mDrawerBinding.expandableListView.setAdapter(mExpandableListAdapter);

    }


    private void setUpTabLayout() {

        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());

        List<Category> categories = mViewModel.getCategories();
        for (int i = 0; i < categories.size(); i++) {
            String tabTitle = categories.get(i).getTitle();
            CategoryListFragment fragment = CategoryListFragment.newInstance(categories.get(i));
            mPagerAdapter.addFragment(fragment, tabTitle);
        }

        mViewPagerBinding.categoryViewPager.setAdapter(mPagerAdapter);
        mViewPagerBinding.tabLayout.setupWithViewPager(mViewPagerBinding.categoryViewPager);

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
                Toast.makeText(MainActivity.this, "Categories loaded", Toast.LENGTH_SHORT).show();
            }
        });

        mViewModel.getErrorLive().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String errorMsg) {
                Toast.makeText(MainActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {

        Log.e(TAG, "Before onBackPressed: " + getSupportFragmentManager().getBackStackEntryCount());
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStackImmediate();
            Log.e(TAG, "Fragment popped up: ");
        } else if ((mDrawerBinding != null) && (mDrawerBinding.drawerLayout
            .isDrawerOpen(GravityCompat.START))) {
            mDrawerBinding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        Log.e(TAG, "After onBackPressed: " + getSupportFragmentManager().getBackStackEntryCount());
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            showViewPager();
        }


    }

    private void showViewPager() {
        mViewPagerBinding.categoryViewPager.setVisibility(View.VISIBLE);
        mViewPagerBinding.tabLayout.setVisibility(View.VISIBLE);
    }


    @Override
    public void onCategoryClick(CategoryItem categoryItem) {
        mViewPagerBinding.categoryViewPager.setVisibility(View.GONE);
        mViewPagerBinding.tabLayout.setVisibility(View.GONE);
        mViewPagerBinding.loadingBar.setVisibility(View.VISIBLE);
        mViewModel.fetchItems(categoryItem.getId());
        mViewModel.getItemsLive().observe(MainActivity.this, new Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable List<Item> items) {
                mViewModel.getItemsLive().removeObserver(this);
                Log.i(TAG, "Items loaded: " + items);
                Log.e(TAG, "getItems called");
                mViewPagerBinding.loadingBar.setVisibility(View.GONE);
                ItemListFragment itemListFragment = ItemListFragment.newInstance(items);

                fragmentId = "frag_" + items.get(0).getCategoryId();
                getSupportFragmentManager().beginTransaction()
                    .replace(mViewPagerBinding.fragmentContainer.getId(), itemListFragment)
                    .addToBackStack("")
                    .commit();
                Log.e(TAG, "After transaction back stack count: " + getSupportFragmentManager()
                    .getBackStackEntryCount());
            }
        });
    }

    @Override
    public void onItemClick(Item item) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_ITEM, item);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                setLayout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private void setLayout() {

        boolean isDrawerLayout = PreferenceUtils.isDrawerLayout(this);
        if (isDrawerLayout) {
            PreferenceUtils.setDrawerLayout(this, false);
            Toast.makeText(this, "Changed to Tab layout", Toast.LENGTH_SHORT).show();
        } else {
            PreferenceUtils.setDrawerLayout(this, true);
            Toast.makeText(this, "Changed to DrawerLayout layout", Toast.LENGTH_SHORT).show();
        }
        recreate();
    }
}
