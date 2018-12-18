package com.aadilmehraj.android.mvvmarch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.aadilmehraj.android.mvvmarch.adapter.MainListAdapter;

public class MainActivity extends AppCompatActivity {



    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mlayoutManager;
    MainListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mlayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mlayoutManager);
        adapter = new MainListAdapter(this);


    }
}
