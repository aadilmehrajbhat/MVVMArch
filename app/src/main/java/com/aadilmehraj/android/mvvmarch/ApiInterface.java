package com.aadilmehraj.android.mvvmarch;

import com.aadilmehraj.android.mvvmarch.model.StackApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("answers")
    Call<StackApiResponse>getData(@Query("page") int page,
                                        @Query("pagesize") int size,
                                        @Query("site") String site);
}
