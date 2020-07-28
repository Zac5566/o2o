package com.imooc.o2o.dto;

//封裝json對象，所有返回結果都使用此類
public class Result<T> {

    private boolean success;
    private T data;
    private String errMsg;
    private int errCode;

    public Result() {
    }
    //成功時候的構造方法
    public Result(boolean success,T data) {
        this.success = success;
        this.data = data;
    }
    //失敗時候的構造方法
    public Result(boolean success,String errMsg,int errCode) {
        this.success = success;
        this.errMsg = errMsg;
        this.errCode = errCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }
}
