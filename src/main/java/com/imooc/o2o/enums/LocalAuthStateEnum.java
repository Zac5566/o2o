package com.imooc.o2o.enums;

public enum LocalAuthStateEnum {
    LOGINFAIL(-1, "帳號或密碼錯誤"), SUCCESS(0, "操作成功")
    , NULL_AUTH_INFO(-1006, "註冊訊息為空")
    , ONLY_ONE_COUNT(-1007, "最多只能綁定一個本地帳號");

    private int state;
    private String stateInfo;

    //設置為私有化是因為不希望程序外面去改變enum值
    private LocalAuthStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static LocalAuthStateEnum stateOf(int state) {
        for (LocalAuthStateEnum stateEnums : values()) {
            if (stateEnums.getState() == state) {
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
