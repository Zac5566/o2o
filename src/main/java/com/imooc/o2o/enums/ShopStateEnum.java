package com.imooc.o2o.enums;

public  enum ShopStateEnum {
    CHECK(0,"審核中"),OFFLINE(-1,"非法店鋪"),SUCCESS(1,"操作成功")
    ,PASS(2,"通過審核"),INNER_ERROR(-1001,"內部系統錯誤"),NULL_SHOPID(-1002,"ShopId為空")
    ,NULL_SHOP(-1003,"SHOP訊息為空");

    private int state;
    private String stateInfo;

    //設置為私有化是因為不希望程序外面去改變enum值
    private ShopStateEnum(int state,String stateInfo){
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static ShopStateEnum stateOf(int state){
        for(ShopStateEnum stateEnums:values()){
            if(stateEnums.getState() == state){
                 return stateEnums;
            }
        }
        return null;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }
}
