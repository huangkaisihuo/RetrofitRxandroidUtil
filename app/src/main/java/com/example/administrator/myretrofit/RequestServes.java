package com.example.administrator.myretrofit;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Author HuangKaiYi
 * Created on 2016/12/22 0022.
 */
public interface RequestServes {
    @POST("api/app/login")
    Observable<root> Login(@QueryMap Map<String, String> options);

    @GET("api/app/item")
    Observable<root> ProductList();
}
