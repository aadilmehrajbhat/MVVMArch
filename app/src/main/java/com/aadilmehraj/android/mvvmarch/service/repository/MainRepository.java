package com.aadilmehraj.android.mvvmarch.service.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import com.aadilmehraj.android.mvvmarch.service.model.Item;
import com.aadilmehraj.android.mvvmarch.service.model.Model;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

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
                ArrayList<Item> items = new ArrayList<>();

                String title = (String) dataSnapshot.child("title").getValue();

                for (DataSnapshot snapshot : dataSnapshot.child("items").getChildren()) {
                    Item item = snapshot.getValue(Item.class);
                    items.add(item);
                }

                model.setTitle(title);
                model.setItems(items);
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


    public LiveData<String> getErrorLive() {
        return mErrorLive;
    }
}
