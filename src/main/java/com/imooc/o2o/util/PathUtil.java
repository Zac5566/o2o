package com.imooc.o2o.util;


//處理路徑相關
public class PathUtil {

    private static String separator = System.getProperty("file.separator");

    //因應不同作業系統，設定存圖片的路徑
    public static String getImgBasePath(){
        String os = System.getProperty("os.name");
        String basePath = "";
        if(os.toLowerCase().startsWith("win")){
            basePath = "C:/Users/L/Desktop/img/upload/images";
        }else {
            basePath = "/home/john/image/";
        }
        basePath = basePath.replace("/",separator);
        return basePath;
    }
    //為店舖新建一個資料夾用來存儲該店的圖片
    public static String getShopImagePath(Long shopId){
        String path = "/upload/item/shop/"+ shopId +"/";
        return path.replace("/",separator);
    }
}
