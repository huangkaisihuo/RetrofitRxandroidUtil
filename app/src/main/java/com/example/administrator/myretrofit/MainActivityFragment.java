package com.example.administrator.myretrofit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements ICallBackListener{

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        Button button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        return view;
    }

    public void login() {
        RetrofitHelper retrofitHelper = RetrofitHelper.getInstance();
        Retrofit retrofit = retrofitHelper.getRetrofit();
        Map<String, String> options = new HashMap<>();
        options.put("type", "8");
        RequestServes requestSerives = retrofit.create(RequestServes.class);
        Observable<root> observable = requestSerives.Login(options);
        retrofitHelper.sendRequest(observable, this, ServerUrlConstants.getLoginUrl(), data.class);
    }

    public void getProductList() {
        RetrofitHelper retrofitHelper = RetrofitHelper.getInstance();
        Retrofit retrofit = retrofitHelper.getRetrofit();
        Map<String, String> options = new HashMap<>();
        options.put("type", "android");
        RequestServes requestSerives = retrofit.create(RequestServes.class);
        Observable<root> observable = requestSerives.ProductList();
        retrofitHelper.sendRequest(observable, this, ServerUrlConstants.getProductListUrl(), data.class);
    }

    @Override
    public void onSuccess(Object data, root r, String http) {
        if(http.equals(ServerUrlConstants.getLoginUrl())){
            data t = (data) data;
        }else if(http.equals(ServerUrlConstants.getProductListUrl())){

        }
    }

    @Override
    public void onFaild(root r, String http) {
        if(http.equals(ServerUrlConstants.getLoginUrl())){

        }else if(http.equals(ServerUrlConstants.getProductListUrl())){

        }
    }

    @Override
    public void onCompleted(String http) {
    }

    @Override
    public void onError(String http) {

    }
}
