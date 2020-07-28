package com.imooc.o2o.dto;

import com.imooc.o2o.entity.LocalAuth;
import com.imooc.o2o.enums.LocalAuthStateEnum;

import java.util.List;

public class LocalAuthExecution {
    //處理的狀態碼
    private int state;
    //處理的描述
    private String stateInfo;
    //查詢時返回的對象
    private LocalAuth localAuth;

    private int count;

    private List<LocalAuth> localAuthList;

    public LocalAuthExecution() {
    }

    //失敗時的構造方法
    public LocalAuthExecution(LocalAuthStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }
    //成功時的構造方法
    public LocalAuthExecution(LocalAuthStateEnum stateEnum,LocalAuth localAuth) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.localAuth = localAuth;
    }
    //成功時的構造方法
    public LocalAuthExecution(LocalAuthStateEnum stateEnum,List<LocalAuth> localAuthList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.localAuthList = localAuthList;
    }
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public LocalAuth getLocalAuth() {
        return localAuth;
    }

    public void setLocalAuth(LocalAuth localAuth) {
        this.localAuth = localAuth;
    }

    public List<LocalAuth> getLocalAuthList() {
        return localAuthList;
    }

    public void setLocalAuthList(List<LocalAuth> localAuthList) {
        this.localAuthList = localAuthList;
    }
}
