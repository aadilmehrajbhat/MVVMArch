package com.aadilmehraj.android.mvvmarch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.aadilmehraj.android.mvvmarch.model.StackApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Adapter adapter;
    ApiInterface apiInterface;
    StackApiResponse stackApiResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        apiInterface = ApiClient.getApiCLient().create(ApiInterface.class);

       Call<StackApiResponse> call =  apiInterface.getData(1,50,"stackoverflow");
        call.enqueue(new Callback<StackApiResponse>() {
            @Override
            public void onResponse(Call<StackApiResponse> call, Response<StackApiResponse> response) {
                 stackApiResponse = response.body();
                adapter = new Adapter(getApplicationContext());
                recyclerView.setAdapter(adapter);
                adapter.setData(stackApiResponse);
            }

            @Override
            public void onFailure(Call<StackApiResponse> call, Throwable t) {

            }
        });


    }
}
