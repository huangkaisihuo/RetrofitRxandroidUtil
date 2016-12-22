package com.example.administrator.myretrofit;

/**
 * Author HuangKaiYi
 * Created on 2016/12/21 0021.
 */
public interface ICallBackListener {
    /**
     * 成功时的返回数据
     *
     * @param data 根据传入类去解析后返回的实体
     * @param r  所有数据，包括msg，status
     * @param url  通过返回url做标识，在一个页面判断多个接口的返回处理逻辑
     */
    public void onSuccess(Object data,root r,String url);
    public void onFaild(root r,String url);
    public void onCompleted(String url);
    public void onError(String url);
}
