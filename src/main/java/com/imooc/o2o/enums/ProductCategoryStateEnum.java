package com.imooc.o2o.enums;

public enum ProductCategoryStateEnum {
    SUCCESS(1,"操作成功"),INNER_ERROR(-1001,"操作失敗")
    ,EMPTY_LIST(-1002,"添加數少於一");

    private int state;
    private String stateInfo;

    private ProductCategoryStateEnum(int state,String stateInfo){
        this.state = state;
        this.stateInfo = stateInfo;
    }
    public static ProductCategoryStateEnum stateOf(int index){
        for(ProductCategoryStateEnum stateEnum:values()){
            if(stateEnum.getState() == index){
                return stateEnum;
            }
        }
        return null;
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
}
