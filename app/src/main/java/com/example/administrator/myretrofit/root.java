package com.example.administrator.myretrofit;

import com.google.gson.JsonElement;

import java.io.Serializable;

/**
 * Author HuangKaiYi
 * Created on 2016/12/20 0020.
 */
public class root implements Serializable{
    private String info;

    private int status;

    private String version;

    public void setInfo(String info){
        this.info = info;
    }
    public String getInfo(){
        return this.info;
    }
    public void setStatus(int status){
        this.status = status;
    }
    public int getStatus(){
        return this.status;
    }
    public void setVersion(String version){
        this.version = version;
    }
    public String getVersion(){
        return this.version;
    }

    private JsonElement data;

    public void setData(JsonElement data){
        this.data = data;
    }
    public JsonElement getData(){
        return this.data;
    }

}
