package com.aadilmehraj.android.mvvmarch.service.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;
import com.aadilmehraj.android.mvvmarch.service.model.Category;
import com.aadilmehraj.android.mvvmarch.service.model.CategoryItem;
import com.aadilmehraj.android.mvvmarch.service.model.Item;
import com.aadilmehraj.android.mvvmarch.service.model.ModelItem;
import com.aadilmehraj.android.mvvmarch.service.model.Model;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class MainRepository {

    public static final String TAG = MainRepository.class.getSimpleName();

    private static MainRepository INSTANCE;
    private static Application mAppContext;

    private MutableLiveData<String> mErrorLive;

    private MainRepository() {
        mErrorLive = new MutableLiveData<>();
    }

    public static MainRepository getInstance(Application appContext) {
        if (INSTANCE == null) {
            INSTANCE = new MainRepository();
            mAppContext = appContext;

        }
        return INSTANCE;
    }

    /**
     * Queries {@link FirebaseDatabase} for the {@link Model} data.
     *
     * @return {@link LiveData<Model>} to observe data
     */
    public LiveData<Model> queryFirebase() {
        final Query query = FirebaseDatabase.getInstance().getReference().child("model");
        final MutableLiveData<Model> mutableLiveData = new MutableLiveData<>();

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Model model = new Model();
                ArrayList<ModelItem> modelItems = new ArrayList<>();
                String title = (String) dataSnapshot.child("title").getValue();

                for (DataSnapshot snapshot : dataSnapshot.child("modelItems").getChildren()) {
                    ModelItem modelItem = snapshot.getValue(ModelItem.class);
                    modelItems.add(modelItem);
                }

                model.setTitle(title);
                model.setModelItems(modelItems);
                Log.i(TAG, "List load: " + model);
                mutableLiveData.postValue(model);
                query.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mErrorLive.setValue(databaseError.getMessage());
            }

        });

        return mutableLiveData;
    }


    /**
     * Queries {@link FirebaseDatabase} for the {@link Item} data.
     *
     * @return {@link LiveData<Item>} to observe data
     */
    public LiveData<List<Item>> queryItems(String itemId) {
        final Query query = FirebaseDatabase.getInstance().getReference().child("items")
            .orderByChild("cat_id").equalTo(itemId);
        final MutableLiveData<List<Item>> mutableLiveData = new MutableLiveData<>();

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<Item> items = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Item item = snapshot.getValue(Item.class);
                    items.add(item);
                }

                Log.i(TAG, "List load: " + items);
                mutableLiveData.postValue(items);
                query.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mErrorLive.setValue(databaseError.getMessage());
            }

        });

        return mutableLiveData;
    }


    /**
     * Queries {@link FirebaseDatabase} for the {@link Category} data
     *
     * @return {@link LiveData<Category>} data to be observed
     */
    public LiveData<List<Category>> getCategoryList() {
        final Query query = FirebaseDatabase.getInstance().getReference().child("category");
        final MutableLiveData<List<Category>> categoriesLive = new MutableLiveData<>();

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<Category> categories = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Category category = new Category();
                    String categoryId = (String) snapshot.child("cat_id").getValue();
                    String id = (String) snapshot.child("id").getValue();
                    String title = (String) snapshot.child("title").getValue();
                    String url = (String) snapshot.child("url").getValue();

                    ArrayList<CategoryItem> subCategories = new ArrayList<>();
                    for (DataSnapshot subCategorySnapshot : snapshot.child("subcategory")
                        .getChildren()) {
                        CategoryItem subItem = subCategorySnapshot.getValue(CategoryItem.class);
                        subCategories.add(subItem);
                    }

                    category.setCategoryId(categoryId);
                    category.setId(id);
                    category.setTitle(title);
                    category.setUrl(url);
                    category.setCategoryItems(subCategories);
                    categories.add(category);
                }

                Log.i(TAG, "Categories: " + categories);
                categoriesLive.postValue(categories);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mErrorLive.setValue(databaseError.getMessage());
            }
        });

        return categoriesLive;
    }


    public LiveData<String> getErrorLive() {
        return mErrorLive;
    }
}
