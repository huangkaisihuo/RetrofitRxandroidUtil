package com.example.administrator.myretrofit;

import java.util.List;

/**
 * Author HuangKaiYi
 * Created on 2016/12/21 0021.
 */
public class data {
    private String id;

    private String title;

    private String stime;

    private String etime;

    private List<Banner> data ;


    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setStime(String stime){
        this.stime = stime;
    }
    public String getStime(){
        return this.stime;
    }
    public void setEtime(String etime){
        this.etime = etime;
    }
    public String getEtime(){
        return this.etime;
    }
    public void setShuju(List<Banner> data){
        this.data = data;
    }
    public List<Banner> getShuju(){
        return this.data;
    }


    public class Banner {
        private String add;

        private String url;

        private String des;

        private String type;

        public void setAdd(String add){
            this.add = add;
        }
        public String getAdd(){
            return this.add;
        }
        public void setUrl(String url){
            this.url = url;
        }
        public String getUrl(){
            return this.url;
        }
        public void setDes(String des){
            this.des = des;
        }
        public String getDes(){
            return this.des;
        }
        public void setType(String type){
            this.type = type;
        }
        public String getType(){
            return this.type;
        }

    }
}
