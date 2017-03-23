package com.thinkeract.tka.data.api.request;

/**
 * Created by lu on 2016/10/31.
 */

public class ListBody {
    protected String page;
    protected String pageSize;
    public void setPageSize(int pageSize){
        this.pageSize=String.valueOf(pageSize);
    }

    public  int getPageSize(){
        return Integer.parseInt(pageSize);
    }

    public void setPage(int page){
        this.page=String.valueOf(page);
    }
}
