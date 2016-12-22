package com.example.administrator.myretrofit;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author HuangKaiYi
 * Created on 2016/12/21 0021.
 */
public class RetrofitHelper<T> {

    private static final String BASE_URL = "http://betajuecainet.com/";
    private static RetrofitHelper mRetrofitHelper;
    private Retrofit mRetrofit;
    private Class<T> clazz;

    private RetrofitHelper() {
        int DEFAULT_TIMEOUT = 10;
        OkHttpClient client = null;
        try {
            client = new OkHttpClient.Builder()
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .sslSocketFactory(getSSLSocketFactory()).hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 使用RxJava作为回调适配器
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()) // 使用Gson作为数据转换器
                .client(client)
                .build();
    }

    public static RetrofitHelper getInstance() {
        if (mRetrofitHelper == null) {
            synchronized (RetrofitHelper.class) {
                if (mRetrofitHelper == null)
                    mRetrofitHelper = new RetrofitHelper();
            }
        }
        return mRetrofitHelper;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    /**
     * rxandroid的形式发起网络请求，并在信息返回后通过callBackListener回调
     *
     * @param observable rxandroid的观察者
     * @param callBackListener  回调接口
     * @param url  通过返回url做标识，在一个页面判断多个接口的返回处理逻辑
     * @param clazz  Gson解析的类型
     */
    public void sendRequest(Observable<root> observable, final ICallBackListener callBackListener, final String url,
                            final Class<T> clazz){
        observable.debounce(250, TimeUnit.MILLISECONDS)//只接受短时间内的传回的第一个数据
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<root>() {
                    @Override
                    public void onCompleted() {
                        callBackListener.onCompleted(url);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBackListener.onError(url);
                    }

                    @Override
                    public void onNext(root t) {
                        if(t.getStatus()==1){
                            Gson gson = new Gson();
                            //如果数据是集合，通过解析成传入的类型解析，并封装成一个List
                            if (t.getData().isJsonArray()) {
                                JsonArray array = t.getData().getAsJsonArray();
                                List<T> result = new ArrayList<T>();
                                for (int i = 0; i < array.size(); i++) {
                                    result.add(gson.fromJson(array.get(i), clazz));
                                }
                                callBackListener.onSuccess(result, t, url);
                            } else if (t.getData().isJsonObject()) {
                                //如果数据是json对象
                                callBackListener.onSuccess(gson.fromJson(t.getData(), clazz),t,url);
                            } else if (t.getData().isJsonPrimitive()) {
                                //如果数据是基本类型
                                callBackListener.onSuccess(gson.fromJson(t.getData(), clazz),t,url);
                            } else {
                                callBackListener.onSuccess(null,t,url);
                            }
                        }else{
                            callBackListener.onFaild(t,url);
                        }
                    }
                });

    }


    public static SSLSocketFactory getSSLSocketFactory() throws Exception {
        //创建一个不验证证书链的证书信任管理器。
        final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] chain,
                    String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] chain,
                    String authType) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[0];
            }
        }};

        // Install the all-trusting trust manager
        final SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts,
                new java.security.SecureRandom());
        // Create an ssl socket factory with our all-trusting manager
        return sslContext
                .getSocketFactory();
    }
}
